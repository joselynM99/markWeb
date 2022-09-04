package ec.edu.uce.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

import ec.edu.uce.modelo.Compra;
import ec.edu.uce.modelo.DetalleCompra;
import ec.edu.uce.modelo.Producto;
import ec.edu.uce.modelo.ProductoTO;
import ec.edu.uce.modelo.Proveedor;
import ec.edu.uce.service.ICompraService;
import ec.edu.uce.service.IDetalleCompraService;
import ec.edu.uce.service.IProductoService;
import ec.edu.uce.service.IProveedorService;

@Controller
@RequestMapping("/compras/")
public class GestionComprasController {

	@Autowired
	private IProductoService productoService;

	@Autowired
	private IProveedorService proveedorService;

	@Autowired
	private ICompraService compraService;

	@Autowired
	private IDetalleCompraService detalleCompraService;

	private static final Logger LOG = LoggerFactory.getLogger(GestionComprasController.class);

	@GetMapping("realizarCompra")
	public String obtenerPaginaCompra(ProductoTO producto, Compra compra, RedirectAttributes redirectAttributes,
			Model model) {

		model.addAttribute("producto", producto);

		return "compras";

	}

	@GetMapping("vetanaInicio")
	public String obtenerPaginaVentas(ProductoTO producto, Compra compra, Model model, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {

		List<DetalleCompra> carrito = this.obtenerCarrito(request);
		BigDecimal total = this.compraService.calcularValorAPagar(carrito);
		model.addAttribute("total", total);
		model.addAttribute("producto", producto);
//		redirectAttributes.addFlashAttribute("producto", producto);
		return "compras";

	}

	@GetMapping("buscarProducto")
	public String buscarProducto(@ModelAttribute ProductoTO producto, Compra compra, Model model) {

		List<Producto> listaProductos = this.productoService.buscarProductoPorNombre(producto.getNombre());

		model.addAttribute("producto", producto);
		model.addAttribute("listaProductos", listaProductos);
		return "compras";
	}

	private List<DetalleCompra> obtenerCarrito(HttpServletRequest request) {
		List<DetalleCompra> carrito = (List<DetalleCompra>) request.getSession().getAttribute("carrito");
		if (carrito == null) {
			carrito = new ArrayList<>();
		}
		return carrito;
	}

	private void guardarCarrito(List<DetalleCompra> carrito, HttpServletRequest request) {
		request.getSession().setAttribute("carrito", carrito);
	}

	@PostMapping("agregar")
	public String agregarAlCarrito(ProductoTO producto, Compra compra, HttpServletRequest request,
			RedirectAttributes redirectAttrs) {
		List<DetalleCompra> carrito = this.obtenerCarrito(request);
		Producto productoBuscadoPorCodigo = this.productoService
				.buscarProductoPorCodigoBarras(producto.getCodigoBarras());

		if (productoBuscadoPorCodigo == null) {
			redirectAttrs.addFlashAttribute("mensaje1",
					"El producto con el código " + producto.getCodigoBarras() + " no existe");

			return "redirect:vetanaInicio";
		}

		boolean encontrado = false;
		for (DetalleCompra det : carrito) {
			if (det.getProducto().getCodigoBarras().equals(productoBuscadoPorCodigo.getCodigoBarras())) {
				det.setCantidad(det.getCantidad() + 1);
				det.setTotal(det.getProducto().getValorVenta().multiply(new BigDecimal(det.getCantidad())));
				encontrado = true;

				break;
			}
		}

		if (!encontrado) {
			carrito.add(new DetalleCompra(producto.getCantidad(), this.detalleCompraService.calcularValorCompra(
					producto.getCantidad(), productoBuscadoPorCodigo.getValorVenta()), productoBuscadoPorCodigo));
		}

//		redirectAttrs.addFlashAttribute("producto", producto);
		this.guardarCarrito(carrito, request);
		return "redirect:vetanaInicio";
	}

	@PostMapping("borrar/{indice}")
	public String quitarDelCarrito(@PathVariable int indice, Compra compra, ProductoTO producto,
			HttpServletRequest request, Model model) {
		List<DetalleCompra> carrito = this.obtenerCarrito(request);
		if (carrito != null && carrito.size() > 0 && carrito.get(indice) != null) {
			carrito.remove(indice);
			this.guardarCarrito(carrito, request);
		}

		BigDecimal total = this.compraService.calcularValorAPagar(carrito);
		model.addAttribute("total", total);
		model.addAttribute("producto", producto);
		return "compras";
	}

	@GetMapping("agregar3/{codBarras}")
	public String agregarAlCarrito3(@PathVariable("codBarras") String codBarras, ProductoTO producto, Compra compra,
			HttpServletRequest request, RedirectAttributes redirectAttrs, Model model) {

		LOG.info("" + producto);
		Producto produc = this.productoService.buscarProductoPorCodigoBarras(codBarras);
		producto.setCantidad(1);
		producto.setCodigoBarras(produc.getCodigoBarras());
		LOG.info("" + producto);

		model.addAttribute("producto", producto);

		return "compras";

	}

	private void limpiarCarrito(HttpServletRequest request) {
		this.guardarCarrito(new ArrayList<>(), request);
	}

	@GetMapping("limpiar")
	public String cancelarVenta(HttpServletRequest request, RedirectAttributes redirectAttrs, Producto producto,
			Compra compra) {
		this.limpiarCarrito(request);
		redirectAttrs.addFlashAttribute("mensaje1", "Compra cancelada");

		return "redirect:/compras/vetanaInicio";
	}

	@GetMapping("busquedaProveedor")
	public String obtenerProveedorPorNombre(ProductoTO producto, Compra compra, Model modelo,
			RedirectAttributes redirectAttributes) {

		Proveedor prov = this.proveedorService.buscarProveedorNombre(compra.getProveedor().getNombreEmpresa());
		if (prov == null || prov.getNombreEmpresa().isEmpty()) {
			redirectAttributes.addFlashAttribute("mensaje1", "Proveedor no encontrado");
			return "redirect:vetanaInicio";
		} else {
			modelo.addAttribute("compra", compra);
			modelo.addAttribute("producto", producto);

			return "compras";
		}
	}

	@PostMapping("realizarCompra")
	public String terminarVenta(HttpServletRequest request, RedirectAttributes redirectAttrs, Compra compra) {
		List<DetalleCompra> carrito = this.obtenerCarrito(request);
		// Si no hay carrito o está vacío, regresamos inmediatamente
		if (carrito == null || carrito.size() <= 0) {
			redirectAttrs.addFlashAttribute("mensaje1", "Lista vacía");
			return "redirect:vetanaInicio";
		} else if (compra.getProveedor().equals(null) || compra.getFecha() == null) {
			redirectAttrs.addFlashAttribute("mensaje1", "Rellene todos los campos");
			return "redirect:vetanaInicio";
		}

		LOG.info("" + compra.getFecha());
		Proveedor p = this.proveedorService.buscarProveedorNombre(compra.getProveedor().getNombreEmpresa());
		this.compraService.realizarCompra(carrito, p, compra.getFecha());

		// Al final limpiamos el carrito
		this.limpiarCarrito(request);
		// e indicamos una venta exitosa
		redirectAttrs.addFlashAttribute("mensaje1", "Compra realizada correctamente");
		return "redirect:/compras/vetanaInicio";

	}
}
