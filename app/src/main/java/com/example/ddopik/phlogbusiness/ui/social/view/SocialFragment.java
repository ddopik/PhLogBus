package com.example.ddopik.phlogbusiness.ui.social.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseFragment;
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.base.widgets.CustomRecyclerView;
import com.example.ddopik.phlogbusiness.ui.MainActivity;
import com.example.ddopik.phlogbusiness.ui.album.view.AllAlbumImgActivity;
import com.example.ddopik.phlogbusiness.ui.brand.view.BrandInnerActivity;
import com.example.ddopik.phlogbusiness.ui.campaigns.inner.view.CampaignInnerActivity;
import com.example.ddopik.phlogbusiness.ui.social.model.Entite;
import com.example.ddopik.phlogbusiness.ui.social.model.SocialData;
import com.example.ddopik.phlogbusiness.ui.social.presenter.SocialFragmentPresenter;
import com.example.ddopik.phlogbusiness.ui.social.presenter.SocialFragmentPresenterImpl;
import com.example.ddopik.phlogbusiness.ui.social.view.adapter.SocialAdapter;
import com.example.ddopik.phlogbusiness.ui.userprofile.view.UserProfileActivity;
import com.example.ddopik.phlogbusiness.ui.search.mainSearchView.view.SearchActivity;
import com.example.ddopik.phlogbusiness.utiltes.Constants;

import java.util.ArrayList;
import java.util.List;

import static com.example.ddopik.phlogbusiness.ui.album.view.AllAlbumImgActivity.*;
import static com.example.ddopik.phlogbusiness.ui.userprofile.view.UserProfileActivity.USER_ID;


public class SocialFragment extends BaseFragment implements SocialFragmentView {

    private View mainView;
    private EditText homeSearch;
    private ProgressBar socialProgress;
    private CustomRecyclerView socailRv;
    private SocialFragmentPresenter socialFragmentPresenter;
    private SocialAdapter socialAdapter;
    private List<SocialData> socialDataList = new ArrayList<>();

    private ImageButton notificationButton;
    private TextView notificationCount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mainView =inflater.inflate(R.layout.fragment_social,container,false);
        return mainView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        initViews();
        initListener();
        socialFragmentPresenter.getSocialData();
    }


    @Override
    protected void initPresenter() {
        socialFragmentPresenter = new SocialFragmentPresenterImpl(getContext(), this);
    }

    @Override
    protected void initViews() {
        homeSearch=mainView.findViewById(R.id.home_search);
        socailRv = mainView.findViewById(R.id.social_rv);
        socialProgress = mainView.findViewById(R.id.social_progress);


        this.socialAdapter = new SocialAdapter(socialDataList);
        socailRv.setAdapter(socialAdapter);

        notificationButton = mainView.findViewById(R.id.notification_icon);
        notificationCount = mainView.findViewById(R.id.notification_count);
    }

    private void initListener(){
        mainView.setOnTouchListener((v, event) -> {
            return false;
        });
        homeSearch.setOnClickListener((v)->{
            Intent intent=new Intent(getActivity(), SearchActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        });

        socialAdapter.onSocialItemListener = new SocialAdapter.OnSocialItemListener() {
            @Override
            public void onSocialProfileClick(Entite entite) {
                Intent intent = new Intent(getActivity(), UserProfileActivity.class);
                intent.putExtra(USER_ID, String.valueOf(entite.id));
                startActivity(intent);

            }

            @Override
            public void OnFollowSocialProfileClick(Entite entite) {
                socialFragmentPresenter.followUser(String.valueOf(entite.id));
            }

            @Override
            public void onSocialCampaignClicked(Entite entite) {
                Intent intent = new Intent(getActivity(), CampaignInnerActivity.class);
                intent.putExtra(CampaignInnerActivity.CAMPAIGN_ID,String.valueOf(entite.id));
                startActivity(intent);
            }

            @Override
            public void onSocialFollowCampaignClicked(Entite entite) {
                socialFragmentPresenter.followSocialCampaign(String.valueOf(entite.id));
            }

            @Override
            public void onSocialSlideImageClicked(Entite entite) { ///todo selected img should be passed here
                Intent intent = new Intent(getActivity(), AllAlbumImgActivity.class);

//                intent.putExtra(SELECTED_IMG_ID,albumImg.albumImgId);/// todo album id should passed here
                List<BaseImage> albumImgList = new ArrayList<>();
                for (int i = 0; i < entite.imgs.size(); i++) {
                    BaseImage albumImg = new BaseImage();
                    albumImg.id = i;
                    albumImg.url = entite.imgs.get(i);
                    albumImgList.add(albumImg);
                }
                intent.putExtra(ALBUM_ID, String.valueOf(entite.id));
//                intent.putParcelableArrayListExtra(ALL_ALBUM_IMAGES, (ArrayList<? extends Parcelable>) albumImgList);
                intent.putExtra(SELECTED_IMG_ID, "1"); ///todo selected img id should be passed here
                startActivity(intent);
            }

            @Override
            public void onSocialBrandClicked(Entite entite) {
                Intent intent = new Intent(getActivity(), BrandInnerActivity.class);
                intent.putExtra(BrandInnerActivity.BRAND_ID, String.valueOf(entite.id));
                startActivity(intent);
            }

            @Override
            public void onSocialBrandFollowClicked(Entite entite) {
                socialFragmentPresenter.followSocialBrand(String.valueOf(entite.id));
            }
        };

        View.OnClickListener notificaionClickListener = v -> {
            MainActivity.navigationManger.navigate(Constants.NavigationHelper.NOTIFICATION);
        };
        notificationButton.setOnClickListener(notificaionClickListener);
        notificationCount.setOnClickListener(notificaionClickListener);
    }

    @Override
    public void viewSocialData(List<SocialData> socialDataList) {
        this.socialDataList.addAll(socialDataList);
        socialAdapter.notifyDataSetChanged();

    }

    @Override
    public void viewSocialDataProgress(boolean state) {
        if (state) {
            socialProgress.setVisibility(View.VISIBLE);
        } else {
            socialProgress.setVisibility(View.GONE);
        }
    }

    @Override
    public void showMessage(String msg) {
        showToast(msg);
    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }

}
