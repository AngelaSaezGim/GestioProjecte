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
})

public class Tascaas01 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idTasca", nullable = false, unique = true)
    private Integer idTasca;

    @Column(name = "descripcio")
    private String descripcio;

    @Column(name = "estat")
    private String estat;

    //Muchas tareas pueden estar asociadas a un proyecto
    @JoinColumn(name = "idProjecte", referencedColumnName = "idProjecte")
    @ManyToOne
    private Projecteas01 idProjecte;

    //Una tarea esta asociada a UNA FACTURA
    //Si BORRAMOS una tarea, se borrará su factura asociada
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idTasca", orphanRemoval = true)
    private Facturaas01 factura;

    // UNA TAREA ESTA ASOCIADA A UN OPEARIO
    //aunque borremos la tarea no se borrara el operario
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idOperari", referencedColumnName = "idOperariTasca", nullable = false, insertable = true, updatable = true)
    private Operariresponsableas01 idOperari;

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

    // UN PROYECTO FINALIZADO SOLO PUEDE TENER TAREAS FINALIZADAS
    public void setIdProjecte(Projecteas01 idProjecte) {
        if (idProjecte != null && "Finalizat".equals(idProjecte.getEstat()) && !"Finalitzat".equals(this.estat)) {
            throw new IllegalStateException("Las tareas solo pueden asociarse a proyectos finalizados si también están finalizadas.");
        }
        this.idProjecte = idProjecte;
    }

    public Facturaas01 getFactura() {
        return factura;
    }

    public void setFactura(Facturaas01 factura) {
        this.factura = factura;
    }

    public Operariresponsableas01 getOperariResponsable() {
        return idOperari;
    }

    public void setOperariResponsable(Operariresponsableas01 operariResponsable) {
        this.idOperari = operariResponsable;
    }

    @Override
    public String toString() {
        return "Tarea {"
                + "idTasca=" + idTasca
                + ", descripcio='" + descripcio + '\''
                + ", estat='" + estat + '\''
                + ", idProjecte=" + (idProjecte != null ? idProjecte.getIdProjecte() : "null")
                + ", factura=" + (factura != null ? factura.getIdFactura() : "null")
                + ", operariResponsable=" + (idOperari != null ? idOperari.getIdOperariTasca() : "null")
                + '}';
    }
}
