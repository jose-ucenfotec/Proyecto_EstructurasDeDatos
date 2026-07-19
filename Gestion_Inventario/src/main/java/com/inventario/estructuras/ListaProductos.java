package com.inventario.estructuras;

import com.inventario.modelo.Producto;

// Lista enlazada simple de productos.
public class ListaProductos {

    // Atributos
    private NodoProducto primero;

    // Constructor
    public ListaProductos() {
        primero = null;
    }

    // Devuelve el primer nodo para poder recorrer la lista externamente
    public NodoProducto getPrimero() {
        return primero;
    }

    // Operaciones
    public boolean estaVacia() {
        return primero == null;
    }

    public void insertarInicio(Producto producto) {
        NodoProducto nodo = new NodoProducto(producto);
        nodo.setSiguiente(primero);
        primero = nodo;
    }

    public void insertarFinal(Producto producto) {
        NodoProducto nodo = new NodoProducto(producto);
        if (estaVacia()) {
            primero = nodo;
            return;
        }
        NodoProducto temp = primero;
        while (temp.getSiguiente() != null) temp = temp.getSiguiente();
        temp.setSiguiente(nodo);
    }

    public NodoProducto buscar(String nombre) {
        if (estaVacia()) {
            System.out.println("La lista de productos esta vacia.\n");
            return null;
        }
        NodoProducto temp = primero;
        while (temp != null) {
            if (temp.getProducto().getNombre().equalsIgnoreCase(nombre)) return temp;
            temp = temp.getSiguiente();
        }
        System.out.println("No se encontro un producto con el nombre indicado.\n");
        return null;
    }

    public Producto modificar(String nombre) {
        NodoProducto nodo = buscar(nombre);
        return (nodo == null) ? null : nodo.getProducto();
    }

    public NodoProducto eliminar(String nombre) {
        if (estaVacia()) {
            System.out.println("La lista de productos esta vacia.\n");
            return null;
        }
        NodoProducto temp = primero;
        NodoProducto anterior = null;
        while (temp != null && !temp.getProducto().getNombre().equalsIgnoreCase(nombre)) {
            anterior = temp;
            temp = temp.getSiguiente();
        }
        if (temp == null) {
            System.out.println("No se encontro un producto con el nombre indicado.\n");
            return null;
        }
        if (anterior == null) {
            primero = temp.getSiguiente();
        } else {
            anterior.setSiguiente(temp.getSiguiente());
        }
        return temp;
    }

    public void mostrar() {
        if (estaVacia()) {
            System.out.println("La lista de productos esta vacia.\n");
            return;
        }
        NodoProducto temp = primero;
        int posicion = 1;
        while (temp != null) {
            System.out.println("--- Producto " + posicion + " ---");
            System.out.println(temp);
            temp = temp.getSiguiente();
            posicion++;
        }
    }

    // Recorre la lista e imprime el costo total de cada producto y el acumulado
    public void generarReporteCostos() {
        if (estaVacia()) {
            System.out.println("La lista de productos esta vacia.\n");
            return;
        }
        NodoProducto temp = primero;
        double costoAcumulado = 0;
        while (temp != null) {
            Producto producto = temp.getProducto();
            double costoProducto = producto.calcularCostoTotal();
            costoAcumulado += costoProducto;
            System.out.printf("%s -> Cantidad: %d x Precio: %.2f = Costo total: %.2f%n",
                    producto.getNombre(), producto.getCantidad(), producto.getPrecio(), costoProducto);
            temp = temp.getSiguiente();
        }
        System.out.printf("Costo total acumulado de la lista: %.2f%n", costoAcumulado);
    }
}
