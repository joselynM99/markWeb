package ec.edu.uce.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import ec.edu.uce.modelo.Rol;

@Repository
@Transactional
public class RolRepoImpl implements IRolRepo {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void insertar(Rol rol) {
		this.entityManager.persist(rol);
	}

	@Override
	public Rol buscar(Integer id) {
		return this.entityManager.find(Rol.class, id);
	}

	@Override
	public Rol buscarNombre(String nombre) {
		TypedQuery<Rol> query = this.entityManager.createQuery("SELECT r FROM Rol r WHERE r.nombre=:nombre", Rol.class);
		query.setParameter("nombre", nombre);
		return query.getSingleResult();
	}

}
