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
            String execPath = getWaifu2XExecutablePath();
            commandBuilder.append(String.format("\"%s\" -i \"%s\" -o \"%s\" -n %d -s %d",
                    execPath,
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
            Process process;
            String osType = getOperatingSystem();

            try {
                if (osType.equals("linux") || osType.equals("macos")) {
                    // En Linux/Mac, usar bash
                    String[] cmd = {"/bin/bash", "-c", command};
                    process = Runtime.getRuntime().exec(cmd);
                } else {
                    // En Windows, ejecutar directamente
                    process = Runtime.getRuntime().exec(command);
                }
            } catch (IOException ex) {
                System.err.println("Error al ejecutar Waifu2X: " + ex.getMessage());
                ex.printStackTrace();
                return null;
            }
            System.out.println("Ejecutando comando: " + command);

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            String line;
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
                System.out.println("[Waifu2X] " + line);
            }

            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                System.err.println("[Waifu2X Error] " + errorLine);
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

    public static String getOperatingSystem() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return "windows";
        } else if (os.contains("linux")) {
            return "linux";
        } else if (os.contains("mac")) {
            return "macos";
        }
        return "unknown";
    }

    private static String getWaifu2XExecutablePath() {
        String os = getOperatingSystem();
        switch (os) {
            case "windows":
                return "bin/windows/waifu2x-ncnn-vulkan.exe";
            case "linux":
                return "bin/linux/waifu2x-ncnn-vulkan";
            /*
            case "macos":
                return "bin/macos/waifu2x-ncnn-vulkan";*/
            default:
                return "waifu2x-ncnn-vulkan";
        }
    }

    /**
     * Verifica si Waifu2X está disponible en el sistema
     *
     * @return true si el ejecutable existe
     */
    public static boolean isWaifu2XAvailable() {
        String execPath = getWaifu2XExecutablePath();
        File waifu2x = new File(execPath);

        if (!waifu2x.exists()) {
            return false;
        }

        // En Linux, verificar que sea ejecutable
        if (getOperatingSystem().equals("linux")) {
            return waifu2x.canExecute();
        }

        return true;
    }

    public static boolean checkLinuxPermissions() {
        if (!getOperatingSystem().equals("linux")) {
            return true; // No es Linux, no aplica
        }

        String execPath = getWaifu2XExecutablePath();
        File waifu2x = new File(execPath);
        return waifu2x.canExecute();
    }

    public static void fixLinuxPermissions() {
        if (!getOperatingSystem().equals("linux")) {
            return; // No es Linux, no aplica
        }

        try {
            String execPath = getWaifu2XExecutablePath();
            File waifu2x = new File(execPath);
            waifu2x.setExecutable(true, false); // true para propietario, false para otros
            System.out.println("Permisos asignados a: " + execPath);
        } catch (Exception e) {
            System.err.println("Error al asignar permisos: " + e.getMessage());
        }
    }

    public static String getWaifu2XErrorMessage() {
        String os = getOperatingSystem();
        String execPath = getWaifu2XExecutablePath();
        File waifu2x = new File(execPath);

        if (!waifu2x.exists()) {
            return "El archivo de Waifu2X no existe en:\n" + execPath + "\n\n"
                    + "Asegúrate de que el directorio 'bin/" + os + "/' existe y contiene el ejecutable.";
        }

        if (os.equals("linux") && !waifu2x.canExecute()) {
            return "El archivo de Waifu2X no tiene permisos de ejecución en Linux.\n"
                    + "Ruta: " + execPath + "\n\n"
                    + "Para arreglarlo, abre una terminal y ejecuta:\n"
                    + "chmod +x " + execPath;
        }

        return "No se puede acceder a Waifu2X.\n"
                + "Sistema Operativo: " + os + "\n"
                + "Ruta esperada: " + execPath;
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
