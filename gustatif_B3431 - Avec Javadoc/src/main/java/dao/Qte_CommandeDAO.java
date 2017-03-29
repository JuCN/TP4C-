package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Qte_Commande;

/**
 * DAO pour Qte_Commande
 * 
 * @author B3431
 */
public class Qte_CommandeDAO {

    /**
     *
     * @param qte_Commande l'objet quantite commande
     * @throws Exception si echec de la creation
     */
    public void create(Qte_Commande qte_Commande) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(qte_Commande);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     *
     * @param id id de la qte_commande
     * @return la qte_commande
     * @throws Exception s'il ne la trouve pas
     */
    public Qte_Commande findById(long id) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Qte_Commande qte_Commande = null;
        try {
            qte_Commande = em.find(Qte_Commande.class, id);
        } catch (Exception e) {
            throw e;
        }
        return qte_Commande;
    }

    /**
     *
     * @return la liste de qte_commande
     * @throws Exception si la requete se passe mal
     */
    public List<Qte_Commande> findAll() throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Qte_Commande> qte_Commandes = null;
        try {
            Query q = em.createQuery("SELECT c FROM Qte_Commande c");
            qte_Commandes = (List<Qte_Commande>) q.getResultList();
        } catch (Exception e) {
            throw e;
        }

        return qte_Commandes;
    }

}
