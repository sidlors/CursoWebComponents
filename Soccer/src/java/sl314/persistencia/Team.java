/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sl314.persistencia;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author juanmanuel
 */
@Entity
@Table(name = "team")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Team.findAll", query = "SELECT t FROM Team t"),
    @NamedQuery(name = "Team.findByIdTeam", query = "SELECT t FROM Team t WHERE t.idTeam = :idTeam"),
    @NamedQuery(name = "Team.findByName", query = "SELECT t FROM Team t WHERE t.name = :name"),
    @NamedQuery(name = "Team.findByFundation", query = "SELECT t FROM Team t WHERE t.fundation = :fundation"),
    @NamedQuery(name = "Team.findByLocation", query = "SELECT t FROM Team t WHERE t.location = :location"),
    @NamedQuery(name = "Team.findByStatus", query = "SELECT t FROM Team t WHERE t.status = :status")})
public class Team implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_TEAM")
    private Integer idTeam;
    @Size(max = 45)
    @Column(name = "NAME")
    private String name;
    @Column(name = "FUNDATION")
    private Integer fundation;
    @Size(max = 45)
    @Column(name = "LOCATION")
    private String location;
    @Column(name = "STATUS")
    private Boolean status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "teamIdTeam")
    private Collection<Player> playerCollection;
    @JoinColumn(name = "ID_TOURNAMENT", referencedColumnName = "ID_TOURNAMENT")
    @ManyToOne(optional = false)
    private Tournament idTournament;
    @JoinColumn(name = "ID_LEAGUE", referencedColumnName = "ID_LEAGUE")
    @ManyToOne(optional = false)
    private League idLeague;

    public Team() {
    }

    public Team(Integer idTeam) {
        this.idTeam = idTeam;
    }

    public Integer getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(Integer idTeam) {
        this.idTeam = idTeam;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFundation() {
        return fundation;
    }

    public void setFundation(Integer fundation) {
        this.fundation = fundation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @XmlTransient
    public Collection<Player> getPlayerCollection() {
        return playerCollection;
    }

    public void setPlayerCollection(Collection<Player> playerCollection) {
        this.playerCollection = playerCollection;
    }

    public Tournament getIdTournament() {
        return idTournament;
    }

    public void setIdTournament(Tournament idTournament) {
        this.idTournament = idTournament;
    }

    public League getIdLeague() {
        return idLeague;
    }

    public void setIdLeague(League idLeague) {
        this.idLeague = idLeague;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTeam != null ? idTeam.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Team)) {
            return false;
        }
        Team other = (Team) object;
        if ((this.idTeam == null && other.idTeam != null) || (this.idTeam != null && !this.idTeam.equals(other.idTeam))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sl314.persisnten.Team[ idTeam=" + idTeam + " ]";
    }
    
}
