
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import dal.Accesobd;
import ent.Alumnado;
import ent.Matricula;
import ent.Profesores;

public class Principal {
    private static Accesobd instancia;

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        instancia = new Accesobd();

        instancia.ejecutarCrearTableAlumnado();
        instancia.ejecutarCrearTableProfesores();
        instancia.ejecutarCrearTableMatricula();

        int opcion = 0;

        do {

            menu();
            opcion = sc.nextInt();
            
            switch (opcion) {
                case 1:
                
                    int opcionAlumno = 0;
                    while (opcionAlumno!=6) {
                    
                        menuAlumnado();
                        opcionAlumno = sc.nextInt();
                        opcionesAlumnado(opcionAlumno);
                    }
                    break;
            
                case 2:
                int opcionProfesorado = 0;

                while (opcionProfesorado != 6) {
                    menuProfesorado();
                    opcionProfesorado = sc.nextInt();
                    opcionesProfesorado(opcionProfesorado);
                }
                    
                    break;

                case 3:
                int opcionMatricula = 0;
                while (opcionMatricula != 6) {
                    menuMatricula();
                    opcionMatricula = sc.nextInt();
                    opcionesMatricula(opcionMatricula);
                }                  
                    break;

                case 4:
                    sc.nextLine();

                    try {
                        String tablaBorrar = "";

                        int opcionTablaBorrar;

                        System.out.println("Que tabla quiere borrar" + "\n1.Alumnado" +"\n2.Profesores" + "\n3.Matricula");
                        
                        opcionTablaBorrar = sc.nextInt();

                        sc.nextLine();

                        switch(opcionTablaBorrar){
                            case 1:
                                tablaBorrar = "Alumnado";
                                break;

                            case 2:
                                tablaBorrar = "Profesores";
                                break;

                            case 3:
                                tablaBorrar = "Matricula";
                                break;
                        }
                    
                        ejecutarDropTable(tablaBorrar);

                        System.out.println("Tabla borrada correctamente\n");

                    } catch (Exception e) {
                        System.out.println("Ha ocurrido un error al borrar la tabla\n");
                        System.out.println(e.getMessage());
                    }
                    
                
                    break;

                case 5:

                    sc.nextLine();

                    try {
                        String datosBorrar= "";
                        int opcionDatosBorrar ;

                        System.out.println("Que tabla quiere borrar" + "\n1.Alumnado" +"\n2.Profesores" + "\n3.Matricula");

                        opcionDatosBorrar = sc.nextInt();

                        sc.nextLine();

                        switch(opcionDatosBorrar){
                            case 1:
                                datosBorrar = "Alumnado";
                                break;

                            case 2:
                                datosBorrar = "Profesores";
                                break;

                            case 3:
                                datosBorrar = "Matricula";
                                break;
                        }
                        
                    
                        ejecutarDeleteTable(datosBorrar);
                        


                        System.out.println("Datos borrados correctamente\n");
                    } catch (Exception e) {
                        System.out.println("Ha ocurrido un error al borrar los datos de la tabla\n");
                    }
                    
                    break;

                case 6:

                    try {
                        ejecutarDropTodasLasTablas();    

                        System.out.println("Todas las tablas borradas con éxito\n");

                    } catch (Exception e) {
                        System.out.println("Ha ocurrido un error al intentar borrar todas las tablas\n");
                    }
                       

                    break;

                case 7:
                    break;
            }
            

        } while (opcion != 7);
    }

    private static void menu(){
            System.out.println("Que entidad desea escoger?");
            System.out.println("1. Alumnado");
            System.out.println("2. Profesorado");
            System.out.println("3. matricula");
            System.out.println("4. Borrar Tabla");
            System.out.println("5. Borrar Todos los datos de una tabla");
            System.out.println("6. Borrar todas las tablas");
            System.out.println("7. Salir");
    }  

    private static void opcionesAlumnado(int opcion) throws Exception {
        
        
        try {
            instancia.abrir(); 
            
            switch (opcion) {
                case 1 :

                    try {
                        List<Alumnado> listaAlumnados = instancia.listar("getAllAlumnados");

                        if(!listaAlumnados.isEmpty() && listaAlumnados != null){
                            for (Alumnado alumnado : listaAlumnados) {
                                System.out.println("Id: " + alumnado.getIdAlumnado() +
                                    "   Nombre: " + alumnado.getNombre() + 
                                "   Apellidos: " + alumnado.getApellidos() + 
                                "   Fecha de nacimiento: " + alumnado.getFechaNac() + "\n");
                            }
                        } else {
                            System.out.println("No se ha encontrado a ningún alumno\n");
                        }
                    } catch (Exception e) {
                        System.out.println("No se ha encontrado a ningún alumno\n");
                    }
                    break;

                case 2:

                    try {
                        System.out.println("Introduzca el ID del alumno que desea buscar");
                        int id = sc.nextInt();
                        Alumnado alumnado = (Alumnado) instancia.obtenerPorId("getAlumnadoById",id);
                        if(alumnado != null){
                            System.out.println("Id: " + alumnado.getIdAlumnado() +
                            "\nNombre: " + alumnado.getNombre() + 
                            "\nApellidos: " + alumnado.getApellidos() + 
                            "\nFecha de nacimiento: " + alumnado.getFechaNac() +"\n");

                        }else {
                            System.out.println("No se ha encontrado a ningun alumno con el ID introducido\n");
                        }
                        
                    } catch (Exception e) {
                        System.out.println("No se ha encontrado a ningún alumno con el ID introducido\n");
                    }
                    break;

                case 3:
                    sc.nextLine();
                    System.out.println("Introduzca el nombre del alumno");
                    String nombre = sc.nextLine();
                    System.out.println("Introduzca el apellido del alumno");
                    String apellido = sc.nextLine();
                    System.out.println("Introduzca la fecha de nacimiento del alumno");
                    String fechaNac = sc.nextLine();
                    Alumnado alumnado = new Alumnado(nombre,apellido,fechaNac);
                    guardar(alumnado);
                    System.out.println("Alumno guardado con éxito\n");
                    break;

                case 4:
                try {
                    String elegirModificar = "";

                    System.out.println("Introduzca el id del alumno a modificar");
                    int idModificar = sc.nextInt();
                    sc.nextLine();

                    Alumnado alumnado2 = (Alumnado) instancia.obtenerPorId("getAlumnadoById",idModificar);

                    System.out.println("Nombre del alumno: " + alumnado2.getNombre());

                    System.out.println("¿Desea modificar este campo?");
                    elegirModificar = sc.nextLine();

                    if(elegirModificar.equalsIgnoreCase("si")){
                        System.out.println("Introduzca el nuevo nombre del alumno");
                        alumnado2.setNombre(sc.nextLine());
                    }

                    System.out.println("Apellidos del alumno: " + alumnado2.getApellidos());

                    System.out.println("¿Desea modificar este campo?");
                    elegirModificar = sc.nextLine();

                    if(elegirModificar.equalsIgnoreCase("si")){
                        System.out.println("Introduzca el nuevo apellido del alumno");
                        alumnado2.setApellidos(sc.nextLine());
                    }
                    
                    System.out.println("Fecha de nacimiento del alumno: " + alumnado2.getFechaNac());

                    System.out.println("¿Desea modificar este campo?");
                    elegirModificar = sc.nextLine();

                    if(elegirModificar.equalsIgnoreCase("si")){
                        System.out.println("Introduzca la nueva fecha de nacimiento del alumno");
                        alumnado2.setFechaNac(sc.nextLine());    
                    }
                    
                    actualizar(alumnado2);
                    System.out.println("Alumno actualizado con éxito\n");

                } catch (Exception e) {
                    System.out.println("Ha habido un error al actualizar el alumno\n");
                }
                    
                    break;

                case 5:
                try {
                    String elegirBorrar = "";

                    System.out.println("Introduzca el id del alumno a borrar");
                    int idBorrar = sc.nextInt();
                    sc.nextLine();

                    Alumnado alumnado3 = (Alumnado) instancia.obtenerPorId("getAlumnadoById",idBorrar);

                    System.out.println("Alumno a borrar: " + "\nNombre:" + alumnado3.getNombre() + "\nApellido:" + alumnado3.getApellidos() + "\nFecha de nacimiento:" + alumnado3.getFechaNac() + "\n");
                    System.out.println("¿Desea borrar este alumno?");
                    elegirBorrar = sc.nextLine();

                    if(elegirBorrar.equalsIgnoreCase("si")){
                        borrar(alumnado3);
                        System.out.println("Alumno borrado con éxito\n");
                    } else {
                        System.out.println("Alumno no borrado\n");
                    }
                } catch (Exception e) {
                    System.out.println("Ha habido un error al borrar el alumno\n");
                    System.out.println(e.getCause());
                }
                    break;

                case 6:
                    break;
                }   
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void opcionesProfesorado(int opcion) throws Exception {
        
        
        try {
            instancia.abrir(); 
            
            switch (opcion) {
                case 1 :

                    try {
                        List<Profesores> listaProfesores = instancia.listar("getAllProfesores");

                        if(!listaProfesores.isEmpty() && listaProfesores != null){
                            for (Profesores profesor : listaProfesores) {
                                System.out.println("Id: " + profesor.getIdProfesor() +
                                    "   Nombre: " + profesor.getNombre() + 
                                "   Apellidos: " + profesor.getApellidos() + 
                                "   Fecha de nacimiento: " + profesor.getFechaNac()+
                                "   Antiguedad:"+ profesor.getAntiguedad() + 
                                 "\n");
                            }
                        } else {
                            System.out.println("No se ha encontrado a ningún profesor\n");
                        }
                    } catch (Exception e) {
                        System.out.println("No se ha encontrado a ningún profesor\n");
                    }
                    break;

                case 2:

                    try {
                        System.out.println("Introduzca el ID del profesor que desea buscar");
                        int id = sc.nextInt();
                        Profesores profesor = (Profesores) instancia.obtenerPorId("getProfesorById",id);
                        if(profesor != null){
                            System.out.println("Id: " + profesor.getIdProfesor() +
                            "\nNombre: " + profesor.getNombre() + 
                            "\nApellidos: " + profesor.getApellidos() + 
                            "\nFecha de nacimiento: " + profesor.getFechaNac() +
                            "\nAntiguedad: " + profesor.getAntiguedad() +
                            "\n");

                        }else {
                            System.out.println("No se ha encontrado a ningun profesor con el ID introducido\n");
                        }
                        
                    } catch (Exception e) {
                        System.out.println("No se ha encontrado a ningún profesor con el ID introducido\n");
                    }
                    break;

                case 3:
                    sc.nextLine();
                    System.out.println("Introduzca el nombre del profesor");
                    String nombre = sc.nextLine();
                    System.out.println("Introduzca el apellido del profesor");
                    String apellido = sc.nextLine();
                    System.out.println("Introduzca la fecha de nacimiento del profesor");
                    String fechaNac = sc.nextLine();
                    System.out.println("Introduzca la antiguedad del profesor");
                    int antiguedad = sc.nextInt();
                    sc.nextLine();
                    Profesores profesor = new Profesores(nombre,apellido,fechaNac,antiguedad);
                    guardar(profesor);
                    System.out.println("Profesor guardado con éxito\n");
                    break;

                case 4:
                try {
                    String elegirModificar = "";

                    System.out.println("Introduzca el id del profesor a modificar");
                    int idModificar = sc.nextInt();
                    sc.nextLine();

                    Profesores profesor2 = (Profesores) instancia.obtenerPorId("getProfesorById",idModificar);

                    System.out.println("Nombre del profesor: " + profesor2.getNombre());

                    System.out.println("¿Desea modificar este campo?");
                    elegirModificar = sc.nextLine();

                    if(elegirModificar.equalsIgnoreCase("si")){
                        System.out.println("Introduzca el nuevo nombre del profesor");
                        profesor2.setNombre(sc.nextLine());
                    }

                    System.out.println("Apellidos del profesor: " + profesor2.getApellidos());

                    System.out.println("¿Desea modificar este campo?");
                    elegirModificar = sc.nextLine();

                    if(elegirModificar.equalsIgnoreCase("si")){
                        System.out.println("Introduzca el nuevo apellido del profesor");
                        profesor2.setApellidos(sc.nextLine());
                    }
                    
                    System.out.println("Fecha de nacimiento del profesor: " + profesor2.getFechaNac());

                    System.out.println("¿Desea modificar este campo?");
                    elegirModificar = sc.nextLine();

                    if(elegirModificar.equalsIgnoreCase("si")){
                        System.out.println("Introduzca la nueva fecha de nacimiento del profesor");
                        profesor2.setFechaNac(sc.nextLine());    
                    }

                    System.out.println("Antiguedad del profesor: " + profesor2.getAntiguedad());

                    System.out.println("¿Desea modificar este campo?");
                    elegirModificar = sc.nextLine();

                    if(elegirModificar.equalsIgnoreCase("si")){
                        System.out.println("Introduzca la nueva antiguedad del profesor");
                        profesor2.setAntiguedad(sc.nextInt());   
                        sc.nextLine(); 
                    }
                    
                    actualizar(profesor2);
                    System.out.println("Profesor actualizado con éxito\n");

                } catch (Exception e) {
                    System.out.println("Ha habido un error al actualizar el profesor\n");
                }
                    
                    break;

                case 5:
                try {
                    String elegirBorrar = "";

                    System.out.println("Introduzca el id del profesor a borrar");
                    int idBorrar = sc.nextInt();
                    sc.nextLine();

                    Profesores profesor3 = (Profesores) instancia.obtenerPorId("getProfesorById",idBorrar);

                    System.out.println("Profesor a borrar: " + 
                    "\nNombre:" + profesor3.getNombre() + 
                    "\nApellido:" + profesor3.getApellidos() + 
                    "\nFecha de nacimiento:" + profesor3.getFechaNac() + 
                    "\nAntiguedad:" + profesor3.getAntiguedad() +
                    "\n");
                    System.out.println("¿Desea borrar este profesor?");
                    elegirBorrar = sc.nextLine();

                    if(elegirBorrar.equalsIgnoreCase("si")){
                        borrar(profesor3);
                        System.out.println("Profesor borrado con éxito\n");
                    } else {
                        System.out.println("Profesor no borrado\n");
                    }
                } catch (Exception e) {
                    System.out.println("Ha habido un error al borrar el profesor\n");
                }
                    break;

                case 6:
                    break;
                }   
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    private static void opcionesMatricula(int opcion) throws Exception {
        switch (opcion) {
            case 1:
                try {
                    instancia.abrir();
                    List<Matricula> listaMatricula = instancia.listar("getAllMatricula");
                    if (!listaMatricula.isEmpty() && listaMatricula != null) {
                        for (Matricula matricula : listaMatricula) {
                            System.out.println("Id: " + matricula.getIdMatricula() +
                                    "   Id Profesor: " + matricula.getIdProfesorado() + 
                                    "   Id Alumno: " + matricula.getIdAlumnado() + 
                                    "   Asignatura: " + matricula.getAsignatura() +
                                    "   Curso: " + matricula.getCurso() + 
                                    "\n");
                        }
                    } else {
                        System.out.println("No se ha encontrado a ninguna matrícula\n");
                    }
                    instancia.cerrar();
                } catch (Exception e) {
                    System.out.println("No se ha encontrado a ninguna matrícula\n");
                    if (instancia != null) instancia.cerrar(); // Asegurar cierre en caso de error
                }
                break;
    
            case 2:
                try {
                    instancia.abrir();
                    System.out.println("Introduzca el ID de la matrícula que desea buscar");
                    int id = sc.nextInt();
                    sc.nextLine(); // Limpiar buffer
                    Matricula matricula = (Matricula) instancia.obtenerPorId("getMatriculaById", id);
                    if (matricula != null) {
                        System.out.println("Id: " + matricula.getIdMatricula() +
                                "   Id Profesor: " + matricula.getIdProfesorado() + 
                                "   Id Alumno: " + matricula.getIdAlumnado() + 
                                "   Asignatura: " + matricula.getAsignatura() +
                                "   Curso: " + matricula.getCurso() + 
                                "\n");
                    } else {
                        System.out.println("No se ha encontrado a ninguna matrícula con el ID introducido\n");
                    }
                    instancia.cerrar();
                } catch (Exception e) {
                    System.out.println("No se ha encontrado a ninguna matrícula con el ID introducido\n");
                    if (instancia != null) instancia.cerrar(); // Asegurar cierre en caso de error
                }
                break;
    
                case 3:
                sc.nextLine();
                int idProfesor = 0;
                int idAlumnado = 0;
                boolean profesorValido = false;
                boolean alumnoValido = false;
            
                // Bucle para el ID del profesor
                while (!profesorValido) {
                    System.out.println("Introduzca el id del profesor");
                    idProfesor = sc.nextInt();
                    sc.nextLine();
                    try {
                        instancia.abrir();
                        if (instancia.verificarExistenciaProfesor(idProfesor)) {
                            profesorValido = true; // Salir del bucle si el profesor existe
                        } else {
                            System.out.println("Error: El profesor con ID " + idProfesor + " no existe. Intente de nuevo.");
                        }
                        instancia.cerrar();
                    } catch (Exception e) {
                        System.out.println("Error al verificar el profesor. Intente de nuevo.");
                        instancia.cerrar();
                    }
                }
            
                // Bucle para el ID del alumno
                while (!alumnoValido) {
                    System.out.println("Introduzca el id del alumnado");
                    idAlumnado = sc.nextInt();
                    sc.nextLine();
                    try {
                        instancia.abrir();
                        if (instancia.verificarExistenciaAlumno(idAlumnado)) {
                            alumnoValido = true; // Salir del bucle si el alumno existe
                        } else {
                            System.out.println("Error: El alumno con ID " + idAlumnado + " no existe. Intente de nuevo.");
                        }
                        instancia.cerrar();
                    } catch (Exception e) {
                        System.out.println("Error al verificar el alumno. Intente de nuevo.");
                        instancia.cerrar();
                    }
                }
            
                // Si llegamos aquí, ambos IDs son válidos, pedimos el resto
                System.out.println("Introduzca la asignatura de la matricula");
                String asignatura = sc.nextLine();
                System.out.println("Introduzca el curso de la matrícula");
                int curso = sc.nextInt();
                sc.nextLine();
            
                // Guardar la matrícula
                instancia.guardarMatricula(idAlumnado, idProfesor, asignatura, curso);
                break;
    
            case 4:
                try {
                    instancia.abrir();
                    String elegirModificar = "";
                    System.out.println("Introduzca el id de la matrícula a modificar");
                    int idModificar = sc.nextInt();
                    sc.nextLine();
    
                    Matricula matricula2 = (Matricula) instancia.obtenerPorId("getMatriculaById", idModificar);
                    if (matricula2 == null) {
                        System.out.println("No se ha encontrado una matrícula con ese ID\n");
                        instancia.cerrar();
                        break;
                    }
    
                    System.out.println("Id del profesor: " + matricula2.getIdProfesorado());
                    System.out.println("¿Desea modificar este campo?");
                    elegirModificar = sc.nextLine();
                    if (elegirModificar.equalsIgnoreCase("si")) {
                        System.out.println("Introduzca el nuevo id del profesor");
                        matricula2.setIdProfesorado(sc.nextInt());
                        sc.nextLine();
                    }
    
                    System.out.println("Id del alumnado: " + matricula2.getIdAlumnado());
                    System.out.println("¿Desea modificar este campo?");
                    elegirModificar = sc.nextLine();
                    if (elegirModificar.equalsIgnoreCase("si")) {
                        System.out.println("Introduzca el nuevo id del alumnado");
                        matricula2.setIdAlumnado(sc.nextInt());
                        sc.nextLine();
                    }
    
                    System.out.println("Asignatura de la matrícula: " + matricula2.getAsignatura());
                    System.out.println("¿Desea modificar este campo?");
                    elegirModificar = sc.nextLine();
                    if (elegirModificar.equalsIgnoreCase("si")) {
                        System.out.println("Introduzca la nueva asignatura de la matrícula");
                        matricula2.setAsignatura(sc.nextLine());
                    }
    
                    System.out.println("Curso de la matrícula: " + matricula2.getCurso());
                    System.out.println("¿Desea modificar este campo?");
                    elegirModificar = sc.nextLine();
                    if (elegirModificar.equalsIgnoreCase("si")) {
                        System.out.println("Introduzca el nuevo curso de la matrícula");
                        matricula2.setCurso(sc.nextInt());
                        sc.nextLine();
                    }
    
                    actualizar(matricula2);
                    System.out.println("Matrícula actualizada con éxito\n");
                    instancia.cerrar();
                } catch (Exception e) {
                    System.out.println("Ha habido un error al actualizar la matrícula\n");
                    if (instancia != null) instancia.cerrar(); // Asegurar cierre en caso de error
                }
                break;
    
            case 5:
                try {
                    instancia.abrir();
                    String elegirBorrar = "";
                    System.out.println("Introduzca el id de la matrícula a borrar");
                    int idBorrar = sc.nextInt();
                    sc.nextLine();
    
                    Matricula matricula3 = (Matricula) instancia.obtenerPorId("getMatriculaById", idBorrar);
                    if (matricula3 == null) {
                        System.out.println("No se ha encontrado una matrícula con ese ID\n");
                        instancia.cerrar();
                        break;
                    }
    
                    System.out.println("Id: " + matricula3.getIdMatricula() +
                            "   Id Profesor: " + matricula3.getIdProfesorado() + 
                            "   Id Alumno: " + matricula3.getIdAlumnado() + 
                            "   Asignatura: " + matricula3.getAsignatura() +
                            "   Curso: " + matricula3.getCurso() + 
                            "\n");
                    System.out.println("¿Desea borrar esta matrícula?");
                    elegirBorrar = sc.nextLine();
    
                    if (elegirBorrar.equalsIgnoreCase("si")) {
                        borrar(matricula3);
                        System.out.println("Matrícula borrada con éxito\n");
                    } else {
                        System.out.println("Matrícula no borrada\n");
                    }
                    instancia.cerrar();
                } catch (Exception e) {
                    System.out.println("Ha habido un error al borrar la matrícula\n");
                    if (instancia != null) instancia.cerrar(); // Asegurar cierre en caso de error
                }
                break;
    
            case 6:
                break;
        }
    }
    
    private static void menuAlumnado(){

        System.out.println("Que le gustaría hacer en la entidad ALUMNADO?");
        System.out.println("1.Ver el listado completo de alumnado");
        System.out.println("2.Buscar alumnado por ID");
        System.out.println("3.Guardar un alumno");
        System.out.println("4.Modificar un alumno");
        System.out.println("5.Borrar un alumno");
        System.out.println("6.Volver al menú principal");
    }

    private static void menuProfesorado(){
        System.out.println("Que le gustaría hacer en la entidad PROFESORADO?");
        System.out.println("1.Ver el listado completo de profesorado");
        System.out.println("2.Buscar profesorado por ID");
        System.out.println("3.Guardar un profesorado");
        System.out.println("4.Modificar un profesorado");
        System.out.println("5.Borrar un profesorado");
        System.out.println("6.Volver al menú principal");
    }

    public static void menuMatricula(){

        System.out.println("Que le gustará hacer en la entidad MATRICULA?");
        System.out.println("1.Ver el listado completo de matriculas");
        System.out.println("2.Buscar matricula por ID");
        System.out.println("3.Guardar una matricula");
        System.out.println("4.Modificar una matricula");
        System.out.println("5.Borrar una matricula");
        System.out.println("6.Volver al menú principal");

    }

    private static void guardar(Object cosa) throws Exception {
        instancia.abrir();
        int id = (int) instancia.guardar(cosa);
        // System.out.println("El id generado es: " + id);
        instancia.cerrar();
    }

    private static void actualizar(Object cosa) throws Exception {
        instancia.abrir();
        instancia.actualizar(cosa);
        instancia.cerrar();
    }

    private static void borrar(Object cosa) throws Exception {
        instancia.abrir();
        instancia.borrar(cosa);
        instancia.cerrar();
    }


    private static void ejecutarDropTable(String entidad) throws Exception{
        instancia.abrir();

        instancia.ejecutarDropTable(entidad);

        instancia.cerrar();


    }
    
     private static void ejecutarDeleteTable(String entidad) throws Exception{
         instancia.abrir();

        instancia.ejecutarDeleteTable(entidad);

        instancia.cerrar();
     }

    

    private static void ejecutarDropTodasLasTablas() throws Exception{
        instancia.abrir();

        instancia.borrarTodasLasTablasConDependencias();

        instancia.cerrar();


    }

}
