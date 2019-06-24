package controller.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonParser {

    public static String getJsonFromObject(Object object) {
        Gson gson = new GsonBuilder().create();

        return gson.toJson(object);
    }

    public static <T> T getObjectFromJson(String json, Class<T> classOfT) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(json, classOfT);
    }
}
