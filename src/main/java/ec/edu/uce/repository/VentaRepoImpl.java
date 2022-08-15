package ec.edu.uce.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import ec.edu.uce.modelo.Compra;
import ec.edu.uce.modelo.Venta;

@Repository
@Transactional
public class VentaRepoImpl implements IVentaRepo {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void insertarVenta(Venta venta) {
		this.entityManager.persist(venta);
	}

	@Override
	public Venta buscarVenta(Integer id) {
		return this.entityManager.find(Venta.class, id);
	}

	@Override
	public List<Venta> buscarTodosVenta() {
		TypedQuery<Venta> myQuery = this.entityManager.createQuery("SELECT v FROM Venta v", Venta.class);
		return myQuery.getResultList();
	}

	@Override
	public void actualizarVenta(Venta venta) {
		this.entityManager.merge(venta);
	}

	@Override
	public void eliminarVenta(Integer id) {
		Venta venta = this.buscarVenta(id);
		this.entityManager.remove(venta);

	}

}
