package site.gr8.mattis.creatingminecraft.settings;

import site.gr8.mattis.creatingminecraft.core.util.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Settings {

    private static Properties prop;
    private static final Logger logger = new Logger();

    static String propFileName = "settings.properties";
    static String fileLocation = "resources/" + propFileName;
    static int initialisations = 0;

    public Settings() {
        initialisations++;
        if (initialisations == 1) // fail safe
            init();
        else
            logger.warn("Tried to initialize settings file twice(or more)! ");
    }

    private static void init() {
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
