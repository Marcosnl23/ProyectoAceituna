package com.iesvdc.proyecto.aceituna.entidades;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlType;

import java.util.List;

@XmlRootElement(name = "cuadrilla")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "nombre", "supervisor_id", "trabajadores", "olivares" })
public class Cuadrilla {
    @XmlAttribute(name = "id")
    private int id;
    @XmlElement(name = "nombre")
    private String nombre;
    @XmlElement(name = "supervisor_id")
    private Trabajador supervisor_id;
    @XmlElementWrapper(name = "trabajadores")
    @XmlElement(name = "trabajador")
    private List<Trabajador> trabajadores;
    @XmlElementWrapper(name = "olivares")
    @XmlElement(name = "olivar")
    private List<Olivar> olivares;

    public Cuadrilla(int id, String nombre, Trabajador supervisor_id, List<Trabajador> trabajadores, List<Olivar> olivares) {
        this.id = id;
        this.nombre = nombre;
        this.supervisor_id = supervisor_id;
        this.trabajadores = trabajadores;
        this.olivares = olivares;
    }

    public Cuadrilla(int id, String nombre, Trabajador supervisor_id) {
        this.id = id;
        this.nombre = nombre;
        this.supervisor_id = supervisor_id;
    }

    public Cuadrilla() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Trabajador getSupervisor_id() {
        return supervisor_id;
    }

    public void setSupervisor_id(Trabajador supervisor_id) {
        this.supervisor_id = supervisor_id;
    }

    public List<Trabajador> getTrabajadores() {
        return trabajadores;
    }

    public void setTrabajadores(List<Trabajador> trabajadores) {
        this.trabajadores = trabajadores;
    }

    public List<Olivar> getOlivares() {
        return olivares;
    }

    public void setOlivares(List<Olivar> olivares) {
        this.olivares = olivares;
    }

    @Override
    public String toString() {
        return "Cuadrilla{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", supervisor_id=" + supervisor_id +
                ", trabajadores=" + trabajadores +
                ", olivares=" + olivares +
                '}';
    }
}