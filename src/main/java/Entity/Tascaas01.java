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
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author angsaegim
 */
@Entity
@Table(name = "tascaas01")
@NamedQueries({
    // BUSCA TODAS LAS TAREAS O LAS TAREAS POR SUS ATRIBUTOS
    @NamedQuery(name = "Tascaas01.findAll", query = "SELECT t FROM Tascaas01 t"),
    @NamedQuery(name = "Tascaas01.findByIdTasca", query = "SELECT t FROM Tascaas01 t WHERE t.idTasca = :idTasca"),
    @NamedQuery(name = "Tascaas01.findByDescripcio", query = "SELECT t FROM Tascaas01 t WHERE t.descripcio = :descripcio"),
    @NamedQuery(name = "Tascaas01.findByEstat", query = "SELECT t FROM Tascaas01 t WHERE t.estat = :estat"
    )})

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
    //Muchas tareas pueden estar asociadas a un proyecto
    @JoinColumn(name = "idProjecte", referencedColumnName = "idProjecte")
    @ManyToOne
    private Projecteas01 idProjecte;

    //Una tarea puede estar asociada a varias facturas
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTasca")
    private Collection<Facturaas01> facturaas01Collection;

    
    @ManyToOne 
    @JoinColumn(name = "IdTasca", nullable = false , insertable = false, updatable = false)
    private Operariresponsableas01 operariResponsable;

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

    public Operariresponsableas01 getOperariResponsable() {
        return operariResponsable;
    }

    public void setOperariResponsable(Operariresponsableas01 operariResponsable) {
        this.operariResponsable = operariResponsable;
    }

    @Override
    public String toString() {
        return "Tarea {"
                + "idTasca= " + idTasca
                + ", descripcio= " + descripcio + '\''
                + ", estat= " + estat + '\''
                + ", idProjecte= " + (idProjecte != null ? idProjecte.getIdProjecte() : "null")
                + ", facturaas01Collection= " + (facturaas01Collection != null ? facturaas01Collection.size() + " facturas" : "null")
                + ", operariResponsable=" + operariResponsable
                + '}';
    }
}
