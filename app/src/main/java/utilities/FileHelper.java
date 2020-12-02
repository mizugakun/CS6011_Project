package utilities;

import android.content.Context;
import android.util.Log;

import com.example.cs6011_project.AbsTimer;
import com.example.cs6011_project.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import data.TimerData;

// This class is for parsing, writing, and reading the timers' data to the local storage or repository
public class FileHelper {

    // getting the List<AbsTimer> for the repository from the local storage
    public static List<AbsTimer> getAbsTimersFromStorage(Context context, String filename) {
        // get the json string in the local storage with certain file name
        // the value will be null if cannot find the file
        String jsonString = getTextFromSource(context, filename);

        // if the data is existing, parse the json string to the List<AbsTimer> and return it;
        // otherwise return empty array list
        if (jsonString != null) {
            return ParseHelper(context, jsonString);
        }
        return new ArrayList<>();
    }

    // read file from assets file or local storage
    public static String getTextFromSource(Context context, String filename) {
        // create the File instance with certain file name
        File file = new File(context.getFilesDir().getAbsolutePath() + "/" + filename);

        // if the file exist in the local storage, obtain the strings in the file from the storage;
        // otherwise obtain the string from the assets file
        if (file.exists()) {
            return getStringFromStorage(context, filename, true);
        }
        return getStringFromStorage(context, filename, false);
    }

    // read file from local storage and return the string
    private static String getStringFromStorage(Context context, String filename, boolean reaFromLocalStorage) {
        String res = null;
        InputStream is;

        // try to read the file's content
        try {
            // check the source of the file, then get the input stream
            if (reaFromLocalStorage) {
                is = context.openFileInput(filename);
            } else {
                is = context.getAssets().open(filename);
            }

            // create a buffer
            int size = is.available();
            byte[] buffer = new byte[size];

            // read the content int the input stream into buffer
            is.read(buffer);
            is.close();

            // parse the buffer to the string
            res = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("LOG_ERROR", "An error occur when reading the data.");
        }

        return res;
    }

    // parse the json string into List<AbsTimer>
    public static List<AbsTimer> ParseHelper(Context context, String jsonString) {
        List<AbsTimer> res = new ArrayList<>();

        if (jsonString == null) {
            return res;
        }

        JsonParser parser = new JsonParser();

        // parse the string into json element by using JsonParser
        JsonElement element = parser.parse(jsonString);

        // parse json element into json array
        JsonArray jsonArray= element.getAsJsonArray();

        // get AbsTimer instance by mapping items in json array,
        // then add the timer instance into the list
        for (JsonElement ele : jsonArray) {
            jsonMappingAbsTimers(ele, res, context);
        }

        return res;
    }

    // mapping the json element to the AbsTimer class, and add the timer into a list
    private static void jsonMappingAbsTimers(JsonElement jsonElement, List<AbsTimer> list, Context context) {
        JsonObject obj = jsonElement.getAsJsonObject();
        AbsTimer timer;

        // get value with certain json token, and then obtain the element by choosing certain type
        String timer_name = obj.get("name").getAsString();
        String type = obj.get("type").getAsString();
        int duration = getDurationHelper(context, timer_name, type);
        LocalDateTime start_date = LocalDateTime.parse(obj.get("start_date").getAsString());

        // create timer instance based on the four values
        timer = TimerHelper.getInstance(timer_name, type, duration, start_date);

        if (timer != null) {
            list.add(timer);
        }
    }

    // read the Duration.json file based on timer's type and surface's type
    public static int getDurationHelper (Context context, String timerName, String timerType) {
        // get the information od duration from the assets file in json string format
        String jsonString = getTextFromSource(context, "Duration.json");

        // parse the json string into json object format
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(jsonString);
        JsonObject obj = element.getAsJsonObject();

        // get certain value by specific token names and by choosing the type of value as integer
        return obj.get(timerName).getAsJsonObject().get(timerType).getAsInt();
    }

    // write a timer into local storage
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

    // parse a list of timers into json string and save the data
    public static void deleteOneTimer(Context context, List<AbsTimer> newTimers) {
        List<TimerData> data = new ArrayList<>();
        for (AbsTimer timer : newTimers) {

            // mapping the timer class into timer data class so it can be parse into json format
            AbsTimerMappingTimeData(context, timer, data);
        }

        // parse the list into json string
        String jsonString = new Gson().toJson(data);

        // write the json string into the local storage
        saveData(context, jsonString);
    }

    // mapping the timer class into TimerData class
    public static void AbsTimerMappingTimeData(Context context, AbsTimer timer, List<TimerData> list) {

        // get main parameters from the timer class
        String name = timer.getTimerName();
        String type = timer.getType();
        int duration = getDurationHelper(context, name, type);
        LocalDateTime start_date = TimerHelper.getStartDate(duration);

        // add data into the list
        list.add(new TimerData(name, type, duration, start_date.toString()));
    }

    // write the json string into the local storage
    public static void saveData(Context context, String jsonString) {
        try {
            BufferedWriter bfw = new BufferedWriter(new FileWriter(context.getFilesDir().getAbsolutePath() + "/" + context.getString(R.string.TimersJSON)));
            bfw.write(jsonString.trim());
            bfw.close();

        } catch (Exception e) {
            Log.i("LOG_TAG", "ERROR OCCUR WHEN SAVING DATA.");
        }
    }
}
