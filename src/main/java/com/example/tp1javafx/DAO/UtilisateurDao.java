package com.example.tp1javafx.DAO;

import com.example.tp1javafx.Model.Utilisateur;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDao {
    public String url = "jdbc:mysql://localhost:3306/gestion_produits";
    public String user = "root";
    public String pass = "";

    public int addUser(Utilisateur u) {
        try (
                Connection connection = DriverManager.getConnection(url, user, pass);
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO utilisateur(nom,email,password,role) VALUES (?,?,?,?)")
        ) {
            stmt.setString(1, u.getNom());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getPassword());
            stmt.setString(4, u.getRole());
            stmt.executeUpdate();
            return 1;
        } catch (SQLException e) {
            System.out.println("Erreur ajout utilisateur : " + e.getMessage());
            return 0;
        }
    }

    public List<Utilisateur> printAllUsers() {
        List<Utilisateur> listeU = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(url, user, pass);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM utilisateur")
        ) {
            while (resultSet.next()) {
                Utilisateur u = new Utilisateur();
                u.setId(resultSet.getInt("id"));
                u.setNom(resultSet.getString("nom"));
                u.setEmail(resultSet.getString("email"));
                u.setPassword(resultSet.getString("password"));
                u.setRole(resultSet.getString("role"));
                listeU.add(u);
            }
            return listeU;
        } catch (SQLException e) {
            System.out.println("Erreur lecture utilisateurs : " + e.getMessage());
        }
        return null;
    }

    public int deleteUser(int id) {
        try (
                Connection connection = DriverManager.getConnection(url, user, pass);
                PreparedStatement stmt = connection.prepareStatement("DELETE FROM utilisateur WHERE id=?")
        ) {
            stmt.setInt(1, id);
            int ok = stmt.executeUpdate();
            return ok;
        } catch (SQLException e) {
            System.out.println("Erreur suppression utilisateur : " + e.getMessage());
        }
        return -1;
    }

    public int updateUser(Utilisateur u) {
        try (
                Connection connection = DriverManager.getConnection(url, user, pass);
                PreparedStatement stmt = connection.prepareStatement("UPDATE utilisateur SET nom=?, email=?, role=? WHERE id=?")
        ) {
            stmt.setString(1, u.getNom());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getRole());
            stmt.setInt(4, u.getId());
            return stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur mise à jour utilisateur : " + e.getMessage());
        }
        return -1;
    }

    public Utilisateur getUserById(int id) {
        try (
                Connection connection = DriverManager.getConnection(url, user, pass);
                PreparedStatement stmt = connection.prepareStatement("SELECT * FROM utilisateur WHERE id=?")
        ) {
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                Utilisateur u = new Utilisateur();
                u.setId(resultSet.getInt("id"));
                u.setNom(resultSet.getString("nom"));
                u.setEmail(resultSet.getString("email"));
                u.setPassword(resultSet.getString("password"));
                u.setRole(resultSet.getString("role"));
                return u;
            }
        } catch (SQLException e) {
            System.out.println("Erreur recherche utilisateur : " + e.getMessage());
        }
        return null;
    }
}