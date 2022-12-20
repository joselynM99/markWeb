package ec.edu.uce.repository;

import ec.edu.uce.modelo.Usuario;

public interface IUserRepo {

	void insertar(Usuario usuario);

	void actualizar(Usuario usuario);

	Usuario buscar(Integer id);

	void borrar(Integer id);
	
	Usuario validarContrasenia(String nombre);
}
