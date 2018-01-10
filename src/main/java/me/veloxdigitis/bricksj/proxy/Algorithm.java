package me.veloxdigitis.bricksj.proxy;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface Algorithm {

    String getName();

    void run();
    void terminate();

    void send(String message);
    String read() throws IOException, TimeoutException;

}
