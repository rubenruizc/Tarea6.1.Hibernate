package ent;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@NamedQueries({

    @NamedQuery(
        name = "getAllProfesores",
        query = "from Profesores"
    ),

    @NamedQuery(
        name = "getProfesorById",
        query = "from Profesores where idProfesor = :id"
    )

})



@Entity
@Table(name = "Profesores")
public class Profesores {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProfesor")
	private int idProfesor;
	
    @Column(name = "nombre")
	private String nombre;
	
    @Column(name = "apellidos")
	private String apellidos;
	
    @Column(name = "fechaNac")
	private String fechaNac;
	
    @Column(name = "antiguedad")
	private int antiguedad;

	// @OneToMany(mappedBy = "profesor", cascade = CascadeType.ALL)
	// private List<Matricula> matricula;

	public Profesores() {
		
	}
	
	public Profesores (int idProfesor, String nombre, String apellidos, String fechaNac, int antiguedad) {
		if (idProfesor > 0) {
			this.idProfesor = idProfesor;
		}
		
		if(nombre != null && !nombre.isEmpty()) {
			this.nombre = nombre;
		}
		
		if(apellidos != null && !apellidos.isEmpty()) {
			this.apellidos = apellidos;
		}
		
		if(fechaNac != null && !fechaNac.isEmpty()) {
			this.fechaNac = fechaNac;
		}
		
		if(antiguedad > 0) {
			this.antiguedad = antiguedad;
		}
	}

	public Profesores (String nombre, String apellidos, String fechaNac, int antiguedad) {
		
		
		if(nombre != null && !nombre.isEmpty()) {
			this.nombre = nombre;
		}
		
		if(apellidos != null && !apellidos.isEmpty()) {
			this.apellidos = apellidos;
		}
		
		if(fechaNac != null && !fechaNac.isEmpty()) {
			this.fechaNac = fechaNac;
		}
		
		if(antiguedad > 0) {
			this.antiguedad = antiguedad;
		}
	}

	public int getIdProfesor() {
		return idProfesor;
	}

	public void setIdProfesor(int idProfesor) {
		if (idProfesor > 0) {
			this.idProfesor = idProfesor;
		}
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		if(nombre != null && !nombre.isEmpty()) {
			this.nombre = nombre;
		}
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		if(apellidos != null && !apellidos.isEmpty()) {
			this.apellidos = apellidos;
		}
	}

	public String getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(String fechaNac) {
		if(fechaNac != null && !fechaNac.isEmpty()) {
			this.fechaNac = fechaNac;
		}
	}

	public int getAntiguedad() {
		return antiguedad;
	}

	public void setAntiguedad(int antiguedad) {
		if(antiguedad > 0) {
			this.antiguedad = antiguedad;
		}
	}
	
}
