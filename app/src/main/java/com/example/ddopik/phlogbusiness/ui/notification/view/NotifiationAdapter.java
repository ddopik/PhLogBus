package com.example.ddopik.phlogbusiness.ui.notification.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.request.RequestOptions;
import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.utiltes.GlideApp;
import com.example.ddopik.phlogbusiness.ui.notification.model.NotificationItem;


import java.util.List;

/**
 * Created by abdalla_maged On Nov,2018
 */
public class NotifiationAdapter extends RecyclerView.Adapter<NotifiationAdapter.NotificationViewHolder> {

    private Context context;
    private List<NotificationItem> notificationItemList;

    public NotifiationAdapter(List<NotificationItem> notificationItemList){
        this.notificationItemList=notificationItemList;
    }
    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context=viewGroup.getContext();
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        return new NotificationViewHolder(layoutInflater.inflate(R.layout.view_holder_notification,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder notificationViewHolder, int i) {
        NotificationItem notificationItem=notificationItemList.get(i);

        GlideApp.with(context).load(notificationItem.notificationImg)
                .apply(RequestOptions.circleCropTransform())
                .into(notificationViewHolder.notificationImg);

        if (notificationItem.notificationTitle !=null)
            notificationViewHolder.notificationTitle.setText(notificationItem.notificationTitle);

        if (notificationItem.notificationDate !=null)
            notificationViewHolder.notificationDate.setText(notificationItem.notificationDate);



    }

    @Override
    public int getItemCount() {
        return notificationItemList.size();
    }

      class NotificationViewHolder extends RecyclerView.ViewHolder {

        ImageView notificationImg;
        TextView notificationTitle,notificationDate;
          NotificationViewHolder(View view) {
            super(view);
            notificationImg=view.findViewById(R.id.notification_img);
            notificationTitle=view.findViewById(R.id.notification_title);
            notificationDate=view.findViewById(R.id.notification_time);
         }
    }
}
