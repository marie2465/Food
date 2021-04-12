package Util;

import com.estimote.sdk.repackaged.okhttp_v2_2_0.com.squareup.okhttp.ResponseBody;

import java.util.ArrayList;

import Models.MyResponse;
import Models.Restourants;
import Models.Tellers;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by 208-it-01 on 28.03.2018.
 */

public interface APIService
{
    @GET("all-rest.php")
    Call<Restourants> getRestourants();

    @GET("restimages.php")
    Call<ArrayList<String>> getImages(@Query("id") int id);

    @GET("teller.php")
    Call<Tellers> getTellers(@Query("id") int id);

    @Multipart
    @POST("Api.php?apicall=upload")
    Call<MyResponse> uploadImage(@Part("file") RequestBody file, @Part("desc") RequestBody desc, @Query("id") int id);

}


