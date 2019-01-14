package com.example.ddopik.phlogbusiness.ui.setupbrand.model;

import com.example.ddopik.phlogbusiness.ui.setupbrand.fragment.stepthree.DocsAdapter;

public class Doc extends AdapterModel {

    public String id;

    public String name;

    public String url;

    public String path;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getDisplayName() {
        return name;
    }

    @Override
    public Object getImage() {
        return url;
    }

    @Override
    public Object getImageFallback() {
        return path;
    }
}
