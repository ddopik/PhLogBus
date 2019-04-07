package com.example.ddopik.phlogbusiness.ui.search.images.view;


import com.example.ddopik.phlogbusiness.base.commonmodel.Filter;
import com.example.ddopik.phlogbusiness.ui.search.images.model.ImagesSearchData;

import java.util.List;

/**
 * Created by abdalla_maged on 10/31/2018.
 */
public interface ImagesSearchFragmentView {

    void viewImagesSearchImages(ImagesSearchData imagesSearchData);

    void viewImagesSearchFilterProgress(boolean state);
    void viewImagesSearchImagesProgress(boolean state);

    void viewSearchFilters(List<Filter> filterList);

    void showMessage(String msg);

    void setNextPageUrl(String page);

}
