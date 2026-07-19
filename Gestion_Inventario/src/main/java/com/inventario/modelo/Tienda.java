package com.inventario.modelo;

import com.inventario.estructuras.ArbolProductos;
import com.inventario.estructuras.ColaClientes;
import com.inventario.estructuras.NodoArbol;

//Entidad Tienda. Concentra el inventario (ArbolProductos) y la cola de
// atencion de clientes (ColaClientes).
public class Tienda {

    // Atributos
    private final ArbolProductos inventario;
    private final ColaClientes colaClientes;

    // Constructor
    public Tienda() {
        this.inventario = new ArbolProductos();
        this.colaClientes = new ColaClientes();
    }

    public ArbolProductos getInventario() {
        return inventario;
    }

    public ColaClientes getColaClientes() {
        return colaClientes;
    }

    //  Operaciones de inventario

    // Devuelve true si se agrego; false si ya existia un producto con ese nombre
    public boolean agregarProducto(Producto producto) {
        if (inventario.buscar(producto.getNombre()) != null) {
            return false;
        }
        inventario.insertar(producto);
        return true;
    }

    public Producto buscarProducto(String nombre) {
        NodoArbol nodo = inventario.buscar(nombre);
        return (nodo == null) ? null : nodo.getProducto();
    }

    public Producto eliminarProducto(String nombre) {
        NodoArbol nodo = inventario.eliminar(nombre);
        return (nodo == null) ? null : nodo.getProducto();
    }

    public void mostrarInventario() {
        inventario.mostrarInventario();
    }

    public void reporteCostos() {
        inventario.reporteCostos();
    }

    public boolean inventarioVacio() {
        return inventario.estaVacia();
    }

    // Verifica que exista inventario suficiente para la cantidad solicitada
    public boolean verificarInventario(String nombre, int cantidad) {
        Producto producto = buscarProducto(nombre);
        return producto != null && producto.getCantidad() >= cantidad;
    }

    // Descuenta del inventario la cantidad comprada
    public void descontarInventario(String nombre, int cantidad) {
        Producto producto = buscarProducto(nombre);
        if (producto == null) return;
        int nuevoInventario = producto.getCantidad() - cantidad;
        producto.setCantidad(nuevoInventario);
    }

    //  Operaciones de la cola de clientes

    public void agregarClienteCola(Cliente cliente) {
        colaClientes.insertar(cliente);
    }

    public boolean hayClientesEnCola() {
        return !colaClientes.estaVacia();
    }

    public Cliente verProximoCliente() {
        return colaClientes.verFrente();
    }

    public Cliente atenderSiguienteCliente() {
        return colaClientes.atender();
    }

    public int getCantidadClientesEnCola() {
        return colaClientes.getTamanio();
    }
}
