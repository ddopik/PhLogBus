package com.example.ddopik.phlogbusiness.ui.downloads.presenter;

import android.content.Context;

import com.example.ddopik.phlogbusiness.ui.downloads.view.DownloadsView;

public interface DownloadsPresenter {

    void setView(DownloadsView view);

    void getDownloads(Context context);
}
