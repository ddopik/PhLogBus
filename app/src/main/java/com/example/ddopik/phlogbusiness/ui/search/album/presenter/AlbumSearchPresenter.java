package com.example.ddopik.phlogbusiness.ui.search.album.presenter;

import com.example.ddopik.phlogbusiness.base.commonmodel.Filter;

import java.util.List;

/**
 * Created by abdalla_maged on 10/30/2018.
 */
public interface AlbumSearchPresenter {

     void getAlbumSearch(String key, List<Filter> filterList, int page);
}
