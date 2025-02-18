package es.ies.puerto.file.dos;

import java.util.List;
import java.util.Objects;

public class Pokemon {

    private String id;
    private String nombre;
    private List<String> tipos;
    private String descripcion;

    public Pokemon() {
    }

    public Pokemon(String id){
        this.id = id;
    }
    public Pokemon(String id, String nombre, List<String> tipos, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.tipos = tipos;
        this.descripcion = descripcion;
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

    public List<String> getTipos() {
        return this.tipos;
    }

    public void setTipos(List<String> tipos) {
        this.tipos = tipos;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Pokemon id(String id) {
        setId(id);
        return this;
    }

    public Pokemon nombre(String nombre) {
        setNombre(nombre);
        return this;
    }

    public Pokemon tipos(List<String> tipos) {
        setTipos(tipos);
        return this;
    }

    public Pokemon descripcion(String descripcion) {
        setDescripcion(descripcion);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Pokemon)) {
            return false;
        }
        Pokemon pokemon = (Pokemon) o;
        return Objects.equals(id, pokemon.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return getId() + ", " +
                getNombre() + ", " +
                getTipos() + ", " +
                getDescripcion();
    }

}
