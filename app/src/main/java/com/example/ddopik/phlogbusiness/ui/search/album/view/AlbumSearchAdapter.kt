package com.example.softmills.phlog.ui.search.view.album.view

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.ddopik.phlogbusiness.R
import com.example.ddopik.phlogbusiness.utiltes.GlideApp
import com.example.softmills.phlog.ui.search.view.album.model.AlbumSearch

/**
 * Created by abdalla_maged on 11/6/2018.
 */
class AlbumSearchAdapter(private var albumSearchList: List<AlbumSearch>?) : RecyclerView.Adapter<AlbumSearchAdapter.AlbumSearchViewHolder>() {
    private var context: Context? = null
    var onAlbumPreview: OnAlbumPreview? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): AlbumSearchViewHolder {
        this.context = viewGroup.context
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.view_holder_album_search_2, viewGroup, false)
        return AlbumSearchViewHolder(view)
    }

    override fun onBindViewHolder(albumSearchViewHolder: AlbumSearchViewHolder, i: Int) {


        takeIf { albumSearchList?.get(i)?.photos?.getOrNull(2) != null }?.apply {
            albumSearchViewHolder.multiImageContainer.visibility = View.VISIBLE
            albumSearchViewHolder.singleImageContainer.visibility = View.GONE
            albumSearchList?.get(i)?.photos?.getOrNull(0).let {
                GlideApp.with(context!!)
                        .load(it?.url)
                        .placeholder(R.drawable.default_place_holder)
                        .error(R.drawable.default_error_img)
                        .into(albumSearchViewHolder.image1)
            }
            albumSearchList?.get(i)?.photos?.getOrNull(1).let {

                GlideApp.with(context!!)
                        .load(it?.url)
                        .placeholder(R.drawable.default_place_holder)
                        .error(R.drawable.default_error_img)
                        .into(albumSearchViewHolder.image2)
            }
            albumSearchList?.get(i)?.photos?.getOrNull(2).let {

                GlideApp.with(context!!)
                        .load(it?.url)
                        .placeholder(R.drawable.default_place_holder)
                        .error(R.drawable.default_error_img)
                        .into(albumSearchViewHolder.image3)
            }
        } ?: run {
            albumSearchViewHolder.singleImageContainer.visibility = View.VISIBLE
            albumSearchViewHolder.multiImageContainer.visibility = View.GONE
            albumSearchList?.get(i)?.photos?.getOrNull(0).let {
                GlideApp.with(context!!)
                        .load(it?.url)
                        .placeholder(R.drawable.default_place_holder)
                        .error(R.drawable.default_error_img)
                        .into(albumSearchViewHolder.imageSingle)
            }
        }
        albumSearchList?.get(i)?.name?.let {
            albumSearchViewHolder.albumName.text = it
        }
        albumSearchList?.get(i)?.photosCount?.let {
            albumSearchViewHolder.albumPhotoCount.text = it.toString()+" "+context?.resources?.getString(R.string.photos)
        }


        if (onAlbumPreview != null) {
            albumSearchViewHolder.albumSearchListItemContainer.setOnClickListener { onAlbumPreview!!.openAlbumPreviewListener(albumSearchList?.get(i)!!) }
        }

    }

    override fun getItemCount(): Int {
        return albumSearchList?.size!!
    }

    inner class AlbumSearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var albumSearchListItemContainer: ConstraintLayout

        var openAlbumPreview: ImageButton
        var imageSingle: ImageView
        var image1: ImageView
        var image2: ImageView
        var image3: ImageView
        var albumName: TextView
        var albumPhotoCount: TextView
        var singleImageContainer: FrameLayout
        var multiImageContainer: ConstraintLayout

        init {
            albumSearchListItemContainer = view.findViewById(R.id.album_search_list_item_container)
            singleImageContainer = view.findViewById(R.id.single_image_container)
            multiImageContainer = view.findViewById(R.id.multi_image_container)
            openAlbumPreview = view.findViewById(R.id.open_album_preview)
            imageSingle = view.findViewById(R.id.album_search_img_single)
            image1 = view.findViewById(R.id.album_search_img_1)
            image2 = view.findViewById(R.id.album_search_img_2)
            image3 = view.findViewById(R.id.album_search_img_3)

            albumName = view.findViewById(R.id.album_name)
            albumPhotoCount = view.findViewById(R.id.album_photo_count)

        }
    }

    interface OnAlbumPreview {
        fun openAlbumPreviewListener(albumSearch: AlbumSearch)
    }
}
