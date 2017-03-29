package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Commande;

/**
 * DAO pour Commande
 *
 * @author B3431
 */
public class CommandeDAO {

    /**
     * Persiste la commande dans la base de donnees
     * Lance une exception dans le cas contraire
     *
     * @param commande la commande
     * @throws Exception si il n'a pas ete cree
     */
    public void create(Commande commande) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(commande);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Met a jour la commande dans la base de donnees
     * Lance une exception dans le cas contraire
     *
     * @param commande la commande
     * @throws Exception si l'operation ne s'est pas bien passee
     */
    public void update(Commande commande) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.merge(commande);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Renvoie la liste de toutes les commandes de la base de donnees
     *
     * @return la liste des commandes
     * @throws Exception si l'operation ne s'est pas bien passee
     */
    public List<Commande> findAll() throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Commande> commandes = null;
        try {
            Query q = em.createQuery("SELECT c FROM Commande c");
            commandes = (List<Commande>) q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return commandes;
    }

}
