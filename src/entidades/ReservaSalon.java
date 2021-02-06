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
@Table(name = "RESERVA_SALON")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReservaSalon.findAll", query = "SELECT r FROM ReservaSalon r"),
    @NamedQuery(name = "ReservaSalon.findById", query = "SELECT r FROM ReservaSalon r WHERE r.id = :id"),
    @NamedQuery(name = "ReservaSalon.findByTipoEvento", query = "SELECT r FROM ReservaSalon r WHERE r.tipoEvento = :tipoEvento"),
    @NamedQuery(name = "ReservaSalon.findByNumPersona", query = "SELECT r FROM ReservaSalon r WHERE r.numPersona = :numPersona"),
    @NamedQuery(name = "ReservaSalon.findByTipoCocina", query = "SELECT r FROM ReservaSalon r WHERE r.tipoCocina = :tipoCocina"),
    @NamedQuery(name = "ReservaSalon.findByNecesitaHabitacion", query = "SELECT r FROM ReservaSalon r WHERE r.necesitaHabitacion = :necesitaHabitacion"),
    @NamedQuery(name = "ReservaSalon.findByCuantasHab", query = "SELECT r FROM ReservaSalon r WHERE r.cuantasHab = :cuantasHab"),
    @NamedQuery(name = "ReservaSalon.findByNumDias", query = "SELECT r FROM ReservaSalon r WHERE r.numDias = :numDias"),
    @NamedQuery(name = "ReservaSalon.findByFechaEvento", query = "SELECT r FROM ReservaSalon r WHERE r.fechaEvento = :fechaEvento")})
public class ReservaSalon implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "TIPO_EVENTO")
    private Character tipoEvento;
    @Basic(optional = false)
    @Column(name = "NUM_PERSONA")
    private short numPersona;
    @Column(name = "TIPO_COCINA")
    private Character tipoCocina;
    @Column(name = "NECESITA_HABITACION")
    private Boolean necesitaHabitacion;
    @Column(name = "CUANTAS_HAB")
    private Short cuantasHab;
    @Column(name = "NUM_DIAS")
    private Short numDias;
    @Basic(optional = false)
    @Column(name = "FECHA_EVENTO")
    @Temporal(TemporalType.DATE)
    private Date fechaEvento;
    @JoinColumn(name = "DNI", referencedColumnName = "DNI")
    @ManyToOne(optional = false)
    private Cliente dni;

    public ReservaSalon() {
    }

    public ReservaSalon(Integer id) {
        this.id = id;
    }

    public ReservaSalon(Integer id, Character tipoEvento, short numPersona, Date fechaEvento) {
        this.id = id;
        this.tipoEvento = tipoEvento;
        this.numPersona = numPersona;
        this.fechaEvento = fechaEvento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Character getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(Character tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public short getNumPersona() {
        return numPersona;
    }

    public void setNumPersona(short numPersona) {
        this.numPersona = numPersona;
    }

    public Character getTipoCocina() {
        return tipoCocina;
    }

    public void setTipoCocina(Character tipoCocina) {
        this.tipoCocina = tipoCocina;
    }

    public Boolean getNecesitaHabitacion() {
        return necesitaHabitacion;
    }

    public void setNecesitaHabitacion(Boolean necesitaHabitacion) {
        this.necesitaHabitacion = necesitaHabitacion;
    }

    public Short getCuantasHab() {
        return cuantasHab;
    }

    public void setCuantasHab(Short cuantasHab) {
        this.cuantasHab = cuantasHab;
    }

    public Short getNumDias() {
        return numDias;
    }

    public void setNumDias(Short numDias) {
        this.numDias = numDias;
    }

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
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
        if (!(object instanceof ReservaSalon)) {
            return false;
        }
        ReservaSalon other = (ReservaSalon) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String resul="";
        String tipo_cocina="";
        
        switch(tipoCocina){
            case 'B': tipo_cocina = "Buffet"; break;
            case 'V': tipo_cocina = "Vegetariano"; break;
            case 'M': tipo_cocina = "Menú a la carta"; break;
            case 'C': tipo_cocina = "Cita con el Chef"; break;
            case 'N': tipo_cocina = "No precisa"; break;
        }
        
        if (tipoEvento == 'B') {
            resul = "Tipo de Evento: Banquete\n"+
                    "Numero de Personas: " + numPersona + "\n" +
                    "Tipo de Cocina: " + tipo_cocina + "\n" + 
                    "Fecha del Evento: " +fechaEvento.toString() + "\n" +
                    "Número de días: " + numDias + "\n"
                    ;
        }else if (tipoEvento == 'J') {
            resul = "Tipo de Evento: Jornada\n"+
                    "Numero de Personas: " + numPersona + "\n" +
                    "Fecha del Evento: " +fechaEvento.toString() + "\n" +
                    "Número de días: " + numDias + "\n"
                    ;
        }else if (tipoEvento == 'C') {
            if (necesitaHabitacion) {
                resul = "Tipo de Evento: Congreso\n"+
                        "Numero de Personas: " + numPersona + "\n" +
                        "Fecha del Evento: " + fechaEvento.toString() + "\n" +
                        "Cantidad de habitaciones: " + cuantasHab + "\n" +
                        "Número de días: " + numDias + "\n"
                        ; 
            }else{
                resul = "Tipo de Evento: Congreso\n"+
                        "Numero de Personas: " + numPersona + "\n" +
                        "Fecha del Evento: " + fechaEvento.toString() + "\n" +
                        "Número de días: " + numDias + "\n"
                        ; 
            }
            
            
  
        }
        
        
        
        
        
        
        return resul;
    }
    
}
