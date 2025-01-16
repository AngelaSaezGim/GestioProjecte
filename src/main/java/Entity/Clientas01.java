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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author angsaegim
 */
@Entity
@Table(name = "clientas01")
@NamedQueries({
    // BUSCA TODOS LOS CLIENTES O CLIENTES POR SUS ATRIBUTOS
    @NamedQuery(name = "Clientas01.findAll", query = "SELECT c FROM Clientas01 c"),
    @NamedQuery(name = "Clientas01.findByIdClient", query = "SELECT c FROM Clientas01 c WHERE c.idClient = :idClient"),
    @NamedQuery(name = "Clientas01.findByNom", query = "SELECT c FROM Clientas01 c WHERE c.nom = :nom"),
    @NamedQuery(name = "Clientas01.findByCognom", query = "SELECT c FROM Clientas01 c WHERE c.cognom = :cognom"),
    @NamedQuery(name = "Clientas01.findByNif", query = "SELECT c FROM Clientas01 c WHERE c.nif = :nif")
})

public class Clientas01 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false) 
    @Column(name = "idClient")
    private Integer idClient;
    
    @Basic(optional = false) 
    @Column(name = "nom", nullable = false) //NN
    private String nom;
    
    @Column(name = "cognom") 
    private String cognom;
    
    //(Valido nif desde service)
    @Basic(optional = false)
    @Column(name = "nif", nullable = false, unique = true)
    private String nif;
    
    //Un cliente puede tener muchas facturas
    //***cascade - Cuando un cliente se elimina, todas las facturas asociadas también se eliminarán automáticamente
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idClient")
    private Collection<Facturaas01> facturaas01Collection;
    
    //Un cliente puede tener muchos proyectos
    //**** FALTA EL CASCADE
    @OneToMany(mappedBy = "idClient")
    private Collection<Projecteas01> projecteas01Collection;

    public Clientas01() {
    }

    public Clientas01(Integer idClient) {
        this.idClient = idClient;
    }

    public Clientas01(Integer idClient, String nom, String nif) {
        this.idClient = idClient;
        this.nom = nom;
        this.nif = nif;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
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

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    //Conseguir facturas y proyectos de sus entidades
    public Collection<Facturaas01> getFacturaas01Collection() {
        return facturaas01Collection;
    }

    public void setFacturaas01Collection(Collection<Facturaas01> facturaas01Collection) {
        this.facturaas01Collection = facturaas01Collection;
    }

    public Collection<Projecteas01> getProjecteas01Collection() {
        return projecteas01Collection;
    }

    public void setProjecteas01Collection(Collection<Projecteas01> projecteas01Collection) {
        this.projecteas01Collection = projecteas01Collection;
    }

    @Override
    public String toString() {
    return "Cliente {" +
            "idClient=" + idClient +
            ", nom='" + nom + '\'' +
            ", cognom='" + cognom + '\'' +
            ", nif='" + nif + '\'' +
            ", Facturas de " + nom +" =" + facturaas01Collection +
            ", Proyectos de " + nom + " =" + projecteas01Collection +
            '}';
}
    
}
