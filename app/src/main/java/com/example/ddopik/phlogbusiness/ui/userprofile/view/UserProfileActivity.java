package com.example.ddopik.phlogbusiness.ui.userprofile.view;
/**
 * Created by Abdalla_maged on 9/30/2018.
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bumptech.glide.request.RequestOptions;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseActivity;
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.base.widgets.CustomRecyclerView;
import com.example.ddopik.phlogbusiness.base.widgets.PagingController;
import com.example.ddopik.phlogbusiness.ui.album.view.AllAlbumImgActivity;
import com.example.ddopik.phlogbusiness.ui.userprofile.presenter.UserProfilePresenter;
import com.example.ddopik.phlogbusiness.ui.userprofile.presenter.UserProfilePresenterImpl;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import com.example.ddopik.phlogbusiness.utiltes.GlideApp;
import com.o_bdreldin.loadingbutton.LoadingButton;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

import static com.example.ddopik.phlogbusiness.ui.album.view.AllAlbumImgActivity.*;
import static com.example.ddopik.phlogbusiness.utiltes.Constants.PhotosListType.USER_PROFILE_PHOTOS_LIST;


public class UserProfileActivity extends BaseActivity implements UserProfileActivityView {


    private static final String TAG = UserProfileActivity.class.getSimpleName();

    public static String USER_ID = "user_id";
    public static String USER_TYPE = "user_type";

    private String userID;
    private Toolbar userProfileToolBar;
    private AppBarLayout mAppBarLayout;
    private CollapsingToolbarLayout userProfileCollapsingToolbarLayout;
    private TextView userProfileLevel, userProfileUserName, userProfileFullName, userProfilePhotosCount, userProfileFolloweresCount, userProfileFollowingCount, userProfileToolbarTitle, userProfileFollowTitle;
    private RatingBar userProfileRating;
    private ImageView userProfileImg, userCoverImg;
    private CustomRecyclerView userProfilePhotosRv;
    private UserProfilePhotosAdapter userProfilePhotosAdapter;
    private UserProfilePresenter userProfilePresenter;
    private List<BaseImage> userPhotoList = new ArrayList<BaseImage>();
    private ProgressBar userProfilePhotosProgressBar;
    private LoadingButton followUser;
    private ImageButton backBtn;
    private PagingController pagingController;


    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        if (getIntent().getStringExtra(USER_ID) != null) {
            this.userID = getIntent().getStringExtra(USER_ID);
            initPresenter();
            initView();
            initListener();
        }
    }


    @Override
    public void initPresenter() {
        userProfilePresenter = new UserProfilePresenterImpl(getBaseContext(), this);
    }


    @Override
    public void initView() {


        mAppBarLayout = findViewById(R.id.user_profile_appBar);
        userProfileCollapsingToolbarLayout = findViewById(R.id.user_profile_collapsing_layout);
        userProfileToolBar = findViewById(R.id.user_profile_toolbar);
        userProfileToolbarTitle = findViewById(R.id.user_profile_toolbar_title);
        userProfileFollowTitle = findViewById(R.id.tool_bar_follow_user);
        backBtn = findViewById(R.id.back_btn);
        userProfileLevel = findViewById(R.id.user_profile_level);
        userProfileRating = findViewById(R.id.profile_rating);
        userProfileImg = findViewById(R.id.user_profile_img);
        userProfileFullName = findViewById(R.id.user_profile_full_name);
        userProfileUserName = findViewById(R.id.user_profile_username);
        userProfilePhotosCount = findViewById(R.id.photos_val);
        userProfileFolloweresCount = findViewById(R.id.followers_val);
        userProfileFollowingCount = findViewById(R.id.following_val);
        userProfilePhotosRv = findViewById(R.id.user_profile_photos);
        userProfilePhotosProgressBar = findViewById(R.id.user_profile_photos_progress_bar);
        followUser = findViewById(R.id.follow_user);
        userProfilePhotosAdapter = new UserProfilePhotosAdapter(this, userPhotoList);
        userProfilePhotosRv.setAdapter(userProfilePhotosAdapter);
        userProfilePresenter.getUserProfileData(userID);
        userProfilePresenter.getUserPhotos(userID, 0);

        userCoverImg = findViewById(R.id.user_cover_img);

    }


    private void initListener() {
        pagingController = new PagingController(userProfilePhotosRv) {
            @Override
            public void getPagingControllerCallBack(int page) {
                userProfilePresenter.getUserPhotos(userID, page + 1);
            }
        };
        followUser.setOnClickListener(v -> {
            setFollowUser();
        });


        userProfileFollowTitle.setOnClickListener(v -> {
            setFollowUser();
        });

        userProfilePhotosAdapter.photoAction = image -> {
            Intent intent = new Intent(this, AllAlbumImgActivity.class);
            intent.putExtra(SELECTED_IMG_ID, image.id);
            intent.putExtra(LIST_NAME, image.photographer.userName);
            intent.putExtra(LIST_TYPE, USER_PROFILE_PHOTOS_LIST);
            intent.putParcelableArrayListExtra(ALL_ALBUM_IMAGES, (ArrayList<? extends Parcelable>) userPhotoList);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        };


        userProfileCollapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.black));
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
                    userProfileToolBar.setVisibility(View.VISIBLE);
                } else if (isShow) {
                    isShow = false;
                    userProfileToolBar.setVisibility(View.GONE);
                }
            }
        });
        backBtn.setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }

    @Override
    public void viewUserProfileLevel(String userLevel) {
        userProfileLevel.setText(userLevel);
    }

    @Override
    public void viewUserProfileRating(float userRating) {
        userProfileRating.setRating(userRating);

    }

    @Override
    public void viewUserProfileProfileImg(String userImg) {
        GlideApp.with(this)
                .load(userImg)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.default_user_pic)
                .into(userProfileImg);
    }

    @Override
    public void viewUserProfileFullName(String fullName) {
        userProfileFullName.setText(fullName);
        userProfileToolbarTitle.setText(fullName);
    }

    @Override
    public void viewUserProfileUserName(String userName) {
        userProfileUserName.setText(userName);


    }

    @Override
    public void viewUserProfilePhotosCount(String photosCount) {
        userProfilePhotosCount.setText(photosCount);
    }

    @Override
    public void viewUserProfileFollowersCount(String followersCount) {
        userProfileFolloweresCount.setText(followersCount);
    }

    @Override
    public void viewUserProfileFollowingCount(String followingCount) {
        userProfileFollowingCount.setText(followingCount);
    }

    @Override
    public void viewUserPhotos(List<BaseImage> userPhotoList) {
        this.userPhotoList.addAll(userPhotoList);
        userProfilePhotosAdapter.notifyDataSetChanged();

    }

    @Override
    public void viewUserPhotosProgress(boolean state) {
        if (state) {
            userProfilePhotosProgressBar.setVisibility(View.VISIBLE);
        } else {
            userProfilePhotosProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showMessage(String msg) {
        super.showToast(msg);
    }

    private boolean following;

    @Override
    public void setIsFollowing(boolean follow) {
        following = follow;
        if (following) {
            followUser.setText(getString(R.string.un_follow));
            userProfileFollowTitle.setText(R.string.un_follow);
        } else {
            followUser.setText(getString(R.string.follow));
            userProfileFollowTitle.setText(R.string.follow);
        }
    }

    private void setFollowUser() {

        if (following) {
            Disposable disposable = userProfilePresenter.unfollow(userID)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(userProfileData -> {
                        following = userProfileData.isFollow();
                        if (!userProfileData.isFollow()) {
                            followUser.setText(R.string.follow);
                            userProfileFollowTitle.setText(R.string.follow);
                            userProfileFolloweresCount.setText("" + userProfileData.getFollowersCount());
                        }
                    }, throwable -> {
                        CustomErrorUtil.Companion.setError(this, TAG, throwable);
                    });
            disposables.add(disposable);
        } else {
            Disposable disposable = userProfilePresenter.followUser(userID)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(userProfileData -> {
                        following = userProfileData.isFollow();
                        if (userProfileData.isFollow()) {
                            followUser.setText(R.string.un_follow);
                            userProfileFollowTitle.setText(R.string.un_follow);
                            userProfileFolloweresCount.setText("" + userProfileData.getFollowersCount());
                        }
                    }, throwable -> {
                        CustomErrorUtil.Companion.setError(this, TAG, throwable);
                    });
            disposables.add(disposable);
        }

    }


    @Override
    protected void onDestroy() {
        disposables.dispose();
        super.onDestroy();
    }
}
