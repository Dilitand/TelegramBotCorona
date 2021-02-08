package bot.models.openweathermodel;

public interface ModelWeather {
    public String getName();

    public void setName(String name);

    public Double getTemp();

    public void setTemp(Double temp);

    public Double getHumidity();

    public void setHumidity(Double humidity);

    public String getIcon();

    public void setIcon(String icon);

    public FullModelWeather.Main getMain();

    public void setMain(FullModelWeather.Main main);
}
