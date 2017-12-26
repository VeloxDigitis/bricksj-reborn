package me.veloxdigitis.bricksj.proxy;

public interface Algorithm {

    String getName();

    void run();
    void terminate();

    void send(String message);
    String read();

}
