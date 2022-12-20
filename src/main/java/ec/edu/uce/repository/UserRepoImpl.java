package ec.edu.uce.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import ec.edu.uce.modelo.Usuario;

@Repository
@Transactional
public class UserRepoImpl implements IUserRepo {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void insertar(Usuario usuario) {
		this.entityManager.persist(usuario);
	}

	@Override
	public void actualizar(Usuario usuario) {
		this.entityManager.merge(usuario);
	}

	@Override
	public Usuario buscar(Integer id) {
		return this.entityManager.find(Usuario.class, id);
	}

	@Override
	public void borrar(Integer id) {
		this.entityManager.remove(this.buscar(id));
	}

	@Override
	public Usuario validarContrasenia(String nombre) {
		TypedQuery<Usuario> query = this.entityManager.createQuery("SELECT u FROM Usuario u WHERE u.usuario=:usuario",
				Usuario.class);
		query.setParameter("usuario", nombre);
		return query.getSingleResult();
	}
}
