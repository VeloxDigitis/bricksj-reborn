package me.veloxdigitis.bricksj.config;

import java.io.IOException;

public class InfoFileException extends IOException {

    public InfoFileException() {
        super("Invalid info file!");
    }
}
