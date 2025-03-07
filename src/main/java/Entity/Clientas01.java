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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
})

public class Clientas01 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false) 
    @Column(name = "idClient", nullable = false, unique = true)
    private Integer idClient;
    
    @Basic(optional = false) 
    @Column(name = "nom", nullable = false) 
    @NotNull(message = "nombre NN")
    private String nom;
    
    @Column(name = "cognom") //puede ser null
    private String cognom;
    
    //(Valido nif desde service)
    @Basic(optional = false)
    @Column(name = "nif", nullable = false, unique = true) //NN 
    @NotNull(message = "nif NN")
    private String nif;
    
    //Un cliente puede tener muchas facturas
    // SI ELIMINO CLIENTE SE ELIMINAN SUS FACTURAS
    @OneToMany(mappedBy = "idClient", cascade= CascadeType.ALL)
    private Collection<Facturaas01> facturaas01Collection;
    
    //Un cliente puede tener muchos proyectos
    //si elimino un cliente NO se eliminan sus proyectos asociados
    // (PARA NO ELIMINARLO LO DESVINCULO)
    @OneToMany(cascade= CascadeType.PERSIST, fetch = FetchType.EAGER, mappedBy = "idClient")
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
                ", Facturas=" + (facturaas01Collection != null && !facturaas01Collection.isEmpty() ? facturaas01Collection.size() : "0") +
                ", Proyectos=" + (projecteas01Collection != null && !projecteas01Collection.isEmpty() ? projecteas01Collection.size() : "0") +
                '}';
    }
    
}
