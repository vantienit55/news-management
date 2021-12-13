package com.group22.news_management.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.group22.news_management.model.Categories;
import com.group22.news_management.model.CategoryModel;
import com.group22.news_management.model.CommentModel;
import com.group22.news_management.model.NewsModel;
import com.group22.news_management.model.UserModel;
import java.util.List;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NewsManagementAPI {

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    NewsManagementAPI newsManagementAPI = new Retrofit.Builder()
                            .baseUrl("http://192.168.187.1:8080/")
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build()
                            .create(NewsManagementAPI.class);

    @POST("api/signin")
    Call<UserModel> callLoginApi(@Query("username") String username, @Query("password") String password);

    @POST("api/signup")
    Call<UserModel> callSignUpApi(@Query("username") String username, @Query("password") String password);

    @GET("api/categories")
    Call<Categories> callCategoryListApi();

    @Headers("Content-Type: application/json")
    @POST("api/news")
    Call<NewsModel> callSaveNewsApi(@Body NewsModel news);

    @GET("api/categories/getByCode")
    Call<CategoryModel> callGetCategoryByCodeApi(@Query("code") String code);

    @Headers("Content-Type: application/json")
    @POST("api/comment")
    Call<CommentModel> callSaveCommentApi(@Body CommentModel commentModel);

    @GET("api/comment")
    Call<List<CommentModel>> callGetCommentByNewsIdApi(@Query("newsId") long newsId);

}
