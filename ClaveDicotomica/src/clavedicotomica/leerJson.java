package clavedicotomica;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileReader;

public class leerJson {

    public static Arbol construirArbol(File archivo, TablaDispersion tabla) {
        Arbol arbol = new Arbol();
        try (FileReader reader = new FileReader(archivo)) {
            JSONTokener tokener = new JSONTokener(reader);
            JSONObject jsonObject = new JSONObject(tokener);

            // Obtener el nombre de la clave dicotómica
            String nombreClave = jsonObject.keys().next();
            JSONArray especiesArray = jsonObject.getJSONArray(nombreClave);

            // Procesar cada especie
            for (int i = 0; i < especiesArray.length(); i++) {
                JSONObject especieJson = especiesArray.getJSONObject(i);
                String nombreEspecie = especieJson.keys().next();
                JSONArray preguntasArray = especieJson.getJSONArray(nombreEspecie);

                // Insertar las preguntas en el árbol
                Nodo nodoActual = arbol.getRaiz();
                for (int j = 0; j < preguntasArray.length(); j++) {
                    JSONObject preguntaJson = preguntasArray.getJSONObject(j);
                    String pregunta = preguntaJson.keys().next();
                    boolean respuesta = preguntaJson.getBoolean(pregunta);

                    if (nodoActual == null) {
                        arbol.insertar(pregunta, null, false);
                        nodoActual = arbol.getRaiz();
                    } else {
                        arbol.insertar(pregunta, nodoActual, respuesta);
                        nodoActual = respuesta ? nodoActual.getSi() : nodoActual.getNo();
                    }
                }

                // Asignar la especie al nodo hoja
                nodoActual.setEspecie(nombreEspecie);

                // Insertar el nodo en la tabla de dispersión
                tabla.insertar(nombreEspecie, nodoActual);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return arbol;
    }
}