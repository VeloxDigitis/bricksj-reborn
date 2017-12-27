package me.veloxdigitis.bricksj.gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import me.veloxdigitis.bricksj.leaderboard.Leaderboard;
import me.veloxdigitis.bricksj.leaderboard.Stats;

public class LeaderboardController {

    private Leaderboard leaderboard;

    @FXML private TableView<Stats> playersTable;

    public LeaderboardController(Leaderboard leaderboard) {
        this.leaderboard = leaderboard;
    }

    @FXML
    private void initialize() {
        this.playersTable.setItems(FXCollections.observableList(leaderboard.getPlayersWithStats()));
    }

}
