/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sl314.persistencia;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.CriteriaQuery;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author juanmanuel
 */
@Entity
@Table(name = "league")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "League.findAll", query = "SELECT l FROM League l"),
    @NamedQuery(name = "League.findByIdLeague", query = "SELECT l FROM League l WHERE l.idLeague = :idLeague"),
    @NamedQuery(name = "League.findByName", query = "SELECT l FROM League l WHERE l.name = :name"),
    @NamedQuery(name = "League.findByYear", query = "SELECT l FROM League l WHERE l.year = :year")})
public class League implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_LEAGUE")
    private Integer idLeague;
    @Size(max = 45)
    @Column(name = "NAME")
    private String name;
    @Column(name = "YEAR")
    private Integer year;
    @Column(name = "STATUS")
    private Integer status;
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idLeague")
    private Collection<Team> teamCollection;

    public League() {
    }

    public League(Integer idLeague) {
        this.idLeague = idLeague;
    }

    public Integer getIdLeague() {
        return idLeague;
    }

    public void setIdLeague(Integer idLeague) {
        this.idLeague = idLeague;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @XmlTransient
    public Collection<Team> getTeamCollection() {
        return teamCollection;
    }

    public void setTeamCollection(Collection<Team> teamCollection) {
        this.teamCollection = teamCollection;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLeague != null ? idLeague.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof League)) {
            return false;
        }
        League other = (League) object;
        if ((this.idLeague == null && other.idLeague != null) || (this.idLeague != null && !this.idLeague.equals(other.idLeague))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sl314.persisnten.League[ idLeague=" + idLeague + " ]";
    }
    
    
     public List<League> findLeagueEntities(EntityManager em, boolean all, int maxResults, int firstResult) {
        
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
    
}
