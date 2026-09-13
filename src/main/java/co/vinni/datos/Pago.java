package co.vinni.datos;

public class Pago {
    private String placa;
    private double valor;
    private String medioPago; // Efectivo, Nequi, etc.

    public Pago() {
    }

    public String obtenerPlaca() {
        return placa;
    }

    public void modificarPlaca(String placa) {
        this.placa = placa;
    }

    public double obtenerValor() {
        return valor;
    }

    public void modificarValor(double valor) {
        this.valor = valor;
    }

    public String obtenerMedioPago() {
        return medioPago;
    }

    public void modificarMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }
}