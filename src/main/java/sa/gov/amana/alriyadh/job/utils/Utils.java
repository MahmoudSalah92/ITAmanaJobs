package sa.gov.amana.alriyadh.job.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.chrono.Chronology;
import java.time.chrono.HijrahChronology;
import java.time.chrono.HijrahDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import sa.gov.amana.alriyadh.job.entity.cmn.CmnServiceDetails;
import sa.gov.amana.alriyadh.job.repository.cmn.CmnServiceDetailsRepository;

public class Utils {

    public static HijrahDate getCurrentHijriDate() {
        ZoneId zoneId = ZoneId.of("Asia/Riyadh");
        HijrahDate hijrahDate = HijrahDate.now(zoneId);
        return hijrahDate;
    }

    public static HijrahDate parseHijriDate(String hijriDate, String pattern) {
        if (StringUtils.isNotBlank(hijriDate)) {
            try {
                Chronology chrono = HijrahChronology.INSTANCE;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern).withChronology(chrono);
                HijrahDate hijri = HijrahDate.from(formatter.parse(hijriDate));
                return hijri;
            } catch (Exception var5) {
            }
        }
        return null;
    }

    public static JSONObject stringToJsonJackson(String jsonString) {
        JSONObject jsonObject = null;
        try {
            // Create ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();
            // Convert String to JsonNode (or Json Object)
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            // Convert JsonNode to String
            String jsonStringFromNode = jsonNode.toString();
            // Convert the String to JSONObject (from org.json)
            jsonObject = new JSONObject(jsonStringFromNode);
            // Print the JSONObject
            System.out.println("Converted JSONObject: " + jsonObject);
            // Print the resulting JSON structure (JsonNode)
            System.out.println("Converted JSON Node: " + jsonNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static JsonObject stringToJsonGson(String jsonString) {
        // Parse the String into JsonObject using Gson
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
        // Print the resulting JSON structure (JsonObject)
        System.out.println("Converted JSON Object: " + jsonObject);
        return jsonObject;
    }

    public static Date unixTimeConverter(String unixTime) {
        // Unix seconds
        long unix_seconds = Long.valueOf(unixTime);
        // convert seconds to milliseconds
        Date date = new Date(unix_seconds * 1000L);
        // format of the date
        SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        jdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
        String java_date = jdf.format(date);
        System.out.println("\n" + java_date + "\n");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // Define the date format
        Date dateConverted = null;
        try {
            // Convert the string to Date
            dateConverted = dateFormat.parse(java_date);
            System.out.println("Date: " + dateConverted);
        } catch (ParseException e) {
            System.out.println("Invalid date format: " + e.getMessage());
        }
        return dateConverted;
    }

    public static void main(String args[]) {
        String s = "940535c1-599f-455b-88bc-5c8781772b9b";
        System.out.println(s);

        // String jsonString = "{\"name\": \"John\", \"age\": 30}"; // Example JSON
        // stringToJsonJackson(jsonString);
        // stringToJsonGson(jsonString);
        // unixTimeConverter("1372339860");
    }

    public static synchronized String getGUID() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

}
