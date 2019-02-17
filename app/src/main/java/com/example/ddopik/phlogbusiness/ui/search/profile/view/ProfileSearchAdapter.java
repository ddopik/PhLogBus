package com.example.ddopik.phlogbusiness.ui.search.profile.view;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.utiltes.GlideApp;
import com.example.ddopik.phlogbusiness.base.commonmodel.Photographer;
import com.example.ddopik.phlogbusiness.base.widgets.CustomTextView;
import io.reactivex.annotations.NonNull;

import java.util.List;

/**
 * Created by abdalla_maged on 11/1/2018.
 */
public class ProfileSearchAdapter extends RecyclerView.Adapter<ProfileSearchAdapter.ProfileSearchViewHolder>  {


    private String TAG = ProfileSearchAdapter.class.getSimpleName();
    public Context context;
    private List<Photographer> profileList;
    private List<Photographer>profileFiltered;
    public ProfileAdapterListener profileAdapterListener;

    public ProfileSearchAdapter(Context context, List<Photographer> profileList) {
        this.context = context;
        this.profileList = profileList;
        //todo no need to filter result as list always filter by server for each Change
//        this.profileFiltered = profileList;
    }

    @NonNull
    @Override
    public ProfileSearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return new ProfileSearchViewHolder(layoutInflater.inflate(R.layout.view_holder_profile_search_2, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ProfileSearchViewHolder profileSearchViewHolder, int i) {


        try {
            if (profileList.get(i).imageProfile != null){
                GlideApp.with(context).load(profileList.get(i).imageProfile)
                        .error(R.drawable.default_error_img)
                        .placeholder(R.drawable.default_error_img)
                        .apply(RequestOptions.circleCropTransform()).into(profileSearchViewHolder.profileImg);

            }
            if (profileList.get(i).fullName != null)
                profileSearchViewHolder.profileFullName.setText(profileList.get(i).fullName);
            if (profileList.get(i).userName != null)
                profileSearchViewHolder.profileUserName.setText(String.format("@%1$s", profileList.get(i).userName));
            if (profileList.get(i).followersCount != null)
                profileSearchViewHolder.profileFollowersCount.setText(new StringBuilder().append(profileList.get(i).followersCount).append(" ").append(context.getResources().getString(R.string.followers)).toString());
            if (profileList.get(i).followingCount != null)
                profileSearchViewHolder.profileCountFollowingCount.setText(new StringBuilder().append(profileList.get(i).followingCount).append(" ").append(context.getResources().getString(R.string.following)).toString());
            if (profileList.get(i).photosCount != null)
                profileSearchViewHolder.photosCount.setText(new StringBuilder().append(profileList.get(i).photosCount).append(" ").append(context.getResources().getString(R.string.photos)).toString());

            profileSearchViewHolder.profileContainer.setOnClickListener(v -> {
                if (profileAdapterListener != null) {
                    profileAdapterListener.onProfileSelected(profileList.get(i));
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "onBindViewHolder () Error--->" + e.getMessage());

        }

    }


    @Override
    public int getItemCount() {
        return profileList.size();
    }

    //
    class ProfileSearchViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout profileContainer;
        private ImageView profileImg;
        private CustomTextView profileFullName, profileUserName;
        private TextView profileFollowersCount, profileCountFollowingCount, photosCount;

        ProfileSearchViewHolder(View view) {
            super(view);
            profileContainer = view.findViewById(R.id.profile_container);
            profileImg = view.findViewById(R.id.profile_img);
            profileFullName = view.findViewById(R.id.profile_full_name);
            profileUserName = view.findViewById(R.id.profile_user_name);
            profileCountFollowingCount = view.findViewById(R.id.profile_following_count);
            profileFollowersCount = view.findViewById(R.id.profile_followers_count);
            photosCount = view.findViewById(R.id.profile_photos_count);
        }
    }

//
//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String charString = charSequence.toString();
//                if (charString.isEmpty()) {
//                    profileFiltered = profileList;
//                } else {
//                    List<ProfileSearch> filteredList = new ArrayList<>();
//                    for (ProfileSearch row : profileList) {
//
//                        // name match condition. this might differ depending on your requirement
//                        // here we are looking for name or phone number match
//                        if (row.fullName.toLowerCase().contains(charString.toLowerCase()) || row.userName.toLowerCase().contains(charString.toLowerCase())) {
//                            filteredList.add(row);
//                        }
//                    }
//                    profileFiltered = filteredList;
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = profileFiltered;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                profileFiltered = (ArrayList<ProfileSearch>) filterResults.values;
//                notifyDataSetChanged();
//            }
//        };
//    }

    public interface ProfileAdapterListener {
        void onProfileSelected(Photographer profileSearch);
    }

}
