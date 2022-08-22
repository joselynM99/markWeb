package ec.edu.uce.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ec.edu.uce.modelo.DetalleVenta;
import ec.edu.uce.modelo.Producto;
import ec.edu.uce.modelo.Venta;
import ec.edu.uce.service.IDetalleVentaService;
import ec.edu.uce.service.IProductoService;
import ec.edu.uce.service.IProveedorService;
import ec.edu.uce.service.IVentaService;

@Controller
@RequestMapping("/ventas/")
public class GestionVentasController {

	@Autowired
	private IProductoService productoService;

	@Autowired
	private IProveedorService proveedorService;

	@Autowired
	private IVentaService ventaService;

	@Autowired
	private IDetalleVentaService detalleVentaService;

	private static final Logger LOG = LoggerFactory.getLogger(GestionVentasController.class);

	@GetMapping("vetanaInicio")
	public String obtenerPaginaVentas(Producto producto, Model model, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {

		List<DetalleVenta> carrito = this.obtenerCarrito(request);
		BigDecimal total = this.ventaService.calcularValorAPagar(carrito);
		model.addAttribute("total", total);
		model.addAttribute("producto", producto);
//		redirectAttributes.addFlashAttribute("producto", producto);
		return "ventas";

	}

	@GetMapping("buscarProducto")
	public String buscarProducto(@ModelAttribute Producto producto, Model model) {

		List<Producto> listaProductos = this.productoService.buscarProductoPorNombre(producto.getNombre());

		model.addAttribute("listaProductos", listaProductos);
		return "ventas";
	}

	private List<DetalleVenta> obtenerCarrito(HttpServletRequest request) {
		List<DetalleVenta> carrito = (List<DetalleVenta>) request.getSession().getAttribute("carrito");
		if (carrito == null) {
			carrito = new ArrayList<>();
		}
		return carrito;
	}

	private void guardarCarrito(List<DetalleVenta> carrito, HttpServletRequest request) {
		request.getSession().setAttribute("carrito", carrito);
	}

	@PostMapping("agregar")
	public String agregarAlCarrito(Producto producto, HttpServletRequest request, RedirectAttributes redirectAttrs) {
		List<DetalleVenta> carrito = this.obtenerCarrito(request);
		Producto productoBuscadoPorCodigo = this.productoService
				.buscarProductoPorCodigoBarras(producto.getCodigoBarras());

		if (productoBuscadoPorCodigo == null) {
			redirectAttrs.addFlashAttribute("mensaje1",
					"El producto con el código " + producto.getCodigoBarras() + " no existe");

			return "redirect:vetanaInicio";
		}

		if (productoBuscadoPorCodigo.getCantidad() <= 0) {
			redirectAttrs.addFlashAttribute("mensaje1", "El producto está agotado");

//			return "redirect:vetanaInicio";
		}

		LOG.info("" + productoBuscadoPorCodigo);

		boolean encontrado = false;
		for (DetalleVenta det : carrito) {
			if (det.getProducto().getCodigoBarras().equals(productoBuscadoPorCodigo.getCodigoBarras())) {
				det.setCantidad(det.getCantidad() + 1);
				det.setTotal(det.getProducto().getValorVenta().multiply(new BigDecimal(det.getCantidad())));
				encontrado = true;

				break;
			}
		}

		if (!encontrado) {
			carrito.add(new DetalleVenta(1,
					this.detalleVentaService.calcularValor(1, productoBuscadoPorCodigo.getValorVenta()),
					productoBuscadoPorCodigo));
		}

		this.guardarCarrito(carrito, request);
		return "redirect:vetanaInicio";
	}

	@PostMapping("borrar/{indice}")
	public String quitarDelCarrito(@PathVariable int indice, Producto producto, HttpServletRequest request,
			Model model) {
		List<DetalleVenta> carrito = this.obtenerCarrito(request);
		if (carrito != null && carrito.size() > 0 && carrito.get(indice) != null) {
			carrito.remove(indice);
			this.guardarCarrito(carrito, request);
		}

		BigDecimal total = this.ventaService.calcularValorAPagar(carrito);
		model.addAttribute("total", total);
		return "ventas";
	}

	@PostMapping("agregar2/{codBarras}")
	public String agregarAlCarrito2(@PathVariable("codBarras") String codBarras, Producto producto,
			HttpServletRequest request, RedirectAttributes redirectAttrs, Model model) {
		List<DetalleVenta> carrito = this.obtenerCarrito(request);
		Producto productoBuscadoPorCodigo = this.productoService.buscarProductoPorCodigoBarras(codBarras);

		if (productoBuscadoPorCodigo == null) {
			redirectAttrs.addFlashAttribute("mensaje1",
					"El producto con el código " + producto.getCodigoBarras() + " no existe");

			return "redirect:/ventas/vetanaInicio";
		}

		if (productoBuscadoPorCodigo.getCantidad() <= 0) {
			redirectAttrs.addFlashAttribute("mensaje1", "El producto está agotado");

//			return "redirect:/ventas/vetanaInicio";
		}

		LOG.info("" + productoBuscadoPorCodigo);

		boolean encontrado = false;
		for (DetalleVenta det : carrito) {
			if (det.getProducto().getCodigoBarras().equals(productoBuscadoPorCodigo.getCodigoBarras())) {
				det.setCantidad(det.getCantidad() + 1);
				det.setTotal(det.getProducto().getValorVenta().multiply(new BigDecimal(det.getCantidad())));
				encontrado = true;

				break;
			}
		}

		if (!encontrado) {
			carrito.add(new DetalleVenta(1,
					this.detalleVentaService.calcularValor(1, productoBuscadoPorCodigo.getValorVenta()),
					productoBuscadoPorCodigo));
		}

		this.guardarCarrito(carrito, request);
		BigDecimal total = this.ventaService.calcularValorAPagar(carrito);
		model.addAttribute("total", total);
		return "redirect:/ventas/vetanaInicio";
	}

	private void limpiarCarrito(HttpServletRequest request) {
		this.guardarCarrito(new ArrayList<>(), request);
	}

	@GetMapping("limpiar")
	public String cancelarVenta(HttpServletRequest request, RedirectAttributes redirectAttrs, Producto producto) {
		this.limpiarCarrito(request);
		redirectAttrs.addFlashAttribute("mensaje1", "Venta cancelada");

		return "redirect:vetanaInicio";
	}

	@PostMapping("realizarVenta")
	public String terminarVenta(HttpServletRequest request, RedirectAttributes redirectAttrs, Producto producto) {
		List<DetalleVenta> carrito = this.obtenerCarrito(request);
		// Si no hay carrito o está vacío, regresamos inmediatamente
		if (carrito == null || carrito.size() <= 0) {
			return "ventas";
		}

		for (DetalleVenta d : carrito) {
			LOG.info("" + d);
		}

		this.ventaService.realizarVenta(carrito);

		// Al final limpiamos el carrito
		this.limpiarCarrito(request);
		// e indicamos una venta exitosa
		redirectAttrs.addFlashAttribute("mensaje1", "Venta realizada correctamente");
		return "redirect:vetanaInicio";
	}
}
