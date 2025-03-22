package clavedicotomica;

/**
 * Clase que representa un árbol binario utilizado para almacenar una clave dicotómica.
 * El árbol contiene nodos que representan preguntas o especies, y permite la inserción
 * de nuevos nodos basados en respuestas de "sí" o "no".
 */
public class Arbol {
    private Nodo raiz;

    /**
     * Constructor por defecto que inicializa un árbol vacío.
     */
    public Arbol() {
        this.raiz = null;
    }

    /**
     * Obtiene la raíz del árbol.
     *
     * @return El nodo raíz del árbol.
     */
    public Nodo getRaiz() {
        return raiz;
    }

    /**
     * Establece la raíz del árbol.
     *
     * @param raiz El nodo que se establecerá como raíz del árbol.
     */
    public void setRaiz(Nodo raiz) {
        this.raiz = raiz;
    }

    /**
     * Inserta un nuevo nodo en el árbol basado en una pregunta y una respuesta.
     *
     * @param pregunta La pregunta que se almacenará en el nodo.
     * @param padre El nodo padre al que se conectará el nuevo nodo.
     * @param esRespuestaSi Indica si el nuevo nodo debe conectarse como respuesta "sí" (true) o "no" (false).
     */
    public void insertar(String pregunta, Nodo padre, boolean esRespuestaSi) {
        Nodo nuevoNodo = new Nodo(pregunta);
        if (padre == null) {
            raiz = nuevoNodo;
        } else {
            if (esRespuestaSi) {
                padre.setSi(nuevoNodo);
            } else {
                padre.setNo(nuevoNodo);
            }
        }
    }
}