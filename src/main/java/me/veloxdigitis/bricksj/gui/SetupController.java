package me.veloxdigitis.bricksj.gui;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import me.veloxdigitis.bricksj.battle.Battle;
import me.veloxdigitis.bricksj.battle.BrickPlayer;
import me.veloxdigitis.bricksj.champions.Champions;
import me.veloxdigitis.bricksj.champions.ChampionsListener;
import me.veloxdigitis.bricksj.champions.PlayersPair;
import me.veloxdigitis.bricksj.champions.SimpleGameSelector;
import me.veloxdigitis.bricksj.history.BattleHistory;
import me.veloxdigitis.bricksj.info.SimpleHTMLHistoryInfoParser;
import me.veloxdigitis.bricksj.leaderboard.Leaderboard;
import me.veloxdigitis.bricksj.logger.BricksLogger;
import me.veloxdigitis.bricksj.logger.Logger;
import me.veloxdigitis.bricksj.logger.LogsToFile;
import me.veloxdigitis.bricksj.logger.StandardOutputLogs;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SetupController implements ChampionsListener {

    private final ObservableList<BrickPlayer> players = FXCollections.observableArrayList();

    @FXML private Spinner<Integer> mapSize;
    @FXML private Spinner<Integer> randomBricks;
    @FXML private Spinner<Integer> initTime;
    @FXML private Spinner<Integer> moveTime;
    @FXML private Label playersAmount;
    @FXML private Button startBtn;
    @FXML private ProgressBar progressBar;
    @FXML private Label progressLabel;

    @FXML private CheckBox logToFile;
    @FXML private CheckBox logToSout;

    @FXML
    public void initialize() {
        this.playersAmount.textProperty().bind(Bindings.size(players).asString().concat(" players"));
        mapSize.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue % 2 != 1)
                mapSize.getValueFactory().setValue(newValue + 1);
            else
                randomBricks.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, (int) (Math.pow(newValue, 2) / 2)));
        });
    }

    @FXML
    public void openPlayersScene() {
        FXApplication.show("players", "Players", t -> new PlayersController(players));
    }

    @FXML
    public void openChampionsScene() {
        if(players.size() >= 2) {
            startBtn.setDisable(true);
            logToFile.setDisable(true);
            logToSout.setDisable(true);
            if(logToFile.isSelected()) Logger.registerListener(new LogsToFile());
            if(logToSout.isSelected()) Logger.registerListener(new StandardOutputLogs());
            Thread thread = new Thread(
                    new Champions(players,
                            mapSize.getValue(),
                            randomBricks.getValue(),
                            initTime.getValue(),
                            moveTime.getValue(),
                            new SimpleGameSelector(players),
                            Arrays.asList(this, BricksLogger.getInstance())));
            thread.setDaemon(true);
            thread.start();
        } else
            new Alert(Alert.AlertType.NONE, "Please add at least 2 players", ButtonType.OK).show();
    }

    private int gamesAmount;

    @Override
    public void start(List<PlayersPair> games) {
        this.gamesAmount = games.size();
        Platform.runLater(() -> progressBar.setDisable(false));
    }

    @Override
    public void game(Battle game) {
        Platform.runLater(() -> {
            progressLabel.setText(game.toString());
            progressBar.setProgress(progressBar.getProgress() + (1.0 / gamesAmount));
        });
    }

    @Override
    public void end(List<BattleHistory> history) {
        Platform.runLater(() -> {
            progressBar.setProgress(1.0);
            progressBar.getScene().getWindow().hide();
            Logger.close();
            FXApplication.show("champions", "Judge",
                    t -> new ChampionsController(history, new Leaderboard(history, players), new SimpleHTMLHistoryInfoParser(), randomBricks.getValue())).
                    setResizable(false);
        });
    }
}
