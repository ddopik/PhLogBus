package com.example.ddopik.phlogbusiness.ui.social.view;


import com.example.ddopik.phlogbusiness.ui.social.model.SocialData;

import java.util.List;

public interface SocialFragmentView {
    void viewSocialData(List<SocialData> socialDataList);
    void viewSocialDataProgress(boolean state);
    void showMessage(String msg);
}
