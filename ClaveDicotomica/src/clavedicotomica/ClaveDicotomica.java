package clavedicotomica;

import ventanaInicio.Inicio;

/**
 * Clase principal que inicia la aplicación de la clave dicotómica.
 * Esta clase carga la interfaz gráfica de inicio y la muestra al usuario.
 */
public class ClaveDicotomica {

    /**
     * Método principal que inicia la aplicación.
     */
    public static void main(String[] args) {
        Inicio inicio = new Inicio();
        inicio.setVisible(true);
        inicio.setLocationRelativeTo(null);
    }
}