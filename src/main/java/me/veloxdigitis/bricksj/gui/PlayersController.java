package me.veloxdigitis.bricksj.gui;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import me.veloxdigitis.bricksj.battle.BrickPlayer;
import me.veloxdigitis.bricksj.config.InfoFileLoader;
import me.veloxdigitis.bricksj.logger.Logger;
import me.veloxdigitis.bricksj.proxy.BricksAlgorithm;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

public class PlayersController {

    private ObservableList<BrickPlayer> players;

    @FXML private Label playersAmount;
    @FXML private ListView<BrickPlayer> playersList;

    public PlayersController(ObservableList<BrickPlayer> players) {
        this.players = players;
    }

    @FXML
    public void initialize() {
        this.playersAmount.textProperty().bind(Bindings.size(players).asString().concat(" players"));
    }

    @FXML
    public void addPlayer(ActionEvent event) {
        loadAlgorithm(((Node)event.getTarget()).getScene().getWindow()).ifPresent(a -> players.add(a));
        playersList.setItems(FXCollections.observableArrayList(players));
    }

    private Optional<BricksAlgorithm> loadAlgorithm(Window window) {
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Algorithm filem (*.info)", "*.info");
        chooser.getExtensionFilters().add(extFilter);
        chooser.setTitle("Choose algorithm");

        File file = chooser.showOpenDialog(window);
        try {
            return Optional.of(new BricksAlgorithm(new InfoFileLoader().load(Paths.get(file.getAbsolutePath()))));
        } catch (IOException | NullPointerException e) {
            Logger.error("Couldn't load " + file);
        }

        return Optional.empty();
    }

    @FXML
    private void hide(ActionEvent event) {
        ((Node) event.getTarget()).getScene().getWindow().hide();
    }

}
