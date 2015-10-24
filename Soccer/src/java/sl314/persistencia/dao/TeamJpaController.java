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
import sl314.persistencia.Tournament;
import sl314.persistencia.League;
import sl314.persistencia.Player;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import sl314.persistencia.Team;
import sl314.persistencia.dao.exceptions.IllegalOrphanException;
import sl314.persistencia.dao.exceptions.NonexistentEntityException;
import sl314.persistencia.dao.exceptions.RollbackFailureException;

/**
 *
 * @author jhernandezn
 */
public class TeamJpaController implements Serializable {

    public TeamJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Team team) throws RollbackFailureException, Exception {
        if (team.getPlayerCollection() == null) {
            team.setPlayerCollection(new ArrayList<Player>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Tournament idTournament = team.getIdTournament();
            if (idTournament != null) {
                idTournament = em.getReference(idTournament.getClass(), idTournament.getIdTournament());
                team.setIdTournament(idTournament);
            }
            League idLeague = team.getIdLeague();
            if (idLeague != null) {
                idLeague = em.getReference(idLeague.getClass(), idLeague.getIdLeague());
                team.setIdLeague(idLeague);
            }
            Collection<Player> attachedPlayerCollection = new ArrayList<Player>();
            for (Player playerCollectionPlayerToAttach : team.getPlayerCollection()) {
                playerCollectionPlayerToAttach = em.getReference(playerCollectionPlayerToAttach.getClass(), playerCollectionPlayerToAttach.getIdPlayer());
                attachedPlayerCollection.add(playerCollectionPlayerToAttach);
            }
            team.setPlayerCollection(attachedPlayerCollection);
            em.persist(team);
            if (idTournament != null) {
                idTournament.getTeamCollection().add(team);
                idTournament = em.merge(idTournament);
            }
            if (idLeague != null) {
                idLeague.getTeamCollection().add(team);
                idLeague = em.merge(idLeague);
            }
            for (Player playerCollectionPlayer : team.getPlayerCollection()) {
                Team oldTeamIdTeamOfPlayerCollectionPlayer = playerCollectionPlayer.getTeamIdTeam();
                playerCollectionPlayer.setTeamIdTeam(team);
                playerCollectionPlayer = em.merge(playerCollectionPlayer);
                if (oldTeamIdTeamOfPlayerCollectionPlayer != null) {
                    oldTeamIdTeamOfPlayerCollectionPlayer.getPlayerCollection().remove(playerCollectionPlayer);
                    oldTeamIdTeamOfPlayerCollectionPlayer = em.merge(oldTeamIdTeamOfPlayerCollectionPlayer);
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

    public void edit(Team team) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Team persistentTeam = em.find(Team.class, team.getIdTeam());
            Tournament idTournamentOld = persistentTeam.getIdTournament();
            Tournament idTournamentNew = team.getIdTournament();
            League idLeagueOld = persistentTeam.getIdLeague();
            League idLeagueNew = team.getIdLeague();
            Collection<Player> playerCollectionOld = persistentTeam.getPlayerCollection();
            Collection<Player> playerCollectionNew = team.getPlayerCollection();
            List<String> illegalOrphanMessages = null;
            for (Player playerCollectionOldPlayer : playerCollectionOld) {
                if (!playerCollectionNew.contains(playerCollectionOldPlayer)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Player " + playerCollectionOldPlayer + " since its teamIdTeam field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idTournamentNew != null) {
                idTournamentNew = em.getReference(idTournamentNew.getClass(), idTournamentNew.getIdTournament());
                team.setIdTournament(idTournamentNew);
            }
            if (idLeagueNew != null) {
                idLeagueNew = em.getReference(idLeagueNew.getClass(), idLeagueNew.getIdLeague());
                team.setIdLeague(idLeagueNew);
            }
            Collection<Player> attachedPlayerCollectionNew = new ArrayList<Player>();
            for (Player playerCollectionNewPlayerToAttach : playerCollectionNew) {
                playerCollectionNewPlayerToAttach = em.getReference(playerCollectionNewPlayerToAttach.getClass(), playerCollectionNewPlayerToAttach.getIdPlayer());
                attachedPlayerCollectionNew.add(playerCollectionNewPlayerToAttach);
            }
            playerCollectionNew = attachedPlayerCollectionNew;
            team.setPlayerCollection(playerCollectionNew);
            team = em.merge(team);
            if (idTournamentOld != null && !idTournamentOld.equals(idTournamentNew)) {
                idTournamentOld.getTeamCollection().remove(team);
                idTournamentOld = em.merge(idTournamentOld);
            }
            if (idTournamentNew != null && !idTournamentNew.equals(idTournamentOld)) {
                idTournamentNew.getTeamCollection().add(team);
                idTournamentNew = em.merge(idTournamentNew);
            }
            if (idLeagueOld != null && !idLeagueOld.equals(idLeagueNew)) {
                idLeagueOld.getTeamCollection().remove(team);
                idLeagueOld = em.merge(idLeagueOld);
            }
            if (idLeagueNew != null && !idLeagueNew.equals(idLeagueOld)) {
                idLeagueNew.getTeamCollection().add(team);
                idLeagueNew = em.merge(idLeagueNew);
            }
            for (Player playerCollectionNewPlayer : playerCollectionNew) {
                if (!playerCollectionOld.contains(playerCollectionNewPlayer)) {
                    Team oldTeamIdTeamOfPlayerCollectionNewPlayer = playerCollectionNewPlayer.getTeamIdTeam();
                    playerCollectionNewPlayer.setTeamIdTeam(team);
                    playerCollectionNewPlayer = em.merge(playerCollectionNewPlayer);
                    if (oldTeamIdTeamOfPlayerCollectionNewPlayer != null && !oldTeamIdTeamOfPlayerCollectionNewPlayer.equals(team)) {
                        oldTeamIdTeamOfPlayerCollectionNewPlayer.getPlayerCollection().remove(playerCollectionNewPlayer);
                        oldTeamIdTeamOfPlayerCollectionNewPlayer = em.merge(oldTeamIdTeamOfPlayerCollectionNewPlayer);
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
                Integer id = team.getIdTeam();
                if (findTeam(id) == null) {
                    throw new NonexistentEntityException("The team with id " + id + " no longer exists.");
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
            Team team;
            try {
                team = em.getReference(Team.class, id);
                team.getIdTeam();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The team with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Player> playerCollectionOrphanCheck = team.getPlayerCollection();
            for (Player playerCollectionOrphanCheckPlayer : playerCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Team (" + team + ") cannot be destroyed since the Player " + playerCollectionOrphanCheckPlayer + " in its playerCollection field has a non-nullable teamIdTeam field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Tournament idTournament = team.getIdTournament();
            if (idTournament != null) {
                idTournament.getTeamCollection().remove(team);
                idTournament = em.merge(idTournament);
            }
            League idLeague = team.getIdLeague();
            if (idLeague != null) {
                idLeague.getTeamCollection().remove(team);
                idLeague = em.merge(idLeague);
            }
            em.remove(team);
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

    public List<Team> findTeamEntities() {
        return findTeamEntities(true, -1, -1);
    }

    public List<Team> findTeamEntities(int maxResults, int firstResult) {
        return findTeamEntities(false, maxResults, firstResult);
    }

    private List<Team> findTeamEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Team.class));
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

    public Team findTeam(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Team.class, id);
        } finally {
            em.close();
        }
    }

    public int getTeamCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Team> rt = cq.from(Team.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
