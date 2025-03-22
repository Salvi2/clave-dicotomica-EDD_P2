package clavedicotomica;

/**
 * Clase que implementa una tabla de dispersión (hash table) para almacenar nodos
 * del árbol de la clave dicotómica. Permite la inserción y búsqueda de especies
 * basadas en su nombre.
 */
public class TablaDispersion {
    private static final int CAPACIDAD = 100;
    private Nodo[] tabla;

    /**
     * Constructor que inicializa la tabla de dispersión con una capacidad fija.
     */
    public TablaDispersion() {
        this.tabla = new Nodo[CAPACIDAD];
    }

    /**
     * Calcula el índice hash para una clave dada.
     *
     * @param clave La clave para la cual se calculará el hash.
     * @return El índice hash correspondiente.
     */
    private int hash(String clave) {
        return Math.abs(clave.hashCode()) % CAPACIDAD;
    }

    /**
     * Inserta un nodo en la tabla de dispersión.
     *
     * @param especie La especie que se utilizará como clave.
     * @param nodo El nodo que se almacenará en la tabla.
     */
    public void insertar(String especie, Nodo nodo) {
        int indice = hash(especie);
        tabla[indice] = nodo;
    }

    /**
     * Busca un nodo en la tabla de dispersión basado en el nombre de la especie.
     *
     * @param especie La especie que se buscará en la tabla.
     * @return El nodo correspondiente a la especie, o null si no se encuentra.
     */
    public Nodo buscar(String especie) {
        int indice = hash(especie);
        return tabla[indice];
    }
}