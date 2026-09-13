package co.vinni.datos;

public class Moto {
    private String placa;
    private String marca;
    private String cedulaPropietario;

    public Moto() {
    }

    public String obtenerPlaca() {
        return placa;
    }

    public void modificarPlaca(String placa) {
        this.placa = placa;
    }

    public String obtenerMarca() {
        return marca;
    }

    public void modificarMarca(String marca) {
        this.marca = marca;
    }

    public String obtenerCedulaPropietario() {
        return cedulaPropietario;
    }

    public void modificarCedulaPropietario(String cedulaPropietario) {
        this.cedulaPropietario = cedulaPropietario;
    }
}