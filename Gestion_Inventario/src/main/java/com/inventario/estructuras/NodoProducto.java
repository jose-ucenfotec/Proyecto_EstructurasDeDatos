package com.inventario.estructuras;

import com.inventario.modelo.Producto;

//Nodo de la lista enlazada simple ListaProductos
public class NodoProducto {

    // Atributos
    private Producto producto;
    private NodoProducto siguiente;

    // Constructor
    public NodoProducto(Producto producto) {
        this.producto = producto;
        this.siguiente = null;
    }

    // Getters
    public Producto getProducto() {
        return producto;
    }

    public NodoProducto getSiguiente() {
        return siguiente;
    }

    // Setters
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public void setSiguiente(NodoProducto siguiente) {
        this.siguiente = siguiente;
    }

    public String toString() {
        return producto.toString();
    }
}
