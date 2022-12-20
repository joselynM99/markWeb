package ec.edu.uce.modelo;

public enum TipoUsuario {
	ROLE_ADMIN("ADMINISTRADOR"),
	ROLE_COMPRAS("COMPRAS"), 
	ROLE_CCALI("CONTROL DE CALIDAD"), 
	ROLE_PRODU("PRODUCCION"),
	ROLE_VENTAS("VENTAS");

	private String etiqueta;

	TipoUsuario(String label) {
		this.etiqueta = label;
	}

	public String getLabel() {
		return etiqueta;
	}

	public void setLabel(String label) {
		this.etiqueta = label;
	}
}