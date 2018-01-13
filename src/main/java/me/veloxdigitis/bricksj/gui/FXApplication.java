package me.veloxdigitis.bricksj.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import me.veloxdigitis.bricksj.logger.Logger;
import me.veloxdigitis.bricksj.proxy.ProcessRegistry;

import java.io.IOException;

public class FXApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setOnCloseRequest(event -> close());
        show("setup", "Setup", primaryStage, true, t -> new SetupController());
    }

    public static void show(String name, String title, Stage stage, boolean closing, Callback<Class<?>, Object> controllerFactory) {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(FXApplication.class.getResource(String.format("/views/%s.fxml", name)));

                if (controllerFactory != null)
                    loader.setControllerFactory(controllerFactory);

                Scene scene = new Scene(loader.load());
                if(closing)
                    stage.setOnCloseRequest(event -> close());
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

    public static Stage show(String name, String title, boolean closing, Callback<Class<?>, Object> controllerFactory) {
        Stage stage = new Stage();
        show(name, title, stage, closing, controllerFactory);
        return stage;
    }

    public static void close() {
        Logger.close();
        ProcessRegistry.getInstance().killAll();
        Platform.exit();
        System.exit(0);
    }

    public static <T> void commitEditorText(Spinner<T> spinner) {
        if (!spinner.isEditable()) return;
        String text = spinner.getEditor().getText();
        SpinnerValueFactory<T> valueFactory = spinner.getValueFactory();
        if (valueFactory != null) {
            StringConverter<T> converter = valueFactory.getConverter();
            if (converter != null) {
                T value = converter.fromString(text);
                valueFactory.setValue(value);
            }
        }
    }

}
