    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.x4vier19.PDI;

import com.x4vier19.PDI.Frames.frameBienvenida;
import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.UIManager;

/**
 *
 * @author xavfe
 */
public class PDI_Procesamiento_de_Imagenes {

    
    // Tets de commit y push
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            System.err.println("No se pudo aplicar FlatLaf Dark: " + e.getMessage());
        }

        java.awt.EventQueue.invokeLater(() -> {
            frameBienvenida frame = new frameBienvenida();
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
            // Estilo profecional 
        });
    }
}
