package adarsh.learn.imagesearchapp.di

import adarsh.learn.imagesearchapp.ui.gallery.PhotoAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object GalleryModule {

    @Provides
    fun providePhotoAdapter() = PhotoAdapter()
}