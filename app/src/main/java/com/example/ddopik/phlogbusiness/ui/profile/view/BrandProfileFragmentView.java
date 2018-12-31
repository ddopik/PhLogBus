package com.example.ddopik.phlogbusiness.ui.profile.view;

import com.example.ddopik.phlogbusiness.base.commonmodel.Brand;

public interface BrandProfileFragmentView {
    void viewBrandProfileData(Brand brand);
    void viewMessage(String msg);
    void viewBrandProfileProgress(Boolean state);
}
