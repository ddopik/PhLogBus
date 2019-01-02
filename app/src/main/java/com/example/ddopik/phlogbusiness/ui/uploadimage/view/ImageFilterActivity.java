package com.example.ddopik.phlogbusiness.ui.uploadimage.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.ddopik.phlogbusiness.R;
import com.example.ddopik.phlogbusiness.utiltes.GlideApp;
import com.example.ddopik.phlogbusiness.base.BaseActivity;
import com.example.ddopik.phlogbusiness.ui.uploadimage.view.fillters.FiltersListFragment;
import com.example.ddopik.phlogbusiness.ui.uploadimage.view.fillters.PickImageViewPagerAdapter;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubfilter;

import static com.example.ddopik.phlogbusiness.utiltes.BitmapUtils.getBitmapFromGallery;


/**
 * Created by abdalla_maged on 10/18/2018.
 */
public class ImageFilterActivity extends BaseActivity implements FiltersListFragment.FiltersListFragmentListener, EditPickedImageFragment.EditImageFragmentListener {

    private static final String TAG = ImageFilterActivity.class.getSimpleName();
    public static String ImageFilter="image_uri";


    private ImageView imagePreview;
    private ImageButton applyFilterBtn, closeFilterBtn;
    private Bitmap originalImage;
    private Bitmap filteredImage; //Image get filtered from filter list
    // the final image after applying
    // brightness, saturation, contrast
    private Bitmap finalImage;
    private FiltersListFragment filtersListFragment;
    private EditPickedImageFragment editImageFragment;

    // modified image values
    private int brightnessFinal = 0;
    private float saturationFinal = 1.0f;
    private float contrastFinal = 1.0f;

    private  String filteredImagePath;


    // load native image filters library
    static {
        System.loadLibrary("NativeImageProcessor");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_filter);
        initView();
        initListeners();


    }


    @Override
    public void initView() {
//        RequestPermutations(); //todo back to GalleryImageActivity
        imagePreview = findViewById(R.id.filtered_image_preview);
        applyFilterBtn = findViewById(R.id.btn_apply_filter);
        closeFilterBtn = findViewById(R.id.btn_close_filter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        ViewPager viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        if (getIntent().getStringExtra(ImageFilter) != null) {
            filteredImagePath=getIntent().getStringExtra(ImageFilter);
            loadImage(filteredImagePath);
        }
    }


    @Override
    public void initPresenter() {


    }


    private void initListeners() {
        closeFilterBtn.setOnClickListener(v -> onBackPressed()
        );

        applyFilterBtn.setOnClickListener(v -> {
            if (filteredImagePath !=null){
                Intent intent=new Intent(this,PickedPhotoInfoActivity.class);
                String s=filteredImagePath;
                intent.putExtra(PickedPhotoInfoActivity.FILTRED_IIMG, filteredImagePath);
                startActivity(intent);
            }


        });


//        openCameraBtn.setOnClickListener(view -> {
//            ImagePicker.cameraOnly().start(this);
////            openPickerDialog();
//        }); //todo back to GalleryImageActivity


    }

    private void setupViewPager(ViewPager viewPager) {
        PickImageViewPagerAdapter adapter = new PickImageViewPagerAdapter(getSupportFragmentManager());

        // adding filter list fragment
        filtersListFragment = FiltersListFragment.getInstance(this);
        filtersListFragment.setListener(this);

        // adding edit image fragment
        editImageFragment = new EditPickedImageFragment();
        editImageFragment.setListener(this);

        adapter.addFragment(filtersListFragment, getString(R.string.tab_filters));
        adapter.addFragment(editImageFragment, getString(R.string.tab_edit));
        viewPager.setAdapter(adapter);
    }

    /**
     * Resets image edit controls to normal when new filter
     * is selected
     */
    private void resetControls() {
        if (editImageFragment != null) {
            editImageFragment.resetControls();
        }
        brightnessFinal = 0;
        saturationFinal = 1.0f;
        contrastFinal = 1.0f;
    }

    private void loadImage(String img) {

        originalImage = getBitmapFromGallery(getBaseContext(), img, 300, 300);
        filteredImage = originalImage.copy(Bitmap.Config.ARGB_8888, true);
        finalImage = originalImage.copy(Bitmap.Config.ARGB_8888, true);
        imagePreview.setImageBitmap(originalImage);

        Uri imgPath = Uri.parse(img);
        //Header Img
        GlideApp.with(getBaseContext())
                .load(imgPath.getPath())
                .error(R.drawable.ic_launcher_foreground)
                .override(LinearLayout.LayoutParams.MATCH_PARENT
                        , LinearLayout.LayoutParams.MATCH_PARENT)
                .into(imagePreview);

        Bitmap bitmap = getBitmapFromGallery(getBaseContext(), img, 800, 800);
        originalImage.recycle();
        finalImage.recycle();
        originalImage = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        filteredImage = originalImage.copy(Bitmap.Config.ARGB_8888, true);
        finalImage = originalImage.copy(Bitmap.Config.ARGB_8888, true);
        imagePreview.setImageBitmap(originalImage);
        bitmap.recycle();

        filtersListFragment.prepareThumbnail(originalImage, imgPath.getPath());

    }


    @Override
    public void onBrightnessChanged(int brightness) {
        brightnessFinal = brightness;
        Filter myFilter = new Filter();
        myFilter.addSubFilter(new BrightnessSubFilter(brightness));
        imagePreview.setImageBitmap(myFilter.processFilter(finalImage.copy(Bitmap.Config.ARGB_8888, true)));

    }

    @Override
    public void onSaturationChanged(float saturation) {
        saturationFinal = saturation;
        Filter myFilter = new Filter();
        myFilter.addSubFilter(new SaturationSubfilter(saturation));
        imagePreview.setImageBitmap(myFilter.processFilter(finalImage.copy(Bitmap.Config.ARGB_8888, true)));
    }

    @Override
    public void onContrastChanged(float contrast) {
        contrastFinal = contrast;
        Filter myFilter = new Filter();
        myFilter.addSubFilter(new ContrastSubFilter(contrast));
        imagePreview.setImageBitmap(myFilter.processFilter(finalImage.copy(Bitmap.Config.ARGB_8888, true)));
    }

    @Override
    public void onEditStarted() {

    }

    @Override
    public void onEditCompleted() {
// once the editing is done i.e seekbar is drag is completed,
// apply the values on to filtered image
        final Bitmap bitmap = filteredImage.copy(Bitmap.Config.ARGB_8888, true);
        Filter myFilter = new Filter();
        myFilter.addSubFilter(new BrightnessSubFilter(brightnessFinal));
        myFilter.addSubFilter(new ContrastSubFilter(contrastFinal));
        myFilter.addSubFilter(new SaturationSubfilter(saturationFinal));
        finalImage = myFilter.processFilter(bitmap);
    }

    @Override
    public void onFilterSelected(Filter filter) {
        // reset image controls
        resetControls();
        // applying the selected filter
        filteredImage = originalImage.copy(Bitmap.Config.ARGB_8888, true);
        // preview filtered image
        imagePreview.setImageBitmap(filter.processFilter(filteredImage));

        finalImage = filteredImage.copy(Bitmap.Config.ARGB_8888, true);
    }


    @Override
    public void showToast(String msg) {
        super.showToast(msg);
    }


}
