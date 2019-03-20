package com.example.ddopik.phlogbusiness.ui.search.mainSearchView.view;

import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by abdalla_maged On Dec,2018
 */

/**
 *  //this interface  pass SearchActivity CommonView
 * **/
public interface OnSearchTabSelected {
    EditText getSearchView();
    TextView getSearchResultCountView();

}
