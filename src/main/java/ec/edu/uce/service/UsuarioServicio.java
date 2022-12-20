package ec.edu.uce.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import ec.edu.uce.modelo.Usuario;
import ec.edu.uce.modelo.dto.UsuarioRegistroDTO;

public interface UsuarioServicio extends UserDetailsService {

	public Usuario guardar(UsuarioRegistroDTO registroDTO);

	public List<Usuario> listarUsuarios();

}
