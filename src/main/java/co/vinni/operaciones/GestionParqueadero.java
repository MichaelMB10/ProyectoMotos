package co.vinni.operaciones;

import co.vinni.datos.Moto;
import co.vinni.datos.Pago;

import java.util.ArrayList;
import java.util.List;

public class GestionParqueadero {

    private static final int CAPACIDAD_MAXIMA = 23;
    private static final double TARIFA_MINUTO = 40.0;

    private List<Moto> motosIngresadas;
    private List<Pago> pagosRealizados;

    public GestionParqueadero() {
        this.motosIngresadas = new ArrayList<>();
        this.pagosRealizados = new ArrayList<>();
    }

    public Moto registrarIngreso(String placa, String marca, String cedula) {
        if (placa == null || marca == null || cedula == null) {
            return null;
        }
        if (motosIngresadas.size() >= CAPACIDAD_MAXIMA) {
            return null; // Capacidad superada
        }

        Moto nuevaMoto = new Moto();
        nuevaMoto.modificarPlaca(placa);
        nuevaMoto.modificarMarca(marca);
        nuevaMoto.modificarCedulaPropietario(cedula);

        motosIngresadas.add(nuevaMoto);
        return nuevaMoto;
    }

    public double calcularCobroSalida(String placa, int minutos) {
        if (placa == null || minutos <= 0) {
            return 0.0;
        }
        return minutos * TARIFA_MINUTO;
    }

    public boolean registrarPago(String placa, double valor, String medioPago) {
        if (placa == null || valor <= 0 || medioPago == null) {
            return false;
        }
        Pago nuevoPago = new Pago();
        nuevoPago.modificarPlaca(placa);
        nuevoPago.modificarValor(valor);
        nuevoPago.modificarMedioPago(medioPago);

        pagosRealizados.add(nuevoPago);
        return true;
    }

    public int obtenerCantidadMotosAtendidas() {
        return pagosRealizados.size();
    }

    public double obtenerTotalDineroRecaudado() {
        double total = 0.0;
        for (Pago p : pagosRealizados) {
            total += p.obtenerValor();
        }
        return total;
    }

    public List<Moto> obtenerMotosIngresadas() {
        return motosIngresadas;
    }
}