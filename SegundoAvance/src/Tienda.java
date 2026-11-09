public class Tienda {

    //Atributos
    private ArbolProductos inventario;
    private ColaClientes colaClientes;

    //Constructor
    public Tienda(){
        inventario = new ArbolProductos();
        colaClientes = new ColaClientes();
    }

    //Getters
    public ArbolProductos getInventario(){
        return inventario;
    }

    public ColaClientes getColaClientes(){
        return colaClientes;
    }

    //Setters

    public void setInventario(ArbolProductos inventario){
        if (inventario == null) this.inventario = new ArbolProductos();
        else this.inventario = inventario;
    }

    public void setColaClientes(ColaClientes colaClientes){
        if (colaClientes == null) this.colaClientes = new ColaClientes();
        else this.colaClientes = colaClientes;
    }

    //Operaciones
    public void agregarProducto(Producto producto){
        if (producto == null){
            System.out.println("No se puede agregar un producto nulo.");
            return;
        }
        inventario.insertar(producto);
    }

    public boolean agregarAlCarrito(Cliente cliente, String nombre, int cantidad){
        if (cliente == null || nombre == null || cantidad <= 0) return false;

        Producto producto = inventario.buscar(nombre);
        if (producto == null || cantidad > producto.getCantidad()) return false;

        Producto enCarrito = cliente.getCarrito().buscar(producto.getNombre());
        if (enCarrito != null){
            enCarrito.setCantidad(enCarrito.getCantidad() + cantidad);
        }
        else {
            Producto copia = new Producto(producto.getNombre(), producto.getPrecio(), producto.getCategoria(),
                                          producto.getFechaVencimiento(), cantidad);
            cliente.getCarrito().insertarFin(copia);
        }

        producto.setCantidad(producto.getCantidad() - cantidad);
        return true;
    }

    public void registrarCliente(Cliente cliente){
        if (cliente == null){
            System.out.println("No se puede registrar un cliente nulo.");
            return;
        }
        colaClientes.encolar(cliente);
    }

    public void atenderSiguiente(){
        if (colaClientes.estaVacia()){
            System.out.println("No hay clientes en espera.");
            return;
        }

        Cliente cliente = colaClientes.atender();
        System.out.println("\n========== FACTURA ==========");
        System.out.println(cliente);
        cliente.getCarrito().reporteCostos();
        System.out.println("=============================");
    }

    public void mostrarInventario(){
        inventario.mostrarInventario();
    }

    public void mostrarCola(){
        colaClientes.mostrarCola();
    }
}