/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author angsaegim
 */
@Entity
@Table(name = "facturaas01")
@NamedQueries({
    @NamedQuery(name = "Facturaas01.findAll", query = "SELECT f FROM Facturaas01 f"),
    @NamedQuery(name = "Facturaas01.findByIdFactura", query = "SELECT f FROM Facturaas01 f WHERE f.idFactura = :idFactura"),
    @NamedQuery(name = "Facturaas01.findByData", query = "SELECT f FROM Facturaas01 f WHERE f.data = :data"),
    @NamedQuery(name = "Facturaas01.findByImport1", query = "SELECT f FROM Facturaas01 f WHERE f.import1 = :import1"),
    @NamedQuery(name = "Facturaas01.findByObservacions", query = "SELECT f FROM Facturaas01 f WHERE f.observacions = :observacions")})
public class Facturaas01 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idFactura")
    private Integer idFactura;
    @Basic(optional = false)
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Basic(optional = false)
    @Column(name = "import")
    private double import1;
    @Column(name = "observacions")
    private String observacions;
    @JoinColumn(name = "idTasca", referencedColumnName = "idTasca")
    @ManyToOne(optional = false)
    private Tascaas01 idTasca;
    @JoinColumn(name = "idClient", referencedColumnName = "idClient")
    @ManyToOne(optional = false)
    private Clientas01 idClient;

    public Facturaas01() {
    }

    public Facturaas01(Integer idFactura) {
        this.idFactura = idFactura;
    }

    public Facturaas01(Integer idFactura, Date data, double import1) {
        this.idFactura = idFactura;
        this.data = data;
        this.import1 = import1;
    }

    public Integer getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Integer idFactura) {
        this.idFactura = idFactura;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getImport1() {
        return import1;
    }

    public void setImport1(double import1) {
        this.import1 = import1;
    }

    public String getObservacions() {
        return observacions;
    }

    public void setObservacions(String observacions) {
        this.observacions = observacions;
    }

    public Tascaas01 getIdTasca() {
        return idTasca;
    }

    public void setIdTasca(Tascaas01 idTasca) {
        this.idTasca = idTasca;
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
        hash += (idFactura != null ? idFactura.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Facturaas01)) {
            return false;
        }
        Facturaas01 other = (Facturaas01) object;
        if ((this.idFactura == null && other.idFactura != null) || (this.idFactura != null && !this.idFactura.equals(other.idFactura))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Facturaas01[ idFactura=" + idFactura + " ]";
    }
    
}
