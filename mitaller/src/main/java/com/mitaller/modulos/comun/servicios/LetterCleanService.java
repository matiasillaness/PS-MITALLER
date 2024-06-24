package com.mitaller.modulos.comun.servicios;


import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Service
public class LetterCleanService {
    public static <T> void convertirStringToNormal(T objeto) throws IllegalAccessException {
        // Obtenemos la clase del objeto
        Class<?> clazz = objeto.getClass();

        // Recorremos todos los campos de la clase
        for (Field field : clazz.getDeclaredFields()) {
            // Permitimos el acceso a campos privados
            field.setAccessible(true);

            // Verificamos si el campo es de tipo String
            if (field.getType() == String.class) {
                // Obtenemos el valor actual del campo
                String valor = (String) field.get(objeto);
                // Si el valor no es nulo, lo convertimos
                if (valor != null) {
                    // Convertimos el valor según el formato deseado
                    String nuevoValor = convertirFormato(valor);
                    // Asignamos el nuevo valor al campo
                    field.set(objeto, nuevoValor);
                }
            }
        }
    }

    // Método para convertir una lista de objetos
    public static <T> void convertirStringToNormal(List<T> objetos) throws IllegalAccessException {
        // Iteramos sobre la lista de objetos
        for (T objeto : objetos) {
            // Convertimos cada objeto individualmente llamando al método convertirStringToNormal(objeto)
            convertirStringToNormal(objeto);
        }
    }

    public static String convertirFormato(String input) {
        StringBuilder result = new StringBuilder();
        String[] segments = input.split("\\.");

        for (int i = 0; i < segments.length; i++) {
            String segment = segments[i].toLowerCase();
            if (!segment.isEmpty()) {
                char firstChar = Character.toUpperCase(segment.charAt(0));
                result.append(firstChar).append(segment.substring(1));
            }
            if (i < segments.length - 1) {
                result.append('.');
            }
        }

        return result.toString();
    }

    public static List<String> limpiarGuionesBajos(List<String> lista) {
        List<String> listaLimpia = new ArrayList<>();

        for (String cadena : lista) {
            String cadenaLimpia = cadena.replaceAll("_", "");
            listaLimpia.add(cadenaLimpia);
        }

        return listaLimpia;
    }
}
