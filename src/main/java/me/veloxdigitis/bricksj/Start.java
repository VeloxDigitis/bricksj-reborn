package me.veloxdigitis.bricksj;

import javafx.application.Application;
import me.veloxdigitis.bricksj.battle.Battle;
import me.veloxdigitis.bricksj.champions.PlayersPair;
import me.veloxdigitis.bricksj.config.InfoFileLoader;
import me.veloxdigitis.bricksj.gui.FXApplication;
import me.veloxdigitis.bricksj.proxy.BricksAlgorithm;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Start {

    public static void main(String[] args) {
        Application.launch(FXApplication.class);
    }

}
