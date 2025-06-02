package model;

public class Producto implements Calculable {
    private String id;
    private String nombre;
    private double precioUnitario;
    private Proveedor proveedor;

    public Producto(String id, String nombre, double precioUnitario, Proveedor proveedor) {
        this.id = id;
        this.nombre = nombre;
        this.precioUnitario = precioUnitario;
        this.proveedor = proveedor;
        // Asociar recíprocamente si se desea:
        if (proveedor != null) {
            proveedor.agregarProducto(this);
        }
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public void setProveedor(Proveedor proveedor) {
        // Remover de lista anterior si existía (no implementado aquí) 
        this.proveedor = proveedor;
        if (proveedor != null) {
            proveedor.agregarProducto(this);
        }
    }

    /**
     * Dado que implementa Calculable, aquí se devuelve el precio unitario.
     * (Si hubiera cantidad, habría que multiplicar.)
     */
    @Override
    public double calcularCostoTotal() {
        return precioUnitario;
    }

    @Override
    public String toString() {
        String prov = (proveedor != null) ? proveedor.getNombre() : "sin proveedor";
        return String.format(
            "[Producto] id: %s, nombre: %s, precioUnitario: %.2f, proveedor: %s",
            id, nombre, precioUnitario, prov
        );
    }
}