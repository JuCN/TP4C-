package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Restaurant;

/**
 * DAO pour staurant
 * 
 * @author B431
 */
public class RestaurantDAO {

    /**
     * Renvoie tous les restaurants de la base de donnees
     * 
     * @return la liste des restaurants
     * @throws Exception si un probleme de la requete
     */
    public List<Restaurant> findAll() throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Restaurant> restaurants = null;
        try {
            Query q = em.createQuery("SELECT r FROM Restaurant r");
            restaurants = (List<Restaurant>) q.getResultList();
        } catch (Exception e) {
            throw e;
        }

        return restaurants;
    }

}
