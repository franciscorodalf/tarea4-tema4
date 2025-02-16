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

    static List<Criatura> criaturas = new ArrayList<>();

    public List<Criatura> obtenerCriaturas() {
        try {
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
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
        return criaturas;
    }

    public Criatura obtener(Criatura criatura) {
        return null;
    }

    public void addCriatura(Criatura criatura) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element root = doc.createElement("criaturas");
            doc.appendChild(root);

            Element empleado = doc.createElement("criatura");
            root.appendChild(empleado);

            Element id = doc.createElement("id");
            id.appendChild(doc.createTextNode(criatura.getId()));
            empleado.appendChild(id);

            Element nombre = doc.createElement("nombre");
            nombre.appendChild(doc.createTextNode(criatura.getNombre()));
            empleado.appendChild(nombre);

            Element descripcion = doc.createElement("descripcion");
            descripcion.appendChild(doc.createTextNode(criatura.getDescripcion()));
            empleado.appendChild(descripcion);

            Element categoria = doc.createElement("categoria");
            categoria.appendChild(doc.createTextNode(criatura.getCategoria()));
            empleado.appendChild(categoria);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("src/main/resources/empleados2.xml"));
            transformer.transform(source, result);
        } catch (Exception e) {
           throw new IllegalArgumentException();
        }
    }

    public boolean deleteCriatura(Criatura criatura, List<Criatura> criaturas) {
        try {
            if (criatura == null) {
                return false;
            }
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

    }

    public static void volcarFicheroXml(List<Criatura> criaturas) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        Element root = doc.createElement("empleados");
        doc.appendChild(root);

        for (Criatura criatura : criaturas) {
            Element empleadoXml = doc.createElement("empleado");
            root.appendChild(empleadoXml);
            Element id = doc.createElement("id");
            id.appendChild(doc.createTextNode(criatura.getId()));
            empleadoXml.appendChild(id);
            Element nombreXml = doc.createElement("nombre");
            nombreXml.appendChild(doc.createTextNode(criatura.getNombre()));
            empleadoXml.appendChild(nombreXml);
            Element fechaNacimientoXml = doc.createElement("descripcion");
            fechaNacimientoXml.appendChild(doc.createTextNode(criatura.getDescripcion()));
            empleadoXml.appendChild(fechaNacimientoXml);
            Element puestoXml = doc.createElement("categoria");
            puestoXml.appendChild(doc.createTextNode(criatura.getCategoria()));
            empleadoXml.appendChild(puestoXml);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("src/main/resources/empleados2.xml"));
        transformer.transform(source, result);
    }
}
