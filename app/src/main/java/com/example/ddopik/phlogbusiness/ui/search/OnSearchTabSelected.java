package com.example.ddopik.phlogbusiness.ui.search;

import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by abdalla_maged On Dec,2018
 */

/**
 *  //this interface gets the query if exists through other taps
 * **/
public interface OnSearchTabSelected {
    EditText getSearchView();
    TextView getSearchResultCount();
}
