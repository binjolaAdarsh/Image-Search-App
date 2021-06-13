package adarsh.learn.imagesearchapp.network

import adarsh.learn.imagesearchapp.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiClient {

    companion object{
        const val ACCESS_KEY = BuildConfig.UNSPLASH_ACCESS_KEY
        const val BASE_URL = "https://api.unsplash.com/"
    }
    @Headers("Accept-Version: v1","Authorization: Client-ID $ACCESS_KEY")
    @GET("search/photos")
    suspend fun  searchPhotos(
        @Query("query") query:String,
        @Query("page") page:Int,
        @Query("per_page") perPage:Int,
    ):PhotoResponse
}