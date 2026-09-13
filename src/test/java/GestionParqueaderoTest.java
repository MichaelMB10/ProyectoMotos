package co.vinni.operaciones;

import co.vinni.datos.Moto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GestionParqueaderoTest {

    private GestionParqueadero gestion;

    @BeforeEach
    public void setUp() {
        gestion = new GestionParqueadero();
    }

    @Test
    public void registrarIngreso_Valido_RetornaMoto() {
        Moto m = gestion.registrarIngreso("XYZ123", "Yamaha", "1018223344");
        assertNotNull(m);
        assertEquals("XYZ123", m.obtenerPlaca());
    }

    @Test
    public void registrarIngreso_InvalidoPlacaNula_RetornaNull() {
        Moto m = gestion.registrarIngreso(null, "Yamaha", "1018223344");
        assertNull(m);
    }

    @Test
    public void registrarIngreso_InvalidoMarcaNula_RetornaNull() {
        Moto m = gestion.registrarIngreso("XYZ123", null, "1018223344");
        assertNull(m);
    }

    @Test
    public void registrarIngreso_InvalidoCapacidadExcedida_RetornaNull() {
        for (int i = 0; i < 23; i++) {
            gestion.registrarIngreso("MOTO" + i, "Marca", "12345");
        }
        Moto mExcedida = gestion.registrarIngreso("MOTO24", "Yamaha", "12345");
        assertNull(mExcedida);
    }

    @Test
    public void calcularCobro_Valido_RetornaValorCorrecto() {
        double total = gestion.calcularCobroSalida("XYZ123", 10);
        assertEquals(400.0, total);
    }

    @Test
    public void calcularCobro_InvalidoPlacaNula_RetornaCero() {
        double total = gestion.calcularCobroSalida(null, 10);
        assertEquals(0.0, total);
    }

    @Test
    public void calcularCobro_InvalidoMinutosNegativos_RetornaCero() {
        double total = gestion.calcularCobroSalida("XYZ123", -5);
        assertEquals(0.0, total);
    }

    @Test
    public void calcularCobro_InvalidoMinutosCero_RetornaCero() {
        double total = gestion.calcularCobroSalida("XYZ123", 0);
        assertEquals(0.0, total);
    }

    @Test
    public void registrarPago_Valido_RetornaTrue() {
        boolean exito = gestion.registrarPago("XYZ123", 400.0, "Nequi");
        assertTrue(exito);
    }

    @Test
    public void registrarPago_InvalidoPlacaNula_RetornaFalse() {
        boolean exito = gestion.registrarPago(null, 400.0, "Efectivo");
        assertFalse(exito);
    }

    @Test
    public void registrarPago_InvalidoValorCero_RetornaFalse() {
        boolean exito = gestion.registrarPago("XYZ123", 0.0, "Efectivo");
        assertFalse(exito);
    }

    @Test
    public void registrarPago_InvalidoMedioPagoNulo_RetornaFalse() {
        boolean exito = gestion.registrarPago("XYZ123", 400.0, null);
        assertFalse(exito);
    }

    @Test
    public void reporteDiario_Valido_RetornaTotalesCorrectos() {
        gestion.registrarPago("XYZ123", 400.0, "Nequi");
        gestion.registrarPago("ABC456", 800.0, "Efectivo");

        assertEquals(2, gestion.obtenerCantidadMotosAtendidas());
        assertEquals(1200.0, gestion.obtenerTotalDineroRecaudado());
    }

    @Test
    public void reporteDiario_SinPagos_RetornaCero() {
        assertEquals(0, gestion.obtenerCantidadMotosAtendidas());
        assertEquals(0.0, gestion.obtenerTotalDineroRecaudado());
    }

    @Test
    public void reporteDiario_InvalidoPagoFallidoNoSuma_RetornaCero() {
        gestion.registrarPago(null, 400.0, "Nequi");
        assertEquals(0, gestion.obtenerCantidadMotosAtendidas());
        assertEquals(0.0, gestion.obtenerTotalDineroRecaudado());
    }

    @Test
    public void reporteDiario_InvalidoValorNegativoNoSuma_RetornaCero() {
        gestion.registrarPago("XYZ123", -100.0, "Efectivo");
        assertEquals(0, gestion.obtenerCantidadMotosAtendidas());
        assertEquals(0.0, gestion.obtenerTotalDineroRecaudado());
    }
}