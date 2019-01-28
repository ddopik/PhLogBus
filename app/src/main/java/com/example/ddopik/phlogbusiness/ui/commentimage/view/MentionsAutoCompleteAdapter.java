package com.example.ddopik.phlogbusiness.ui.commentimage.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.bumptech.glide.request.RequestOptions;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.commonmodel.MentionedUser;
import com.example.ddopik.phlogbusiness.base.commonmodel.Mentions;
import com.example.ddopik.phlogbusiness.utiltes.GlideApp;

import java.util.ArrayList;
import java.util.List;

public class MentionsAutoCompleteAdapter extends ArrayAdapter {


    private Context mContext;
    private int itemLayout;
    private List<MentionedUser> mentionedUsersAll;

    public OnUserClicked onUserClicked;

    public MentionsAutoCompleteAdapter(Context context, int resource, List<MentionedUser> mentionedUsers) {
        super(context, resource, mentionedUsers);
        mentionedUsersAll = mentionedUsers;
        mContext = context;
        itemLayout = resource;
    }

    @Override
    public int getCount() {
        return mentionedUsersAll.size();
    }

    @Override
    public MentionedUser getItem(int position) {

        return mentionedUsersAll.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {


        MentionedUserViewHolder mentionedUserViewHolder=new MentionedUserViewHolder();


        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.view_holder_mentioned_user, null);
            mentionedUserViewHolder.mentionedUserImg=convertView.findViewById(R.id.mention_profile_img);
            mentionedUserViewHolder.mentionedUserName=convertView.findViewById(R.id.mentioned_profile_user_name);
            convertView.setTag(mentionedUserViewHolder);
        }else {
            mentionedUserViewHolder=(MentionedUserViewHolder) convertView.getTag();
        }




        GlideApp.with(mContext)
                .load(getItem(position).mentionedImage)
                .error(R.drawable.default_error_img)
                .placeholder(R.drawable.default_place_holder)
                .override(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
                .apply(RequestOptions.circleCropTransform())
                .into(mentionedUserViewHolder.mentionedUserImg);

        mentionedUserViewHolder.mentionedUserName.setText(getItem(position).mentionedUserName);

        if (onUserClicked !=null){
            mentionedUserViewHolder.mentionedUserImg.setOnClickListener(v->{
                onUserClicked.onUserSelected(getItem(position));
            });
        }

        return convertView;
    }


    private Filter mFilter =new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null) {
                ArrayList<MentionedUser> suggestions = new ArrayList<MentionedUser>();
                for (MentionedUser mentionsUser : mentionedUsersAll) {
                    // Note: change the "contains" to "startsWith" if you only want starting matches
                    if (mentionsUser.mentionedUserName.startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(mentionsUser);
//                    }
                    }

                    results.values = suggestions;
                    results.count = suggestions.size();
                }
            }

                return results;

        }



        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values != null) {
                addAll((ArrayList<MentionedUser>) results.values);
            } else {

             addAll(mentionedUsersAll);
            }

            notifyDataSetChanged();


        }



    };
    @NonNull
    @Override
    public Filter getFilter() {
        return mFilter;
    }


    class MentionedUserViewHolder{
        ImageView mentionedUserImg ;
        TextView mentionedUserName ;
    }
    public interface OnUserClicked {
        void onUserSelected(MentionedUser mentionedUser);

    }
}
