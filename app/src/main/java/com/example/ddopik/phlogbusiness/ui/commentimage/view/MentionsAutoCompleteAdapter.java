package com.example.ddopik.phlogbusiness.ui.commentimage.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.bumptech.glide.request.RequestOptions;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.commonmodel.SocialUser;
import com.example.ddopik.phlogbusiness.utiltes.GlideApp;

import java.util.ArrayList;
import java.util.List;

public class MentionsAutoCompleteAdapter extends ArrayAdapter {

    private List<SocialUser> socialUsersFiltered;
    private Context mContext;
    private int itemLayout;

    private ListFilter listFilter = new ListFilter();
    private List<SocialUser> socialUsers;

    public OnUserClicked onUserClicked;

    public MentionsAutoCompleteAdapter(Context context, int resource, List<SocialUser> socialUsers) {
        super(context, resource, socialUsers);
        socialUsersFiltered = socialUsers;
        mContext = context;
        itemLayout = resource;
    }

    @Override
    public int getCount() {
        return socialUsersFiltered.size();
    }

    @Override
    public SocialUser getItem(int position) {

        return socialUsersFiltered.get(position);
    }

    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {

        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        }


        ImageView mentionedUserImg = view.findViewById(R.id.mention_profile_img);
        TextView mentionedUserName = view.findViewById(R.id.mentioned_profile_user_name);

        GlideApp.with(mContext)
                .load(getItem(position).mentionedImage)
                .error(R.drawable.default_error_img)
                .placeholder(R.drawable.default_place_holder)
                .override(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
                .apply(RequestOptions.circleCropTransform())
                .into(mentionedUserImg);

        mentionedUserName.setText(getItem(position).mentionedUserName);

        if (onUserClicked !=null){
            view.setOnClickListener(v->{
                onUserClicked.onUserSelected(getItem(position));
            });
        }

        return view;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return listFilter;
    }

    public class ListFilter extends Filter {
        private Object lock = new Object();

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if (socialUsers == null) {
                synchronized (lock) {
                    socialUsers = new ArrayList<SocialUser>(socialUsersFiltered);
                }
            }

            if (prefix == null || prefix.length() == 0) {
                synchronized (lock) {
                    results.values = socialUsers;
                    results.count = socialUsers.size();
                }
            } else {
                final String searchStrLowerCase = prefix.toString().toLowerCase();

                ArrayList<SocialUser> matchValues = new ArrayList<SocialUser>();
                for (SocialUser socialUser : socialUsers) {
                    if (socialUser.mentionedUserName.startsWith(searchStrLowerCase)) {
                        matchValues.add(socialUser);
                    }
                }

                results.values = matchValues;
                results.count = matchValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values != null) {
                socialUsersFiltered = (ArrayList<SocialUser>) results.values;
            } else {
                socialUsersFiltered = null;
            }
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }

    }


    public interface OnUserClicked {
        void onUserSelected(SocialUser socialUser);

    }
}
