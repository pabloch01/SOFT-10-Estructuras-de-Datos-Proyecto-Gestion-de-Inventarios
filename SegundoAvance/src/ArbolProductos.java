public class ArbolProductos {
    private Nodo raiz;

    public ArbolProductos() {
        this.raiz = null;
    }

    public Nodo getRaiz() { return raiz; }
    public void setRaiz(Nodo raiz) { this.raiz = raiz; }

    public boolean estaVacio() {
        return raiz == null;
    }

    public void insertar(Producto producto) {
        raiz = insertarRec(raiz, producto);
    }

    private Nodo insertarRec(Nodo actual, Producto nuevo) {
        if (actual == null) {
            return new Nodo(nuevo);
        }
        int cmp = nuevo.getNombre().compareToIgnoreCase(actual.getDato().getNombre());
        if (cmp < 0) {
            actual.setIzquierdo(insertarRec(actual.getIzquierdo(), nuevo));
        } else if (cmp > 0) {
            actual.setDerecho(insertarRec(actual.getDerecho(), nuevo));
        } else {
            Producto existente = actual.getDato();
            existente.setCantidad(existente.getCantidad() + nuevo.getCantidad());
        }
        return actual;
    }

    public Producto buscar(String nombre) {
        Nodo actual = raiz;
        while (actual != null) {
            int cmp = nombre.trim().compareToIgnoreCase(actual.getDato().getNombre());
            if (cmp == 0) return actual.getDato();
            actual = (cmp < 0) ? actual.getIzquierdo() : actual.getDerecho();
        }
        return null;
    }

    public void mostrarInventario() {
        if (estaVacio()) {
            System.out.println("El inventario está vacío.");
            return;
        }
        inorden(raiz);
    }

    private void inorden(Nodo nodo) {
        if (nodo == null) return;
        inorden(nodo.getIzquierdo());
        Producto p = nodo.getDato();
        System.out.println("Nombre: " + p.getNombre()
                + " | Precio: " + String.format("%.2f", p.getPrecio())
                + " | Cantidad: " + p.getCantidad());
        inorden(nodo.getDerecho());
    }
}