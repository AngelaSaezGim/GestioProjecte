/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author angsaegim
 */
@Entity
@Table(name = "projecteas01")
@NamedQueries({
    // BUSCA TODOS LOS PROYECTOS O LOS PROYECTOS POR SUS ATRIBUTOS
    @NamedQuery(name = "Projecteas01.findAll", query = "SELECT p FROM Projecteas01 p"),
    @NamedQuery(name = "Projecteas01.findByIdProjecte", query = "SELECT p FROM Projecteas01 p WHERE p.idProjecte = :idProjecte"),
    @NamedQuery(name = "Projecteas01.findByDescripcio", query = "SELECT p FROM Projecteas01 p WHERE p.descripcio = :descripcio"),
    @NamedQuery(name = "Projecteas01.findByEstat", query = "SELECT p FROM Projecteas01 p WHERE p.estat = :estat"
    )})

public class Projecteas01 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idProjecte", nullable = false, unique = true)
    private Integer idProjecte;

    @Column(name = "descripcio")
    private String descripcio;

    @Column(name = "estat")
    private String estat;

    @Column(name = "fechaFinalitzacio")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaFinalitzacio;

    //Un proyecto puede estar asociado a varias tareas
    //Modificaciones de proyecto se aplicarán sobre tareas relacionadas
    //Si se elimina una tarea de la colección(se desvincula)será eliminada
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "idProjecte")
    private Collection<Tascaas01> tascaas01Collection;

    //Varios proyectos pueden estar asociados a un cliente
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idClient", referencedColumnName = "idClient")
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

    public Date getFechaFinalitzacio() {
        return fechaFinalitzacio;
    }

    public void setFechaFinalitzacio(Date fechaFinalitzacio) {
        this.fechaFinalitzacio = fechaFinalitzacio;
    }

    public Collection<Tascaas01> getTascaas01Collection() {
        return tascaas01Collection;
    }

    // UN PROYECTO FINALIZADO SOLO PUEDE TENER TAREAS FINALIZADAS
    public void setTascaas01Collection(Collection<Tascaas01> tascaas01Collection) {
        if ("Finalizat".equals(this.estat)) {
            // Valido que todas las tareas que asociamos estan finalizadas
            for (Tascaas01 tasca : tascaas01Collection) {
                if (!"Finalitzat".equals(tasca.getEstat())) {
                    throw new IllegalStateException("No se pueden asociar tareas no finalizadas a proyectos finalizados.");
                }
            }
        }
        this.tascaas01Collection = tascaas01Collection;
    }

    public Clientas01 getIdClient() {
        return idClient;
    }

    public void setIdClient(Clientas01 idClient) {
        this.idClient = idClient;
    }

    @Override
    public String toString() {
        return "Proyecto {"
                + "idProjecte= " + idProjecte
                + ", descripcio= " + descripcio + '\''
                + ", estat= " + estat + '\''
                + ", fechaFinalitzacio= " + (fechaFinalitzacio != null ? fechaFinalitzacio : "No finalizado")
                + ", tasques = " + (tascaas01Collection != null ? tascaas01Collection.size() + " tareas" : "null")
                + ", idClient = " + (idClient != null ? idClient.getIdClient() : "null")
                + '}';
    }

}
