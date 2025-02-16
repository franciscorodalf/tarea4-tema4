package es.ies.puerto.file.uno;

import java.util.Objects;

public class Criatura {
    private String id;
    private String nombre;
    private String descripcion;
    private String categoria;

    public Criatura() {
    }

    public Criatura(String id) {
        this.id = id;
    }

    public Criatura(String id, String nombre, String descripcion, String categoria) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
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

    public String getCategoria() {
        return this.categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Criatura)) {
            return false;
        }
        Criatura criatura = (Criatura) o;
        return Objects.equals(id, criatura.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id + ", " + nombre + ", " + descripcion + ", " + categoria;
    }

}
