import properties.AppProperties;
import properties.PropertiesConfig;
import properties.PropertiesUtil;

import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties properties = PropertiesUtil.readProperties("unit11reflection/src/main/resources/app.properties");

        PropertiesConfig propertiesConfig = new PropertiesConfig();
        AppProperties appProperties = propertiesConfig.initialise(AppProperties.class, properties);
        System.out.println(appProperties);
    }
}
