package tion.ua.es;

import java.util.Date;

public class Periodico extends Publicacion{
	
	private Date fecha;
	
	public Periodico(Date fecha) {
		super();
		this.fecha = fecha;
	}
	
	public Periodico(String titulo, String autor, Date fecha) {
		super(titulo,autor);
		this.fecha = fecha;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
