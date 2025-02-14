package dal;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
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
        transaction = sesion.beginTransaction();
    }

    
    /**
     * Cierra la conexión con la base de datos. Debes llamar a
     * este método cuando hayas terminado de hacer operaciones
     * con la base de datos.
     * 
     * Si se produce alguna excepción al intentar guardar los
     * cambios, se hace un rollback de la transacción.
     */
    public void cerrar(){
        try {
            transaction.commit();
        }catch(Exception e){
            transaction.rollback();
        }
        sf.close();
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
        return sesion.save(cosa);
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
        sesion.delete(cosa);
    }

    
    /**
     * Actualiza un objeto en la base de datos. El objeto debe ser
     * persistible por Hibernate y ya existir en la base de datos.
     * @param cosa objeto a actualizar.
     */
    public void actualizar(Object cosa) {
        sesion.update(cosa);
    }

    public void ejecutarDropTable(String nombreTabla) {
        String hql = "DROP TABLE " + nombreTabla;
        System.out.println("Ejecutando: " + hql);
        sesion.createNativeQuery(hql).executeUpdate();
        sesion.flush();
    }

    public void borrarTodasLasTablasConDependencias() {
        try {
            // Borrar primero las entidades que dependen de otras (por ejemplo, Matricula depende de Alumnado)
            ejecutarDropTable("Matricula");  // Primero, borrar registros de la tabla "Matricula"
            ejecutarDropTable("Alumnado");   // Luego, borrar registros de la tabla "Alumnado"
            ejecutarDropTable("Profesores");   // Finalmente, borrar registros de la tabla "Profesor"
            
            // Asegurarse de que los cambios se escriban en la base de datos
            sesion.flush();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al borrar todas las tablas con dependencias", e);
        }
    }

    public void ejecutarDeleteTable(String entidad) {
        String hql = "DELETE FROM " + entidad;
        sesion.createQuery(hql).executeUpdate();
        sesion.flush();
    }

    public void ejecutarCrearTableAlumnado() {
        String sql = "CREATE TABLE Alumnado" + " ("
                    + "idAlumnado INT PRIMARY KEY, "
                    + "nombre VARCHAR(255), "
                    + "apellidos VARCHAR(255), "
                    + "fechaNac DATE)";
        sesion.createQuery(sql).executeUpdate();
        sesion.flush();
    }

    public void ejecutarCrearTableProfesores() {
        String sql = "CREATE TABLE Profesores" + " ("
                    + "idProfesor INT AUTO_INCREMENT PRIMARY KEY, "
                    + "nombre VARCHAR(255), "
                    + "apellidos VARCHAR(255), "
                    + "fechaNac DATE, "
                    + "antiguedad INT)";
        sesion.createQuery(sql).executeUpdate();
        sesion.flush();
    }

    public void ejecutarCrearTableMatricula() {
        String sql = "CREATE TABLE Matricula" + " ("
                    + "idMatricula INT AUTO_INCREMENT PRIMARY KEY, "
                    + "idProfesorado INT, "
                    + "idAlumnado INT, "
                    + "asignatura VARCHAR(255), "
                    + "curso INT)";
        sesion.createQuery(sql).executeUpdate();
        sesion.flush();
    }
    
    
    

    

}
