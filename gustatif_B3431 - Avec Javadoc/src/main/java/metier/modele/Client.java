package metier.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import static util.GeoTest.getLatLng;

/**
 * Classe Client
 * Un client est l'utilisateur de Gustat'if qui se fait commander
 *
 * @author B3431
 */
@Entity
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nom;
    private String prenom;
    private String mail;
    private String adresse;
    private Double longitude;
    private Double latitude;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    List<Commande> commandes;

    /**
     * Constructeur par default
     */
    protected Client() {
    }

    /**
     * Constructeur de Client
     * Initie tous ses attributs
     * Effectue le calcul des longitute/latitude
     * Initie sa liste de commandes vide
     * 
     * @param nom son nom
     * @param prenom son prenom
     * @param mail son mail
     * @param adresse son adresse
     */
    public Client(String nom, String prenom, String mail, String adresse) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.adresse = adresse;
        this.longitude = getLatLng(adresse).lng;
        this.latitude = getLatLng(adresse).lat;
        commandes = new ArrayList();
    }

    /**
     * Get l'id du client
     * 
     * @return son id
     */
    public Long getId() {
        return id;
    }

    /**
     * Get le nom du client
     * 
     * @return son nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Get le prenom du client
     * 
     * @return son prenom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Get le mail du client
     * 
     * @return son mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * Get l'adresse du client
     * 
     * @return son adresse
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * Get la longitude du client
     * 
     * @return sa longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * Get la latitude du client
     * 
     * @return sa latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * Get la liste des commandes du client
     * 
     * @return sa liste de commandes
     */
    public List<Commande> getCommandes() {
        return commandes;
    }

    /**
     * Set le nom du client
     * 
     * @param nom nouveau nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Set le prenom du client
     * 
     * @param prenom nouveau prenom
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Set le mail du client
     * 
     * @param mail nouveau mail
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * Set l'adresse du client
     * Change aussi sa longitude et latitude
     * 
     * @param adresse nouvelle adresse
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
        this.longitude = getLatLng(adresse).lng;
        this.latitude = getLatLng(adresse).lat;
    }

    /**
     * Ajoute la commande à la liste des commande du client
     * 
     * @param c commande a ajouter
     */
    public void addCommande(Commande c) {
        commandes.add(c);
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", mail=" + mail + ", adresse=" + adresse + ", longitude=" + longitude + ", latitude=" + latitude + '}';
    }

}
