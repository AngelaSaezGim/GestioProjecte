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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author angsaegim
 */
@Entity
@Table(name = "facturaas01")
@NamedQueries({
    // BUSCA TODAS LAS FACTURAS O FACTURAS POR SUS ATRIBUTOS
    @NamedQuery(name = "Facturaas01.findAll", query = "SELECT f FROM Facturaas01 f"),
    @NamedQuery(name = "Facturaas01.findByIdFactura", query = "SELECT f FROM Facturaas01 f WHERE f.idFactura = :idFactura"),
    @NamedQuery(name = "Facturaas01.findByData", query = "SELECT f FROM Facturaas01 f WHERE f.data = :data"),
    @NamedQuery(name = "Facturaas01.findByImport1", query = "SELECT f FROM Facturaas01 f WHERE f.importTotal = :importTotal"),
    @NamedQuery(name = "Facturaas01.findByObservacions", query = "SELECT f FROM Facturaas01 f WHERE f.observacions = :observacions")})

public class Facturaas01 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idFactura", nullable = false, unique = true)
    private Integer idFactura;

    @Basic(optional = false)
    @Column(name = "data", nullable = false)
    @Temporal(TemporalType.DATE)
    @NotNull(message = "Fecha NN")
    private Date data;

    @Basic(optional = false)
    @Column(name = "importTotal", nullable = false)
    @NotNull(message = "Import NN")
    private double importTotal;

    @Column(name = "observacions")
    private String observacions;

    //una FACTURA ESTA ASOCIADA A UNA TAREA
    @OneToOne(optional = false)
    @JoinColumn(name = "idTasca", referencedColumnName = "idTasca", unique = true)
    private Tascaas01 idTasca;

    //Varias facturas pueden estas asociadas a un cliente
    @JoinColumn(name = "idClient", referencedColumnName = "idClient")
    @ManyToOne(optional = false)
    private Clientas01 idClient;

    public Facturaas01() {
    }

    public Facturaas01(Integer idFactura) {
        this.idFactura = idFactura;
    }

    public Facturaas01(Integer idFactura, Date data, double importTotal) {
        this.idFactura = idFactura;
        this.data = data;
        this.importTotal = importTotal;
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

    public double getImportTotal() {
        return importTotal;
    }

    public void setImportTotal(double importTotal) {
        this.importTotal = importTotal;
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
    public String toString() {
        return "Facturaas01 {"
                + "idFactura=" + idFactura
                + ", data=" + data
                + ", importTotal=" + importTotal
                + ", observacions='" + (observacions != null ? observacions : "N/A") + '\''
                + ", Cliente asociado=" + (idClient != null ? idClient.getNom() + " " + idClient.getCognom() : "N/A")
                + ", Tarea asociada=" + (idTasca != null ? idTasca.toString() : "N/A")
                + '}';
    }

}
