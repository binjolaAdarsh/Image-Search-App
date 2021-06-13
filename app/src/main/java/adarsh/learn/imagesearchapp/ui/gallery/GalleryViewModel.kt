package adarsh.learn.imagesearchapp.ui.gallery

import adarsh.learn.imagesearchapp.data.Repo
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn

class GalleryViewModel @ViewModelInject constructor(private val repo: Repo) : ViewModel() {

    private val imageQuery = MutableLiveData(DEFAULT_IMAGE_QUERY)

    // when ever the value of imageQuery livedata changes this switchMap block gets executes
    val photos = imageQuery.switchMap { searchString->
        repo.getSearchResults(searchString).cachedIn(viewModelScope)
    }

    fun searchImages(query:String){
        imageQuery.value =query
    }

    companion object{
        private const val DEFAULT_IMAGE_QUERY ="dog"
    }
}