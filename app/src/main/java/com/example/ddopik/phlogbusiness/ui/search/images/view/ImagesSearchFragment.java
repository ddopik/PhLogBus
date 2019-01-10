package com.example.ddopik.phlogbusiness.ui.search.images.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.ui.album.model.AlbumGroup;
import com.example.ddopik.phlogbusiness.ui.album.view.AllAlbumImgActivity;
import com.example.ddopik.phlogbusiness.ui.album.view.ImageCommentActivity;
import com.example.ddopik.phlogbusiness.ui.album.view.adapter.AlbumAdapter;
import com.example.ddopik.phlogbusiness.ui.search.SearchActivity;
import com.example.ddopik.phlogbusiness.ui.search.album.model.Filter;
import com.example.ddopik.phlogbusiness.ui.search.album.view.ExpandableListAdapter;
import com.example.ddopik.phlogbusiness.utiltes.Constants;
import com.example.ddopik.phlogbusiness.base.BaseFragment;
import com.example.ddopik.phlogbusiness.base.widgets.CustomRecyclerView;
import com.example.ddopik.phlogbusiness.base.widgets.PagingController;
 import com.example.ddopik.phlogbusiness.ui.search.OnSearchTabSelected;
import com.example.ddopik.phlogbusiness.ui.search.images.presenter.ImagesSearchFragmentPresenter;
import com.example.ddopik.phlogbusiness.ui.search.images.presenter.ImagesSearchFragmentPresenterImpl;
import com.example.ddopik.phlogbusiness.utiltes.Utilities;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.jakewharton.rxbinding3.widget.TextViewTextChangeEvent;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.example.ddopik.phlogbusiness.ui.album.view.AllAlbumImgActivity.*;

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

    private List<Filter> filterList = new ArrayList<>();
    private ExpandableListAdapter expandableListAdapter;
    private AlbumAdapter imageSearchAdapter;
    private List<AlbumGroup> albumGroupList = new ArrayList<>();

    private List<BaseImage> imagesSearchList = new ArrayList<>();
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

        if (onSearchTabSelected.getSearchView() != null) {
            initPresenter();
            initViews();
            initListener();

        }

        if (imageSearch.getText().toString().length() > 0)
            imagesSearchFragmentPresenter.getSearchImages(imageSearch.getText().toString().trim(), 0);
    }


    @Override
    protected void initPresenter() {
        imagesSearchFragmentPresenter = new ImagesSearchFragmentPresenterImpl(getContext(), this);
    }

    @Override
    protected void initViews() {

//        imageSearch = mainView.findViewById(R.id.search_brand);
        imageSearch = onSearchTabSelected.getSearchView();
        filterExpListView = mainView.findViewById(R.id.filters_expand);
        expandableListAdapter = new ExpandableListAdapter(getActivity(), filterList);

        searchResultCount = onSearchTabSelected.getSearchResultCount();
        searchImageRv = mainView.findViewById(R.id.search_images_rv);
        searchImageProgress = mainView.findViewById(R.id.search_images_progress_bar);
        imageSearchAdapter = new AlbumAdapter(getContext(), albumGroupList);
        searchImageRv.setAdapter(imageSearchAdapter);
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

        pagingController = new PagingController(searchImageRv) {
            @Override
            public void getPagingControllerCallBack(int page) {

                imagesSearchFragmentPresenter.getSearchImages(imageSearch.getText().toString().trim(), page );


            }
        };

        imageSearchAdapter.onAlbumImageClicked = imageSearch -> {
            Intent intent = new Intent(getContext(), ImageCommentActivity.class);
            intent.putExtra(ImageCommentActivity.IMAGE_DATA,imageSearch);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        };
    }

    private DisposableObserver<TextViewTextChangeEvent> searchQuery() {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                // user cleared search get default data

                imagesSearchList.clear();
                imagesSearchFragmentPresenter.getSearchImages(imageSearch.getText().toString().trim(), 0);
                imageSearchAdapter.notifyDataSetChanged();
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
    public void viewImagesSearchItems(List<BaseImage> baseImageList) {

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


            imageSearchAdapter.notifyDataSetChanged();
            Utilities.hideKeyboard(getActivity());
            searchResultCount.setText(new StringBuilder().append(this.imagesSearchList.size()).append(" ").append(getResources().getString(R.string.result)).toString());

        }



//        this.imagesSearchList.addAll(baseImageList);
//        imageSearchAdapter.notifyDataSetChanged();
//        imageSearchAdapter.notifyDataSetChanged();
//        searchResultCount.setText(new StringBuilder().append(this.imagesSearchList.size()).append(" ").append(getResources().getString(R.string.result)).toString());
//        hideSoftKeyBoard();
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


//    private void hideSoftKeyBoard() {
//        imageSearch.clearFocus();
//        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
//        if (imm.isAcceptingText()) { // verify if the soft keyboard is open
//            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
//        }
//    }

    @Override
    public void viewSearchFilters(List<Filter> filterList) {
        filterExpListView.setVisibility(View.VISIBLE);
        searchImageRv.setVisibility(View.GONE);
        this.filterList.addAll(filterList);
        expandableListAdapter.notifyDataSetChanged();


    }

    @Override
    public void onFilterIconClicked() {

    }
}
