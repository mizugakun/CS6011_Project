package utilities;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentManager;

import com.example.cs6011_project.AbsTimer;
import com.example.cs6011_project.R;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import data.TimerData;

public class FileHelper {

    public static List<AbsTimer> getAbsTimersFromStorage(Context context, String filename) {
        String jsonString = getTextFromSource(context, filename);
        if (jsonString != null) {
            return ParseHelper(context, jsonString);
        }
        return new ArrayList<>();
    }

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

    public static List<AbsTimer> ParseHelper(Context context, String jsonString) {
        List<AbsTimer> res = new ArrayList<>();

        if (jsonString == null) {
            return res;
        }
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(jsonString);
        JsonArray jsonArray= element.getAsJsonArray();

        for (JsonElement ele : jsonArray) {
            jsonMappingAbsTimers(ele, res, context);
        }

        return res;
    }

    private static void jsonMappingAbsTimers(JsonElement jsonElement, List<AbsTimer> list, Context context) {
        JsonObject obj = jsonElement.getAsJsonObject();
        AbsTimer timer;

        String timer_name = obj.get("name").getAsString();
        String type = obj.get("type").getAsString();
        int duration = getDurationHelper(context, timer_name, type);
        LocalDateTime start_date = LocalDateTime.parse(obj.get("start_date").getAsString());

        timer = TimerHelper.getInstance(timer_name, type, duration, start_date);

        if (timer != null) {
            list.add(timer);
        }
    }

    public static int getDurationHelper (Context context, String timerName, String timerType) {
        String jsonString = getTextFromSource(context, "Duration.json");

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(jsonString);
        JsonObject obj = element.getAsJsonObject();
        return obj.get(timerName).getAsJsonObject().get(timerType).getAsInt();
    }

    public static void addOneTimer(Context context, TimerData data) {
        // get all timers in the storage and parse those into jsonArray
        String allTimersJSON = getTextFromSource(context, context.getString(R.string.TimersJSON));
        JsonParser parser = new JsonParser();
        JsonArray jsonArray = parser.parse(allTimersJSON).getAsJsonArray();

        // get new timer and parse to json element
        Gson gson = new Gson();
        String singleTimerJSON = gson.getAdapter(TimerData.class).toJson(data);
        JsonElement newTimer = parser.parse(singleTimerJSON).getAsJsonObject();

        // add json element into json array
        jsonArray.add(newTimer);

        //parse json array to json string
        String jsonString = gson.toJson(jsonArray);
        saveData(context, jsonString);
    }

    public static void deleteOneTimer(Context context, List<AbsTimer> newTimers) {
        List<TimerData> data = new ArrayList<>();
        for (AbsTimer timer : newTimers) {
            AbsTimerMappingTimeData(context, timer, data);
        }
        String jsonString = parseTimerDataToJson(data);
        saveData(context, jsonString);
    }

    public static void AbsTimerMappingTimeData(Context context, AbsTimer timer, List<TimerData> list) {
        String name = timer.getTimerName();
        String type = timer.getType();
        int duration = getDurationHelper(context, name, type);
        LocalDateTime start_date = TimerHelper.getStartDate(duration);

        list.add(new TimerData(name, type, duration, start_date.toString()));
    }

    public static String parseTimerDataToJson (List<TimerData> data) {
        Gson gson = new Gson();
        return gson.toJson(data);
    }

    public static void saveData(Context context, String jsonString) {
        try {
            BufferedWriter fos = new BufferedWriter(new FileWriter(context.getFilesDir().getAbsolutePath() + "/" + context.getString(R.string.TimersJSON)));
            fos.write(jsonString.trim());
            fos.close();

        } catch (Exception e) {
            Log.i("LOG_TAG", "ERROR OCCUR WHEN SAVING DATA.");
        }
    }
}
