package com.example.ddopik.phlogbusiness.ui.signup.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.commonmodel.Industry;


import java.util.ArrayList;

public class IndustrieAdapter extends ArrayAdapter<Industry> {
    private Context context;
    private int resourceId;
    private ArrayList<Industry> industryList;
    private ArrayList<Industry> industryAll;
    private ArrayList<Industry> suggestions;
    private int viewResourceId;
    private IndustryListener industrieListner;


    public IndustrieAdapter(@NonNull Context context, int resourceId, ArrayList<Industry> industryList) {
        super(context, resourceId, industryList);
        this.industryList = industryList;
        this.context = context;
        this.resourceId = resourceId;
        this.industryAll = (ArrayList<Industry>) industryList.clone();
        this.suggestions = new ArrayList<Industry>();

    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        try {
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                view = inflater.inflate(resourceId, parent, false);
            }

            if (industryList.size() > 0) {
                Industry industry = getItem(position);
                TextView industryName = view.findViewById(R.id.industrie_id);
                industryName.setText(industry.nameEn);
                industryName.setOnClickListener(v -> industrieListner.OnCountryClick(industry));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Nullable
    @Override
    public Industry getItem(int position) {
        return industryList.get(position);
    }

    @Override
    public int getCount() {
        return industryList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            String str = ((Industry) (resultValue)).nameEn;
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (Industry industry : industryAll) {
                    if (industry.nameEn.toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(industry);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<Industry> filteredList = (ArrayList<Industry>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (Industry c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            }
        }
    };


    public void setIndustrieListener(IndustryListener industryListener) {
        this.industrieListner = industryListener;
    }

    public interface IndustryListener {

        void OnCountryClick(Industry country);
    }
}
