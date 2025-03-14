package clavedicotomica;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

/**
 * Clase para mostrar gráficamente el árbol de la clave dicotómica.
 */
public class GraficoArbol {
    private static int contadorId = 0; // Contador para generar IDs únicos

    /**
     * Muestra el árbol de la clave dicotómica en una ventana gráfica.
     *
     * @param raiz La raíz del árbol que se desea visualizar.
     */
    public static void mostrarArbol(Nodo raiz) {
        // Reiniciar el contador de IDs para cada nueva visualización
        contadorId = 0;
        
        // Verificar si el árbol está vacío
        if (raiz == null) {
            System.err.println("El árbol está vacío. No hay nada que mostrar.");
            return;
        }

        try {
            // Configurar la propiedad para renderizar con JavaFX
            System.setProperty("org.graphstream.ui", "javafx");
            
            // Crear un nuevo grafo
            Graph graph = new SingleGraph("Clave Dicotómica");

            // Configurar el estilo del grafo
            graph.setAttribute("ui.stylesheet", 
                "node { fill-color: #6495ED; text-size: 14px; size: 30px; text-color: black; text-style: bold; }" +
                "edge { fill-color: #4682B4; size: 2px; }" +
                "edge.si { fill-color: #228B22; }" +
                "edge.no { fill-color: #B22222; }");

            // Crear los nodos del árbol en el grafo
            construirGrafo(graph, raiz);

            // Verificar si el grafo tiene nodos
            if (graph.getNodeCount() == 0) {
                System.err.println("El grafo no tiene nodos. Verifica la estructura del árbol.");
                return;
            }

            // Crear un visor para el grafo usando JavaFX
            Viewer viewer = graph.display();
            viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
            
        } catch (Exception e) {
            System.err.println("Error al mostrar el árbol: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Método para construir el grafo a partir del árbol.
     * 
     * @param graph El grafo donde se construirán los nodos.
     * @param raiz La raíz del árbol.
     */
    private static void construirGrafo(Graph graph, Nodo raiz) {
        // Crear nodo raíz
        String idRaiz = "nodo_" + contadorId++;
        Node nodoRaiz = graph.addNode(idRaiz);
        
        // Establecer etiqueta del nodo
        if (raiz.getEspecie() != null) {
            nodoRaiz.setAttribute("ui.label", raiz.getEspecie());
            nodoRaiz.setAttribute("ui.style", "fill-color: #32CD32;");
        } else {
            nodoRaiz.setAttribute("ui.label", raiz.getPregunta());
        }
        
        // Recursivamente construir el árbol
        construirSubArbol(graph, raiz, idRaiz);
    }
    
    /**
     * Método recursivo para construir el subárbol.
     * 
     * @param graph El grafo donde se construirán los nodos.
     * @param nodoActual El nodo actual del árbol.
     * @param idPadre El ID del nodo padre en el grafo.
     */
    private static void construirSubArbol(Graph graph, Nodo nodoActual, String idPadre) {
        // Construir subárbol SI
        if (nodoActual.getSi() != null) {
            String idSi = "nodo_" + contadorId++;
            Node nodoSi = graph.addNode(idSi);
            
            if (nodoActual.getSi().getEspecie() != null) {
                nodoSi.setAttribute("ui.label", nodoActual.getSi().getEspecie());
                nodoSi.setAttribute("ui.style", "fill-color: #32CD32;");
            } else {
                nodoSi.setAttribute("ui.label", nodoActual.getSi().getPregunta());
            }
            
            // Crear arista SI
            String idArista = idPadre + "-" + idSi;
            graph.addEdge(idArista, idPadre, idSi, true);
            graph.getEdge(idArista).setAttribute("ui.label", "Sí");
            graph.getEdge(idArista).setAttribute("ui.class", "si");
            
            // Recursivamente construir subárbol SI
            construirSubArbol(graph, nodoActual.getSi(), idSi);
        }
        
        // Construir subárbol NO
        if (nodoActual.getNo() != null) {
            String idNo = "nodo_" + contadorId++;
            Node nodoNo = graph.addNode(idNo);
            
            if (nodoActual.getNo().getEspecie() != null) {
                nodoNo.setAttribute("ui.label", nodoActual.getNo().getEspecie());
                nodoNo.setAttribute("ui.style", "fill-color: #32CD32;");
            } else {
                nodoNo.setAttribute("ui.label", nodoActual.getNo().getPregunta());
            }
            
            // Crear arista NO
            String idArista = idPadre + "-" + idNo;
            graph.addEdge(idArista, idPadre, idNo, true);
            graph.getEdge(idArista).setAttribute("ui.label", "No");
            graph.getEdge(idArista).setAttribute("ui.class", "no");
            
            // Recursivamente construir subárbol NO
            construirSubArbol(graph, nodoActual.getNo(), idNo);
        }
    }
}