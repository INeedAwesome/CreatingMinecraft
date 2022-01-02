package site.gr8.mattis.creatingminecraft.core.logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    private static String date;
    static SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss.SSS");

    private static Logger LOGGER;

    private Logger() {    }

    public static Logger get() {
        if (Logger.LOGGER == null) {
            Logger.LOGGER = new Logger();
        }
        return Logger.LOGGER;
    }

    private static void update() {
        String dateString = format.format(new Date());
        date = "[" + dateString + "] ";
    }

    public void info(Object s) {
        update();
        System.out.println(date + s + Colors.ANSI_RESET);
    }

    public void warn(Object s) {
        update();
        System.out.println(Colors.ANSI_YELLOW + date + s + Colors.ANSI_RESET);
    }

    public void error(Object s) {
        update();
        System.out.println(Colors.ANSI_RED + date + s + Colors.ANSI_RESET);
    }
}
