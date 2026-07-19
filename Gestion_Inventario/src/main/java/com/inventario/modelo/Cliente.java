package com.inventario.modelo;

import com.inventario.estructuras.ListaProductos;
import com.inventario.estructuras.NodoProducto;

// Entidad Cliente. Cada cliente tiene una prioridad (1 basico, 2 afiliado,
// 3 premium) y un carrito propio implementado como ListaProductos.

public class Cliente {

    // Atributos
    private String nombre;
    private int prioridad; // 1 = basico, 2 = afiliado, 3 = premium
    private final ListaProductos carrito;

    // Constructor
    public Cliente(String nombre, int prioridad) {
        this.nombre = nombre;
        setPrioridad(prioridad);
        this.carrito = new ListaProductos();
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public ListaProductos getCarrito() {
        return carrito;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrioridad(int prioridad) {
        if (prioridad >= 1 && prioridad <= 3) {
            this.prioridad = prioridad;
        }
    }

    // Agrega al carrito una copia del producto con la cantidad comprada,
    // para no alterar el producto original que vive en el inventario.
    public void agregarAlCarrito(Producto producto, int cantidad) {
        Producto copia = new Producto(
                producto.getNombre(),
                producto.getPrecio(),
                producto.getCategoria(),
                producto.getFechaVencimiento(),
                cantidad
        );
        carrito.insertarFinal(copia);
    }

    // Suma el costo total de todos los productos del carrito
    public double calcularTotal() {
        double total = 0;
        NodoProducto temp = carrito.getPrimero();
        while (temp != null) {
            total += temp.getProducto().calcularCostoTotal();
            temp = temp.getSiguiente();
        }
        return total;
    }

    // Cantidad de lineas de producto en el carrito
    public int getCantidadProductos() {
        int contador = 0;
        NodoProducto temp = carrito.getPrimero();
        while (temp != null) {
            contador++;
            temp = temp.getSiguiente();
        }
        return contador;
    }

    public void mostrarCarrito() {
        carrito.mostrar();
    }

    private String etiquetaPrioridad() {
        switch (prioridad) {
            case 1: return "Basico";
            case 2: return "Afiliado";
            case 3: return "Premium";
            default: return "Desconocido";
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cliente: ").append(nombre).append("\n");
        sb.append("Prioridad: ").append(etiquetaPrioridad())
                .append(" (").append(prioridad).append(")\n");
        sb.append("Productos en carrito: ").append(getCantidadProductos()).append("\n");
        return sb.toString();
    }
}
