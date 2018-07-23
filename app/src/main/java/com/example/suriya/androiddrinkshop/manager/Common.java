package com.example.suriya.androiddrinkshop.manager;

import com.example.suriya.androiddrinkshop.model.CategoryModel;
import com.example.suriya.androiddrinkshop.model.UserModel;

public class Common {
    private static final String BASE_URL = "http://10.0.2.2/drinkshop/";

    public static UserModel cerrentUserModel = null;
    public static CategoryModel cerrentCategoryModel = null;

    public static IDrinkShopAPI getAPI(){
        return RetrofitClient.getClient(BASE_URL).create(IDrinkShopAPI.class);
    }
}
