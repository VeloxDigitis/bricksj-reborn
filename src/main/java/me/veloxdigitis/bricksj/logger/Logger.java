package me.veloxdigitis.bricksj.logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Logger {

    private final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss:SSS");
    private static Logger instance;

    private final List<LogListener> listeners;

    private Logger() {
        this.listeners = new ArrayList<>();
    }

    private static Logger getInstance() {
        if(instance == null)
            instance = new Logger();
        return instance;
    }

    private void log(LogLevel info, String line) {
        String lineBuilder = String.format("[%s] %s %s: %s",
                dateFormat.format(Calendar.getInstance().getTime()),
                info.name(),
                new Exception().getStackTrace()[2].getClassName(),
                line);
        listeners.forEach(l -> l.log(lineBuilder));
    }

    public static void info(String line) {
        Logger.getInstance().log(LogLevel.INFO, line);
    }

    public static void error(String line) {
        Logger.getInstance().log(LogLevel.ERROR, line);
    }

    public static void registerListener(LogListener listener) {
        Logger.getInstance().listeners.add(listener);
    }

    public static void unregisterAll() {
        Logger.getInstance().listeners.clear();
    }

    public static void close() {
        Logger.getInstance().listeners.forEach(LogListener::close);
    }

    private enum LogLevel {
        INFO, ERROR
    }

}
