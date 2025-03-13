
package clavedicotomica;

import java.io.File;
import java.io.FileReader;
import org.json.JSONObject;
import org.json.JSONTokener;

public class leerJson {

    public static void leerArchivoJson(File archivo) {
        try {
            // Crear un FileReader para leer el archivo
            FileReader reader = new FileReader(archivo);
            
            // Crear un JSONTokener para parsear el archivo
            JSONTokener tokener = new JSONTokener(reader);
            
            // Crear un JSONObject a partir del tokener
            JSONObject jsonObject = new JSONObject(tokener);
            
            // Aquí puedes procesar el JSONObject como necesites
            System.out.println("JSON Leído: " + jsonObject.toString());
            
            // Cerrar el FileReader
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
    

