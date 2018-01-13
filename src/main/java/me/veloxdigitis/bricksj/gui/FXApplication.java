package me.veloxdigitis.bricksj.gui;

import com.oracle.tools.packager.Log;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;
import me.veloxdigitis.bricksj.logger.Logger;
import me.veloxdigitis.bricksj.proxy.ProcessRegistry;

import java.io.IOException;
import java.util.Set;
import java.util.function.Consumer;

public class FXApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setOnCloseRequest(event -> close());
        show("setup", "Setup", primaryStage, null);
    }

    public static void show(String name, String title, Stage stage, Callback<Class<?>, Object> controllerFactory) {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(FXApplication.class.getResource(String.format("/views/%s.fxml", name)));

                if (controllerFactory != null)
                    loader.setControllerFactory(controllerFactory);

                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
                stage.setResizable(false);
                stage.setTitle(title);
                stage.show();
            } catch (IOException e) {
                Logger.error("Couldn't load view file!");
                e.printStackTrace();
            }
        });
    }

    public static Stage show(String name, String title, Callback<Class<?>, Object> controllerFactory) {
        Stage stage = new Stage();
        show(name, title, stage, controllerFactory);
        return stage;
    }

    public static void close() {
        Logger.close();
        ProcessRegistry.getInstance().killAll();
        Platform.exit();
        System.exit(0);
    }

}
