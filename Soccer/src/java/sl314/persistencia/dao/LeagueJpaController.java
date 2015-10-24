/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sl314.persistencia.dao;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import sl314.persistencia.Team;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import sl314.persistencia.League;
import sl314.persistencia.dao.exceptions.IllegalOrphanException;
import sl314.persistencia.dao.exceptions.NonexistentEntityException;
import sl314.persistencia.dao.exceptions.RollbackFailureException;

/**
 *
 * @author jhernandezn
 */
public class LeagueJpaController implements Serializable {

    public LeagueJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(League league) throws RollbackFailureException, Exception {
        if (league.getTeamCollection() == null) {
            league.setTeamCollection(new ArrayList<Team>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Team> attachedTeamCollection = new ArrayList<Team>();
            for (Team teamCollectionTeamToAttach : league.getTeamCollection()) {
                teamCollectionTeamToAttach = em.getReference(teamCollectionTeamToAttach.getClass(), teamCollectionTeamToAttach.getIdTeam());
                attachedTeamCollection.add(teamCollectionTeamToAttach);
            }
            league.setTeamCollection(attachedTeamCollection);
            em.persist(league);
            for (Team teamCollectionTeam : league.getTeamCollection()) {
                League oldIdLeagueOfTeamCollectionTeam = teamCollectionTeam.getIdLeague();
                teamCollectionTeam.setIdLeague(league);
                teamCollectionTeam = em.merge(teamCollectionTeam);
                if (oldIdLeagueOfTeamCollectionTeam != null) {
                    oldIdLeagueOfTeamCollectionTeam.getTeamCollection().remove(teamCollectionTeam);
                    oldIdLeagueOfTeamCollectionTeam = em.merge(oldIdLeagueOfTeamCollectionTeam);
                }
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

    public void edit(League league) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            League persistentLeague = em.find(League.class, league.getIdLeague());
            Collection<Team> teamCollectionOld = persistentLeague.getTeamCollection();
            Collection<Team> teamCollectionNew = league.getTeamCollection();
            List<String> illegalOrphanMessages = null;
            for (Team teamCollectionOldTeam : teamCollectionOld) {
                if (!teamCollectionNew.contains(teamCollectionOldTeam)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Team " + teamCollectionOldTeam + " since its idLeague field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Team> attachedTeamCollectionNew = new ArrayList<Team>();
            for (Team teamCollectionNewTeamToAttach : teamCollectionNew) {
                teamCollectionNewTeamToAttach = em.getReference(teamCollectionNewTeamToAttach.getClass(), teamCollectionNewTeamToAttach.getIdTeam());
                attachedTeamCollectionNew.add(teamCollectionNewTeamToAttach);
            }
            teamCollectionNew = attachedTeamCollectionNew;
            league.setTeamCollection(teamCollectionNew);
            league = em.merge(league);
            for (Team teamCollectionNewTeam : teamCollectionNew) {
                if (!teamCollectionOld.contains(teamCollectionNewTeam)) {
                    League oldIdLeagueOfTeamCollectionNewTeam = teamCollectionNewTeam.getIdLeague();
                    teamCollectionNewTeam.setIdLeague(league);
                    teamCollectionNewTeam = em.merge(teamCollectionNewTeam);
                    if (oldIdLeagueOfTeamCollectionNewTeam != null && !oldIdLeagueOfTeamCollectionNewTeam.equals(league)) {
                        oldIdLeagueOfTeamCollectionNewTeam.getTeamCollection().remove(teamCollectionNewTeam);
                        oldIdLeagueOfTeamCollectionNewTeam = em.merge(oldIdLeagueOfTeamCollectionNewTeam);
                    }
                }
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
                Integer id = league.getIdLeague();
                if (findLeague(id) == null) {
                    throw new NonexistentEntityException("The league with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            League league;
            try {
                league = em.getReference(League.class, id);
                league.getIdLeague();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The league with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Team> teamCollectionOrphanCheck = league.getTeamCollection();
            for (Team teamCollectionOrphanCheckTeam : teamCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This League (" + league + ") cannot be destroyed since the Team " + teamCollectionOrphanCheckTeam + " in its teamCollection field has a non-nullable idLeague field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(league);
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

    public List<League> findLeagueEntities() {
        return findLeagueEntities(true, -1, -1);
    }

    public List<League> findLeagueEntities(int maxResults, int firstResult) {
        return findLeagueEntities(false, maxResults, firstResult);
    }

    private List<League> findLeagueEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(League.class));
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

    public League findLeague(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(League.class, id);
        } finally {
            em.close();
        }
    }

    public int getLeagueCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<League> rt = cq.from(League.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
