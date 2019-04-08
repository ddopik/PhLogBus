package com.example.ddopik.phlogbusiness.ui.search.profile.view;


import com.example.ddopik.phlogbusiness.base.commonmodel.Photographer;
import com.example.ddopik.phlogbusiness.ui.search.profile.model.ProfileSearchData;

import java.util.List;

/**
 * Created by abdalla_maged on 11/1/2018.
 */
public interface ProfileSearchFragmentView {

    void viewProfileSearchItems(ProfileSearchData profileSearchData);

    void viewProfileSearchProgress(Boolean state);

    void showMessage(String msg);
    void setNextPageUrl(String page);

}
