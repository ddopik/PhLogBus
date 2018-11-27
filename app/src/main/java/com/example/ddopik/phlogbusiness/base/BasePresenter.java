package com.example.ddopik.phlogbusiness.base;

/**
 * Created by abdalla-maged on 3/27/18.
 */

/**
 * this Class  should have common method that all Presenter interface should have
 **/
public abstract class BasePresenter<T extends BaseView> {

   abstract void setView(T view);
}
