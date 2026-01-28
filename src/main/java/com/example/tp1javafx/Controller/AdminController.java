package com.example.tp1javafx.Controller;

import com.example.tp1javafx.DAO.UtilisateurDao;
import com.example.tp1javafx.utilitaires.BoiteDialogue;
import com.example.tp1javafx.utilitaires.Navigation;
import com.example.tp1javafx.Model.Utilisateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Optional;

public class AdminController {
    UtilisateurDao db = new UtilisateurDao();

    public static Utilisateur userConnecte = null;

    @FXML
    private TableView<Utilisateur> tableUser;
    @FXML
    private ObservableList<Utilisateur> users;
    @FXML
    private TableColumn<Utilisateur, Integer> colId;
    @FXML
    private TableColumn<Utilisateur, String> colNom;
    @FXML
    private TableColumn<Utilisateur, String> colEmail;
    @FXML
    private TableColumn<Utilisateur, String> colRole;

    @FXML
    private TextField nomT;
    @FXML
    private TextField emailT;
    @FXML
    private TextField passT;
    @FXML
    private ComboBox<String> roleCombo;

    @FXML
    private MenuBar menuB;

    @FXML
    protected void initialize() {
        if (colId == null) return;
        System.out.println("AdminController initialisé");
        if (userConnecte != null) {
            System.out.println("Admin connecté : " + userConnecte.getNom());
        }

        // Configuration des colonnes
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        // Configuration du ComboBox
        roleCombo.setItems(FXCollections.observableArrayList("ADMIN", "USER"));
        roleCombo.setValue("USER");

        // Rafraîchir le tableau
        refreshTable();
    }

    protected void refreshTable() {
        users = FXCollections.observableArrayList(db.printAllUsers());
        tableUser.setItems(users);
    }

    @FXML
    protected void addUser() {
        String nom = nomT.getText();
        String email = emailT.getText();
        String password = passT.getText();
        String role = roleCombo.getValue();

        if (nom.isEmpty() || email.isEmpty() || password.isEmpty()) {
            BoiteDialogue.MessaBox(Alert.AlertType.WARNING, "Erreur", "Veuillez remplir tous les champs");
            return;
        }

        if (!email.contains("@")) {
            BoiteDialogue.MessaBox(Alert.AlertType.WARNING, "Erreur", "Email invalide");
            return;
        }

        if (password.length() < 6) {
            BoiteDialogue.MessaBox(Alert.AlertType.WARNING, "Erreur", "Le mot de passe doit faire au moins 6 caractères");
            return;
        }

        Utilisateur u = new Utilisateur(nom, email, password, role);
        int result = db.addUser(u);

        if (result == 1) {
            BoiteDialogue.MessaBox(Alert.AlertType.INFORMATION, "Succès", "Utilisateur ajouté avec succès");
            clearForm();
            refreshTable();
        } else {
            BoiteDialogue.MessaBox(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ajout de l'utilisateur");
        }
    }

    @FXML
    protected void deleteUser() {
        Utilisateur u = tableUser.getSelectionModel().getSelectedItem();
        if (u != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Suppression");
            alert.setContentText("Voulez-vous supprimer cet utilisateur ?");
            alert.setHeaderText(null);
            Optional<ButtonType> btn = alert.showAndWait();
            if (btn.isPresent() && btn.get() == ButtonType.OK) {
                int ok = db.deleteUser(u.getId());
                if (ok == 1) {
                    BoiteDialogue.MessaBox(Alert.AlertType.INFORMATION, "Suppression", "Utilisateur supprimé avec succès");
                    refreshTable();
                } else {
                    BoiteDialogue.MessaBox(Alert.AlertType.ERROR, "Erreur", "Erreur de suppression");
                }
            }
        } else {
            BoiteDialogue.MessaBox(Alert.AlertType.WARNING, "Avertissement", "Sélectionnez un utilisateur");
        }
    }

    @FXML
    protected void seDeconnecter(ActionEvent event) {
        Navigation.changerFenetre("login-view.fxml", "Connexion", menuB);
    }

    @FXML
    private void clearForm() {
        nomT.setText("");
        emailT.setText("");
        passT.setText("");
        roleCombo.setValue("USER");
    }
}