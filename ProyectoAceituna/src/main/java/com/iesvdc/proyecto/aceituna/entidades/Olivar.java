package com.iesvdc.proyecto.aceituna.entidades;

import javax.xml.bind.annotation.*;
import java.util.List;
@XmlRootElement(name = "olivar")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "ubicacion", "hectareas", "produccionAnual", "cuadrillas" })
public class Olivar {
    @XmlAttribute(name = "id")
    private int id;
    @XmlElement(name = "ubicacion")
    private String ubicacion;
    @XmlElement(name = "hectareas")
    private Double hectareas;
    @XmlElement(name = "produccionAnual")
    private Double produccionAnual;
    @XmlElementWrapper(name = "cuadrillas")
    @XmlElement(name = "cuadrilla")
    private List<Cuadrilla> cuadrillas;

    public Olivar(int id, String ubicacion, Double hectareas, Double produccionAnual, List<Cuadrilla> cuadrillas) {
        this.id = id;
        this.ubicacion = ubicacion;
        this.hectareas = hectareas;
        this.produccionAnual = produccionAnual;
        this.cuadrillas = cuadrillas;
    }

    public Olivar(int id, String ubicacion, Double hectareas, Double produccionAnual) {
        this.id = id;
        this.ubicacion = ubicacion;
        this.hectareas = hectareas;
        this.produccionAnual = produccionAnual;
    }

    public Olivar() {
    }

    public List<Cuadrilla> getCuadrillas() {
        return cuadrillas;
    }

    public void setCuadrillas(List<Cuadrilla> cuadrillas) {
        this.cuadrillas = cuadrillas;
    }

    public Double getProduccionAnual() {
        return produccionAnual;
    }

    public void setProduccionAnual(Double produccionAnual) {
        this.produccionAnual = produccionAnual;
    }

    public Double getHectareas() {
        return hectareas;
    }

    public void setHectareas(Double hectareas) {
        this.hectareas = hectareas;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Olivar{" +
                "id=" + id +
                ", ubicacion='" + ubicacion + '\'' +
                ", hectareas=" + hectareas +
                ", produccionAnual=" + produccionAnual +
                ", cuadrillas=" + cuadrillas +
                '}';
    }
}
