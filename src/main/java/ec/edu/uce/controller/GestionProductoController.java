package ec.edu.uce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public String obtenerProveedorPorNombre(Producto p, Model modelo) {

		Proveedor prov = this.proveedorService.buscarProveedorNombre(p.getProveedor().getNombreEmpresa());
		if (prov == null || prov.getNombreEmpresa().isEmpty()) {
			return "redirect:ingresoProveedor";
		} else {
			modelo.addAttribute("prov", prov);

			return "productoNuevo";
		}

	}

	@PostMapping("insertarProducto")
	public String insertarProducto(Producto producto, Model model) {

		Proveedor proveedor = this.proveedorService.buscarProveedorNombre(producto.getProveedor().getNombreEmpresa());
		producto.setProveedor(proveedor);
		this.productoService.insertarProducto(producto);

		return "redirect:ingresoProducto";
	}

	@GetMapping("ingresoProveedor")
	public String obtenerPaginaIngresoDatosProveedor(Proveedor proveedor) {

		return "proveedorNuevo";

	}

	@PostMapping("insertarProveedor")
	public String insertarProveedor(Proveedor proveedor, Model model) {

		this.proveedorService.insertarProveedor(proveedor);

		return "redirect:ingresoProveedor";
	}

}
