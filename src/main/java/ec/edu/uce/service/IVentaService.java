package ec.edu.uce.service;

import java.util.List;

import ec.edu.uce.modelo.Compra;
import ec.edu.uce.modelo.Venta;

public interface IVentaService {

	void insertarVenta(Venta venta);

	Venta buscarVenta(Integer id);

	List<Venta> buscarTodosVenta();

	void actualizarVenta(Venta venta);

	void eliminarVenta(Integer id);

}
