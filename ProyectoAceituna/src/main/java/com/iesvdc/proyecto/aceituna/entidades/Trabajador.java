package com.iesvdc.proyecto.aceituna.entidades;

import javax.xml.bind.annotation.*;
import java.util.List;
@XmlRootElement(name = "trabajador")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "nombre", "edad", "puesto", "salario", "cuadrillas" })
public class Trabajador {
    @XmlAttribute(name = "id")
    private int id;
    @XmlElement(name = "nombre")
    private String nombre;
    @XmlElement(name = "edad")
    private int edad;
    @XmlElement(name = "puesto")
    private String puesto;
    @XmlElement(name = "salario")
    private Double salario;
    @XmlElementWrapper(name = "cuadrillas")
    @XmlElement(name = "cuadrilla")
    private List<Cuadrilla> cuadrillas;

    public Trabajador(int id, String nombre, int edad, String puesto, Double salario, List<Cuadrilla> cuadrillas) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.puesto = puesto;
        this.salario = salario;
        this.cuadrillas = cuadrillas;
    }

    public Trabajador() {
    }

    public Trabajador(Double salario, String puesto, int edad, String nombre, int id) {
        this.salario = salario;
        this.puesto = puesto;
        this.edad = edad;
        this.nombre = nombre;
        this.id = id;
    }

    public Trabajador(int id, String nombre, int edad, String puesto, Double salario) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.puesto = puesto;
        this.salario = salario;
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

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public List<Cuadrilla> getCuadrillas() {
        return cuadrillas;
    }

    public void setCuadrillas(List<Cuadrilla> cuadrillas) {
        this.cuadrillas = cuadrillas;
    }

    @Override
    public String toString() {
        return "Trabajador{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", puesto='" + puesto + '\'' +
                ", salario=" + salario +
                ", cuadrillas=" + cuadrillas +
                '}';
    }
}
