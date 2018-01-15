package me.veloxdigitis.bricksj.logger;

interface LogListener {

    void log(String line);
    void close();
}
