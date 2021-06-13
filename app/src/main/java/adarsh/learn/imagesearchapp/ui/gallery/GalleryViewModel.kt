package adarsh.learn.imagesearchapp.ui.gallery

import adarsh.learn.imagesearchapp.data.Repo
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn

class GalleryViewModel @ViewModelInject constructor(private val repo: Repo,
                                                    @Assisted state:SavedStateHandle // for handling the process death
) : ViewModel() {

    private val imageQuery =state.getLiveData(CURRENT_QUERY, DEFAULT_IMAGE_QUERY)

    // when ever the value of imageQuery livedata changes this switchMap block gets executes
    val photos = imageQuery.switchMap { searchString->
        repo.getSearchResults(searchString).cachedIn(viewModelScope)
    }

    fun searchImages(query:String){
        imageQuery.value =query
    }

    companion object{
        private const val CURRENT_QUERY ="current_query"
        private const val DEFAULT_IMAGE_QUERY ="dog"
    }
}