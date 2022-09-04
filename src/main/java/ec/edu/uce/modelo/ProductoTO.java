package ec.edu.uce.modelo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class ProductoTO {

	private String codigoBarras;

	private String nombre;

	private String descripcion;

	private String categoria;

	private BigDecimal costoBruto;

	private BigDecimal valorVenta;

	private Integer cantidad;

	private Proveedor proveedor;

	private List<DetalleVenta> ventas;

	private List<DetalleCompra> compras;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime fecha;

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
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

}
