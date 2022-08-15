package ec.edu.uce.service;

import java.util.List;

import ec.edu.uce.modelo.Compra;

public interface ICompraService {

	void insertarCompra(Compra compra);

	Compra buscarCompra(Integer id);

	List<Compra> buscarTodosCompra();

	void actualizarCompra(Compra compra);

	void eliminarCompra(Integer id);

}
