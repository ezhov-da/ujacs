package ru.ezhov.common.objects.ujacs.tools;

import com.google.gson.Gson;

/**
 * класс предоставляет методы для конвертирования данных в JSON обратно
 *
 * @author ezhov_da
 */
public class JsonConverter<T> {

    private static Gson gson;
    private static JsonConverter converter;

    private JsonConverter() {

    }

    public static final JsonConverter getInstance() {
        if (converter == null) {
            converter = new JsonConverter();
            gson = new Gson();

        }
        return converter;
    }

    public synchronized T convertFromJsonClass(String json, Class<T> clazzForConvert) {
        T classFromJson = gson.fromJson(json, clazzForConvert);
        return classFromJson;
    }

    public synchronized String convertToJsonObject(Object objectForJsonConvert) {
        return gson.toJson(objectForJsonConvert);
    }
}
