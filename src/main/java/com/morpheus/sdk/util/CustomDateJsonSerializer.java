package com.morpheus.sdk.util;

import com.google.gson.JsonSerializer;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonParseException;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonSerializationContext;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.text.DateFormat;
import java.util.TimeZone;
import java.util.Calendar;
import java.util.Date;
import java.lang.reflect.Type;

public class CustomDateJsonSerializer implements JsonSerializer<Date>, JsonDeserializer<Date> {
    private static final TimeZone UTC_TIME_ZONE = TimeZone.getTimeZone("UTC");
    
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String asString = json.getAsString();
        try {
            if (asString.endsWith("Z")) {
                return getDateZuluFormat().parse(asString);
            } else {
                return getDateFormat().parse(asString);
            }
        } catch (ParseException e) {
            throw new JsonParseException("Could not parse to date: " + json, e);
        }
    }

    private static DateFormat getDateZuluFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        simpleDateFormat.setTimeZone(UTC_TIME_ZONE);
        return simpleDateFormat;
    }

    private static DateFormat getDateFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        dateFormat.setTimeZone(UTC_TIME_ZONE);
        return dateFormat;
    }

    public JsonElement serialize(Date date, Type typeOfSrc, JsonSerializationContext context) {
        Calendar calendar = Calendar.getInstance(UTC_TIME_ZONE);
        calendar.setTime(date);
        String dateFormatted = getDateFormat().format(date);
        return new JsonPrimitive(dateFormatted);
    }
}