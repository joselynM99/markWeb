package ec.edu.uce.services;

import ec.edu.uce.modelo.Rol;

public interface IRolService {
	void insertar(Rol rol);

	Rol buscar(Integer id);

	Rol buscarNombre(String nombre);
}
