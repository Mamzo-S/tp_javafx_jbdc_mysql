package com.example.tp1javafx.Controller;

import com.example.tp1javafx.DAO.ProduitDao;
import com.example.tp1javafx.DAO.UtilisateurDao;
import com.example.tp1javafx.Model.Produit;
import com.example.tp1javafx.Model.Utilisateur;
import com.example.tp1javafx.utilitaires.BoiteDialogue;
import com.example.tp1javafx.utilitaires.Navigation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.Optional;

public class UserController {
    ProduitDao dbProd = new ProduitDao();
    UtilisateurDao dbUser = new UtilisateurDao();

    // Variables statiques pour stocker l'utilisateur connecté
    public static int userConnecteId = 1;
    public static Utilisateur userConnecte = null;

    @FXML
    private TableView<Produit> tableProd;
    @FXML
    private ObservableList<Produit> products;
    @FXML
    private TableColumn<Produit, Integer> colId;
    @FXML
    private TableColumn<Produit, String> colLibelle;
    @FXML
    private TableColumn<Produit, Double> colPrix;
    @FXML
    private TableColumn<Produit, Integer> colQuantite;

    @FXML
    private TextField libelleT;
    @FXML
    private TextField prixT;
    @FXML
    private TextField quantiteT;

    @FXML
    private MenuBar menuB;
    @FXML
    private Label userLabel;

    @FXML
    protected void initialize() {
        if (colId == null) return;

        // Configuration des colonnes
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colLibelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        colPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        colQuantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));

        // Afficher le nom de l'utilisateur
        if (userConnecte != null) {
            userLabel.setText("Bienvenue, " + userConnecte.getNom());
            System.out.println("✅ UserController initialisé pour : " + userConnecte.getNom());
        } else {
            userLabel.setText("Bienvenue");
        }

        // Rafraîchir le tableau
        refreshTable();
    }

    protected void refreshTable() {
        List<Produit> listeProd = dbProd.getProductsByUser(userConnecteId);
        if (listeProd != null) {
            products = FXCollections.observableArrayList(listeProd);
            tableProd.setItems(products);
        }
    }

    @FXML
    protected void addProduct() {
        String libelle = libelleT.getText();
        String prixStr = prixT.getText();
        String quantiteStr = quantiteT.getText();

        if (libelle.isEmpty() || prixStr.isEmpty() || quantiteStr.isEmpty()) {
            BoiteDialogue.MessaBox(Alert.AlertType.WARNING, "Erreur", "Veuillez remplir tous les champs");
            return;
        }

        try {
            double prix = Double.parseDouble(prixStr);
            int quantite = Integer.parseInt(quantiteStr);

            if (prix <= 0) {
                BoiteDialogue.MessaBox(Alert.AlertType.WARNING, "Erreur", "Le prix doit être positif");
                return;
            }

            if (quantite < 0) {
                BoiteDialogue.MessaBox(Alert.AlertType.WARNING, "Erreur", "La quantité doit être positive");
                return;
            }

            Produit p = new Produit(libelle, prix, quantite, userConnecteId);
            int result = dbProd.addProduct(p);

            if (result == 1) {
                BoiteDialogue.MessaBox(Alert.AlertType.INFORMATION, "Succès", "Produit ajouté avec succès");
                clearForm();
                refreshTable();
            } else {
                BoiteDialogue.MessaBox(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ajout du produit");
            }
        } catch (NumberFormatException e) {
            BoiteDialogue.MessaBox(Alert.AlertType.WARNING, "Erreur", "Prix et quantité doivent être des nombres");
        }
    }

    @FXML
    protected void deleteProduct() {
        Produit p = tableProd.getSelectionModel().getSelectedItem();
        if (p != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Suppression");
            alert.setContentText("Voulez-vous supprimer ce produit ?");
            alert.setHeaderText(null);
            Optional<ButtonType> btn = alert.showAndWait();
            if (btn.isPresent() && btn.get() == ButtonType.OK) {
                int ok = dbProd.deleteProduct(p.getId());
                if (ok == 1) {
                    BoiteDialogue.MessaBox(Alert.AlertType.INFORMATION, "Suppression", "Produit supprimé avec succès");
                    refreshTable();
                } else {
                    BoiteDialogue.MessaBox(Alert.AlertType.ERROR, "Erreur", "Erreur de suppression");
                }
            }
        } else {
            BoiteDialogue.MessaBox(Alert.AlertType.WARNING, "Avertissement", "Sélectionnez un produit");
        }
    }

    @FXML
    protected void updateProduct() {
        // À implémenter
    }

    @FXML
    protected void selectProduct() {
        Produit p = tableProd.getSelectionModel().getSelectedItem();
        if (p != null) {
            libelleT.setText(p.getLibelle());
            prixT.setText(String.valueOf(p.getPrix()));
            quantiteT.setText(String.valueOf(p.getQuantite()));
        }
    }

    @FXML
    protected void seDeconnecter(ActionEvent event) {
        userConnecteId = 1;
        userConnecte = null;
        Navigation.changerFenetre("views/login-view.fxml", "Connexion", menuB);
    }

    @FXML
    private void clearForm() {
        libelleT.setText("");
        prixT.setText("");
        quantiteT.setText("");
    }
}