public class Nodo {
    private Producto dato;
    private Nodo siguiente;
    private Nodo izquierdo;
    private Nodo derecho;

    public Nodo(Producto dato) {
        this.dato = dato;
    }

    public Producto getDato() { return dato; }
    public void setDato(Producto dato) { this.dato = dato; }

    public Nodo getSiguiente() { return siguiente; }
    public void setSiguiente(Nodo siguiente) { this.siguiente = siguiente; }

    public Nodo getIzquierdo() { return izquierdo; }
    public void setIzquierdo(Nodo izquierdo) { this.izquierdo = izquierdo; }

    public Nodo getDerecho() { return derecho; }
    public void setDerecho(Nodo derecho) { this.derecho = derecho; }
}