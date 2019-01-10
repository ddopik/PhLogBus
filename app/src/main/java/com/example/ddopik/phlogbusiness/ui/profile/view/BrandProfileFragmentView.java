package com.example.ddopik.phlogbusiness.ui.profile.view;

import com.example.ddopik.phlogbusiness.base.commonmodel.Business;

public interface BrandProfileFragmentView {
    void viewBrandProfileData(Business business);
    void viewMessage(String msg);
    void viewBrandProfileProgress(Boolean state);
}
