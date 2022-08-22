package ec.edu.uce.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ec.edu.uce.modelo.Producto;
import ec.edu.uce.modelo.Proveedor;
import ec.edu.uce.service.IProductoService;
import ec.edu.uce.service.IProveedorService;

@Controller
@RequestMapping("/inventario/")
public class GestionProductoController {

	@Autowired
	private IProductoService productoService;

	@Autowired
	private IProveedorService proveedorService;

	private static final Logger LOG = LoggerFactory.getLogger(GestionProductoController.class);

	@GetMapping("MenuInicial")
	public String obtenerMenuInicial() {
		return "menuInicial";
	}

	@GetMapping("GestionProductos")
	public String obtenerMenuProductos() {
		return "menuProductos";
	}

	@GetMapping("ingresoProducto")
	public String obtenerPaginaIngresoDatosProducto(Producto producto) {

		return "productoNuevo";

	}

	@GetMapping("busquedaProveedor")
	public String obtenerProveedorPorNombre(Producto p, Model modelo, RedirectAttributes redirectAttributes) {

		Proveedor prov = this.proveedorService.buscarProveedorNombre(p.getProveedor().getNombreEmpresa());
		if (prov == null || prov.getNombreEmpresa().isEmpty()) {
			redirectAttributes.addFlashAttribute("error", "Proveedor no encontrado. Registrar Proveedor:");
			return "redirect:ingresoProveedor";
		} else {
			modelo.addAttribute("prov", prov);

			return "productoNuevo";
		}

	}

	@PostMapping("insertarProducto")
	public String insertarProducto(Producto producto, Model model, RedirectAttributes redirectAttributes) {

		Proveedor proveedor = this.proveedorService.buscarProveedorNombre(producto.getProveedor().getNombreEmpresa());
		producto.setProveedor(proveedor);

		Producto p = this.productoService.buscarProductoPorCodigoBarras(producto.getCodigoBarras());

		if (p == null || p.getCodigoBarras().isEmpty()) {
			this.productoService.insertarProducto(producto);
			redirectAttributes.addFlashAttribute("mensaje", "Producto insertado con exíto");
		} else {
			redirectAttributes.addFlashAttribute("error", "CÓDIGO DE BARRAS DUPLICADO");
		}

		return "redirect:ingresoProducto";
	}

	@GetMapping("actualizarProducto")
	public String obtenerPaginaActualizarProducto(Producto producto) {

		return "productoActualizar";

	}

	@GetMapping("busquedaProducto")
	public String obtenerProductoPorNombre(Producto producto, Model modelo, RedirectAttributes redirectAttributes) {

		Producto p = this.productoService.buscarProductoPorCodigoBarras(producto.getCodigoBarras());
		if (p == null || p.getId().equals(null)) {
			redirectAttributes.addFlashAttribute("error", "Producto no encontrado");
			return "redirect:/inventario/actualizarProducto";
		} else {
			modelo.addAttribute("producto", p);
			LOG.info("" + p);

			return "productoActualizar";
		}

	}

	@PostMapping("actualizar")
	public String actualizarProducto(Producto producto, Model modelo, RedirectAttributes redirectAttributes) {

		Proveedor proveedor = this.proveedorService.buscarProveedorNombre(producto.getProveedor().getNombreEmpresa());
		producto.setId(producto.getId());
		producto.setProveedor(proveedor);
		LOG.info("" + producto);
		this.proveedorService.actualizarProveedor(proveedor);
		this.productoService.actualizarProducto(producto);
		redirectAttributes.addFlashAttribute("mensaje", "Producto actualizado");
		return "redirect:/inventario/actualizarProducto";
	}

	@PostMapping("eliminarProducto/{idProducto}")
	public String eliminarProducto(@PathVariable Integer idProducto, Producto producto, Model modelo) {

		LOG.info("" + idProducto);
		this.productoService.eliminarProducto(idProducto);
		return "productoActualizar";
	}

	@GetMapping("ingresoProveedor")
	public String obtenerPaginaIngresoDatosProveedor(Proveedor proveedor, RedirectAttributes redirectAttributes) {

		return "proveedorNuevo";

	}

	@PostMapping("insertarProveedor")
	public String insertarProveedor(Proveedor proveedor, Model model, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("mensaje", "Proveedor registrado con exíto");

		this.proveedorService.insertarProveedor(proveedor);

		return "redirect:ingresoProveedor";
	}

}
