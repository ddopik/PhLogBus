package com.example.ddopik.phlogbusiness.ui.album.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseActivity;
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.base.widgets.CustomRecyclerView;
import com.example.ddopik.phlogbusiness.base.widgets.CustomTextView;
import com.example.ddopik.phlogbusiness.base.widgets.dialogs.addtoLightbox.view.AddToLightBoxDialogFragment;
import com.example.ddopik.phlogbusiness.ui.album.presenter.AllAlbumImgPresenter;
import com.example.ddopik.phlogbusiness.ui.album.presenter.AllAlbumImgPresenterImpl;
import com.example.ddopik.phlogbusiness.ui.album.view.adapter.AllAlbumImgAdapter;
import com.example.ddopik.phlogbusiness.ui.cart.view.CartActivity;
import com.example.ddopik.phlogbusiness.ui.commentimage.view.ImageCommentActivity;
import com.example.ddopik.phlogbusiness.ui.userprofile.view.UserProfileActivity;
import com.example.ddopik.phlogbusiness.utiltes.Constants;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import com.example.ddopik.phlogbusiness.utiltes.PrefUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.ddopik.phlogbusiness.utiltes.Constants.PhotosListType.SOCIAL_LIST;

/**
 * Created by abdalla_maged on 11/5/2018.
 */
public class AllAlbumImgActivity extends BaseActivity implements AllAlbumImgActivityView {


    private static final String TAG = AllAlbumImgActivity.class.getSimpleName();
    private static final int CART_REQUEST_CODE = 487;

    public static String ALBUM_ID = "album_id";
    public static String ALL_ALBUM_IMAGES = "album_list";
    public static String SELECTED_IMG_ID = "selected_img_id";
    public static String LIST_TYPE = "list_type";
    public static String LIST_NAME = "list_name";

    private ImageButton mainBackBtn;
    private CustomTextView topBarTitle;
    private AllAlbumImgAdapter allAlbumImgAdapter;
    private List<BaseImage> albumImgList = new ArrayList<>();
    private ProgressBar albumImgProgress;
    private AllAlbumImgPresenter allAlbumImgPresenter;
    private Constants.PhotosListType photosListType;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_album_img);
        initPresenter();
        initView();
    }


    @Override
    public void initView() {
        if (getIntent().getParcelableArrayListExtra(ALL_ALBUM_IMAGES) != null) {
            topBarTitle = findViewById(R.id.toolbar_title);
            mainBackBtn = findViewById(R.id.back_btn);
            mainBackBtn.setVisibility(View.INVISIBLE);
            albumImgProgress = findViewById(R.id.album_img_list_progress_bar);
            CustomRecyclerView allAlbumImgRv = findViewById(R.id.album_img_list_rv);

            photosListType = (Constants.PhotosListType) getIntent().getSerializableExtra(LIST_TYPE);


            if (getIntent().getStringExtra(LIST_NAME) != null) {
                topBarTitle.setText(getIntent().getStringExtra(LIST_NAME));
            }

            this.albumImgList = getIntent().<BaseImage>getParcelableArrayListExtra(ALL_ALBUM_IMAGES);
            int selectedPosition = getIntent().getIntExtra(SELECTED_IMG_ID, 0);


            allAlbumImgAdapter = new AllAlbumImgAdapter(albumImgList, photosListType);
            allAlbumImgRv.setAdapter(allAlbumImgAdapter);


            for (int i = 0; i < albumImgList.size(); i++) {
                if (albumImgList.get(i).id == selectedPosition) {
//                    Objects.requireNonNull(allAlbumImgRv.getLayoutManager()).smoothScrollToPosition(allAlbumImgRv, null, i);
                    allAlbumImgRv.scrollToPosition(i);
                    break;
                }

            }

            initListener();
        }


    }

    @Override
    public void initPresenter() {
        allAlbumImgPresenter = new AllAlbumImgPresenterImpl(this, this);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void initListener() {

        allAlbumImgAdapter.onAlbumImgClicked = new AllAlbumImgAdapter.OnAlbumImgClicked() {


            @SuppressLint("CheckResult")
            @Override
            public void onAlbumImgAddToCartClick(BaseImage albumImg, int position) {
                if (albumImg == null || albumImg.isCart == null)
                    return;
                if (!albumImg.isCart) {
                    albumImgProgress.setVisibility(View.VISIBLE);
                    allAlbumImgPresenter.addImageToCart(albumImg)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .doFinally(() -> albumImgProgress.setVisibility(View.GONE))
                            .subscribe(success -> {
                                if (success) {
                                    albumImg.isCart = true;
                                    allAlbumImgAdapter.notifyItemChanged(position);
                                }
                            }, throwable -> {
                                CustomErrorUtil.Companion.setError(AllAlbumImgActivity.this, TAG, throwable);
                            });
                } else {
                    Intent intent = new Intent(AllAlbumImgActivity.this, CartActivity.class);
                    startActivityForResult(intent, CART_REQUEST_CODE);
                }

            }


            @Override
            public void onAlbumImgClick(BaseImage albumImg) {


                if (photosListType == SOCIAL_LIST) {
                    allAlbumImgPresenter.getPhotoDetails(albumImg.id);

                } else {

                    Intent intent = new Intent(getBaseContext(), ImageCommentActivity.class);
                    intent.putExtra(ImageCommentActivity.IMAGE_DATA, albumImg);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                }


            }


            @Override
            public void onAlbumImgLikeClick(BaseImage albumImg) {
                allAlbumImgPresenter.likePhoto(albumImg);
            }

            @Override
            public void onAlbumImgCommentClick(BaseImage albumImg) {
                Intent intent = new Intent(getBaseContext(), ImageCommentActivity.class);
                intent.putExtra(ImageCommentActivity.IMAGE_DATA, albumImg);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }


            @Override
            public void onAlbumImgLightBoxClick(BaseImage albumImg) {


                AddToLightBoxDialogFragment addToLightBoxDialogFragment = AddToLightBoxDialogFragment.getInstance(albumImg);
                ///change "lightBox icon state" after image get Added
                addToLightBoxDialogFragment.onLighBoxImageComplete = state -> {

                    for (BaseImage mBaseImage : albumImgList) {

                        if (mBaseImage.id == albumImg.id) {
                            mBaseImage.isSaved = state;
                            allAlbumImgAdapter.notifyDataSetChanged();
                            break;
                        }
                    }

                };


                addToLightBoxDialogFragment.show(getSupportFragmentManager(), AllAlbumImgActivity.class.getSimpleName());

            }

            @Override
            public void onAlbumImgFollowClick(BaseImage albumImg) {
                allAlbumImgPresenter.followImagePhotoGrapher(albumImg);
            }

            @Override
            public void onAlbumImgHeaderClick(BaseImage albumImg) {
                if (PrefUtils.getBrandId(getBaseContext()).equals(String.valueOf(albumImg.photographer.id))) {
                    Intent intent = new Intent(getBaseContext(), UserProfileActivity.class);
                    intent.putExtra(UserProfileActivity.USER_ID, String.valueOf(albumImg.photographer.id));
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    getBaseContext().startActivity(intent);
                } else if (!photosListType.equals(Constants.PhotosListType.USER_PROFILE_PHOTOS_LIST)) { //case user already came from photographer Profile list do not navigate
                    Intent intent = new Intent(getBaseContext(), UserProfileActivity.class);
                    intent.putExtra(UserProfileActivity.USER_ID, String.valueOf(albumImg.photographer.id));
                    intent.putExtra(UserProfileActivity.USER_TYPE, Constants.UserType.USER_TYPE_BUSINESS);
                    getBaseContext().startActivity(intent);
                }
            }
        };


    }

    @Override
    public void viewAlbumImageList(List<BaseImage> albumImgList) {

        this.albumImgList.clear();
        this.albumImgList.addAll(albumImgList);
        allAlbumImgAdapter.notifyDataSetChanged();

    }

    @Override
    public void onImageAddedToCard(BaseImage baseImage, boolean state) {
        for (BaseImage mBaseImage : albumImgList) {
            if (mBaseImage.id == baseImage.id) {
                mBaseImage.isCart = state;
                break;
            }
        }

        allAlbumImgAdapter.notifyDataSetChanged();

    }


    @Override
    public void onImagePhotoGrapherFollowed(BaseImage baseImage, boolean state) {
        for (BaseImage mBaseImage : albumImgList) {
            if (mBaseImage.id == baseImage.id) {
                mBaseImage.photographer.isFollow = state;
                break;
            }
        }
        allAlbumImgAdapter.notifyDataSetChanged();
    }


    @Override
    public void onImagePhotoGrapherLiked(int photoId, boolean state) {
        for (int i = 0; i < albumImgList.size(); i++) {
            if (albumImgList.get(i).id == photoId) {
                albumImgList.get(i).isLiked = state;

                if (state) {
                    albumImgList.get(i).likesCount++;
                } else {
                    albumImgList.get(i).likesCount--;
                }

                break;
            }
        }

        allAlbumImgAdapter.notifyDataSetChanged();
    }

    @Override
    public void navigateToImageDetails(BaseImage baseImage) {
        Intent intent = new Intent(getBaseContext(), ImageCommentActivity.class);
        intent.putExtra(ImageCommentActivity.IMAGE_DATA, baseImage);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    public void viewAlbumImageListProgress(boolean state) {

        if (state) {
            albumImgProgress.setVisibility(View.VISIBLE);
        } else {
            albumImgProgress.setVisibility(View.GONE);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CART_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    if (data.getIntegerArrayListExtra(CartActivity.REMOVED_LIST) != null) {
                        if (allAlbumImgAdapter == null)
                            return;
                        List<Integer> removedItems = data.getIntegerArrayListExtra(CartActivity.REMOVED_LIST);
                        if (!removedItems.isEmpty()) {
                            if (removedItems.get(0).equals(-1)) {
                                for (BaseImage i : albumImgList)
                                    i.isCart = false;
                                allAlbumImgAdapter.notifyDataSetChanged();
                            } else {
                                for (int i = 0; i < albumImgList.size(); i++) {
                                    if (removedItems.contains(albumImgList.get(i).id)) {
                                        albumImgList.get(i).isCart = false;
                                        allAlbumImgAdapter.notifyItemChanged(i);
                                    }
                                }
                            }
                        }
                    }
                    Intent data2 = new Intent();
                    data2.putParcelableArrayListExtra(ALL_ALBUM_IMAGES, (ArrayList<? extends Parcelable>) albumImgList);
                    setResult(RESULT_OK, data);
                }
                break;
        }
    }
}
