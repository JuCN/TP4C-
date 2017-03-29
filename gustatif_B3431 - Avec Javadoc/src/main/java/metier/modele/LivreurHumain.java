package metier.modele;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 * Classe Livreur pour les cyclistes, herite de Livreur
 * 
 * @author B3431
 */
@Entity
public class LivreurHumain extends Livreur implements Serializable {

    private String nom;
    private String prenom;
    private String mail;

    /**
     * Constructeur par default
     */
    protected LivreurHumain() {

    }

    /**
     * Constructeur de Livreur Humain
     * Initie ses attributs
     * 
     * @param a l'adresse
     * @param c la capacite
     * @param n le nom
     * @param p le prenom
     * @param m le mail
     */
    public LivreurHumain(String a, Double c, String n, String p, String m) {
        super(a, c);
        nom = n;
        prenom = p;
        mail = m;
    }

    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public String getPrenom() {
        return prenom;
    }

    @Override
    public String getMail() {
        return mail;
    }

    /**
     * set le nom du livreur humain
     * 
     * @param nom le nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * set le prenom du livreur humain
     * 
     * @param prenom le prenom
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * set le mail du livreur humain
     * 
     * @param mail le mail
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "Livreur :{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", mail=" + mail + ", adresse=" + adresse + ", capacité=" + capacite + ", disponibilité=" + disponibilite + '}';
    }
}
