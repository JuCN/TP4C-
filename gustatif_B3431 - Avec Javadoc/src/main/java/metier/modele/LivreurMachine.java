package metier.modele;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 * Classe Livreur pour les drones, herite de Livreur
 * 
 * @author B3431
 */
@Entity
public class LivreurMachine extends Livreur implements Serializable {

    private String denomination;

    private double vitesse;

    /**
     * Constructeur par default
     */
    protected LivreurMachine() {

    }

    /**
     * Constructeur de Livreur Machine
     * Initie ses attributs
     * 
     * @param a l'adresse
     * @param c la capacite
     * @param den la denomination
     * @param v la vitesse
     */
    public LivreurMachine(String a, Double c, String den, double v) {
        super(a, c);
        denomination = den;
        vitesse = v;
    }

    /**
     * get la denomination du drone
     * 
     * @return la denomination
     */
    public String getDenomination() {
        return denomination;
    }

    /**
     * set la denomination du drone
     * 
     * @param d nouvelle denomination
     */
    public void setDenomination(String d) {
        denomination = d;
    }

    @Override
    public double getVitesse() {
        return vitesse;
    }

    /**
     * set la vitesse du drone
     * 
     * @param v nouvelle vitesse
     */
    public void setVitesse(double v) {
        vitesse = v;
    }

    @Override
    public String toString() {
        return "LivreurMachine :{" + "id=" + id + ", adresse=" + adresse + ", vitesse=" + vitesse + ", capacité=" + capacite + ", disponibilité=" + disponibilite + '}';
    }

    @Override
    public String getNom() {
        return getDenomination();
    }

}
