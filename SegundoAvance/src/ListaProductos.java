import java.time.LocalDate;

public class ListaProductos {
    Nodo primero;

    public ListaProductos() {
        primero = null;
    }

    public boolean estaVacia() {
        return primero == null;
    }

    public void insertarInicio(Producto p) {
        Nodo nuevo = new Nodo(p);
        nuevo.siguiente = primero;
        primero = nuevo;
    }

    public void insertarFin(Producto p) {
        Nodo nuevo = new Nodo(p);
        if (estaVacia()) {
            primero = nuevo;
        } else {
            Nodo actual = primero;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
    }

    public Producto buscar(String nombre) {
        Nodo actual = primero;
        while (actual != null) {
            if (actual.dato.getNombre().equalsIgnoreCase(nombre.trim())) {
                return actual.dato;
            }
            actual = actual.siguiente;
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

        if (primero.dato.getNombre().equalsIgnoreCase(nombre.trim())) {
            primero = primero.siguiente;
            return true;
        }

        Nodo anterior = primero;
        Nodo actual = primero.siguiente;
        while (actual != null) {
            if (actual.dato.getNombre().equalsIgnoreCase(nombre.trim())) {
                anterior.siguiente = actual.siguiente;
                return true;
            }
            anterior = actual;
            actual = actual.siguiente;
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
            Producto p = actual.dato;
            double costo = p.getCostoTotal();
            System.out.printf("%s | Cantidad: %d | Costo: %.2f%n", p.getNombre(), p.getCantidad(), costo);
            total += costo;
            actual = actual.siguiente;
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
            System.out.println(actual.dato);
            actual = actual.siguiente;
        }
    }
}
