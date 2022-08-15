package ec.edu.uce.service;

import java.util.List;

import ec.edu.uce.modelo.DetalleCompra;

public interface IDetalleCompraService {

	void insertarDetalleCompra(DetalleCompra detalleCompra);

	DetalleCompra buscarDetalleCompra(Integer id);

	List<DetalleCompra> buscarTodosDetalleCompra();

	void actualizarDetalleCompra(DetalleCompra detalleCompra);

	void eliminarDetalleCompra(Integer id);

}
