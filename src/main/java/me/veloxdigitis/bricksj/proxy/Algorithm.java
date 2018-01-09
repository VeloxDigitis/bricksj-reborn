package me.veloxdigitis.bricksj.proxy;

import java.io.IOException;

public interface Algorithm {

    String getName();

    void run();
    void terminate();

    void send(String message);
    String read() throws IOException;

}
