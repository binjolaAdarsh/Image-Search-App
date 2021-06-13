package adarsh.learn.imagesearchapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Photo (
    val id: String,
    val description: String?,
    val urls: UnsplashPhotoUrls,
    val user: UnsplashUser
) : Parcelable {
    @Parcelize
    data class UnsplashPhotoUrls(
        val raw: String,
        val full: String,
        val regular: String,
        val small: String,
        val thumb: String,
    ) : Parcelable

    @Parcelize
    data class UnsplashUser(
        val name: String,
        val username: String
    ) : Parcelable {
        private val appName= "Image Search App"
        val attributionUrl get() = "https://unsplash.com/$username?utm_source=$appName&utm_medium=referral"
    }
}