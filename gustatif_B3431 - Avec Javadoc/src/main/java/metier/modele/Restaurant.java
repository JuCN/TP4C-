package metier.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import static util.GeoTest.getLatLng;

/**
 * Classe Restaurant, les restaurants qui proposent des produits a livrer
 * 
 * @author B3431
 */
@Entity
public class Restaurant implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String denomination;
    private String description;
    private String adresse;
    private Double longitude;
    private Double latitude;

    @OneToMany
    private List<Produit> produits;

    /**
     * Constructeur par default
     */
    protected Restaurant() {
        this.produits = new ArrayList<>();
    }

    /**
     * Constructeur de restaurant
     * Initie ses attributs
     * Effectue les calculs de longitude / latitude
     * 
     * @param denomination la denomination
     * @param description la description
     * @param adresse l'adresse
     */
    public Restaurant(String denomination, String description, String adresse) {
        this.denomination = denomination;
        this.description = description;
        this.adresse = adresse;
        this.longitude = getLatLng(adresse).lng;
        this.latitude = getLatLng(adresse).lat;
        this.produits = new ArrayList<>();
    }

    /**
     * get l'id du restaurant
     * 
     * @return son id
     */
    public Long getId() {
        return id;
    }

    /**
     * get la denomination du restaurant
     * 
     * @return sa denomination
     */
    public String getDenomination() {
        return denomination;
    }

    /**
     * get la description du restaurant
     * 
     * @return sa description
     */
    public String getDescription() {
        return description;
    }

    /**
     * get l'adressse du restaurant
     * @return son adresse
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * get la longitude du restaurant
     * 
     * @return sa longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * get la latitude du restaurant
     * 
     * @return sa latitude
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * get la liste des produits du restaurant
     * 
     * @return sa liste de produits
     */
    public List<Produit> getProduits() {
        return produits;
    }

    /**
     * set la denomination du restaurant
     * 
     * @param denomination nouvelle denomination
     */
    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    /**
     * set la description du restaurant
     * 
     * @param description nouvelle description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * set l'adresse du restaurant
     * Modifie la latitude et longitude
     * 
     * @param adresse nouvelle adresse
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
        this.longitude = getLatLng(adresse).lng;
        this.latitude = getLatLng(adresse).lat;
    }

    /**
     * ajoute un produit au restaurant
     * 
     * @param produit nouveau produit
     */
    public void addProduit(Produit produit) {
        this.produits.add(produit);
    }

    @Override
    public String toString() {
        return "Restaurant{" + "id=" + id + ", denomination=" + denomination + ", description=" + description + ", adresse=" + adresse + ", longitude=" + longitude + ", latitude=" + latitude + ", produits=" + produits + '}';
    }

}
