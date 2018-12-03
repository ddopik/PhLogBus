package com.example.ddopik.phlogbusiness.ui.uploadimage.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.ui.uploadimage.model.Tag;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged on 10/28/2018.
 */
public class AutoCompleteTagMenuAdapter extends ArrayAdapter<Tag> {


    private int resourceLayout;
    private Context mContext;
    private List<Tag> tagList;
    private List<Tag> allTagList;
    public OnMenuItemClicked onMenuItemClicked;

    public AutoCompleteTagMenuAdapter(Context context, int resource, List<Tag> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
        this.tagList = new ArrayList<>(items);
        this.allTagList = new ArrayList<>(items);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {


            if (convertView == null) {
                LayoutInflater layoutInflater = LayoutInflater.from(mContext);
                convertView = layoutInflater.inflate(resourceLayout, parent, false);
            }
            Tag tag = getItem(position);
            TextView name =   convertView.findViewById(R.id.tag_auto_complete_name);
             if (onMenuItemClicked !=null){
                name.setOnClickListener((view)-> onMenuItemClicked.onItemSelected(tag));
             }
            name.setText(tag.tagName);

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
                return ((Tag) resultValue).tagName;
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<Tag> departmentsSuggestion = new ArrayList<>();
                if (constraint != null) {
                    for (Tag tag : allTagList) {
                        if (tag.tagName.toLowerCase().startsWith(constraint.toString().toLowerCase())  || tag.tagName.toLowerCase().contains(constraint.toString().toLowerCase())  ) {
                            departmentsSuggestion.add(tag);
                        }
                    }
                    filterResults.values = departmentsSuggestion;
                    filterResults.count = departmentsSuggestion.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                tagList.clear();
                if (results != null && results.count > 0) {

                    for (Object object : (List<?>) results.values) {
                        if (object instanceof Tag) {
                            tagList.add((Tag) object);
                        }
                    }
                    notifyDataSetChanged();
                } else if (constraint == null) {
                    // no filter, add entire original list back in
                    tagList.addAll(allTagList);
                    notifyDataSetInvalidated();
                }
            }
        };
    }

    public interface OnMenuItemClicked{
        void onItemSelected(Tag tag);
     }
}


