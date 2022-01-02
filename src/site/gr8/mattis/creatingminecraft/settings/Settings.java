package site.gr8.mattis.creatingminecraft.settings;

import site.gr8.mattis.creatingminecraft.core.logger.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Settings {

    private static final Logger LOGGER = Logger.get();

    private static String propFileName = "settings.properties";
    private static String fileLocation = "resources/" + propFileName;

    private static Settings settings = null;
    private static Properties prop;

    private Settings() {
    }

    public static Settings get() {
        if (Settings.settings == null) {
            Settings.settings = new Settings();
        }
        return Settings.settings;
    }

    public void init() {
        LOGGER.info("Initializing settings file.");
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
