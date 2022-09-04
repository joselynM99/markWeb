package ec.edu.uce.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.edu.uce.modelo.Compra;
import ec.edu.uce.modelo.DetalleCompra;
import ec.edu.uce.modelo.DetalleVenta;
import ec.edu.uce.modelo.Producto;
import ec.edu.uce.modelo.Venta;
import ec.edu.uce.repository.IVentaRepo;

@Service
public class VentaServiceImpl implements IVentaService {

	@Autowired
	private IVentaRepo ventaRepo;

	@Autowired
	private IProductoService productoService;

	@Autowired
	private IDetalleVentaService detalleVentaService;

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
		Venta venta = this.ventaRepo.buscarVenta(id);
		List<DetalleVenta> ventas = venta.getDetalles();

		for (DetalleVenta d : ventas) {
			Producto p = d.getProducto();
			p.setCantidad(p.getCantidad() + d.getCantidad());
			this.productoService.actualizarProducto(p);
		}
		this.ventaRepo.eliminarVenta(id);
	}

	@Override
	public BigDecimal calcularValorAPagar(List<DetalleVenta> detalles) {

		BigDecimal total = new BigDecimal(0);
		for (DetalleVenta d : detalles) {
			total = d.getTotal().add(total);
		}

		return total;
	}

	@Override
	@Transactional(value = TxType.REQUIRED)
	public void realizarVenta(List<DetalleVenta> detalles) {

		for (DetalleVenta d : detalles) {
			Producto p = d.getProducto();

			p.setCantidad(p.getCantidad() - d.getCantidad());

			p.setId(p.getId());

			this.productoService.actualizarProducto(p);

			d.setTotal(this.detalleVentaService.calcularValor(d.getCantidad(), p.getValorVenta()));
			this.detalleVentaService.insertarDetalleVenta(d);

		}
		Venta venta = new Venta();
		venta.setDetalles(detalles);
		venta.setFecha(LocalDateTime.now());
		venta.setValorAPagar(calcularValorAPagar(detalles));

		this.insertarVenta(venta);

		for (DetalleVenta d : detalles) {
			d.setVenta(venta);
			this.detalleVentaService.actualizarVenta(d);
		}

	}

	@Override
	public List<Venta> buscarPorFechaTO(LocalDateTime fechaInicio, LocalDateTime fechaFinal) {
		return this.ventaRepo.buscarPorFechaTO(fechaInicio, fechaFinal);
	}

}
