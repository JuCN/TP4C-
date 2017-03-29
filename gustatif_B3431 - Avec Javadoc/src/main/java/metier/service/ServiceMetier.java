package metier.service;

import dao.ClientDAO;
import dao.CommandeDAO;
import dao.JpaUtil;
import dao.LivreurDAO;
import dao.ProduitDAO;
import dao.Qte_CommandeDAO;
import dao.RestaurantDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.RollbackException;
import metier.modele.Client;
import metier.modele.Commande;
import metier.modele.Livreur;
import metier.modele.LivreurHumain;
import metier.modele.LivreurMachine;
import metier.modele.Restaurant;

/**
 * Les services metiers a appelé par l'IHM
 * 
 * @author groupe b3431
 */
public class ServiceMetier {

    //creation de toutes les DAO necessaires et du service technique
    ClientDAO cdao = new ClientDAO();
    CommandeDAO codao = new CommandeDAO();
    LivreurDAO ldao = new LivreurDAO();
    ProduitDAO pdao = new ProduitDAO();
    Qte_CommandeDAO qdao = new Qte_CommandeDAO();
    RestaurantDAO rdao = new RestaurantDAO();
    ServiceTechnique stechnique = new ServiceTechnique();

    /**
     * Enregistre le client passe en parametre dans la base de donnees
     * Envoie le mail au client de reussite ou echec
     * renvoie l'objet Client qui vient d'etre persiste
     * 
     * @param client le client
     * @return le client
     */
    public Client signUpClient(Client client) {
        JpaUtil.creerEntityManager();
        try {
            JpaUtil.ouvrirTransaction();
            cdao.create(client);
            JpaUtil.validerTransaction();
            stechnique.envoiMailInscription(1, client);
        } catch (Exception ex) {
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
            stechnique.envoiMailInscription(0, client);
        }
        JpaUtil.fermerEntityManager();

        return client;
    }

    /**
     * Retrouve le client dans la base de donnees grace a son adresse mail
     * renvoie l'objet Client qui correspond a cette adresse mail
     * Permet la connexion d'un client
     * 
     * @param mail le mail
     * @return le client
     */
    public Client singInClient(String mail) {
        JpaUtil.creerEntityManager();
        Client cl = null;
        try {
            cl = cdao.findClientByMail(mail);
        } catch (Exception ex) {
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        JpaUtil.fermerEntityManager();
        return cl;
    }
    
    /**
     * Retrouve le livreur humain dans la base de donnees grace a son adresse mail
     * renvoie l'objet Livreur qui correspond a cette adresse mail
     * Permet la connexion d'un livreur
     * 
     * @param mail le mail
     * @return le livreur
     */
    public Livreur singInLivreur(String mail) {
        JpaUtil.creerEntityManager();
        Livreur cl = null;
        try {
            cl = ldao.findLivreurByMail(mail);
        } catch (Exception ex) {
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        JpaUtil.fermerEntityManager();
        return cl;
    }

    /**
     * Enregiste dans la base de donnees la commande passee en parametre
     * 
     * @param commande la commande
     */
    public void createCommande(Commande commande) {
        JpaUtil.creerEntityManager();

        try {
            JpaUtil.ouvrirTransaction();
            codao.create(commande);
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
        }

        JpaUtil.fermerEntityManager();
    }
    
    /**
     * Cherche le meilleur livreur (critere du temps, avec contrainte du temps, et de disponibilite) pour la commande
     * gere la concurence (si 2 commandes en meme temps)
     * retourne la commande qui vient d'etre completee ou null si aucun livreur ne peut l'effectuer
     * 
     * @param commande la commande
     * @param restaurant le restaurant
     * @return la commande
     */
    public Commande checkCommande(Commande commande, Restaurant restaurant) {
        Client client = commande.getClient();
        boolean reussi = false;
        Livreur livreur = stechnique.selectNewLivreur(commande.getPoidsTotal(), client, restaurant, getLivreurs());
        if (livreur != null) {
            while (!reussi) {
                commande.setLivreur(livreur);
                livreur.setDisponibilite(false);
                commande.setEtat(1);
                try {
                    updateLivreur(livreur);
                    reussi = true;
                } catch (Exception ex) {
                    reussi = false;
                    livreur.setDisponibilite(true);
                    livreur = stechnique.selectNewLivreur(commande.getPoidsTotal(), client, restaurant, getLivreurs());
                    if (livreur == null) {
                        commande.setEtat(3);
                        return null;
                    }
                }
            }
            client.addCommande(commande);
            createCommande(commande);
            if (livreur instanceof LivreurHumain) {
                stechnique.envoiMailLivreur(livreur, commande, restaurant);
            }
            livreur.finirCommande(commande);
            return commande;
        } else {
            return null;
        }
    }
    
    /**
     * Permet au livreur de cloturer sa commande
     * Met a jour le livreur (disponible) et la commande (finie)
     * 
     * @param commande la commande
     * @param livreur le livreur
     */
    public void finirCommande(Commande commande, Livreur livreur) {
        livreur.finirCommande(commande);
        try {
            updateLivreur(livreur);
        } catch (Exception ex) {
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        updateCommande(commande);
    }
    
    /**
     * Met a jour le livreur dans la base de donnees
     * Lance une exception s'il n'y parvient pas
     * 
     * @param livreur le livreur
     * @throws Exception si la mise a jour n'a pas fonctionnee
     */
    public void updateLivreur(Livreur livreur) throws Exception {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        try {
            ldao.update(livreur);
            JpaUtil.validerTransaction();
        } catch (RollbackException ex) {
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        JpaUtil.fermerEntityManager();
    }
    
    /**
     * Met à jour la commande dans la base de donnees
     * 
     * @param commande la commande
     */
    public void updateCommande(Commande commande) {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        try {
            codao.update(commande);
        } catch (Exception ex) {
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
    }

    /**
     * Renvoie la liste de tous les restaurants de la base de donnees
     * 
     * @return la liste de restaurant
     */
    public List<Restaurant> getRestaurants() {
        JpaUtil.creerEntityManager();
        List<Restaurant> restaurants = new ArrayList();
        try {
            restaurants = rdao.findAll();
        } catch (Exception ex) {
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        JpaUtil.fermerEntityManager();
        return restaurants;
    }

    /**
     * Renvoie la liste de tous les clients de la base de donnees
     * 
     * @return la liste de clients
     */
    public List<Client> getClients() {
        JpaUtil.creerEntityManager();
        List<Client> clients = new ArrayList();
        try {
            clients = cdao.findAll();
        } catch (Exception ex) {
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        JpaUtil.fermerEntityManager();
        return clients;
    }    
    
    /**
     * Renvoie la liste des livreurs de la base de donnees
     * 
     * @return la liste de livreurs
     */
    public List<Livreur> getLivreurs() {
        JpaUtil.creerEntityManager();
        List<Livreur> livreurs = new ArrayList();
        try {
            livreurs = ldao.findAll();
        } catch (Exception ex) {
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        JpaUtil.fermerEntityManager();
        return livreurs;
    }

    /**
     * Renvoie la liste des commande de la base de donnees
     * 
     * @return la liste de commandes
     */
    public List<Commande> getCommandes() {
        JpaUtil.creerEntityManager();
        List<Commande> commandes = new ArrayList();
        try {
            commandes = codao.findAll();
        } catch (Exception ex) {
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        JpaUtil.fermerEntityManager();
        return commandes;
    }


    /**
     * cree des livreurs, a utiliser si la table livreur est vide
     * enregistre 5 livreurs humains et 5 machines
     */
    public void createLivreurs() {
        JpaUtil.creerEntityManager();
        LivreurHumain h1 = new LivreurHumain("8 Rue Arago, Villeurbanne", 25000.0,
                "Premier", "Fisrt", "premier@gustatif.fr");
        LivreurHumain h2 = new LivreurHumain("80 Rue Léon Fabre, Villeurbanne", 23000.93,
                "Deuxieme", "Second", "deuxieme@gustatif.fr");
        LivreurHumain h3 = new LivreurHumain("8 Rue Wilhelmine, Villeurbanne", 20000.95,
                "Troisieme", "Third", "troisieme@gustatif.fr");
        LivreurHumain h4 = new LivreurHumain("9 Place de la Paix", 18000.79,
                "Quatrieme", "Forth", "quatrieme@gustatif.fr");
        LivreurHumain h5 = new LivreurHumain("3 Allée Louis Pergaud", 21500.9,
                "Cinquieme", "Fifth", "cinquieme@gustatif.fr");
        LivreurMachine m1 = new LivreurMachine("20 Rue des Peupliers, Villeurbanne", 1500.0,
                "R1G1", 55.9);
        LivreurMachine m2 = new LivreurMachine("7 Rue Pelisson, Villeurbanne", 2000.0,
                "R2G2", 50.5);
        LivreurMachine m3 = new LivreurMachine("16 Boulevard Niels Bohr, Villeurbanne", 2500.0,
                "R3G3", 60.5);
        LivreurMachine m4 = new LivreurMachine("11 Rue Mansard, Villeurbanne", 1700.0,
                "R4G4", 80.5);
        LivreurMachine m5 = new LivreurMachine("12 Rue Léon Piat, Villeurbanne", 2300.0,
                "R5G5", 45.5);
        try {
            JpaUtil.ouvrirTransaction();
            ldao.create(h1);
            ldao.create(h2);
            ldao.create(h3);
            ldao.create(h4);
            ldao.create(h5);
            ldao.create(m1);
            ldao.create(m2);
            ldao.create(m3);
            ldao.create(m4);
            ldao.create(m5);
            JpaUtil.validerTransaction();
        } catch (Exception ex) {
            Logger.getLogger(ServiceMetier.class.getName()).log(Level.SEVERE, null, ex);
        }
        JpaUtil.fermerEntityManager();
    }

}
