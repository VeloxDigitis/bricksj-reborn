package me.veloxdigitis.bricksj.gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import me.veloxdigitis.bricksj.leaderboard.Leaderboard;
import me.veloxdigitis.bricksj.leaderboard.Stats;

public class LeaderboardController {

    private final Leaderboard leaderboard;

    @FXML
    private TableView<Stats> playersTable;

    @FXML
    private NumberAxis wins;

    @FXML
    private BarChart<String, Number> bc;


    public LeaderboardController(Leaderboard leaderboard) {
        this.leaderboard = leaderboard;
    }

    @FXML
    private void initialize() {
        this.playersTable.setItems(FXCollections.observableList(leaderboard.getPlayersWithStats()));
        setChartBar();
    }

    private void setChartBar() {
        wins.setTickLabelFormatter(new ChartStringConverter());
        wins.setMinorTickCount(0);
        wins.setTickUnit(1);
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        leaderboard.getPlayersWithStats().forEach(
                player -> series.getData().add(new XYChart.Data<>(player.getPlayer().toString(), player.getWins()))
        );

        for(XYChart.Data<String, Number> data : series.getData())
            data.nodeProperty().addListener((observable, oldValue, newNode) -> {
                if(newNode != null)
                    newNode.setStyle(String.format("-fx-bar-fill: #%s",
                            Integer.toHexString(
                                    leaderboard.getPlayersWithStats().stream().
                                            filter(a -> a.getPlayer().toString().equals(data.getXValue())).
                                            map(a -> a.getPlayer().getColor()).
                                            findAny().orElse(Color.WHITE).hashCode())));
            });

        bc.setData(FXCollections.observableArrayList(series));
    }

}
