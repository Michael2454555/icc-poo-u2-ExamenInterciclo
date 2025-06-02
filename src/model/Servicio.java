package model;

public class Servicio implements Calculable {
    private String descripcion;
    private double tarifa;

    public Servicio(String descripcion, double tarifa) {
        this.descripcion = descripcion;
        this.tarifa = tarifa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getTarifa() {
        return tarifa;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setTarifa(double tarifa) {
        this.tarifa = tarifa;
    }

    @Override
    public double calcularCostoTotal() {
        return tarifa;
    }

    @Override
    public String toString() {
        return String.format("[Servicio] %s – Tarifa: $%.2f", descripcion, tarifa);
    }
}