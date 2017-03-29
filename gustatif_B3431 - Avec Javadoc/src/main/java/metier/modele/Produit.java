package metier.modele;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Classe Produit, un produit proposé par un restaurant
 * @author B3431
 */
@Entity
public class Produit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String denomination;
    private String description;
    private Double poids;
    private Double prix;

    /**
     * Constructeur par default 
    */
    public Produit() {
    }

    /**
     * Constructeur de Produit
     * Initie ses attibuts
     * 
     * @param denomination la denomination
     * @param description la description
     * @param poids le poids
     * @param prix le prix
     */
    public Produit(String denomination, String description, Double poids, Double prix) {
        this.denomination = denomination;
        this.description = description;
        this.poids = poids;
        this.prix = prix;
    }

    /**
     * get l'id du produit
     * @return son id
     */
    public Long getId() {
        return id;
    }

    /**
     * get la denomination du Produit
     * 
     * @return la denomintion
     */
    public String getDenomination() {
        return denomination;
    }

    /**
     * get la description du produit
     * 
     * @return la description
     */
    public String getDescription() {
        return description;
    }

    /**
     * get le poids du produit
     * 
     * @return le poids
     */
    public Double getPoids() {
        return poids;
    }

    /**
     * get le prox du produit
     * 
     * @return le prix
     */
    public Double getPrix() {
        return prix;
    }

    /**
     * set la denomination du produit
     * 
     * @param denomination nouvelle denomination
     */
    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    /**
     * set la description du produit
     * 
     * @param description nouvelle description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * set le poids du produit
     * 
     * @param poids nouveau poids
     */
    public void setPoids(Double poids) {
        this.poids = poids;
    }

    /**
     * set le prix du produit
     * 
     * @param prix nouveau prix
     */
    public void setPrix(Double prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Produit{" + "id=" + id + ", denomination=" + denomination + ", description=" + description + ", poids=" + poids + ", prix=" + prix + '}';
    }

}
