package ent;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@NamedQueries({
    @NamedQuery(
        name="getAllMatricula",
        query="from Matricula"
    ),
    @NamedQuery(
        name="getMatriculaById",
        query="from Matricula where idMatricula = :id"
    ),
})

@Entity
@Table(name = "Matricula")
public class Matricula implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMatricula")
	private int idMatricula;

    @Column(name = "idProfesorado")
	private int idProfesorado;
	
    @Column(name = "idAlumnado")
	private int idAlumnado;
	
    @Column(name = "asignatura")
	private String asignatura;
	
    @Column(name = "curso")
	private int curso;
	
	public Matricula() {
		
	}
	
	public Matricula(int idMatricula,int idProfesorado,int idAlumnado,String asignatura,int curso) {
		if(idMatricula > 0) {
			this.idMatricula = idMatricula;
		}
		
		if(idProfesorado > 0) {
			this.idProfesorado = idProfesorado;
		}
		
		if(idAlumnado > 0) {
			this.idAlumnado = idAlumnado;
		}
		
		if(asignatura != null && !asignatura.isEmpty()) {
			this.asignatura = asignatura;
		}
		
		if(curso > 0) {
			this.curso = curso;
		}
		
		
		
	}

	public Matricula(int idProfesorado,int idAlumnado,String asignatura,int curso) {
		
		
		if(idProfesorado > 0) {
			this.idProfesorado = idProfesorado;
		}
		
		if(idAlumnado > 0) {
			this.idAlumnado = idAlumnado;
		}
		
		if(asignatura != null && !asignatura.isEmpty()) {
			this.asignatura = asignatura;
		}
		
		if(curso > 0) {
			this.curso = curso;
		}
		
		
		
	}

	public int getIdMatricula() {
		return idMatricula;
	}

	public void setIdMatricula(int idMatricula) {
		if(idMatricula > 0) {
			this.idMatricula = idMatricula;
		}
	}

	public int getIdProfesorado() {
		return idProfesorado;
	}

	public void setIdProfesorado(int idProfesorado) {
		if(idProfesorado > 0) {
			this.idProfesorado = idProfesorado;
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

	public String getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(String asignatura) {
		if(asignatura != null && !asignatura.isEmpty()) {
			this.asignatura = asignatura;
		}
	}

	public int getCurso() {
		return curso;
	}

	public void setCurso(int curso) {
		if(curso > 0) {
			this.curso = curso;
		}
	}
}
