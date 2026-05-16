/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.PDI;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Clase para procesar imágenes usando Waifu2X-ncnn-Vulkan
 *
 * @author XAV
 */
public class Waifu2XProcessor {

    private static final String WAIFU2X_EXECUTABLE = "bin/windows/waifu2x-ncnn-vulkan.exe";
    private static final String TEMP_INPUT = "temp_input.png";
    private static final String TEMP_OUTPUT = "temp_output.png";

    // Parámetros fijos por ahora
    private static final String NOISE_LEVEL = "2";  // -n 2
    private static final String SCALE = "2";        // -s 2

    /**
     * Procesa una imagen con Waifu2X
     *
     * @param inputImage Imagen de entrada (BufferedImage)
     * @return Imagen procesada (BufferedImage) o null si hay error
     */
    public static BufferedImage upscaleImage(BufferedImage inputImage, int noiseLevel, int scale, boolean useTTA) {
        if (inputImage == null) {
            System.err.println("Error: inputImage es null");
            return null;
        }

        try {
            File tempInput = new File(TEMP_INPUT);
            boolean writeSuccess = ImageIO.write(inputImage, "png", tempInput);

            if (!writeSuccess) {
                System.err.println("Error: No se pudo escribir el archivo temporal de entrada");
                return null;
            }

            System.out.println("Archivo temporal creado: " + tempInput.getAbsolutePath());

            // Construir el comando con parámetros dinámicos
            StringBuilder commandBuilder = new StringBuilder();
            commandBuilder.append(String.format("\"%s\" -i \"%s\" -o \"%s\" -n %d -s %d",
                    WAIFU2X_EXECUTABLE,
                    tempInput.getAbsolutePath(),
                    TEMP_OUTPUT,
                    noiseLevel,
                    scale
            ));

            // Agregar TTA si está habilitado
            if (useTTA) {
                commandBuilder.append(" -x");
            }

            String command = commandBuilder.toString();
            System.out.println("Ejecutando comando: " + command);

            Process process = Runtime.getRuntime().exec(command);

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
                System.out.println("[Waifu2X] " + line);
            }

            int exitCode = process.waitFor();
            System.out.println("Proceso finalizado con código: " + exitCode);

            if (exitCode != 0) {
                System.err.println("Error: Waifu2X falló con código de salida: " + exitCode);
                System.err.println("Salida: " + output.toString());
                return null;
            }

            File outputFile = new File(TEMP_OUTPUT);
            if (!outputFile.exists()) {
                System.err.println("Error: El archivo de salida no fue creado");
                return null;
            }

            BufferedImage resultImage = ImageIO.read(outputFile);
            System.out.println("Imagen procesada exitosamente");
            System.out.println("Dimensiones originales: " + inputImage.getWidth() + "x" + inputImage.getHeight());
            System.out.println("Dimensiones finales: " + resultImage.getWidth() + "x" + resultImage.getHeight());

            cleanupTemporaryFiles(tempInput, outputFile);

            return resultImage;

        } catch (IOException ex) {
            System.err.println("Error de IO: " + ex.getMessage());
            ex.printStackTrace();
            return null;
        } catch (InterruptedException ex) {
            System.err.println("Error: El proceso fue interrumpido: " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Verifica si Waifu2X está disponible en el sistema
     *
     * @return true si el ejecutable existe
     */
    public static boolean isWaifu2XAvailable() {
        File waifu2x = new File(WAIFU2X_EXECUTABLE);
        return waifu2x.exists() && waifu2x.isFile();
    }

    /**
     * Limpia los archivos temporales
     */
    private static void cleanupTemporaryFiles(File... files) {
        for (File file : files) {
            if (file != null && file.exists()) {
                if (file.delete()) {
                    System.out.println("Archivo temporal eliminado: " + file.getName());
                } else {
                    System.out.println("Advertencia: No se pudo eliminar: " + file.getName());
                }
            }
        }
    }

    /**
     * Obtiene la información de configuración actual
     *
     * @return String con los parámetros configurados
     */
    public static String getConfigInfo() {
        return String.format("Waifu2X Configuración:\n- Noise Level: %s\n- Scale: %sx",
                NOISE_LEVEL, SCALE);
    }
}
