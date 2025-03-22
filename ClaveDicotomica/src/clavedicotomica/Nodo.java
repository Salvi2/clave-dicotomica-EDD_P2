package clavedicotomica;

/**
 * Clase que representa un nodo en el árbol de la clave dicotómica.
 * Cada nodo puede contener una pregunta o una especie, y tiene referencias
 * a los nodos hijos correspondientes a las respuestas "sí" y "no".
 */
public class Nodo {
    private String pregunta;
    private Nodo si;
    private Nodo no;
    private String especie;

    /**
     * Constructor que inicializa un nodo con una pregunta.
     *
     * @param pregunta La pregunta que se almacenará en el nodo.
     */
    public Nodo(String pregunta) {
        this.pregunta = pregunta;
        this.si = null;
        this.no = null;
        this.especie = null;
    }

    /**
     * Obtiene la pregunta almacenada en el nodo.
     *
     * @return La pregunta del nodo.
     */
    public String getPregunta() {
        return pregunta;
    }

    /**
     * Obtiene el nodo hijo correspondiente a la respuesta "sí".
     *
     * @return El nodo hijo "sí".
     */
    public Nodo getSi() {
        return si;
    }

    /**
     * Establece el nodo hijo correspondiente a la respuesta "sí".
     *
     * @param si El nodo hijo "sí".
     */
    public void setSi(Nodo si) {
        this.si = si;
    }

    /**
     * Obtiene el nodo hijo correspondiente a la respuesta "no".
     *
     * @return El nodo hijo "no".
     */
    public Nodo getNo() {
        return no;
    }

    /**
     * Establece el nodo hijo correspondiente a la respuesta "no".
     *
     * @param no El nodo hijo "no".
     */
    public void setNo(Nodo no) {
        this.no = no;
    }

    /**
     * Obtiene la especie almacenada en el nodo.
     *
     * @return La especie del nodo.
     */
    public String getEspecie() {
        return especie;
    }

    /**
     * Establece la especie en el nodo.
     *
     * @param especie La especie que se almacenará en el nodo.
     */
    public void setEspecie(String especie) {
        this.especie = especie;
    }
}