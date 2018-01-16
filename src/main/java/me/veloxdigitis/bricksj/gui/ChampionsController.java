package me.veloxdigitis.bricksj.gui;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import me.veloxdigitis.bricksj.battle.BrickPlayer;
import me.veloxdigitis.bricksj.battle.reason.InvalidMove;
import me.veloxdigitis.bricksj.history.BattleHistory;
import me.veloxdigitis.bricksj.info.HistoryInfoParser;
import me.veloxdigitis.bricksj.leaderboard.Leaderboard;
import me.veloxdigitis.bricksj.map.Brick;
import me.veloxdigitis.bricksj.map.Slab;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class ChampionsController implements ChangeListener<Number> {

    @FXML TextField search;
    @FXML ListView<BattleHistory> historyList;
    @FXML Slider gameSlider;
    @FXML Canvas gameCanvas;
    @FXML Label moveLabel;

    @FXML Label mapSizeLabel;
    @FXML Label randomBricksLabel;
    @FXML Label currentGameLabel;

    @FXML WebView infoView;

    private final List<BrickPlayer> players;
    private final List<BattleHistory> history;
    private final HistoryInfoParser parser;
    private final int randomBricks;
    private final Leaderboard leaderboard;

    public ChampionsController(List<BrickPlayer> players, List<BattleHistory> history, Leaderboard leaderboard, HistoryInfoParser parser, int randomBricks) {
        this.players = players;
        this.history = history;
        this.leaderboard = leaderboard;
        this.parser = parser;
        this.randomBricks = randomBricks;
    }

    @FXML
    public void initialize() {
        historyList.getSelectionModel().selectedItemProperty().
                addListener((ObservableValue<? extends BattleHistory> observable, BattleHistory oldValue, BattleHistory newValue) -> replay(newValue));
        FilteredList<BattleHistory> filteredData = new FilteredList<>(FXCollections.observableArrayList(history), p -> true);
        search.textProperty().addListener((observable, oldValue, newValue) ->
            filteredData.setPredicate(
                    history -> newValue == null
                            || newValue.isEmpty()
                            || history.toString().toLowerCase().contains(newValue.toLowerCase()))
        );
        SortedList<BattleHistory> sortedData = new SortedList<>(filteredData);
        historyList.setItems(sortedData);

        historyList.getSelectionModel().selectFirst();
        gameSlider.valueProperty().addListener(this);
        mapSizeLabel.setText(String.format("Map size %d", history.get(0).getMapSize()));
        randomBricksLabel.setText(String.format("Random bricks %d", randomBricks));
    }

    private void replay(BattleHistory battle) {
        if(battle == null) return;

        gameSlider.adjustValue(0.0);
        gameSlider.setMax(battle.length());
        gameSlider.setMajorTickUnit(gameSlider.getMax() > 0 ? gameSlider.getMax() / 10 : 1);
        gameCanvas.getGraphicsContext2D().setFill(Color.BLACK);
        gameCanvas.getGraphicsContext2D().fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
        currentGameLabel.setText(battle.toString());
        changed(null, 0, 0);
        Platform.runLater(() -> infoView.getEngine().loadContent(parser.parse(battle)));
    }

    @Override
    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        if(historyList.getSelectionModel().getSelectedItem() == null) return;

        moveLabel.textProperty().setValue("" + newValue.intValue());
        BattleHistory battle = historyList.getSelectionModel().getSelectedItem();

        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        double slabSize = gameCanvas.getWidth() / battle.getMapSize();

        gc.setFill(Color.WHITE);
        battle.getStartingSlabs().forEach(slab -> fillSlab(gc, slab, slabSize));

        battle.getHistory().
                stream().
                limit(newValue.longValue()).
                forEach(move -> fillBrick(gc, move.getPlayer().getColor(), move, slabSize));

        if(newValue.doubleValue() == gameSlider.getMax() && battle.getReason() instanceof InvalidMove)
            fillBrick(gc, Color.rgb(255, 0, 0, 0.5), ((InvalidMove)battle.getReason()).getBrick(), slabSize);
    }

    private void fillBrick(GraphicsContext gc, Color color, Brick brick, double slabSize) {
        gc.setFill(color);
        Arrays.stream(brick.getSlabs()).forEach(slab -> fillSlab(gc, slab, slabSize));
    }

    private void fillSlab(GraphicsContext gc, Slab slab, double slabSize) {
        gc.fillRect(slab.getX() * slabSize, slab.getY() * slabSize, slabSize, slabSize);
    }

    @FXML
    public void newChampions() {
        Platform.runLater(() -> {
            FXApplication.show("setup", "Setup", true, t -> new SetupController(players));
            gameCanvas.getScene().getWindow().hide();
        });
    }

    @FXML
    public void openLeaderboard() {
        Platform.runLater(() -> FXApplication.show("leaderboard", "Leaderboard", false, t -> new LeaderboardController(leaderboard)));
    }

    @FXML
    public void stressTestAll() {
        Platform.runLater(() -> FXApplication.show("stresstest", "Stress Test", false, t ->
                new StressTestController(history.stream().flatMap(h -> h.getPlayers().toList().stream()).collect(Collectors.toList()))));
    }

    @FXML
    public void stressTest() {
        Platform.runLater(() -> FXApplication.show("stresstest", "Stress Test", false, t ->
                new StressTestController(historyList.getSelectionModel().getSelectedItem().getPlayers().toList())));
    }

    @FXML
    public void play() {
    }

}
