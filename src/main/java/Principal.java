
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
                    menuProfesorado();
                    opcion = sc.nextInt();
                    break;

                case 3:
                    menuMatricula();
                    opcion = sc.nextInt();
                    break;

                case 4:
                    break;
            }
            

        } while (opcion != 4);
    }

    private static void menu(){
            System.out.println("Que entidad desea escoger?");
            System.out.println("1. Alumnado");
            System.out.println("2. Profesorado");
            System.out.println("3. Matrícula");
            System.out.println("4. Salir");
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
                            System.out.println("No se ha encontrado a ningún alumno");
                        }
                    } catch (Exception e) {
                        System.out.println("No se ha encontrado a ningún alumno");
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
                            System.out.println("No se ha encontrado a ningun alumno con el ID introducido");
                        }
                        
                    } catch (Exception e) {
                        System.out.println("No se ha encontrado a ningún alumno con el ID introducido");
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
                    System.out.println("Alumno actualizado con éxito");
                    break;

                case 5:
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
                        System.out.println("Alumno borrado con éxito");
                    }
                    break;

                case 6:
                    break;
                }
                    
                    
                    
            
                
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
        System.out.println(id);
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

    

    



    
}
