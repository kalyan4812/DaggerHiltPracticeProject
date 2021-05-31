package com.saikalyandaroju.daggerhilt.source.local;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.saikalyandaroju.daggerhilt.source.Network.models.Blog;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Convertors {


    static Gson gson = new Gson();
    static Type type = new TypeToken<Blog>() {
    }.getType();

    @TypeConverter
    public static Blog getBlogList(String value) {
        return gson.fromJson(value, type);
    }

    @TypeConverter
    public static String getBlogString(Blog list) {
        return gson.toJson(list, type);
    }


}
