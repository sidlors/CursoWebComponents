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
@Table(name = "tournament")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tournament.findAll", query = "SELECT t FROM Tournament t"),
    @NamedQuery(name = "Tournament.findByIdTournament", query = "SELECT t FROM Tournament t WHERE t.idTournament = :idTournament"),
    @NamedQuery(name = "Tournament.findByDivision", query = "SELECT t FROM Tournament t WHERE t.division = :division"),
    @NamedQuery(name = "Tournament.findByIsProfessional", query = "SELECT t FROM Tournament t WHERE t.isProfessional = :isProfessional")})
public class Tournament implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_TOURNAMENT")
    private Integer idTournament;
    @Size(max = 45)
    @Column(name = "DIVISION")
    private String division;
    @Column(name = "IS_PROFESSIONAL")
    private Boolean isProfessional;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTournament")
    private Collection<Team> teamCollection;

    public Tournament() {
    }

    public Tournament(Integer idTournament) {
        this.idTournament = idTournament;
    }

    public Integer getIdTournament() {
        return idTournament;
    }

    public void setIdTournament(Integer idTournament) {
        this.idTournament = idTournament;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public Boolean getIsProfessional() {
        return isProfessional;
    }

    public void setIsProfessional(Boolean isProfessional) {
        this.isProfessional = isProfessional;
    }

    @XmlTransient
    public Collection<Team> getTeamCollection() {
        return teamCollection;
    }

    public void setTeamCollection(Collection<Team> teamCollection) {
        this.teamCollection = teamCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTournament != null ? idTournament.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tournament)) {
            return false;
        }
        Tournament other = (Tournament) object;
        if ((this.idTournament == null && other.idTournament != null) || (this.idTournament != null && !this.idTournament.equals(other.idTournament))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sl314.persisnten.Tournament[ idTournament=" + idTournament + " ]";
    }
    
}
