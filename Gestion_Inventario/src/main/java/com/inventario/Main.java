package com.inventario;

import com.inventario.modelo.Cliente;
import com.inventario.modelo.Producto;
import com.inventario.modelo.Tienda;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Main {

    private static final BufferedReader BR = new BufferedReader(new InputStreamReader(System.in));
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final Tienda TIENDA = new Tienda();

    public static void main(String[] args) throws IOException {
        menu();
    }

    // Menu principal de la aplicacion
    public static void menu() throws IOException {
        int opcion;
        do {
            System.out.println("===== Sistema de Gestion de Inventarios =====");
            System.out.println("1. Agregar producto al inventario");
            System.out.println("2. Mostrar inventario");
            System.out.println("3. Buscar producto por nombre");
            System.out.println("4. Modificar producto");
            System.out.println("5. Eliminar producto");
            System.out.println("6. Generar reporte de costos del inventario");
            System.out.println("7. Agregar cliente a la cola (llenar carrito)");
            System.out.println("8. Atender siguiente cliente (factura)");
            System.out.println("9. Mostrar cola de clientes");
            System.out.println("0. Salir");
            System.out.println("==============================================");
            opcion = leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1 -> agregarProducto();
                case 2 -> TIENDA.mostrarInventario();
                case 3 -> buscarProducto();
                case 4 -> modificarProducto();
                case 5 -> eliminarProducto();
                case 6 -> TIENDA.reporteCostos();
                case 7 -> agregarCliente();
                case 8 -> atenderCliente();
                case 9 -> mostrarCola();
                case 0 -> System.out.println("Gracias por usar el sistema. Hasta pronto.");
                default -> System.out.println("Opcion invalida. Intente de nuevo.\n");
            }
        } while (opcion != 0);
    }

    // Opciones sobre productos

    private static void agregarProducto() throws IOException {
        System.out.println("--- Registro de nuevo producto ---");
        String nombre = leerTexto("Nombre del producto: ");
        if (TIENDA.buscarProducto(nombre) != null) {
            System.out.println("Ya existe un producto con ese nombre en el inventario.\n");
            return;
        }
        double precio = leerDecimal("Precio del producto: ");
        String categoria = leerTexto("Categoria del producto: ");
        LocalDate fechaVencimiento = leerFechaOpcional();
        int cantidad = leerEntero("Cantidad de unidades: ");

        Producto producto = new Producto(nombre, precio, categoria, fechaVencimiento, cantidad);

        String rutaImagen = leerTexto("Ruta de una imagen para el producto (vacio para omitir): ");
        if (!rutaImagen.isBlank()) producto.agregarImagen(rutaImagen);

        if (TIENDA.agregarProducto(producto)) {
            System.out.println("Producto agregado correctamente.\n");
        } else {
            System.out.println("No se pudo agregar el producto.\n");
        }
    }

    private static void buscarProducto() throws IOException {
        String nombre = leerTexto("Ingrese el nombre del producto a buscar: ");
        Producto producto = TIENDA.buscarProducto(nombre);
        if (producto == null) {
            System.out.println("No se encontro un producto con el nombre indicado.\n");
        } else {
            System.out.println("Producto encontrado:");
            System.out.println(producto);
        }
    }

    private static void modificarProducto() throws IOException {
        String nombre = leerTexto("Ingrese el nombre del producto a modificar: ");
        Producto producto = TIENDA.buscarProducto(nombre);
        if (producto == null) {
            System.out.println("No se encontro un producto con el nombre indicado.\n");
            return;
        }

        int opcion;
        do {
            System.out.println("--- Modificando: " + producto.getNombre() + " ---");
            System.out.println("1. Cambiar precio");
            System.out.println("2. Cambiar categoria");
            System.out.println("3. Cambiar fecha de vencimiento");
            System.out.println("4. Cambiar cantidad");
            System.out.println("5. Agregar imagen");
            System.out.println("0. Volver al menu principal");
            opcion = leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1 -> producto.setPrecio(leerDecimal("Nuevo precio: "));
                case 2 -> producto.setCategoria(leerTexto("Nueva categoria: "));
                case 3 -> producto.setFechaVencimiento(leerFechaOpcional());
                case 4 -> producto.setCantidad(leerEntero("Nueva cantidad: "));
                case 5 -> producto.agregarImagen(leerTexto("Ruta de la nueva imagen: "));
                case 0 -> System.out.println("Volviendo al menu principal.\n");
                default -> System.out.println("Opcion invalida. Intente de nuevo.\n");
            }
        } while (opcion != 0);
    }

    private static void eliminarProducto() throws IOException {
        String nombre = leerTexto("Ingrese el nombre del producto a eliminar: ");
        Producto eliminado = TIENDA.eliminarProducto(nombre);
        if (eliminado != null) {
            System.out.println("Producto eliminado correctamente:");
            System.out.println(eliminado);
        } else {
            System.out.println("No se encontro un producto con el nombre indicado.\n");
        }
    }

    // Opciones sobre clientes

    private static void agregarCliente() throws IOException {
        if (TIENDA.inventarioVacio()) {
            System.out.println("El inventario esta vacio. Agregue productos antes de registrar clientes.\n");
            return;
        }

        System.out.println("--- Registro de nuevo cliente ---");
        String nombre = leerTexto("Nombre del cliente: ");
        System.out.println("Prioridad: 1 = Basico, 2 = Afiliado, 3 = Premium");
        int prioridad = leerEntero("Seleccione la prioridad (1-3): ");
        while (prioridad < 1 || prioridad > 3) {
            prioridad = leerEntero("Prioridad invalida. Ingrese 1, 2 o 3: ");
        }

        Cliente cliente = new Cliente(nombre, prioridad);

        System.out.println("\n--- Llenado del carrito para " + nombre + " ---");
        boolean seguir = true;
        while (seguir) {
            System.out.println("\nInventario disponible:");
            TIENDA.mostrarInventario();

            String nombreProducto = leerTexto("Nombre del producto a agregar ('fin' para terminar): ");
            if (nombreProducto.equalsIgnoreCase("fin")) {
                seguir = false;
                continue;
            }

            Producto producto = TIENDA.buscarProducto(nombreProducto);
            if (producto == null) {
                System.out.println("Producto no encontrado.\n");
                continue;
            }

            int cantidad = leerEntero("Cantidad deseada: ");
            if (cantidad <= 0) {
                System.out.println("La cantidad debe ser mayor que cero.\n");
                continue;
            }
            if (!TIENDA.verificarInventario(nombreProducto, cantidad)) {
                System.out.println("Inventario insuficiente. Disponible: " + producto.getCantidad() + ".\n");
                continue;
            }

            cliente.agregarAlCarrito(producto, cantidad);
            TIENDA.descontarInventario(nombreProducto, cantidad);
            System.out.println("Producto agregado al carrito.\n");
        }

        if (cliente.getCantidadProductos() > 0) {
            TIENDA.agregarClienteCola(cliente);
            System.out.println("Cliente '" + nombre + "' agregado a la cola con "
                    + cliente.getCantidadProductos() + " producto(s).\n");
        } else {
            System.out.println("Cliente no agregado: el carrito quedo vacio.\n");
        }
    }

    private static void atenderCliente() {
        if (!TIENDA.hayClientesEnCola()) {
            System.out.println("No hay clientes en espera.\n");
            return;
        }
        Cliente atendido = TIENDA.atenderSiguienteCliente();

        System.out.println("\n=============== FACTURA ===============");
        System.out.println("Cliente: " + atendido.getNombre());
        System.out.println("Prioridad: " + atendido.getPrioridad());
        System.out.println("--------------- Detalle ---------------");
        atendido.getCarrito().generarReporteCostos();
        System.out.println("---------------------------------------");
        System.out.printf("TOTAL A PAGAR: %.2f%n", atendido.calcularTotal());
        System.out.println("=======================================\n");
    }

    private static void mostrarCola() {
        if (!TIENDA.hayClientesEnCola()) {
            System.out.println("No hay clientes en la cola.\n");
            return;
        }
        System.out.println("Clientes en espera: " + TIENDA.getCantidadClientesEnCola());
        Cliente proximo = TIENDA.verProximoCliente();
        if (proximo != null) {
            System.out.println("Proximo a atender: " + proximo.getNombre()
                    + " (prioridad " + proximo.getPrioridad() + ")");
        }
        System.out.println();
    }

    private static String leerTexto(String mensaje) throws IOException {
        System.out.print(mensaje);
        String linea = BR.readLine();
        return (linea == null) ? "" : linea.trim();
    }

    private static int leerEntero(String mensaje) throws IOException {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(BR.readLine().trim());
            } catch (NumberFormatException | NullPointerException e) {
                System.out.println("Entrada invalida. Debe ingresar un numero entero.\n");
            }
        }
    }

    private static double leerDecimal(String mensaje) throws IOException {
        while (true) {
            System.out.print(mensaje);
            try {
                return Double.parseDouble(BR.readLine().trim());
            } catch (NumberFormatException | NullPointerException e) {
                System.out.println("Entrada invalida. Debe ingresar un numero (use punto decimal).\n");
            }
        }
    }

    private static LocalDate leerFechaOpcional() throws IOException {
        while (true) {
            String entrada = leerTexto("Fecha de vencimiento (dd/MM/yyyy, vacio si no aplica): ");
            if (entrada.isBlank()) return null;
            try {
                return LocalDate.parse(entrada, FORMATO_FECHA);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha invalido. Use el formato dd/MM/yyyy.\n");
            }
        }
    }
}
