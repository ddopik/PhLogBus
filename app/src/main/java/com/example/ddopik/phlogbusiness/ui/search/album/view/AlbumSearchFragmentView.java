package com.example.ddopik.phlogbusiness.ui.search.album.view;

import com.example.ddopik.phlogbusiness.base.commonmodel.Filter;
import com.example.ddopik.phlogbusiness.ui.search.album.model.AlbumSearchData;
import com.example.softmills.phlog.ui.search.view.album.model.AlbumSearch;

import java.util.List;

/**
 * Created by abdalla_maged on 10/30/2018.
 */
public interface AlbumSearchFragmentView {

    void viewSearchFilters(List<Filter> filterList);
    void viewSearchAlbum(AlbumSearchData albumSearchData);
    void showMessage(String msg);
    void showFilterSearchProgress(boolean state);
    void setNextPageUrl(String page);

}
