package com.iesvdc.proyecto.aceituna.entidades;

import java.util.List;

public class Cuadrilla {
    private int id;
    private String nombre;
    private Trabajador supervisor_id;
    private List<Trabajador> trabajadores;
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