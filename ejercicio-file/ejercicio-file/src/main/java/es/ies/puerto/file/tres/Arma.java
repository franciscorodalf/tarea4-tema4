package es.ies.puerto.file.tres;

import java.util.Objects;

public class Arma {
    private String id;
    private String nombre;
    private String descripcion;
    private String origen;
    private int fuerza;

    public Arma() {
    }

    public Arma(String id, String nombre, String descripcion, String origen, int fuerza) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.origen = origen;
        this.fuerza = fuerza;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getOrigen() {
        return this.origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public int getFuerza() {
        return this.fuerza;
    }

    public void setFuerza(int fuerza) {
        this.fuerza = fuerza;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Arma)) {
            return false;
        }
        Arma arma = (Arma) o;
        return Objects.equals(id, arma.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", nombre='" + getNombre() + "'" +
                ", descripcion='" + getDescripcion() + "'" +
                ", origen='" + getOrigen() + "'" +
                ", fuerza='" + getFuerza() + "'" +
                "}";
    }

}
