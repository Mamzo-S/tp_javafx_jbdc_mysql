package com.example.tp1javafx.Model;

public class Produit {
    private int id;
    private String libelle;
    private double prix;
    private int quantite;
    private int userId;

    public Produit() {
    }

    public Produit(String libelle, double prix, int quantite, int userId) {
        this.libelle = libelle;
        this.prix = prix;
        this.quantite = quantite;
        this.userId = userId;
    }

    public Produit(int id, String libelle, double prix, int quantite, int userId) {
        this.id = id;
        this.libelle = libelle;
        this.prix = prix;
        this.quantite = quantite;
        this.userId = userId;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getTotal() {
        return prix * quantite;
    }

    @Override
    public String toString() {
        return libelle;
    }
}