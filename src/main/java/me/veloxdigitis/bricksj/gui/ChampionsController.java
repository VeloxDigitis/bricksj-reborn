package me.veloxdigitis.bricksj.gui;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import me.veloxdigitis.bricksj.history.BattleHistory;
import me.veloxdigitis.bricksj.info.HistoryInfoParser;

import java.util.Arrays;
import java.util.List;

public class ChampionsController implements ChangeListener<Number> {

    @FXML ListView<BattleHistory> historyList;
    @FXML Slider gameSlider;
    @FXML Canvas gameCanvas;

    @FXML Label mapSizeLabel;
    @FXML Label randomBricksLabel;
    @FXML Label currentGameLabel;

    @FXML WebView infoView;

    private final List<BattleHistory> history;
    private final HistoryInfoParser parser;

    public ChampionsController(List<BattleHistory> history, HistoryInfoParser parser) {
        this.history = history;
        this.parser = parser;
    }

    @FXML
    public void initialize() {
        historyList.setItems(FXCollections.observableArrayList(history));
        historyList.getSelectionModel().selectedItemProperty().
                addListener((ObservableValue<? extends BattleHistory> observable, BattleHistory oldValue, BattleHistory newValue) -> replay(newValue));
        historyList.getSelectionModel().selectFirst();
        gameSlider.valueProperty().addListener(this);
        mapSizeLabel.setText(String.format("Map size %d", history.get(0).getMapSize()));
    }

    private void replay(BattleHistory battle) {
        gameSlider.adjustValue(0.0);
        gameSlider.setMax(battle.length());
        gameSlider.setMajorTickUnit(gameSlider.getMax() / 10);
        gameCanvas.getGraphicsContext2D().setFill(Color.BLACK);
        gameCanvas.getGraphicsContext2D().fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
        currentGameLabel.setText(battle.toString());
        Platform.runLater(() -> infoView.getEngine().loadContent(parser.parse(battle)));
    }

    @Override
    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
        BattleHistory battle = historyList.getSelectionModel().getSelectedItem();
        double slabSize = gameCanvas.getWidth() / battle.getMapSize();
        battle.getHistory().
                stream().
                limit(newValue.longValue()).
                forEach(move -> {
                    gc.setFill(move.getPlayer().getColor());
                    Arrays.stream(move.getSlabs()).forEach(slab ->
                            gc.fillRect(slab.getX() * slabSize, slab.getY() * slabSize, slabSize, slabSize));
                });
    }

    @FXML
    public void newChampions() {
        Platform.runLater(() -> {
            FXApplication.show("setup", "Setup", null);
            gameCanvas.getScene().getWindow().hide();
        });
    }

    @FXML
    public void soloGame() {

    }
}
