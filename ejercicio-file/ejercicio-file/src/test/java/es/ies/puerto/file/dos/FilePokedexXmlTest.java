package es.ies.puerto.file.dos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.ies.puerto.file.dos.FilePokedexXml;
import es.ies.puerto.file.dos.Pokemon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static utilidades.UtilClassTest.MESSAGE_ERROR;

class FilePokedexXmlTest {


    FilePokedexXml persistencia;

    List<Pokemon> pokemons;

    @BeforeEach
    void beforeEach() {
        persistencia = new FilePokedexXml();
        pokemons = persistencia.obtenerPokemons();
    }

    @Test
    void obtenerPokemosTest() {
        Assertions.assertFalse(pokemons.isEmpty(),
                MESSAGE_ERROR);
        Assertions.assertEquals(5, pokemons.size(),
                MESSAGE_ERROR);
    }

    @Test
    void obtenerPokemonTest() {
        String idBuscar = "001";
        Pokemon PokemonBuscar = new Pokemon(idBuscar);
        PokemonBuscar = persistencia.obtenerPokemon(PokemonBuscar);
        Assertions.assertEquals(PokemonBuscar.getId(),"001",
                MESSAGE_ERROR);
        Assertions.assertNotNull(PokemonBuscar.getNombre(),
                MESSAGE_ERROR);
        Assertions.assertTrue (PokemonBuscar.getNombre().equals("Bulbasaur"),
                MESSAGE_ERROR);
        Assertions.assertNotNull(PokemonBuscar.getDescripcion().equals("Un pequeño Pokémon con una semilla en su espalda que crece a medida que el Pokémon se desarrolla."),
                MESSAGE_ERROR);
    }

    @Test
    void addDeletePokemonTest() {

        int numPokemonsInicial = pokemons.size();
        Pokemon PokemonInsertar = new Pokemon("123","ibfabf",new ArrayList<>(Arrays.asList("fuego","planta")),"descripcion");

        persistencia.addPokemon(PokemonInsertar);
        pokemons = persistencia.obtenerPokemons();
        int numPokemonsInsertar = pokemons.size();
        Assertions.assertTrue(pokemons.contains(PokemonInsertar),
                MESSAGE_ERROR);
        Assertions.assertEquals(numPokemonsInicial +1 ,
                numPokemonsInsertar, MESSAGE_ERROR);
        persistencia.deletePokemon(PokemonInsertar);
        pokemons = persistencia.obtenerPokemons();
        int numPokemonsBorrado = pokemons.size();
        Assertions.assertEquals(numPokemonsInicial ,
                numPokemonsBorrado,
                MESSAGE_ERROR);
    }

    @Test
    void actualizarPokemon() {
        String idActualizar = "123";
        Pokemon PokemonBuscar = new Pokemon("001");
        Pokemon PokemonActualizar = persistencia.obtenerPokemon(PokemonBuscar);
        Pokemon PokemonBackup = persistencia.obtenerPokemon(PokemonBuscar);
        PokemonActualizar.setNombre("nombreActualizar");
        PokemonActualizar.setDescripcion("descripcionActualizar");
        persistencia.updatePokemon(PokemonActualizar);

        PokemonBuscar = persistencia.obtenerPokemon(PokemonBuscar);
        Assertions.assertEquals(PokemonBuscar.toString(), PokemonActualizar.toString(),
                MESSAGE_ERROR);
        persistencia.updatePokemon(PokemonBackup);

    }


}
