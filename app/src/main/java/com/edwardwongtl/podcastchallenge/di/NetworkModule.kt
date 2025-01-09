package com.edwardwongtl.podcastchallenge.di

import com.edwardwongtl.podcastchallenge.BuildConfig
import com.edwardwongtl.podcastchallenge.data.api.BASE_URL
import com.edwardwongtl.podcastchallenge.data.api.PodcastApi
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .apply {
            if (BuildConfig.DEBUG)
                addInterceptor(
                    HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY)
                )
        }
        .build()

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        // Add call adapter to convert Retrofit response to Kotlin Result, default on IO scope
        .addCallAdapterFactory(ResultCallAdapterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    @Provides
    fun providePodcastApi(retrofit: Retrofit): PodcastApi = retrofit.create(PodcastApi::class.java)

    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().build()
}