package utilities;

import android.content.Context;
import android.util.Log;

import com.example.cs6011_project.AbsTimer;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import data.TimerData;

public class FileHelper {

    public static String getTextFromSource(Context context, String filename) {
        File file = new File(context.getFilesDir().getAbsolutePath() + "/" + filename);
        if (file.exists()) {
            return getStringFromCache(context, filename);
        }
        return getStringFromAssets(context, filename);
    }

    private static String getStringFromCache(Context context, String filename) {
        String res = null;
        try {
            FileInputStream fis = context.openFileInput(filename);
            InputStreamReader inputStreamReader =
                    new InputStreamReader(fis, StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
            }

            res = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("LOG_ERROR", "An error occur when reading the data.");
        }

        return res;
    }
    private static String getStringFromAssets(Context context, String filename) {
        String res = null;
        try {
            InputStream is = context.getAssets().open(filename);

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

        timer = TimerHelper.getInstance(timer_name, type, start_date);

        if (timer != null) {
            list.add(timer);
        }
    }

    public static String ParseHelper (List<AbsTimer> timers) {
        List<TimerData> data = new ArrayList<>();

        for (AbsTimer timer : timers) {
            TimerData timerData = new TimerData();
            timerData.name = timer.getTimerName();
            timerData.type = timer.getType();
            timerData.duration = timer.getDuration();
            timerData.start_date = timer.getStartDate().toString();
            data.add(timerData);
        }

        Gson gson = new Gson();
        String json = gson.toJson(data);
        return json;
    }

    public static void saveData(Context context, String filename, List<AbsTimer> timers) {
        String json = ParseHelper(timers);
        try {
            BufferedWriter fos = new BufferedWriter(new FileWriter(context.getFilesDir().getAbsolutePath() + "/" + filename));
            fos.write(json.trim());
            fos.close();

        } catch (Exception e) {

        }
    }
}
