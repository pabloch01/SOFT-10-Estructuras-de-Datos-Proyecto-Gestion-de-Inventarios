import java.time.LocalDate;
import java.util.ArrayList;
public class Producto {


    // Atributos
    private String nombre;
    private double precio;
    private String categoria;
    private LocalDate fechaVencimiento;          // null si el producto no vence
    private int cantidad;
    private ArrayList<String> listaImagenes;

    // Constructores

    public Producto(String nombre, double precio, String categoria,
                    LocalDate fechaVencimiento, int cantidad) {
        setNombre(nombre);
        setPrecio(precio);
        setCategoria(categoria);
        this.fechaVencimiento = fechaVencimiento;
        setCantidad(cantidad);
        this.listaImagenes = new ArrayList<>();
    }


    public Producto(String nombre, double precio, String categoria, int cantidad) {
        this(nombre, precio, categoria, null, cantidad);
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public ArrayList<String> getListaImagenes() {
        return listaImagenes;
    }

    // Setters

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public void setPrecio(double precio) {
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
        this.precio = precio;
    }

    public void setCategoria(String categoria) {
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException("La categoría no puede estar vacía.");
        }
        this.categoria = categoria.trim();
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public void setCantidad(int cantidad) {
        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa.");
        }
        this.cantidad = cantidad;
    }

    // Manejo de imágenes

    public boolean agregarImagen(String ruta) {
        if (ruta == null || ruta.trim().isEmpty()) {
            return false;
        }
        ruta = ruta.trim();
        if (listaImagenes.contains(ruta)) {
            return false;
        }
        return listaImagenes.add(ruta);
    }

    public boolean quitarImagen(String ruta) {
        if (ruta == null) {
            return false;
        }
        return listaImagenes.remove(ruta.trim());
    }

    // Utilidades
    public double getCostoTotal() {
        return precio * cantidad;
    }

    @Override
    public String toString() {
        String vencimiento = (fechaVencimiento == null)
                ? "No aplica"
                : fechaVencimiento.toString();
        String imagenes = listaImagenes.isEmpty()
                ? "Sin imágenes"
                : String.join(", ", listaImagenes);

        return "Producto: " + nombre
                + " | Precio: " + String.format("%.2f", precio)
                + " | Categoría: " + categoria
                + " | Vencimiento: " + vencimiento
                + " | Cantidad: " + cantidad
                + " | Imágenes: " + imagenes;
    }
}