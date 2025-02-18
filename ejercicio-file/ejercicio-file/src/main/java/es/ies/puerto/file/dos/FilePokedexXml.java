package es.ies.puerto.file.dos;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FilePokedexXml {

    public FilePokedexXml() {

    }

    public List<Pokemon> obtenerPokemons() {
        try {
            List<Pokemon> pokemones = new ArrayList<>();
            File archivo = new File("src/main/resources/dos.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(archivo);

            NodeList lista = doc.getElementsByTagName("pokemon");
            for (int i = 0; i < lista.getLength(); i++) {
                Node nodo = lista.item(i);
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) nodo;
                    String id = elemento.getElementsByTagName("id").item(0).getTextContent();
                    String nombre = elemento.getElementsByTagName("nombre").item(0).getTextContent();

                    NodeList tipos = elemento.getElementsByTagName("tipo");
                    List<String> tiposList = new ArrayList<>();
                    for (int j = 0; j < tipos.getLength(); j++) {
                        tiposList.add(elemento.getElementsByTagName("tipo").item(j).getTextContent());
                    }
                    String descripcion = elemento.getElementsByTagName("descripcion").item(0).getTextContent();

                    Pokemon pokemon = new Pokemon(id, nombre, tiposList, descripcion);
                    System.out.println(pokemon);
                    pokemones.add(pokemon);
                }
            }
            return pokemones;
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public Pokemon obtenerPokemon(Pokemon pokemon) {
        File file = new File("src/main/resources/dos.xml");
        if (!file.exists()) {
            throw new IllegalArgumentException("el archivo xml no existe");
        }
        List<Pokemon> pokemones = obtenerPokemons();
        int posicionPokemon = pokemones.indexOf(pokemon);
        if (posicionPokemon < 0) {
            throw new IllegalArgumentException();
        }
        return pokemones.get(posicionPokemon);
    }

    public void addPokemon(Pokemon pokemon) {
        if (pokemon == null || pokemon.getId() == null || pokemon.getId().isEmpty()) {
            return;
        }
        List<Pokemon> pokemones = obtenerPokemons();
        if (pokemones.contains(pokemon)) {
            return;
        }
        pokemones.add(pokemon);
        try {
            volcarFicheroXml(pokemones);
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public boolean deletePokemon(Pokemon pokemon) {
        if (pokemon == null || pokemon.getId() == null || pokemon.getId().isEmpty()) {
            return false;
        }
        List<Pokemon> pokemones = obtenerPokemons();
        if (!pokemones.contains(pokemon)) {
            return false;
        }
         
        try {
            boolean eliminar = pokemones.remove(pokemon);

            if (eliminar) {
                volcarFicheroXml(pokemones);
            }
            return true;
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public void updatePokemon(Pokemon pokemon) {
        if (pokemon == null | pokemon.getId() == null || pokemon.getId().isEmpty()) {
            return;
        }
        List<Pokemon> pokemones = obtenerPokemons();
        if (!pokemones.contains(pokemon)) {
            return;
        }

        pokemones.remove(pokemon);
        pokemones.add(pokemon);

        try {
            volcarFicheroXml(pokemones);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void volcarFicheroXml(List<Pokemon> pokemones) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        Element root = doc.createElement("criaturas");
        doc.appendChild(root);

        for (Pokemon pokemon : pokemones) {

            Element criaturaXml = doc.createElement("pokemon");
            root.appendChild(criaturaXml);

            Element id = doc.createElement("id");
            id.appendChild(doc.createTextNode(pokemon.getId()));
            criaturaXml.appendChild(id);

            Element nombreXml = doc.createElement("nombre");
            nombreXml.appendChild(doc.createTextNode(pokemon.getNombre()));
            criaturaXml.appendChild(nombreXml);

            Element tiposXml = doc.createElement("tipos");
            criaturaXml.appendChild(tiposXml);

            for (String pokemon2 : pokemon.getTipos()) {
    
                Element tipoXml = doc.createElement("tipo");
                tipoXml.appendChild(doc.createTextNode(pokemon2));
                tiposXml.appendChild(tipoXml);
                
            }

            Element descripcionXml = doc.createElement("descripcion");
            descripcionXml.appendChild(doc.createTextNode(pokemon.getDescripcion()));
            criaturaXml.appendChild(descripcionXml);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("src/main/resources/dos.xml"));
        transformer.transform(source, result);
    }
}
