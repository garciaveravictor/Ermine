/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author UsuarioDAM
 */
@Entity
@Table(name = "RESERVA_HAB")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReservaHab.findAll", query = "SELECT r FROM ReservaHab r"),
    @NamedQuery(name = "ReservaHab.findById", query = "SELECT r FROM ReservaHab r WHERE r.id = :id"),
    @NamedQuery(name = "ReservaHab.findByFechaLlegada", query = "SELECT r FROM ReservaHab r WHERE r.fechaLlegada = :fechaLlegada"),
    @NamedQuery(name = "ReservaHab.findByFechaSalida", query = "SELECT r FROM ReservaHab r WHERE r.fechaSalida = :fechaSalida"),
    @NamedQuery(name = "ReservaHab.findByNumHabitaciones", query = "SELECT r FROM ReservaHab r WHERE r.numHabitaciones = :numHabitaciones"),
    @NamedQuery(name = "ReservaHab.findByTipoHabitaciones", query = "SELECT r FROM ReservaHab r WHERE r.tipoHabitaciones = :tipoHabitaciones"),
    @NamedQuery(name = "ReservaHab.findByRegimen", query = "SELECT r FROM ReservaHab r WHERE r.regimen = :regimen"),
    @NamedQuery(name = "ReservaHab.findByFumador", query = "SELECT r FROM ReservaHab r WHERE r.fumador = :fumador")})
public class ReservaHab implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "FECHA_LLEGADA")
    @Temporal(TemporalType.DATE)
    private Date fechaLlegada;
    @Basic(optional = false)
    @Column(name = "FECHA_SALIDA")
    @Temporal(TemporalType.DATE)
    private Date fechaSalida;
    @Basic(optional = false)
    @Column(name = "NUM_HABITACIONES")
    private short numHabitaciones;
    @Basic(optional = false)
    @Column(name = "TIPO_HABITACIONES")
    private Character tipoHabitaciones;
    @Basic(optional = false)
    @Column(name = "REGIMEN")
    private Character regimen;
    @Column(name = "FUMADOR")
    private Boolean fumador;
    @JoinColumn(name = "DNI", referencedColumnName = "DNI")
    @ManyToOne(optional = false)
    private Cliente dni;

    public ReservaHab() {
    }

    public ReservaHab(Integer id) {
        this.id = id;
    }

    public ReservaHab(Integer id, Date fechaLlegada, Date fechaSalida, short numHabitaciones, Character tipoHabitaciones, Character regimen) {
        this.id = id;
        this.fechaLlegada = fechaLlegada;
        this.fechaSalida = fechaSalida;
        this.numHabitaciones = numHabitaciones;
        this.tipoHabitaciones = tipoHabitaciones;
        this.regimen = regimen;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaLlegada() {
        return fechaLlegada;
    }

    public void setFechaLlegada(Date fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public short getNumHabitaciones() {
        return numHabitaciones;
    }

    public void setNumHabitaciones(short numHabitaciones) {
        this.numHabitaciones = numHabitaciones;
    }

    public Character getTipoHabitaciones() {
        return tipoHabitaciones;
    }

    public void setTipoHabitaciones(Character tipoHabitaciones) {
        this.tipoHabitaciones = tipoHabitaciones;
    }

    public Character getRegimen() {
        return regimen;
    }

    public void setRegimen(Character regimen) {
        this.regimen = regimen;
    }

    public Boolean getFumador() {
        return fumador;
    }

    public void setFumador(Boolean fumador) {
        this.fumador = fumador;
    }

    public Cliente getDni() {
        return dni;
    }

    public void setDni(Cliente dni) {
        this.dni = dni;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReservaHab)) {
            return false;
        }
        ReservaHab other = (ReservaHab) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String resul ="";
        String tipo_regimen="";
        String tipo_hab="";
        switch(regimen){
            case 'M': tipo_regimen = "Media pensión";break;
            case 'C': tipo_regimen = "Pensión Completa";break;
            case 'A': tipo_regimen = "Alojamiento y desayuno";break;
        }
        switch(tipoHabitaciones){
            case 'O': tipo_hab = "Un uso";break;
            case 'D': tipo_hab = "Doble uso";break;

        }        
        
        
        resul = "Fecha de llegada: " + fechaLlegada.toString() + "\n" +
                "Fecha de salida: " + fechaSalida.toString() + "\n" +
                "Número de habitaciones: " + numHabitaciones + "\n" + 
                "Régimen: " + tipo_regimen + "\n" + 
                "Tipo de Habitación: " + tipo_hab + "\n" + 
                "Fumador: " + (fumador ? "Si" : "No") 
                ;      
        return resul;
    }
    
}
