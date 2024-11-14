package com.iesvdc.proyecto.aceituna.entidades;

import java.time.LocalDate;

public class Produccion {
    private int id;
    private Cuadrilla cuadrilla_id;
    private Olivar olivar_id;
    private Almazara almazara_id;
    private LocalDate fecha;
    private Double cantidadRecogida;

    public Produccion(int id, Cuadrilla cuadrilla_id, Olivar olivar_id, Almazara almazara_id, LocalDate fecha, Double cantidadRecogida) {
        this.id = id;
        this.cuadrilla_id = cuadrilla_id;
        this.olivar_id = olivar_id;
        this.almazara_id = almazara_id;
        this.fecha = fecha;
        this.cantidadRecogida = cantidadRecogida;
    }

    public Produccion() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cuadrilla getCuadrilla_id() {
        return cuadrilla_id;
    }

    public void setCuadrilla_id(Cuadrilla cuadrilla_id) {
        this.cuadrilla_id = cuadrilla_id;
    }

    public Olivar getOlivar_id() {
        return olivar_id;
    }

    public void setOlivar_id(Olivar olivar_id) {
        this.olivar_id = olivar_id;
    }

    public Almazara getAlmazara_id() {
        return almazara_id;
    }

    public void setAlmazara_id(Almazara almazara_id) {
        this.almazara_id = almazara_id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Double getCantidadRecogida() {
        return cantidadRecogida;
    }

    public void setCantidadRecogida(Double cantidadRecogida) {
        this.cantidadRecogida = cantidadRecogida;
    }

    @Override
    public String toString() {
        return "Produccion{" +
                "id=" + id +
                ", cuadrilla_id=" + cuadrilla_id +
                ", olivar_id=" + olivar_id +
                ", almazara_id=" + almazara_id +
                ", fecha=" + fecha +
                ", cantidadRecogida=" + cantidadRecogida +
                '}';
    }
}
