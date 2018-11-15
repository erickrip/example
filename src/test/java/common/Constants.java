package common;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Constants {

    public static String BASE_URL = "https://pokeapi.co/api/v2/";
    public static String POKEMON_SERVICE = "pokemon/";
    public static String LOCATION_SERVICE = "location/";
    public static String BERRI_SERVICE = "berry/";

    public static final String HTML_REPORT = System.getProperty("user.dir") + "/results/api_report" +
            new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()).toString() + ".html";
    public static final String EXTENT_CONFIG = System.getProperty("user.dir") + "/extent-config.xml";

}
