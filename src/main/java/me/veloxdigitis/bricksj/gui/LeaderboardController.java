package me.veloxdigitis.bricksj.gui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableView;
import javafx.util.StringConverter;
import me.veloxdigitis.bricksj.leaderboard.Leaderboard;
import me.veloxdigitis.bricksj.leaderboard.Stats;
import org.junit.FixMethodOrder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LeaderboardController {

    private final Leaderboard leaderboard;

    @FXML private TableView<Stats> playersTable;

    @FXML private CategoryAxis alghName;

    @FXML private NumberAxis wins;

    @FXML private BarChart<String, Number> bc;


    public LeaderboardController(Leaderboard leaderboard) {
        this.leaderboard = leaderboard;
    }

    @FXML
    private void initialize() {
        wins.setTickUnit(10);
        wins.setTickLabelFormatter(new StringConverter<>() {
                                       @Override
                                       public String toString(Number object) {
                                           return String.valueOf(object.intValue() == object.doubleValue() ? object.intValue() : "");
                                       }
                                       @Override
                                       public Number fromString(String string) {
                                           return Integer.parseInt(string);
                                       }
                                   }
        );

        this.playersTable.setItems(FXCollections.observableList(leaderboard.getPlayersWithStats()));
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        ObservableList<XYChart.Series<String, Number>> answer = FXCollections.observableArrayList();
        List<Number> result = leaderboard.getPlayersWithStats().stream().map(Stats::getWins).collect(Collectors.toList());
        List<String> names = leaderboard.getPlayersWithStats().stream().map(Stats::getPlayerName).collect(Collectors.toList());

        for (int j = 0; j < names.size() && !names.isEmpty(); j++) {
            final int counter = j;
            Platform.runLater(() -> series.getData().add(new XYChart.Data<>(names.get(counter), result.get(counter))) );
        }
        answer.add(series);
        bc.setData(answer);
    }

}
