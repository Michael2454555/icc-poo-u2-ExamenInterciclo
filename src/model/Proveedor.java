package model;

import java.util.ArrayList;
import java.util.List;


public class Proveedor {
    private String id;
    private String nombre;
    private List<Producto> productosOfrecidos;

    public Proveedor(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.productosOfrecidos = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Producto> getProductosOfrecidos() {
        return productosOfrecidos;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void agregarProducto(Producto p) {
        if (p != null && !productosOfrecidos.contains(p)) {
            productosOfrecidos.add(p);
        }
    }

    @Override
    public String toString() {
        return String.format("[Proveedor] id: %s, nombre: %s, #productos: %d",
                id, nombre, productosOfrecidos.size());
    }

}