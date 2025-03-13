/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clavedicotomica;

/**
 *
 * @author PC
 */
public class Nodo {
    private String pregunta;
    private Nodo si;
    private Nodo no;
    private String especie;

    public Nodo(String pregunta) {
        this.pregunta = pregunta;
        this.si = null;
        this.no = null;
        this.especie = null;
    }

    // Getters y setters
    public String getPregunta() {
        return pregunta;
    }

    public Nodo getSi() {
        return si;
    }

    public void setSi(Nodo si) {
        this.si = si;
    }

    public Nodo getNo() {
        return no;
    }

    public void setNo(Nodo no) {
        this.no = no;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }
}