package clavedicotomica;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author PC
 */
public class Arbol {
    private Nodo raiz;

    public Arbol() {
        this.raiz = null;
    }

    public Nodo getRaiz() {
        return raiz;
    }

    public void setRaiz(Nodo raiz) {
        this.raiz = raiz;
    }

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