package ec.edu.uce.modelo;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "producto")
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_producto")
	@SequenceGenerator(name = "seq_producto", sequenceName = "seq_producto", allocationSize = 1)
	@Column(name = "prod_id")
	private Integer id;

	@Column(name = "prod_codigo_barras", unique = true)
	private String codigoBarras;

	@Column(name = "prod_nombre")
	private String nombre;

	@Column(name = "prod_descripcion")
	private String descripcion;

	@Column(name = "prod_categoria")
	private String categoria;

	@Column(name = "prod_costoBruto")
	private BigDecimal costoBruto;

	@Column(name = "prod_valorVenta")
	private BigDecimal valorVenta;

	@Column(name = "prod_cantidad")
	private Integer cantidad;

	@ManyToOne
	@JoinColumn(name = "prov_id")
	private Proveedor proveedor;

	@OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
	private List<DetalleVenta> ventas;

	@OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
	private List<DetalleCompra> compras;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public BigDecimal getCostoBruto() {
		return costoBruto;
	}

	public void setCostoBruto(BigDecimal costoBruto) {
		this.costoBruto = costoBruto;
	}

	public BigDecimal getValorVenta() {
		return valorVenta;
	}

	public void setValorVenta(BigDecimal valorVenta) {
		this.valorVenta = valorVenta;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public List<DetalleVenta> getVentas() {
		return ventas;
	}

	public void setVentas(List<DetalleVenta> ventas) {
		this.ventas = ventas;
	}

	public List<DetalleCompra> getCompras() {
		return compras;
	}

	public void setCompras(List<DetalleCompra> compras) {
		this.compras = compras;
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", codigoBarras=" + codigoBarras + ", nombre=" + nombre + ", descripcion="
				+ descripcion + ", categoria=" + categoria + ", costoBruto=" + costoBruto + ", valorVenta=" + valorVenta
				+ ", cantidad=" + cantidad + "]";
	}
	
	

}
