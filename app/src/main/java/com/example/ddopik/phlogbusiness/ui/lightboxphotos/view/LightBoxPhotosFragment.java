package com.example.ddopik.phlogbusiness.ui.lightboxphotos.view;


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
import com.example.ddopik.phlogbusiness.base.widgets.PagingController;
import com.example.ddopik.phlogbusiness.ui.album.view.adapter.AllAlbumImgAdapter;
import com.example.ddopik.phlogbusiness.ui.lightboxphotos.presenter.LightboxPhotosPresenter;
import com.example.ddopik.phlogbusiness.ui.lightboxphotos.presenter.LightboxPhotosPresenterImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LightBoxPhotosFragment extends BaseFragment implements LightboxPhotosView {

    private LightboxPhotosPresenter presenter;

    private List<BaseImage> images;
    private int lightBoxId;

    private View mainView;
    private RecyclerView recyclerView;
    private PagingController pagingController;
    private LightboxPhotosAdapter adapter;

    public LightBoxPhotosFragment() {
        // Required empty public constructor
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
    }

    @Override
    protected void initViews() {
        recyclerView = mainView.findViewById(R.id.recycler_view);
        pagingController = new PagingController(recyclerView) {
            @Override
            public void getPagingControllerCallBack(int page) {
                presenter.getLightboxPhotos(getContext(), lightBoxId, page);
            }
        };
        adapter = new LightboxPhotosAdapter(images == null? images = new ArrayList<>() : images);
        recyclerView.setAdapter(adapter);
    }

    private void initListeners() {
        adapter.onAlbumImgClicked = onAlbumImgClicked;
    }

    private LightboxPhotosAdapter.OnAlbumImgClicked onAlbumImgClicked = new LightboxPhotosAdapter.OnAlbumImgClicked() {
        @Override
        public void onAlbumImgClick(BaseImage albumImg) {

        }

        @Override
        public void onAlbumImgLikeClick(BaseImage albumImg) {

        }

        @Override
        public void onAlbumImgCommentClick(BaseImage albumImg) {

        }

        @Override
        public void onAlbumImgAddLightBoxClick(BaseImage albumImg) {

        }

        @Override
        public void onAlbumImgToCartClick(BaseImage albumImg) {

        }

        @Override
        public void onAlbumImgLightBoxRemoveClick(BaseImage albumImg) {

        }
    };

    @Override
    public void addPhotos(List<BaseImage> data) {
        images.addAll(data);
        adapter.notifyDataSetChanged();
    }
}
