package com.example.ddopik.phlogbusiness.ui.uploadimage.view.fillters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.Utiltes.BitmapUtils;
import com.example.ddopik.phlogbusiness.base.BaseFragment;
import com.example.ddopik.phlogbusiness.base.widgets.SpacesItemDecoration;
import com.zomato.photofilters.FilterPack;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.utils.ThumbnailItem;
import com.zomato.photofilters.utils.ThumbnailsManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged on 10/18/2018.
 */
public class FiltersListFragment extends BaseFragment implements ThumbnailsAdapter.ThumbnailsAdapterListener {

    private static String TAG = FiltersListFragment.class.getSimpleName();
    RecyclerView recyclerView;
    ThumbnailsAdapter mAdapter;
    List<ThumbnailItem> thumbnailItemList = new ArrayList<>();
    FiltersListFragmentListener listener;
    private Activity activityContext;

    public static FiltersListFragment getInstance(Activity activity) {
        FiltersListFragment filtersListFragment = new FiltersListFragment();
        filtersListFragment.activityContext = activity;
        return filtersListFragment;
    }

    public void setListener(FiltersListFragmentListener listener) {
        this.listener = listener;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filters_list, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        mAdapter = new ThumbnailsAdapter(getActivity(), thumbnailItemList, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
        recyclerView.addItemDecoration(new SpacesItemDecoration(space));
        recyclerView.setAdapter(mAdapter);

//        prepareThumbnail(null,"");

        return view;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initViews() {

    }

    /**
     * Renders thumbnails in horizontal list
     * loads default image from Assets if passed param is null
     *
     * @param bitmap
     */
    public void prepareThumbnail(final Bitmap bitmap, String pickedImageName) {
        Runnable r = () -> {
            Bitmap thumbImage;

            if (bitmap == null) {
                thumbImage = BitmapUtils.getBitmapFromAssets(activityContext, pickedImageName, 100, 100);
            } else {
                thumbImage = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
            }

            if (thumbImage == null) {
                Log.e(TAG, "prepareThumbnail() ---> thumbImage is null");
                return;
            }


            ThumbnailsManager.clearThumbs();
            thumbnailItemList.clear();

            // add normal bitmap first
            ThumbnailItem thumbnailItem = new ThumbnailItem();
            thumbnailItem.image = thumbImage;
            thumbnailItem.filterName = activityContext.getString(R.string.filter_normal);
            ThumbnailsManager.addThumb(thumbnailItem);

            List<Filter> filters = FilterPack.getFilterPack(activityContext);

            for (Filter filter : filters) {
                ThumbnailItem tI = new ThumbnailItem();
                tI.image = thumbImage;
                tI.filter = filter;
                tI.filterName = filter.getName();
                ThumbnailsManager.addThumb(tI);
            }

            thumbnailItemList.addAll(ThumbnailsManager.processThumbs(activityContext));

            activityContext.runOnUiThread(() -> mAdapter.notifyDataSetChanged());
        };

        new Thread(r).start();
    }

    @Override
    public void onFilterSelected(Filter filter) {
        if (listener != null)
            listener.onFilterSelected(filter);
    }

    public interface FiltersListFragmentListener {
        void onFilterSelected(Filter filter);
    }
}