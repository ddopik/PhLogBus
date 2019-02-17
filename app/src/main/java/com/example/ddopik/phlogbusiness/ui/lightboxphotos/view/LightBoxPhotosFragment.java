package com.example.ddopik.phlogbusiness.ui.lightboxphotos.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.BaseFragment;
import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.base.commonmodel.LightBox;
import com.example.ddopik.phlogbusiness.base.widgets.PagingController;
import com.example.ddopik.phlogbusiness.ui.MainActivity;
import com.example.ddopik.phlogbusiness.ui.commentimage.view.ImageCommentActivity;
import com.example.ddopik.phlogbusiness.ui.lightboxphotos.presenter.LightboxPhotosPresenter;
import com.example.ddopik.phlogbusiness.ui.lightboxphotos.presenter.LightboxPhotosPresenterImpl;
import com.example.ddopik.phlogbusiness.ui.userprofile.view.UserProfileActivity;
import com.example.ddopik.phlogbusiness.utiltes.Constants;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LightBoxPhotosFragment extends BaseFragment implements LightboxPhotosView {

    public static final String TAG = LightBoxPhotosFragment.class.getSimpleName();

    private LightboxPhotosPresenter presenter;

    private List<BaseImage> images;
    private int lightBoxId;

    private View mainView;
    private RecyclerView recyclerView;
    private PagingController pagingController;
    private LightboxPhotosAdapter adapter;

    private CompositeDisposable disposables = new CompositeDisposable();

    public LightBoxPhotosFragment() {
        // Required empty public constructor
    }

    public static Fragment getInstance(LightBox lightBox) {
        LightBoxPhotosFragment fragment = new LightBoxPhotosFragment();
        fragment.lightBoxId = lightBox.id;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return mainView = inflater.inflate(R.layout.fragment_light_box_photos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        initListeners();
        initPresenter();
    }

    @Override
    protected void initPresenter() {
        presenter = new LightboxPhotosPresenterImpl();
        presenter.setView(this);
        presenter.getLightboxPhotos(getContext(), lightBoxId, 0);
    }

    @Override
    protected void initViews() {
        recyclerView = mainView.findViewById(R.id.recycler_view);
//        pagingController = new PagingController(recyclerView) {
//            @Override
//            public void getPagingControllerCallBack(int page) {
//                presenter.getLightboxPhotos(getContext(), lightBoxId, page);
//            }
//        };
        adapter = new LightboxPhotosAdapter(images == null? images = new ArrayList<>() : images);
        recyclerView.setAdapter(adapter);
    }

    private void initListeners() {
        adapter.photoActionListener = photoActionListener;
    }

    private LightboxPhotosAdapter.PhotoActionListener photoActionListener = (type, image) -> {
        switch (type) {
            case FOLLOW:
                if (!image.photographer.isFollow) {
                    Disposable d1 = presenter.follow(getContext(), image)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(success -> {
                                if (success) {
                                    image.photographer.isFollow = true;
                                    adapter.notifyItemChanged(images.indexOf(image));
                                }
                            }, throwable -> {
                                CustomErrorUtil.Companion.setError(getContext(), TAG, throwable);
                            });
                    disposables.add(d1);
                } else {
                    Disposable d2 = presenter.unfollow(getContext(), image)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(success -> {
                                if (success) {
                                    image.photographer.isFollow = false;
                                    adapter.notifyItemChanged(images.indexOf(image));
                                }
                            }, throwable -> {
                                CustomErrorUtil.Companion.setError(getContext(), TAG, throwable);
                            });
                    disposables.add(d2);
                }
                break;
            case VIEW:
                Intent intent = new Intent(getContext(), ImageCommentActivity.class);
                intent.putExtra(ImageCommentActivity.IMAGE_DATA, image);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case LIKE:
                if (!image.isLiked) {
                    Disposable d3 = presenter.like(image)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(success -> {
                                if (success) {
                                    image.isLiked = true;
                                    adapter.notifyItemChanged(images.indexOf(image));
                                }
                            }, throwable -> {
                                CustomErrorUtil.Companion.setError(getContext(), TAG, throwable);
                            });
                    disposables.add(d3);
                } else {
                    Disposable d4 = presenter.unlike(image)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(success -> {
                                if (success) {
                                    image.isLiked = false;
                                    adapter.notifyItemChanged(images.indexOf(image));
                                }
                            }, throwable -> {
                                CustomErrorUtil.Companion.setError(getContext(), TAG, throwable);
                            });
                    disposables.add(d4);
                }
                break;
            case COMMENT:
                Intent i = new Intent(getContext(), ImageCommentActivity.class);
                i.putExtra(ImageCommentActivity.IMAGE_DATA, image);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
                break;
            case DELETE:
                Disposable d5 = presenter.delete(lightBoxId, image)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(success -> {
                            if (success) {
                                int pos = images.indexOf(image);
                                images.remove(image);
                                adapter.notifyItemRemoved(pos);
                            }
                        }, throwable -> {
                            CustomErrorUtil.Companion.setError(getContext(), TAG, throwable);
                        });
                disposables.add(d5);
                break;
            case ADD_TO_CART:
                Disposable d6 = presenter.addToCart(image)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(success -> {
                            if (success) {
                                int pos = images.indexOf(image);
                                image.isCart = true;
                                adapter.notifyItemChanged(images.indexOf(image));
                            }
                        }, throwable -> {
                            CustomErrorUtil.Companion.setError(getContext(), TAG, throwable);
                        });
                disposables.add(d6);
                break;
            case PHOTOGRAPHER:
                Intent i2 = new Intent(getContext(), UserProfileActivity.class);
                i2.putExtra(UserProfileActivity.USER_ID, image.photographer.id.toString());
                i2.putExtra(UserProfileActivity.USER_TYPE, Constants.UserType.USER_TYPE_PHOTOGRAPHER);
                startActivity(i2);
                break;
        }
    };

    @Override
    public void addPhotos(List<BaseImage> data) {
        images.addAll(data);
        adapter.notifyDataSetChanged();
    }
}
