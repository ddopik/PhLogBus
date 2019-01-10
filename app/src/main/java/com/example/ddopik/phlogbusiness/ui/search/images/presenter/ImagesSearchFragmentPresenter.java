package com.example.ddopik.phlogbusiness.ui.search.images.presenter;

import com.example.ddopik.phlogbusiness.base.commonmodel.Filter;

import java.util.List;

/**
 * Created by abdalla_maged on 10/31/2018.
 */
public interface ImagesSearchFragmentPresenter {


     void getSearchImages(String key, List<Filter> filterList, int page);


}
