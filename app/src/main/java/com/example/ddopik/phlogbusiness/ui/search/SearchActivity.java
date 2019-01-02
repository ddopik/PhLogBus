package com.example.ddopik.phlogbusiness.ui.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseActivity;
import com.example.ddopik.phlogbusiness.base.widgets.CustomTextView;
import com.example.ddopik.phlogbusiness.ui.search.album.view.AlbumSearchFragment;
import com.example.ddopik.phlogbusiness.ui.search.images.view.ImagesSearchFragment;
import com.example.ddopik.phlogbusiness.ui.search.profile.view.ProfileSearchFragment;


/**
 * Created by abdalla_maged on 10/29/2018.
 */
public class SearchActivity extends BaseActivity {

    private EditText searchView;
    private CustomTextView brandTab, profileTab, albumTab, filterTab, searchResult;
    private FrameLayout searchContainer;
    private AlbumSearchFragment albumSearchFragment;
    private OnFilterClicked onFilterClicked;
    private OnSearchTabSelected onSearchTabSelected;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initPresenter();
        initView();
        initListener();
    }


    @Override
    public void initView() {

        searchView = findViewById(R.id.search_view);
        brandTab = findViewById(R.id.tab_brand);
        profileTab = findViewById(R.id.tab_profile);
        albumTab = findViewById(R.id.tab_album);
        searchContainer = findViewById(R.id.search_container);
        filterTab = findViewById(R.id.filter_ic);
        searchResult = findViewById(R.id.search_result);

    }

    @Override
    public void initPresenter() {

    }

    private void initListener() {

        onSearchTabSelected=new OnSearchTabSelected() {
            @Override
            public EditText getSearchView() {
                return searchView;
            }

            @Override
            public TextView getSearchResultCount() {
                return searchResult;
            }
        };

        brandTab.setOnClickListener((view) -> {
            setTapSelected(R.id.tab_brand);
            ImagesSearchFragment brandSearchFragment = ImagesSearchFragment.getInstance();
            brandSearchFragment.setBrandSearchView(onSearchTabSelected);
            addFragment(R.id.search_container, brandSearchFragment, ImagesSearchFragment.class.getSimpleName(), false);

        });
        profileTab.setOnClickListener((view) -> {
            setTapSelected(R.id.tab_profile);
            ProfileSearchFragment profileSearchFragment = ProfileSearchFragment.getInstance();
            profileSearchFragment.setOnSearchProfile(onSearchTabSelected);
            addFragment(R.id.search_container, profileSearchFragment, ProfileSearchFragment.class.getSimpleName(), false);

        });

        albumTab.setOnClickListener((view) -> {
            setTapSelected(R.id.tab_album);
            filterTab.setVisibility(View.VISIBLE);
            albumSearchFragment = AlbumSearchFragment.getInstance();
            onFilterClicked = albumSearchFragment;
            albumSearchFragment.setAlbumSearchView(onSearchTabSelected);
            addFragment(R.id.search_container, albumSearchFragment, AlbumSearchFragment.class.getSimpleName(), false);
        });

        filterTab.setOnClickListener(v -> {
            if (onFilterClicked != null) {
                onFilterClicked.onFilterIconClicked();
            }
        });

    }


    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }

    private void setTapSelected(int tabId) {
        brandTab.setTextColor(getResources().getColor(R.color.text_input_color));
        profileTab.setTextColor(getResources().getColor(R.color.text_input_color));
        albumTab.setTextColor(getResources().getColor(R.color.text_input_color));

        brandTab.setBackground(getResources().getDrawable(R.drawable.rounded_frame_orange));
        profileTab.setBackground(getResources().getDrawable(R.drawable.rounded_frame_orange));
        albumTab.setBackground(getResources().getDrawable(R.drawable.rounded_frame_orange));
        filterTab.setVisibility(View.GONE);

        switch (tabId) {
            case R.id.tab_brand:
                brandTab.setTextColor(getResources().getColor(R.color.white));
                brandTab.setBackground(getResources().getDrawable(R.drawable.rounded_frame_orange_fill));
                break;

            case R.id.tab_profile:
                profileTab.setTextColor(getResources().getColor(R.color.white));
                profileTab.setBackground(getResources().getDrawable(R.drawable.rounded_frame_orange_fill));
                break;

            case R.id.tab_album:
                albumTab.setTextColor(getResources().getColor(R.color.white));
                albumTab.setBackground(getResources().getDrawable(R.drawable.rounded_frame_orange_fill));
                break;

        }

    }

    public interface OnFilterClicked {
        void onFilterIconClicked();
    }
}