package com.example.ddopik.phlogbusiness.ui.search.profile.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseFragment;
import com.example.ddopik.phlogbusiness.base.commonmodel.Photographer;
import com.example.ddopik.phlogbusiness.base.widgets.CustomRecyclerView;
import com.example.ddopik.phlogbusiness.base.widgets.PagingController;
import com.example.ddopik.phlogbusiness.ui.search.mainSearchView.view.OnSearchTabSelected;
import com.example.ddopik.phlogbusiness.ui.search.profile.model.ProfileSearchData;
import com.example.ddopik.phlogbusiness.ui.search.profile.presenter.ProfileSearchPresenter;
import com.example.ddopik.phlogbusiness.ui.search.profile.presenter.ProfileSearchPresenterImpl;
import com.example.ddopik.phlogbusiness.ui.userprofile.view.UserProfileActivity;
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
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by abdalla_maged on 11/1/2018.
 */
public class ProfileSearchFragment extends BaseFragment implements ProfileSearchFragmentView {

    private String TAG = ProfileSearchFragment.class.getSimpleName();
    private View mainView;
    private EditText profileSearch;
    private TextView searchResultCount;
    private ProgressBar profileSearchProgress;
    private CustomRecyclerView profileSearchRv;
    private OnSearchTabSelected onSearchTabSelected;
    private PagingController pagingController;
    private ProfileSearchAdapter profileSearchAdapter;
    private List<Photographer> profileSearchList = new ArrayList<>();


    private ConstraintLayout promptView;
    private ImageView promptImage;
    private TextView promptText;

    private ProfileSearchPresenter profileSearchPresenter;
    private CompositeDisposable disposable = new CompositeDisposable();

    public static ProfileSearchFragment getInstance() {
        ProfileSearchFragment profileSearchFragment = new ProfileSearchFragment();
        return profileSearchFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_profile_search, container, false);

        return mainView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (onSearchTabSelected != null) {

            initPresenter();
            initViews();
            initListener();

            if (profileSearch.getText().toString().length() > 0) {
                promptView.setVisibility(View.GONE);
                profileSearchList.clear();
                profileSearchPresenter.getProfileSearchList(onSearchTabSelected.getSearchView().getText().toString().trim(), 0);
            }

        }

    }


    @Override
    protected void initViews() {

        profileSearchRv = mainView.findViewById(R.id.profile_search_rv);
        profileSearchProgress = mainView.findViewById(R.id.profile_search_progress_bar);
        profileSearch = onSearchTabSelected.getSearchView();
        searchResultCount = onSearchTabSelected.getSearchResultCount();
        profileSearchAdapter = new ProfileSearchAdapter(getContext(), profileSearchList);
        profileSearchRv.setAdapter(profileSearchAdapter);


        promptView = mainView.findViewById(R.id.prompt_view);
        promptImage = mainView.findViewById(R.id.prompt_image);
        promptImage.setBackgroundResource(R.drawable.ic_profile_search);
        promptText = mainView.findViewById(R.id.prompt_text);
        promptText.setText(R.string.type_something_profile);
    }

    @Override
    protected void initPresenter() {
        profileSearchPresenter = new ProfileSearchPresenterImpl(getContext(), this);
    }


    private void initListener() {
        disposable.add(
                RxTextView.textChangeEvents(profileSearch)
                        .skipInitialValue()
                        .debounce(Constants.QUERY_SEARCH_TIME_OUT, TimeUnit.MILLISECONDS)
                        .distinctUntilChanged()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(searchQuery()));





        pagingController = new PagingController(profileSearchRv) {
            @Override
            public void getPagingControllerCallBack(int page) {
                if (profileSearch.getText().length() > 0) {
                    promptView.setVisibility(View.GONE);
                    profileSearchPresenter.getProfileSearchList(profileSearch.getText().toString().trim(), page);
                }

            }
        };


        profileSearchAdapter.profileAdapterListener = profileSearch -> {
            Intent intent = new Intent(getActivity(), UserProfileActivity.class);
            intent.putExtra(UserProfileActivity.USER_ID, String.valueOf(profileSearch.id));
            startActivity(intent);
        };
    }

    private DisposableObserver<TextViewTextChangeEvent> searchQuery() {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                if (textViewTextChangeEvent.getCount() == 0) {
                    promptView.setVisibility(View.VISIBLE);
                    promptText.setText(R.string.type_something_profile);
                    return;
                }
                promptView.setVisibility(View.GONE);
                profileSearchList.clear();
                profileSearchAdapter.notifyDataSetChanged();
                profileSearchPresenter.getProfileSearchList(profileSearch.getText().toString().trim(), 0);
                Log.e(TAG, "search string: " + profileSearch.getText().toString());

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
    public void viewProfileSearchItems(ProfileSearchData profileSearchData) {
        this.profileSearchList.addAll(profileSearchData.data);
        profileSearchAdapter.notifyDataSetChanged();
        searchResultCount.setVisibility(View.VISIBLE);
        searchResultCount.setTextColor(getResources().getColor(R.color.white));
        searchResultCount.setText(new StringBuilder().append(profileSearchData.total).append(" ").append(getResources().getString(R.string.result)).toString());
        hideSoftKeyBoard();



        if (this.profileSearchList.size() == 0) {
            promptView.setVisibility(View.VISIBLE);
            promptText.setText(R.string.could_not_find_profiles);
        } else {
            promptView.setVisibility(View.GONE);
        }
    }

    @Override
    public void viewProfileSearchProgress(Boolean state) {
        if (state) {
            profileSearchProgress.setVisibility(View.VISIBLE);
        } else {
            profileSearchProgress.setVisibility(View.GONE);
        }

    }

    @Override
    public void showMessage(String msg) {
        showToast(msg);
    }


    private void hideSoftKeyBoard() {
        profileSearch.clearFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        if (imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void setOnSearchProfile(OnSearchTabSelected onSearchTabSelected) {
        this.onSearchTabSelected = onSearchTabSelected;
    }


}
