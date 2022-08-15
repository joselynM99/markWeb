package ec.edu.uce.repository;

import java.util.List;

import ec.edu.uce.modelo.Proveedor;

public interface IProveedorRepo {
	
	void insertarProveedor(Proveedor proveedor);

	Proveedor buscarProveedor(Integer id);

	List<Proveedor> buscarTodosProveedor();

	void actualizarProducto(Proveedor proveedor);
	
	void eliminarProveedor(Integer id);

	Proveedor buscarProveedorNombre(String nombreEmpresa);


}
