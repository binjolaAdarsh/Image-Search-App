package adarsh.learn.imagesearchapp.data

import adarsh.learn.imagesearchapp.network.ApiClient
import androidx.paging.PagingSource
import retrofit2.HttpException
import java.io.IOException

private const val DEFAULT_PAGE_INDEX = 1

class GalleryPagingSource(private val apiClient: ApiClient, private val query: String) :
    PagingSource<Int, Photo>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        //page we are currently in
        // params.key will be null for the very first time we load so in that case we need to tell which page we need to load
        val position = params.key ?: DEFAULT_PAGE_INDEX

        return try {
            // api call
            // params.loadSize is the total pages we want to load per call
            val response = apiClient.searchPhotos(query, position, params.loadSize)
            val photos = response.results
            val reachedEnd = photos.isEmpty()
            LoadResult.Page(
                data = photos,
                prevKey = if (position == DEFAULT_PAGE_INDEX) null else position - 1,
                nextKey = if (reachedEnd) null else position + 1
            )
        } catch (e: IOException) { // when there is no internet connection
            LoadResult.Error(e)
        } catch (e: HttpException) { // api errors  ,or there is no data
            LoadResult.Error(e)
        }
    }
}