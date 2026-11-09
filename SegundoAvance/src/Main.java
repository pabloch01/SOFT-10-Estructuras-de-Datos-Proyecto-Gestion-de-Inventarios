import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {

    private static Scanner sc = new Scanner(System.in);
    private static Tienda tienda = new Tienda();

    public static void main(String[] args) {
        precargarDatosPrueba();
        menu();
    }

    public static void precargarDatosPrueba() {
        tienda.agregarProducto(new Producto("Arroz", 1200.0, "Granos", LocalDate.of(2027, 12, 31), 50));
        tienda.agregarProducto(new Producto("Frijoles", 950.0, "Granos", LocalDate.of(2027, 10, 15), 40));
        tienda.agregarProducto(new Producto("Leche", 900.0, "Lácteos", LocalDate.of(2026, 11, 20), 30));
    }

    public static void menu() {
        int opcion = -1;
        do {
            System.out.println("\n===== MENU DE GESTIÓN DE INVENTARIO Y VENTAS =====");
            System.out.println("1. Insertar producto al inventario");
            System.out.println("2. Registrar un cliente y llenar su carrito");
            System.out.println("3. Atender al siguiente cliente");
            System.out.println("4. Ver inventario completo (Arbol de Productos)");
            System.out.println("5. Buscar un producto en el inventario");
            System.out.println("6. Ver cola de clientes en espera");
            System.out.println("0. Salir del sistema");

            opcion = leerEntero("\nSeleccione una opción: ");

            switch (opcion) {
                case 1:
                    opcionInsertarProducto();
                    break;
                case 2:
                    opcionRegistrarCliente();
                    break;
                case 3:
                    opcionAtenderCliente();
                    break;
                case 4:
                    tienda.mostrarInventario();
                    break;
                case 5:
                    opcionBuscarProducto();
                    break;
                case 6:
                    tienda.mostrarCola();
                    break;
                case 0:
                    System.out.println("\nSaliendo del sistema de gestión. ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("\n[Error] Opción no válida. Por favor elija un número entre 0 y 6.");
            }
        } while (opcion != 0);
    }

    public static void opcionInsertarProducto() {
        System.out.println("\n--- INSERTAR PRODUCTO AL INVENTARIO ---");
        System.out.print("Nombre del producto: ");
        String nombre = sc.nextLine();
        double precio = leerDouble("Precio: ");
        System.out.print("Categoría: ");
        String categoria = sc.nextLine();
        LocalDate fecha = leerFecha("Fecha de vencimiento (aaaa-mm-dd, presione Enter si no aplica): ");
        int cantidad = leerEntero("Cantidad: ");

        try {
            Producto nuevoProducto = new Producto(nombre, precio, categoria, fecha, cantidad);
            tienda.agregarProducto(nuevoProducto);
            System.out.println("¡Producto procesado e insertado en el inventario exitosamente!");
        } catch (IllegalArgumentException e) {
            System.out.println("\n[Error de validación] " + e.getMessage());
        }
    }

    public static void opcionRegistrarCliente() {
        System.out.println("\n--- REGISTRO DE CLIENTE Y COMPRA ---");

        if (tienda.getInventario().estaVacio()) {
            System.out.println("[Error] El inventario está vacío; no hay productos para agregar al carrito.");
            return;
        }

        String nombreCliente = leerNombre("Nombre del cliente: ");

        int prioridad = 0;
        while (true) {
            prioridad = leerEntero("Prioridad (1: Básico, 2: Afiliado, 3: Premium): ");
            if (prioridad >= Cliente.PRIORIDAD_MINIMA && prioridad <= Cliente.PRIORIDAD_MAXIMA) {
                break;
            }
            System.out.println("[Error] La prioridad debe ser obligatoriamente 1, 2 o 3. Intente de nuevo.");
        }

        Cliente cliente = new Cliente(nombreCliente, prioridad);

        String continuar = "s";
        while (continuar.equalsIgnoreCase("s")) {
            System.out.println("\n--- Inventario Disponible ---");
            tienda.mostrarInventario();

            System.out.print("\nEscriba el nombre exacto del producto que desea llevar: ");
            String nombreProd = sc.nextLine();
            int cantProd = leerCantidad("¿Cuántas unidades desea?: ");

            boolean exito = tienda.agregarAlCarrito(cliente, nombreProd, cantProd);
            if (exito) {
                System.out.println("¡Producto agregado al carrito del cliente con éxito!");
            } else {
                System.out.println("[Error] No se pudo agregar (revise si el nombre es correcto o si hay suficiente stock).");
            }

            System.out.print("¿Desea agregar otro producto al carrito? (s/n): ");
            continuar = sc.nextLine().trim();
        }

        if (cliente.getCarrito().estaVacia()) {
            System.out.println("\n[Aviso] El carrito quedó vacío, por lo que el cliente no fue registrado en la cola.");
            return;
        }

        tienda.registrarCliente(cliente);
        System.out.println("\n¡Cliente registrado y encolado correctamente en el sistema!");
    }

    public static void opcionAtenderCliente() {
        System.out.println("\n--- ATENCIÓN DE CLIENTES ---");
        tienda.atenderSiguiente();
    }

    public static void opcionBuscarProducto() {
        System.out.println("\n--- BUSCAR PRODUCTO EN EL INVENTARIO ---");
        System.out.print("Nombre del producto: ");
        String nombre = sc.nextLine();

        Producto encontrado = tienda.getInventario().buscar(nombre);
        if (encontrado != null) {
            System.out.println("Encontrado: " + encontrado);
        } else {
            System.out.println("[Error] No existe ningún producto llamado \"" + nombre.trim() + "\" en el inventario.");
        }
    }

    static String leerNombre(String msg) {
        while (true) {
            System.out.print(msg);
            String nombre = sc.nextLine().trim();
            if (nombre.matches("\\p{L}+( \\p{L}+)*")) {
                return nombre;
            }
            System.out.println("\nError: el nombre solo puede tener letras y espacios (sin números ni símbolos).");
        }
    }

    static int leerCantidad(String msg) {
        while (true) {
            int cantidad = leerEntero(msg);
            if (cantidad > 0) {
                return cantidad;
            }
            System.out.println("\nError: la cantidad debe ser mayor que 0.");
        }
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
}