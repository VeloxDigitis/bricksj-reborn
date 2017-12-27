package me.veloxdigitis.bricksj.logger;

public interface LogListener {

    void log(String line);
    void close();
}
