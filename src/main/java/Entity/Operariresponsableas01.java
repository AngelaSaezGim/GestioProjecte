/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.io.Serializable;
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
import java.util.Collection;

/**
 *
 * @author angsaegim
 */
@Entity
@Table(name = "operariresponsableas01")
@NamedQueries({
    // BUSCA TODAS LAS RESPONSABILIDADES DE OPERARIOS O LAS RESPONSABILIDADES POR SUS ATRIBUTOS
    @NamedQuery(name = "Operariresponsableas01.findAll", query = "SELECT o FROM Operariresponsableas01 o"),
    @NamedQuery(name = "Operariresponsableas01.findByIdOperariTasca", query = "SELECT o FROM Operariresponsableas01 o WHERE o.idOperariTasca = :idOperariTasca"),
    @NamedQuery(name = "Operariresponsableas01.findByNom", query = "SELECT o FROM Operariresponsableas01 o WHERE o.nom = :nom"),
    @NamedQuery(name = "Operariresponsableas01.findByCognom", query = "SELECT o FROM Operariresponsableas01 o WHERE o.cognom = :cognom"),
    @NamedQuery(name = "Operariresponsableas01.findByNifOperari", query = "SELECT o FROM Operariresponsableas01 o WHERE o.nifOperari = :nifOperari"),
    @NamedQuery(name = "Operariresponsableas01.findByObservacions", query = "SELECT o FROM Operariresponsableas01 o WHERE o.observacions = :observacions"
    )})
public class Operariresponsableas01 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idOperariTasca")
    private Integer idOperariTasca;

    @Column(name = "nom")
    private String nom;

    @Column(name = "cognom")
    private String cognom;

    @Basic(optional = false)
    @Column(name = "nifOperari", nullable = false, unique = true)
    private String nifOperari;

    @Column(name = "observacions")
    private String observacions;

    //UN (Y SOLO UNO) OPERARIO RESPONSABLE PUEDE TENER VARIAS TAREAS
    @OneToMany(mappedBy = "operariResponsable")
    private Collection<Tascaas01> tasques;

    public Operariresponsableas01() {
    }

    public Operariresponsableas01(Integer idOperariTasca) {
        this.idOperariTasca = idOperariTasca;
    }

    public Operariresponsableas01(Integer idOperariTasca, String nifOperari) {
        this.idOperariTasca = idOperariTasca;
        this.nifOperari = nifOperari;
    }

    public Integer getIdOperariTasca() {
        return idOperariTasca;
    }

    public void setIdOperariTasca(Integer idOperariTasca) {
        this.idOperariTasca = idOperariTasca;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognom() {
        return cognom;
    }

    public void setCognom(String cognom) {
        this.cognom = cognom;
    }

    public String getNifOperari() {
        return nifOperari;
    }

    public void setNifOperari(String nifOperari) {
        this.nifOperari = nifOperari;
    }

    public String getObservacions() {
        return observacions;
    }

    public void setObservacions(String observacions) {
        this.observacions = observacions;
    }

    public Collection<Tascaas01> getTasques() {
    return tasques;
    }

    public void setTasques(Collection<Tascaas01> tasques) {
    this.tasques = tasques;
    }

    @Override
    public String toString() {
    return "Operariresponsableas01 { "
            + "idOperariTasca=" + idOperariTasca
            + ", nom='" + nom + '\''
            + ", cognom='" + cognom + '\''
            + ", nifOperari='" + nifOperari + '\''
            + ", observacions='" + observacions + '\''
            + ", tasques vinculadas=" + (tasques != null ? tasques.size() + " tareas" : "null")
            + '}';
    }

}
