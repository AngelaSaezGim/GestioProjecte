/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
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

/**
 *
 * @author angsaegim
 */
@Entity
@Table(name = "projecteas01")
@NamedQueries({
    @NamedQuery(name = "Projecteas01.findAll", query = "SELECT p FROM Projecteas01 p"),
    @NamedQuery(name = "Projecteas01.findByIdProjecte", query = "SELECT p FROM Projecteas01 p WHERE p.idProjecte = :idProjecte"),
    @NamedQuery(name = "Projecteas01.findByDescripcio", query = "SELECT p FROM Projecteas01 p WHERE p.descripcio = :descripcio"),
    @NamedQuery(name = "Projecteas01.findByEstat", query = "SELECT p FROM Projecteas01 p WHERE p.estat = :estat")})
public class Projecteas01 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idProjecte")
    private Integer idProjecte;
    @Column(name = "descripcio")
    private String descripcio;
    @Column(name = "estat")
    private String estat;
    @OneToMany(mappedBy = "idProjecte")
    private Collection<Tascaas01> tascaas01Collection;
    @JoinColumn(name = "idClient", referencedColumnName = "idClient")
    @ManyToOne
    private Clientas01 idClient;

    public Projecteas01() {
    }

    public Projecteas01(Integer idProjecte) {
        this.idProjecte = idProjecte;
    }

    public Integer getIdProjecte() {
        return idProjecte;
    }

    public void setIdProjecte(Integer idProjecte) {
        this.idProjecte = idProjecte;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public String getEstat() {
        return estat;
    }

    public void setEstat(String estat) {
        this.estat = estat;
    }

    public Collection<Tascaas01> getTascaas01Collection() {
        return tascaas01Collection;
    }

    public void setTascaas01Collection(Collection<Tascaas01> tascaas01Collection) {
        this.tascaas01Collection = tascaas01Collection;
    }

    public Clientas01 getIdClient() {
        return idClient;
    }

    public void setIdClient(Clientas01 idClient) {
        this.idClient = idClient;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProjecte != null ? idProjecte.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Projecteas01)) {
            return false;
        }
        Projecteas01 other = (Projecteas01) object;
        if ((this.idProjecte == null && other.idProjecte != null) || (this.idProjecte != null && !this.idProjecte.equals(other.idProjecte))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Projecteas01[ idProjecte=" + idProjecte + " ]";
    }
    
}
