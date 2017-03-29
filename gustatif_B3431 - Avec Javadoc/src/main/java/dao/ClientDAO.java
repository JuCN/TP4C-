package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Client;

/**
 * DAO pour Client
 * 
 * @author B431
 */
public class ClientDAO {

    /**
     * Persiste le client dans la base de donnees
     * Lance une exception dans le cas contraire
     * 
     * @param client le client
     * @throws Exception si la creation a echoue
     */
    public void create(Client client) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(client);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Trouve un client de la base de donnees grace a son adresse mail
     * renvoie le client correspondant ou null s'il n'existe pas
     * Lance aussi une exception s'il n'existe pas
     * 
     * @param mail la mail du client
     * @return le client trouve
     * @throws Exception si le client n'est pas trouve
     */
    public Client findClientByMail(String mail) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Client client = null;
        try {
            Query q = em.createQuery("SELECT cl FROM Client cl WHERE cl.mail like :mail");
            q.setParameter("mail", mail);
            client = (Client) q.getResultList().get(q.getFirstResult());
        } catch (Exception e) {
            throw e;
        }
        return client;
    }

    /**
     * Renvoie la liste de tous les clients de la base de donnees
     * 
     * @return la liste des clients
     * @throws Exception si l'operation ne s'est pas bien passee
     */
    public List<Client> findAll() throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Client> clients = null;
        try {
            Query q = em.createQuery("SELECT c FROM Client c");
            clients = (List<Client>) q.getResultList();
        } catch (Exception e) {
            throw e;
        }

        return clients;
    }
    
}
