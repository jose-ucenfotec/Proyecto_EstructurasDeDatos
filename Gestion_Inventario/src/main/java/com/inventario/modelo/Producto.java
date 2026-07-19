package com.inventario.modelo;

import java.time.LocalDate;
import java.util.ArrayList;

// Entidad Producto. Es el elemento almacenado tanto en el inventario de la
// Tienda (ArbolProductos) como en el carrito de cada Cliente (ListaProductos).
public class Producto {

    // Atributos
    private String nombre;
    private double precio;
    private String categoria;
    private LocalDate fechaVencimiento; // puede ser null si el producto no vence
    private int cantidad;
    private ArrayList<String> listaImagenes;

    // Constructor para productos sin fecha de vencimiento
    public Producto(String nombre, double precio, String categoria, int cantidad) {
        this(nombre, precio, categoria, null, cantidad);
    }

    // Constructor completo
    public Producto(String nombre, double precio, String categoria, LocalDate fechaVencimiento, int cantidad) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.fechaVencimiento = fechaVencimiento;
        this.cantidad = cantidad;
        this.listaImagenes = new ArrayList<>();
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public ArrayList<String> getListaImagenes() {
        return listaImagenes;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    // Operaciones sobre la lista de imagenes
    public void agregarImagen(String rutaImagen) {
        listaImagenes.add(rutaImagen);
    }

    // Costo total del producto en funcion de su cantidad
    public double calcularCostoTotal() {
        return precio * cantidad;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre: ").append(nombre).append("\n");
        sb.append("Precio: ").append(String.format("%.2f", precio)).append("\n");
        sb.append("Categoria: ").append(categoria).append("\n");
        sb.append("Fecha de vencimiento: ")
                .append(fechaVencimiento == null ? "No aplica" : fechaVencimiento).append("\n");
        sb.append("Cantidad: ").append(cantidad).append("\n");
        sb.append("Imagenes: ").append(listaImagenes.isEmpty() ? "Ninguna" : listaImagenes).append("\n");
        return sb.toString();
    }
}
