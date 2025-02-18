package es.ies.puerto.file.uno;

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

public class FileXmlCriatura {
    List<Criatura> criaturasFile;

    public FileXmlCriatura(){
        criaturasFile = obtenerCriaturas();
    }

    public List<Criatura> obtenerCriaturas() {
        try {
            List<Criatura> criaturas = new ArrayList<>();
            File archivo = new File("src/main/resources/uno.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(archivo);

            NodeList lista = doc.getElementsByTagName("criatura");
            for (int i = 0; i < lista.getLength(); i++) {
                Node nodo = lista.item(i);
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) nodo;
                    String id = elemento.getElementsByTagName("id").item(0).getTextContent();
                    String nombre = elemento.getElementsByTagName("nombre").item(0).getTextContent();
                    String descripcion = elemento.getElementsByTagName("descripcion").item(0).getTextContent();
                    String categoria = elemento.getElementsByTagName("categoria").item(0).getTextContent();
                    Criatura criatura = new Criatura(id, nombre, descripcion, categoria);
                    criaturas.add(criatura);
                }
            }
            return criaturas;
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public Criatura obtener(Criatura criatura) {

        File file = new File("src/main/resources/uno.xml");
        if (!file.exists()) {
            throw new IllegalArgumentException("El archivo xml no existe");
        }
        List<Criatura> criaturas = obtenerCriaturas();
        int posicionCriatura = criaturas.indexOf(criatura);
        if (posicionCriatura < 0) {
            throw new IllegalArgumentException("La criatura no existe");
        }
        return criaturas.get(posicionCriatura);

    }

    public void addCriatura(Criatura criatura) throws Exception {

        if (criatura == null || criatura.getId() == null || criatura.getId().isEmpty()) {
            return;
        }
        List<Criatura> criaturas = obtenerCriaturas();
        if (criaturas.contains(criatura)) {
            return;
        }

        criaturas.add(criatura);
        volcarFicheroXml(criaturas);
    }

    public boolean deleteCriatura(Criatura criatura) {
        if (criatura == null || criatura.getId() == null || criatura.getId().isEmpty()) {
            return false;
        }
        List<Criatura> criaturas = obtenerCriaturas();
        if (!criaturas.contains(criatura)) {
            return false;
        }

        try {
            boolean eliminado = criaturas.remove(criatura);

            if (eliminado) {
                volcarFicheroXml(criaturas);
            }
            return eliminado;
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public void updateCriatura(Criatura criatura) {
        if (criatura == null || criatura.getId() == null || criatura.getId().isEmpty()) {
            return;
        }
        List<Criatura> criaturas = obtenerCriaturas();
        if (!criaturas.contains(criatura)) {
            return;
        }

        criaturas.remove(criatura);
        criaturas.add(criatura);


        try {
            volcarFicheroXml(criaturas);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void volcarFicheroXml(List<Criatura> criaturas) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        Element root = doc.createElement("criaturas");
        doc.appendChild(root);

        for (Criatura criatura : criaturas) {

            Element criaturaXml = doc.createElement("criatura");
            root.appendChild(criaturaXml);

            Element id = doc.createElement("id");
            id.appendChild(doc.createTextNode(criatura.getId()));
            criaturaXml.appendChild(id);

            Element nombreXml = doc.createElement("nombre");
            nombreXml.appendChild(doc.createTextNode(criatura.getNombre()));
            criaturaXml.appendChild(nombreXml);

            Element fechaNacimientoXml = doc.createElement("descripcion");
            fechaNacimientoXml.appendChild(doc.createTextNode(criatura.getDescripcion()));
            criaturaXml.appendChild(fechaNacimientoXml);

            Element puestoXml = doc.createElement("categoria");
            puestoXml.appendChild(doc.createTextNode(criatura.getCategoria()));
            criaturaXml.appendChild(puestoXml);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("src/main/resources/uno.xml"));
        transformer.transform(source, result);
    }
}
