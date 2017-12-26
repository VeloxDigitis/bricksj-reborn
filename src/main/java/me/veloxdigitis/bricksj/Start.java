package me.veloxdigitis.bricksj;

import javafx.application.Application;
import me.veloxdigitis.bricksj.gui.FXApplication;
import me.veloxdigitis.bricksj.logger.LogListener;
import me.veloxdigitis.bricksj.logger.Logger;

public class Start implements LogListener {

    public static void main(String[] args) {
        new Start();
    }

    private Start() {
        Logger.registerListener(this);
        Application.launch(FXApplication.class);
    }

    @Override
    public void log(String line) {
        System.out.println(line);
    }
}
