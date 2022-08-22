package ec.edu.uce;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ec.edu.uce.modelo.DetalleVenta;
import ec.edu.uce.modelo.Producto;
import ec.edu.uce.modelo.Proveedor;
import ec.edu.uce.service.ICompraService;
import ec.edu.uce.service.IDetalleCompraService;
import ec.edu.uce.service.IDetalleVentaService;
import ec.edu.uce.service.IProductoService;
import ec.edu.uce.service.IProveedorService;
import ec.edu.uce.service.IVentaService;

@SpringBootApplication
public class MarwebApplication implements CommandLineRunner {
	private static final Logger LOG = LoggerFactory.getLogger(MarwebApplication.class);

	@Autowired
	private IProductoService productoService;

	@Autowired
	private IProveedorService proveedorService;

	@Autowired
	private IVentaService ventaService;

	@Autowired
	private IDetalleVentaService detalleVentaService;

	@Autowired
	private ICompraService compraService;

	@Autowired
	private IDetalleCompraService detalleCompraService;

	public static void main(String[] args) {
		SpringApplication.run(MarwebApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Proveedor p = new Proveedor();

		p.setNombreEmpresa("Nestle");
		p.setTelefono("0985995135");

		Producto pr = new Producto();

//		Proveedor p = this.proveedorService.buscarProveedor(1);
		pr.setCategoria("Confiteria");
		pr.setCantidad(15);
		pr.setCodigoBarras("866655878877");
		pr.setCostoBruto(new BigDecimal(0.15));
		pr.setDescripcion("Chocolate nestle");
		pr.setNombre("KIT KAT 2Finger Multipack 16.7g");
		pr.setProveedor(p);
		pr.setValorVenta(new BigDecimal(0.30));

		List<Producto> lPr = new ArrayList<Producto>();
		lPr.add(pr);
		p.setProductos(lPr);

//	this.proveedorService.insertarProveedor(p);
//this.productoService.insertarProducto(pr);

		List<Producto> lp = this.productoService.buscarProductoPorNombre("g");

		List<DetalleVenta> list = new ArrayList<DetalleVenta>();
		for (Producto d : lp) {
			DetalleVenta dv = new DetalleVenta();
			dv.setCantidad(2);
			dv.setProducto(d);
			list.add(dv);
		}
		
//	this.ventaService.realizarVenta(list);
		
		
	}

}
