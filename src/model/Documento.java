package model;

import java.time.LocalDate;


public abstract class Documento {
    private String numero;
    private LocalDate fecha;

    public Documento(String numero, LocalDate fecha) {
        this.numero = numero;
        this.fecha = fecha;
    }

    public String getNumero() {
        return numero;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    
    public abstract void mostrarResumen();
}
