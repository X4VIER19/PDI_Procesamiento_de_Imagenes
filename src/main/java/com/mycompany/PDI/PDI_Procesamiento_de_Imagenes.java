    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.PDI;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.UIManager;

/**
 *
 * @author xavfe
 */
public class PDI_Procesamiento_de_Imagenes {

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
        });
    }
}
