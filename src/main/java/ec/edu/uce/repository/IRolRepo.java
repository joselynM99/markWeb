package ec.edu.uce.repository;

import ec.edu.uce.modelo.Rol;

public interface IRolRepo {

	void insertar(Rol rol);

	Rol buscar(Integer id);

	Rol buscarNombre(String nombre);

}
