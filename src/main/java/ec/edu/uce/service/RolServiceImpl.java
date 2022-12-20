package ec.edu.uce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.edu.uce.modelo.Rol;
import ec.edu.uce.repository.IRolRepo;

@Service
public class RolServiceImpl implements IRolService {

	@Autowired
	private IRolRepo iRolRepo;

	@Override
	public void insertar(Rol rol) {
		this.iRolRepo.insertar(rol);
	}

	@Override
	public Rol buscar(Integer id) {
		return this.iRolRepo.buscar(id);
	}

	@Override
	public Rol buscarNombre(String nombre) {
		return this.iRolRepo.buscarNombre(nombre);
	}

}
