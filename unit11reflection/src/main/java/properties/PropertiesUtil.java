package properties;

import java.io.*;
import java.util.Properties;

public class PropertiesUtil {
    public static Properties readProperties(String path) {
        File propsFile = new File(path);
        Properties properties = new Properties();
        try(Reader in = new FileReader(propsFile)){
            properties.load(in);
        } catch (IOException e){
            throw new UncheckedIOException(e);
        }
        return properties;
    }
}
