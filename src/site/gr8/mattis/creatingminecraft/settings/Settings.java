package site.gr8.mattis.creatingminecraft.settings;

import site.gr8.mattis.creatingminecraft.core.logger.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Settings {

    private static Properties prop;
    private static final Logger logger = new Logger();

    static String propFileName = "settings.properties";
    static String fileLocation = "resources/" + propFileName;

    public Settings() {
    }

    public void init() {
        logger.info("Initializing settings file.");
        prop = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(fileLocation)) {
            prop.load(fileInputStream);
        } catch (IOException exception) {
            logger.error("Failed trying to read " + propFileName);
        }
    }

    public String getProperty(String key) {
        return prop.getProperty(key);
    }


}
