package com.example.ddopik.phlogbusiness.ui.search.album.presenter;

import com.example.ddopik.phlogbusiness.base.commonmodel.Filter;

import java.util.List;
import java.util.Map;

/**
 * Created by abdalla_maged on 10/30/2018.
 */
public interface AlbumSearchPresenter {

     void getAlbumSearch(String key, List<Filter> filterList, int page);
     Map<String,String> getFilter(List<Filter> filterList);

     void getSearchFilters();
}
