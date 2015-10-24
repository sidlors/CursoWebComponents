/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sl314.persistencia.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import sl314.persistencia.Player;
import sl314.persistencia.Team;
import sl314.persistencia.dao.exceptions.NonexistentEntityException;
import sl314.persistencia.dao.exceptions.RollbackFailureException;

/**
 *
 * @author jhernandezn
 */
public class PlayerJpaController implements Serializable {

    public PlayerJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Player player) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Team teamIdTeam = player.getTeamIdTeam();
            if (teamIdTeam != null) {
                teamIdTeam = em.getReference(teamIdTeam.getClass(), teamIdTeam.getIdTeam());
                player.setTeamIdTeam(teamIdTeam);
            }
            em.persist(player);
            if (teamIdTeam != null) {
                teamIdTeam.getPlayerCollection().add(player);
                teamIdTeam = em.merge(teamIdTeam);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Player player) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Player persistentPlayer = em.find(Player.class, player.getIdPlayer());
            Team teamIdTeamOld = persistentPlayer.getTeamIdTeam();
            Team teamIdTeamNew = player.getTeamIdTeam();
            if (teamIdTeamNew != null) {
                teamIdTeamNew = em.getReference(teamIdTeamNew.getClass(), teamIdTeamNew.getIdTeam());
                player.setTeamIdTeam(teamIdTeamNew);
            }
            player = em.merge(player);
            if (teamIdTeamOld != null && !teamIdTeamOld.equals(teamIdTeamNew)) {
                teamIdTeamOld.getPlayerCollection().remove(player);
                teamIdTeamOld = em.merge(teamIdTeamOld);
            }
            if (teamIdTeamNew != null && !teamIdTeamNew.equals(teamIdTeamOld)) {
                teamIdTeamNew.getPlayerCollection().add(player);
                teamIdTeamNew = em.merge(teamIdTeamNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = player.getIdPlayer();
                if (findPlayer(id) == null) {
                    throw new NonexistentEntityException("The player with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Player player;
            try {
                player = em.getReference(Player.class, id);
                player.getIdPlayer();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The player with id " + id + " no longer exists.", enfe);
            }
            Team teamIdTeam = player.getTeamIdTeam();
            if (teamIdTeam != null) {
                teamIdTeam.getPlayerCollection().remove(player);
                teamIdTeam = em.merge(teamIdTeam);
            }
            em.remove(player);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Player> findPlayerEntities() {
        return findPlayerEntities(true, -1, -1);
    }

    public List<Player> findPlayerEntities(int maxResults, int firstResult) {
        return findPlayerEntities(false, maxResults, firstResult);
    }

    private List<Player> findPlayerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Player.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Player findPlayer(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Player.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlayerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Player> rt = cq.from(Player.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
