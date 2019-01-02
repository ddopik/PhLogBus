package com.example.ddopik.phlogbusiness.ui.search.images.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.utiltes.Constants;
import com.example.ddopik.phlogbusiness.base.BaseFragment;
import com.example.ddopik.phlogbusiness.base.commonmodel.Brand;
import com.example.ddopik.phlogbusiness.base.widgets.CustomRecyclerView;
import com.example.ddopik.phlogbusiness.base.widgets.PagingController;
import com.example.ddopik.phlogbusiness.ui.brand.view.BrandInnerActivity;
import com.example.ddopik.phlogbusiness.ui.search.OnSearchTabSelected;
import com.example.ddopik.phlogbusiness.ui.search.images.presenter.BrandSearchFragmentPresenter;
import com.example.ddopik.phlogbusiness.ui.search.images.presenter.BrandSearchFragmentPresenterImpl;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.jakewharton.rxbinding3.widget.TextViewTextChangeEvent;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by abdalla_maged on 10/31/2018.
 */
public class ImagesSearchFragment extends BaseFragment implements ImagesSearchFragmentView {

    private String TAG = ImagesSearchFragment.class.getSimpleName();
    private View mainView;
    private EditText imageSearch;
    private TextView searchResultCount;
    private CustomRecyclerView searchImageRv;
    private ProgressBar searchImageProgress;
    private ImageSearchAdapter imageSearchAdapter;
    private List<Brand> brandSearchList = new ArrayList<>();
    private BrandSearchFragmentPresenter brandSearchFragmentPresenter;
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
        mainView = inflater.inflate(R.layout.fragment_brand_search, container, false);
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
            brandSearchFragmentPresenter.getSearchBrand(imageSearch.getText().toString().trim(), 0);
    }


    @Override
    protected void initPresenter() {
        brandSearchFragmentPresenter = new BrandSearchFragmentPresenterImpl(getContext(), this);
    }

    @Override
    protected void initViews() {

//        imageSearch = mainView.findViewById(R.id.search_brand);
        imageSearch = onSearchTabSelected.getSearchView();
        searchResultCount = onSearchTabSelected.getSearchResultCount();
        searchImageRv = mainView.findViewById(R.id.search_brand_rv);
        searchImageProgress = mainView.findViewById(R.id.search_brand_progress_bar);
        imageSearchAdapter = new ImageSearchAdapter(getContext(), brandSearchList);
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

                brandSearchFragmentPresenter.getSearchBrand(imageSearch.getText().toString().trim(), page );


            }
        };

        imageSearchAdapter.brandAdapterListener = brandSearch -> {
            Intent intent = new Intent(getActivity(), BrandInnerActivity.class);
            intent.putExtra(BrandInnerActivity.BRAND_ID, String.valueOf(brandSearch.id));
            startActivity(intent);
        };
    }

    private DisposableObserver<TextViewTextChangeEvent> searchQuery() {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                // user cleared search get default data

                brandSearchList.clear();
                brandSearchFragmentPresenter.getSearchBrand(imageSearch.getText().toString().trim(), 0);
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
        this.brandSearchList.addAll(brandSearchList);
        imageSearchAdapter.notifyDataSetChanged();
        searchResultCount.setText(new StringBuilder().append(this.brandSearchList.size()).append(" ").append(getResources().getString(R.string.result)).toString());
        hideSoftKeyBoard();
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

    public void setBrandSearchView(OnSearchTabSelected onSearchTabSelected) {
        this.onSearchTabSelected = onSearchTabSelected;
    }


    private void hideSoftKeyBoard() {
        imageSearch.clearFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        if (imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }
}
