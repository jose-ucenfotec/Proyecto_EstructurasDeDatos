package com.inventario;

import com.inventario.estructuras.ListaProductos;
import com.inventario.estructuras.NodoProducto;
import com.inventario.modelo.Producto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {

    private static final Scanner SC = new Scanner(System.in);
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final ListaProductos LISTA_PRODUCTOS = new ListaProductos();

    public static void main(String[] args) {
        menu();
        SC.close();
    }

    // Menu principal de la aplicacion
    public static void menu() {
        int opcion;
        do {
            System.out.println("===== Sistema de Gestion de Inventarios =====");
            System.out.println("1. Insertar producto al inicio de la lista");
            System.out.println("2. Insertar producto al final de la lista");
            System.out.println("3. Buscar producto por nombre");
            System.out.println("4. Modificar producto");
            System.out.println("5. Eliminar producto");
            System.out.println("6. Mostrar todos los productos");
            System.out.println("7. Generar reporte de costos del inventario");
            System.out.println("0. Salir");
            System.out.println("==============================================");
            opcion = leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1 -> insertarProducto(true);
                case 2 -> insertarProducto(false);
                case 3 -> buscarProducto();
                case 4 -> modificarProducto();
                case 5 -> eliminarProducto();
                case 6 -> LISTA_PRODUCTOS.mostrar();
                case 7 -> LISTA_PRODUCTOS.generarReporteCostos();
                case 0 -> System.out.println("Gracias por usar el sistema. Hasta pronto.");
                default -> System.out.println("Opcion invalida. Intente de nuevo.\n");
            }
        } while (opcion != 0);
    }

    private static void insertarProducto(boolean alInicio) {
        System.out.println("--- Registro de nuevo producto ---");
        String nombre = leerTexto("Nombre del producto: ");
        double precio = leerDecimal("Precio del producto: ");
        String categoria = leerTexto("Categoria del producto: ");
        LocalDate fechaVencimiento = leerFechaOpcional();
        int cantidad = leerEntero("Cantidad de unidades: ");

        Producto producto = new Producto(nombre, precio, categoria, fechaVencimiento, cantidad);

        String rutaImagen = leerTexto("Ruta de una imagen para el producto (vacio para omitir): ");
        if (!rutaImagen.isBlank()) producto.agregarImagen(rutaImagen);

        if (alInicio) {
            LISTA_PRODUCTOS.insertarInicio(producto);
        } else {
            LISTA_PRODUCTOS.insertarFinal(producto);
        }
        System.out.println("Producto agregado correctamente.\n");
    }

    private static void buscarProducto() {
        String nombre = leerTexto("Ingrese el nombre del producto a buscar: ");
        NodoProducto nodo = LISTA_PRODUCTOS.buscar(nombre);
        if (nodo != null) {
            System.out.println("Producto encontrado:");
            System.out.println(nodo);
        }
    }

    private static void modificarProducto() {
        String nombre = leerTexto("Ingrese el nombre del producto a modificar: ");
        NodoProducto nodo = LISTA_PRODUCTOS.buscar(nombre);
        if (nodo == null) return;

        Producto producto = nodo.getProducto();
        int opcion;
        do {
            System.out.println("--- Modificando: " + producto.getNombre() + " ---");
            System.out.println("1. Cambiar nombre");
            System.out.println("2. Cambiar precio");
            System.out.println("3. Cambiar categoria");
            System.out.println("4. Cambiar fecha de vencimiento");
            System.out.println("5. Cambiar cantidad");
            System.out.println("6. Agregar imagen");
            System.out.println("0. Volver al menu principal");
            opcion = leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1 -> producto.setNombre(leerTexto("Nuevo nombre: "));
                case 2 -> producto.setPrecio(leerDecimal("Nuevo precio: "));
                case 3 -> producto.setCategoria(leerTexto("Nueva categoria: "));
                case 4 -> producto.setFechaVencimiento(leerFechaOpcional());
                case 5 -> producto.setCantidad(leerEntero("Nueva cantidad: "));
                case 6 -> producto.agregarImagen(leerTexto("Ruta de la nueva imagen: "));
                case 0 -> System.out.println("Volviendo al menu principal.\n");
                default -> System.out.println("Opcion invalida. Intente de nuevo.\n");
            }
        } while (opcion != 0);
    }

    private static void eliminarProducto() {
        String nombre = leerTexto("Ingrese el nombre del producto a eliminar: ");
        NodoProducto eliminado = LISTA_PRODUCTOS.eliminar(nombre);
        if (eliminado != null) {
            System.out.println("Producto eliminado correctamente:");
            System.out.println(eliminado);
        }
    }

    // Metodos de lectura auxiliares con validacion de entrada

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return SC.nextLine().trim();
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(SC.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Debe ingresar un numero entero.\n");
            }
        }
    }

    private static double leerDecimal(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(SC.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Debe ingresar un numero (use punto decimal).\n");
            }
        }
    }

    private static LocalDate leerFechaOpcional() {
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
