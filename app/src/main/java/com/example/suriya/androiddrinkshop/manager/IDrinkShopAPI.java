package com.example.suriya.androiddrinkshop.manager;

import com.example.suriya.androiddrinkshop.model.BannerModel;
import com.example.suriya.androiddrinkshop.model.CategoryModel;
import com.example.suriya.androiddrinkshop.model.CheckUserResponse;
import com.example.suriya.androiddrinkshop.model.DrinkModel;
import com.example.suriya.androiddrinkshop.model.UserModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IDrinkShopAPI {

    @FormUrlEncoded
    @POST("checkuser.php")
    Call<CheckUserResponse> checkUserExists(@Field("phone") String phone);

    @FormUrlEncoded
    @POST("register.php")
    Call<UserModel> registerNewUser(@Field("phone") String phone,
                                    @Field("name") String name,
                                    @Field("address") String address,
                                    @Field("birthdate") String birthdate);

    @FormUrlEncoded
    @POST("getDrink.php")
    Call<DrinkModel> getDrink(@Field("menuId") String menuId);

    @FormUrlEncoded
    @POST("getuser.php")
    Call<UserModel> getUserInformation(@Field("phone") String phone);

    @GET("getbanner.php")
    Observable<List<BannerModel>> getBanner();

    @GET("getMenu.php")
    Observable<List<CategoryModel>> getCategory();

}
