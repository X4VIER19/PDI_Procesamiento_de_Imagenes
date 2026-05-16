/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.PDI;

/**
 *
 * @author XAV
 */
public class mainFrame extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(mainFrame.class.getName());

    private java.awt.image.BufferedImage imagenOriginal;
    private java.awt.image.BufferedImage imagenGrises;
    private java.awt.image.BufferedImage imagenFinal;
    private int[] freqRojo = new int[256];
    private int[] freqVerde = new int[256];
    private int[] freqAzul = new int[256];
    private int[] freqLuma = new int[256];
    private int totalPixeles = 0;

    /**
     * Creates new form mainFrame
     */
    public mainFrame() {
        initComponents();
    }

    private void calcularEstadisticas(java.awt.image.BufferedImage img) {
        freqRojo = new int[256];
        freqVerde = new int[256];
        freqAzul = new int[256];
        freqLuma = new int[256];

        int ancho = img.getWidth();
        int alto = img.getHeight();
        totalPixeles = ancho * alto;

        imagenGrises = new java.awt.image.BufferedImage(ancho, alto, java.awt.image.BufferedImage.TYPE_BYTE_GRAY);

        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                int rgb = img.getRGB(x, y);

                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                // Fórmula estandar para escala de grises
                int luma = (int) (0.299 * r + 0.587 * g + 0.114 * b);

                freqRojo[r]++;
                freqVerde[g]++;
                freqAzul[b]++;
                freqLuma[luma]++;

                imagenGrises.getRaster().setSample(x, y, 0, luma);
            }
        }
    }

    private void llenarTablaFrecuencias() {
        javax.swing.table.DefaultTableModel modelo
                = (javax.swing.table.DefaultTableModel) tablaHistogramaData.getModel();

        modelo.setRowCount(0);

        long sumaR = 0, sumaG = 0, sumaB = 0, sumaLuma = 0;

        int maxR = 0, maxG = 0, maxB = 0, maxL = 0;

        for (int nivel = 0; nivel < 256; nivel++) {
            int vR = freqRojo[nivel];
            int vG = freqVerde[nivel];
            int vB = freqAzul[nivel];
            int vL = freqLuma[nivel];

            sumaR += (long) nivel * vR;
            sumaG += (long) nivel * vG;
            sumaB += (long) nivel * vB;
            sumaLuma += (long) nivel * vL;

            if (freqRojo[nivel] > freqRojo[maxR]) {
                maxR = nivel;
            }
            if (freqVerde[nivel] > freqVerde[maxG]) {
                maxG = nivel;
            }
            if (freqAzul[nivel] > freqAzul[maxB]) {
                maxB = nivel;
            }
            if (freqLuma[nivel] > freqLuma[maxL]) {
                maxL = nivel;
            }

            double pR = (vR * 100.0) / totalPixeles;
            double pG = (vG * 100.0) / totalPixeles;
            double pB = (vB * 100.0) / totalPixeles;
            double pL = (vL * 100.0) / totalPixeles;

            modelo.addRow(new Object[]{
                nivel,
                formatearCelda(vR, pR),
                formatearCelda(vG, pG),
                formatearCelda(vB, pB),
                formatearCelda(vL, pL)
            });
        }

        modelo.addRow(new Object[]{
            "Promedio",
            String.format("%.1f", totalPixeles > 0 ? (double) sumaR / totalPixeles : 0),
            String.format("%.1f", totalPixeles > 0 ? (double) sumaG / totalPixeles : 0),
            String.format("%.1f", totalPixeles > 0 ? (double) sumaB / totalPixeles : 0),
            String.format("%.1f", totalPixeles > 0 ? (double) sumaLuma / totalPixeles : 0)
        });

        java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();
        modelo.addRow(new Object[]{
            "Total",
            nf.format(totalPixeles) + " (100%)",
            nf.format(totalPixeles) + " (100%)",
            nf.format(totalPixeles) + " (100%)",
            nf.format(totalPixeles) + " (100%)"
        });
    }

    private void mostrarEnPreview() {
        if (imagenFinal == null) {
            return;
        }

        javax.swing.ImageIcon icono = new javax.swing.ImageIcon(
                imagenFinal.getScaledInstance(panelImagenPreview.getWidth(), panelImagenPreview.getHeight(), java.awt.Image.SCALE_SMOOTH));

        panelImagenPreview.removeAll();
        panelImagenPreview.setLayout(new java.awt.BorderLayout());

        javax.swing.JLabel labelImagen = new javax.swing.JLabel(icono);
        labelImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        panelImagenPreview.add(labelImagen, java.awt.BorderLayout.CENTER);
        panelImagenPreview.revalidate();
        panelImagenPreview.repaint();
    }

    private String formatearCelda(int cantidad, double porcentaje) {
        java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();
        return nf.format(cantidad) + " (" + String.format("%.2f", porcentaje) + "%)";
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        menuTopPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        buttonAbrir = new javax.swing.JButton();
        buttonInfo = new javax.swing.JButton();
        buttonSalir = new javax.swing.JButton();
        labelTitulo = new javax.swing.JLabel();
        splitPaneMain = new javax.swing.JSplitPane();
        panelColumnaIzquierda = new javax.swing.JPanel();
        panelImagenOriginal = new javax.swing.JPanel();
        panelHeaderOriginal = new javax.swing.JPanel();
        labelHeaderOriginal = new javax.swing.JLabel();
        canvasImagenOriginal = new javax.swing.JPanel();
        tabbedPaneHistogramas = new javax.swing.JTabbedPane();
        tabPanelData = new javax.swing.JPanel();
        scrollTablaData = new javax.swing.JScrollPane();
        tablaHistogramaData = new javax.swing.JTable();
        tabPanelRojo = new javax.swing.JPanel();
        canvasHistogramaRojo = new javax.swing.JPanel();
        tabPanelVerde = new javax.swing.JPanel();
        canvasHistogramaVerde = new javax.swing.JPanel();
        tabPanelAzul = new javax.swing.JPanel();
        canvasHistogramaAzul = new javax.swing.JPanel();
        tabPanelLuma = new javax.swing.JPanel();
        canvasHistogramaLuma = new javax.swing.JPanel();
        panelColumnaDerechaContainer = new javax.swing.JPanel();
        panelImagenPreviewContent = new javax.swing.JPanel();
        panelHeaderPreview = new javax.swing.JPanel();
        labelHeaderPreview = new javax.swing.JLabel();
        panelImagenPreview = new javax.swing.JPanel();
        panelControlesMetadata = new javax.swing.JPanel();
        panelHeaderControles = new javax.swing.JPanel();
        labelHeaderControles = new javax.swing.JLabel();
        panelCentroControles = new javax.swing.JPanel();
        panelBotones = new javax.swing.JPanel();
        panelFilaSuperior = new javax.swing.JPanel();
        buttonUpScale = new javax.swing.JButton();
        buttonNegative = new javax.swing.JButton();
        panelFilaMedia = new javax.swing.JPanel();
        buttonBinarize = new javax.swing.JButton();
        buttonGrises = new javax.swing.JButton();
        panelFilaInferior = new javax.swing.JPanel();
        buttonResetImage = new javax.swing.JButton();
        buttonSaved = new javax.swing.JButton();
        panelInfoMetadata = new javax.swing.JPanel();
        contentLabelKey = new javax.swing.JPanel();
        labelKeyFile = new javax.swing.JLabel();
        labelKeyRes = new javax.swing.JLabel();
        labelKeyFormat = new javax.swing.JLabel();
        labelKeySource = new javax.swing.JLabel();
        contentLabelVal = new javax.swing.JPanel();
        labelValFile = new javax.swing.JLabel();
        labelValRes = new javax.swing.JLabel();
        labelValFormat = new javax.swing.JLabel();
        labelValSource = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PDI - Procesador de Imagenes");
        setBackground(new java.awt.Color(10, 10, 10));
        setMinimumSize(new java.awt.Dimension(1280, 720));

        mainPanel.setBackground(new java.awt.Color(10, 10, 10));
        mainPanel.setLayout(new java.awt.BorderLayout());

        menuTopPanel.setBackground(new java.awt.Color(0, 0, 0));
        menuTopPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(51, 51, 51)));
        menuTopPanel.setPreferredSize(new java.awt.Dimension(1280, 55));
        menuTopPanel.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));
        jPanel2.setLayout(new java.awt.BorderLayout());

        buttonAbrir.setBackground(new java.awt.Color(0, 0, 0));
        buttonAbrir.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        buttonAbrir.setForeground(new java.awt.Color(255, 255, 255));
        buttonAbrir.setText("Abrir Imagen");
        buttonAbrir.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(51, 51, 51)), javax.swing.BorderFactory.createEmptyBorder(0, 20, 0, 20)));
        buttonAbrir.setContentAreaFilled(false);
        buttonAbrir.setFocusPainted(false);
        buttonAbrir.setOpaque(false);
        buttonAbrir.addActionListener(this::buttonAbrirActionPerformed);
        jPanel2.add(buttonAbrir, java.awt.BorderLayout.CENTER);

        buttonInfo.setBackground(new java.awt.Color(0, 0, 0));
        buttonInfo.setFont(new java.awt.Font("Segoe UI Emoji", 1, 12)); // NOI18N
        buttonInfo.setForeground(new java.awt.Color(255, 255, 255));
        buttonInfo.setText("i");
        buttonInfo.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 1, new java.awt.Color(51, 51, 51)), javax.swing.BorderFactory.createEmptyBorder(0, 20, 0, 20)));
        buttonInfo.setContentAreaFilled(false);
        buttonInfo.setFocusPainted(false);
        buttonInfo.setMaximumSize(new java.awt.Dimension(50, 21));
        buttonInfo.setMinimumSize(new java.awt.Dimension(50, 21));
        buttonInfo.setOpaque(false);
        buttonInfo.setPreferredSize(new java.awt.Dimension(50, 21));
        buttonInfo.addActionListener(this::buttonInfoActionPerformed);
        jPanel2.add(buttonInfo, java.awt.BorderLayout.EAST);

        menuTopPanel.add(jPanel2, java.awt.BorderLayout.WEST);

        buttonSalir.setBackground(new java.awt.Color(185, 28, 28));
        buttonSalir.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        buttonSalir.setForeground(new java.awt.Color(255, 255, 255));
        buttonSalir.setText("Salir");
        buttonSalir.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 0, new java.awt.Color(51, 51, 51)), javax.swing.BorderFactory.createEmptyBorder(0, 20, 0, 20)));
        buttonSalir.setContentAreaFilled(true);
        buttonSalir.setFocusPainted(false);
        buttonSalir.setOpaque(true);
        buttonSalir.setPreferredSize(new java.awt.Dimension(100, 55));
        buttonSalir.addActionListener(this::buttonSalirActionPerformed);
        menuTopPanel.add(buttonSalir, java.awt.BorderLayout.EAST);

        labelTitulo.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        labelTitulo.setForeground(new java.awt.Color(185, 28, 28));
        labelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTitulo.setText("PROCESADOR DE IMAGENES");
        menuTopPanel.add(labelTitulo, java.awt.BorderLayout.CENTER);

        mainPanel.add(menuTopPanel, java.awt.BorderLayout.PAGE_START);

        splitPaneMain.setBackground(new java.awt.Color(10, 10, 10));
        splitPaneMain.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 8, 8, 8));
        splitPaneMain.setDividerLocation(576);
        splitPaneMain.setDividerSize(4);
        splitPaneMain.setResizeWeight(0.5);
        splitPaneMain.setEnabled(false);

        panelColumnaIzquierda.setBackground(new java.awt.Color(10, 10, 10));
        panelColumnaIzquierda.setMaximumSize(new java.awt.Dimension(9999, 9999));
        panelColumnaIzquierda.setMinimumSize(new java.awt.Dimension(576, 657));
        panelColumnaIzquierda.setPreferredSize(new java.awt.Dimension(576, 657));
        panelColumnaIzquierda.setLayout(new javax.swing.BoxLayout(panelColumnaIzquierda, javax.swing.BoxLayout.Y_AXIS));

        panelImagenOriginal.setBackground(new java.awt.Color(26, 26, 26));
        panelImagenOriginal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        panelImagenOriginal.setMaximumSize(new java.awt.Dimension(9999, 9999));
        panelImagenOriginal.setMinimumSize(new java.awt.Dimension(576, 390));
        panelImagenOriginal.setPreferredSize(new java.awt.Dimension(576, 390));
        panelImagenOriginal.setLayout(new java.awt.BorderLayout());

        panelHeaderOriginal.setBackground(new java.awt.Color(17, 17, 17));
        panelHeaderOriginal.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(51, 51, 51)));
        panelHeaderOriginal.setPreferredSize(new java.awt.Dimension(576, 26));

        labelHeaderOriginal.setFont(new java.awt.Font("SansSerif", 1, 10)); // NOI18N
        labelHeaderOriginal.setForeground(new java.awt.Color(160, 160, 160));
        labelHeaderOriginal.setText("Imagen Original");
        panelHeaderOriginal.add(labelHeaderOriginal);

        panelImagenOriginal.add(panelHeaderOriginal, java.awt.BorderLayout.PAGE_START);

        canvasImagenOriginal.setBackground(new java.awt.Color(12, 12, 12));
        canvasImagenOriginal.setLayout(new java.awt.BorderLayout());
        panelImagenOriginal.add(canvasImagenOriginal, java.awt.BorderLayout.CENTER);

        panelColumnaIzquierda.add(panelImagenOriginal);

        tabbedPaneHistogramas.setBackground(new java.awt.Color(26, 26, 26));
        tabbedPaneHistogramas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        tabbedPaneHistogramas.setForeground(new java.awt.Color(160, 160, 160));
        tabbedPaneHistogramas.setFont(new java.awt.Font("SansSerif", 1, 10)); // NOI18N
        tabbedPaneHistogramas.setMaximumSize(new java.awt.Dimension(9999, 255));
        tabbedPaneHistogramas.setPreferredSize(new java.awt.Dimension(576, 255));

        tabPanelData.setBackground(new java.awt.Color(26, 26, 26));
        tabPanelData.setLayout(new java.awt.BorderLayout());

        tablaHistogramaData.setBackground(new java.awt.Color(26, 26, 26));
        tablaHistogramaData.setForeground(new java.awt.Color(160, 160, 160));
        tablaHistogramaData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nivel", "Rojo (R)", "Verde (G)", "Azul (B)", "Grises (Luma)"
            }
        ));
        tablaHistogramaData.setGridColor(new java.awt.Color(34, 34, 34));
        tablaHistogramaData.setRowHeight(22);
        scrollTablaData.setViewportView(tablaHistogramaData);

        tabPanelData.add(scrollTablaData, java.awt.BorderLayout.CENTER);

        tabbedPaneHistogramas.addTab("Data", tabPanelData);

        tabPanelRojo.setBackground(new java.awt.Color(26, 26, 26));
        tabPanelRojo.setLayout(new java.awt.BorderLayout());

        canvasHistogramaRojo.setBackground(new java.awt.Color(12, 12, 12));
        canvasHistogramaRojo.setPreferredSize(new java.awt.Dimension(576, 170));

        javax.swing.GroupLayout canvasHistogramaRojoLayout = new javax.swing.GroupLayout(canvasHistogramaRojo);
        canvasHistogramaRojo.setLayout(canvasHistogramaRojoLayout);
        canvasHistogramaRojoLayout.setHorizontalGroup(
            canvasHistogramaRojoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 566, Short.MAX_VALUE)
        );
        canvasHistogramaRojoLayout.setVerticalGroup(
            canvasHistogramaRojoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 218, Short.MAX_VALUE)
        );

        tabPanelRojo.add(canvasHistogramaRojo, java.awt.BorderLayout.CENTER);

        tabbedPaneHistogramas.addTab("Red", tabPanelRojo);

        tabPanelVerde.setBackground(new java.awt.Color(26, 26, 26));
        tabPanelVerde.setLayout(new java.awt.BorderLayout());

        canvasHistogramaVerde.setBackground(new java.awt.Color(12, 12, 12));
        canvasHistogramaVerde.setPreferredSize(new java.awt.Dimension(576, 170));

        javax.swing.GroupLayout canvasHistogramaVerdeLayout = new javax.swing.GroupLayout(canvasHistogramaVerde);
        canvasHistogramaVerde.setLayout(canvasHistogramaVerdeLayout);
        canvasHistogramaVerdeLayout.setHorizontalGroup(
            canvasHistogramaVerdeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 566, Short.MAX_VALUE)
        );
        canvasHistogramaVerdeLayout.setVerticalGroup(
            canvasHistogramaVerdeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 218, Short.MAX_VALUE)
        );

        tabPanelVerde.add(canvasHistogramaVerde, java.awt.BorderLayout.CENTER);

        tabbedPaneHistogramas.addTab("Green", tabPanelVerde);

        tabPanelAzul.setBackground(new java.awt.Color(26, 26, 26));
        tabPanelAzul.setLayout(new java.awt.BorderLayout());

        canvasHistogramaAzul.setBackground(new java.awt.Color(12, 12, 12));
        canvasHistogramaAzul.setPreferredSize(new java.awt.Dimension(576, 170));

        javax.swing.GroupLayout canvasHistogramaAzulLayout = new javax.swing.GroupLayout(canvasHistogramaAzul);
        canvasHistogramaAzul.setLayout(canvasHistogramaAzulLayout);
        canvasHistogramaAzulLayout.setHorizontalGroup(
            canvasHistogramaAzulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 566, Short.MAX_VALUE)
        );
        canvasHistogramaAzulLayout.setVerticalGroup(
            canvasHistogramaAzulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 218, Short.MAX_VALUE)
        );

        tabPanelAzul.add(canvasHistogramaAzul, java.awt.BorderLayout.CENTER);

        tabbedPaneHistogramas.addTab("Blue", tabPanelAzul);

        tabPanelLuma.setBackground(new java.awt.Color(26, 26, 26));
        tabPanelLuma.setLayout(new java.awt.BorderLayout());

        canvasHistogramaLuma.setBackground(new java.awt.Color(12, 12, 12));
        canvasHistogramaLuma.setPreferredSize(new java.awt.Dimension(576, 170));

        javax.swing.GroupLayout canvasHistogramaLumaLayout = new javax.swing.GroupLayout(canvasHistogramaLuma);
        canvasHistogramaLuma.setLayout(canvasHistogramaLumaLayout);
        canvasHistogramaLumaLayout.setHorizontalGroup(
            canvasHistogramaLumaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 566, Short.MAX_VALUE)
        );
        canvasHistogramaLumaLayout.setVerticalGroup(
            canvasHistogramaLumaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 218, Short.MAX_VALUE)
        );

        tabPanelLuma.add(canvasHistogramaLuma, java.awt.BorderLayout.CENTER);

        tabbedPaneHistogramas.addTab("Luma", tabPanelLuma);

        panelColumnaIzquierda.add(tabbedPaneHistogramas);

        splitPaneMain.setLeftComponent(panelColumnaIzquierda);

        panelColumnaDerechaContainer.setBackground(new java.awt.Color(10, 10, 10));
        panelColumnaDerechaContainer.setMaximumSize(new java.awt.Dimension(9999, 9999));
        panelColumnaDerechaContainer.setPreferredSize(new java.awt.Dimension(700, 657));
        panelColumnaDerechaContainer.setLayout(new javax.swing.BoxLayout(panelColumnaDerechaContainer, javax.swing.BoxLayout.Y_AXIS));

        panelImagenPreviewContent.setBackground(new java.awt.Color(26, 26, 26));
        panelImagenPreviewContent.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        panelImagenPreviewContent.setMaximumSize(new java.awt.Dimension(9999, 9999));
        panelImagenPreviewContent.setPreferredSize(new java.awt.Dimension(700, 470));
        panelImagenPreviewContent.setLayout(new java.awt.BorderLayout());

        panelHeaderPreview.setBackground(new java.awt.Color(17, 17, 17));
        panelHeaderPreview.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(51, 51, 51)));
        panelHeaderPreview.setPreferredSize(new java.awt.Dimension(700, 26));

        labelHeaderPreview.setFont(new java.awt.Font("SansSerif", 1, 10)); // NOI18N
        labelHeaderPreview.setForeground(new java.awt.Color(160, 160, 160));
        labelHeaderPreview.setText("Imagen Preview");
        labelHeaderPreview.setToolTipText("");
        panelHeaderPreview.add(labelHeaderPreview);

        panelImagenPreviewContent.add(panelHeaderPreview, java.awt.BorderLayout.PAGE_START);

        panelImagenPreview.setBackground(new java.awt.Color(12, 12, 12));

        javax.swing.GroupLayout panelImagenPreviewLayout = new javax.swing.GroupLayout(panelImagenPreview);
        panelImagenPreview.setLayout(panelImagenPreviewLayout);
        panelImagenPreviewLayout.setHorizontalGroup(
            panelImagenPreviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 876, Short.MAX_VALUE)
        );
        panelImagenPreviewLayout.setVerticalGroup(
            panelImagenPreviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 454, Short.MAX_VALUE)
        );

        panelImagenPreviewContent.add(panelImagenPreview, java.awt.BorderLayout.CENTER);

        panelColumnaDerechaContainer.add(panelImagenPreviewContent);

        panelControlesMetadata.setBackground(new java.awt.Color(26, 26, 26));
        panelControlesMetadata.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        panelControlesMetadata.setMaximumSize(new java.awt.Dimension(9999, 175));
        panelControlesMetadata.setPreferredSize(new java.awt.Dimension(700, 175));
        panelControlesMetadata.setLayout(new java.awt.BorderLayout());

        panelHeaderControles.setBackground(new java.awt.Color(17, 17, 17));
        panelHeaderControles.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(51, 51, 51)));
        panelHeaderControles.setPreferredSize(new java.awt.Dimension(700, 26));

        labelHeaderControles.setFont(new java.awt.Font("SansSerif", 1, 10)); // NOI18N
        labelHeaderControles.setForeground(new java.awt.Color(160, 160, 160));
        labelHeaderControles.setText("CONTROLES Y METADATA");
        panelHeaderControles.add(labelHeaderControles);

        panelControlesMetadata.add(panelHeaderControles, java.awt.BorderLayout.PAGE_START);

        panelCentroControles.setBackground(new java.awt.Color(26, 26, 26));
        panelCentroControles.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelCentroControles.setLayout(new java.awt.GridLayout(1, 2, 20, 0));

        panelBotones.setBackground(new java.awt.Color(26, 26, 26));
        panelBotones.setLayout(new java.awt.GridLayout(3, 1, 8, 8));

        panelFilaSuperior.setBackground(new java.awt.Color(26, 26, 26));
        panelFilaSuperior.setLayout(new java.awt.GridLayout(1, 2, 5, 0));

        buttonUpScale.setBackground(new java.awt.Color(37, 37, 37));
        buttonUpScale.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        buttonUpScale.setForeground(new java.awt.Color(28, 28, 185));
        buttonUpScale.setText("Upscale");
        buttonUpScale.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(28, 28, 185)));
        buttonUpScale.setFocusable(false);
        buttonUpScale.setOpaque(true);
        buttonUpScale.addActionListener(this::buttonUpScaleActionPerformed);
        panelFilaSuperior.add(buttonUpScale);

        buttonNegative.setBackground(new java.awt.Color(37, 37, 37));
        buttonNegative.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        buttonNegative.setForeground(new java.awt.Color(255, 255, 255));
        buttonNegative.setText("Filtro Negativo");
        buttonNegative.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        buttonNegative.setFocusable(false);
        buttonNegative.setOpaque(true);
        buttonNegative.addActionListener(this::buttonNegativeActionPerformed);
        panelFilaSuperior.add(buttonNegative);

        panelBotones.add(panelFilaSuperior);

        panelFilaMedia.setBackground(new java.awt.Color(26, 26, 26));
        panelFilaMedia.setLayout(new java.awt.GridLayout(1, 2, 5, 0));

        buttonBinarize.setBackground(new java.awt.Color(37, 37, 37));
        buttonBinarize.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        buttonBinarize.setForeground(new java.awt.Color(255, 255, 255));
        buttonBinarize.setText("Binarizar");
        buttonBinarize.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        buttonBinarize.setFocusable(false);
        buttonBinarize.setOpaque(true);
        buttonBinarize.addActionListener(this::buttonBinarizeActionPerformed);
        panelFilaMedia.add(buttonBinarize);

        buttonGrises.setBackground(new java.awt.Color(37, 37, 37));
        buttonGrises.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        buttonGrises.setForeground(new java.awt.Color(255, 255, 255));
        buttonGrises.setText("Escala de Grises");
        buttonGrises.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        buttonGrises.setFocusable(false);
        buttonGrises.setOpaque(true);
        buttonGrises.addActionListener(this::buttonGrisesActionPerformed);
        panelFilaMedia.add(buttonGrises);

        panelBotones.add(panelFilaMedia);

        panelFilaInferior.setBackground(new java.awt.Color(26, 26, 26));
        panelFilaInferior.setLayout(new java.awt.GridLayout(1, 2, 5, 0));

        buttonResetImage.setBackground(new java.awt.Color(37, 37, 37));
        buttonResetImage.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        buttonResetImage.setForeground(new java.awt.Color(185, 28, 28));
        buttonResetImage.setText("Reiniciar");
        buttonResetImage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(185, 28, 28)));
        buttonResetImage.setFocusable(false);
        buttonResetImage.setOpaque(true);
        buttonResetImage.addActionListener(this::buttonResetImageActionPerformed);
        panelFilaInferior.add(buttonResetImage);

        buttonSaved.setBackground(new java.awt.Color(37, 37, 37));
        buttonSaved.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        buttonSaved.setForeground(new java.awt.Color(28, 185, 28));
        buttonSaved.setText("Guardar Imagen");
        buttonSaved.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(28, 185, 28)));
        buttonSaved.setFocusable(false);
        buttonSaved.setOpaque(true);
        buttonSaved.addActionListener(this::buttonSavedActionPerformed);
        panelFilaInferior.add(buttonSaved);

        panelBotones.add(panelFilaInferior);

        panelCentroControles.add(panelBotones);

        panelInfoMetadata.setBackground(new java.awt.Color(17, 17, 17));
        panelInfoMetadata.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(34, 34, 34)), javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5)));
        panelInfoMetadata.setLayout(new java.awt.BorderLayout());

        contentLabelKey.setBackground(new java.awt.Color(17, 17, 17));
        contentLabelKey.setMaximumSize(new java.awt.Dimension(120, 32767));
        contentLabelKey.setMinimumSize(new java.awt.Dimension(120, 100));
        contentLabelKey.setLayout(new java.awt.GridLayout(4, 1, 10, 5));

        labelKeyFile.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        labelKeyFile.setForeground(new java.awt.Color(185, 28, 28));
        labelKeyFile.setText("Archivo");
        contentLabelKey.add(labelKeyFile);

        labelKeyRes.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        labelKeyRes.setForeground(new java.awt.Color(185, 28, 28));
        labelKeyRes.setText("Resolución:");
        contentLabelKey.add(labelKeyRes);

        labelKeyFormat.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        labelKeyFormat.setForeground(new java.awt.Color(185, 28, 28));
        labelKeyFormat.setText("Formato:");
        contentLabelKey.add(labelKeyFormat);

        labelKeySource.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        labelKeySource.setForeground(new java.awt.Color(185, 28, 28));
        labelKeySource.setText("Dirección:");
        contentLabelKey.add(labelKeySource);

        panelInfoMetadata.add(contentLabelKey, java.awt.BorderLayout.WEST);

        contentLabelVal.setBackground(new java.awt.Color(17, 17, 17));
        contentLabelVal.setAlignmentX(1.0F);
        contentLabelVal.setLayout(new java.awt.GridLayout(4, 1, 10, 5));

        labelValFile.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        labelValFile.setForeground(new java.awt.Color(255, 255, 255));
        labelValFile.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelValFile.setText("---");
        contentLabelVal.add(labelValFile);

        labelValRes.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        labelValRes.setForeground(new java.awt.Color(255, 255, 255));
        labelValRes.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelValRes.setText("0 x 0 px");
        contentLabelVal.add(labelValRes);

        labelValFormat.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        labelValFormat.setForeground(new java.awt.Color(255, 255, 255));
        labelValFormat.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelValFormat.setText("---");
        contentLabelVal.add(labelValFormat);

        labelValSource.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        labelValSource.setForeground(new java.awt.Color(255, 255, 255));
        labelValSource.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelValSource.setText("Local");
        contentLabelVal.add(labelValSource);

        panelInfoMetadata.add(contentLabelVal, java.awt.BorderLayout.CENTER);

        panelCentroControles.add(panelInfoMetadata);

        panelControlesMetadata.add(panelCentroControles, java.awt.BorderLayout.CENTER);

        panelColumnaDerechaContainer.add(panelControlesMetadata);

        splitPaneMain.setRightComponent(panelColumnaDerechaContainer);

        mainPanel.add(splitPaneMain, java.awt.BorderLayout.CENTER);

        getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAbrirActionPerformed
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle("Seleccionar imagen");

        javax.swing.filechooser.FileNameExtensionFilter filtro
                = new javax.swing.filechooser.FileNameExtensionFilter(
                        "Imágenes (JPG, PNG, BMP, GIF)", "jpg", "jpeg", "png", "bmp", "gif"
                );
        fileChooser.setFileFilter(filtro);
        fileChooser.setAcceptAllFileFilterUsed(false);

        int resultado = fileChooser.showOpenDialog(this);
        if (resultado == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File archivoSeleccionado = fileChooser.getSelectedFile();

            try {
                imagenOriginal = javax.imageio.ImageIO.read(archivoSeleccionado);

                if (imagenOriginal == null) {
                    javax.swing.JOptionPane.showMessageDialog(this,
                            "No se pudo leer el archivo de imagen.",
                            "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                    return;
                }

                imagenFinal = null;
                limpiarComponenteVisual(panelImagenPreview);
                labelHeaderPreview.setText("Imagen Preview");

                javax.swing.ImageIcon icono = new javax.swing.ImageIcon(
                        imagenOriginal.getScaledInstance(
                                canvasImagenOriginal.getWidth(),
                                canvasImagenOriginal.getHeight(),
                                java.awt.Image.SCALE_SMOOTH
                        )
                );
                canvasImagenOriginal.removeAll();
                javax.swing.JLabel labelImagen = new javax.swing.JLabel(icono);
                labelImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                canvasImagenOriginal.add(labelImagen, java.awt.BorderLayout.CENTER);
                canvasImagenOriginal.revalidate();
                canvasImagenOriginal.repaint();

                calcularEstadisticas(imagenOriginal);
                llenarTablaFrecuencias();

                String nombreArchivo = archivoSeleccionado.getName();
                labelValFile.setText(nombreArchivo);

                labelValRes.setText(imagenOriginal.getWidth() + " x " + imagenOriginal.getHeight() + " px");

                String extension = nombreArchivo.contains(".")
                        ? nombreArchivo.substring(nombreArchivo.lastIndexOf('.') + 1).toUpperCase()
                        : "---";
                labelValFormat.setText(extension);

                String rutaCompleta = archivoSeleccionado.getAbsolutePath();
                labelValSource.setText(rutaCompleta);

                HistogramaRenderer.render(canvasHistogramaRojo, freqRojo, java.awt.Color.RED, "Rojo");
                HistogramaRenderer.render(canvasHistogramaVerde, freqVerde, java.awt.Color.GREEN, "Verde");
                HistogramaRenderer.render(canvasHistogramaAzul, freqAzul, java.awt.Color.BLUE, "Azul");
                HistogramaRenderer.render(canvasHistogramaLuma, freqLuma, java.awt.Color.GRAY, "Luma");

            } catch (java.io.IOException ex) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Error al cargar la imagen: " + ex.getMessage(),
                        "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_buttonAbrirActionPerformed

    private void buttonResetImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonResetImageActionPerformed
        imagenOriginal = null;
        imagenGrises = null;
        imagenFinal = null;

        freqRojo = new int[256];
        freqVerde = new int[256];
        freqAzul = new int[256];
        freqLuma = new int[256];
        totalPixeles = 0;

        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) tablaHistogramaData.getModel();
        modelo.setRowCount(0);

        labelValFile.setText("---");
        labelValRes.setText("0 x 0 px");
        labelValFormat.setText("---");
        labelValSource.setText("Local");
        labelHeaderPreview.setText("Imagen Preview");

        limpiarComponenteVisual(canvasImagenOriginal);
        limpiarComponenteVisual(panelImagenPreview);

        limpiarComponenteVisual(canvasHistogramaRojo);
        limpiarComponenteVisual(canvasHistogramaVerde);
        limpiarComponenteVisual(canvasHistogramaAzul);
        limpiarComponenteVisual(canvasHistogramaLuma);
    }//GEN-LAST:event_buttonResetImageActionPerformed

    private void limpiarComponenteVisual(javax.swing.JPanel panel) {
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }

    private void buttonNegativeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNegativeActionPerformed
        if (imagenOriginal == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Primero debes abrir una imagen.");
            return;
        }

        int ancho = imagenOriginal.getWidth();
        int alto = imagenOriginal.getHeight();
        java.awt.image.BufferedImage imagenNegativa = new java.awt.image.BufferedImage(
                ancho, alto, java.awt.image.BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                int rgb = imagenOriginal.getRGB(x, y);

                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                int rNegativo = 255 - r;
                int gNegativo = 255 - g;
                int bNegativo = 255 - b;

                int rgbNegativo = (rNegativo << 16) | (gNegativo << 8) | bNegativo;
                imagenNegativa.setRGB(x, y, rgbNegativo);
            }
        }

        imagenFinal = imagenNegativa;
        mostrarEnPreview();

        labelHeaderPreview.setText("Filtro Negativo");
    }//GEN-LAST:event_buttonNegativeActionPerformed

    private void buttonGrisesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonGrisesActionPerformed
        if (imagenGrises == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Primero debes abrir una imagen.");
            return;
        }

        imagenFinal = imagenGrises;

        mostrarEnPreview();

        labelHeaderPreview.setText("Escala de Grises (Luma)");
    }//GEN-LAST:event_buttonGrisesActionPerformed

    private void buttonBinarizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBinarizeActionPerformed
        if (imagenGrises == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Primero debes abrir una imagen.");
            return;
        }

        int min = 0;
        for (int i = 0; i < 256; i++) {
            if (freqLuma[i] > 0) {
                min = i;
                break;
            }
        }

        int max = 255;
        for (int i = 255; i >= 0; i--) {
            if (freqLuma[i] > 0) {
                max = i;
                break;
            }
        }

        int umbral = (min + max) / 2;

        int ancho = imagenGrises.getWidth();
        int alto = imagenGrises.getHeight();
        java.awt.image.BufferedImage imagenBinarizada = new java.awt.image.BufferedImage(
                ancho, alto, java.awt.image.BufferedImage.TYPE_BYTE_GRAY);

        for (int y = 0; y < alto; y++) {
            for (int x = 0; x < ancho; x++) {
                int nivelGris = imagenGrises.getRaster().getSample(x, y, 0);
                int binario = (nivelGris > umbral) ? 255 : 0;
                imagenBinarizada.getRaster().setSample(x, y, 0, binario);
            }
        }

        imagenFinal = imagenBinarizada;
        mostrarEnPreview();

        labelHeaderPreview.setText("Binarización (Umbral: " + umbral + " | Min: " + min + ", Max: " + max + ")");
    }//GEN-LAST:event_buttonBinarizeActionPerformed

    private void buttonSavedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSavedActionPerformed
        if (imagenFinal == null) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "No hay ninguna imagen procesada para guardar.",
                    "Advertencia", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String userHome = System.getProperty("user.home");

            java.io.File folder = new java.io.File(userHome + java.io.File.separator + "Pictures" + java.io.File.separator + "PDI");

            if (!folder.exists()) {
                if (folder.mkdirs()) {
                    System.out.println("Carpeta creada: " + folder.getAbsolutePath());
                }
            }

            // 4. Generar un nombre único basado en la fecha y hora para no sobrescribir archivos
            String timeStamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
            String nombreArchivo = "PDI_" + timeStamp + ".png";
            java.io.File archivoSalida = new java.io.File(folder, nombreArchivo);

            boolean exito = javax.imageio.ImageIO.write(imagenFinal, "png", archivoSalida);

            if (exito) {
                // Definimos los botones personalizados que aparecerán en el Dialog
                Object[] opciones = {"Aceptar", "Abrir ubicación"};

                // Mostramos el dialog con las opciones
                int seleccion = javax.swing.JOptionPane.showOptionDialog(this,
                        "Imagen guardada con éxito en:\n" + archivoSalida.getAbsolutePath(),
                        "Guardado Exitoso",
                        javax.swing.JOptionPane.YES_NO_OPTION,
                        javax.swing.JOptionPane.INFORMATION_MESSAGE,
                        null, // No usamos un icono personalizado
                        opciones, // Array de botones
                        opciones[0] // Botón seleccionado por defecto
                );

                if (seleccion == 1) {
                    try {
                        if (java.awt.Desktop.isDesktopSupported()) {
                            java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
                            desktop.open(folder);
                        } else {
                            javax.swing.JOptionPane.showMessageDialog(this,
                                    "La función de abrir el directorio no es compatible con este sistema.",
                                    "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
                        }
                    } catch (java.io.IOException e) {
                        javax.swing.JOptionPane.showMessageDialog(this,
                                "No se pudo abrir la carpeta: " + e.getMessage(),
                                "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                throw new java.io.IOException("Error interno al escribir el archivo.");
            }

        } catch (java.io.IOException ex) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Error al guardar la imagen: " + ex.getMessage(),
                    "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_buttonSavedActionPerformed

    private void buttonUpScaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonUpScaleActionPerformed
        if (imagenOriginal == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Primero debes abrir una imagen.");
            return;
        }

        if (!Waifu2XProcessor.isWaifu2XAvailable()) {
            // Si es Linux y el archivo existe pero no tiene permisos
            if (Waifu2XProcessor.getOperatingSystem().equals("linux") && !Waifu2XProcessor.checkLinuxPermissions()) {
                int opcion = javax.swing.JOptionPane.showConfirmDialog(this,
                        "Waifu2X no tiene permisos de ejecución en Linux.\n\n"
                        + "¿Deseas que intente asignar los permisos automáticamente?\n"
                        + "Si no funciona, deberás ejecutar manualmente:\n"
                        + "chmod +x bin/linux/waifu2x-ncnn-vulkan",
                        "Permisos Insuficientes",
                        javax.swing.JOptionPane.YES_NO_OPTION,
                        javax.swing.JOptionPane.WARNING_MESSAGE);

                if (opcion == javax.swing.JOptionPane.YES_OPTION) {
                    Waifu2XProcessor.fixLinuxPermissions();
                    if (!Waifu2XProcessor.isWaifu2XAvailable()) {
                        javax.swing.JOptionPane.showMessageDialog(this,
                                Waifu2XProcessor.getWaifu2XErrorMessage(),
                                "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                    }
                    return;
                } else {
                    return;
                }
            } else {
                javax.swing.JOptionPane.showMessageDialog(this,
                        Waifu2XProcessor.getWaifu2XErrorMessage(),
                        "Waifu2X no disponible", javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        Waifu2XSettings settingsDialog = new Waifu2XSettings(this, true);
        settingsDialog.setLocationRelativeTo(this);
        settingsDialog.setVisible(true);

        if (!settingsDialog.wasUpscaleClicked()) {
            return;
        }

        int noiseLevel = settingsDialog.getNoiseLevel();
        int scale = settingsDialog.getScale();
        boolean useTTA = settingsDialog.isTTAEnabled();

        Waifu2XProgressDialog progressDialog = new Waifu2XProgressDialog(this, true);
        progressDialog.setLocationRelativeTo(this);

        new java.lang.Thread(() -> {
            try {
                java.awt.image.BufferedImage imagenProcesada = Waifu2XProcessor.upscaleImage(
                        imagenOriginal,
                        noiseLevel,
                        scale,
                        useTTA
                );

                progressDialog.close();

                if (imagenProcesada != null) {
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        imagenFinal = imagenProcesada;
                        mostrarEnPreview();

                        labelHeaderPreview.setText(
                                String.format("Waifu2X Upscale %dx (Original: %dx%d → Final: %dx%d)",
                                        scale,
                                        imagenOriginal.getWidth(), imagenOriginal.getHeight(),
                                        imagenProcesada.getWidth(), imagenProcesada.getHeight())
                        );

                        javax.swing.JOptionPane.showMessageDialog(mainFrame.this,
                                "Imagen escalada exitosamente.\n\n"
                                + "Tamaño original: " + imagenOriginal.getWidth() + "x" + imagenOriginal.getHeight() + " px\n"
                                + "Tamaño final: " + imagenProcesada.getWidth() + "x" + imagenProcesada.getHeight() + " px",
                                "Éxito", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    });
                } else {
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        javax.swing.JOptionPane.showMessageDialog(mainFrame.this,
                                "Error al procesar la imagen con Waifu2X.\n\nRevisa la consola para más detalles.",
                                "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                    });
                }
            } catch (Exception ex) {
                progressDialog.close();
                javax.swing.SwingUtilities.invokeLater(() -> {
                    javax.swing.JOptionPane.showMessageDialog(mainFrame.this,
                            "Error durante el procesamiento: " + ex.getMessage(),
                            "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                });
            }
        }).start();

        progressDialog.setVisible(true);
    }//GEN-LAST:event_buttonUpScaleActionPerformed

    private void buttonInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonInfoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonInfoActionPerformed

    private void buttonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_buttonSalirActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new mainFrame().setVisible(true));
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonAbrir;
    private javax.swing.JButton buttonBinarize;
    private javax.swing.JButton buttonGrises;
    private javax.swing.JButton buttonInfo;
    private javax.swing.JButton buttonNegative;
    private javax.swing.JButton buttonResetImage;
    private javax.swing.JButton buttonSalir;
    private javax.swing.JButton buttonSaved;
    private javax.swing.JButton buttonUpScale;
    private javax.swing.JPanel canvasHistogramaAzul;
    private javax.swing.JPanel canvasHistogramaLuma;
    private javax.swing.JPanel canvasHistogramaRojo;
    private javax.swing.JPanel canvasHistogramaVerde;
    private javax.swing.JPanel canvasImagenOriginal;
    private javax.swing.JPanel contentLabelKey;
    private javax.swing.JPanel contentLabelVal;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel labelHeaderControles;
    private javax.swing.JLabel labelHeaderOriginal;
    private javax.swing.JLabel labelHeaderPreview;
    private javax.swing.JLabel labelKeyFile;
    private javax.swing.JLabel labelKeyFormat;
    private javax.swing.JLabel labelKeyRes;
    private javax.swing.JLabel labelKeySource;
    private javax.swing.JLabel labelTitulo;
    private javax.swing.JLabel labelValFile;
    private javax.swing.JLabel labelValFormat;
    private javax.swing.JLabel labelValRes;
    private javax.swing.JLabel labelValSource;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel menuTopPanel;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JPanel panelCentroControles;
    private javax.swing.JPanel panelColumnaDerechaContainer;
    private javax.swing.JPanel panelColumnaIzquierda;
    private javax.swing.JPanel panelControlesMetadata;
    private javax.swing.JPanel panelFilaInferior;
    private javax.swing.JPanel panelFilaMedia;
    private javax.swing.JPanel panelFilaSuperior;
    private javax.swing.JPanel panelHeaderControles;
    private javax.swing.JPanel panelHeaderOriginal;
    private javax.swing.JPanel panelHeaderPreview;
    private javax.swing.JPanel panelImagenOriginal;
    private javax.swing.JPanel panelImagenPreview;
    private javax.swing.JPanel panelImagenPreviewContent;
    private javax.swing.JPanel panelInfoMetadata;
    private javax.swing.JScrollPane scrollTablaData;
    private javax.swing.JSplitPane splitPaneMain;
    private javax.swing.JPanel tabPanelAzul;
    private javax.swing.JPanel tabPanelData;
    private javax.swing.JPanel tabPanelLuma;
    private javax.swing.JPanel tabPanelRojo;
    private javax.swing.JPanel tabPanelVerde;
    private javax.swing.JTabbedPane tabbedPaneHistogramas;
    private javax.swing.JTable tablaHistogramaData;
    // End of variables declaration//GEN-END:variables
}
