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
import com.example.ddopik.phlogbusiness.ui.signup.model.Country;


import java.util.ArrayList;

public class CountersAdapter extends ArrayAdapter<Country> {
    private Context context;
    private int resourceId;
    private ArrayList<Country> countryList;
    private ArrayList<Country> countresAll;
    private ArrayList<Country> suggestions;
    private int viewResourceId;
    private CountersListener countresListner;


    public CountersAdapter(@NonNull Context context, int resourceId, ArrayList<Country> countryList) {
        super(context, resourceId, countryList);
        this.countryList = countryList;
        this.context = context;
        this.resourceId = resourceId;
        this.countresAll = (ArrayList<Country>) countryList.clone();
        this.suggestions = new ArrayList<Country>();

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

            if (countryList.size() > 0) {
                Country country = getItem(position);
                TextView countryName = view.findViewById(R.id.country_id);
                countryName.setText(country.nameEn);
                countryName.setOnClickListener(v -> countresListner.OnCountryClick(country));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Nullable
    @Override
    public Country getItem(int position) {
        return countryList.get(position);
    }

    @Override
    public int getCount() {
        return countryList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            String str = ((Country) (resultValue)).nameEn;
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (Country country : countresAll) {
                    if (country.nameEn.toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(country);
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
            ArrayList<Country> filteredList = (ArrayList<Country>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (Country c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            }
        }
    };


    public void setCountersListener(CountersListener countersListener) {
        this.countresListner = countersListener;
    }

    public interface CountersListener {

        void OnCountryClick(Country country);
    }
}
