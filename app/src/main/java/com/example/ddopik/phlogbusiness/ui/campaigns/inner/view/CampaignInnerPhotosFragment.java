package com.example.ddopik.phlogbusiness.ui.campaigns.inner.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseFragment;
import com.example.ddopik.phlogbusiness.base.PagingController;
import com.example.ddopik.phlogbusiness.base.widgets.CustomRecyclerView;
import com.example.ddopik.phlogbusiness.ui.campaigns.inner.model.DataItem;
import com.example.ddopik.phlogbusiness.ui.campaigns.inner.presenter.CampaignInnerPhotosFragmentPresenter;
import com.example.ddopik.phlogbusiness.ui.campaigns.inner.presenter.CampaignInnerPhotosFragmentPresenterImpl;
import com.example.ddopik.phlogbusiness.ui.commentimage.view.ImageCommentActivity;
import com.example.ddopik.phlogbusiness.utiltes.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged on 10/8/2018.
 */
public class CampaignInnerPhotosFragment extends BaseFragment implements CampaignInnerPhotosFragmentView {

    private String campaignID;
    private int status;
    private View mainView;
    private CustomRecyclerView campaignInnerRv;
    private ProgressBar campaignInnerProgressBar;
    private List<DataItem> photoGrapherPhotoList = new ArrayList<>();
    private GroupAdapter groupAdapter;
    private PagingController pagingController;
    private String nextPageUrl = "1";
    private boolean isLoading;
    private CampaignInnerPhotosFragmentPresenter campaignInnerPhotosFragmentPresenter;

    public static CampaignInnerPhotosFragment getInstance(String campaignID, Integer status) {
        CampaignInnerPhotosFragment campaignInnerPhotosFragment = new CampaignInnerPhotosFragment();
        campaignInnerPhotosFragment.campaignID = campaignID;
        campaignInnerPhotosFragment.status = status;
        return campaignInnerPhotosFragment;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mainView = inflater.inflate(R.layout.fragment_campaign_inner_photos, container, false);
        return mainView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        initViews();
        initLister();
    }

    @Override
    protected void initViews() {
        campaignInnerRv = mainView.findViewById(R.id.campaign_inner_rv);
        campaignInnerProgressBar = mainView.findViewById(R.id.campaign_inner_progress_bar);

        groupAdapter = new com.example.ddopik.phlogbusiness.ui.campaigns.inner.view.GroupAdapter(photoGrapherPhotoList, null);
        groupAdapter.setType(GroupAdapter.Type.GRID);
        groupAdapter.setPhotoAction(action);
        campaignInnerRv.setAdapter(groupAdapter);
        campaignInnerPhotosFragmentPresenter.getCampaignInnerPhotos(campaignID, "1");
    }

    @Override
    protected void initPresenter() {
        campaignInnerPhotosFragmentPresenter = new CampaignInnerPhotosFragmentPresenterImpl(getActivity().getBaseContext(), this);
    }

    private void initLister() {


        ////// initial block works by forcing then next Api for Each ScrollTop
        // cause recycler listener won't work until mainView ported with items
        campaignInnerRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            LinearLayoutManager mLayoutManager = (LinearLayoutManager) campaignInnerRv.getLayoutManager();

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

                    if (firstVisibleItemPosition == 0) {
                        if (nextPageUrl != null) {
//                            campaignInnerPhotosFragmentPresenter.getCampaignInnerPhotos(campaignID, nextPageUrl);

                        }

                    }
                }
            }
        });

        ////////////////
        pagingController = new PagingController(campaignInnerRv) {


            @Override
            protected void loadMoreItems() {
                campaignInnerPhotosFragmentPresenter.getCampaignInnerPhotos(campaignID, nextPageUrl);
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

    }

    public static void setHeightDynamically(GridView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i = i + 2) {
            view = listAdapter.getView(i, view, listView);
            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    @Override
    public void addPhotos(List<DataItem> data) {
        if (!data.isEmpty()) {
            if (!photoGrapherPhotoList.isEmpty()) {
                int insertPosition = photoGrapherPhotoList.size() - 1;
                DataItem firstInNew = data.remove(0);
                DataItem lastInOld = photoGrapherPhotoList.get(photoGrapherPhotoList.size() - 1);
                if (lastInOld != null) {
                    if (lastInOld.getHummanDate().equals(firstInNew.getHummanDate())) {
                        lastInOld.getPhotos().addAll(firstInNew.getPhotos());
                        photoGrapherPhotoList.addAll(data);
                    } else {
                        data.add(0, firstInNew);
                        photoGrapherPhotoList.addAll(data);
                    }
                    groupAdapter.notifyItemInserted(insertPosition);
                }
            } else {
                photoGrapherPhotoList.addAll(data);
                groupAdapter.notifyDataSetChanged();
            }
        }

    }


    @Override
    public void viewCampaignInnerPhotosProgress(boolean state) {
        isLoading = state;

        if (state) {
            campaignInnerProgressBar.setVisibility(View.VISIBLE);
        } else {
            campaignInnerProgressBar.setVisibility(View.GONE);
        }

    }

    private CampaignInnerPhotosAdapter.PhotoAction action = baseImage -> {
        Intent intent = new Intent(getContext(), ImageCommentActivity.class);
        intent.putExtra(ImageCommentActivity.IMAGE_DATA, baseImage);
        if (baseImage.canWin && !baseImage.isWon) {
            intent.putExtra(ImageCommentActivity.SHOULD_SHOW_CHOOSE_WINNER, true);
        }
        intent.putExtra(ImageCommentActivity.CAMPAIGN_ID, Integer.valueOf(campaignID));
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    };

    @Override
    public void setNextPageUrl(String page) {
        this.nextPageUrl = page;
    }
}
