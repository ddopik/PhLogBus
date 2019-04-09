package com.example.ddopik.phlogbusiness.ui.album.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import com.example.ddopik.phlogbusiness.base.commonmodel.Business;
import com.example.ddopik.phlogbusiness.base.commonmodel.Photographer;
import com.example.ddopik.phlogbusiness.base.commonmodel.MentionedUser;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.album.view.adapter.CommentsAdapterView;
import com.example.ddopik.phlogbusiness.utiltes.Constants;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapterPresenterImpl implements CommentAdapterPresenter {
    private String TAG = CommentAdapterPresenterImpl.class.getSimpleName();
    private CommentsAdapterView commentsAdapterView;
    private Context context;

    public CommentAdapterPresenterImpl(Context context,CommentsAdapterView commentsAdapterView) {
        this.commentsAdapterView = commentsAdapterView;
        this.context = context;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getMentionedUser(String key) {
        BaseNetworkApi.getSocialAutoComplete(key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socialAutoCompleteResponse -> {

                    List<MentionedUser> mentionedUserList = new ArrayList<>();
                    if (socialAutoCompleteResponse.data.photographers != null) {


                        for (Photographer photographer : socialAutoCompleteResponse.data.photographers) {
                            MentionedUser mentionedUser = new MentionedUser();

                            mentionedUser.mentionedUserId = photographer.id;
                            mentionedUser.mentionedUserName = photographer.fullName;
                            mentionedUser.mentionedImage = photographer.imageProfile;
                            mentionedUser.mentionedType = Constants.UserType.USER_TYPE_PHOTOGRAPHER;
                            mentionedUserList.add(mentionedUser);
                        }
                    }
                    if (socialAutoCompleteResponse.data.businesses != null) {
                        for (Business business : socialAutoCompleteResponse.data.businesses) {
                            MentionedUser mentionedUser = new MentionedUser();
                            mentionedUser.mentionedUserId = business.id;
                            mentionedUser.mentionedUserName = business.firstName + " " + business.lastName;
                            mentionedUser.mentionedImage = business.thumbnail;
                            mentionedUser.mentionedType = Constants.UserType.USER_TYPE_BUSINESS;
                            mentionedUserList.add(mentionedUser);
                        }
                    }
                    commentsAdapterView.viewMentionedUsers(mentionedUserList);
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                });
    }
}
