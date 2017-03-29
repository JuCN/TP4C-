package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Livreur;
import metier.modele.LivreurHumain;

/**
 * DAO pour Livreur
 * @author B3431
 */
public class LivreurDAO {

    /**
     * Persiste le livreur dans la base de donnees
     * Lance une exception dans le cas contraire
     * 
     * @param livreur le livreur
     * @throws Exception si il n'est pas cree
     */
    public void create(Livreur livreur) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(livreur);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Met a jour le livreur dans la base de donnees
     * Lance une exception dans le cas contraire
     *
     * @param livreur le livreur
     * @throws Exception si il n'est pas mis a jour
     */
    public void update(Livreur livreur) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.merge(livreur);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Trouve un livreur humain de la base de donnees grace a son adresse mail
     * renvoie le libreur correspondant ou null s'il n'existe pas
     * Lance aussi une exception s'il n'existe pas
     * 
     * @param mail le mail
     * @return le livreur recherche
     * @throws Exception si la requete ne marche pas
     */
    public LivreurHumain findLivreurByMail(String mail) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        LivreurHumain livreur = null;
        try {
            Query q = em.createQuery("SELECT cl FROM LivreurHumain cl WHERE cl.mail like :mail");
            q.setParameter("mail", mail);
            livreur = (LivreurHumain) q.getResultList().get(q.getFirstResult());
        } catch (Exception e) {
            throw e;
        }
        return livreur;
    }

    /**
     * Renvoie la liste de tous les livreurs de la base de donnees
     * 
     * @return la liste de livreurs
     * @throws Exception s'il y a un probleme lors de la requete
     */
    public List<Livreur> findAll() throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Livreur> livreurs = null;
        try {
            Query q = em.createQuery("SELECT c FROM Livreur c");
            livreurs = (List<Livreur>) q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return livreurs;
    }

}
