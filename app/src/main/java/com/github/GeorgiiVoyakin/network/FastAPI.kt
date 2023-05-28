package com.github.GeorgiiVoyakin.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface FastAPI {
    @FormUrlEncoded
    @POST("/token")
    fun loginForAccessToken(
        @Field("username")
        username: String,
        @Field("password")
        password: String
    ): Call<Token>

    @GET("/users/me")
    fun readUsersMe(@Header("Authorization") accessToken: String): Call<User>

//    @GET("/users/me/images/")
//    fun readOwnImages(): Call<List<Image>>

//    @GET("/users/")
//    fun readUsers(
//        @Query("skip") skip: Int = 0,
//        @Query("limit") limit: Int = 100
//    ): Call<Response_Read_Users_Users_Get>

    @POST("/users/")
    fun createUser(@Body userCreate: UserCreate): Call<User>
//
//    @GET("/users/{user_id}")
//    fun readUser(@Path("user_id") userId: Int): Call<User>

//    @POST("/users/{user_id}/images/")
//    fun createImageForUser(
//        @Path("user_id") userId: Int,
//        @Body requestBody: Body_create_image_for_user_users__user_id__images__post
//    ): Call<Image>

//    @GET("/images/")
//    fun readImages(
//        @Query("skip") skip: Int = 0,
//        @Query("limit") limit: Int = 100
//    ): Call<Response_Read_Images_Images_Get>

//    @POST("/images/{image_id}/objects")
//    fun addObjectToImage(
//        @Path("image_id") imageId: Int,
//        @Query("object") objectName: String
//    ): Call<ImageObject>

//    @GET("/images/{object}")
//    fun readImagesByObject(
//        @Path("object") objectName: String,
//        @Query("skip") skip: Int = 0,
//        @Query("limit") limit: Int = 100
//    ): Call<Response_Read_Images_By_Object_Images_Object_Get>

//    @GET("/users/me/images/{object}")
//    fun readOwnImagesByObject(
//        @Path("object") objectName: String,
//        @Query("user_id") userId: Int,
//        @Query("skip") skip: Int = 0,
//        @Query("limit") limit: Int = 100
//    ): Call<Response_Read_Own_Images_By_Object_Users_Me_Images_Object_Get>

//    @GET("/images/albums/")
//    fun readAlbums(
//        @Query("skip") skip: Int = 0,
//        @Query("limit") limit: Int = 100
//    ): Call<Response_Read_Albums_Images_Albums_Get>
//
//    @GET("/users/me/images/albums/")
//    fun readOwnAlbums(
//        @Query("user_id") userId: Int,
//        @Query("skip") skip: Int = 0,
//        @Query("limit") limit: Int = 100
//    ): Call<Response_Read_Own_Albums_Users_Me_Images_Albums_Get>
//
//    @POST("/users/{user_id}/images/albums/")
//    fun createAlbumForUser(
//        @Path("user_id") userId: Int,
//        @Body albumCreate: AlbumCreate
//    ): Call<Album>

//    @POST("/users/{user_id}/images/albums/{album_id}")
//    fun addImageToAlbum(
//        @Path("user_id") userId: Int,
//        @Path("album_id") albumId: Int,
//        @Query("image_id") imageId: Int
//    ): Call<Album>
//
//    @GET("/users/me/images/favorites/")
//    fun readOwnFavoriteImages(
//        @Query("user_id") userId: Int,
//        @Query("skip") skip: Int = 0,
//        @Query("limit") limit: Int = 100
//    ): Call<Response_Read_Own_Favorite_Images_Users_Me_Images_Favorites_Get>
//
//    @POST("/users/{user_id}/images/{image_id}/favorites")
//    fun addImageToFavorites(
//        @Path("user_id") userId: Int,
//        @Path("image_id") imageId: Int
//    ): Call<ApiResponse>
//
//    @DELETE("/users/{user_id}/images/{image_id}/favorites")
//    fun removeImageFromFavorites(
//        @Path("user_id") userId: Int,
//        @Path("image_id") imageId: Int
//    ): Call<ApiResponse>
}