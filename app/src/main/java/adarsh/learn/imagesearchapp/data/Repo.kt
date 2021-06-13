package adarsh.learn.imagesearchapp.data

import adarsh.learn.imagesearchapp.network.ApiClient
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repo @Inject constructor(private val apiClient: ApiClient) {
    fun getSearchResults(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100, // at what number recyclerview will drop items
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GalleryPagingSource(apiClient,query)}
        ).liveData
}