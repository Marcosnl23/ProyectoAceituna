package com.iesvdc.proyecto.aceituna.interfaz;

import com.iesvdc.proyecto.aceituna.conexion.FactoriaConexion;
import com.iesvdc.proyecto.aceituna.entidades.*;
import com.iesvdc.proyecto.aceituna.serviciosimpl.*;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Menu {
    Connection conn = FactoriaConexion.getConnection();
    private CuadrillaDAOimpl cuadrillaDAO=new CuadrillaDAOimpl(conn);
    private OlivarDAOimpl olivarDAO=new OlivarDAOimpl(conn);
    private AlmazaraDAOimpl almazaraDAO=new AlmazaraDAOimpl(conn);
    private TrabajadorDAOimpl trabajadorDAO=new TrabajadorDAOimpl(conn);
    private ProduccionDAOimpl produccionDAO=new ProduccionDAOimpl(conn);

    public Menu() {
        // Constructor vacío
    }


    public void iniciar() {
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                System.out.println("Seleccione una opción:");
                System.out.println("1. Mostrar los trabajadores de una determinada cuadrilla.");
                System.out.println("2. Mostrar las cuadrillas que supervisa un determinado trabajador.");
                System.out.println("3. Mostrar los olivares donde trabaja una determinada cuadrilla.");
                System.out.println("4. Mostrar las cuadrillas que trabajan en un determinado olivar.");
                System.out.println("5. Mostrar las almazaras donde lleva aceituna una determinada cuadrilla.");
                System.out.println("6. Mostrar la producción en una fecha concreta, de una cuadrilla concreta en una almazara concreta.");
                System.out.println("7. Mostrar la producción hasta una determinada fecha, de una determinada almazara.");
                System.out.println("8. Mostrar la producción hasta una determinada fecha, de un determinado olivar.");
                System.out.println("9. Mostrar la producción hasta una determinada fecha, de una cuadrilla determinada.");
                System.out.println("0. Salir.");

                int opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        mostrarTrabajadoresDeCuadrilla();
                        break;
                    case 2:
                        mostrarCuadrillasSupervisadasPorTrabajador();
                        break;
                    case 3:
                        mostrarOlivaresDeCuadrilla();
                        break;
                    case 4:
                        mostrarCuadrillasDeOlivar();
                        break;
                    case 5:
                        mostrarAlmazarasDeCuadrilla();
                        break;
                    case 6:
                        mostrarProduccionEnFecha();
                        break;
                    case 7:
                        mostarProduccionHastaFechaAlmazara();
                        break;
                    case 8:
                        mostrarProduccionHastaFechaOlivar();
                        break;
                    case 9:
                        mostrarProduccionHastaFechaCuadrilla();
                        break;
                    case 0:
                        System.out.println("Saliendo...");
                        return;
                    default:
                        System.out.println("Opción no válida.");
                }
            }
        } finally {
            scanner.close();
        }
    }

    public void mostrarTrabajadoresDeCuadrilla() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce el ID de la cuadrilla: ");
        int cuadrillaId = scanner.nextInt();

        try {
            List<Trabajador> trabajadores = trabajadorDAO.obtenerTrabajadoresPorCuadrilla(cuadrillaId);

            if (trabajadores.isEmpty()) {
                System.out.println("No hay trabajadores asociados a esta cuadrilla.");
            } else {
                System.out.println("Trabajadores en la cuadrilla con ID " + cuadrillaId + ":");
                for (Trabajador trabajador : trabajadores) {
                    System.out.println("ID: " + trabajador.getId() +
                            ", Nombre: " + trabajador.getNombre() +
                            ", Edad: " + trabajador.getEdad() +
                            ", Puesto: " + trabajador.getPuesto() +
                            ", Salario: " + trabajador.getSalario());
                }
            }
        } catch (Exception e) {
            System.out.println("Error al obtener los trabajadores de la cuadrilla: " + e.getMessage());
        }
    }

    public void mostrarCuadrillasSupervisadasPorTrabajador() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce el ID del trabajador (supervisor): ");
        int trabajadorId = scanner.nextInt();
        scanner.nextLine();

        try {
            // Obtenemos las cuadrillas que supervisa el trabajador con el ID dado
            List<Cuadrilla> cuadrillas = cuadrillaDAO.obtenerCuadrillasPorSupervisor(trabajadorId);

            if (cuadrillas.isEmpty()) {
                System.out.println("Este trabajador no supervisa ninguna cuadrilla.");
            } else {
                System.out.println("Cuadrillas supervisadas por el trabajador con ID " + trabajadorId + ":");
                for (Cuadrilla cuadrilla : cuadrillas) {
                    System.out.println(
                            "Nombre de Cuadrilla: " + cuadrilla.getNombre());
                }
            }
        } catch (Exception e) {
            System.out.println("Error al obtener las cuadrillas supervisadas: " + e.getMessage());
        }
    }

    public void mostrarOlivaresDeCuadrilla() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce el ID de la cuadrilla: ");
        int cuadrillaId = scanner.nextInt();
        scanner.nextLine();

        try {
            // Obtenemos los olivares donde trabaja la cuadrilla con el ID dado
            List<Olivar> olivares = olivarDAO.obtenerOlivaresPorCuadrilla(cuadrillaId);

            if (olivares.isEmpty()) {
                System.out.println("Esta cuadrilla no trabaja en ningún olivar.");
            } else {
                System.out.println("Olivares donde trabaja la cuadrilla con ID " + cuadrillaId + ":");
                for (Olivar olivar : olivares) {
                    System.out.println(
                            "Ubicación de Olivar: " + olivar.getUbicacion()+ ", Hectareas: " + olivar.getHectareas() + ", Produccion Anual: " + olivar.getProduccionAnual());

                }
            }
        } catch (Exception e) {
            System.out.println("Error al obtener los olivares de la cuadrilla: " + e.getMessage());
        }
    }

    public void mostrarCuadrillasDeOlivar() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce el ID del olivar: ");
        int olivarId = scanner.nextInt();
        scanner.nextLine();

        try {
            // Obtenemos las cuadrillas que trabajan en el olivar con el ID dado
            List<Cuadrilla> cuadrillas = olivarDAO.obtenerCuadrillasPorOlivar(olivarId);

            if (cuadrillas.isEmpty()) {
                System.out.println("Ninguna cuadrilla trabaja en este olivar.");
            } else {
                System.out.println("Cuadrillas que trabajan en el olivar con ID " + olivarId + ":");
                for (Cuadrilla cuadrilla : cuadrillas) {
                    System.out.println(
                            "Nombre de Cuadrilla: " + cuadrilla.getNombre());
                }
            }
        } catch (Exception e) {
            System.out.println("Error al obtener las cuadrillas del olivar: " + e.getMessage());
        }
    }

    public void mostrarAlmazarasDeCuadrilla() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce el ID de la cuadrilla: ");
        int cuadrillaId = scanner.nextInt();
        scanner.nextLine();

        try {
            // Obtenemos las almazaras donde lleva aceituna la cuadrilla con el ID dado
            List<Almazara> almazaras = almazaraDAO.obtenerAlmazarasPorCuadrilla(cuadrillaId);

            if (almazaras.isEmpty()) {
                System.out.println("Esta cuadrilla no lleva aceituna a ninguna almazara.");
            } else {
                System.out.println("Almazaras donde lleva aceituna la cuadrilla con ID " + cuadrillaId + ":");
                for (Almazara almazara : almazaras) {
                    System.out.println(
                            "Nombre de Almazara: " + almazara.getNombre());
                }
            }
        } catch (Exception e) {
            System.out.println("Error al obtener las almazaras de la cuadrilla: " + e.getMessage());
        }
    }

    public void mostrarProduccionEnFecha() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce el ID de la cuadrilla: ");
        int cuadrillaId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Introduce el ID de la almazara: ");
        int almazaraId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Introduce la fecha (yyyy-mm-dd): ");
        String fechaStr = scanner.nextLine();

        // Convertir la fecha a LocalDate
        LocalDate fecha;
        try {
            fecha = LocalDate.parse(fechaStr);
        } catch (DateTimeParseException e) {
            System.out.println("Error: La fecha proporcionada no tiene el formato correcto (yyyy-mm-dd).");
            return; // Salir del método si la fecha es inválida
        }

        try {
            // Obtenemos la lista de producciones en la fecha dada
            List<Produccion> producciones = produccionDAO.obtenerProduccionEnFecha(cuadrillaId, almazaraId, fecha);

            if (producciones.isEmpty()) {
                System.out.println("No se encontraron producciones para la cuadrilla " + cuadrillaId +
                        " en la almazara " + almazaraId + " en la fecha " + fecha);
            } else {
                for (Produccion produccion : producciones) {
                    // Mostrar los resultados de la producción
                    System.out.println("ID: " + produccion.getId() +
                            ", Cuadrilla: " + produccion.getCuadrilla_id().getNombre() +
                            ", Olivar: " + produccion.getOlivar_id().getUbicacion() +
                            ", Almazara: " + produccion.getAlmazara_id().getNombre() +
                            ", Fecha: " + produccion.getFecha() +
                            ", Cantidad: " + produccion.getCantidadRecogida() + " kg");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al obtener la producción: " + e.getMessage());
        }
    }

    public void mostarProduccionHastaFechaAlmazara() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce el ID de la almazara: ");
        int almazaraId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Introduce la fecha (yyyy-mm-dd): ");
        String fechaStr = scanner.nextLine();

        // Convertir la fecha a LocalDate
        LocalDate fecha;
        try {
            fecha = LocalDate.parse(fechaStr);  // Convertir la cadena a LocalDate
        } catch (DateTimeParseException e) {
            System.out.println("Error: La fecha proporcionada no tiene el formato correcto (yyyy-mm-dd).");
            return; // Salir del método si la fecha es inválida
        }

        try {
            // Obtenemos la lista de producciones hasta la fecha dada
            List<Produccion> producciones = produccionDAO.obtenerProduccionHastaFechaAlmazara(almazaraId, fecha);

            if (producciones.isEmpty()) {
                System.out.println("No se encontraron producciones para la almazara " + almazaraId + " hasta la fecha " + fecha);
            } else {
                for (Produccion produccion : producciones) {
                    // Mostrar los resultados de la producción
                    System.out.println("ID: " + produccion.getId() +
                            ", Cuadrilla: " + produccion.getCuadrilla_id().getNombre() +
                            ", Olivar: " + produccion.getOlivar_id().getUbicacion() +
                            ", Almazara: " + produccion.getAlmazara_id().getNombre() +
                            ", Fecha: " + produccion.getFecha() +
                            ", Cantidad: " + produccion.getCantidadRecogida() + " kg");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al obtener la producción: " + e.getMessage());
        }
    }

    public void mostrarProduccionHastaFechaOlivar() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce el ID del olivar: ");
        int olivarId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Introduce la fecha (yyyy-mm-dd): ");
        String fechaStr = scanner.nextLine();

        // Convertir la fecha a LocalDate
        LocalDate fecha;
        try {
            fecha = LocalDate.parse(fechaStr);  // Convertir la cadena a LocalDate
        } catch (DateTimeParseException e) {
            System.out.println("Error: La fecha proporcionada no tiene el formato correcto (yyyy-mm-dd).");
            return; // Salir del método si la fecha es inválida
        }

        try {
            // Obtenemos la lista de producciones hasta la fecha dada
            List<Produccion> producciones = produccionDAO.obtenerProduccionHastaFechaOlivar(olivarId, fecha);

            if (producciones.isEmpty()) {
                System.out.println("No se encontraron producciones para el olivar " + olivarId + " hasta la fecha " + fecha);
            } else {
                for (Produccion produccion : producciones) {
                    // Mostrar los resultados de la producción
                    System.out.println("ID: " + produccion.getId() +
                            ", Cuadrilla: " + produccion.getCuadrilla_id().getNombre() +
                            ", Olivar: " + produccion.getOlivar_id().getUbicacion() +
                            ", Almazara: " + produccion.getAlmazara_id().getNombre() +
                            ", Fecha: " + produccion.getFecha() +
                            ", Cantidad: " + produccion.getCantidadRecogida() + " kg");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al obtener la producción: " + e.getMessage());
        }
    }

    public void mostrarProduccionHastaFechaCuadrilla() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce el ID de la cuadrilla: ");
        int cuadrillaId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Introduce la fecha (yyyy-mm-dd): ");
        String fechaStr = scanner.nextLine();

        // Convertir la fecha a LocalDate
        LocalDate fecha;
        try {
            fecha = LocalDate.parse(fechaStr);  // Convertir la cadena a LocalDate
        } catch (DateTimeParseException e) {
            System.out.println("Error: La fecha proporcionada no tiene el formato correcto (yyyy-mm-dd).");
            return; // Salir del método si la fecha es inválida
        }

        try {
            // Obtenemos la lista de producciones hasta la fecha dada
            List<Produccion> producciones = produccionDAO.obtenerProduccionHastaFechaCuadrilla(cuadrillaId, fecha);

            if (producciones.isEmpty()) {
                System.out.println("No se encontraron producciones para la cuadrilla " + cuadrillaId + " hasta la fecha " + fecha);
            } else {
                for (Produccion produccion : producciones) {
                    // Mostrar los resultados de la producción
                    System.out.println("ID: " + produccion.getId() +
                            ", Cuadrilla: " + produccion.getCuadrilla_id().getNombre() +
                            ", Olivar: " + produccion.getOlivar_id().getUbicacion() +
                            ", Almazara: " + produccion.getAlmazara_id().getNombre() +
                            ", Fecha: " + produccion.getFecha() +
                            ", Cantidad: " + produccion.getCantidadRecogida() + " kg");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al obtener la producción: " + e.getMessage());
        }
    }

}
