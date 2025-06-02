package model;

import java.util.List;

public class PaqueteCompra implements Calculable {
    private String nombre;
    private List<Calculable> componentes;

    public PaqueteCompra(String nombre, List<Calculable> componentes) {
        this.nombre = nombre;
        this.componentes = componentes;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Calculable> getComponentes() {
        return componentes;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setComponentes(List<Calculable> componentes) {
        this.componentes = componentes;
    }

    @Override
    public double calcularCostoTotal() {
        return componentes.stream()
                          .mapToDouble(Calculable::calcularCostoTotal)
                          .sum();
    }

    @Override
    public String toString() {
        return String.format("[PaqueteCompra] %s – $%.2f", nombre, calcularCostoTotal());
    }
}

