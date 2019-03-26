package com.example.ddopik.phlogbusiness.ui.search.mainSearchView.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.content.res.AppCompatResources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.widgets.CustomTextView;
import com.example.ddopik.phlogbusiness.ui.search.mainSearchView.model.FilterOption;
import com.example.ddopik.phlogbusiness.base.commonmodel.Filter;

import java.util.List;

/**
 * Created by abdalla_maged on 10/30/2018.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<Filter> filterList;
    public OnChildViewListener onChildViewListener;

    public ExpandableListAdapter(Context context, List<Filter> filterList) {
        this._context = context;
        this.filterList = filterList;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return filterList.get(groupPosition).options.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        FilterOption filterOption = filterList.get(groupPosition).options.get(childPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_expand_list, null);
        }

        CustomTextView txtListChild = convertView.findViewById(R.id.filter_item_val);
        ConstraintLayout itemContainer = convertView.findViewById(R.id.item_filter_container);
        RadioButton filterRadioButton = convertView.findViewById(R.id.filter_select);
        txtListChild.setText(filterOption.displayName);


        if (filterOption.isSelected) {
            filterRadioButton.setChecked(true);

        } else {
            filterRadioButton.setChecked(false);
        }

        itemContainer.setOnClickListener(v -> onChildViewListener.onChildViewClickListener(filterOption));
        filterRadioButton.setOnClickListener(v -> onChildViewListener.onChildViewClickListener(filterOption));


        return convertView;
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return filterList.get(groupPosition).options.size();

    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
        Object o = getGroup(groupPosition);
    }

    @Override
    public Object getGroup(int groupPosition) {
        return filterList.get(groupPosition).options;
    }

    @Override
    public int getGroupCount() {
        return filterList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = filterList.get(groupPosition).displayName;
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_header, null);
        }

        CustomTextView lblListHeader = convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        ImageView arrow = convertView.findViewById(R.id.header_item_down_btn);

        if (isExpanded) {
            arrow.setBackground(AppCompatResources.getDrawable(_context, R.drawable.arrow_up_orange));
            lblListHeader.setTextColor(convertView.getContext().getResources().getColor(R.color.text_input_color));
        } else {
            arrow.setBackground(AppCompatResources.getDrawable(_context, R.drawable.down_arrow_white));
            lblListHeader.setTextColor(convertView.getContext().getResources().getColor(R.color.white));
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    public interface OnChildViewListener {
        void onChildViewClickListener(FilterOption filterOption);

    }
}
