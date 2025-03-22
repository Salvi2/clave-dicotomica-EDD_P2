package clavedicotomica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Clase que proporciona una representación gráfica del árbol de la clave dicotómica.
 * Permite visualizar el árbol en una ventana con funcionalidades de zoom y desplazamiento.
 */
public class GraficoArbol {
    
    private static final int ANCHO_NODO = 120;
    private static final int ALTO_NODO = 50;
    private static final int ESPACIO_HORIZONTAL = 350;
    private static final int ESPACIO_VERTICAL = 80;
    private static final Color COLOR_NODO = new Color(100, 149, 237); // Azul acero claro
    private static final Color COLOR_NODO_ESPECIE = new Color(50, 205, 50); // Verde lima
    private static final Color COLOR_LINEA_SI = new Color(34, 139, 34); // Verde bosque
    private static final Color COLOR_LINEA_NO = new Color(178, 34, 34); // Rojo ladrillo
    
    /**
     * Muestra el árbol de la clave dicotómica en una ventana gráfica.
     *
     * @param raiz La raíz del árbol que se desea visualizar.
     */
    public static void mostrarArbol(Nodo raiz) {
        // Verificar si el árbol está vacío
        if (raiz == null) {
            JOptionPane.showMessageDialog(null, 
                "El árbol está vacío. No hay nada que mostrar.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear una nueva ventana
        JFrame frame = new JFrame("Visualizador de Clave Dicotómica");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 700);
        
        // Calcular profundidad del árbol para dimensionar el panel
        int profundidad = calcularProfundidad(raiz);
        int anchura = calcularAnchuraMaxima(raiz);
        
        // Crear un panel para dibujar el árbol
        PanelArbol panel = new PanelArbol(raiz, profundidad, anchura);
        
        // Agregar el panel a un ScrollPane para permitir desplazamiento
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(950, 650));
        
        // Agregar botones de zoom
        JButton zoomInButton = new JButton("Zoom +");
        JButton zoomOutButton = new JButton("Zoom -");
        JButton resetButton = new JButton("Reset Zoom");
        
        zoomInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.zoomIn();
            }
        });
        
        zoomOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.zoomOut();
            }
        });
        
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.resetZoom();
            }
        });
        
        // Panel para los botones
        JPanel botonesPanel = new JPanel();
        botonesPanel.add(zoomInButton);
        botonesPanel.add(zoomOutButton);
        botonesPanel.add(resetButton);
        
        // Agregar elementos al frame
        frame.setLayout(new BorderLayout());
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(botonesPanel, BorderLayout.SOUTH);
        
        // Centrar la ventana y hacerla visible
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    /**
     * Calcula la profundidad máxima del árbol.
     *
     * @param nodo El nodo desde el cual se calcula la profundidad.
     */
    private static int calcularProfundidad(Nodo nodo) {
        if (nodo == null) {
            return 0;
        }
        return 1 + Math.max(calcularProfundidad(nodo.getSi()), calcularProfundidad(nodo.getNo()));
    }
    
    /**
     * Calcula la anchura máxima del árbol (cantidad máxima de nodos en un nivel).
     *
     * @param raiz La raíz del árbol.
     * @return La anchura máxima del árbol.
     */
    private static int calcularAnchuraMaxima(Nodo raiz) {
        int[] anchuras = new int[50]; // Suponemos un máximo de 50 niveles
        calcularAnchurasPorNivel(raiz, 0, anchuras);
        
        int maxAnchura = 0;
        for (int anchura : anchuras) {
            if (anchura > maxAnchura) {
                maxAnchura = anchura;
            }
        }
        return maxAnchura;
    }
    
    /**
     * Calcula la cantidad de nodos por nivel.
     *
     * @param nodo El nodo actual.
     * @param nivel El nivel actual en el árbol.
     * @param anchuras Un arreglo que almacena la cantidad de nodos por nivel.
     */
    private static void calcularAnchurasPorNivel(Nodo nodo, int nivel, int[] anchuras) {
        if (nodo == null) {
            return;
        }
        
        anchuras[nivel]++;
        
        calcularAnchurasPorNivel(nodo.getSi(), nivel + 1, anchuras);
        calcularAnchurasPorNivel(nodo.getNo(), nivel + 1, anchuras);
    }
    
    /**
     * Panel interno para dibujar el árbol.
     */
    private static class PanelArbol extends JPanel {
        private Nodo raiz;
        private int profundidad;
        private int anchura;
        private double escala = 1.0;
        private int desplazamientoX = 0;
        private int desplazamientoY = 0;
        private Point puntoArrastre;
        
        /**
         * Constructor del panel que dibuja el árbol.
         *
         * @param raiz La raíz del árbol.
         * @param profundidad La profundidad del árbol.
         * @param anchura La anchura máxima del árbol.
         */
        public PanelArbol(Nodo raiz, int profundidad, int anchura) {
            this.raiz = raiz;
            this.profundidad = profundidad;
            this.anchura = anchura;
            
            // Calcular dimensiones del panel
            int anchoPanel = Math.max(1000, anchura * (ANCHO_NODO + ESPACIO_HORIZONTAL));
            int altoPanel = Math.max(700, profundidad * (ALTO_NODO + ESPACIO_VERTICAL) + 100);
            setPreferredSize(new Dimension(anchoPanel, altoPanel));
            
            // Agregar listeners para permitir arrastrar el árbol
            MouseAdapter mouseAdapter = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    puntoArrastre = e.getPoint();
                }
                
                @Override
                public void mouseReleased(MouseEvent e) {
                    puntoArrastre = null;
                }
                
                @Override
                public void mouseDragged(MouseEvent e) {
                    if (puntoArrastre != null) {
                        int dx = e.getX() - puntoArrastre.x;
                        int dy = e.getY() - puntoArrastre.y;
                        desplazamientoX += dx;
                        desplazamientoY += dy;
                        puntoArrastre = e.getPoint();
                        repaint();
                    }
                }
            };
            
            addMouseListener(mouseAdapter);
            addMouseMotionListener(mouseAdapter);
        }
        
        /**
         * Aumenta el zoom del árbol.
         */
        public void zoomIn() {
            escala *= 1.1;
            repaint();
        }
        
        /**
         * Disminuye el zoom del árbol.
         */
        public void zoomOut() {
            escala /= 1.1;
            repaint();
        }
        
        /**
         * Restablece el zoom y el desplazamiento del árbol.
         */
        public void resetZoom() {
            escala = 1.0;
            desplazamientoX = 0;
            desplazamientoY = 0;
            repaint();
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            
            // Activar antialiasing para mejorar la calidad visual
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Aplicar escala y desplazamiento
            g2d.translate(desplazamientoX, desplazamientoY);
            g2d.scale(escala, escala);
            
            // Dibujar el árbol
            if (raiz != null) {
                int x = getWidth() / 2;
                int y = 50;
                dibujarNodo(g2d, raiz, x, y, getWidth() / 4);
            }
        }
        
        /**
         * Dibuja un nodo del árbol y sus hijos recursivamente.
         */
        private void dibujarNodo(Graphics2D g2d, Nodo nodo, int x, int y, int desplazamiento) {
            if (nodo == null) return;
            
            // Determinar color del nodo
            Color colorNodo = nodo.getEspecie() != null ? COLOR_NODO_ESPECIE : COLOR_NODO;
            
            // Dibujar el nodo
            g2d.setColor(colorNodo);
            g2d.fillRoundRect(x - ANCHO_NODO / 2, y, ANCHO_NODO, ALTO_NODO, 20, 20);
            g2d.setColor(Color.BLACK);
            g2d.drawRoundRect(x - ANCHO_NODO / 2, y, ANCHO_NODO, ALTO_NODO, 20, 20);
            
            // Dibujar el texto del nodo
            g2d.setFont(new Font("Arial", Font.BOLD, 12));
            String texto = nodo.getEspecie() != null ? nodo.getEspecie() : nodo.getPregunta();
            dibujarTextoMultilinea(g2d, texto, x, y + 20);
            
            // Dibujar líneas y nodos hijos
            int siguienteY = y + ALTO_NODO + ESPACIO_VERTICAL;
            
            // Dibujar hijo SI
            if (nodo.getSi() != null) {
                int hijoX = x - desplazamiento;
                
                // Dibujar línea
                g2d.setColor(COLOR_LINEA_SI);
                g2d.drawLine(x, y + ALTO_NODO, hijoX, siguienteY);
                
                // Dibujar etiqueta "Sí"
                g2d.setFont(new Font("Arial", Font.BOLD, 14));
                g2d.drawString("Sí", (x + hijoX) / 2 - 10, y + ALTO_NODO + (ESPACIO_VERTICAL / 2));
                
                // Dibujar nodo hijo
                dibujarNodo(g2d, nodo.getSi(), hijoX, siguienteY, desplazamiento / 2);
            }
            
            // Dibujar hijo NO
            if (nodo.getNo() != null) {
                int hijoX = x + desplazamiento;
                
                // Dibujar línea
                g2d.setColor(COLOR_LINEA_NO);
                g2d.drawLine(x, y + ALTO_NODO, hijoX, siguienteY);
                
                // Dibujar etiqueta "No"
                g2d.setFont(new Font("Arial", Font.BOLD, 14));
                g2d.drawString("No", (x + hijoX) / 2 - 10, y + ALTO_NODO + (ESPACIO_VERTICAL / 2));
                
                // Dibujar nodo hijo
                dibujarNodo(g2d, nodo.getNo(), hijoX, siguienteY, desplazamiento / 2);
            }
        }
        
        /**
         * Dibuja texto multilínea dentro de un nodo.
         */
        private void dibujarTextoMultilinea(Graphics2D g2d, String texto, int x, int y) {
            // Dividir el texto en líneas si es muy largo
            FontMetrics fm = g2d.getFontMetrics();
            int anchoMax = ANCHO_NODO - 10;
            
            if (texto == null) {
                return;
            }
            
            // Acortar el texto si es demasiado largo
            if (texto.length() > 70) {
                texto = texto.substring(0, 67) + "...";
            }
            
            // Dividir el texto en líneas
            String[] palabras = texto.split(" ");
            String lineaActual = "";
            int lineaY = y;
            
            for (String palabra : palabras) {
                if (fm.stringWidth(lineaActual + palabra) < anchoMax) {
                    lineaActual += palabra + " ";
                } else {
                    g2d.drawString(lineaActual, x - fm.stringWidth(lineaActual) / 2, lineaY);
                    lineaActual = palabra + " ";
                    lineaY += fm.getHeight();
                }
            }
            
            // Dibujar la última línea
            if (!lineaActual.isEmpty()) {
                g2d.drawString(lineaActual, x - fm.stringWidth(lineaActual) / 2, lineaY);
            }
        }
    }
}