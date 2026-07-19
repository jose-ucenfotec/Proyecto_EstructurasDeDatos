package com.inventario.estructuras;

import com.inventario.modelo.Producto;

// Arbol binario de busqueda que almacena el inventario de la Tienda.

public class ArbolProductos {

    // Atributos
    private NodoArbol raiz;

    // Constructor
    public ArbolProductos() {
        this.raiz = null;
    }

    public NodoArbol getRaiz() {
        return raiz;
    }

    public boolean estaVacia() {
        return raiz == null;
    }

    // Busqueda iterativa por llave (nombre del producto)
    public NodoArbol buscar(String llave) {
        NodoArbol temp = raiz;
        while (temp != null) {
            int comparacion = llave.compareToIgnoreCase(temp.getLlave());
            if (comparacion == 0) return temp;
            temp = (comparacion < 0) ? temp.getIzquierdo() : temp.getDerecho();
        }
        return null;
    }

    // Insercion iterativa manteniendo la propiedad de ABB
    public void insertar(Producto producto) {
        NodoArbol nuevo = new NodoArbol(producto);
        if (estaVacia()) {
            raiz = nuevo;
            return;
        }
        NodoArbol temp = raiz;
        NodoArbol padre = null;
        int comparacion = 0;
        while (temp != null) {
            padre = temp;
            comparacion = producto.getNombre().compareToIgnoreCase(temp.getLlave());
            temp = (comparacion < 0) ? temp.getIzquierdo() : temp.getDerecho();
        }
        if (comparacion < 0) padre.setIzquierdo(nuevo);
        else padre.setDerecho(nuevo);
    }

    // Devuelve el nodo eliminado o null si no existe.
    public NodoArbol eliminar(String llave) {
        NodoArbol objetivo = buscar(llave);
        if (objetivo == null) return null;
        raiz = eliminarRecursivo(raiz, llave);
        return objetivo;
    }

    private NodoArbol eliminarRecursivo(NodoArbol actual, String llave) {
        if (actual == null) return null;

        int comparacion = llave.compareToIgnoreCase(actual.getLlave());
        if (comparacion < 0) {
            actual.setIzquierdo(eliminarRecursivo(actual.getIzquierdo(), llave));
        } else if (comparacion > 0) {
            actual.setDerecho(eliminarRecursivo(actual.getDerecho(), llave));
        } else {
            if (actual.getIzquierdo() == null) return actual.getDerecho();
            if (actual.getDerecho() == null) return actual.getIzquierdo();

            NodoArbol sucesor = minimo(actual.getDerecho());
            NodoArbol reemplazo = new NodoArbol(sucesor.getProducto());
            reemplazo.setIzquierdo(actual.getIzquierdo());
            reemplazo.setDerecho(eliminarRecursivo(actual.getDerecho(), sucesor.getLlave()));
            return reemplazo;
        }
        return actual;
    }

    // Menor nodo de un subarbol (mas a la izquierda)
    private NodoArbol minimo(NodoArbol nodo) {
        while (nodo.getIzquierdo() != null) nodo = nodo.getIzquierdo();
        return nodo;
    }

    // Muestra el inventario en orden alfabetico (recorrido en orden)
    public void mostrarInventario() {
        if (estaVacia()) {
            System.out.println("El inventario esta vacio.\n");
            return;
        }
        System.out.println("===== Inventario de la tienda =====");
        recorrerEnOrden(raiz);
        System.out.println("===================================\n");
    }

    private void recorrerEnOrden(NodoArbol nodo) {
        if (nodo == null) return;
        recorrerEnOrden(nodo.getIzquierdo());
        System.out.println(nodo.getProducto());
        System.out.println("- - - - - - - - - - - - - - - -");
        recorrerEnOrden(nodo.getDerecho());
    }

    // Reporte de costos recorriendo el arbol en orden
    public void reporteCostos() {
        if (estaVacia()) {
            System.out.println("El inventario esta vacio.\n");
            return;
        }
        System.out.println("===== Reporte de costos del inventario =====");
        double[] costoAcumulado = {0};
        reporteEnOrden(raiz, costoAcumulado);
        System.out.printf("Costo total acumulado del inventario: %.2f%n", costoAcumulado[0]);
        System.out.println("=============================================\n");
    }

    private void reporteEnOrden(NodoArbol nodo, double[] costoAcumulado) {
        if (nodo == null) return;
        reporteEnOrden(nodo.getIzquierdo(), costoAcumulado);
        Producto p = nodo.getProducto();
        double costoProducto = p.calcularCostoTotal();
        costoAcumulado[0] += costoProducto;
        System.out.printf("%s -> Cantidad: %d x Precio: %.2f = Costo total: %.2f%n",
                p.getNombre(), p.getCantidad(), p.getPrecio(), costoProducto);
        reporteEnOrden(nodo.getDerecho(), costoAcumulado);
    }
}
