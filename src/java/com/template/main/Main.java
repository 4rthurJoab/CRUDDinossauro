package com.template.main;

import com.template.controller.MainController;
import com.template.model.DinossauroDAO;
import com.template.model.IDinossauroDAO;
import com.template.service.DinossauroService;
import com.template.service.IDinossauroService;
import com.template.validator.DinossauroValidator;
import com.template.validator.IDinossauroValidator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Injeção de dependências com ControllerFactory (Slides 31 e 32)
        IDinossauroDAO dao = new DinossauroDAO();
        IDinossauroService service = new DinossauroService(dao);
        IDinossauroValidator validator = new DinossauroValidator();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/template/main.fxml"));
        fxmlLoader.setControllerFactory(controllerClass -> {
            if (controllerClass == MainController.class) {
                return new MainController(service, validator);
            }
            try {
                return controllerClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Scene scene = new Scene(fxmlLoader.load(), 1299, 780);
        stage.setTitle("SISTEMA DE ARQUIVAMENTO PALEONTOLÓGICO");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}