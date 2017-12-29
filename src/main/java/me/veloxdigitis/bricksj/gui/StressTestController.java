package me.veloxdigitis.bricksj.gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import me.veloxdigitis.bricksj.battle.BrickPlayer;
import me.veloxdigitis.bricksj.stress.*;

import java.util.List;

public class StressTestController implements StressListener{

    @FXML private ChoiceBox<StressLevel> stressLevel;
    @FXML private ChoiceBox<TimeLevel> timeLevel;

    @FXML private TableView<PlayerStressResult> resultsTable;
    @FXML private Button runBtn;
    @FXML private ProgressBar progressBar;

    private final List<BrickPlayer> players;

    public StressTestController(List<BrickPlayer> players) {
        this.players = players;
    }

    @FXML
    private void initialize() {
        stressLevel.setItems(FXCollections.observableArrayList(StressLevel.values()));
        timeLevel.setItems(FXCollections.observableArrayList(TimeLevel.values()));
    }

    @FXML
    private void run() {
        StressTest test = new StressTest(players, stressLevel.getValue(), timeLevel.getValue(), this);
        new Thread(test).start();
        runBtn.setDisable(true);
    }

    @Override
    public void iteration(int iteration) {
        progressBar.setProgress(progressBar.getProgress() + (1 / (double)(players.size() * timeLevel.getValue().getIterations())));
    }

    @Override
    public void player(BrickPlayer player) {

    }

    @Override
    public void end(List<PlayerStressResult> results) {
        runBtn.setDisable(false);
        resultsTable.setItems(FXCollections.observableList(results));
    }


}
