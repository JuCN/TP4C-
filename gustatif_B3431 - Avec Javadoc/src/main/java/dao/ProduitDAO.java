package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Produit;

/**
 * DAO pour Produit
 *
 * @author B3431
 */
public class ProduitDAO {

    /**
     *
     * @param id id de la peoduit
     * @return le produit
     * @throws Exception s'il ne trouve pas
     */
    public Produit findById(long id) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Produit produit = null;
        try {
            produit = em.find(Produit.class, id);
        } catch (Exception e) {
            throw e;
        }
        return produit;
    }

    /**
     *
     * @return la liste des produits
     * @throws Exception si la requete ne se passe pas bien
     */
    public List<Produit> findAll() throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Produit> produits = null;
        try {
            Query q = em.createQuery("SELECT p FROM Produit p");
            produits = (List<Produit>) q.getResultList();
        } catch (Exception e) {
            throw e;
        }

        return produits;
    }

}
