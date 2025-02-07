
import java.util.List;
import java.util.Scanner;

import dal.Accesobd;
import ent.Alumnado;
import ent.Matricula;
import ent.Profesores;

public class Principal {
    private static Accesobd instancia;

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        instancia = new Accesobd();

        int opcion = 0;

        do {

            menu();
            opcion = sc.nextInt();
            
            switch (opcion) {
                case 1:
                    menuAlumnado();
                    opcion = sc.nextInt();
                    opcionesAlumnado(opcion);
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
                                    "\nNombre: " + alumnado.getNombre() + 
                                "\nApellidos: " + alumnado.getApellidos() + 
                                "\nFecha de nacimiento: " + alumnado.getFechaNac());
                            }
                        } else {
                            System.out.println("No se ha encontrado a ningún alumno");
                        }
                    } catch (Exception e) {
                        System.out.println("No se ha encontrado a ningún alumno");
                    }
                    break;

                case 2:
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

    

    



    
}
