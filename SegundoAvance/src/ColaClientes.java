/**
 * Cola de prioridad de clientes, implementada con una lista enlazada.
 * Los clientes se encolan siempre al final; al atender se escoge el de
 * mayor prioridad y, en caso de empate, el que esté más cerca del frente.
 */
public class ColaClientes {

    /** Nodo interno de la cola (el Nodo general guarda Productos, no Clientes). */
    private static class NodoCliente {
        private Cliente cliente;
        private NodoCliente siguiente;

        private NodoCliente(Cliente cliente) {
            this.cliente = cliente;
        }

        public Cliente getCliente() { return cliente; }
        public void setCliente(Cliente cliente) { this.cliente = cliente; }

        public NodoCliente getSiguiente() { return siguiente; }
        public void setSiguiente(NodoCliente siguiente) { this.siguiente = siguiente; }
    }

    private NodoCliente frente;
    private NodoCliente fin;
    private int tamano;

    public ColaClientes() {
        this.frente = null;
        this.fin = null;
        this.tamano = 0;
    }

    public boolean estaVacia() {
        return frente == null;
    }

    public int getTamano() {
        return tamano;
    }

    /** Agrega un cliente al final de la cola. */
    public void encolar(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo.");
        }
        NodoCliente nuevo = new NodoCliente(cliente);
        if (estaVacia()) {
            frente = nuevo;
        } else {
            fin.setSiguiente(nuevo);
        }
        fin = nuevo;
        tamano++;
    }

    /**
     * Saca de la cola al cliente con la prioridad más alta.
     * Si hay empate, sale el más cercano al frente.
     *
     * @return el cliente atendido, o null si la cola está vacía
     */
    public Cliente atender() {
        if (estaVacia()) {
            return null;
        }

        // Buscar el mejor candidato (el ">" estricto conserva al más cercano al frente en empates)
        NodoCliente mejor = frente;
        NodoCliente anteriorMejor = null;
        NodoCliente anterior = frente;
        NodoCliente actual = frente.getSiguiente();

        while (actual != null) {
            if (actual.getCliente().getPrioridad() > mejor.getCliente().getPrioridad()) {
                mejor = actual;
                anteriorMejor = anterior;
            }
            anterior = actual;
            actual = actual.getSiguiente();
        }

        // Desenlazar al elegido
        if (anteriorMejor == null) {
            frente = mejor.getSiguiente();
        } else {
            anteriorMejor.setSiguiente(mejor.getSiguiente());
        }
        if (mejor == fin) {
            fin = anteriorMejor;
        }
        tamano--;
        return mejor.getCliente();
    }

    /** Imprime la cola desde el frente hasta el final. */
    public void mostrarCola() {
        if (estaVacia()) {
            System.out.println("No hay clientes en la cola.");
            return;
        }
        System.out.println("--- Cola de clientes (frente -> final) ---");
        NodoCliente actual = frente;
        int posicion = 1;
        while (actual != null) {
            System.out.println(posicion + ". " + actual.getCliente());
            actual = actual.getSiguiente();
            posicion++;
        }
    }
}
