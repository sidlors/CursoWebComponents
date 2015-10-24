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
import sl314.persistencia.Tournament;
import sl314.persistencia.dao.exceptions.IllegalOrphanException;
import sl314.persistencia.dao.exceptions.NonexistentEntityException;
import sl314.persistencia.dao.exceptions.RollbackFailureException;

/**
 *
 * @author jhernandezn
 */
public class TournamentJpaController implements Serializable {

    public TournamentJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tournament tournament) throws RollbackFailureException, Exception {
        if (tournament.getTeamCollection() == null) {
            tournament.setTeamCollection(new ArrayList<Team>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Team> attachedTeamCollection = new ArrayList<Team>();
            for (Team teamCollectionTeamToAttach : tournament.getTeamCollection()) {
                teamCollectionTeamToAttach = em.getReference(teamCollectionTeamToAttach.getClass(), teamCollectionTeamToAttach.getIdTeam());
                attachedTeamCollection.add(teamCollectionTeamToAttach);
            }
            tournament.setTeamCollection(attachedTeamCollection);
            em.persist(tournament);
            for (Team teamCollectionTeam : tournament.getTeamCollection()) {
                Tournament oldIdTournamentOfTeamCollectionTeam = teamCollectionTeam.getIdTournament();
                teamCollectionTeam.setIdTournament(tournament);
                teamCollectionTeam = em.merge(teamCollectionTeam);
                if (oldIdTournamentOfTeamCollectionTeam != null) {
                    oldIdTournamentOfTeamCollectionTeam.getTeamCollection().remove(teamCollectionTeam);
                    oldIdTournamentOfTeamCollectionTeam = em.merge(oldIdTournamentOfTeamCollectionTeam);
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

    public void edit(Tournament tournament) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Tournament persistentTournament = em.find(Tournament.class, tournament.getIdTournament());
            Collection<Team> teamCollectionOld = persistentTournament.getTeamCollection();
            Collection<Team> teamCollectionNew = tournament.getTeamCollection();
            List<String> illegalOrphanMessages = null;
            for (Team teamCollectionOldTeam : teamCollectionOld) {
                if (!teamCollectionNew.contains(teamCollectionOldTeam)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Team " + teamCollectionOldTeam + " since its idTournament field is not nullable.");
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
            tournament.setTeamCollection(teamCollectionNew);
            tournament = em.merge(tournament);
            for (Team teamCollectionNewTeam : teamCollectionNew) {
                if (!teamCollectionOld.contains(teamCollectionNewTeam)) {
                    Tournament oldIdTournamentOfTeamCollectionNewTeam = teamCollectionNewTeam.getIdTournament();
                    teamCollectionNewTeam.setIdTournament(tournament);
                    teamCollectionNewTeam = em.merge(teamCollectionNewTeam);
                    if (oldIdTournamentOfTeamCollectionNewTeam != null && !oldIdTournamentOfTeamCollectionNewTeam.equals(tournament)) {
                        oldIdTournamentOfTeamCollectionNewTeam.getTeamCollection().remove(teamCollectionNewTeam);
                        oldIdTournamentOfTeamCollectionNewTeam = em.merge(oldIdTournamentOfTeamCollectionNewTeam);
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
                Integer id = tournament.getIdTournament();
                if (findTournament(id) == null) {
                    throw new NonexistentEntityException("The tournament with id " + id + " no longer exists.");
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
            Tournament tournament;
            try {
                tournament = em.getReference(Tournament.class, id);
                tournament.getIdTournament();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tournament with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Team> teamCollectionOrphanCheck = tournament.getTeamCollection();
            for (Team teamCollectionOrphanCheckTeam : teamCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tournament (" + tournament + ") cannot be destroyed since the Team " + teamCollectionOrphanCheckTeam + " in its teamCollection field has a non-nullable idTournament field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tournament);
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

    public List<Tournament> findTournamentEntities() {
        return findTournamentEntities(true, -1, -1);
    }

    public List<Tournament> findTournamentEntities(int maxResults, int firstResult) {
        return findTournamentEntities(false, maxResults, firstResult);
    }

    private List<Tournament> findTournamentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tournament.class));
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

    public Tournament findTournament(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tournament.class, id);
        } finally {
            em.close();
        }
    }

    public int getTournamentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tournament> rt = cq.from(Tournament.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
