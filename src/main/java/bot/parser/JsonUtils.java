package bot.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtils {

    public static String objectToJson(Object obj){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }

    public static Object jsonToObject(String json, Class myClass){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Object o = objectMapper.readValue(json,myClass);
            return o;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}