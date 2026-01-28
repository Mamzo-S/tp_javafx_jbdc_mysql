package com.example.tp1javafx.Controller;

import com.example.tp1javafx.DAO.UtilisateurDao;
import com.example.tp1javafx.Model.Utilisateur;
import com.example.tp1javafx.utilitaires.BoiteDialogue;
import com.example.tp1javafx.utilitaires.Navigation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

public class LoginController {
    UtilisateurDao db = new UtilisateurDao();

    @FXML
    private TextField nomT;
    @FXML
    private PasswordField passT;

    @FXML
    protected void seConnecter(ActionEvent event) {
        String nom = nomT.getText();
        String password = passT.getText();

        if (nom.isEmpty() || password.isEmpty()) {
            BoiteDialogue.MessaBox(Alert.AlertType.WARNING, "Erreur", "Veuillez remplir tous les champs");
            return;
        }

        // Chercher l'utilisateur dans la base de données
        java.util.List<Utilisateur> users = db.printAllUsers();
        Utilisateur userConnecte = null;

        if (users != null) {
            for (Utilisateur u : users) {
                if (u.getNom().equals(nom) && u.getPassword().equals(password)) {
                    userConnecte = u;
                    break;
                }
            }
        }

        if (userConnecte != null) {
            // Connexion réussie
            System.out.println("✅ Connexion réussie : " + userConnecte.getNom());

            // Stocker l'utilisateur dans UserController
            UserController.userConnecteId = userConnecte.getId();
            UserController.userConnecte = userConnecte;

            // Rediriger selon le rôle
            if ("ADMIN".equals(userConnecte.getRole())) {
                Navigation.changerFenetre("admin-view.fxml", "Menu Admin", nomT);
            } else {
                Navigation.changerFenetre("user-view.fxml", "Menu Utilisateur", nomT);
            }
        } else {
            BoiteDialogue.MessaBox(Alert.AlertType.ERROR, "Erreur", "Nom ou mot de passe incorrect");
            passT.clear();
        }
    }

    @FXML
    protected void clearForm() {
        nomT.clear();
        passT.clear();
    }
}