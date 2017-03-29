package metier.modele;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Classe qui lie un produit avec sa quantite, a utiliser pour les commandes
 * @author B3431
 */
@Entity
public class Qte_Commande implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    private int quantite;

    @ManyToOne
    private Produit produit;

    /**
     * Constructeur  par default de Qte_Commande
     * 
     */
    protected Qte_Commande() {

    }

    /**
     * Constructeur de Qte_Commande
     * 
     * @param p le produit
     * @param q la quantite
     */
    public Qte_Commande(Produit p, int q) {
        produit = p;
        quantite = q;
    }

    /**
     * get le produit de la Qte_Commande
     * 
     * @return le produit
     */
    public Produit getProduit() {
        return produit;
    }

    /**
     * get la quantite de la Qte_Commande
     * 
     * @return la quantite
     */
    public int getQuantite() {
        return quantite;
    }

    /**
     * set le produit de la Qte_Commande
     * 
     * @param p nouveau produit
     */
    public void setProduit(Produit p) {
        produit = p;
    }

    /**
     * set la quantite de la Qte_Commande
     * 
     * @param q nouvelle quantite
     */
    public void setQuantite(int q) {
        quantite = q;
    }

    @Override
    public String toString() {
        return produit.getDenomination() + " a été commandé pour une quantité de " + quantite;
    }
}
