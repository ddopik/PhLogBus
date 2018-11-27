package com.example.ddopik.phlogbusiness.base;

/**
 * Created by abdalla-maged on 3/27/18.
 */
/**
 * this Class should have common method that all view interface should have
 **/
public abstract class BaseView {
    abstract void onError(String message);

    abstract void onError(int resID);
}
