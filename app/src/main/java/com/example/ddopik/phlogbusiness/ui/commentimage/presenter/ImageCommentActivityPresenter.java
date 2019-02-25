package com.example.ddopik.phlogbusiness.ui.commentimage.presenter;

import com.example.ddopik.phlogbusiness.base.commonmodel.BaseImage;
import com.example.ddopik.phlogbusiness.ui.commentimage.model.ReportModel;
import com.example.ddopik.phlogbusiness.ui.commentimage.model.ReportReason;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by abdalla_maged On Nov,2018
 */
public interface ImageCommentActivityPresenter {
    void getImageComments(String imageId, String page);
    void likePhoto(BaseImage baseImage);
    void unLikePhoto(BaseImage baseImage);
    void addImageToCart(int imageId);
    void rateImage(BaseImage baseImage,float rate);
    void submitComment(String imageId, String comment);

    void getReportReasons(Consumer<List<ReportReason>> consumer);

    void submitReport(Consumer<Boolean> success, ReportModel model);

    void chooseWinner(int campaignId, BaseImage image, Consumer<Boolean> success);
}
