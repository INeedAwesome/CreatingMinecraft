package site.gr8.mattis.creatingminecraft.settings;

import site.gr8.mattis.creatingminecraft.core.logger.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AdditionalSettings {

    private static Properties prop;
    private static final Logger LOGGER = Logger.get();

    static String propFileName = "additional.properties";
    static String fileLocation = "resources/" + propFileName;

    private static AdditionalSettings settings = null;

    private AdditionalSettings() {
    }

    public static AdditionalSettings get() {
        if (AdditionalSettings.settings == null) {
            AdditionalSettings.settings = new AdditionalSettings();
        }
        return AdditionalSettings.settings;
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
