package utilities;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import data.TimerData;

public class FileHelper {

    public static String getTextFromAssets(Context context, String fileName) {
        String res = null;
        try {
            InputStream is = context.getAssets().open(fileName);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            res = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("LOG_ERROR", "An error occur when reading the data.");
        }

        return res;
    }

    public static List<TimerData> ParseHelper(String jsonString) {
        List<TimerData> res = new ArrayList<>();

        if (jsonString == null) {
            return res;
        }
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(jsonString);
        JsonArray jsonArray= element.getAsJsonArray();

        for (JsonElement ele : jsonArray) {
            jsonMappingTimerData(ele, res);
        }

        return res;
    }

    private static void jsonMappingTimerData(JsonElement jsonElement, List<TimerData> list) {
        JsonObject obj = jsonElement.getAsJsonObject();
        TimerData data = new TimerData();
        data.name = obj.get("name").getAsString();
        data.label = obj.get("label").getAsString();
        String s = obj.get("start_date").getAsString();
        data.start_date = LocalDateTime.parse(s);
        data.duration = Long.parseLong(obj.get("duration").getAsString());
        list.add(data);
    }

    public static void TimerLogHelper(List<TimerData> timerData) {
        if (timerData == null) {
            Log.i("Timer Data", "No timer was founded");
        } else {
            for (TimerData data : timerData) {
                Log.i("Timer Data", data.name);
                Log.i("Timer Data", data.label);
                Log.i("Timer Data", data.start_date.toString());
                Log.i("Timer Data", String.valueOf(data.duration));
            }
        }

    }
}
