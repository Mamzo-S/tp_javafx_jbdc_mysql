package com.example.tp1javafx.utilitaires;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Navigation {
    public static void changerFenetre(String page, String titre, Node node) {
        try {
            URL fxmlUrl = Navigation.class.getResource("/com/example/tp1javafx/views/" + page);

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent parent = loader.load();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setScene(new Scene(parent));
            stage.setTitle(titre);
            stage.show();
        } catch (IOException e) {
            System.out.println("Erreur navigation : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void tryPath(String path) {
        URL url = Navigation.class.getResource(path);
        if (url != null) {
            System.out.println("Chemin valide: " + path);
        } else {
            System.out.println("Chemin NON valide: " + path);
        }
    }
}