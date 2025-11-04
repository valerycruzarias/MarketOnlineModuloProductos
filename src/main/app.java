package main;

import config.conexionDB;
import java.sql.*;
import java.util.Scanner;

public class app {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Connection conexion = conexionDB.getConnection();

        if (conexion == null) {
            System.out.println("No se pudo establecer conexión. Finalizando...");
            return;
        }

        int opcion;
        do {
            System.out.println("\n=== MENÚ PRODUCTOS ===");
            System.out.println("1. Insertar producto");
            System.out.println("2. Consultar productos");
            System.out.println("3. Actualizar producto");
            System.out.println("4. Eliminar producto");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            // Leer opción de forma segura
            String input = sc.nextLine();
            try {
                opcion = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                opcion = -1; // opción inválida
            }

            switch (opcion) {
                case 1:
                    insertarProducto(conexion, sc);
                    break;
                case 2:
                    consultarProductos(conexion);
                    break;
                case 3:
                    actualizarProducto(conexion, sc);
                    break;
                case 4:
                    eliminarProducto(conexion, sc);
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 0);

        sc.close();
        try {
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // INSERTAR 
    public static void insertarProducto(Connection conexion, Scanner sc) {
        try {
            System.out.print("Nombre del producto: ");
            String nombre = sc.nextLine();

            double precio;
            while (true) {
                System.out.print("Precio: ");
                try {
                    precio = Double.parseDouble(sc.nextLine().replace(",", "."));
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Ingrese un número válido para el precio.");
                }
            }

            System.out.print("Descripción: ");
            String descripcion = sc.nextLine();

            int stock;
            while (true) {
                System.out.print("Stock: ");
                try {
                    stock = Integer.parseInt(sc.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Ingrese un número entero válido para el stock.");
                }
            }

            int idVendedor;
            while (true) {
                System.out.print("ID del vendedor: ");
                try {
                    idVendedor = Integer.parseInt(sc.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Ingrese un número entero válido para el ID del vendedor.");
                }
            }

            // Verificar que el vendedor exista
            String sqlCheck = "SELECT COUNT(*) FROM vendedores WHERE id_vendedor = ?";
            PreparedStatement psCheck = conexion.prepareStatement(sqlCheck);
            psCheck.setInt(1, idVendedor);
            ResultSet rs = psCheck.executeQuery();
            rs.next();
            if (rs.getInt(1) == 0) {
                System.out.println("❌ Error: El ID del vendedor no existe. Inserta un vendedor válido primero.");
                return;
            }

            // Insertar producto
            String sql = "INSERT INTO productos (nombre_producto, precio, descripcion, stock, id_vendedor) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setDouble(2, precio);
            ps.setString(3, descripcion);
            ps.setInt(4, stock);
            ps.setInt(5, idVendedor);

            ps.executeUpdate();
            System.out.println("✅ Producto insertado correctamente.");
        } catch (SQLException e) {
            System.out.println("❌ Error al insertar: " + e.getMessage());
        }
    }

    // CONSULTAR 
    public static void consultarProductos(Connection conexion) {
        try {
            String sql = "SELECT * FROM productos";
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);

            System.out.println("\n--- Lista de productos ---");
            if (!rs.isBeforeFirst()) {
                System.out.println("No hay productos registrados.");
            } else {
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id_productos") +
                            " | Nombre: " + rs.getString("nombre_producto") +
                            " | Precio: $" + rs.getDouble("precio") +
                            " | Descripción: " + rs.getString("descripcion") +
                            " | Stock: " + rs.getInt("stock") +
                            " | ID Vendedor: " + rs.getInt("id_vendedor"));
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al consultar: " + e.getMessage());
        }
    }

    //ACTUALIZAR 
    public static void actualizarProducto(Connection conexion, Scanner sc) {
        try {
            int id;
            while (true) {
                System.out.print("ID del producto a actualizar: ");
                try {
                    id = Integer.parseInt(sc.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Ingrese un número entero válido para el ID.");
                }
            }

            System.out.print("Nuevo nombre: ");
            String nombre = sc.nextLine();

            double precio;
            while (true) {
                System.out.print("Nuevo precio: ");
                try {
                    precio = Double.parseDouble(sc.nextLine().replace(",", "."));
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Ingrese un número válido para el precio.");
                }
            }

            System.out.print("Nueva descripción: ");
            String descripcion = sc.nextLine();

            int stock;
            while (true) {
                System.out.print("Nuevo stock: ");
                try {
                    stock = Integer.parseInt(sc.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Ingrese un número entero válido para el stock.");
                }
            }

            int idVendedor;
            while (true) {
                System.out.print("ID del vendedor: ");
                try {
                    idVendedor = Integer.parseInt(sc.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Ingrese un número entero válido para el ID del vendedor.");
                }
            }

            // Verificar que el vendedor exista
            String sqlCheck = "SELECT COUNT(*) FROM vendedores WHERE id_vendedor = ?";
            PreparedStatement psCheck = conexion.prepareStatement(sqlCheck);
            psCheck.setInt(1, idVendedor);
            ResultSet rs = psCheck.executeQuery();
            rs.next();
            if (rs.getInt(1) == 0) {
                System.out.println("Error: El ID del vendedor no existe.");
                return;
            }

            String sql = "UPDATE productos SET nombre_producto = ?, precio = ?, descripcion = ?, stock = ?, id_vendedor = ? WHERE id_productos = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setDouble(2, precio);
            ps.setString(3, descripcion);
            ps.setInt(4, stock);
            ps.setInt(5, idVendedor);
            ps.setInt(6, id);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Producto actualizado correctamente.");
            } else {
                System.out.println("No se encontró un producto con ese ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar: " + e.getMessage());
        }
    }

    //ELIMINAR 
    public static void eliminarProducto(Connection conexion, Scanner sc) {
        try {
            int id;
            while (true) {
                System.out.print("ID del producto a eliminar: ");
                try {
                    id = Integer.parseInt(sc.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Ingrese un número entero válido para el ID.");
                }
            }

            String sql = "DELETE FROM productos WHERE id_productos = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);

            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Producto eliminado correctamente.");
            } else {
                System.out.println("No se encontró un producto con ese ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
    }
}
