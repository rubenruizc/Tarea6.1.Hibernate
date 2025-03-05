package dal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.NativeQuery;

import ent.*;

public class Accesobd {

    private SessionFactory sf;
    
    private Session sesion;
    
    
    private Transaction transaction;
    
    
    /**
     * Prepara la conexión con la base de datos. Antes de llamar a
     * cualquiera de los demás métodos, debes llamar a este
     * método para que se configure correctamente la conexión
     * con la base de datos.
     */
    protected void setUp() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // por defecto: hibernate.cfg.xml
                .build();
        try {
            sf = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    
    /**
     * Abre la conexión con la base de datos. Llamar a este método
     * es obligatorio antes de llamar a los dem s m todos. Si
     * falla, lanza una excepción.
     * @throws Exception si se produce un error al abrir la conexi n.
     */
    public void abrir() throws Exception {
        setUp();
        sesion=sf.openSession();
        // transaction = sesion.beginTransaction();
    }

    
    /**
     * Cierra la conexión con la base de datos. Debes llamar a
     * este método cuando hayas terminado de hacer operaciones
     * con la base de datos.
     * 
     * Si se produce alguna excepción al intentar guardar los
     * cambios, se hace un rollback de la transacción.
     */
    public void cerrar() {
        if (transaction != null && transaction.isActive()) {
            try {
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                e.printStackTrace();
            }
        }
        if (sesion != null && sesion.isOpen()) { // ✅ Cerrar la sesión correctamente
            sesion.close();
        }
        if (sf != null) {
            sf.close();
        }
    }
    

    
    /**
     * Guarda un objeto en la base de datos. El objeto se considera
     * "nuevo" si no tiene un ID asignado, y en ese caso se
     * generará un nuevo ID para el objeto.
     * @param cosa objeto a guardar. Debe ser un objeto que sea
     *             persistible por Hibernate.
     * @return el objeto guardado, con su ID asignado.
     */
    public Object guardar(Object cosa) {
        try {
            abrir();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // Abre la conexión
        Transaction tx = sesion.beginTransaction();
        Object resultado = null;
        try {
            resultado = sesion.save(cosa);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback(); // Revertir en caso de error
            }
            e.printStackTrace();
        } finally {
            cerrar(); // Cierra la conexión
        }
        return resultado;
    }
    

    
    /**
     * Recupera la lista de todas las personas de la base de datos.
     * @return la lista de personas.
     */
    public List listar(String queryDeseado) {
        TypedQuery query = sesion.getNamedQuery(queryDeseado);

        return query.getResultList();
    }


    /**
     * Recupera un objeto de la base de datos por su ID.
     * @param entidad nombre de la entidad a recuperar.
     * @param id ID del objeto a recuperar.
     * @return el objeto recuperado.
     */
    public Object obtenerPorId(String queryDeseado,int id) {
        TypedQuery query = sesion.getNamedQuery(queryDeseado).setParameter("id", id);

        return query.getSingleResult();
    }
    
    
    /**
     * Elimina un objeto de la base de datos.
     * @param cosa objeto a eliminar. Debe ser un objeto que sea
     *             persistible por Hibernate y ya exista en la base de datos.
     */

     public void borrar(Object cosa) {
        try {
            abrir();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // Asegurar que la sesión está abierta
        transaction = sesion.beginTransaction();
        
        try {
            cosa = sesion.merge(cosa); // 🔹 Asegurar que el objeto está en la sesión
            sesion.delete(cosa);       // 🔹 Borrar el objeto
            transaction.commit();               // 🔹 Confirmar el borrado
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // 🔹 Revertir en caso de error
            }
            e.printStackTrace();
        } finally {
            cerrar(); // 🔹 Cerrar la conexión
        }
    }
    

    
    /**
     * Actualiza un objeto en la base de datos. El objeto debe ser
     * persistible por Hibernate y ya existir en la base de datos.
     * @param cosa objeto a actualizar.
     */
    public void actualizar(Object cosa) {
        try {
            abrir();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // Asegurar que la sesión está abierta
         transaction = sesion.beginTransaction();
    
        try {
            cosa = sesion.merge(cosa); // 🔹 Asegurar que el objeto está en la sesión
            sesion.update(cosa);       // 🔹 Actualizar el objeto
            transaction.commit();               // 🔹 Confirmar los cambios
            System.out.println("Objeto actualizado correctamente");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // 🔹 Revertir en caso de error
            }
            e.printStackTrace();
        } finally {
            cerrar(); // 🔹 Cerrar la conexión
        }
    }
    

    public void ejecutarDropTable(String nombreTabla) {

        transaction = sesion.beginTransaction();

        try {
            String hql = "DROP TABLE " + nombreTabla;
            System.out.println("Ejecutando: " + hql);
            sesion.createNativeQuery(hql).executeUpdate();
            transaction.commit();
            System.out.println("Tabla borrada correctamente\n");
            sesion.flush();
            
            
        } catch (Exception e) {
            if(transaction != null && transaction.isActive()){
                transaction.rollback();
            }

        }
        
    }

    public void borrarTodasLasTablasConDependencias() {
        transaction = sesion.beginTransaction();

        try {
            // Crear la consulta SQL nativa para borrar la tabla
            String sql = "DROP TABLE IF EXISTS Matricula, Alumnado, Profesores";

            // Ejecutar la consulta SQL nativa
            sesion.createNativeQuery(sql).executeUpdate();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    

    public void ejecutarDeleteTable(String entidad) {
        try {
            abrir(); // Asegurar que la sesión está abierta
            transaction = sesion.beginTransaction(); // Iniciar transacción
    
            String hql = "DELETE FROM " + entidad;
            int filasAfectadas = sesion.createQuery(hql).executeUpdate();
            
            transaction.commit(); // Confirmar los cambios
            System.out.println("Se han eliminado " + filasAfectadas + " registros de la tabla " + entidad);
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback(); // Revertir en caso de error
            }
            System.out.println("Error al eliminar los datos de la tabla " + entidad + ": " + e.getMessage());
            e.printStackTrace();
        } finally {
            cerrar(); // Cerrar la sesión siempre
        }
    }

    public void ejecutarCrearTableAlumnado() {

        if(!tablaExists("Alumnado")){
            try{
                abrir();
                transaction = sesion.beginTransaction();

                String sql = "CREATE TABLE IF NOT EXISTS Alumnado ("
                    + "idAlumnado INT AUTO_INCREMENT PRIMARY KEY, "
                    + "nombre VARCHAR(255), "
                    + "apellidos VARCHAR(255), "
                    + "fechaNac VARCHAR(255));";

                sesion.createNativeQuery(sql).executeUpdate();

                transaction.commit();
                System.out.println("Tabla Alumnado creada");
            } catch(Exception e){
                if(transaction != null && transaction.isActive()){
                    transaction.rollback();
                }

                e.printStackTrace();
            }finally {
                cerrar();
            }
        }
        
    }

    public void ejecutarCrearTableProfesores() {

        if(!tablaExists("Profesores")){
            try{
                abrir();
                transaction = sesion.beginTransaction();

                String sql = "CREATE TABLE IF NOT EXISTS Profesores ("
                    + "idProfesor INT AUTO_INCREMENT PRIMARY KEY, "
                    + "nombre VARCHAR(255), "
                    + "apellidos VARCHAR(255), "
                    + "fechaNac VARCHAR(255), "
                    + "antiguedad INT);";
                sesion.createNativeQuery(sql).executeUpdate();

                transaction.commit();
                System.out.println("Tabla Profesores creada");

            }catch(Exception e){
                if(transaction != null && transaction.isActive()){
                    transaction.rollback();
                }
            }finally{
                cerrar();
            }
        }

    }

    public void ejecutarCrearTableMatricula() {

        if(!tablaExists("Matricula")){
            try{
                abrir();
                transaction = sesion.beginTransaction();

                

                String sql = "CREATE TABLE IF NOT EXISTS Matricula ("
                    + "idMatricula INT AUTO_INCREMENT PRIMARY KEY, "
                    + "idProfesor INT, "
                    + "idAlumnado INT, "
                    + "asignatura VARCHAR(255), "
                    + "curso INT, "
                    + "FOREIGN KEY (idProfesor) REFERENCES Profesores(idProfesor) ON DELETE CASCADE ON UPDATE CASCADE, "
                    + "FOREIGN KEY (idAlumnado) REFERENCES Alumnado(idAlumnado) ON DELETE CASCADE ON UPDATE CASCADE);";

                sesion.createNativeQuery(sql).executeUpdate();
                transaction.commit();
                System.out.println("Tabla Matricula creada");

            }catch(Exception e){
                if(transaction!=null && transaction.isActive()){
                    transaction.rollback();
                }
            }finally{
                cerrar();
            }
        }
    }

    public boolean tablaExists(String nombreTabla){
        boolean existe = false;
        try{
            abrir();

            String sql = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = :nombreTabla";

            NativeQuery <?> query = sesion.createNativeQuery(sql);
            
            query.setParameter("nombreTabla",nombreTabla);

            Number count = (Number) query.getSingleResult();
            
            existe = count.intValue()  > 0;
        
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            cerrar();
        }

        return existe;
    }

    public boolean verificarExistenciaAlumno(int idAlumno) {
        String hql = "SELECT COUNT(a) FROM Alumnado a WHERE a.idAlumnado = :idAlumno";
        Long count = (Long) sesion.createQuery(hql)
                                  .setParameter("idAlumno", idAlumno)
                                  .getSingleResult();
        return count > 0;  // Retorna true si existe, false si no existe
    }
    
    public boolean verificarExistenciaProfesor(int idProfesor) {
        String hql = "SELECT COUNT(p) FROM Profesores p WHERE p.idProfesor = :idProfesor";
        Long count = (Long) sesion.createQuery(hql)
                                  .setParameter("idProfesor", idProfesor)
                                  .getSingleResult();
        return count > 0;  // Retorna true si existe, false si no existe
    }
    
    public void guardarMatricula(int idAlumno, int idProfesor, String asignatura, int curso) {
        try {
            abrir(); // Asegurarse de abrir la conexión
        
            Transaction tx = sesion.beginTransaction();
        
            Matricula matricula = new Matricula(); // Crear nueva matrícula
            matricula.setIdAlumnado(idAlumno);     // Asignar idAlumno
            matricula.setIdProfesorado(idProfesor);   // Asignar idProfesor
            matricula.setAsignatura(asignatura);   // Asignar asignatura
            matricula.setCurso(curso);             // Asignar curso
        
            sesion.save(matricula); // Guardar la matrícula
            tx.commit();            // Confirmar transacción
        
            System.out.println("Matrícula guardada correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            if (sesion.getTransaction().isActive()) {
                sesion.getTransaction().rollback(); // Revertir en caso de error
            }
            System.out.println("Error al guardar la matrícula.\n");
        } finally {
            cerrar(); // Cerrar la sesión
        }
    }

    // Método para obtener matrículas por ID de alumno
    public List<Matricula> obtenerMatriculasPorAlumno(int idAlumnado) {
        try {
            abrir();
            String hql = "FROM Matricula m WHERE m.idAlumnado = :idAlumnado";
            TypedQuery<Matricula> query = sesion.createQuery(hql, Matricula.class);
            query.setParameter("idAlumnado", idAlumnado);
            List<Matricula> matriculas = query.getResultList();
            cerrar();
            return matriculas;
        } catch (Exception e) {
            if (sesion != null && sesion.isOpen()) cerrar();
            return new ArrayList<>(); // Retorna lista vacía si hay error o no hay resultados
        }
    }

    // Método para obtener matrículas por ID de profesor
    public List<Matricula> obtenerMatriculasPorProfesor(int idProfesor) {
        try {
            abrir();
            String hql = "FROM Matricula m WHERE m.idProfesorado = :idProfesor";
            TypedQuery<Matricula> query = sesion.createQuery(hql, Matricula.class);
            query.setParameter("idProfesor", idProfesor);
            List<Matricula> matriculas = query.getResultList();
            cerrar();
            return matriculas;
        } catch (Exception e) {
            if (sesion != null && sesion.isOpen()) cerrar();
            return new ArrayList<>(); // Retorna lista vacía si hay error o no hay resultados
        }
    }
        


}
