package com.example.ddopik.phlogbusiness.ui.uploadimage.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.Utiltes.GlideApp;
import com.example.ddopik.phlogbusiness.Utiltes.MapUtls;
import com.example.ddopik.phlogbusiness.base.BaseActivity;
import com.example.ddopik.phlogbusiness.ui.uploadimage.view.adapter.PlaceArrayAdapter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import io.reactivex.annotations.NonNull;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.example.ddopik.phlogbusiness.Utiltes.Constants.REQUEST_CODE_LOCATION;


/**
 * Created by abdalla_maged on 10/24/2018.
 */
public class PickedPhotoInfoActivity extends BaseActivity implements MapUtls.OnLocationUpdate,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    public static String FILTRED_IIMG = "filtered_image_path";
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private final String TAG = PickedPhotoInfoActivity.class.getSimpleName();
    private String imagePath;
    private ImageView filtredImg;
    private AutoCompleteTextView placesAutoCompete;
    private Button backBtn, nextBtn;
    private ImageButton locateMeBtn;
    private EditText caption;
    private MapUtls mapUtls;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getIntent().getStringExtra(FILTRED_IIMG) != null) {
            setContentView(R.layout.activity_photo_info_activity);
            imagePath = getIntent().getStringExtra(FILTRED_IIMG);
            initPresenter();
            initView();
            initListener();
        }
    }


    @Override
    public void initView() {


        mapUtls = new MapUtls(this);

        mGoogleApiClient = new GoogleApiClient.Builder(PickedPhotoInfoActivity.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();
        placesAutoCompete = findViewById(R.id.auto_complete_places);
        placesAutoCompete.setThreshold(3);


        filtredImg = findViewById(R.id.photo_info);
        GlideApp.with(getBaseContext())
                .load(Uri.parse(imagePath).getPath())
                .centerCrop()
                .error(R.drawable.ic_launcher_foreground)
                .into(filtredImg);

        nextBtn = findViewById(R.id.activity_info_photo_next_btn);
        backBtn = findViewById(R.id.activity_info_photo_back_btn);

        locateMeBtn = findViewById(R.id.locate_me_btn);

        caption = findViewById(R.id.photo_caption);
        placesAutoCompete.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1,
                null, null);
        placesAutoCompete.setAdapter(mPlaceArrayAdapter);

    }

    @Override
    public void initPresenter() {
    }

    private void initListener() {
        nextBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddTagActivity.class);
            intent.putExtra(AddTagActivity.IMAGE_PREVIEW, imagePath);
            startActivity(intent);
        });
        backBtn.setOnClickListener(v -> onBackPressed());

        locateMeBtn.setOnClickListener((view) -> requestLocation());
    }

    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }


    @SuppressLint("MissingPermission")
    @AfterPermissionGranted(REQUEST_CODE_LOCATION)
    private void requestLocation() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            mapUtls.startLocationUpdates(this,MapUtls.MapConst.UPDATE_INTERVAL_INSTANT);
        } else {
            // Request one permission
            EasyPermissions.requestPermissions(this, getString(R.string.need_location_permation),
                    REQUEST_CODE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }


    @Override
    public void onLocationUpdate(Location location) {
        // New location has now been determined
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mapUtls.getAddressFromLocation(latLng.latitude, latLng.longitude, this, new GeocodeHandler());
        mapUtls.removeLocationRequest();
        hideSoftKeyBoard();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }






    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i(TAG, "Selected: " + item.description);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            Log.i(TAG, "Fetching details for ID: " + item.placeId);

        }
    };

    /// this method parsing and fetching placesObj
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = places -> {
        if (!places.getStatus().isSuccess()) {
            Log.e(TAG, "Place query did not complete. Error: " +
                    places.getStatus().toString());
            return;
        }
        // Selecting the first object buffer.
        final Place place = places.get(0);
        placesAutoCompete.setText(place.getAddress());
        hideSoftKeyBoard();
//        CharSequence attributions = places.getAttributions();
//        mNameTextView.setText(Html.fromHtml(place.getName() + ""));
//        mAddressTextView.setText(Html.fromHtml(place.getAddress() + ""));
//        Toast.makeText(this, place.getName(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, place.getAddress(), Toast.LENGTH_SHORT).show();
//        mIdTextView.setText(Html.fromHtml(place.getId() + ""));
//        mPhoneTextView.setText(Html.fromHtml(place.getPhoneNumber() + ""));
//        mWebTextView.setText(place.getWebsiteUri() + "");
//        if (attributions != null) {
////            mAttTextView.setText(Html.fromHtml(attributions.toString()));
//        }
    };
    private void hideSoftKeyBoard() {
        placesAutoCompete.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if(imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
    @Override
    public void onConnected(Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.e(TAG, "Google Places API connected.");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

//        Toast.makeText(this,
//                "Google Places API connection failed with error code:" + connectionResult.getErrorCode(),
//                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
        Log.e(TAG, "Google Places API connection suspended.");
    }




    public class GeocodeHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    placesAutoCompete.setText(locationAddress);
                    break;
                default:
                    locationAddress = null;
            }
            Log.e("location Address=", locationAddress);
        }
    }



    private void processed(){

    }
    protected void onDestroy() {
        super.onDestroy();

    }
}
