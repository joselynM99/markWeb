package ec.edu.uce.modelo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class CompraTO {

	private BigDecimal valorAPagar;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime fechaInicio;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime fechaFin;
	
	

	/**
	 * @param valorAPagar
	 * @param fechaInicio
	 * @param fechaFin
	 */
	public CompraTO(BigDecimal valorAPagar, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
		super();
		this.valorAPagar = valorAPagar;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}

	public BigDecimal getValorAPagar() {
		return valorAPagar;
	}

	public void setValorAPagar(BigDecimal valorAPagar) {
		this.valorAPagar = valorAPagar;
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDateTime fechaFin) {
		this.fechaFin = fechaFin;
	}



}
