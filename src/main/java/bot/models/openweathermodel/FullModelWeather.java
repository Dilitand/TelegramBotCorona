package bot.models.openweathermodel;

import java.io.IOException;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

import com.fasterxml.jackson.annotation.*;

public class FullModelWeather {
    private Coord coord;
    private Weather[] weather;
    private String base;
    private Main main;
    private long visibility;
    private Wind wind;
    private Clouds clouds;
    private long dt;
    private Sys sys;
    private long timezone;
    private long id;
    private String name;
    private long cod;

    @JsonProperty("coord")
    public Coord getCoord() {
        return coord;
    }

    @JsonProperty("coord")
    public void setCoord(Coord value) {
        this.coord = value;
    }

    @JsonProperty("weather")
    public Weather[] getWeather() {
        return weather;
    }

    @JsonProperty("weather")
    public void setWeather(Weather[] value) {
        this.weather = value;
    }

    @JsonProperty("base")
    public String getBase() {
        return base;
    }

    @JsonProperty("base")
    public void setBase(String value) {
        this.base = value;
    }

    @JsonProperty("main")
    public Main getMain() {
        return main;
    }

    @JsonProperty("main")
    public void setMain(Main value) {
        this.main = value;
    }

    @JsonProperty("visibility")
    public long getVisibility() {
        return visibility;
    }

    @JsonProperty("visibility")
    public void setVisibility(long value) {
        this.visibility = value;
    }

    @JsonProperty("wind")
    public Wind getWind() {
        return wind;
    }

    @JsonProperty("wind")
    public void setWind(Wind value) {
        this.wind = value;
    }

    @JsonProperty("clouds")
    public Clouds getClouds() {
        return clouds;
    }

    @JsonProperty("clouds")
    public void setClouds(Clouds value) {
        this.clouds = value;
    }

    @JsonProperty("dt")
    public long getDt() {
        return dt;
    }

    @JsonProperty("dt")
    public void setDt(long value) {
        this.dt = value;
    }

    @JsonProperty("sys")
    public Sys getSys() {
        return sys;
    }

    @JsonProperty("sys")
    public void setSys(Sys value) {
        this.sys = value;
    }

    @JsonProperty("timezone")
    public long getTimezone() {
        return timezone;
    }

    @JsonProperty("timezone")
    public void setTimezone(long value) {
        this.timezone = value;
    }

    @JsonProperty("id")
    public long getID() {
        return id;
    }

    @JsonProperty("id")
    public void setID(long value) {
        this.id = value;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String value) {
        this.name = value;
    }

    @JsonProperty("cod")
    public long getCod() {
        return cod;
    }

    @JsonProperty("cod")
    public void setCod(long value) {
        this.cod = value;
    }

    public class Clouds {
        private long all;

        @JsonProperty("all")
        public long getAll() {
            return all;
        }

        @JsonProperty("all")
        public void setAll(long value) {
            this.all = value;
        }
    }

    public
    class Coord {
        private double lon;
        private double lat;

        @JsonProperty("lon")
        public double getLon() {
            return lon;
        }

        @JsonProperty("lon")
        public void setLon(double value) {
            this.lon = value;
        }

        @JsonProperty("lat")
        public double getLat() {
            return lat;
        }

        @JsonProperty("lat")
        public void setLat(double value) {
            this.lat = value;
        }
    }

    public class Main {
        private double temp;
        private double feelsLike;
        private double tempMin;
        private double tempMax;
        private long pressure;
        private long humidity;

        @JsonProperty("temp")
        public double getTemp() {
            return temp;
        }

        @JsonProperty("temp")
        public void setTemp(double value) {
            this.temp = value;
        }

        @JsonProperty("feels_like")
        public double getFeelsLike() {
            return feelsLike;
        }

        @JsonProperty("feels_like")
        public void setFeelsLike(double value) {
            this.feelsLike = value;
        }

        @JsonProperty("temp_min")
        public double getTempMin() {
            return tempMin;
        }

        @JsonProperty("temp_min")
        public void setTempMin(double value) {
            this.tempMin = value;
        }

        @JsonProperty("temp_max")
        public double getTempMax() {
            return tempMax;
        }

        @JsonProperty("temp_max")
        public void setTempMax(double value) {
            this.tempMax = value;
        }

        @JsonProperty("pressure")
        public long getPressure() {
            return pressure;
        }

        @JsonProperty("pressure")
        public void setPressure(long value) {
            this.pressure = value;
        }

        @JsonProperty("humidity")
        public long getHumidity() {
            return humidity;
        }

        @JsonProperty("humidity")
        public void setHumidity(long value) {
            this.humidity = value;
        }
    }

    public class Sys {
        private long type;
        private long id;
        private double message;
        private String country;
        private long sunrise;
        private long sunset;

        @JsonProperty("type")
        public long getType() {
            return type;
        }

        @JsonProperty("type")
        public void setType(long value) {
            this.type = value;
        }

        @JsonProperty("id")
        public long getID() {
            return id;
        }

        @JsonProperty("id")
        public void setID(long value) {
            this.id = value;
        }

        @JsonProperty("message")
        public double getMessage() {
            return message;
        }

        @JsonProperty("message")
        public void setMessage(double value) {
            this.message = value;
        }

        @JsonProperty("country")
        public String getCountry() {
            return country;
        }

        @JsonProperty("country")
        public void setCountry(String value) {
            this.country = value;
        }

        @JsonProperty("sunrise")
        public long getSunrise() {
            return sunrise;
        }

        @JsonProperty("sunrise")
        public void setSunrise(long value) {
            this.sunrise = value;
        }

        @JsonProperty("sunset")
        public long getSunset() {
            return sunset;
        }

        @JsonProperty("sunset")
        public void setSunset(long value) {
            this.sunset = value;
        }
    }

    public class Wind {
        private double speed;
        private long deg;

        @JsonProperty("speed")
        public double getSpeed() {
            return speed;
        }

        @JsonProperty("speed")
        public void setSpeed(double value) {
            this.speed = value;
        }

        @JsonProperty("deg")
        public long getDeg() {
            return deg;
        }

        @JsonProperty("deg")
        public void setDeg(long value) {
            this.deg = value;
        }
    }

    public static class Weather {
        private long id;
        private String main;
        private String description;
        private String icon;

        @JsonProperty("id")
        public long getID() { return id; }
        @JsonProperty("id")
        public void setID(long value) { this.id = value; }

        @JsonProperty("main")
        public String getMain() { return main; }
        @JsonProperty("main")
        public void setMain(String value) { this.main = value; }

        @JsonProperty("description")
        public String getDescription() { return description; }
        @JsonProperty("description")
        public void setDescription(String value) { this.description = value; }

        @JsonProperty("icon")
        public String getIcon() { return icon; }
        @JsonProperty("icon")
        public void setIcon(String value) { this.icon = value; }
    }
}

class Converter {
    // Date-time helpers

    private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ISO_DATE_TIME)
            .appendOptional(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            .appendOptional(DateTimeFormatter.ISO_INSTANT)
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SX"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            .toFormatter()
            .withZone(ZoneOffset.UTC);

    public static OffsetDateTime parseDateTimeString(String str) {
        return ZonedDateTime.from(Converter.DATE_TIME_FORMATTER.parse(str)).toOffsetDateTime();
    }

    private static final DateTimeFormatter TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ISO_TIME)
            .appendOptional(DateTimeFormatter.ISO_OFFSET_TIME)
            .parseDefaulting(ChronoField.YEAR, 2020)
            .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
            .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
            .toFormatter()
            .withZone(ZoneOffset.UTC);

    public static OffsetTime parseTimeString(String str) {
        return ZonedDateTime.from(Converter.TIME_FORMATTER.parse(str)).toOffsetDateTime().toOffsetTime();
    }
    // Serialize/deserialize helpers

    public static FullModelWeather fromJsonString(String json) throws IOException {
        return getObjectReader().readValue(json);
    }

    public static String toJsonString(FullModelWeather obj) throws JsonProcessingException {
        return getObjectWriter().writeValueAsString(obj);
    }

    private static ObjectReader reader;
    private static ObjectWriter writer;

    private static void instantiateMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(OffsetDateTime.class, new JsonDeserializer<OffsetDateTime>() {
            @Override
            public OffsetDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
                String value = jsonParser.getText();
                return Converter.parseDateTimeString(value);
            }
        });
        mapper.registerModule(module);
        reader = mapper.readerFor(FullModelWeather.class);
        writer = mapper.writerFor(FullModelWeather.class);
    }

    private static ObjectReader getObjectReader() {
        if (reader == null) instantiateMapper();
        return reader;
    }

    private static ObjectWriter getObjectWriter() {
        if (writer == null) instantiateMapper();
        return writer;
    }
}


