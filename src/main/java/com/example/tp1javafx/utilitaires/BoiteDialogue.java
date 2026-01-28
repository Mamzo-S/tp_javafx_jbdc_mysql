package com.example.tp1javafx.utilitaires;

import javafx.scene.control.Alert;

public class BoiteDialogue {
    public static void MessaBox(Alert.AlertType type, String titre, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}