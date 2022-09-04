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

@Entity
@Table(name = "compra")
public class Compra {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_compra")
	@SequenceGenerator(name = "seq_compra", sequenceName = "seq_compra", allocationSize = 1)
	@Column(name = "comp_id")
	private Integer id;

	@Column(name = "comp_valor_compra")
	private BigDecimal valorCompra;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	@Column(name = "comp_fecha")
	private LocalDateTime fecha;

	@OneToMany(mappedBy = "compra", cascade = CascadeType.ALL)
	private List<DetalleCompra> detalles;

	@ManyToOne
	@JoinColumn(name = "prov_id")
	private Proveedor proveedor;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getValorCompra() {
		return valorCompra;
	}

	public void setValorCompra(BigDecimal valorCompra) {
		this.valorCompra = valorCompra;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public List<DetalleCompra> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetalleCompra> detalles) {
		this.detalles = detalles;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

}
