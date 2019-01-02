package com.example.ddopik.phlogbusiness.ui.search.album.view;

import com.example.ddopik.phlogbusiness.ui.search.album.model.SearchFilter;
import com.example.softmills.phlog.ui.search.view.album.model.AlbumSearch;

import java.util.List;

/**
 * Created by abdalla_maged on 10/30/2018.
 */
public interface AlbumSearchFragmentView {

    void viewSearchFilters(List<SearchFilter> searchFilterList);
    void viewSearchAlbum(List<AlbumSearch> albumSearchList);
    void showMessage(String msg);
    void showFilterSearchProgress(boolean state);

}
