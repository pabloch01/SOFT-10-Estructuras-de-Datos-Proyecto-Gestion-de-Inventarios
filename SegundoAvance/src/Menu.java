import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Menu {
    static Scanner sc = new Scanner(System.in);
    static ListaProductos lista = new ListaProductos();

    public static void main(String[] args) {
        menu();
    }

    static double leerDouble(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Double.parseDouble(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("\nError: escriba un numero decimal.");
            }
        }
    }

    static int leerEntero(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("\nError: escriba un numero entero.");
            }
        }
    }

    static LocalDate leerFecha(String msg) {
        while (true) {
            System.out.print(msg);
            String texto = sc.nextLine().trim();
            if (texto.isEmpty()) {
                return null;
            }
            try {
                return LocalDate.parse(texto);
            } catch (DateTimeParseException e) {
                System.out.println("\nError: use el formato aaaa-mm-dd (ej. 2026-12-31).");
            }
        }
    }

    static Producto pedirProducto() {
        while (true) {
            System.out.print("\nNombre del producto: ");
            String nombre = sc.nextLine();
            double precio = leerDouble("Precio: ");
            System.out.print("Categoria: ");
            String categoria = sc.nextLine();
            LocalDate fecha = leerFecha("Fecha de vencimiento (aaaa-mm-dd, Enter si no aplica): ");
            int cantidad = leerEntero("Cantidad: ");
            try {
                return new Producto(nombre, precio, categoria, fecha, cantidad);
            } catch (IllegalArgumentException e) {
                System.out.println("\nError: " + e.getMessage() + " Intente de nuevo.\n");
            }
        }
    }

    static Double leerDoubleOpcional(String msg) {
        while (true) {
            System.out.print(msg);
            String texto = sc.nextLine().trim();
            if (texto.isEmpty()) {
                return null;
            }
            try {
                double valor = Double.parseDouble(texto);
                if (valor < 0) {
                    System.out.println("\nError: el precio no puede ser negativo.");
                } else {
                    return valor;
                }
            } catch (NumberFormatException e) {
                System.out.println("\nError: escriba un numero decimal.");
            }
        }
    }

    static Integer leerEnteroOpcional(String msg) {
        while (true) {
            System.out.print(msg);
            String texto = sc.nextLine().trim();
            if (texto.isEmpty()) {
                return null;
            }
            try {
                int valor = Integer.parseInt(texto);
                if (valor < 0) {
                    System.out.println("\nError: la cantidad no puede ser negativa.");
                } else {
                    return valor;
                }
            } catch (NumberFormatException e) {
                System.out.println("\nError: escriba un numero entero.");
            }
        }
    }

    static void opcionModificar() {
        System.out.println("\n--- MODIFICAR PRODUCTO ---");
        System.out.print("\nNombre del producto a modificar: ");
        String nombre = sc.nextLine().trim();

        Producto producto = lista.buscar(nombre);
        if (producto == null) {
            System.out.println("\nNo se encontro ningun producto llamado \"" + nombre + "\".");
            return;
        }

        System.out.println("Producto encontrado: " + producto);
        System.out.println("(Presione Enter sin escribir nada para dejar un campo igual)");

        System.out.print("Nuevo nombre [" + producto.getNombre() + "]: ");
        String nuevoNombre = sc.nextLine().trim();

        Double nuevoPrecio = leerDoubleOpcional("Nuevo precio [" + producto.getPrecio() + "]: ");

        System.out.print("Nueva categoria [" + producto.getCategoria() + "]: ");
        String nuevaCategoria = sc.nextLine().trim();

        boolean cambiarFecha = false;
        LocalDate nuevaFecha = null;
        while (true) {
            System.out.print("Nueva fecha de vencimiento (aaaa-mm-dd, '-' = no aplica, Enter = sin cambios): ");
            String texto = sc.nextLine().trim();
            if (texto.isEmpty()) {
                break;
            }
            if (texto.equals("-")) {
                cambiarFecha = true;
                break;
            }
            try {
                nuevaFecha = LocalDate.parse(texto);
                cambiarFecha = true;
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Error: use el formato aaaa-mm-dd (ej. 2026-12-31).");
            }
        }

        Integer nuevaCantidad = leerEnteroOpcional("Nueva cantidad [" + producto.getCantidad() + "]: ");

        lista.modificar(nombre,
                nuevoNombre.isEmpty() ? null : nuevoNombre,
                nuevoPrecio,
                nuevaCategoria.isEmpty() ? null : nuevaCategoria,
                cambiarFecha, nuevaFecha,
                nuevaCantidad);

        System.out.println("Producto modificado correctamente.");
    }

    static void opcionAgregarImagen() {
        System.out.println("\n--- AGREGAR IMAGEN A UN PRODUCTO ---");
        System.out.print("\nNombre del producto: ");
        String nombre = sc.nextLine().trim();

        if (lista.buscar(nombre) == null) {
            System.out.println("No se encontro ningun producto llamado \"" + nombre + "\".");
            return;
        }

        System.out.print("Ruta de la imagen (ej. imagenes/cuaderno.png): ");
        String ruta = sc.nextLine().trim();

        if (lista.agregarImagenAProducto(nombre, ruta)) {
            System.out.println("Imagen agregada correctamente.");
        } else {
            System.out.println("No se agrego: la ruta esta vacia o la imagen ya estaba registrada.");
        }
    }

    static void opcionEliminar() {
        System.out.println("\n--- ELIMINAR PRODUCTO ---");
        if (lista.estaVacia()) {
            System.out.println("\nLa lista esta vacia; no hay productos para eliminar.");
            return;
        }

        System.out.print("Nombre del producto a eliminar: ");
        String nombre = sc.nextLine().trim();

        Producto producto = lista.buscar(nombre);
        if (producto == null) {
            System.out.println("No se encontro ningun producto llamado \"" + nombre + "\".");
            return;
        }

        System.out.println("Producto encontrado: " + producto);
        System.out.print("¿Seguro que desea eliminarlo? (s/n): ");
        String confirmacion = sc.nextLine().trim();

        if (confirmacion.equalsIgnoreCase("s")) {
            lista.eliminar(nombre);
            System.out.println("Producto eliminado correctamente.");
        } else {
            System.out.println("Eliminacion cancelada.");
        }
    }

    static void opcionReporteCostos() {
        lista.reporteCostos();
    }

    static void opcionMostrarLista() {
        System.out.println("\n--- LISTA DE PRODUCTOS ---");
        lista.mostrarLista();
    }

    static void menu() {
        int opcion;
        do {
            System.out.println("\n--- MENU DE GESTION DE INVENTARIO ---\n");
            System.out.println("1. Insertar al inicio");
            System.out.println("2. Insertar al final");
            System.out.println("3. Modificar producto");
            System.out.println("4. Agregar imagen a un producto");
            System.out.println("5. Eliminar producto");
            System.out.println("6. Ver reporte de costos");
            System.out.println("7. Mostrar lista completa");
            System.out.println("0. Salir");
            opcion = leerEntero("\nOpcion: ");

            if (opcion == 1) {
                lista.insertarInicio(pedirProducto());
            } else if (opcion == 2) {
                lista.insertarFin(pedirProducto());
            } else if (opcion == 3) {
                opcionModificar();
            } else if (opcion == 4) {
                opcionAgregarImagen();
            } else if (opcion == 5) {
                opcionEliminar();
            } else if (opcion == 6) {
                opcionReporteCostos();
            } else if (opcion == 7) {
                opcionMostrarLista();
            } else if (opcion != 0) {
                System.out.println("\nOpcion no valida.");
            }
        } while (opcion != 0);
        System.out.println("\nSaliendo del sistema.");
    }
}
