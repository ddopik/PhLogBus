package com.example.ddopik.phlogbusiness.ui.campaigns.addcampaign.view;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.base.commonmodel.Industry;
import com.example.ddopik.phlogbusiness.base.commonmodel.Tag;

import java.util.List;

/**
 * Created by abdalla_maged on 10/28/2018.
 */
public class CampaignIndustryAdapter extends RecyclerView.Adapter<CampaignIndustryAdapter.IndustryViewHolder> {
    private List<Industry> industrylist;
    public OnSelectedItemClicked onSelectedItemClicked;

    public CampaignIndustryAdapter(List<Industry> industrylist) {
        this.industrylist = industrylist;
    }

    @NonNull
    @Override
    public IndustryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = layoutInflater.inflate(R.layout.view_holder_tag, viewGroup, false);
        return new IndustryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IndustryViewHolder industryViewHolder, int i) {

        industryViewHolder.industryName.setText(industrylist.get(i).nameEn);

        if (onSelectedItemClicked != null) {
            industryViewHolder.delete.setOnClickListener((view) -> onSelectedItemClicked.onItemDeleted(industrylist.get(i)));
         }

    }

    @Override
    public int getItemCount() {

        return industrylist.size();
    }


    class IndustryViewHolder extends RecyclerView.ViewHolder {
        TextView industryName;
        ImageButton delete;

        IndustryViewHolder(View view) {
            super(view);
            industryName = view.findViewById(R.id.tag_name);
            delete= view.findViewById(R.id.delete_tag);
        }

    }

    public interface OnSelectedItemClicked {
        void onItemDeleted(Industry industry);
     }
}
