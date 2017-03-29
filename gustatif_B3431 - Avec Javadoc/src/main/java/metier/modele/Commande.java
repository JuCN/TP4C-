/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import static javax.persistence.TemporalType.DATE;
import util.GeoTest;

/**
 * Classe Commande
 * 
 * @author B3431
 */
@Entity
public class Commande implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(DATE)
    private Date dateDeb;
    @Temporal(DATE)
    private Date dateFin;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Livreur livreur;

    @ManyToOne
    private Restaurant restaurant;

    @OneToMany(cascade = CascadeType.ALL)
    List<Qte_Commande> qte_commande;

    private String etat;

    /**
     * Constructeur par default de Commande
     */
    protected Commande() {
    }

    /**
     * Constructeur de Commande
     * Initie ses attibuts
     * L'etat initiale de la commande est en attente
     * N'a pas de livreur, in de date de fin
     * 
     * @param c le client
     * @param d la date
     * @param r le restaurant
     */
    public Commande(Client c, Date d, Restaurant r) {
        client = c;
        dateDeb = d;
        restaurant = r;
        
        livreur = null;
        qte_commande = new ArrayList();
        etat = "En attente";
        dateFin = null;
        
    }

    /**
     * get l'id de la commande
     * 
     * @return son id
     */
    public long getID() {
        return id;
    }

    /**
     * get le client de la commande
     * 
     * @return son client
     */
    public Client getClient() {
        return client;
    }

    /**
     * get le livreur de la commande
     * 
     * @return son livreur
     */
    public Livreur getLivreur() {
        return livreur;
    }

    /**
     * get la liste des qte commande de la commande
     * 
     * @return sa liste de qte_commande
     */
    public List<Qte_Commande> getQteProduit() {
        return qte_commande;
    }

    /**
     * get la date de debut de la commande
     * 
     * @return sa date de debut
     */
    public Date getDateDeb() {
        return dateDeb;
    }

    /**
     * get la date de fin de la commande
     * 
     * @return sa date de fin
     */
    public Date getDateFin() {
        return dateFin;
    }

    /**
     * get le restaurant de la commande
     * 
     * @return son restaurant
     */
    public Restaurant getRestaurant() {
        return restaurant;
    }
    
    /**
     * get l'etat de la commande
     * 
     * @return son etat
     */
    public String getEtat() {
        return etat;
    }

    /**
     * get le temps de livraison de la commande estime par google maps
     * 
     * @param livreur le livreur
     * @return le temps estime
     */
    public double getTimeLivraison(Livreur livreur) {
        double time = 0.0;
        if (livreur != null && livreur instanceof LivreurHumain) {
            time = GeoTest.getTripDurationByBicycleInMinute(GeoTest.getLatLng(livreur.getAdresse()), GeoTest.getLatLng(client.getAdresse()), GeoTest.getLatLng(restaurant.getAdresse()));
        } else if (livreur instanceof LivreurMachine) {
            time = GeoTest.getFlightDistanceInKm(GeoTest.getLatLng(livreur.getAdresse()), GeoTest.getLatLng(client.getAdresse())) * 60.0 / livreur.getVitesse();
        }
        return time;
    }
    
    /**
     * get un string contenant les produits commandes avec le prix total
     * 
     * @return un string des produits commandes
     */
    public String getProduitsCommande() {
        String res = "Produits commandés : \n";
        double prix = 0;
        List<Qte_Commande> produits = getQteProduit();
        for (int i = 0; i < produits.size(); i++) {
            Qte_Commande q = produits.get(i);
            res += q.getQuantite() + " x " + q.getProduit().getDenomination() + " au prix unitaire de " + q.getProduit().getPrix() + "\n";
            prix += q.getQuantite() * q.getProduit().getPrix();
        }
        res += "Prix total : " + prix;
        return res;
    }

    /**
     * get le poids de la commande
     * 
     * @return le poids total de la commande
     */
    public double getPoidsTotal() {
        double res = 0.0;
        List<Qte_Commande> produits = getQteProduit();
        for (int i = 0; i < produits.size(); i++) {
            Qte_Commande q = produits.get(i);
            res += q.getQuantite() * q.getProduit().getPoids();
        }
        return res;
    }

    /**
     * set le client de la commande
     * 
     * @param c le client
     */
    public void setClient(Client c) {
        client = c;
    }
    
    /**
     * set la date de debut de la commande
     * 
     * @param d nouvelle date de debut
     */
    public void setDateDeb(Date d) {
        dateDeb = d;
    }

    /**
     * set la date de fin de la commande
     * 
     * @param d nouvelle date de fin
     */
    public void setDateFin(Date d) {
        dateFin = d;
    }

    /**
     * set le livreur de la commande
     * @param l le livreur
     */
    public void setLivreur(Livreur l) {
        livreur = l;
    }

    /**
     * set le restaurant de la commande
     * 
     * @param r nouveau restaurant
     */
    public void setRestaurant(Restaurant r) {
        restaurant = r;
    }

    /**
     * ajoute une quantite de produit a la commande
     * 
     * @param qP quantite produit a ajoute
     */
    public void addQteProduit(Qte_Commande qP) {
        qte_commande.add(qP);
    }

    /**
     * set l'etat de la commande
     * 
     * @param n nouvel etat
     */
    public void setEtat(int n) {
        switch (n) {
            case 0:
                etat = "En attente";
                break;
            case 1:
                etat = "En cours";
                break;
            case 2:
                etat = "Finie";
                break;
            case 3:
                etat = "Annulée";
                break;
            default:
                System.out.println("Entrez 0, 1, 2, ou 3");
                break;
        }
    }

    @Override
    public String toString() {
        String res = "";
        if (etat.equals("En cours")) {
            res = "Commande n° " + id + " du client " + client.getId() + " effectuée le " + dateDeb + " et en attente de livraison.";
        } else {
            res = "Commande n° " + id + " du client " + client.getId() + " effectuée le " + dateDeb + " et livrée le " + dateFin;
        }
        return res;
    }
}
