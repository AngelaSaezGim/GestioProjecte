/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

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

/**
 *
 * @author angsaegim
 */
@Entity
@Table(name = "tascaas01")
@NamedQueries({
    @NamedQuery(name = "Tascaas01.findAll", query = "SELECT t FROM Tascaas01 t"),
    @NamedQuery(name = "Tascaas01.findByIdTasca", query = "SELECT t FROM Tascaas01 t WHERE t.idTasca = :idTasca"),
    @NamedQuery(name = "Tascaas01.findByDescripcio", query = "SELECT t FROM Tascaas01 t WHERE t.descripcio = :descripcio"),
    @NamedQuery(name = "Tascaas01.findByEstat", query = "SELECT t FROM Tascaas01 t WHERE t.estat = :estat")})
public class Tascaas01 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTasca")
    private Integer idTasca;
    @Column(name = "descripcio")
    private String descripcio;
    @Column(name = "estat")
    private String estat;
    @JoinColumn(name = "idProjecte", referencedColumnName = "idProjecte")
    @ManyToOne
    private Projecteas01 idProjecte;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTasca")
    private Collection<Facturaas01> facturaas01Collection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTasca")
    private Collection<Operariresponsableas01> operariresponsableas01Collection;

    public Tascaas01() {
    }

    public Tascaas01(Integer idTasca) {
        this.idTasca = idTasca;
    }

    public Integer getIdTasca() {
        return idTasca;
    }

    public void setIdTasca(Integer idTasca) {
        this.idTasca = idTasca;
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

    public Projecteas01 getIdProjecte() {
        return idProjecte;
    }

    public void setIdProjecte(Projecteas01 idProjecte) {
        this.idProjecte = idProjecte;
    }

    public Collection<Facturaas01> getFacturaas01Collection() {
        return facturaas01Collection;
    }

    public void setFacturaas01Collection(Collection<Facturaas01> facturaas01Collection) {
        this.facturaas01Collection = facturaas01Collection;
    }

    public Collection<Operariresponsableas01> getOperariresponsableas01Collection() {
        return operariresponsableas01Collection;
    }

    public void setOperariresponsableas01Collection(Collection<Operariresponsableas01> operariresponsableas01Collection) {
        this.operariresponsableas01Collection = operariresponsableas01Collection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTasca != null ? idTasca.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tascaas01)) {
            return false;
        }
        Tascaas01 other = (Tascaas01) object;
        if ((this.idTasca == null && other.idTasca != null) || (this.idTasca != null && !this.idTasca.equals(other.idTasca))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Tascaas01[ idTasca=" + idTasca + " ]";
    }
    
}
