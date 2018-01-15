package me.veloxdigitis.bricksj.gui;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import me.veloxdigitis.bricksj.battle.BrickPlayer;
import me.veloxdigitis.bricksj.config.InfoFileLoader;
import me.veloxdigitis.bricksj.logger.Logger;
import me.veloxdigitis.bricksj.proxy.BricksAlgorithm;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class PlayersController {

    private final ObservableList<BrickPlayer> players;

    @FXML private Label playersAmount;
    @FXML private ListView<BrickPlayer> playersList;

    public PlayersController(ObservableList<BrickPlayer> players) {
        this.players = players;
    }

    @FXML
    public void initialize() {
        this.playersAmount.textProperty().bind(Bindings.size(players).asString().concat(" players"));
        playersList.setItems(players);
    }

    @FXML
    public void addPlayer(ActionEvent event) {
        loadAlgorithm(((Node)event.getTarget()).getScene().getWindow()).ifPresent(players::add);
    }

    @FXML
    public void addDir(ActionEvent event) {
        players.addAll(loadAlgorithms(new DirectoryChooser().showDialog(((Node) event.getTarget()).getScene().getWindow()).toPath()));
    }

    @FXML
    private void loadDesktop() {
        players.addAll(loadAlgorithms(Paths.get(System.getProperty("user.home"), "Desktop")));
    }

    @FXML
    private void removePlayer() {
        players.remove(playersList.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void removePlayerFromKey(KeyEvent event) {
        if(event.getCode() == KeyCode.DELETE)
            removePlayer();
    }

    private Optional<BricksAlgorithm> loadAlgorithm(Window window) {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Algorithm files", "*.txt", "*.info"));
        chooser.setTitle("Choose algorithm");

        File file = chooser.showOpenDialog(window);
        try {
            return Optional.of(new BricksAlgorithm(new InfoFileLoader().load(Paths.get(file.getAbsolutePath()))));
        } catch (IOException | NullPointerException e) {
            Logger.error("Couldn't load " + file);
        }

        return Optional.empty();
    }

    private List<BricksAlgorithm> loadAlgorithms(Path directory) {
        try {
            return Files.walk(directory).
                    filter(p -> p.toString().endsWith(".info")
                            || p.toString().endsWith(".txt")).
                    map(this::algorithmFromPath).
                    filter(Optional::isPresent).
                    map(Optional::get).
                    collect(Collectors.toList());
        } catch (IOException e) {
            Logger.error("Error while walking directory");
            return Collections.emptyList();
        }
    }

    private Optional<BricksAlgorithm> algorithmFromPath(Path path) {
        try {
            return Optional.of(new BricksAlgorithm(new InfoFileLoader().load(path)));
        } catch (IOException e) {
            Logger.error("Couldn't load " + path);
            return Optional.empty();
        }
    }

    @FXML
    private void hide(ActionEvent event) {
        ((Node) event.getTarget()).getScene().getWindow().hide();
    }

}
