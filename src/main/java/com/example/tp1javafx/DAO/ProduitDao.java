package com.example.tp1javafx.DAO;

import com.example.tp1javafx.Model.Produit;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProduitDao {
    public String url = "jdbc:mysql://localhost:3306/gestion_produits";
    public String user = "root";
    public String pass = "";

    public int addProduct(Produit p) {
        try (
                Connection connection = DriverManager.getConnection(url, user, pass);
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO produit(libelle,prix,quantite,user_id) VALUES (?,?,?,?)")
        ) {
            stmt.setString(1, p.getLibelle());
            stmt.setDouble(2, p.getPrix());
            stmt.setInt(3, p.getQuantite());
            stmt.setInt(4, p.getUserId());
            stmt.executeUpdate();
            return 1;
        } catch (SQLException e) {
            System.out.println("Erreur ajout produit : " + e.getMessage());
            return 0;
        }
    }

    public List<Produit> printAllProducts() {
        List<Produit> listeProd = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(url, user, pass);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM produit")
        ) {
            while (resultSet.next()) {
                Produit p = new Produit();
                p.setId(resultSet.getInt("id"));
                p.setLibelle(resultSet.getString("libelle"));
                p.setPrix(resultSet.getDouble("prix"));
                p.setQuantite(resultSet.getInt("quantite"));
                p.setUserId(resultSet.getInt("user_id"));
                listeProd.add(p);
            }
            return listeProd;
        } catch (SQLException e) {
            System.out.println("Erreur lecture produits : " + e.getMessage());
        }
        return null;
    }

    public List<Produit> getProductsByUser(int userId) {
        List<Produit> listeProd = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(url, user, pass);
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM produit WHERE user_id=?")
        ) {
            stmt.setInt(1, userId);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Produit p = new Produit();
                p.setId(resultSet.getInt("id"));
                p.setLibelle(resultSet.getString("libelle"));
                p.setPrix(resultSet.getDouble("prix"));
                p.setQuantite(resultSet.getInt("quantite"));
                p.setUserId(resultSet.getInt("user_id"));
                listeProd.add(p);
            }
            return listeProd;
        } catch (SQLException e) {
            System.out.println("Erreur lecture produits utilisateur : " + e.getMessage());
        }
        return null;
    }

    public int deleteProduct(int id) {
        try (
                Connection connection = DriverManager.getConnection(url, user, pass);
                PreparedStatement stmt = connection.prepareStatement("DELETE FROM produit WHERE id=?")
        ) {
            stmt.setInt(1, id);
            int ok = stmt.executeUpdate();
            return ok;
        } catch (SQLException e) {
            System.out.println("Erreur suppression produit : " + e.getMessage());
        }
        return -1;
    }

    public int updateProduct(Produit p) {
        try (
                Connection connection = DriverManager.getConnection(url, user, pass);
                PreparedStatement stmt = connection.prepareStatement("UPDATE produit SET libelle=?, prix=?, quantite=? WHERE id=?")
        ) {
            stmt.setString(1, p.getLibelle());
            stmt.setDouble(2, p.getPrix());
            stmt.setInt(3, p.getQuantite());
            stmt.setInt(4, p.getId());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur mise à jour produit : " + e.getMessage());
        }
        return -1;
    }

    public Produit getProductById(int id) {
        try (
                Connection connection = DriverManager.getConnection(url, user, pass);
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM produit WHERE id=?")
        ) {
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                Produit p = new Produit();
                p.setId(resultSet.getInt("id"));
                p.setLibelle(resultSet.getString("libelle"));
                p.setPrix(resultSet.getDouble("prix"));
                p.setQuantite(resultSet.getInt("quantite"));
                p.setUserId(resultSet.getInt("user_id"));
                return p;
            }
        } catch (SQLException e) {
            System.out.println("Erreur recherche produit : " + e.getMessage());
        }
        return null;
    }
}
