package com.mycompany.holamundo_3periodo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HolaMundo_3Periodo {

    public static void main(String[] args) throws SQLException {

        System.out.println("=== Operaciones CRUD en tabla Alumnos ===");

        // Esta linea de codigo ayuda con la conexion
        
        Connection cn = ConexionDB.getConexion();

        if (cn == null) {
            System.out.println("No se puede establecer la conexión");
            return;
        }

        // Declara los objetos de SQL
        PreparedStatement psInsertar = null;
        PreparedStatement psSeleccionar = null;
        PreparedStatement psActualizar = null;
        PreparedStatement psEliminar = null;
        ResultSet rs = null;

        // Scanner para el menu
        Scanner sc = new Scanner(System.in);

        try {

            int opcion;

            do {

                System.out.println("\n============CRUD============");
                System.out.println("1. Insertar");
                System.out.println("2. Consultar");
                System.out.println("3. Actualizar");
                System.out.println("4. Eliminar");
                System.out.println("5. Salir");
                System.out.println("====================================");
                System.out.print("Ingrese una de las opciones: ");

                opcion = sc.nextInt();
                sc.nextLine();

                switch (opcion) {

                  
                    case 1 -> {
                        System.out.println("\n--- INSERTAR ALUMNO ---");

                        System.out.print("Ingrese el nombre: ");
                        String nombre = sc.nextLine();

                        System.out.print("Ingrese el correo: ");
                        String correo = sc.nextLine();

                        String sqlInsert =
                                "INSERT INTO Alumnos(nombre, correo) VALUES(?,?)";

                        psInsertar = cn.prepareStatement(sqlInsert);

                        psInsertar.setString(1, nombre);
                        psInsertar.setString(2, correo);

                        int filasAfectadas =
                                psInsertar.executeUpdate();

                        if (filasAfectadas > 0) {
                            System.out.println("¡Alumno insertado con exito!");
                        }

                        psInsertar.close();
                        psInsertar = null;
                    }

                    
                    case 2 -> {
                        System.out.println(
                                "\n--- LISTA DE ALUMNOS ---"
                        );

                        String sqlSelect =
                                "SELECT id, nombre, correo FROM Alumnos";

                        psSeleccionar =
                                cn.prepareStatement(sqlSelect);

                        rs = psSeleccionar.executeQuery();

                        System.out.println(
                                "\n=================================================="
                        );
                        System.out.println(
                                " ID   | Nombre               | Correo"
                        );
                        System.out.println(
                                "=================================================="
                        );

                        while (rs.next()) {

                            int id = rs.getInt("id");
                            String nombreAlumno =
                                    rs.getString("nombre");
                            String correoAlumno =
                                    rs.getString("correo");

                            System.out.printf(
                                    "%-4d | %-20s | %s\n",
                                    id,
                                    nombreAlumno,
                                    correoAlumno
                            );
                        }

                        System.out.println(
                                "=================================================="
                        );

                        rs.close();
                        rs = null;

                        psSeleccionar.close();
                        psSeleccionar = null;
                    }

                  
                    case 3 -> {
                        System.out.println(
                                "\n--- ACTUALIZAR ALUMNO ---"
                        );

                        System.out.print(
                                "Ingrese el ID del alumno: "
                        );
                        int idActualizar = sc.nextInt();
                        sc.nextLine();

                        System.out.print(
                                "Ingrese el nuevo nombre: "
                        );
                        String nuevoNombre = sc.nextLine();

                        System.out.print(
                                "Ingrese el nuevo correo: "
                        );
                        String nuevoCorreo = sc.nextLine();

                        String sqlActualizar =
                                "UPDATE Alumnos "
                                + "SET nombre = ?, correo = ? "
                                + "WHERE id = ?";

                        psActualizar =
                                cn.prepareStatement(sqlActualizar);

                        psActualizar.setString(1, nuevoNombre);
                        psActualizar.setString(2, nuevoCorreo);
                        psActualizar.setInt(3, idActualizar);

                        int filasActualizadas =
                                psActualizar.executeUpdate();

                        if (filasActualizadas > 0) {
                            System.out.println(
                                    "¡Alumno actualizado con exito!"
                            );
                        } else {
                            System.out.println(
                                    "No se encontro un alumno con ese ID."
                            );
                        }

                        psActualizar.close();
                        psActualizar = null;
                    }
                    case 4 -> {
                        System.out.println(
                                "\n--- ELIMINAR ALUMNO ---"
                        );

                        System.out.print(
                                "Ingrese el ID del alumno a eliminar: "
                        );
                        int idEliminar = sc.nextInt();
                        sc.nextLine();

                        String sqlEliminar =
                                "DELETE FROM Alumnos WHERE id = ?";

                        psEliminar =
                                cn.prepareStatement(sqlEliminar);

                        psEliminar.setInt(1, idEliminar);

                        int filasEliminadas =
                                psEliminar.executeUpdate();

                        if (filasEliminadas > 0) {
                            System.out.println(
                                    "¡Alumno eliminado con exito!"
                            );
                        } else {
                            System.out.println(
                                    "No se encontro un alumno con ese ID."
                            );
                        }

                        psEliminar.close();
                        psEliminar = null;
                    }

                    
                    case 5 -> System.out.println(
                                "\nSaliendo del programa..."
                        );

                   
                    default -> System.out.println(
                                "\nOpcion no valida."
                        );
                }
              
                
            } while (opcion != 5);

        } catch (SQLException e) {

            System.err.println(
                    "Ocurrio un error con la base de datos: "
                    + e.getMessage()
            );

        } finally {

            // Cerrar ResultSet
            if (rs != null) {
                rs.close();
            }

            // Cerrar PreparedStatements
            if (psInsertar != null) {
                psInsertar.close();
            }

            if (psSeleccionar != null) {
                psSeleccionar.close();
            }

            if (psActualizar != null) {
                psActualizar.close();
            }

            if (psEliminar != null) {
                psEliminar.close();
            }

            // Cerrar Scanner
            sc.close();

            // Cerrar conexion
            ConexionDB.cerrarConexion();
        }
    }
}

