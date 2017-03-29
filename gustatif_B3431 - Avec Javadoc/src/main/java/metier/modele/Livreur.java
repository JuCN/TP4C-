package metier.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import static util.GeoTest.getLatLng;

/**
 * Classe abstraite de Livreur
 *
 * @author B3431
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Livreur implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    protected String adresse;

    protected Double capacite;

    protected boolean disponibilite;

    @OneToMany(mappedBy = "livreur")
    List<Commande> commandes;

    @Version
    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
    private long version = 0L;

    private Double longitude;
    private Double latitude;

    /**
     * Constructeur par default
     */
    public Livreur() {
    }

    /**
     * Constructeur de Livreur
     * Initie ses attributs
     * Effectue le calcul pour les latitude et longitude
     * 
     * @param a l'adresse
     * @param c la capacite
     */
    public Livreur(String a, Double c) {
        adresse = a;
        capacite = c;
        disponibilite = true;
        commandes = new ArrayList();
        this.longitude = getLatLng(a).lng;
        this.latitude = getLatLng(a).lat;
    }

    /**
     * get l'id du livreur
     * 
     * @return son id
     */
    public Long getId() {
        return id;
    }

    /**
     * get l'adresse du livreur
     * 
     * @return son adresse
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * get la capacite du livreur
     * 
     * @return sa capacite
     */
    public Double getCapacite() {
        return capacite;
    }

    /**
     * get la disponibilite du livreur
     * 
     * @return sa disponibilite
     */
    public boolean getDisponibilite() {
        return disponibilite;
    }

    /**
     * get la liste de toutes les commandes du livreur
     * 
     * @return sa liste de commande
     */
    public List<Commande> getCommandes() {
        return commandes;
    }

    /**
     * set l'adresse du livreur
     * change la latitude et longitude
     * 
     * @param adresse nouvelle adresse
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
        this.latitude = getLatLng(adresse).lng;
        this.longitude = getLatLng(adresse).lat;
    }

    /**
     * set la capacite du livreur
     * 
     * @param c nouvelle capacite
     */
    public void setCapacite(double c) {
        this.capacite = c;
    }

    /**
     * set la disponibilite du livreur
     * 
     * @param c nouvelle disponibilite
     */
    public void setDisponibilite(boolean c) {
        this.disponibilite = c;
    }

    /**
     * ajoute une commande au livreur
     * 
     * @param c commande a ajoute
     */
    public void addCommande(Commande c) {
        commandes.add(c);
    }

    @Override
    public String toString() {
        return "Livreur :{" + "id=" + id + ", adresse=" + adresse + ", capacité=" + capacite + ", disponibilité=" + disponibilite + '}';
    }

    /**
     * s'occupe de la cloture de la commande
     * fais  les changements du cote commande et livreur
     * 
     * @param commande la commande
     */
    public void finirCommande(Commande commande) {
        Date now = new Date();
        commande.setDateFin(now);
        commande.setEtat(2);
        disponibilite = true;
    }

    /**
     * get la vitesse du livreur (interessant pour les drones)
     * 
     * @return la vitesse
     */
    public double getVitesse() {
        return 1.0;
    }

    /**
     * get le nom (humain)
     * 
     * @return le nom
     */
    public String getNom() {
        return "";
    }

    /**
     * get le prenom (humain)
     * 
     * @return le prenom
     */
    public String getPrenom() {
        return "";
    }

    /**
     * get le mail (humain)
     * 
     * @return le mail
     */
    public String getMail() {
        return "";
    }

    /**
     * get la longitude du livreur
     * 
     * @return la longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * get lalatitude du livreur
     * 
     * @return la latitude
     */
    public Double getLatitude() {
        return latitude;
    }
}
