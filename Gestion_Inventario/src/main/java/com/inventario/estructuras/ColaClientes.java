package com.inventario.estructuras;

import com.inventario.modelo.Cliente;

//Cola de prioridad de clientes.
public class ColaClientes {

    // Atributos
    private NodoCliente frente;
    private NodoCliente fondo;

    // Constructor
    public ColaClientes() {
        this.frente = null;
        this.fondo = null;
    }

    public boolean estaVacia() {
        return frente == null;
    }

    public int getTamanio() {
        int contador = 0;
        NodoCliente actual = frente;
        while (actual != null) {
            contador++;
            actual = actual.getSiguiente();
        }
        return contador;
    }

    // Inserta respetando la prioridad y el orden de llegada
    public void insertar(Cliente cliente) {
        NodoCliente nuevo = new NodoCliente(cliente);

        // Cola vacia
        if (estaVacia()) {
            frente = nuevo;
            fondo = nuevo;
            return;
        }

        // El nuevo cliente supera en prioridad al del frente
        if (cliente.getPrioridad() > frente.getCliente().getPrioridad()) {
            nuevo.setSiguiente(frente);
            frente = nuevo;
            return;
        }

        // Se avanza mientras la prioridad existente sea mayor o igual,
        // asi los empates quedan detras de los que llegaron antes.
        NodoCliente actual = frente;
        NodoCliente anterior = null;
        while (actual != null && actual.getCliente().getPrioridad() >= cliente.getPrioridad()) {
            anterior = actual;
            actual = actual.getSiguiente();
        }
        anterior.setSiguiente(nuevo);
        nuevo.setSiguiente(actual);
        if (actual == null) {
            fondo = nuevo;
        }
    }

    // Atiende al cliente del frente
    public Cliente atender() {
        if (estaVacia()) {
            return null;
        }
        Cliente atendido = frente.getCliente();
        frente = frente.getSiguiente();
        if (frente == null) {
            fondo = null;
        }
        return atendido;
    }

    // Consulta al cliente del frente sin eliminarlo
    public Cliente verFrente() {
        return estaVacia() ? null : frente.getCliente();
    }
}
