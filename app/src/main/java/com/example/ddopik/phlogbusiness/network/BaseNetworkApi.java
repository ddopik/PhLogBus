package com.example.ddopik.phlogbusiness.network;

import com.androidnetworking.common.Priority;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.example.ddopik.phlogbusiness.base.widgets.dialogs.addtoLightbox.model.AddImageToCartResponse;
import com.example.ddopik.phlogbusiness.base.widgets.dialogs.addtoLightbox.model.AddImageToLightBoxResponse;
import com.example.ddopik.phlogbusiness.base.widgets.dialogs.addtoLightbox.model.LightBoxListResponse;
import com.example.ddopik.phlogbusiness.base.widgets.dialogs.addtoLightbox.model.RemoveImageToLightBoxResponse;
import com.example.ddopik.phlogbusiness.ui.accountdetails.model.AccountDetailsModel;
import com.example.ddopik.phlogbusiness.ui.album.model.AlbumPreviewImagesResponse;
import com.example.ddopik.phlogbusiness.ui.album.model.AlbumPreviewResponse;
import com.example.ddopik.phlogbusiness.ui.album.model.SavePhotoResponse;
import com.example.ddopik.phlogbusiness.ui.brand.model.BrandInnerResponse;
import com.example.ddopik.phlogbusiness.ui.campaigns.addcampaign.model.SubmitCampaignResponse;
import com.example.ddopik.phlogbusiness.ui.campaigns.inner.model.CampaignInnerPhotosResponse;
import com.example.ddopik.phlogbusiness.ui.campaigns.inner.model.CampaignInnerResponse;
import com.example.ddopik.phlogbusiness.ui.campaigns.inner.model.ChangeCampaignDateResponse;
import com.example.ddopik.phlogbusiness.ui.campaigns.model.CampaignResponse;
import com.example.ddopik.phlogbusiness.ui.campaigns.model.FollowBrandResponse;
import com.example.ddopik.phlogbusiness.ui.campaigns.model.FollowCampaignResponse;
import com.example.ddopik.phlogbusiness.ui.cart.model.CartResponse;
import com.example.ddopik.phlogbusiness.ui.cart.model.RemoveItemResponse;
import com.example.ddopik.phlogbusiness.ui.downloads.model.Response;
import com.example.ddopik.phlogbusiness.ui.commentimage.model.ImageCommentsResponse;
import com.example.ddopik.phlogbusiness.ui.commentimage.model.ImageRateResponse;
import com.example.ddopik.phlogbusiness.ui.commentimage.model.LikeImageResponse;
import com.example.ddopik.phlogbusiness.ui.commentimage.model.SubmitImageCommentResponse;
import com.example.ddopik.phlogbusiness.ui.commentimage.model.*;
import com.example.ddopik.phlogbusiness.ui.lightbox.model.AddLighBoxResponse;
import com.example.ddopik.phlogbusiness.ui.lightbox.model.BrandLightBoxResponse;
import com.example.ddopik.phlogbusiness.ui.lightbox.model.DeleteLightBoxResponse;
import com.example.ddopik.phlogbusiness.ui.lightboxphotos.model.LightboxPhotosResponse;
import com.example.ddopik.phlogbusiness.ui.login.model.LoginResponse;
import com.example.ddopik.phlogbusiness.ui.login.model.SocialLoginResponse;
import com.example.ddopik.phlogbusiness.ui.notification.model.NotificationResponse;
import com.example.ddopik.phlogbusiness.ui.profile.model.BusinessProfileResponse;
import com.example.ddopik.phlogbusiness.ui.search.album.model.AlbumSearchResponse;
import com.example.ddopik.phlogbusiness.ui.search.images.model.ImagesSearchResponse;
import com.example.ddopik.phlogbusiness.ui.search.mainSearchView.model.SearchFiltersResponse;
import com.example.ddopik.phlogbusiness.ui.search.profile.model.ProfileSearchResponse;
import com.example.ddopik.phlogbusiness.ui.setupbrand.model.DocumentsResponse;
import com.example.ddopik.phlogbusiness.ui.setupbrand.model.RequestVerificationResponse;
import com.example.ddopik.phlogbusiness.ui.setupbrand.model.SetupBrandModel;
import com.example.ddopik.phlogbusiness.ui.signup.model.AllIndustriesResponse;
import com.example.ddopik.phlogbusiness.ui.signup.model.AllTagsResponse;
import com.example.ddopik.phlogbusiness.ui.signup.model.SignUpResponse;
import com.example.ddopik.phlogbusiness.ui.signup.model.UploadProfileImgResponse;
import com.example.ddopik.phlogbusiness.ui.social.model.SocialResponse;
import com.example.ddopik.phlogbusiness.ui.userprofile.model.FollowUserResponse;
import com.example.ddopik.phlogbusiness.ui.userprofile.model.UserPhotosResponse;
import com.example.ddopik.phlogbusiness.ui.userprofile.model.UserProfileResponse;
import com.example.ddopik.phlogbusiness.ui.welcome.model.WelcomeScreenResponse;
import com.rx2androidnetworking.Rx2ANRequest;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import io.reactivex.Observable;
import io.reactivex.Single;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by abdalla-maged on 3/29/18.
 */

public class BaseNetworkApi {


    //Network Status
    public static String STATUS_OK = "200";
    public static String DEFAULT_USER_TYPE = "1";
    public static final int STATUS_BAD_REQUEST = 400;
    public static final int STATUS_401 = 401;
    public static final int STATUS_404 = 404;
    public static final int STATUS_500 = 500;
    public static String STATUS_ERROR = "405";
    public static final String ERROR_STATE_1 = "1";

    //
    private static final String BASE_URL = "http://178.128.162.10/public/api/business";
    private static final String BASE_URL_COMMON = "http://178.128.162.10/public/api/common";

    private static final String WELCOME_SLIDES_IMAGES = BASE_URL + "/photographer/init_slider";
    private static final String USER_SEARCH_FILTERS = BASE_URL_COMMON + "/filters/list";
    private static final String SEARCH_ALBUM = BASE_URL + "/album/search";
    private static final String SEARCH_IMAGES = BASE_URL + "/photo/search";
    private static final String ALL_INDUSTRY = BASE_URL_COMMON + "/industries/list";
    private static final String ALL_TAGS_URL = BASE_URL_COMMON + "/industries/list";
    private static final String SIGN_UP_USER = BASE_URL + "/auth/signup";
    private static final String NORMAL_LOGIN = BASE_URL + "/auth/login";
    private static final String FACEBOOK_LOGIN_URL = BASE_URL + "/signup_facebook";
    private static final String INNER_BRAND_URL = BASE_URL + "/search_in_one_brands";
    private static final String BRAND_FOLLOW_URL = BASE_URL + "/join_photographer_brand";
    private static final String BRAND_UN_FOLLOW_URL = BASE_URL + "/unfollow";
    private static final String SOCIAL_DATA_URL = BASE_URL + "/social";
    private static final String SOCIAL_DATA_URL_RESET = BASE_URL + "/social?reset=1";
    private static final String GET_ALL_NOTIFICATION = BASE_URL + "/notification/list";
    private static final String ALL_COMPLETED_CAMPAIGN_URL = BASE_URL + "/campaign/old";
    private static final String ALL_RUNNING_CAMPAIGN_URL = BASE_URL + "/campaign/running";
    private static final String ALL_DRAFT_CAMPAIGN_URL = BASE_URL + "/campaign/draft";
    private static final String CAMPAIGN_PHOTOS_URL = BASE_URL + "/campaign/photos";
    private static final String CAMPAIGN_DETAILS_URL = BASE_URL + "/campaign/details";
    private static final String USER_PROFILE_URL = BASE_URL + "/photographer/details";
    private static final String USER_PROFILE_PHOTOS = BASE_URL + "/photographer/photos";
    private static final String PHOTOGRAPHER_UN_FOLLOW_USER_URL = BASE_URL + "/photographer/unfollow";
    private static final String PHOTOGRAPHER_FOLLOW_USER_URL = BASE_URL + "/photographer/follow";
    private static final String GET_ALBUM_DETAILS = BASE_URL + "/photographer/album/details";
    private static final String PHOTOGRAPHER_SEARCH_URL = BASE_URL + "/photographer/list";
    private static final String GET_ALBUM_PREVIEW = BASE_URL +"/album/details";
    private static final String GET_ALBUM_IMAGES_PREVIEW = BASE_URL +"/album/photos";
    private static final String GET_IMAGE_COMMENT = BASE_URL + "/photo/comment/list";
    private static final String SUBMIT_IMAGE_COMMENT = BASE_URL + "/photo/comment";
    private static final String LIKE_IMAGE = BASE_URL + "/photo/like";
    private static final String UN_LIKE_IMAGE = BASE_URL + "/photo/unlike";
    private static final String RATE_IMAGE = BASE_URL + "/photo/rate";
    private static final String FOLLOW_CAMPAIGN_URL = BASE_URL + "/join_photographer_campaign";
    private static final String UPLOAD_PROFILE_IMG = BASE_URL + "/profile/upload";
    private static final String SUBMIT_CAMPAIGN_URL = BASE_URL + "/campaign/create";
    private static final String BRAND_PROFILE_URL = BASE_URL + "/profile";
    private static final String BRAND_LIGHT_BOX_URL = BASE_URL + "/lightBox/all";
    private static final String DELETE_LIGHT_BOX_URL = BASE_URL + "/lightBox/delete";
    private static final String ADD_LIGHT_BOX_URL = BASE_URL + "/lightBox/add";
    private static final String GET_LIGHT_BOX_URL = BASE_URL + "/lightBox/all";
    private static final String ADD_IMG_TO_LIGHT_BOX_URL = BASE_URL + "/lightBox/photo/save";
    private static final String ADD_IMG_TO_CART_URL = BASE_URL + "/cart/add";
    private static final String REMOVE_IMG_TO_LIGHT_BOX_URL = BASE_URL + "/lightBox/photo/delete";
    private static final String SETUP_BRAND_URL = BASE_URL + "/brand/setup";
    private static final String UPLOAD_DOCUMENT_URL = BASE_URL + "/brand/document/upload";
    private static final String GET_DOCUMENTS_URL = BASE_URL + "/brand/document/list";
    private static final String GET_CART_ITEMS_URL = BASE_URL + "/cart/list";
    private static final String REMOVE_FROM_CART_URL = BASE_URL + "/cart/remove";
    private static final String SOCIAL_AUTO_COMPLETE = BASE_URL_COMMON + "/social/search";


    private static final String UPDATE_PROFILE_URL = BASE_URL + "/profile/update";
    private static final String UNFOLLOW_USER_URL = BASE_URL + "/photographer/unfollow";
    private static final String DOWNLOADS_URL = BASE_URL + "/campaign/photos";
    private static final String FORGOT_PASSWORD_URL = BASE_URL + "/auth/forgot_password";
    private static final String LIGHT_BOX_PHOTOS_URL = BASE_URL + "/lightBox/photos";
    private static final String CHANGE_CAMPAIGN_END_DATE_URL = BASE_URL + "/campaign/extend";
    private static final String DUMMY_SOCIAL = "http://178.128.162.10/public/api/photographer/social/dummy";
    private static final String REQUEST_VERIFICATION_BRAND_URL = BASE_URL + "/brand/request_verify";
    private static final String SAVE_PHOTO_URL = BASE_URL + "/photographer/photo/save";
    private static final String UNSAVE_PHOTO_URL = BASE_URL + "/photographer/photo/unsave";

    //Path Parameters
    private static final String PAGER_PATH_PARAMETER = "page";


    //Body Parameters
    private static final String TOKEN_BODY_PARAMETER = "token";

    public static io.reactivex.Observable<SignUpResponse> signUpUser(HashMap<String, String> signUpData) {
        return Rx2AndroidNetworking.post(SIGN_UP_USER)
                .addBodyParameter(signUpData)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(SignUpResponse.class);
    }

    public static io.reactivex.Observable<LoginResponse> LoginUserNormal(HashMap<String, String> loginData) {
        return Rx2AndroidNetworking.post(NORMAL_LOGIN)
                .addBodyParameter("email", loginData.get("email"))
                .addBodyParameter("password", loginData.get("password"))
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(LoginResponse.class);
    }

    public static io.reactivex.Observable<SocialLoginResponse> socialLoginFacebook(HashMap<String, String> loginData) {
        return Rx2AndroidNetworking.post(FACEBOOK_LOGIN_URL)
                .addBodyParameter("fullName", loginData.get("fullName"))
                .addBodyParameter("facebook_id", loginData.get("facebook_id"))
                .addBodyParameter("mobile_os", loginData.get("mobile_os"))
                .addBodyParameter("mobile_model", loginData.get("mobile_model"))
                .addBodyParameter("email", loginData.get("email"))
                .addBodyParameter("image_profile", loginData.get("image_profile"))
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(SocialLoginResponse.class);
    }

    public static io.reactivex.Observable<AllIndustriesResponse> getAllIndustries() {
        return Rx2AndroidNetworking.get(ALL_INDUSTRY)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(AllIndustriesResponse.class);
    }

    public static io.reactivex.Observable<AllTagsResponse> getAllTags() {
        return Rx2AndroidNetworking.get(ALL_INDUSTRY)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(AllTagsResponse.class);
    }


    public static io.reactivex.Observable<WelcomeScreenResponse> getWelcomeSlidesImages() {
        return Rx2AndroidNetworking.get(WELCOME_SLIDES_IMAGES)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(WelcomeScreenResponse.class);
    }

    public static io.reactivex.Observable<SearchFiltersResponse> getFilters() {
        return Rx2AndroidNetworking.get(USER_SEARCH_FILTERS)

                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(SearchFiltersResponse.class);
    }


    public static io.reactivex.Observable<AlbumSearchResponse> getSearchAlbum(String key, Map<String, String> filters, String page) {
        return Rx2AndroidNetworking.post(SEARCH_ALBUM)
                .addQueryParameter(PAGER_PATH_PARAMETER, page)
                .addBodyParameter("keyword", key)
                .addBodyParameter(filters)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(AlbumSearchResponse.class);
    }


    public static io.reactivex.Observable<ImagesSearchResponse> getSearchImages(String key,Map<String,String> filters ,String page) {
        return Rx2AndroidNetworking.post(SEARCH_IMAGES)
                .addQueryParameter(PAGER_PATH_PARAMETER, page)
                .addBodyParameter("keyword",key)
                .addBodyParameter(filters)
                 .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(ImagesSearchResponse.class);
    }


    public static io.reactivex.Observable<SavePhotoResponse> savePhoto(int id) {
        return Rx2AndroidNetworking.post(SAVE_PHOTO_URL)
                .addBodyParameter("photo_id", String.valueOf(id))
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(SavePhotoResponse.class);
    }

    public static io.reactivex.Observable<SavePhotoResponse> unSavePhoto(int id) {
        return Rx2AndroidNetworking.post(UNSAVE_PHOTO_URL)
                .addBodyParameter("photo_id", String.valueOf(id))
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(SavePhotoResponse.class);
    }
    public static io.reactivex.Observable<FollowBrandResponse> followBrand(String brandId) {
        return Rx2AndroidNetworking.post(BRAND_FOLLOW_URL)
                .getResponseOnlyFromNetwork()
                .addBodyParameter("business_id", brandId)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(FollowBrandResponse.class);
    }

    public static io.reactivex.Observable<FollowBrandResponse> unFollowBrand(String brandId) {
        return Rx2AndroidNetworking.post(BRAND_UN_FOLLOW_URL)
                .getResponseOnlyFromNetwork()
                .addBodyParameter("business_id", brandId)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(FollowBrandResponse.class);
    }
    public static io.reactivex.Observable<SocialResponse> getSocialData(boolean firstTime) {

        String url;
        if (firstTime){
            url=SOCIAL_DATA_URL_RESET;
        }else {
            url=SOCIAL_DATA_URL;
        }
        return Rx2AndroidNetworking.post(url)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(SocialResponse.class);
    }


    public static io.reactivex.Observable<ProfileSearchResponse> getProfileSearch(String key, int page) {
        return Rx2AndroidNetworking.post(PHOTOGRAPHER_SEARCH_URL)
                .addBodyParameter("keyword", key)
                .addQueryParameter(PAGER_PATH_PARAMETER, String.valueOf(page))
                .getResponseOnlyFromNetwork()
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(ProfileSearchResponse.class);
    }

    public static io.reactivex.Observable<NotificationResponse> getNotification(String token, String page) {
        return Rx2AndroidNetworking.post(GET_ALL_NOTIFICATION)
                .addBodyParameter(TOKEN_BODY_PARAMETER, token)
                .addQueryParameter(PAGER_PATH_PARAMETER, page)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(NotificationResponse.class);
    }


    public static io.reactivex.Observable<CampaignResponse> getAllCompleteCampaign(int page) {
        return Rx2AndroidNetworking.post(ALL_COMPLETED_CAMPAIGN_URL)
                .addQueryParameter(PAGER_PATH_PARAMETER, String.valueOf(page))
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(CampaignResponse.class);
    }

    public static io.reactivex.Observable<CampaignResponse> getAllDraftCampaign(int page) {
        return Rx2AndroidNetworking.post(ALL_DRAFT_CAMPAIGN_URL)
                .addQueryParameter(PAGER_PATH_PARAMETER, String.valueOf(page))
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(CampaignResponse.class);
    }

    public static io.reactivex.Observable<CampaignResponse> getAllRunningCampaign(int page) {
        return Rx2AndroidNetworking.post(ALL_RUNNING_CAMPAIGN_URL)
                .addQueryParameter(PAGER_PATH_PARAMETER, String.valueOf(page))
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(CampaignResponse.class);
    }

    public static io.reactivex.Observable<CampaignInnerPhotosResponse> getCampaignInnerPhotos(String token, String campaignID, int page) {
        return Rx2AndroidNetworking.post(CAMPAIGN_PHOTOS_URL)
                .addBodyParameter(TOKEN_BODY_PARAMETER, token)
                .addBodyParameter("campaign_id", campaignID)
                .addQueryParameter(PAGER_PATH_PARAMETER, String.valueOf(page))
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(CampaignInnerPhotosResponse.class);
    }

    public static io.reactivex.Observable<CampaignInnerResponse> getCampaignDetails(String token, String campaignID) {
        return Rx2AndroidNetworking.post(CAMPAIGN_DETAILS_URL)
                .addBodyParameter(TOKEN_BODY_PARAMETER, token)
                .addBodyParameter("campaign_id", campaignID)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(CampaignInnerResponse.class);
    }


    public static io.reactivex.Observable<UserProfileResponse> getUserProfile(String token, String userID) {
        return Rx2AndroidNetworking.post(USER_PROFILE_URL)
                .addBodyParameter(TOKEN_BODY_PARAMETER, token)
                .addBodyParameter("photographer_id", userID)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(UserProfileResponse.class);
    }


    public static io.reactivex.Observable<UserPhotosResponse> getUserProfilePhotos( String userID, int page) {
        return Rx2AndroidNetworking.post(USER_PROFILE_PHOTOS)
                .addBodyParameter("photographer_id", userID)
                .addQueryParameter(PAGER_PATH_PARAMETER, String.valueOf(page))
                .getResponseOnlyFromNetwork()
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(UserPhotosResponse.class);

    }
    public static io.reactivex.Observable<FollowUserResponse> unFollowUser(String userID) {
        return Rx2AndroidNetworking.post(PHOTOGRAPHER_UN_FOLLOW_USER_URL)
                .addBodyParameter("photographer_id", userID)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(FollowUserResponse.class);
    }

    public static io.reactivex.Observable<FollowUserResponse> followUser(String userID) {
        return Rx2AndroidNetworking.post(PHOTOGRAPHER_FOLLOW_USER_URL)
                .addBodyParameter("photographer_id", userID)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(FollowUserResponse.class);
    }

    public static Observable<AlbumPreviewResponse> getAlbumDetails(String albumId) {
        return Rx2AndroidNetworking.post(GET_ALBUM_DETAILS)
                .addBodyParameter("album_id", albumId)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(AlbumPreviewResponse.class);
//        .getStringSingle();
    }

    public static io.reactivex.Observable<AlbumPreviewImagesResponse> getAlbumImagesPreview(String albumId, String page) {
        return Rx2AndroidNetworking.post(GET_ALBUM_IMAGES_PREVIEW)
                .addQueryParameter(PAGER_PATH_PARAMETER, page)
                .addBodyParameter("album_id", albumId)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(AlbumPreviewImagesResponse.class);
    }


    public static io.reactivex.Observable<ImageCommentsResponse> getImageComments(String image_id, String page) {
        return Rx2AndroidNetworking.post(GET_IMAGE_COMMENT)
                .addQueryParameter("photo_id", image_id)
                .addQueryParameter(PAGER_PATH_PARAMETER, page)
                .getResponseOnlyFromNetwork()
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(ImageCommentsResponse.class);
    }

    public static io.reactivex.Observable<SubmitImageCommentResponse> submitImageComment(String image_id, String imageComment) {
        return Rx2AndroidNetworking.post(SUBMIT_IMAGE_COMMENT)
                .addQueryParameter("photo_id", image_id)
                .addQueryParameter("comment", imageComment)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(SubmitImageCommentResponse.class);
    }


    public static io.reactivex.Observable<LikeImageResponse> likeImage(String imageId) {
        return Rx2AndroidNetworking.post(LIKE_IMAGE)
                .addBodyParameter("photo_id", imageId)
                .getResponseOnlyFromNetwork()
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(LikeImageResponse.class);
    }

    public static io.reactivex.Observable<LikeImageResponse> unlikeImage(String imageId) {
        return Rx2AndroidNetworking.post(UN_LIKE_IMAGE)
                .addBodyParameter("photo_id", imageId)
                .getResponseOnlyFromNetwork()
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(LikeImageResponse.class);
    }
    public static io.reactivex.Observable<ImageRateResponse> rateImage(String imageId, String rate) {
        return Rx2AndroidNetworking.post(RATE_IMAGE)
                .addBodyParameter("photo_id", imageId)
                .addBodyParameter("rate_value", rate)
                .getResponseOnlyFromNetwork()
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(ImageRateResponse.class);
    }

    public static io.reactivex.Observable<BrandInnerResponse> getBrandInnerData(String token, String brandId) {
        return Rx2AndroidNetworking.post(INNER_BRAND_URL)
                .addBodyParameter(TOKEN_BODY_PARAMETER, token)
                .addBodyParameter("id", brandId)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(BrandInnerResponse.class);
    }

    public static io.reactivex.Observable<FollowCampaignResponse> followCampaign(String campaignID) {
        return Rx2AndroidNetworking.post(FOLLOW_CAMPAIGN_URL)
                .addBodyParameter("campaign_id", campaignID)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(FollowCampaignResponse.class);
    }


    public static io.reactivex.Observable<BusinessProfileResponse> getBrandProfileData() {
        return Rx2AndroidNetworking.post(BRAND_PROFILE_URL)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(BusinessProfileResponse.class);
    }


    public static io.reactivex.Observable<BrandLightBoxResponse> getBrandLightBoxes(String page) {
        return Rx2AndroidNetworking.post(BRAND_LIGHT_BOX_URL)
                .setPriority(Priority.HIGH)
                .addQueryParameter(PAGER_PATH_PARAMETER, page)
                .build()
                .getObjectObservable(BrandLightBoxResponse.class);
    }

    public static io.reactivex.Observable<DeleteLightBoxResponse> deleteLightBox(String id) {
        return Rx2AndroidNetworking.post(DELETE_LIGHT_BOX_URL)
                .setPriority(Priority.HIGH)
                .addBodyParameter("lightbox_id", id)
                .build()
                .getObjectObservable(DeleteLightBoxResponse.class);
    }

    public static io.reactivex.Observable<AddLighBoxResponse> addLightBox(HashMap<String, String> data) {
        return Rx2AndroidNetworking.post(ADD_LIGHT_BOX_URL)
                .setPriority(Priority.HIGH)
                .addBodyParameter(data)
                .build()
                .getObjectObservable(AddLighBoxResponse.class);
    }

    public static io.reactivex.Observable<LightBoxListResponse> getLightBoxes() {
        return Rx2AndroidNetworking.post(GET_LIGHT_BOX_URL)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(LightBoxListResponse.class);
    }


    public static io.reactivex.Observable<AddImageToLightBoxResponse> addImageToLightBox(Map<String, String> data) {
        return Rx2AndroidNetworking.post(ADD_IMG_TO_LIGHT_BOX_URL)
                .setPriority(Priority.HIGH)
                .addBodyParameter(data)
                .build()
                .getObjectObservable(AddImageToLightBoxResponse.class);
    }


    public static io.reactivex.Observable<AddImageToCartResponse> addImageToCart(String imageID) {
        return Rx2AndroidNetworking.post(ADD_IMG_TO_CART_URL)
                .setPriority(Priority.HIGH)
                .addBodyParameter("photo_id",imageID)
                .build()
                .getObjectObservable(AddImageToCartResponse.class);
    }

    public static io.reactivex.Observable<RemoveImageToLightBoxResponse> removeLightBoxImage(String lightBoxId, String imageId) {
        return Rx2AndroidNetworking.post(REMOVE_IMG_TO_LIGHT_BOX_URL)
                .setPriority(Priority.HIGH)
                .addBodyParameter("photo_id", imageId)
                .addBodyParameter("lightbox_id", lightBoxId)
                .build()
                .getObjectObservable(RemoveImageToLightBoxResponse.class);
    }


    public static io.reactivex.Observable<SubmitCampaignResponse> submitCampaign(String token, HashMap<String, String> data, File campaignImg) {
        return Rx2AndroidNetworking.upload(SUBMIT_CAMPAIGN_URL)
                .addHeaders("x-auth-token", token)
                .addHeaders("x-user-type", DEFAULT_USER_TYPE)
                .addHeaders("x-lang-code", "en-us")
                .addMultipartFile("image_cover", campaignImg)
                .addMultipartParameter(data)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(SubmitCampaignResponse.class);
    }

    public static io.reactivex.Observable<UploadProfileImgResponse> uploadUserProfilePhoto(String token, File image) {
        return Rx2AndroidNetworking.upload(UPLOAD_PROFILE_IMG)
                .addHeaders("x-auth-token", token)
                .addHeaders("x-user-type", DEFAULT_USER_TYPE)
                .addHeaders("x-lang-code", "en-us")
                .addMultipartFile("thumbnail", image)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(UploadProfileImgResponse.class);
    }

    public static io.reactivex.Observable<String> uploadBrandDocument(String token, String id, File file, UploadProgressListener progressListener) {
        return Rx2AndroidNetworking.upload(UPLOAD_DOCUMENT_URL)
                .addHeaders("x-auth-token", token)
                .addHeaders("x-user-type", DEFAULT_USER_TYPE)
                .addHeaders("x-lang-code", "en-us")
                .addMultipartFile("file", file)
                .addMultipartParameter("document_type_id", id)
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener(progressListener)
                .getStringObservable();
    }

    public static io.reactivex.Observable<String> setupBrand(String token, SetupBrandModel model) {
        Rx2ANRequest.MultiPartBuilder builder = Rx2AndroidNetworking.upload(SETUP_BRAND_URL);
        if (model.coverChanged)
            builder.addMultipartFile("image_cover", new File(model.cover));
        if (model.thumbnailChanged)
            builder.addMultipartFile("thumbnail", new File(model.thumbnail));
        return builder.addMultipartParameter(model)
                .addHeaders("x-auth-token", token)
                .addHeaders("x-user-type", DEFAULT_USER_TYPE)
                .addHeaders("x-lang-code", "en-us")
                .setPriority(Priority.HIGH)
                .build()
                .getStringObservable();
    }

    public static Observable<DocumentsResponse> getDocumentList() {
        return Rx2AndroidNetworking.post(GET_DOCUMENTS_URL)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(DocumentsResponse.class);
    }

    public static Observable<CartResponse> getCartItems() {
        return Rx2AndroidNetworking.post(GET_CART_ITEMS_URL)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(CartResponse.class);
    }

    public static Observable<RemoveItemResponse> removeCartItem(int id) {
        return Rx2AndroidNetworking.post(REMOVE_FROM_CART_URL)
                .addBodyParameter("photo_id", "" + id)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(RemoveItemResponse.class);
    }


    public static Observable<SocialAutoCompleteResponse> getSocialAutoComplete(String keyword) {
        return Rx2AndroidNetworking.post(SOCIAL_AUTO_COMPLETE)
                .addBodyParameter("keyword",keyword)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(SocialAutoCompleteResponse.class);
    }

    public static Observable<String> updateProfile(String token, AccountDetailsModel model) {
        Rx2ANRequest.MultiPartBuilder builder = Rx2AndroidNetworking.upload(UPDATE_PROFILE_URL)
                .addHeaders("x-auth-token", token)
                .addHeaders("x-user-type", DEFAULT_USER_TYPE)
                .addHeaders("x-lang-code", "en-us");
        if (model.isCoverImageChanged())
            builder.addMultipartFile("image_cover", new File(model.getCoverImage()));
        if (model.isProfileImageChanged())
            builder.addMultipartFile("thumbnail", new File(model.getProfileImage()));
        builder.addMultipartParameter("first_name", model.getFirstName());
        builder.addMultipartParameter("last_name", model.getLastName());
        builder.addMultipartParameter("phone", model.getPhone());
        builder.addMultipartParameter("email", model.getEmail());
        builder.addMultipartParameter("password", model.getPassword());
        return builder
                .setPriority(Priority.HIGH)
                .build()
                .getStringObservable();
    }

    public static Observable<FollowUserResponse> unfollowUser(String userID) {
        return Rx2AndroidNetworking.post(UNFOLLOW_USER_URL)
                .addBodyParameter("photographer_id", userID)
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(FollowUserResponse.class);
    }

    public static Observable<Response> getDownloads(String token) {
        // TODO: edit when API is ready
        return Rx2AndroidNetworking.post(DOWNLOADS_URL)
                .addBodyParameter(TOKEN_BODY_PARAMETER, token)
                .addBodyParameter("campaign_id", "269")
                .addQueryParameter(PAGER_PATH_PARAMETER, String.valueOf(0))
                .setPriority(Priority.HIGH)
                .build()
                .getObjectObservable(Response.class);
    }

    public static Observable<String> forgotPassword(String email) {
        return Rx2AndroidNetworking.post(FORGOT_PASSWORD_URL)
                .setPriority(Priority.HIGH)
                .addBodyParameter("email", email)
                .build()
                .getStringObservable();
    }

    public static Observable<LightboxPhotosResponse> getLigtboxPhotos(int lightBoxId, int page) {
        return Rx2AndroidNetworking.post(LIGHT_BOX_PHOTOS_URL)
                .setPriority(Priority.HIGH)
                .addBodyParameter("lightbox_id", String.valueOf(lightBoxId))
                .addQueryParameter("page", String.valueOf(page))
                .build()
                .getObjectObservable(LightboxPhotosResponse.class);
    }

    public static Observable<ChangeCampaignDateResponse> changeCampaignEndDate(Integer id, String dateString) {
        return Rx2AndroidNetworking.post(CHANGE_CAMPAIGN_END_DATE_URL)
                .setPriority(Priority.HIGH)
                .addBodyParameter("campaign_id", id.toString())
                .addBodyParameter("end_date", dateString)
                .build()
                .getObjectObservable(ChangeCampaignDateResponse.class);
    }

    public static Single<RequestVerificationResponse> requestVerificationBrand() {
        return Rx2AndroidNetworking.post(REQUEST_VERIFICATION_BRAND_URL)
                .setPriority(Priority.HIGH)
                .build()
//                .getStringObservable();
        .getObjectSingle(RequestVerificationResponse.class);
    }

//    public static io.reactivex.Observable<GeoCodeAutoCompleteResponse> getGeoGodeAutoCompleteResponse(String key){
//        return Rx2AndroidNetworking.get()
//
//    }
//

//    public static io.reactivex.Observable<BaseResponse> makeGetRequest(String lang, String key) {
//        return Rx2AndroidNetworking.get(REQUEST_URL)
//                .addPathParameter("lang", lang)
//                .addQueryParameter("key", String.valueOf(key))
//                .getResponseOnlyFromNetwork()
//                .build()
//                .getObjectObservable(BaseResponse.class);
//    }

//    /**
//     * --Upload Attachment
//     *
//     * @param file -->File src
//     * @message -->Request parameter you can add multible parameter to request  body along with uploaded attachment
//     */
//    public static void complaint(String message, File file, final RequestCallBack requestCallBack) {
//        AndroidNetworking.upload(REQUEST_URL)
//                .addHeaders("Content-Type", "multipart/form-data")
//                .addMultipartParameter("message", message)
//                .addMultipartFile("files[]", file) //todo "files[]" is A key According to back End
//                .setPriority(Priority.HIGH)
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            boolean success = response.getBoolean("success");
//                            if (success) {
//                                requestCallBack.OnSuccsess();
//                            } else {
//                                requestCallBack.onFailer();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            requestCallBack.onFailer();
//                        }
//                    }
//
//                    @Override
//                    public void onError(ANError anError) {
//                        requestCallBack.onFailer();
//                    }
//                });
//    }
//

}
