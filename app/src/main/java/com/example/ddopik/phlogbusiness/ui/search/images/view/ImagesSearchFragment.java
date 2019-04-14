package com.example.ddopik.phlogbusiness.ui.search.images.view;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseFragment;
import com.example.ddopik.phlogbusiness.base.PagingController;
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.base.commonmodel.Filter;
import com.example.ddopik.phlogbusiness.base.widgets.CustomRecyclerView;
import com.example.ddopik.phlogbusiness.base.widgets.CustomTextView;
import com.example.ddopik.phlogbusiness.ui.album.model.AlbumGroup;
import com.example.ddopik.phlogbusiness.ui.album.view.adapter.AlbumAdapter;
import com.example.ddopik.phlogbusiness.ui.commentimage.view.ImageCommentActivity;
import com.example.ddopik.phlogbusiness.ui.search.images.model.ImagesSearchData;
import com.example.ddopik.phlogbusiness.ui.search.images.presenter.ImagesSearchFragmentPresenter;
import com.example.ddopik.phlogbusiness.ui.search.images.presenter.ImagesSearchFragmentPresenterImpl;
import com.example.ddopik.phlogbusiness.ui.search.mainSearchView.model.FilterOption;
import com.example.ddopik.phlogbusiness.ui.search.mainSearchView.view.ExpandableListAdapter;
import com.example.ddopik.phlogbusiness.ui.search.mainSearchView.view.OnSearchTabSelected;
import com.example.ddopik.phlogbusiness.ui.search.mainSearchView.view.SearchActivity;
import com.example.ddopik.phlogbusiness.utiltes.Constants;
import com.example.ddopik.phlogbusiness.utiltes.Utilities;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.jakewharton.rxbinding3.widget.TextViewTextChangeEvent;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static android.app.Activity.RESULT_OK;


/**
 * Created by abdalla_maged on 10/31/2018.
 */
public class ImagesSearchFragment extends BaseFragment implements ImagesSearchFragmentView, SearchActivity.OnFilterClicked {

    private String TAG = ImagesSearchFragment.class.getSimpleName();
    private View mainView;
    private EditText imageSearch;
    private TextView searchResultCount;
    private CustomRecyclerView searchImageRv;
    private ProgressBar searchImageProgress;
    private ExpandableListView filterExpListView;
    private ConstraintLayout promptView;
    private ImageView promptImage;
    private TextView promptText;
    private DisplayMetrics metrics = new DisplayMetrics();

    private List<Filter> filterList = new ArrayList<>();
    private ExpandableListAdapter expandableListAdapter;
    private AlbumAdapter imageSearchAdapter;
    private List<AlbumGroup> albumGroupList = new ArrayList<>();
    private ImagesSearchFragmentPresenter imagesSearchFragmentPresenter;
    private PagingController pagingController;
    private String nextPageUrl = "1";
    private boolean isLoading;
    private CompositeDisposable disposable = new CompositeDisposable();
    private OnSearchTabSelected onSearchTabSelected;
    private CustomTextView filterIcon, clearFilterBtn;
    private String totalResultCount = "0";

    public static ImagesSearchFragment getInstance() {
        ImagesSearchFragment imagesSearchFragment = new ImagesSearchFragment();
        return imagesSearchFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_images_search, container, false);
        return mainView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (onSearchTabSelected != null) {
            initPresenter();
            initViews();
            initListener();

            imagesSearchFragmentPresenter.getSearchFilters();

        }

        if (imageSearch.getText().toString().length() > 0) {
            promptView.setVisibility(View.GONE);
            imagesSearchFragmentPresenter.getSearchImages(imageSearch.getText().toString().trim(), filterList, null);
        }
    }


    @Override
    protected void initPresenter() {
        imagesSearchFragmentPresenter = new ImagesSearchFragmentPresenterImpl(getContext(), this);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void initViews() {

//        imageSearch = mainView.findViewById(R.id.search_brand);
        imageSearch = onSearchTabSelected.getSearchView();
        filterExpListView = mainView.findViewById(R.id.images_filters_expand);
        filterList.clear();
        expandableListAdapter = new ExpandableListAdapter(getActivity(), filterList);
        filterExpListView.setAdapter(expandableListAdapter);
        searchResultCount = onSearchTabSelected.getSearchResultCountView();
        searchImageRv = mainView.findViewById(R.id.search_images_rv);
        searchImageProgress = mainView.findViewById(R.id.search_images_progress_bar);
        imageSearchAdapter = new AlbumAdapter(getContext(), albumGroupList);
        searchImageRv.setAdapter(imageSearchAdapter);

        promptView = mainView.findViewById(R.id.prompt_view);
        promptImage = mainView.findViewById(R.id.prompt_image);
        promptImage.setBackgroundResource(R.drawable.ic_image_search);
        promptText = mainView.findViewById(R.id.prompt_text);
        promptText.setText(R.string.type_something_image);

        //////// setting ExpandableList indicator to right
        Objects.requireNonNull(getActivity()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        filterExpListView.setIndicatorBoundsRelative(width - Utilities.GetPixelFromDips(Objects.requireNonNull(getContext()), 50), width - Utilities.GetPixelFromDips(getContext(), 10));
        filterExpListView.setIndicatorBoundsRelative(width - Utilities.GetPixelFromDips(Objects.requireNonNull(getContext()), 50), width - Utilities.GetPixelFromDips(getContext(), 10));
        ///////////
        searchResultCount.setText(new StringBuilder().append(getAlbumImagesCount()).append(" ").append(getResources().getString(R.string.result)).toString());
        searchResultCount.setTextColor(getActivity().getResources().getColor(R.color.white));
        searchResultCount.setVisibility(View.INVISIBLE);


    }

    private void initListener() {


        disposable.add(RxTextView.textChangeEvents(imageSearch)
                .skipInitialValue()
//                        .skipWhile(event -> event.getCount() == 0)
                .debounce(Constants.QUERY_SEARCH_TIME_OUT, TimeUnit.MILLISECONDS)
//                        .debounce(event -> event.getCount() > 0 ? Observable.just(event) : Observable.empty())
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(searchQuery()));


        pagingController = new PagingController(searchImageRv, 10/4) {


            @Override
            protected void loadMoreItems() {
                imagesSearchFragmentPresenter.getSearchImages(imageSearch.getText().toString().trim(), filterList, nextPageUrl);
            }

            @Override
            public boolean isLastPage() {

                if (nextPageUrl == null) {
                    return true;
                } else {
                    return false;
                }

            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

        };
        expandableListAdapter.onChildViewListener = filterOption -> {
            for (int i = 0; i < filterList.size(); i++) {
                for (int x = 0; x < filterList.get(i).options.size(); x++) {
                    FilterOption currFilterOption = filterList.get(i).options.get(x);
                    if (currFilterOption.displayName.equals(filterOption.displayName)) {
                        if (currFilterOption.isSelected) {
                            filterList.get(i).options.get(x).isSelected = false;
                        } else {
                            filterList.get(i).options.get(x).isSelected = true;
                        }
                        expandableListAdapter.notifyDataSetChanged();
                        return;
                    }
                }
            }
        };


        imageSearchAdapter.onAlbumImageClicked = imageSearch -> {
            Intent intent = new Intent(getActivity(), ImageCommentActivity.class);
            intent.putExtra(ImageCommentActivity.IMAGE_DATA, imageSearch);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(intent, ImageCommentActivity.ImageComment_REQUEST_CODE);
        };


        expandableListAdapter.onChildViewListener = this::setImagesResultStats;
    }

    @Override
    public void viewSearchFilters(List<Filter> filterList) {
        this.filterList.clear();
        this.filterList.addAll(filterList);

    }

    @Override
    public void onFilterCleared(CustomTextView clearResultBtn, boolean state) {
        clearSelectedFilters();
    }


    @Override
    public void onFilterIconClicked(CustomTextView filterIcon, CustomTextView clearFilterBtn) {

        this.filterIcon = filterIcon;
        this.clearFilterBtn = clearFilterBtn;

        if (filterExpListView.getVisibility() == View.GONE) { ///handle Search result screen

            filterIcon.setCompoundDrawablesWithIntrinsicBounds(null, null, AppCompatResources.getDrawable(getContext(), R.drawable.ic_filter), null);
            setSearchFilterView();


        } else { //handle filter result screen

            filterIcon.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            searchResultCount.setTextColor(getResources().getColor(R.color.white));
            filterExpListView.setVisibility(View.VISIBLE);
            clearFilterBtn.setVisibility(View.INVISIBLE);
            searchImageRv.setVisibility(View.GONE);
            setImagesSearchView();

            if (albumGroupList.size() == 0) {
                promptView.setVisibility(View.VISIBLE);
                promptImage.setBackgroundResource(R.drawable.ic_album_search);
                promptText.setText(R.string.type_something_album);
            }

        }


    }

    private void setImagesResultStats(FilterOption filterOption) {

//        searchResultCount.setVisibility(View.VISIBLE);

        for (int i = 0; i < filterList.size(); i++) {
            for (int x = 0; x < filterList.get(i).options.size(); x++) {
                FilterOption currFilterOption = filterList.get(i).options.get(x);
                if (currFilterOption.displayName.equals(filterOption.displayName)) {
                    if (currFilterOption.isSelected) {
                        filterList.get(i).options.get(x).isSelected = false;
                    } else {
                        filterList.get(i).options.get(x).isSelected = true;
                    }

                    expandableListAdapter.notifyDataSetChanged();
                    break;
                }
            }
        }


        if (filterIcon != null) {
            filterIcon.setText(new StringBuilder().append(getResources().getString(R.string.filters)).append(" ( ").append(imagesSearchFragmentPresenter.getFilter(filterList).size()).append(" )").toString());
        }


        //// listing for filter list selecting state
        if (imagesSearchFragmentPresenter.getFilter(filterList).size() > 0) {
            clearFilterBtn.setVisibility(View.VISIBLE);

        } else {
            clearFilterBtn.setVisibility(View.INVISIBLE);
        }

    }

    private void clearSelectedFilters() {

        for (int i = 0; i < filterList.size(); i++) {
            for (int x = 0; x < filterList.get(i).options.size(); x++) {
                filterList.get(i).options.get(x).isSelected = false;

            }
            expandableListAdapter.notifyDataSetChanged();
        }

        filterIcon.setText(getResources().getString(R.string.filters));
        clearFilterBtn.setVisibility(View.INVISIBLE);

    }

    private DisposableObserver<TextViewTextChangeEvent> searchQuery() {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                // user cleared search get default data
                if (textViewTextChangeEvent.getCount() == 0) {
//                    setTotalResultCount("0");
                    if (searchResultCount != null) {
                        searchResultCount.setVisibility(View.INVISIBLE);
                    }
                    promptView.setVisibility(View.VISIBLE);
                    promptText.setText(R.string.type_something_image);
                    return;
                }

                promptView.setVisibility(View.GONE);
                albumGroupList.clear();
                imageSearchAdapter.notifyDataSetChanged();
                imagesSearchFragmentPresenter.getSearchImages(imageSearch.getText().toString().trim(), filterList, null);
                Log.e(TAG, "search string: " + imageSearch.getText().toString());

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    @Override
    public void viewImagesSearchImages(ImagesSearchData imagesSearchData) {

        setTotalResultCount(String.valueOf(imagesSearchData.total));
        /**
         * parsing and loading image into AlbumGroups
         * */
        if (imagesSearchData.imageList != null && imagesSearchData.imageList.size() > 0) {
            for (int i = 0; i < imagesSearchData.imageList.size(); i++) {

                if (albumGroupList.size() == 0) {
                    AlbumGroup albumGroup = new AlbumGroup();
                    albumGroup.albumGroupList.add(imagesSearchData.imageList.get(i));
                    albumGroupList.add(albumGroup);
                } else if (albumGroupList.get(albumGroupList.size() - 1).albumGroupList.size() < 4) {
                    albumGroupList.get(albumGroupList.size() - 1).albumGroupList.add(imagesSearchData.imageList.get(i));
                } else {
                    AlbumGroup albumGroup = new AlbumGroup();
                    albumGroup.albumGroupList.add(imagesSearchData.imageList.get(i));
                    albumGroupList.add(albumGroup);
                }


            }
        }


        /**
         * Replacing (Apply) in case Expandable was previously visible
         * */
        filterExpListView.setVisibility(View.GONE);
        searchImageRv.setVisibility(View.VISIBLE);
        imageSearchAdapter.notifyDataSetChanged();
        searchResultCount.setVisibility(View.VISIBLE);
        searchResultCount.setTextColor(getResources().getColor(R.color.white));
        searchResultCount.setText(new StringBuilder().append(imagesSearchData.total).append(" ").append(getResources().getString(R.string.result)).toString());
        searchResultCount.setTextColor(Objects.requireNonNull(getActivity()).getResources().getColor(R.color.white));
        Utilities.hideKeyboard(getActivity());

        if (albumGroupList.size() == 0) {
            promptView.setVisibility(View.VISIBLE);
            promptText.setText(R.string.could_not_find_images);
        } else {
            promptView.setVisibility(View.GONE);
        }

        if (clearFilterBtn != null) {
            clearFilterBtn.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void viewImagesSearchFilterProgress(boolean state) {

        if (state) {
            searchImageProgress.setVisibility(View.VISIBLE);
        } else {
            searchImageProgress.setVisibility(View.GONE);
        }

    }

    @Override
    public void viewImagesSearchImagesProgress(boolean state) {
        isLoading = state;

        if (state) {
            searchImageProgress.setVisibility(View.VISIBLE);
        } else {
            searchImageProgress.setVisibility(View.GONE);
        }
    }

    @Override
    public void setNextPageUrl(String page) {
        this.nextPageUrl = page;
    }


    @Override
    public void showMessage(String msg) {
        showToast(msg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }


    private void setImagesSearchView() {
        filterExpListView.setVisibility(View.GONE);
        searchImageRv.setVisibility(View.VISIBLE);

        searchResultCount.setText(new StringBuilder().append(getTotalResultCount()).append(" ").append(getResources().getString(R.string.result)).toString());
    }

    public void setImagesSearchView(OnSearchTabSelected onSearchTabSelected) {
        this.onSearchTabSelected = onSearchTabSelected;
    }


    private int getAlbumImagesCount() {
        /**
         * inCase last Album wasn't filled with all 4 photos
         * */

        if (albumGroupList.size() > 0) {
            int lastImageCount = ((albumGroupList.get(albumGroupList.size() - 1)).albumGroupList.size());
            return ((albumGroupList.size() - 1) * 4) + lastImageCount;
        } else {
            return 0;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == ImageCommentActivity.ImageComment_REQUEST_CODE && resultCode == RESULT_OK) {
                BaseImage selectedImage = data.getParcelableExtra(ImageCommentActivity.IMAGE_DATA);

                for (int x = 0; x < albumGroupList.size(); x++) {
                    for (int i = 0; i < albumGroupList.get(x).albumGroupList.size(); i++) {
                        AlbumGroup albumGroup = albumGroupList.get(x);
                        if (selectedImage.id == albumGroup.albumGroupList.get(i).id) {
                            albumGroup.albumGroupList.get(i).isSaved = selectedImage.isSaved;
                            imageSearchAdapter.notifyDataSetChanged();
                            albumGroup.albumGroupList.set(i, selectedImage);
                            return;
                        }
                    }
                }
            }
        } catch (Exception ex) {

        }

    }

    private void setSearchFilterView() {

        filterExpListView.setVisibility(View.VISIBLE);
        searchImageRv.setVisibility(View.GONE);
        expandableListAdapter.notifyDataSetChanged();
        promptView.setVisibility(View.GONE);

        //// change state of (Apply and Clear All) Btn --->wither expandable list visible ot not
        if (imagesSearchFragmentPresenter.getFilter(filterList).size() > 0 && filterExpListView.getVisibility() == View.VISIBLE) {
            clearFilterBtn.setVisibility(View.VISIBLE);

        } else {
            clearFilterBtn.setVisibility(View.INVISIBLE);
        }


        searchResultCount.setText(R.string.apply);
        searchResultCount.setTextColor(getResources().getColor(R.color.text_input_color));
        searchResultCount.setVisibility(View.VISIBLE);
        searchResultCount.setOnClickListener(v -> { //searchResultCount switched to Apply in case Filter was visible
            filterIcon.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            searchResultCount.setTextColor(getResources().getColor(R.color.white));
            clearFilterBtn.setVisibility(View.INVISIBLE);
            ////
            promptView.setVisibility(View.GONE);
            albumGroupList.clear();
            imageSearchAdapter.notifyDataSetChanged();
            if (imagesSearchFragmentPresenter.getFilter(filterList).size() > 0) {
                imagesSearchFragmentPresenter.getSearchImages(imageSearch.getText().toString(), filterList, nextPageUrl);
            } else {
                viewImagesSearchImages(new ImagesSearchData());
                searchResultCount.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void setTotalResultCount(String count) {
        totalResultCount = count;
    }

    private String getTotalResultCount() {
        return totalResultCount;
    }

}
