package com.example.ddopik.phlogbusiness.ui.downloads.view;

import com.example.ddopik.phlogbusiness.ui.downloads.model.GroupItem;

import java.util.List;

public interface DownloadsView {
    void setDownloads(List<GroupItem> data);

    void setLoading(boolean loading);
}
