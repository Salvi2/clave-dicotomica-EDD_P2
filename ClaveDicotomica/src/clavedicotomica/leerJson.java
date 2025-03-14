package clavedicotomica;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileReader;

/**
 * Clase para leer un archivo JSON y construir el árbol de la clave dicotómica.
 */
public class leerJson {

    /**
     * Construye un árbol a partir de un archivo JSON.
     *
     * @param archivo El archivo JSON a leer.
     * @param tabla La tabla de dispersión donde se almacenarán las especies.
     * @return El árbol construido.
     */
    public static Arbol construirArbol(File archivo, TablaDispersion tabla) {
        Arbol arbol = new Arbol();
        try (FileReader reader = new FileReader(archivo)) {
            JSONTokener tokener = new JSONTokener(reader);
            JSONObject jsonObject = new JSONObject(tokener);

            // Obtener el nombre de la clave dicotómica
            String nombreClave = jsonObject.keys().next();
            JSONArray especiesArray = jsonObject.getJSONArray(nombreClave);

            // Primero, encontrar todas las preguntas únicas
            // Esto es necesario para crear correctamente el árbol
            for (int i = 0; i < especiesArray.length(); i++) {
                JSONObject especieJson = especiesArray.getJSONObject(i);
                String nombreEspecie = especieJson.keys().next();
                JSONArray preguntasArray = especieJson.getJSONArray(nombreEspecie);

                // Procesar la primera pregunta (raíz) si aún no existe
                if (arbol.getRaiz() == null && preguntasArray.length() > 0) {
                    JSONObject primeraPregunta = preguntasArray.getJSONObject(0);
                    String textoPregunta = primeraPregunta.keys().next();
                    arbol.insertar(textoPregunta, null, false);
                }
            }

            // Ahora, procesar cada especie
            for (int i = 0; i < especiesArray.length(); i++) {
                JSONObject especieJson = especiesArray.getJSONObject(i);
                String nombreEspecie = especieJson.keys().next();
                JSONArray preguntasArray = especieJson.getJSONArray(nombreEspecie);

                // Iniciar desde la raíz para cada especie
                Nodo nodoActual = arbol.getRaiz();
                Nodo nodoPadre = null;
                boolean esRespuestaSi = false;

                // Procesar cada pregunta de la especie
                for (int j = 0; j < preguntasArray.length(); j++) {
                    JSONObject preguntaJson = preguntasArray.getJSONObject(j);
                    String pregunta = preguntaJson.keys().next();
                    boolean respuesta = preguntaJson.getBoolean(pregunta);

                    // Si es la primera pregunta, verificar que coincida con la raíz
                    if (j == 0) {
                        if (!pregunta.equals(nodoActual.getPregunta())) {
                            System.err.println("Error: La primera pregunta no coincide con la raíz.");
                            return null;
                        }
                    }

                    // Avanzar al siguiente nodo según la respuesta
                    nodoPadre = nodoActual;
                    esRespuestaSi = respuesta;
                    nodoActual = respuesta ? nodoActual.getSi() : nodoActual.getNo();

                    // Si el nodo no existe, crearlo
                    if (nodoActual == null) {
                        if (j == preguntasArray.length() - 1) {
                            // Es la última pregunta, crear un nodo hoja con la especie
                            Nodo nodoEspecie = new Nodo(null);
                            nodoEspecie.setEspecie(nombreEspecie);
                            
                            if (esRespuestaSi) {
                                nodoPadre.setSi(nodoEspecie);
                            } else {
                                nodoPadre.setNo(nodoEspecie);
                            }
                            
                            // Guardar el nodo en la tabla de dispersión
                            tabla.insertar(nombreEspecie, nodoEspecie);
                            
                            // Actualizar el nodo actual
                            nodoActual = nodoEspecie;
                        } else {
                            // No es la última pregunta, crear un nodo de pregunta
                            String siguientePregunta = preguntasArray.getJSONObject(j + 1).keys().next();
                            Nodo nuevaPregunta = new Nodo(siguientePregunta);
                            
                            if (esRespuestaSi) {
                                nodoPadre.setSi(nuevaPregunta);
                            } else {
                                nodoPadre.setNo(nuevaPregunta);
                            }
                            
                            // Actualizar el nodo actual
                            nodoActual = nuevaPregunta;
                        }
                    }
                }
            }

            // Verificar que el árbol se construyó correctamente
            if (arbol.getRaiz() == null) {
                System.err.println("Error: No se pudo construir el árbol.");
                return null;
            }

            return arbol;
        } catch (Exception e) {
            System.err.println("Error al leer el archivo JSON: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}