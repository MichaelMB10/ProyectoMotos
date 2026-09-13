package co.vinni.vista;

import co.vinni.datos.Moto;
import co.vinni.operaciones.GestionParqueadero;

import javax.swing.*;
import java.awt.*;

public class VentanaParqueadero extends JFrame {

    private GestionParqueadero gestion;

    // Componentes para Ingreso
    private JTextField txtPlacaIngreso, txtMarca, txtCedula;
    // Componentes para Cobro/Pago
    private JTextField txtPlacaSalida, txtMinutos, txtValorPago;
    private JComboBox<String> cbMedioPago;
    // Área de salida de texto
    private JTextArea areaConsola;

    public VentanaParqueadero() {
        this.gestion = new GestionParqueadero();

        setTitle("Gestión de Parqueadero de Motos");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel Superior: Formulario de Registro e Ingreso
        JPanel panelIngreso = new JPanel(new GridLayout(4, 2, 5, 5));
        panelIngreso.setBorder(BorderFactory.createTitledBorder("1. Registrar Ingreso de Moto"));

        panelIngreso.add(new JLabel(" Placa:"));
        txtPlacaIngreso = new JTextField();
        panelIngreso.add(txtPlacaIngreso);

        panelIngreso.add(new JLabel(" Marca:"));
        txtMarca = new JTextField();
        panelIngreso.add(txtMarca);

        panelIngreso.add(new JLabel(" Cédula Propietario:"));
        txtCedula = new JTextField();
        panelIngreso.add(txtCedula);

        JButton btnIngresar = new JButton("Registrar Ingreso");
        btnIngresar.addActionListener(e -> registrarIngreso());
        panelIngreso.add(new JLabel()); // Espacio en blanco
        panelIngreso.add(btnIngresar);

        // Panel Central: Formulario de Cobro y Pago
        JPanel panelPago = new JPanel(new GridLayout(4, 2, 5, 5));
        panelPago.setBorder(BorderFactory.createTitledBorder("2. Cobro y Registro de Pago"));

        panelPago.add(new JLabel(" Placa Moto:"));
        txtPlacaSalida = new JTextField();
        panelPago.add(txtPlacaSalida);

        panelPago.add(new JLabel(" Minutos Estacionado:"));
        txtMinutos = new JTextField();
        panelPago.add(txtMinutos);

        panelPago.add(new JLabel(" Medio de Pago:"));
        cbMedioPago = new JComboBox<>(new String[]{"Efectivo", "Nequi", "Tarjeta"});
        panelPago.add(cbMedioPago);

        JButton btnCobrar = new JButton("Calcular y Registrar Pago");
        btnCobrar.addActionListener(e -> procesarPago());
        panelPago.add(new JLabel());
        panelPago.add(btnCobrar);

        // Panel Combinado (Ingreso + Pago)
        JPanel panelSuperior = new JPanel(new GridLayout(1, 2, 10, 10));
        panelSuperior.add(panelIngreso);
        panelSuperior.add(panelPago);

        // Panel Inferior: Consola de Reporte y Salida
        areaConsola = new JTextArea();
        areaConsola.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaConsola);
        scroll.setBorder(BorderFactory.createTitledBorder("Consola de Operaciones / Reporte Diario"));

        // Botón de Reporte Diario
        JButton btnReporte = new JButton("Ver Reporte Diario de Recaudo");
        btnReporte.addActionListener(e -> mostrarReporte());

        JPanel panelContenedorInferior = new JPanel(new BorderLayout());
        panelContenedorInferior.add(btnReporte, BorderLayout.NORTH);
        panelContenedorInferior.add(scroll, BorderLayout.CENTER);

        add(panelSuperior, BorderLayout.NORTH);
        add(panelContenedorInferior, BorderLayout.CENTER);
    }

    private void registrarIngreso() {
        String placa = txtPlacaIngreso.getText().trim();
        String marca = txtMarca.getText().trim();
        String cedula = txtCedula.getText().trim();

        Moto m = gestion.registrarIngreso(placa, marca, cedula);
        if (m != null) {
            areaConsola.append(" [INGRESO EXITOSO] Moto " + placa + " (" + marca + ") registrada.\n");
            txtPlacaIngreso.setText("");
            txtMarca.setText("");
            txtCedula.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo registrar la moto (Verifique datos o capacidad).", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void procesarPago() {
        try {
            String placa = txtPlacaSalida.getText().trim();
            int minutos = Integer.parseInt(txtMinutos.getText().trim());
            String medio = (String) cbMedioPago.getSelectedItem();

            double total = gestion.calcularCobroSalida(placa, minutos);
            if (total > 0) {
                boolean exito = gestion.registrarPago(placa, total, medio);
                if (exito) {
                    areaConsola.append(" [PAGO REGISTRADO] Placa: " + placa + " | Minutos: " + minutos + " | Total: $" + total + " (" + medio + ")\n");
                    txtPlacaSalida.setText("");
                    txtMinutos.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Datos de cobro inválidos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese un número válido en los minutos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarReporte() {
        areaConsola.append("\n=== REPORTE GENERAL DEL PARQUEADERO ===\n");
        areaConsola.append(" Total Motos Atendidas: " + gestion.obtenerCantidadMotosAtendidas() + "\n");
        areaConsola.append(" Total Recaudado: $" + gestion.obtenerTotalDineroRecaudado() + "\n");
        areaConsola.append("=======================================\n\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaParqueadero().setVisible(true);
        });
    }
}