package com.example.ddopik.phlogbusiness.ui.userprofile.view;
/**
 * Created by Abdalla_maged on 9/30/2018.
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.*;

import com.bumptech.glide.request.RequestOptions;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.ui.album.view.AllAlbumImgActivity;
import com.example.ddopik.phlogbusiness.ui.commentimage.view.ImageCommentActivity;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import com.example.ddopik.phlogbusiness.utiltes.GlideApp;
import com.example.ddopik.phlogbusiness.base.BaseActivity;
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.base.widgets.CustomRecyclerView;
import com.example.ddopik.phlogbusiness.base.widgets.PagingController;
import com.example.ddopik.phlogbusiness.ui.userprofile.presenter.UserProfilePresenter;
import com.example.ddopik.phlogbusiness.ui.userprofile.presenter.UserProfilePresenterImpl;
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

    public static String USER_ID="user_id";
    public static String USER_TYPE="user_type";

    private String userID;
    private TextView userProfileLevel, userProfileUserName, userProfileFullName, userProfilePhotosCount, userProfileFolloweresCount, userProfileFollowingCount;
    private RatingBar userProfileRating;
    private ImageView userProfileImg, userCoverImg;
    private CustomRecyclerView userProfilePhotosRv;
    private UserProfilePhotosAdapter userProfilePhotosAdapter;
    private UserProfilePresenter userProfilePresenter;
    private List<BaseImage> userPhotoList = new ArrayList<BaseImage>();
    private ProgressBar userProfilePhotosProgressBar;
    private Button followUser;
    private PagingController pagingController;

    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        initPresenter();
        initView();
        initListener();
    }


    @Override
    public void initPresenter() {
        userProfilePresenter = new UserProfilePresenterImpl(getBaseContext(), this);
    }


    @Override
    public void initView() {


        if(getIntent().getStringExtra(USER_ID) != null)
            this.userID=getIntent().getStringExtra(USER_ID);
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
            followUser=findViewById(R.id.follow_user);
            userProfilePhotosAdapter = new UserProfilePhotosAdapter(this, userPhotoList);
            userProfilePhotosRv.setAdapter(userProfilePhotosAdapter);
            userProfilePresenter.getUserProfileData(userID);
            userProfilePresenter.getUserPhotos(userID,0);

            userCoverImg = findViewById(R.id.user_cover_img);

    }


    private void initListener(){
        pagingController=new PagingController(userProfilePhotosRv) {
            @Override
            public void getPagingControllerCallBack(int page) {
                userProfilePresenter.getUserPhotos(userID,page+1);
            }
        };
        followUser.setOnClickListener(v -> {
            if(userID ==null)
                return;
            if (following) {
                Disposable disposable = userProfilePresenter.unfollow(userID)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(userProfileData -> {
                            following = userProfileData.isFollow();
                            if (!userProfileData.isFollow()) {
                                followUser.setText(R.string.follow);
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
                                userProfileFolloweresCount.setText("" + userProfileData.getFollowersCount());
                            }
                        }, throwable -> {
                            CustomErrorUtil.Companion.setError(this, TAG, throwable);
                        });
                disposables.add(disposable);
            }
        });


        userProfilePhotosAdapter.photoAction = image -> {
            Intent intent = new Intent(this, AllAlbumImgActivity.class);
            intent.putExtra(SELECTED_IMG_ID, image.id);
            intent.putExtra(LIST_NAME, image.photographer.userName);
            intent.putExtra(LIST_TYPE, USER_PROFILE_PHOTOS_LIST);
            intent.putParcelableArrayListExtra(ALL_ALBUM_IMAGES, (ArrayList<? extends Parcelable>) userPhotoList);
            startActivity(intent);
        };
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
            followUser.setText(R.string.un_follow);
        } else {
            followUser.setText(R.string.follow);
        }
    }

    @Override
    protected void onDestroy() {
        disposables.dispose();
        super.onDestroy();
    }
}
