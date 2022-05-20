package com.example.catapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.example.catapps.api.ApiService
import com.example.catapps.model.ImageResultData
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {
    private val agentBreedView: TextView by lazy { findViewById(R.id.main_agent_breed_value) }
    private val profileImageView: ImageView by lazy { findViewById(R.id.main_profile_image) }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/v1/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    private val theCatApiService by lazy { retrofit.create(ApiService::class.java) }

    private val imageLoader: ImageLoader by lazy { GlidImageLoader(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getCAtImageResponse()
    }

    private fun getCAtImageResponse(){
        val call = theCatApiService.searchImages(1,"full")
        call.enqueue(object :Callback<List<ImageResultData>>{
            override fun onFailure(call: Call<List<ImageResultData>>, t:Throwable){
                Log.e("MainActivity","Failed to grt search results",t)
            }
            override fun onResponse(
                call: Call<List<ImageResultData>>,
                response: Response<List<ImageResultData>>
            ){
                if (response.isSuccessful){
                    val imageResult = response.body()
                    val firstImageUrl = imageResult?.firstOrNull()?.imageUrl ?: ""
                    if (firstImageUrl.isNotBlank()){
                        imageLoader.loadImage(firstImageUrl,profileImageView)
                    } else{
                        Log.d("MainActivity","Missing image URL")
                    }
                    agentBreedView.text =
                        imageResult?.firstOrNull()?.breds?.firstOrNull()?.name ?: "Unknown"
                }
                else{
                    Log.e(
                        "MainActivity",
                        "Failed to get search results\n${response.errorBody()?.string() ?: ""}"

                    )
                }
            }}
        )
    }
}