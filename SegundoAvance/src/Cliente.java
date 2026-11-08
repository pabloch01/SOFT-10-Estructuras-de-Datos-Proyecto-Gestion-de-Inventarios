/**
 * Representa a un cliente de la tienda.
 * Cada cliente tiene un nombre, una prioridad de atención (1 a 3)
 * y un carrito de compras implementado como ListaProductos.
 *
 * Prioridades: 1 = básico, 2 = afiliado, 3 = premium.
 */
public class Cliente {
    public static final int PRIORIDAD_MINIMA = 1;
    public static final int PRIORIDAD_MAXIMA = 3;

    private String nombre;
    private int prioridad;
    private ListaProductos carrito;

    /**
     * @param nombre    nombre del cliente (no puede ser vacío)
     * @param prioridad entero entre 1 y 3
     * @throws IllegalArgumentException si el nombre es vacío o la prioridad está fuera de rango
     */
    public Cliente(String nombre, int prioridad) {
        setNombre(nombre);
        setPrioridad(prioridad);
        this.carrito = new ListaProductos();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        if (prioridad < PRIORIDAD_MINIMA || prioridad > PRIORIDAD_MAXIMA) {
            throw new IllegalArgumentException(
                    "La prioridad debe estar entre " + PRIORIDAD_MINIMA + " y " + PRIORIDAD_MAXIMA + ".");
        }
        this.prioridad = prioridad;
    }

    public ListaProductos getCarrito() {
        return carrito;
    }

    public void setCarrito(ListaProductos carrito) {
        this.carrito = (carrito == null) ? new ListaProductos() : carrito;
    }

    /** @return descripción textual de la prioridad (Básico, Afiliado o Premium). */
    public String getTipo() {
        switch (prioridad) {
            case 3:  return "Premium";
            case 2:  return "Afiliado";
            default: return "Básico";
        }
    }

    @Override
    public String toString() {
        return "Cliente: " + nombre + " | Prioridad: " + prioridad + " (" + getTipo() + ")";
    }
}
