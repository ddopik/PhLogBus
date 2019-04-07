package com.example.ddopik.phlogbusiness.ui.commentimage.replay.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.commentimage.replay.view.ReplayCommentActivityView;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import com.example.ddopik.phlogbusiness.utiltes.Utilities;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ReplayCommentPresenterImpl implements ReplayCommentPresenter {


    private String TAG = ReplayCommentPresenterImpl.class.getSimpleName();
    private Context context;
    private ReplayCommentActivityView replayCommentActivityView;

    public ReplayCommentPresenterImpl(Context context, ReplayCommentActivityView replayCommentActivityView) {
        this.context = context;
        this.replayCommentActivityView = replayCommentActivityView;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getReplies(int parentCommentId, int imageID,String page) {
        replayCommentActivityView.viewRepliesProgress(true);
        BaseNetworkApi.getCommentReplies(parentCommentId, imageID, String.valueOf(page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(repliesResponse -> {
                    replayCommentActivityView.viewRepliesProgress(false);
                    replayCommentActivityView.viewReplies(repliesResponse.data);
                    if (repliesResponse.data.nextPageUrl != null) {
                        replayCommentActivityView.setNextPageUrl(Utilities.getNextPageNumber(context, repliesResponse.data.nextPageUrl));

                    } else {
                        replayCommentActivityView.setNextPageUrl(null);
                    }
                }, throwable -> {
                    replayCommentActivityView.viewRepliesProgress(false);
                });

    }

    @SuppressLint("CheckResult")
    @Override
    public void submitReplayComment(String imageId, String parentReplayId, String comment) {
        replayCommentActivityView.viewRepliesProgress(true);
        BaseNetworkApi.submitImageCommentReplay(imageId,  parentReplayId, comment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(submitImageCommentResponse -> {
                    replayCommentActivityView.viewMessage("Comment Submitted");
                    replayCommentActivityView.onCommentReplied(submitImageCommentResponse.data);
                    replayCommentActivityView.viewRepliesProgress(false);
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                    replayCommentActivityView.viewRepliesProgress(false);
                });

    }

}
