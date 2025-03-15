package com.vn.whosthatdog.services

import com.vn.whosthatdog.models.BreedImagesModel
import com.vn.whosthatdog.models.BreedsListModel
import com.vn.whosthatdog.models.RandomImageModel
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {
    @GET("breeds/image/random")
    suspend fun getRandomImage(): RandomImageModel

    @GET("breeds/list/all")
    suspend fun getBreedsList(): BreedsListModel

    @GET("breed/{breed}/images")
    suspend fun getImagesByBreed(@Path("breed") breed: String): BreedImagesModel

    @GET("breed/{breed}/{subBreed}/images")
    suspend fun getImagesBySubBreed(
        @Path("breed") breed: String,
        @Path("subBreed") subBreed: String
    ): BreedImagesModel
}