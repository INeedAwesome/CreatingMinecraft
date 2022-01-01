package site.gr8.mattis.creatingminecraft.settings;

import site.gr8.mattis.creatingminecraft.core.logger.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AdditionalSettings {

    private static Properties prop;
    private static final Logger LOGGER = new Logger();

    static String propFileName = "additional.properties";
    static String fileLocation = "resources/" + propFileName;

    public AdditionalSettings() {
    }

    public void init() {
        LOGGER.info("Initializing additional settings. ");
        prop = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(fileLocation)) {
            prop.load(fileInputStream);
        } catch (IOException exception) {
            LOGGER.error("Failed trying to read " + propFileName);
        }
    }

    public String getProperty(String key) {
        return prop.getProperty(key);
    }


}
