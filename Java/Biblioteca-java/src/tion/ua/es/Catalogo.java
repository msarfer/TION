package tion.ua.es;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Catalogo {

	private List<Publicacion> catalogo;

	public Catalogo(List<Publicacion> catalogo) {
		super();
		this.catalogo = catalogo;
	}
	
	public Catalogo() {
		this.catalogo = new ArrayList<Publicacion>();
	}
	
	public List<Publicacion> getCatalogo() {
		return catalogo;
	}

	public void setCatalogo(List<Publicacion> catalogo) {
		this.catalogo = catalogo;
	}
	
	public void addPublicacion(Publicacion publicacion) {
		this.catalogo.add(publicacion);
	}
	
	public void mostrar() {
		for(Publicacion p: catalogo) {
			System.out.println("Publicacion tipo:" + p.getClass() + " titulo:" + p.getTitulo() + " autor:" + p.getAutor());
		}
	}
	
	public static void main(String[] a) {
		
		Catalogo catalogo = new Catalogo();
		
		Libro libro = new Libro("Open a GLAM Lab", "Gustavo Candela", "	978-84-1302-078-5", "Universidad de Alicante");
		catalogo.addPublicacion(libro);
		
		String fechaStr = "02-01-2021";
		Date fecha;
		try {
			fecha = new SimpleDateFormat("dd-MM-yyyy").parse(fechaStr);
			
			Periodico periodico = new Periodico("El información", "Editorial Prensa Alicantina S.A.U.", fecha);
			catalogo.addPublicacion(periodico);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		catalogo.mostrar();
	}
}
