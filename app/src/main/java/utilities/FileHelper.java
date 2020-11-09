package utilities;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import data.TimerData;

public class FileHelper {

    public String getTextFromAssets(Context context, String fileName) {
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<TimerData> ParseHelper(String jsonString) {
        if (jsonString == null) {
            return null;
        }

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(jsonString);
        JsonArray jsonArray= element.getAsJsonArray();

        List<TimerData> res = new ArrayList<>();

        for (JsonElement ele : jsonArray) {
            jsonMappingTimerData(ele, res);
        }

        return res;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void jsonMappingTimerData(JsonElement jsonElement, List<TimerData> list) {
        JsonObject obj = jsonElement.getAsJsonObject();
        TimerData data = new TimerData();
        data.name = obj.get("name").getAsString();
        data.label = obj.get("label").getAsString();
        String s = obj.get("start_date").getAsString();
        data.start_date = LocalDateTime.parse(s);
        data.duration = Long.parseLong(obj.get("duration").getAsString());
        list.add(data);
    }

    public void TimerLogHelper(List<TimerData> timerData) {
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
