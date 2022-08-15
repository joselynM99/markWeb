package ec.edu.uce.service;

import java.util.List;

import ec.edu.uce.modelo.DetalleVenta;

public interface IDetalleVentaService {

	void insertarDetalleVenta(DetalleVenta detalleVenta);

	DetalleVenta buscarDetalleVenta(Integer id);

	List<DetalleVenta> buscarTodosDetalleVenta();

	void actualizarVenta(DetalleVenta detalleVenta);

	void eliminarDetalleVenta(Integer id);

}
