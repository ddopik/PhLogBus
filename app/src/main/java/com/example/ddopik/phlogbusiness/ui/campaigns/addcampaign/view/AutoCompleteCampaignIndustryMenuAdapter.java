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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdalla_maged on 10/28/2018.
 */
public class AutoCompleteCampaignIndustryMenuAdapter extends ArrayAdapter<Industry> {


    private int resourceLayout;
    private Context mContext;
    public List<Industry> industryList;
    private List<Industry> allIndustryList;
    public OnMenuItemClicked onMenuItemClicked;

    public AutoCompleteCampaignIndustryMenuAdapter(Context context, int resource, List<Industry> industries) {
        super(context, resource, industries);
        this.resourceLayout = resource;
        this.mContext = context;
        this.industryList = new ArrayList<>(industries);
        this.allIndustryList = new ArrayList<>(industries);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(resourceLayout, parent, false);
        }
        Industry industry = getItem(position);
        TextView name = convertView.findViewById(R.id.tag_auto_complete_name);
        if (onMenuItemClicked != null) {
            name.setOnClickListener((view) -> onMenuItemClicked.onItemSelected(industry));
        }
        name.setText(industry.nameEn);

        return convertView;
    }

    public Industry getItem(int position) {
        return industryList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public int getCount() {
        return industryList.size();
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
                List<Industry> departmentsSuggestion = new ArrayList<>();
                if (constraint != null) {
                    for (Industry industry : allIndustryList) {
                        if (industry.nameEn.toLowerCase().startsWith(constraint.toString().toLowerCase()) || industry.nameEn.toLowerCase().contains(constraint.toString().toLowerCase())) {
                            departmentsSuggestion.add(industry);
                        }
                    }
                    filterResults.values = departmentsSuggestion;
                    filterResults.count = departmentsSuggestion.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                industryList.clear();
                if (results != null && results.count > 0) {

                    for (Object object : (List<?>) results.values) {
                        if (object instanceof Industry) {
                            industryList.add((Industry) object);
                        }
                    }
                    notifyDataSetChanged();
                } else if (constraint == null) {
                    // no filter, add entire original list back in
                    industryList.addAll(allIndustryList);
                    notifyDataSetInvalidated();
                }
            }
        };
    }

    public interface OnMenuItemClicked {
        void onItemSelected(Industry industry);
    }
}


