package controller;

import model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Controlador principal que maneja el menú en consola y CRUD de proveedores,
 * productos, servicios, paquetes y solicitudes.
 */
public class Controlador {
    private Scanner sc;
    private List<Proveedor> proveedores;
    private List<Producto> productos;
    private List<Servicio> servicios;
    private List<PaqueteCompra> paquetes;
    private List<SolicitudCompra> solicitudes;

    public Controlador() {
        sc = new Scanner(System.in);
        proveedores = new ArrayList<>();
        productos = new ArrayList<>();
        servicios = new ArrayList<>();
        paquetes = new ArrayList<>();
        solicitudes = new ArrayList<>();
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");
            switch (opcion) {
                case 1: registrarProveedor(); break;
                case 2: registrarProducto(); break;
                case 3: registrarServicio(); break;
                case 4: registrarPaquete(); break;
                case 5: registrarSolicitudCompra(); break;
                case 6: listarProveedores(); break;
                case 7: listarProductos(); break;
                case 8: listarServicios(); break;
                case 9: listarPaquetes(); break;
                case 10: listarSolicitudes(); break;
                case 11: buscarProveedorPorId(); break;
                case 12: buscarProductoPorNombre(); break;
                case 13: buscarSolicitudPorNumero(); break;
                case 14: aprobarSolicitud(); break;
                case 15: rechazarSolicitud(); break;
                case 16: calcularTotalSolicitud(); break;
                case 17: System.out.println("Saliendo del sistema…"); break;
                default: System.out.println("Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 17);
    }

    private void mostrarMenu() {
        System.out.println("\n===== GESTIÓN DE COMPRAS ERP =====");
        System.out.println("1.  Registrar proveedor");
        System.out.println("2.  Registrar producto");
        System.out.println("3.  Registrar servicio");
        System.out.println("4.  Registrar paquete de compra");
        System.out.println("5.  Registrar solicitud de compra");
        System.out.println("6.  Listar proveedores");
        System.out.println("7.  Listar productos");
        System.out.println("8.  Listar servicios");
        System.out.println("9.  Listar paquetes");
        System.out.println("10. Listar solicitudes de compra");
        System.out.println("11. Buscar proveedor por ID");
        System.out.println("12. Buscar producto por nombre");
        System.out.println("13. Buscar solicitud por número");
        System.out.println("14. Aprobar solicitud");
        System.out.println("15. Rechazar solicitud");
        System.out.println("16. Calcular total de una solicitud");
        System.out.println("17. Salir");
    }

    /** Lee un entero de consola con validación básica. */
    private int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!sc.hasNextInt()) {
            sc.next();
            System.out.print("Por favor ingrese un número válido: ");
        }
        int valor = sc.nextInt();
        sc.nextLine();
        return valor;
    }

    /** Lee una cadena de consola. */
    private String leerCadena(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim();
    }

    /** Valida que un texto contenga solo dígitos. */
    private boolean esNumerico(String texto) {
        return texto.matches("\\d+");
    }

    /** Registrar nuevo proveedor */
    private void registrarProveedor() {
        System.out.println("\n-- Registrar nuevo proveedor --");
        String id = leerCadena("ID numérico del proveedor: ");
        if (!esNumerico(id)) {
            System.out.println("Error: el ID del proveedor debe contener solo números.");
            return;
        }
        String nombre = leerCadena("Nombre del proveedor: ");
        Optional<Proveedor> existente = proveedores.stream()
            .filter(p -> p.getId().equalsIgnoreCase(id))
            .findAny();
        if (existente.isPresent()) {
            System.out.println("Ya existe un proveedor con ese ID.");
            return;
        }
        Proveedor nuevo = new Proveedor(id, nombre);
        proveedores.add(nuevo);
        System.out.println("Proveedor registrado con éxito.");
    }

    /** Registrar nuevo producto */
    private void registrarProducto() {
        System.out.println("\n-- Registrar nuevo producto --");
        String id = leerCadena("ID numérico del producto: ");
        if (!esNumerico(id)) {
            System.out.println("Error: el ID del producto debe contener solo números.");
            return;
        }
        String nombre = leerCadena("Nombre del producto: ");
        double precio;
        try {
            System.out.print("Precio unitario: ");
            precio = Double.parseDouble(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Precio inválido. Operación cancelada.");
            return;
        }
        if (proveedores.isEmpty()) {
            System.out.println("No hay proveedores registrados. Registre uno primero.");
            return;
        }
        System.out.println("Seleccione el proveedor por número:");
        for (int i = 0; i < proveedores.size(); i++) {
            Proveedor p = proveedores.get(i);
            System.out.printf("  %d. %s (%s)%n", i + 1, p.getNombre(), p.getId());
        }
        int idxProv = leerEntero("Número de proveedor: ") - 1;
        if (idxProv < 0 || idxProv >= proveedores.size()) {
            System.out.println("Índice de proveedor inválido. Operación cancelada.");
            return;
        }
        Proveedor proveedor = proveedores.get(idxProv);

        boolean existeProd = productos.stream()
            .anyMatch(prod -> prod.getId().equalsIgnoreCase(id));
        if (existeProd) {
            System.out.println("Ya existe un producto con ese ID.");
            return;
        }

        Producto prod = new Producto(id, nombre, precio, proveedor);
        productos.add(prod);
        System.out.println("Producto registrado con éxito.");
    }

    /** Registrar nuevo servicio */
    private void registrarServicio() {
        System.out.println("\n-- Registrar nuevo servicio --");
        String descripcion = leerCadena("Descripción del servicio: ");
        double tarifa;
        try {
            System.out.print("Tarifa: ");
            tarifa = Double.parseDouble(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Tarifa inválida. Operación cancelada.");
            return;
        }
        Servicio serv = new Servicio(descripcion, tarifa);
        servicios.add(serv);
        // Formateamos manualmente para evitar caracteres extraños
        System.out.printf("Servicio registrado con éxito: [Servicio] %s -Tarifa: $%.2f%n",
                          serv.getDescripcion(), serv.getTarifa());
    }

    /** Registrar nuevo paquete de compra */
    private void registrarPaquete() {
        System.out.println("\n-- Registrar nuevo paquete de compra --");
        String nombre = leerCadena("Nombre del paquete: ");
        if (productos.isEmpty() && servicios.isEmpty()) {
            System.out.println("Debe haber al menos un producto o servicio registrado para crear un paquete.");
            return;
        }
        List<Calculable> componentes = new ArrayList<>();
        String opc;
        do {
            System.out.println("¿Qué desea agregar al paquete?");
            System.out.println("1. Producto");
            System.out.println("2. Servicio");
            int tipo = leerEntero("Opción: ");
            if (tipo == 1) {
                if (productos.isEmpty()) {
                    System.out.println("No hay productos disponibles.");
                } else {
                    System.out.println("Productos disponibles:");
                    for (int i = 0; i < productos.size(); i++) {
                        Producto p = productos.get(i);
                        System.out.printf("  %d. %s | %s | $%.2f%n",
                            i + 1, p.getId(), p.getNombre(), p.getPrecioUnitario());
                    }
                    int idxProd = leerEntero("Número de producto a agregar: ") - 1;
                    if (idxProd >= 0 && idxProd < productos.size()) {
                        componentes.add(productos.get(idxProd));
                        System.out.println("Producto agregado al paquete.");
                    } else {
                        System.out.println("Índice de producto inválido.");
                    }
                }
            } else if (tipo == 2) {
                if (servicios.isEmpty()) {
                    System.out.println("No hay servicios disponibles.");
                } else {
                    System.out.println("Servicios disponibles:");
                    for (int i = 0; i < servicios.size(); i++) {
                        Servicio s = servicios.get(i);
                        System.out.printf("  %d. [Servicio] %s -Tarifa: $%.2f%n",
                            i + 1, s.getDescripcion(), s.getTarifa());
                    }
                    int idxServ = leerEntero("Número de servicio a agregar: ") - 1;
                    if (idxServ >= 0 && idxServ < servicios.size()) {
                        componentes.add(servicios.get(idxServ));
                        System.out.println("Servicio agregado al paquete.");
                    } else {
                        System.out.println("Índice de servicio inválido.");
                    }
                }
            } else {
                System.out.println("Opción inválida.");
            }
            opc = leerCadena("¿Desea agregar otro componente? (s/n): ");
        } while (opc.equalsIgnoreCase("s"));

        if (componentes.isEmpty()) {
            System.out.println("No se agregó ningún componente. Operación cancelada.");
            return;
        }
        PaqueteCompra paquete = new PaqueteCompra(nombre, componentes);
        paquetes.add(paquete);
        System.out.println("Paquete registrado con éxito: " +
            String.format("[PaqueteCompra] %s -$%.2f", paquete.getNombre(), paquete.calcularCostoTotal()));
    }

    /** Registrar nueva solicitud de compra */
    private void registrarSolicitudCompra() {
        System.out.println("\n-- Registrar nueva solicitud de compra --");
        String numero = leerCadena("Número de la solicitud: ");
        if (!esNumerico(numero)) {
            System.out.println("Error: el número de la solicitud debe contener solo números.");
            return;
        }
        boolean existeNum = solicitudes.stream()
            .anyMatch(s -> s.getNumero().equalsIgnoreCase(numero));
        if (existeNum) {
            System.out.println("Ya existe una solicitud con ese número.");
            return;
        }
        if (productos.isEmpty() && servicios.isEmpty() && paquetes.isEmpty()) {
            System.out.println("No hay productos, servicios ni paquetes disponibles. Registre primero alguno.");
            return;
        }
        List<Calculable> itemsSeleccionados = new ArrayList<>();
        String continuar;
        do {
            System.out.println("¿Qué desea agregar a la solicitud?");
            System.out.println("1. Producto");
            System.out.println("2. Servicio");
            System.out.println("3. Paquete");
            int tipo = leerEntero("Opción: ");
            if (tipo == 1) {
                if (productos.isEmpty()) {
                    System.out.println("No hay productos disponibles.");
                } else {
                    System.out.println("Productos disponibles:");
                    for (int i = 0; i < productos.size(); i++) {
                        Producto p = productos.get(i);
                        System.out.printf("  %d. %s - %s - $%.2f%n",
                            i + 1, p.getId(), p.getNombre(), p.getPrecioUnitario());
                    }
                    int idxProd = leerEntero("Número de producto a agregar: ") - 1;
                    if (idxProd >= 0 && idxProd < productos.size()) {
                        itemsSeleccionados.add(productos.get(idxProd));
                        System.out.println("Producto agregado a la solicitud.");
                    } else {
                        System.out.println("Índice de producto inválido.");
                    }
                }
            } else if (tipo == 2) {
                if (servicios.isEmpty()) {
                    System.out.println("No hay servicios disponibles.");
                } else {
                    System.out.println("Servicios disponibles:");
                    for (int i = 0; i < servicios.size(); i++) {
                        Servicio s = servicios.get(i);
                        System.out.printf("  %d. [Servicio] %s  -Tarifa: $%.2f%n",
                            i + 1, s.getDescripcion(), s.getTarifa());
                    }
                    int idxServ = leerEntero("Número de servicio a agregar: ") - 1;
                    if (idxServ >= 0 && idxServ < servicios.size()) {
                        itemsSeleccionados.add(servicios.get(idxServ));
                        System.out.println("Servicio agregado a la solicitud.");
                    } else {
                        System.out.println("Índice de servicio inválido.");
                    }
                }
            } else if (tipo == 3) {
                if (paquetes.isEmpty()) {
                    System.out.println("No hay paquetes disponibles.");
                } else {
                    System.out.println("Paquetes disponibles:");
                    for (int i = 0; i < paquetes.size(); i++) {
                        PaqueteCompra p = paquetes.get(i);
                        System.out.printf("  %d. [PaqueteCompra] %s -$%.2f%n",
                            i + 1, p.getNombre(), p.calcularCostoTotal());
                    }
                    int idxPaquete = leerEntero("Número de paquete a agregar: ") - 1;
                    if (idxPaquete >= 0 && idxPaquete < paquetes.size()) {
                        itemsSeleccionados.add(paquetes.get(idxPaquete));
                        System.out.println("Paquete agregado a la solicitud.");
                    } else {
                        System.out.println("Índice de paquete inválido.");
                    }
                }
            } else {
                System.out.println("Opción inválida.");
            }
            continuar = leerCadena("¿Desea agregar otro ítem? (s/n): ");
        } while (continuar.equalsIgnoreCase("s"));

        if (itemsSeleccionados.isEmpty()) {
            System.out.println("No se agregó ningún ítem. Operación cancelada.");
            return;
        }
        SolicitudCompra nuevaSol = new SolicitudCompra(
            numero,
            LocalDate.now(),
            itemsSeleccionados
        );
        solicitudes.add(nuevaSol);
        System.out.println("Solicitud de compra registrada con éxito.");
    }

    /** Listar proveedores */
    private void listarProveedores() {
        System.out.println("\n-- Lista de proveedores --");
        if (proveedores.isEmpty()) {
            System.out.println("No hay proveedores registrados.");
            return;
        }
        for (Proveedor p : proveedores) {
            System.out.println("- " + p.toString());
        }
    }

    /** Listar productos */
    private void listarProductos() {
        System.out.println("\n-- Lista de productos --");
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }
        for (Producto p : productos) {
            System.out.println("- " + p.toString());
        }
    }

    /** Listar servicios */
    private void listarServicios() {
        System.out.println("\n-- Lista de servicios --");
        if (servicios.isEmpty()) {
            System.out.println("No hay servicios registrados.");
            return;
        }
        for (Servicio s : servicios) {
            System.out.printf("- [Servicio] %s -Tarifa: $%.2f%n", s.getDescripcion(), s.getTarifa());
        }
    }

    /** Listar paquetes */
    private void listarPaquetes() {
        System.out.println("\n-- Lista de paquetes de compra --");
        if (paquetes.isEmpty()) {
            System.out.println("No hay paquetes registrados.");
            return;
        }
        for (PaqueteCompra p : paquetes) {
            System.out.printf("- [PaqueteCompra] %s -$%.2f%n", p.getNombre(), p.calcularCostoTotal());
        }
    }

    /** Listar solicitudes de compra */
    private void listarSolicitudes() {
        System.out.println("\n-- Lista de solicitudes de compra --");
        if (solicitudes.isEmpty()) {
            System.out.println("No hay solicitudes registradas.");
            return;
        }
        for (SolicitudCompra sol : solicitudes) {
            sol.mostrarResumen();
            System.out.println("------------------------------");
        }
    }

    /** Buscar proveedor por ID */
    private void buscarProveedorPorId() {
        String id = leerCadena("Ingrese ID de proveedor a buscar: ");
        if (!esNumerico(id)) {
            System.out.println("Error: el ID debe contener solo números.");
            return;
        }
        Optional<Proveedor> opt = proveedores.stream()
            .filter(p -> p.getId().equalsIgnoreCase(id))
            .findAny();
        if (opt.isPresent()) {
            System.out.println("Proveedor encontrado:");
            System.out.println(opt.get().toString());
        } else {
            System.out.println("Proveedor con ID \"" + id + "\" no encontrado.");
        }
    }

    /** Buscar producto por nombre (parcial) */
    private void buscarProductoPorNombre() {
        String nombre = leerCadena("Ingrese nombre (completo o parcial) del producto: ");
        List<Producto> encontrados = new ArrayList<>();
        for (Producto p : productos) {
            if (p.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                encontrados.add(p);
            }
        }
        if (encontrados.isEmpty()) {
            System.out.println("No se encontraron productos que contengan \"" + nombre + "\".");
        } else {
            System.out.println("Productos encontrados:");
            for (Producto p : encontrados) {
                System.out.println("- " + p.toString());
            }
        }
    }

    /** Buscar solicitud por número */
    private void buscarSolicitudPorNumero() {
        String num = leerCadena("Ingrese número de solicitud a buscar: ");
        if (!esNumerico(num)) {
            System.out.println("Error: el número de la solicitud debe contener solo números.");
            return;
        }
        Optional<SolicitudCompra> opt = solicitudes.stream()
            .filter(s -> s.getNumero().equalsIgnoreCase(num))
            .findAny();
        if (opt.isPresent()) {
            System.out.println("Solicitud encontrada:");
            opt.get().mostrarResumen();
        } else {
            System.out.println("No existe ninguna solicitud con número \"" + num + "\".");
        }
    }

    /** Aprobar solicitud */
    private void aprobarSolicitud() {
        String num = leerCadena("Número de solicitud a aprobar: ");
        if (!esNumerico(num)) {
            System.out.println("Error: el número de la solicitud debe contener solo números.");
            return;
        }
        Optional<SolicitudCompra> opt = solicitudes.stream()
            .filter(s -> s.getNumero().equalsIgnoreCase(num))
            .findAny();
        if (opt.isPresent()) {
            SolicitudCompra sol = opt.get();
            sol.aprobar();
            System.out.println("Solicitud \"" + num + "\" ahora está en estado: " + sol.getEstado());
        } else {
            System.out.println("No se encontró la solicitud \"" + num + "\".");
        }
    }

    /** Rechazar solicitud */
    private void rechazarSolicitud() {
        String num = leerCadena("Número de solicitud a rechazar: ");
        if (!esNumerico(num)) {
            System.out.println("Error: el número de la solicitud debe contener solo números.");
            return;
        }
        Optional<SolicitudCompra> opt = solicitudes.stream()
            .filter(s -> s.getNumero().equalsIgnoreCase(num))
            .findAny();
        if (opt.isPresent()) {
            SolicitudCompra sol = opt.get();
            sol.rechazar();
            System.out.println("Solicitud \"" + num + "\" ahora está en estado: " + sol.getEstado());
        } else {
            System.out.println("No se encontró la solicitud \"" + num + "\".");
        }
    }

    /** Calcular total de una solicitud */
    private void calcularTotalSolicitud() {
        String num = leerCadena("Número de solicitud para calcular total: ");
        if (!esNumerico(num)) {
            System.out.println("Error: el número de la solicitud debe contener solo números.");
            return;
        }
        Optional<SolicitudCompra> opt = solicitudes.stream()
            .filter(s -> s.getNumero().equalsIgnoreCase(num))
            .findAny();
        if (opt.isPresent()) {
            SolicitudCompra sol = opt.get();
            double total = sol.calcularCostoTotal();
            System.out.printf("Total de la solicitud \"%s\": $%.2f%n", num, total);
        } else {
            System.out.println("No se encontró la solicitud \"" + num + "\".");
        }
    }
}
