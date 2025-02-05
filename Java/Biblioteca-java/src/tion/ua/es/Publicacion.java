package tion.ua.es;

public class Publicacion {

	protected String titulo;
	protected String autor;
	
	public Publicacion() {
		this.titulo = "";
		this.autor = "";
	}
	
	public Publicacion(String titulo, String autor) {
		super();
		this.titulo = titulo;
		this.autor = autor;
	}
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	
}
