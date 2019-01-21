package com.example.ddopik.phlogbusiness.ui.commentimage.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.commonmodel.Business;
import com.example.ddopik.phlogbusiness.base.commonmodel.Photographer;
import com.example.ddopik.phlogbusiness.ui.commentimage.model.SocialUser;

import java.util.ArrayList;
import java.util.List;

public class MentionsAutoCompleteAdapter extends ArrayAdapter {

    private List<SocialUser> socialUsersFiltered;
    private Context mContext;
    private int itemLayout;

    private ListFilter listFilter = new ListFilter();
    private List<SocialUser> socialUsers;


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
            view = LayoutInflater.from(parent.getContext())
                    .inflate(itemLayout, parent, false);
        }

        TextView strName = (TextView) view.findViewById(R.id.textView);
//        strName.setText(getItem(position));
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

                ArrayList<String> matchValues = new ArrayList<String>();

                for (SocialUser socialUser : socialUsers) {
//                    if (socialUser.toLowerCase().startsWith(searchStrLowerCase)) {
//                        matchValues.add(dataItem);
//                    }
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
        void onPhotoGrapgerSelected(Photographer photographer);

        void onBusinessSelected(Business business);
    }
}
