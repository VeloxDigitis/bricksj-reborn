package me.veloxdigitis.bricksj.logger;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogsToFile implements LogListener {

    private static final DateFormat dateFormat = new SimpleDateFormat("MMddHHmmss");
    private PrintWriter writer;

    private LogsToFile(String fileName) {
        try {
            this.writer = new PrintWriter(fileName, "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            Logger.error("Couldn't create logs file!");
        }
    }

    public LogsToFile() {
        this("BricksJudge logs " + dateFormat.format( new Date()) + ".log");
    }

    @Override
    public void log(String line) {
        writer.println(line);
    }

    @Override
    public void close() {
        writer.close();
    }

}
