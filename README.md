# PDI - Procesamiento de Imágenes con Integración Waifu2X

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Apache_Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![Vulkan](https://img.shields.io/badge/Vulkan-E65226?style=for-the-badge&logo=vulkan&logoColor=white)

Es una aplicación de escritorio desarrollada en Java orientada al **Procesamiento Digital de Imágenes (PDI)**. El software combina herramientas interactivas de análisis visual, como renderizado de histogramas en tiempo real, junto con capacidades avanzadas de super-resolución y reducción de ruido mediante el escalador **waifu2x** optimizado para la API Vulkan (vía `waifu2x-ncnn-vulkan`).

---

## 🚀 Características Principales

- **Renderizado de Histogramas:** Análisis detallado de la distribución de frecuencias de color (`HistogramaRenderer.java`).
- **Super-Resolución con IA:** Escalado y filtrado de ruido en imágenes utilizando modelos neuronales avanzados (`models-cunet`, `upconv_7`).
- **Procesamiento Multiplataforma Híbrido:** Detección automática del sistema operativo para mandar a llamar los binarios nativos optimizados correspondientes (`bin/windows` o `bin/linux`).
- **Interfaz Gráfica Intuitiva:** Flujo de trabajo cómodo construido con componentes Swing y ventanas de progreso detalladas.

---

## 🛠️ Requisitos del Sistema

Al delegar el procesamiento pesado a través de la arquitectura Vulkan, se requiere:
- **Java JDK 17** o superior.
- **Gestor de dependencias:** Apache Maven.
- **GPU compatible con Vulkan** (con controladores gráficos actualizados tanto en Windows como en Linux) para la ejecución del módulo de Inteligencia Artificial.

---

## 🔧 Instalación y Ejecución

1. **Clonar el repositorio:**
   ```bash
   git clone [https://github.com/X4VIER19/PDI_Procesamiento_de_Imagenes.git](https://github.com/X4VIER19/PDI_Procesamiento_de_Imagenes.git)
   cd PDI_Procesamiento_de_Imagenes

---

## 📁 Estructura del Proyecto

```text
PDI_Procesamiento_de_Imagenes/
│
├── bin/                             # Binarios nativos de terceros (Portables)
│   ├── linux/                       # Ejecutables y dependencias para Linux
│   │   ├── waifu2x-ncnn-vulkan      # Binario de Waifu2X para Linux
│   │   └── models-.../              # Modelos neuronales (cunet, upconv_7)
│   │
│   └── windows/                     # Ejecutables y dependencias para Windows
│       ├── waifu2x-ncnn-vulkan.exe  # Binario de Waifu2X para Windows
│       └── models-.../              # Modelos neuronales (.bin y .param)
│
├── src/main/java/com/mycompany/PDI/ # Código fuente principal en Java
│   │
│   ├── Frames/                      # Interfaces gráficas de usuario (Swing UI)
│   │   ├── frameBienvenida.java     # Pantalla de inicio de la aplicación
│   │   ├── mainFrame.java           # Ventana principal de procesamiento
│   │   ├── Waifu2XProgressDialog.java # Control visual del progreso del escalado
│   │   └── Waifu2XSettings.java     # Configuración de parámetros de la IA
│   │
│   ├── icons/                       # Recursos visuales del sistema
│   │   └── informacion.png
│   │
│   ├── HistogramaRenderer.java      # Lógica para el cálculo y dibujado de histogramas
│   ├── PDI_Procesamiento_de_Imagenes.java # Clase principal y punto de entrada (Main)
│   └── Waifu2XProcessor.java        # Orquestador encargado de invocar los binarios nativos
│
├── .gitignore                       # Archivos y carpetas excluidos de Git (ej. target/)
├── pom.xml                          # Configuración del proyecto y dependencias de Maven
└── nbactions.xml                    # Acciones personalizadas de NetBeans (Opcional)
