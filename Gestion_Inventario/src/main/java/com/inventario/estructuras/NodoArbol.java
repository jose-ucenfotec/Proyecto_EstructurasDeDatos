package com.inventario.estructuras;

import com.inventario.modelo.Producto;

//Nodo del arbol binario de busqueda ArbolProductos. La llave del nodo es el nombre del Producto
public class NodoArbol {

    // Atributos
    private Producto producto;
    private final String llave;
    private NodoArbol izquierdo;
    private NodoArbol derecho;

    // Constructor
    public NodoArbol(Producto producto) {
        this.producto = producto;
        this.llave = producto.getNombre();
    }

    // Getters
    public Producto getProducto() {
        return producto;
    }

    public String getLlave() {
        return llave;
    }

    public NodoArbol getIzquierdo() {
        return izquierdo;
    }

    public NodoArbol getDerecho() {
        return derecho;
    }

    // Setters
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public void setIzquierdo(NodoArbol izquierdo) {
        this.izquierdo = izquierdo;
    }

    public void setDerecho(NodoArbol derecho) {
        this.derecho = derecho;
    }

    public String toString() {
        return producto.toString();
    }
}
