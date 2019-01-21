package com.example.ddopik.phlogbusiness.ui.search.images.view;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseFragment;
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.base.commonmodel.Filter;
import com.example.ddopik.phlogbusiness.base.widgets.CustomRecyclerView;
import com.example.ddopik.phlogbusiness.base.widgets.PagingController;
import com.example.ddopik.phlogbusiness.ui.album.model.AlbumGroup;
import com.example.ddopik.phlogbusiness.ui.album.view.adapter.AlbumAdapter;
import com.example.ddopik.phlogbusiness.ui.commentimage.view.ImageCommentActivity;
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
public class ImagesSearchFragment extends BaseFragment implements ImagesSearchFragmentView,SearchActivity.OnFilterClicked {

    private String TAG = ImagesSearchFragment.class.getSimpleName();
    private View mainView;
    private EditText imageSearch;
    private TextView searchResultCount;
    private CustomRecyclerView searchImageRv;
    private ProgressBar searchImageProgress;
    private ExpandableListView filterExpListView;
    private DisplayMetrics metrics = new DisplayMetrics();

    private List<Filter> filterList = new ArrayList<>();
    private ExpandableListAdapter expandableListAdapter;
    private AlbumAdapter imageSearchAdapter;
    private List<AlbumGroup> albumGroupList = new ArrayList<>();
    private ImagesSearchFragmentPresenter imagesSearchFragmentPresenter;
    private PagingController pagingController;
    private CompositeDisposable disposable = new CompositeDisposable();
    private OnSearchTabSelected onSearchTabSelected;

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

        }

        if (imageSearch.getText().toString().length() > 0)
            imagesSearchFragmentPresenter.getSearchImages(imageSearch.getText().toString().trim(), filterList, 0);
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
        expandableListAdapter = new ExpandableListAdapter(getActivity(), filterList);
        filterExpListView.setAdapter(expandableListAdapter);
        searchResultCount = onSearchTabSelected.getSearchResultCount();
        searchImageRv = mainView.findViewById(R.id.search_images_rv);
        searchImageProgress = mainView.findViewById(R.id.search_images_progress_bar);
        imageSearchAdapter = new AlbumAdapter(getContext(), albumGroupList);
        searchImageRv.setAdapter(imageSearchAdapter);


        //////// setting ExpandableList indicator to right
        Objects.requireNonNull(getActivity()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        filterExpListView.setIndicatorBoundsRelative(width - Utilities.GetPixelFromDips(getContext(), 50), width - Utilities.GetPixelFromDips(getContext(), 10));
        filterExpListView.setIndicatorBoundsRelative(width - Utilities.GetPixelFromDips(getContext(), 50), width - Utilities.GetPixelFromDips(getContext(), 10));
        ///////////
        searchResultCount.setText(new StringBuilder().append(getAlbumImagesCount()).append(" ").append(getResources().getString(R.string.result)).toString());
        searchResultCount.setTextColor(getActivity().getResources().getColor(R.color.white));

    }

    private void initListener() {


        disposable.add(

                RxTextView.textChangeEvents(imageSearch)
                        .skipInitialValue()
                        .debounce(Constants.QUERY_SEARCH_TIME_OUT, TimeUnit.MILLISECONDS)
                        .distinctUntilChanged()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(searchQuery()));


        /**
         * visibleThreshold =3 --> is a Special case for ImagesSearchFragment Adapter
         * */
        pagingController = new PagingController(searchImageRv,3) {

                      @Override
                public void getPagingControllerCallBack(int page) {

                    imagesSearchFragmentPresenter.getSearchImages(imageSearch.getText().toString().trim(), filterList, page);


                }
        };


        expandableListAdapter.onChildViewListener = filterOption -> {
            for (int i = 0; i < filterList.size(); i++) {
                for (int x = 0; x < filterList.get(i).options.size(); x++) {
                    FilterOption currFilterOption = filterList.get(i).options.get(x);

                    if (currFilterOption.id.equals(filterOption.id)) {
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
            intent.putExtra(ImageCommentActivity.IMAGE_DATA,imageSearch);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(intent, ImageCommentActivity.ImageComment_REQUEST_CODE);
        };
    }

    private DisposableObserver<TextViewTextChangeEvent> searchQuery() {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                // user cleared search get default data

                albumGroupList.clear();
                imageSearchAdapter.notifyDataSetChanged();
                imagesSearchFragmentPresenter.getSearchImages(imageSearch.getText().toString().trim(), filterList, 0);
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
    public void viewImagesSearchImages(List<BaseImage> baseImageList) {

        /**
         * parsing and loading image into AlbumGroups
         * */
        if (baseImageList.size() > 0) {
            for (int i = 0; i < baseImageList.size(); i++) {

                if (albumGroupList.size() == 0) {
                    AlbumGroup albumGroup = new AlbumGroup();
                    albumGroup.albumGroupList.add(baseImageList.get(i));
                    albumGroupList.add(albumGroup);
                } else if (albumGroupList.get(albumGroupList.size() - 1).albumGroupList.size() < 4) {
                    albumGroupList.get(albumGroupList.size() - 1).albumGroupList.add(baseImageList.get(i));
                } else {
                    AlbumGroup albumGroup = new AlbumGroup();
                    albumGroup.albumGroupList.add(baseImageList.get(i));
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
        searchResultCount.setText(new StringBuilder().append(getAlbumImagesCount()).append(" ").append(getResources().getString(R.string.result)).toString());
        searchResultCount.setTextColor(getActivity().getResources().getColor(R.color.white));
        Utilities.hideKeyboard(getActivity());


    }

    @Override
    public void viewImagesSearchProgress(boolean state) {
        if (state) {
            searchImageProgress.setVisibility(View.VISIBLE);
        } else {
            searchImageProgress.setVisibility(View.GONE);
        }

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

    public void setImagesSearchView(OnSearchTabSelected onSearchTabSelected) {
        this.onSearchTabSelected = onSearchTabSelected;
    }




    @Override
    public void onFilterIconClicked(List<Filter> filterList) {
        filterExpListView.setVisibility(View.VISIBLE);
        searchImageRv.setVisibility(View.GONE);

        if (this.filterList.size() == 0) {
            this.filterList.addAll(filterList);
            expandableListAdapter.notifyDataSetChanged();
        }


        searchResultCount.setText(getActivity().getResources().getString(R.string.apply));
        searchResultCount.setTextColor(getActivity().getResources().getColor(R.color.text_input_color));
        searchResultCount.setOnClickListener(v -> {
            albumGroupList.clear();
            imageSearchAdapter.notifyDataSetChanged();
            imagesSearchFragmentPresenter.getSearchImages(imageSearch.getText().toString(), filterList, 0);
        });


    }


    private int getAlbumImagesCount(){
        /**
         * inCase last Album wasn't filled with all 4 photos
         * */

        if (albumGroupList.size() > 0) {
            int lastImageCount = ((albumGroupList.get(albumGroupList.size() - 1)).albumGroupList.size());
          return   ((albumGroupList.size() - 1) * 4) + lastImageCount;
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

                for (int x=0;x<albumGroupList.size();x++) {
                    for (int i = 0; i < albumGroupList.get(x).albumGroupList.size(); i++) {
                        AlbumGroup albumGroup=albumGroupList.get(x);
                        if (selectedImage.id == albumGroup.albumGroupList.get(i).id) {
                            albumGroup.albumGroupList.get(i).isSaved=selectedImage.isSaved;
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


}
