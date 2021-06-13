package adarsh.learn.imagesearchapp.ui.gallery

import adarsh.learn.imagesearchapp.R
import adarsh.learn.imagesearchapp.data.Photo
import adarsh.learn.imagesearchapp.databinding.ItemImagesBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

class PhotoAdapter : PagingDataAdapter<Photo, PhotoAdapter.PhotoViewHolder>(PHOTO_DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val inflater = ItemImagesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = getItem(position)
        if (photo != null) {
            holder.bind(photo)
        }
    }

    class PhotoViewHolder(private val binding: ItemImagesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo) {
            binding.apply {
                Glide.with(itemView).setDefaultRequestOptions(RequestOptions.diskCacheStrategyOf(
                    DiskCacheStrategy.RESOURCE)).load(photo.urls.regular).centerCrop().transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_baseline_error_outline_24)
                    .into(imageView)

                textViewUserName.text = photo.user.username
            }

        }

    }


    companion object {
        private val PHOTO_DIFF_UTIL = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo) =
                oldItem == newItem


        }
    }

}