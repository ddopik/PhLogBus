package com.example.ddopik.phlogbusiness.ui.social.presenter;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.ddopik.phlogbusiness.network.BaseNetworkApi;
import com.example.ddopik.phlogbusiness.ui.social.model.SocialResponse;
import com.example.ddopik.phlogbusiness.ui.social.view.SocialFragmentView;
import com.example.ddopik.phlogbusiness.utiltes.CustomErrorUtil;
import com.google.gson.Gson;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SocailFragmentPresenterImpl implements SocialFragmentPresenter {

    private static String TAG = SocailFragmentPresenterImpl.class.getSimpleName();

    private Context context;
    private SocialFragmentView socialFragmentView;

    public SocailFragmentPresenterImpl(Context context, SocialFragmentView socialFragmentView) {
        this.context = context;
        this.socialFragmentView = socialFragmentView;

    }

    @SuppressLint("CheckResult")
    @Override
    public void getSocialData(boolean firstTime) {
        socialFragmentView.viewSocialDataProgress(true);
        BaseNetworkApi.getSocialData(firstTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(socialResponse -> {


                    Gson tempModel=new Gson();
                    SocialResponse tempSocailRespobse  = tempModel.fromJson("{\n" +
                            "    \"data\": [\n" +
                            "        {\n" +
                            "            \"source\": \"getTopRatedPhotographers\",\n" +
                            "            \"title\": \"Top Rated Photographers\",\n" +
                            "            \"story_id\": 1,\n" +
                            "            \"display_type\": \"103\",\n" +
                            "            \"slider\": false,\n" +
                            "            \"list_id\": null,\n" +
                            "            \"go_to_list\": false,\n" +
                            "            \"profiles\": [\n" +
                            "                {\n" +
                            "                    \"id\": 406,\n" +
                            "                    \"followers_count\": 4,\n" +
                            "                    \"followings_count\": 9,\n" +
                            "                    \"photos_count\": 13,\n" +
                            "                    \"image_profile\": \"https://phlog-dev.s3.eu-west-1.amazonaws.com/photographer/406/profile/1549446366_user-profile.jpg\",\n" +
                            "                    \"image_cover\": \"https://phlog-dev.s3.eu-west-1.amazonaws.com/photographer/406/profile/1549446367_user-cover.jpg\",\n" +
                            "                    \"user_name\": \"minayousry27\",\n" +
                            "                    \"full_name\": \"Mina yosry\",\n" +
                            "                    \"email\": \"mena.yousry@yahoo.com\",\n" +
                            "                    \"mobile\": \"01066444258\",\n" +
                            "                    \"country\": {\n" +
                            "                        \"id\": 1,\n" +
                            "                        \"name_en\": \"Egypt\",\n" +
                            "                        \"name_ar\": \"مصر\",\n" +
                            "                        \"created_at\": null,\n" +
                            "                        \"updated_at\": null,\n" +
                            "                        \"deleted_at\": null\n" +
                            "                    },\n" +
                            "                    \"rate\": 4.3,\n" +
                            "                    \"earnings\": [],\n" +
                            "                    \"is_follow\": false\n" +
                            "                }\n" +
                            "            ],\n" +
                            "            \"albums\": null,\n" +
                            "            \"campaigns\": null,\n" +
                            "            \"brands\": null,\n" +
                            "            \"photos\": null\n" +
                            "        },\n" +
                            "        {\n" +
                            "            \"source\": \"getPhotographersWithCommonTags\",\n" +
                            "            \"title\": \"Photographers With Common Tags\",\n" +
                            "            \"story_id\": 14,\n" +
                            "            \"display_type\": \"103\",\n" +
                            "            \"slider\": false,\n" +
                            "            \"list_id\": null,\n" +
                            "            \"go_to_list\": false,\n" +
                            "            \"profiles\": [\n" +
                            "                {\n" +
                            "                    \"id\": 546,\n" +
                            "                    \"followers_count\": 0,\n" +
                            "                    \"followings_count\": 2,\n" +
                            "                    \"photos_count\": 0,\n" +
                            "                    \"image_profile\": \"http://178.128.162.10/images/default-avatar.png\",\n" +
                            "                    \"image_cover\": null,\n" +
                            "                    \"user_name\": \"ashraf71\",\n" +
                            "                    \"full_name\": \"ashraf\",\n" +
                            "                    \"email\": \"ashraf@mm.mm\",\n" +
                            "                    \"mobile\": null,\n" +
                            "                    \"country\": null,\n" +
                            "                    \"rate\": 0,\n" +
                            "                    \"earnings\": [],\n" +
                            "                    \"is_follow\": false\n" +
                            "                }\n" +
                            "            ],\n" +
                            "            \"albums\": null,\n" +
                            "            \"campaigns\": null,\n" +
                            "            \"brands\": null,\n" +
                            "            \"photos\": null\n" +
                            "        },\n" +
                            "       \n" +
                            "        {\n" +
                            "            \"source\": \"getProfilesOfTopSellerPhotographers\",\n" +
                            "            \"title\": \"Top Seller Photographers\",\n" +
                            "            \"story_id\": 2,\n" +
                            "            \"display_type\": \"103\",\n" +
                            "            \"slider\": false,\n" +
                            "            \"list_id\": null,\n" +
                            "            \"go_to_list\": false,\n" +
                            "            \"profiles\": [\n" +
                            "                {\n" +
                            "                    \"id\": 235,\n" +
                            "                    \"followers_count\": 3,\n" +
                            "                    \"followings_count\": 0,\n" +
                            "                    \"photos_count\": 5,\n" +
                            "                    \"image_profile\": \"http://178.128.162.10/images/default-avatar.png\",\n" +
                            "                    \"image_cover\": null,\n" +
                            "                    \"user_name\": \"omartaha67\",\n" +
                            "                    \"full_name\": \"Omar Taha\",\n" +
                            "                    \"email\": \"xcoder@phlog.com\",\n" +
                            "                    \"mobile\": \"01152077545\",\n" +
                            "                    \"country\": {\n" +
                            "                        \"id\": 1,\n" +
                            "                        \"name_en\": \"Egypt\",\n" +
                            "                        \"name_ar\": \"مصر\",\n" +
                            "                        \"created_at\": null,\n" +
                            "                        \"updated_at\": null,\n" +
                            "                        \"deleted_at\": null\n" +
                            "                    },\n" +
                            "                    \"rate\": 3.6,\n" +
                            "                    \"earnings\": [],\n" +
                            "                    \"is_follow\": false\n" +
                            "                }\n" +
                            "            ],\n" +
                            "            \"albums\": null,\n" +
                            "            \"campaigns\": null,\n" +
                            "            \"brands\": null,\n" +
                            "            \"photos\": null\n" +
                            "        },\n" +
                            "       \n" +
                            "   \n" +
                            "        {\n" +
                            "            \"source\": \"getCampaignsWithTagsCommonWithUserInterest\",\n" +
                            "            \"title\": \"Campaign With Common Interest\",\n" +
                            "            \"story_id\": 11,\n" +
                            "            \"display_type\": \"201\",\n" +
                            "            \"slider\": false,\n" +
                            "            \"list_id\": null,\n" +
                            "            \"go_to_list\": false,\n" +
                            "            \"profiles\": null,\n" +
                            "            \"albums\": null,\n" +
                            "            \"campaigns\": [\n" +
                            "                {\n" +
                            "                    \"end_date\": \"2019-12-20\",\n" +
                            "                    \"image_cover\": \"https://www.bmw.co.th/content/dam/bmw/marketTH/common/Topics/allnews/2018/September/21.09.18%20BMW%20Thailand%20announces%20the%20final%20winner/BMW-News-1185x533.jpg/_jcr_content/renditions/cq5dam.resized.img.1185.large.time1537519276601.jpg\",\n" +
                            "                    \"brand_image_cover\": \"https://www.bmw.co.th/content/dam/bmw/marketTH/common/Topics/allnews/2018/September/21.09.18%20BMW%20Thailand%20announces%20the%20final%20winner/BMW-News-1185x533.jpg/_jcr_content/renditions/cq5dam.resized.img.1185.large.time1537519276601.jpg\",\n" +
                            "                    \"max_images_per_photographer\": 0,\n" +
                            "                    \"created_at\": \"2019-01-03 15:06:23\",\n" +
                            "                    \"winners_count\": \"2\",\n" +
                            "                    \"prize\": \"one thouand dollar\",\n" +
                            "                    \"descrption_en\": \"Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.\",\n" +
                            "                    \"updated_at\": \"2019-01-03 15:06:23\",\n" +
                            "                    \"max_images\": 0,\n" +
                            "                    \"title_en\": \"New Year 2019 _ 3\",\n" +
                            "                    \"status\": \"3\",\n" +
                            "                    \"start_date\": \"2018-12-13\",\n" +
                            "                    \"id\": 509\n" +
                            "                }\n" +
                            "            ],\n" +
                            "            \"brands\": null,\n" +
                            "            \"photos\": null\n" +
                            "        },\n" +
                            "        {\n" +
                            "            \"source\": \"getPhotographersWithCommonTags\",\n" +
                            "            \"title\": \"Photographers With Common Tags\",\n" +
                            "            \"story_id\": 14,\n" +
                            "            \"display_type\": \"103\",\n" +
                            "            \"slider\": false,\n" +
                            "            \"list_id\": null,\n" +
                            "            \"go_to_list\": false,\n" +
                            "            \"profiles\": [\n" +
                            "                {\n" +
                            "                    \"id\": 319,\n" +
                            "                    \"followers_count\": 4,\n" +
                            "                    \"followings_count\": 6,\n" +
                            "                    \"photos_count\": 21,\n" +
                            "                    \"image_profile\": \"http://178.128.162.10/images/default-avatar.png\",\n" +
                            "                    \"image_cover\": null,\n" +
                            "                    \"user_name\": \"osama80\",\n" +
                            "                    \"full_name\": \"PPPPP\",\n" +
                            "                    \"email\": \"osama@mm.mm\",\n" +
                            "                    \"mobile\": null,\n" +
                            "                    \"country\": null,\n" +
                            "                    \"rate\": 3.3,\n" +
                            "                    \"earnings\": [],\n" +
                            "                    \"is_follow\": false\n" +
                            "                }\n" +
                            "            ],\n" +
                            "            \"albums\": null,\n" +
                            "            \"campaigns\": null,\n" +
                            "            \"brands\": null,\n" +
                            "            \"photos\": null\n" +
                            "        }\n" +
                            "      \n" +
                            "   \n" +
                            "    ]\n" +
                            "}", SocialResponse.class);

                    socialFragmentView.viewSocialData(socialResponse.data);
                    socialFragmentView.viewSocialDataProgress(false);
                }, throwable -> {
                    CustomErrorUtil.Companion.setError(context, TAG, throwable);
                    socialFragmentView.viewSocialDataProgress(false);
                });

    }





}
