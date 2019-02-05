package com.example.ddopik.phlogbusiness.ui.search.album.view;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseFragment;
import com.example.ddopik.phlogbusiness.base.widgets.CustomRecyclerView;
import com.example.ddopik.phlogbusiness.base.widgets.PagingController;
import com.example.ddopik.phlogbusiness.ui.album.view.AlbumPreviewActivity;
import com.example.ddopik.phlogbusiness.ui.search.mainSearchView.view.ExpandableListAdapter;
import com.example.ddopik.phlogbusiness.ui.search.mainSearchView.view.OnSearchTabSelected;
import com.example.ddopik.phlogbusiness.ui.search.mainSearchView.view.SearchActivity;
import com.example.ddopik.phlogbusiness.ui.search.mainSearchView.model.FilterOption;
import com.example.ddopik.phlogbusiness.base.commonmodel.Filter;
import com.example.ddopik.phlogbusiness.ui.search.album.presenter.AlbumSearchFragmentImpl;
import com.example.ddopik.phlogbusiness.ui.search.album.presenter.AlbumSearchPresenter;
import com.example.ddopik.phlogbusiness.utiltes.Constants;
import com.example.ddopik.phlogbusiness.utiltes.Utilities;
import com.example.softmills.phlog.ui.search.view.album.model.AlbumSearch;
import com.example.softmills.phlog.ui.search.view.album.view.AlbumSearchAdapter;
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

import static android.content.Context.INPUT_METHOD_SERVICE;
import static com.example.ddopik.phlogbusiness.ui.album.view.AlbumPreviewActivity.ALBUM_PREVIEW_ID;

/**
 * Created by abdalla_maged on 10/29/2018.
 */
public class AlbumSearchFragment extends BaseFragment implements AlbumSearchFragmentView, SearchActivity.OnFilterClicked {

    private String TAG = AlbumSearchFragment.class.getSimpleName();
    private View mainView;
    private EditText albumSearch;
    private TextView searchResultCount;
    private ExpandableListAdapter expandableListAdapter;
    private CustomRecyclerView albumSearchRv;
    private ExpandableListView filterExpListView;
    private ProgressBar progressBar;
    private AlbumSearchPresenter albumSearchPresenter;
    private List<Filter> filterList = new ArrayList<>();
    private DisplayMetrics metrics = new DisplayMetrics();
    private List<AlbumSearch> albumSearchList = new ArrayList<AlbumSearch>();
    private AlbumSearchAdapter albumSearchAdapter;
    private CompositeDisposable disposable = new CompositeDisposable();
    private PagingController pagingController;
    private OnSearchTabSelected onSearchTabSelected;


    private ConstraintLayout promptView;
    private ImageView promptImage;
    private TextView promptText;

    public static AlbumSearchFragment getInstance() {
        AlbumSearchFragment albumSearchFragment = new AlbumSearchFragment();
        return albumSearchFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_album_search, container, false);
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

        if (albumSearch.getText().toString().length() > 0) {
            albumSearchList.clear();
            albumSearchPresenter.getAlbumSearch(albumSearch.getText().toString().trim(), filterList,0);
        } //there is A search query exist

    }


    @Override
    protected void initPresenter() {
        albumSearchPresenter = new AlbumSearchFragmentImpl(getContext(), this);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void initViews() {

        albumSearch = onSearchTabSelected.getSearchView();
        searchResultCount = onSearchTabSelected.getSearchResultCount();
        progressBar = mainView.findViewById(R.id.album_search_filter_progress);
        filterExpListView = mainView.findViewById(R.id.filters_expand);
        albumSearchRv = mainView.findViewById(R.id.album_search_rv);
        expandableListAdapter = new ExpandableListAdapter(getActivity(), filterList);
        albumSearchAdapter = new AlbumSearchAdapter(albumSearchList);
        albumSearchRv.setAdapter(albumSearchAdapter);
        filterExpListView.setAdapter(expandableListAdapter);

        //////// setting ExpandableList indicator to RIGHT
        Objects.requireNonNull(getActivity()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        filterExpListView.setIndicatorBoundsRelative(width - Utilities.GetPixelFromDips(getContext(),50), width - Utilities.GetPixelFromDips(getContext(),10));
        filterExpListView.setIndicatorBoundsRelative(width - Utilities.GetPixelFromDips(getContext(),50), width - Utilities.GetPixelFromDips(getContext(),10));
        ///////////
        searchResultCount.setText(new StringBuilder().append(this.albumSearchList.size()).append(" ").append(getResources().getString(R.string.result)).toString());
        searchResultCount.setTextColor(getActivity().getResources().getColor(R.color.white));

        promptView = mainView.findViewById(R.id.prompt_view);
        promptImage = mainView.findViewById(R.id.prompt_image);
        promptImage.setBackgroundResource(R.drawable.ic_album_search);
        promptText = mainView.findViewById(R.id.prompt_text);
        promptText.setText(R.string.type_something_album);
    }

    private void initListener() {


        disposable.add(

                RxTextView.textChangeEvents(albumSearch)
                        .skipInitialValue()
                        .debounce(Constants.QUERY_SEARCH_TIME_OUT, TimeUnit.MILLISECONDS)
                        .distinctUntilChanged()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(searchQuery())
        );


        pagingController = new PagingController(albumSearchRv) {
            @Override
            public void getPagingControllerCallBack(int page) {
                if (albumSearch.getText().length() > 0) {
                    albumSearchPresenter.getAlbumSearch(albumSearch.getText().toString().trim(), filterList,page);
                }

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



        albumSearchAdapter.setOnAlbumPreview(albumSearch -> {
            Intent intent = new Intent(getActivity(), AlbumPreviewActivity.class);
            intent.putExtra(ALBUM_PREVIEW_ID, albumSearch.getId());
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });
    }


    private DisposableObserver<TextViewTextChangeEvent> searchQuery() {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {

                if (textViewTextChangeEvent.getCount() == 0) {
                    promptView.setVisibility(View.VISIBLE);
                    promptText.setText(R.string.type_something_album);
                    return;
                }
                // user cleared search get default data
                albumSearchList.clear();
                albumSearchPresenter.getAlbumSearch(albumSearch.getText().toString().trim(), filterList,0);
                Log.e(TAG,"search string: "+albumSearch.getText().toString());

        }

        @Override
        public void onError (Throwable e){

        }

        @Override
        public void onComplete () {

        }
    };
}

    @Override
    public void viewSearchAlbum(List<AlbumSearch> albumSearchList) {
        filterExpListView.setVisibility(View.GONE);
        albumSearchRv.setVisibility(View.VISIBLE);

        this.albumSearchList.addAll(albumSearchList);
        albumSearchAdapter.notifyDataSetChanged();

        /**
         * Replacing (Apply) in case Expandable was previously visible
         * */
        searchResultCount.setVisibility(View.VISIBLE);
        searchResultCount.setTextColor(getResources().getColor(R.color.white));
        searchResultCount.setText(new StringBuilder().append(this.albumSearchList.size()).append(" ").append(getResources().getString(R.string.result)).toString());
        searchResultCount.setTextColor(getActivity().getResources().getColor(R.color.white));
        Utilities.hideKeyboard(getActivity());


        if (this.albumSearchList.size() == 0) {
            promptView.setVisibility(View.VISIBLE);
            promptText.setText(R.string.could_not_find_albums);
        } else {
            promptView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }




    @Override
    public void showMessage(String msg) {
        showToast(msg);
    }

    @Override
    public void showFilterSearchProgress(boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }



    @Override
    public void onFilterIconClicked(List<Filter> filterList) {
        filterExpListView.setVisibility(View.VISIBLE);
        albumSearchRv.setVisibility(View.GONE);
        promptView.setVisibility(View.GONE);

        if (this.filterList.size() == 0) {
            this.filterList.addAll(filterList);
            expandableListAdapter.notifyDataSetChanged();
        }


        searchResultCount.setText(getActivity().getResources().getString(R.string.apply));
        searchResultCount.setVisibility(View.VISIBLE);
        searchResultCount.setTextColor(getActivity().getResources().getColor(R.color.text_input_color));
        searchResultCount.setOnClickListener(v -> {
            albumSearchList.clear();
            albumSearchAdapter.notifyDataSetChanged();
            albumSearchPresenter.getAlbumSearch(albumSearch.getText().toString(), filterList, 0);
        });


    }


    public void setAlbumSearchView(OnSearchTabSelected onSearchTabSelected) {
        this.onSearchTabSelected = onSearchTabSelected;
    }


}
