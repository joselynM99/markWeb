package ec.edu.uce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.edu.uce.modelo.Venta;
import ec.edu.uce.repository.IVentaRepo;

@Service
public class VentaServiceImpl implements IVentaService {

	@Autowired
	private IVentaRepo ventaRepo;

	@Override
	public void insertarVenta(Venta venta) {
		this.ventaRepo.insertarVenta(venta);
	}

	@Override
	public Venta buscarVenta(Integer id) {
		return this.ventaRepo.buscarVenta(id);
	}

	@Override
	public List<Venta> buscarTodosVenta() {
		return this.ventaRepo.buscarTodosVenta();
	}

	@Override
	public void actualizarVenta(Venta venta) {
		this.ventaRepo.actualizarVenta(venta);
	}

	@Override
	public void eliminarVenta(Integer id) {
		this.ventaRepo.eliminarVenta(id);
	}

}
