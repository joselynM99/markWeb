package ec.edu.uce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.edu.uce.modelo.Compra;
import ec.edu.uce.repository.ICompraRepo;

@Service
public class CompraServiceImpl implements ICompraService{
	
	@Autowired
	private ICompraRepo compraRepo;

	@Override
	public void insertarCompra(Compra compra) {
		this.compraRepo.insertarCompra(compra);
	}

	@Override
	public Compra buscarCompra(Integer id) {
		return this.compraRepo.buscarCompra(id);
	}

	@Override
	public List<Compra> buscarTodosCompra() {
		return this.compraRepo.buscarTodosCompra();
	}

	@Override
	public void actualizarCompra(Compra compra) {
		this.compraRepo.actualizarCompra(compra);
	}

	@Override
	public void eliminarCompra(Integer id) {
		this.compraRepo.eliminarCompra(id);
	}

}
