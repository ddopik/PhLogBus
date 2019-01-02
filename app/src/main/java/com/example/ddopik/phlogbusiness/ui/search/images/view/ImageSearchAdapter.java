package com.example.ddopik.phlogbusiness.ui.search.images.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.commonmodel.Brand;
import com.example.ddopik.phlogbusiness.base.widgets.CustomTextView;
import com.example.ddopik.phlogbusiness.utiltes.GlideApp;
import io.reactivex.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by abdalla_maged on 10/31/2018.
 */
public class ImageSearchAdapter extends RecyclerView.Adapter<ImageSearchAdapter.ImageSearchViewHolder> implements Filterable {


    private String TAG = ImageSearchAdapter.class.getSimpleName();
    public Context context;
    private List<Brand> brandList;
    private List<Brand> brandFiltered;
    public BrandAdapterListener brandAdapterListener;

    public ImageSearchAdapter(Context context, List<Brand> brandList) {
        this.context = context;
        this.brandList = brandList;
        this.brandFiltered = brandList;
    }

    @NonNull
    @Override
    public ImageSearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return new ImageSearchViewHolder(layoutInflater.inflate(R.layout.view_holder_brand, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ImageSearchViewHolder imageSearchViewHolder, int i) {


        try {

            if(brandList.get(i).isFollow){
                imageSearchViewHolder.brandFollowBtn.setText(context.getResources().getString(R.string.following));
            }else {
                imageSearchViewHolder.brandFollowBtn.setText(context.getResources().getString(R.string.follow));
            }
            GlideApp.with(context)
                    .load(brandList.get(i).thumbnail)
                    .placeholder(R.drawable.default_place_holder)
                    .error(R.drawable.default_place_holder)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(imageSearchViewHolder.brandIconImg);


            GlideApp .with(context)
                    .load(brandList.get(i).imageCover)
                    .placeholder(R.drawable.default_place_holder)
                    .error(R.drawable.default_place_holder)
                    .centerCrop()
                    .into(imageSearchViewHolder.brandImg);

            imageSearchViewHolder.brandName.setText(brandList.get(i).fullName);
            imageSearchViewHolder.brandFollowers.setText(new StringBuilder().append(brandList.get(i).followersCount).append(" ").append(context.getResources().getString(R.string.following)).toString());
            imageSearchViewHolder.searchBrandContainer.setOnClickListener(v -> {
                if (brandAdapterListener != null) {
                    brandAdapterListener.onBrandSelected(brandFiltered.get(i));
                }
            });

            imageSearchViewHolder.brandFollowBtn.setOnClickListener(v -> {
                if (brandList.get(i).isFollow) {
//                    BaseNetworkApi.unFollowBrand(String.valueOf(brandList.get(i).id))
//                            .subscribeOn(Schedulers.io())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe(followBrandResponse -> {
//                                brandList.get(i).isFollow = false;
//                                imageSearchViewHolder.brandFollowBtn.setText(context.getResources().getString(R.string.follow));
//                            }, throwable -> {
//                                CustomErrorUtil.Companion.setError(context, TAG, throwable);
//                            });
                } else {
//                    BaseNetworkApi.followBrand(String.valueOf(brandList.get(i).id))
//                            .subscribeOn(Schedulers.io())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe(followBrandResponse -> {
//                                brandList.get(i).isFollow = true;
//                                imageSearchViewHolder.brandFollowBtn.setText(context.getResources().getString(R.string.following));
//                            }, throwable -> {
//                                CustomErrorUtil.Companion.setError(context, TAG, throwable);
//                            });
                }
            });

        } catch (Exception e) {
            Log.e(TAG, "onBindViewHolder () Error--->" + e.getMessage());

        }

    }


    @Override
    public int getItemCount() {
        return brandFiltered.size();
    }

    //
    public class ImageSearchViewHolder extends RecyclerView.ViewHolder {

        LinearLayout searchBrandContainer;
        ImageView brandIconImg,brandImg;
        CustomTextView brandName,brandFollowers;
        Button brandFollowBtn;

        public ImageSearchViewHolder(View view) {
            super(view);
            searchBrandContainer=view.findViewById(R.id.search_brand_container);
            brandIconImg=view.findViewById(R.id.brand_search_icon_img);
            brandImg=view.findViewById(R.id.brand_search_img);
            brandName=view.findViewById(R.id.brand_search_name);
            brandFollowers=view.findViewById(R.id.brand_search_following);
            brandFollowBtn=view.findViewById(R.id.follow_brand);
        }
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    brandFiltered = brandList;
                } else {
                    List<Brand> filteredList = new ArrayList<>();
                    for (Brand row : brandList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.fullName.toLowerCase().contains(charString.toLowerCase()) ) {
                            filteredList.add(row);
                        }
                    }
                    brandFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = brandFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                brandFiltered = (ArrayList<Brand>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public interface BrandAdapterListener {
        void onBrandSelected(Brand brandSearch);
    }
}