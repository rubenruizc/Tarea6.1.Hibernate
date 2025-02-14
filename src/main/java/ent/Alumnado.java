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
        name = "getAllAlumnados", 
        query = "from Alumnado"
    ), 
    @NamedQuery(
      name = "getAlumnadoById",
      query = "from Alumnado where idAlumnado = :id"  
    ),
})

@Entity
@Table(name = "Alumnado")
public class Alumnado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdAlumnado")
	private int idAlumnado;
	
    @Column(name = "nombre")
	private String nombre;
	
    @Column(name = "apellidos")
	private String apellidos;
	
    @Column(name = "fechaNac")
	private String fechaNac;

	// @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL)
	// private List<Matricula> matricula;
	
	public Alumnado() {
		
	}
	
	public Alumnado(int idAlumnado,String nombre,String apellidos,String fechaNac) {
		if(idAlumnado > 0) {
			this.idAlumnado = idAlumnado;
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
	}

	public Alumnado(String nombre,String apellidos,String fechaNac){
		if(nombre != null && !nombre.isEmpty()) {
			this.nombre = nombre;
		}
		
		if(apellidos != null && !apellidos.isEmpty()) {
			this.apellidos = apellidos;
		}
		
		if(fechaNac != null && !fechaNac.isEmpty()) {
			this.fechaNac = fechaNac;
		}
	}

	public int getIdAlumnado() {
		return idAlumnado;
	}

	public void setIdAlumnado(int idAlumnado) {
		if(idAlumnado > 0) {
			this.idAlumnado = idAlumnado;
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
	
	
	
}

