package utilities;

import android.content.Context;
import android.util.Log;

import com.example.cs6011_project.AbsTimer;
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

    public static List<AbsTimer> ParseHelper(String jsonString) {
        List<AbsTimer> res = new ArrayList<>();

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

    private static void jsonMappingTimerData(JsonElement jsonElement, List<AbsTimer> list) {
        JsonObject obj = jsonElement.getAsJsonObject();
        AbsTimer timer;

        String timer_name = obj.get("name").getAsString();
        String type = obj.get("type").getAsString();

        // get duration and start date to calculate remaining time when the app start
        int duration = obj.get("duration").getAsInt();
        LocalDateTime start_date = LocalDateTime.parse(obj.get("start_date").getAsString());

        timer = TimerHelper.getInstance(timer_name, type, duration, start_date);

        if (timer != null) {
            list.add(timer);
        }
    }

    // obsolete
//    public static void TimerLogHelper(List<TimerData> timerData) {
//        if (timerData == null) {
//            Log.i("Timer Data", "No timer was founded");
//        } else {
//            for (TimerData data : timerData) {
//                Log.i("Timer Data", data.name);
//                Log.i("Timer Data", data.type);
//                Log.i("Timer Data", data.start_date.toString());
//                Log.i("Timer Data", String.valueOf(data.duration));
//            }
//        }
//
//    }
}
