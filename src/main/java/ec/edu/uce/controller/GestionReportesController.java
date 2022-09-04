package ec.edu.uce.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ec.edu.uce.modelo.Compra;
import ec.edu.uce.modelo.CompraTO;
import ec.edu.uce.modelo.Producto;
import ec.edu.uce.modelo.Proveedor;
import ec.edu.uce.modelo.Venta;
import ec.edu.uce.modelo.VentaTO;
import ec.edu.uce.service.ICompraService;
import ec.edu.uce.service.IDetalleCompraService;
import ec.edu.uce.service.IDetalleVentaService;
import ec.edu.uce.service.IProductoService;
import ec.edu.uce.service.IProveedorService;
import ec.edu.uce.service.IVentaService;

@Controller
@RequestMapping("/reportes/")
public class GestionReportesController {

	@Autowired
	private IProductoService productoService;

	@Autowired
	private IProveedorService proveedorService;

	@Autowired
	private ICompraService compraService;

	@Autowired
	private IDetalleCompraService detalleCompraService;

	@Autowired
	private IVentaService ventaService;

	@Autowired
	private IDetalleVentaService detalleVentaService;
	private static final Logger LOG = LoggerFactory.getLogger(GestionInventarioController.class);

	@GetMapping("reporteProductos")
	public String obtenerPaginaReporteProductos(Producto producto, RedirectAttributes redirectAttributes, Model model) {

		model.addAttribute("producto", producto);

		return "reporteProducto";

	}

	@GetMapping("buscarProductos")
	public String buscarProductos(Producto producto, RedirectAttributes redirectAttributes, Model model) {

		List<Producto> productos = this.productoService.buscarProductoPorCategoria(producto.getCategoria());

		model.addAttribute("productos", productos);

		return "reporteProducto";

	}

	@DeleteMapping("borrarProducto/{indice}")
	public String borrarProducto(@PathVariable int indice, Producto producto, HttpServletRequest request, Model model) {
		this.productoService.eliminarProducto(indice);

		List<Producto> productos = this.productoService.buscarProductoPorCategoria(producto.getCategoria());

		model.addAttribute("productos", productos);

		return "reporteProducto";
	}

	@GetMapping("actualizacionProducto/{indice}")
	public String obtenerProductoPorNombre(@PathVariable Integer indice, Producto producto, Model modelo,
			RedirectAttributes redirectAttributes) {

		Producto p = this.productoService.buscarProducto(indice);
		if (p == null || p.getId().equals(null)) {
			redirectAttributes.addFlashAttribute("error", "Producto no encontrado");
			return "redirect:/inventario/actualizarProducto";
		} else {
			modelo.addAttribute("producto", p);
			LOG.info("" + p);

			return "productoActualizar";
		}

	}

	@GetMapping("reporteProveedores")
	public String obtenerPaginaReporteProveedores(Proveedor proveedor, RedirectAttributes redirectAttributes,
			Model model) {

		model.addAttribute("proveedor", proveedor);

		return "reporteProveedor";

	}

	@GetMapping("buscarProveedores")
	public String buscarProveedores(Proveedor proveedor, RedirectAttributes redirectAttributes, Model model) {

		List<Proveedor> proveedores = this.proveedorService.buscarTodosProveedor();

		model.addAttribute("proveedores", proveedores);
		model.addAttribute("proveedor", proveedor);

		return "reporteProveedor";

	}

	@DeleteMapping("borrarProveedor/{indice}")
	public String borrarProducto(@PathVariable Integer indice, Proveedor proveedor, HttpServletRequest request,
			Model model) {
		this.proveedorService.eliminarProveedor(indice);

		List<Proveedor> proveedores = this.proveedorService.buscarTodosProveedor();

		model.addAttribute("proveedores", proveedores);
		model.addAttribute("proveedor", proveedor);

		return "reporteProveedor";
	}

	@GetMapping("actualizacionProveedor/{indice}")
	public String obtenerProductoPorNombre(@PathVariable Integer indice, Proveedor proveedor, Model modelo,
			RedirectAttributes redirectAttributes) {

		Proveedor p = this.proveedorService.buscarProveedor(indice);
		if (p == null || p.getId().equals(null)) {
			redirectAttributes.addFlashAttribute("error", "Proveedor no encontrado");
			return "redirect:/inventario/actualizarProveedor";
		} else {
			modelo.addAttribute("proveedor", p);
			LOG.info("" + p);

			return "proveedorActualizar";
		}

	}

	@GetMapping("reporteVentas")
	public String obtenerPaginaReporteVentas(VentaTO ventaTO, Venta venta, RedirectAttributes redirectAttributes,
			Model model) {

		model.addAttribute("venta", venta);
		model.addAttribute("ventaTO", ventaTO);

		return "reporteVentas";

	}

	@GetMapping("buscarVentas")
	public String buscarVentas(VentaTO ventaTO, Venta venta, RedirectAttributes redirectAttributes, Model model) {

		List<Venta> ventas = this.ventaService.buscarPorFechaTO(ventaTO.getFechaInicio(), ventaTO.getFechaFin());

		model.addAttribute("ventas", ventas);
		model.addAttribute("ventaTO", ventaTO);

		return "reporteVentas";

	}

	@DeleteMapping("borrarVenta/{indice}")
	public String borrarVenta(@PathVariable Integer indice, VentaTO ventaTO, Venta venta, HttpServletRequest request,
			Model model) {
		this.ventaService.eliminarVenta(indice);

		List<Venta> ventas = this.ventaService.buscarPorFechaTO(ventaTO.getFechaInicio(), ventaTO.getFechaFin());

		model.addAttribute("ventas", ventas);
		model.addAttribute("ventaTO", ventaTO);

		return "reporteVentas";
	}

	@GetMapping("reporteCompras")
	public String obtenerPaginaReporteCompras(CompraTO compraTO, Compra compra, RedirectAttributes redirectAttributes,
			Model model) {

		model.addAttribute("compra", compra);
		model.addAttribute("compraTO", compraTO);

		return "reporteCompras";

	}

	@GetMapping("buscarCompras")
	public String buscarCompras(CompraTO compraTO, Compra compra, RedirectAttributes redirectAttributes, Model model) {

		List<Compra> compras = this.compraService.buscarPorFechaTO(compraTO.getFechaInicio(), compraTO.getFechaFin());

		model.addAttribute("compras", compras);
		model.addAttribute("compraTO", compraTO);

		return "reporteCompras";

	}

	@DeleteMapping("borrarCompra/{indice}")
	public String borrarVenta(@PathVariable Integer indice, CompraTO compraTO, Compra compra,
			HttpServletRequest request, Model model) {
		this.compraService.eliminarCompra(indice);

		List<Compra> compras = this.compraService.buscarPorFechaTO(compraTO.getFechaInicio(), compraTO.getFechaFin());

		model.addAttribute("compras", compras);
		model.addAttribute("compraTO", compraTO);

		return "reporteCompras";
	}
}
