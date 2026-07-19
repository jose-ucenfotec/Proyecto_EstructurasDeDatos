package com.inventario.estructuras;

import com.inventario.modelo.Cliente;

public class NodoCliente {

    // Atributos
    private Cliente cliente;
    private NodoCliente siguiente;

    // Constructor
    public NodoCliente(Cliente cliente) {
        this.cliente = cliente;
        this.siguiente = null;
    }

    // Getters
    public Cliente getCliente() {
        return cliente;
    }

    public NodoCliente getSiguiente() {
        return siguiente;
    }

    // Setters
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setSiguiente(NodoCliente siguiente) {
        this.siguiente = siguiente;
    }

    public String toString() {
        return cliente.toString();
    }
}
