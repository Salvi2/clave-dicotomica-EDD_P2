/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clavedicotomica;

/**
 *
 * @author PC
 */
public class TablaDispersion {
    private static final int CAPACIDAD = 100;
    private Nodo[] tabla;

    public TablaDispersion() {
        this.tabla = new Nodo[CAPACIDAD];
    }

    private int hash(String clave) {
        return Math.abs(clave.hashCode()) % CAPACIDAD;
    }

    public void insertar(String especie, Nodo nodo) {
        int indice = hash(especie);
        tabla[indice] = nodo;
    }

    public Nodo buscar(String especie) {
        int indice = hash(especie);
        return tabla[indice];
    }
}