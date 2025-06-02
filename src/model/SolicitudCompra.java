package model;

import java.time.LocalDate;
import java.util.List;


public class SolicitudCompra extends Documento implements Calculable {
    private List<Calculable> items;
    private EstadoSolicitud estado;

    public SolicitudCompra(String numero, LocalDate fecha, List<Calculable> items) {
        super(numero, fecha);
        this.items = items;
        this.estado = EstadoSolicitud.SOLICITADA;
    }

    public List<Calculable> getItems() {
        return items;
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }

    public void setItems(List<Calculable> items) {
        this.items = items;
    }

    public void setEstado(EstadoSolicitud estado) {
        this.estado = estado;
    }

    @Override
    public double calcularCostoTotal() {
        return items.stream()
                    .mapToDouble(Calculable::calcularCostoTotal)
                    .sum();
    }

    @Override
    public void mostrarResumen() {
        System.out.printf(
            "SolicitudCompra [%s] - Fecha: %s - Total: $%.2f - Estado: %s%n",
            getNumero(),
            getFecha(),
            calcularCostoTotal(),
            estado
        );
        System.out.println("Items:");
        for (Calculable c : items) {
            System.out.println("  • " + c.toString());
        }
    }

    public void aprobar() {
        if (estado == EstadoSolicitud.SOLICITADA || estado == EstadoSolicitud.EN_REVISION) {
            estado = EstadoSolicitud.APROBADA;
        }
    }

    public void rechazar() {
        estado = EstadoSolicitud.RECHAZADA;
    }
}