package com.example.ddopik.phlogbusiness.ui.search.images.view;


import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.ui.search.album.model.Filter;

import java.util.List;

/**
 * Created by abdalla_maged on 10/31/2018.
 */
public interface ImagesSearchFragmentView {

    void viewImagesSearchItems(List<BaseImage> baseImageList);
    void viewSearchFilters(List<Filter> filterList);
    void viewImagesSearchProgress(boolean state);
    void showMessage(String msg);
}
