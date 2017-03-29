/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import dao.ClientDAO;
import dao.CommandeDAO;
import dao.LivreurDAO;
import dao.ProduitDAO;
import dao.Qte_CommandeDAO;
import dao.RestaurantDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import metier.modele.Client;
import metier.modele.Commande;
import metier.modele.Livreur;
import metier.modele.LivreurHumain;
import metier.modele.Restaurant;
import util.GeoTest;

/**
 * les services techniques utilises par l'application
 * 
 * @author B3431
 */
public class ServiceTechnique {

    ClientDAO cdao = new ClientDAO();
    CommandeDAO codao = new CommandeDAO();
    LivreurDAO ldao = new LivreurDAO();
    ProduitDAO pdao = new ProduitDAO();
    Qte_CommandeDAO qdao = new Qte_CommandeDAO();
    RestaurantDAO rdao = new RestaurantDAO();
    Scanner clavier = new Scanner(System.in);

    /**
     * selectionner le meilleur livreur disponible pour une commande
     * choisi d'abord selon la disponibilite, puis le poids, puis le temps de livraison le plus court
     * 
     * @param poids le poids
     * @param client le client
     * @param restaurant le restaurant
     * @param livreurs la liste des livreurs
     * @return le livreur
     */
    public Livreur selectNewLivreur(double poids, Client client, Restaurant restaurant, List<Livreur> livreurs) {
        Livreur livreur;
        List<Livreur> livreursDispo = new ArrayList();
        for (int i = 0; i < livreurs.size(); i++) {
            Livreur liv = livreurs.get(i);
            if (liv.getDisponibilite() && liv.getCapacite() >= poids) {
                livreursDispo.add(liv);
            }
        }
        double time = 99999999;
        double temp = 99999999;
        int ires = 0;
        for (int i = 0; i < livreursDispo.size(); i++) {
            Livreur l = livreursDispo.get(i);
            if (l instanceof LivreurHumain) {
                temp = GeoTest.getTripDurationByBicycleInMinute(GeoTest.getLatLng(l.getAdresse()), GeoTest.getLatLng(client.getAdresse()), GeoTest.getLatLng(restaurant.getAdresse()));
            } else {
                temp = getTimeMachine(GeoTest.getFlightDistanceInKm(GeoTest.getLatLng(l.getAdresse()), GeoTest.getLatLng(client.getAdresse())), l);
            }
            if (temp < time) {
                time = temp;
                ires = i;
            }
        }
        if (livreursDispo.size() > 0) {
            livreur = livreursDispo.get(ires);

        } else {
            //commande trop lourde / pas de livreurs disponibles
            return null;
        }
        return livreur;
    }

    /**
     * calcul la duree d'un trajet pour un drone
     * 
     * @param distance la distance
     * @param drone le drone
     * @return le temps estime
     */
    public double getTimeMachine(double distance, Livreur drone) {
        double vitesse = drone.getVitesse();
        return distance * 60.0 / vitesse;
    }

    /**
     * Envoi le mail d'inscription
     * Les 2 cas: echec ou reussite
     * Sur console
     * 
     * @param etat l'etat
     * @param cl le client
     */
    public void envoiMailInscription(int etat, Client cl) {
        //si 0 echec si 1 reussi
        System.out.println("Expediteur : gustatif@gustatif.com\n"
                + "Pour : " + cl.getMail() + "\n"
                + "Sujet : Bienvenue chez Gustatif\n"
                + "Corps :\n"
                + "Bonjour " + cl.getPrenom() + ",\n");
        if (etat == 1) {
            System.out.println("Nous vous confirmons votre inscription au service Gustat'IF. "
                    + "Votre numero de client est " + cl.getId() + ".\n");
        } else {
            System.out.println("Votre inscription au service gustat'IF a malheureusement échouée..."
                    + " Merci de recommencer ultériorement.\n");
        }
    }

    /**
     * Envoi mail au livreur pour le prevenir d'une nouvelle commande
     * sur la console
     * 
     * @param li le livreur
     * @param co la commande
     * @param r le restaurant
     */
    public void envoiMailLivreur(Livreur li, Commande co, Restaurant r) {
        //si 0 echec si 1 reussi

        System.out.println("Expediteur : gustatif@gustatif.com\n"
                + "Pour : " + li.getNom() + " " + li.getPrenom() + " <" + li.getMail() + ">\n"
                + "Sujet :Livraison n°" + co.getID() + "\n"
                + "Corps :\n"
                + "Bonjour " + li.getPrenom() + ",\n"
                + "Merci d'effectuer cette livraison dès maintenant tout en respectant le code de la route >.^\n"
                + "Le Chef\n"
                + "Détails de la livraison\n"
                + "\t-date/heure :" + co.getDateDeb() + "\n"
                + "\t-Livreur : " + li.getPrenom() + " " + li.getNom() + " (n°" + li.getId() + ")\n"
                + "\t-Restaurant : " + r.getDenomination() + "\n"
                + "\t-Client :\n" + co.getClient().getPrenom() + " " + co.getClient().getNom() + "\n"
                + "\t" + co.getClient().getAdresse() + "\n\n"
                + "Commande : \n"
                + "\t-a continuer !!!!!!!!!!!!!!!");

    }

}
