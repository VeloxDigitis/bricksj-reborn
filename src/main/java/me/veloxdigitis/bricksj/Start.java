package me.veloxdigitis.bricksj;

import javafx.application.Application;
import me.veloxdigitis.bricksj.gui.FXApplication;
import me.veloxdigitis.bricksj.logger.LogListener;
import me.veloxdigitis.bricksj.logger.Logger;

public class Start {

    public static void main(String[] args) {
        new Start();
    }

    private Start() {
        Application.launch(FXApplication.class);
    }

}
