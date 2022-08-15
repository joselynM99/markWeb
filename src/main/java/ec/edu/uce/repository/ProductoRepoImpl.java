package ec.edu.uce.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import ec.edu.uce.modelo.Producto;

@Repository
@Transactional
public class ProductoRepoImpl implements IProductoRepo{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void insertarProducto(Producto producto) {
		this.entityManager.persist(producto);
	}

	@Override
	public Producto buscarProducto(Integer id) {
		return this.entityManager.find(Producto.class, id);
	}

	@Override
	public List<Producto> buscarTodosProductos() {
		TypedQuery<Producto> myQuery = this.entityManager.createQuery("SELECT p FROM Producto p", Producto.class);

		return myQuery.getResultList();
	}

	@Override
	public void actualizarProducto(Producto producto) {
		this.entityManager.merge(producto);
	}

	@Override
	public void eliminarProducto(Integer id) {
		Producto prodAEliminar = this.buscarProducto(id);
		this.entityManager.remove(prodAEliminar);
	}

}