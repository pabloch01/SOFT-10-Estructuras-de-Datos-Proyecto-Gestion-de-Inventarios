import java.time.LocalDate;

public class ListaProductos {
    private Nodo primero;

    public ListaProductos() {
        primero = null;
    }

    public boolean estaVacia() {
        return primero == null;
    }

    public void insertarInicio(Producto p) {
        Nodo nuevo = new Nodo(p);
        nuevo.setSiguiente(primero);
        primero = nuevo;
    }

    public void insertarFin(Producto p) {
        Nodo nuevo = new Nodo(p);
        if (estaVacia()) {
            primero = nuevo;
        } else {
            Nodo actual = primero;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevo);
        }
    }

    public Producto buscar(String nombre) {
        Nodo actual = primero;
        while (actual != null) {
            if (actual.getDato().getNombre().equalsIgnoreCase(nombre.trim())) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    public boolean modificar(String nombreActual, String nuevoNombre, Double nuevoPrecio,
                             String nuevaCategoria, boolean cambiarFecha,
                             LocalDate nuevaFecha, Integer nuevaCantidad) {
        Producto producto = buscar(nombreActual);
        if (producto == null) {
            return false;
        }
        if (nuevoNombre != null)    producto.setNombre(nuevoNombre);
        if (nuevoPrecio != null)    producto.setPrecio(nuevoPrecio);
        if (nuevaCategoria != null) producto.setCategoria(nuevaCategoria);
        if (cambiarFecha)           producto.setFechaVencimiento(nuevaFecha);
        if (nuevaCantidad != null)  producto.setCantidad(nuevaCantidad);
        return true;
    }

    public boolean agregarImagenAProducto(String nombre, String rutaImagen) {
        Producto producto = buscar(nombre);
        if (producto == null) {
            return false;
        }
        return producto.agregarImagen(rutaImagen);
    }

    public boolean eliminar(String nombre) {
        if (estaVacia()) {
            return false;
        }

        if (primero.getDato().getNombre().equalsIgnoreCase(nombre.trim())) {
            primero = primero.getSiguiente();
            return true;
        }

        Nodo anterior = primero;
        Nodo actual = primero.getSiguiente();
        while (actual != null) {
            if (actual.getDato().getNombre().equalsIgnoreCase(nombre.trim())) {
                anterior.setSiguiente(actual.getSiguiente());
                return true;
            }
            anterior = actual;
            actual = actual.getSiguiente();
        }
        return false;
    }

    public void reporteCostos() {
        if (estaVacia()) {
            System.out.println("La lista esta vacia; no hay productos para reportar.");
            return;
        }
        Nodo actual = primero;
        double total = 0;
        System.out.println("\n--- REPORTE DE COSTOS ---");
        while (actual != null) {
            Producto p = actual.getDato();
            double costo = p.getCostoTotal();
            System.out.printf("%s | Cantidad: %d | Costo: %.2f%n", p.getNombre(), p.getCantidad(), costo);
            total += costo;
            actual = actual.getSiguiente();
        }
        System.out.printf("Costo total acumulado: %.2f%n", total);
    }

    public void mostrarLista() {
        if (estaVacia()) {
            System.out.println("La lista esta vacia.");
            return;
        }
        Nodo actual = primero;
        while (actual != null) {
            System.out.println(actual.getDato());
            actual = actual.getSiguiente();
        }
    }
}
