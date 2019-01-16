package com.example.ddopik.phlogbusiness.ui.campaigns.addcampaign.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.commonmodel.Industry;
import com.example.ddopik.phlogbusiness.base.commonmodel.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged on 10/28/2018.
 */
public class AutoCompleteTagsMenuAdapter extends ArrayAdapter<Tag> {


    private int resourceLayout;
    private Context mContext;
    public List<Tag> tagList;
    private List<Tag> allTagsList;
    public OnMenuItemClicked onMenuItemClicked;

    public AutoCompleteTagsMenuAdapter(Context context, int resource, List<Tag> tags) {
        super(context, resource, tags);
        this.resourceLayout = resource;
        this.mContext = context;
        this.tagList = new ArrayList<>(tags);
        this.allTagsList = new ArrayList<>(tags);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(resourceLayout, parent, false);
        }
        Tag tag = getItem(position);
        TextView name = convertView.findViewById(R.id.tag_auto_complete_name);
        if (onMenuItemClicked != null) {
            name.setOnClickListener((view) -> onMenuItemClicked.onItemSelected(tag));
        }
        name.setText(tag.name);

        return convertView;
    }

    public Tag getItem(int position) {
        return tagList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public int getCount() {
        return tagList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {


            @Override
            public String convertResultToString(Object resultValue) {
                return ((Industry) resultValue).nameEn;
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<Tag> tagsSuggestion = new ArrayList<>();
                if (constraint != null) {
                    for (Tag tag : allTagsList) {
                        if (tag.name.toLowerCase().startsWith(constraint.toString().toLowerCase()) || tag.name.toLowerCase().contains(constraint.toString().toLowerCase())) {
                            tagsSuggestion.add(tag);
                        }
                    }
                    filterResults.values = tagsSuggestion;
                    filterResults.count = tagsSuggestion.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                tagList.clear();
                if (results != null && results.count > 0) {

                    for (Object object : (List<?>) results.values) {
                        if (object instanceof Industry) {
                            tagList.add((Tag) object);
                        }
                    }
                    notifyDataSetChanged();
                } else if (constraint == null) {
                    // no filter, add entire original list back in
                    tagList.addAll(allTagsList);
                    notifyDataSetInvalidated();
                }
            }
        };
    }

    public interface OnMenuItemClicked {
        void onItemSelected(Tag tag);
    }
}


