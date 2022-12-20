package ec.edu.uce.services;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ec.edu.uce.modelo.Rol;
import ec.edu.uce.modelo.Usuario;
import ec.edu.uce.modelo.dto.UsuarioRegistroDTO;
import ec.edu.uce.repository.UsuarioRepositorio;

@Service
public class UsuarioServicioImpl implements UsuarioServicio {

	private UsuarioRepositorio usuarioRepositorio;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private IRolService iRolService;

	public UsuarioServicioImpl(UsuarioRepositorio usuarioRepositorio) {
		super();
		this.usuarioRepositorio = usuarioRepositorio;
	}

	@Override
	public Usuario guardar(UsuarioRegistroDTO registroDTO) {

		Usuario tmp = usuarioRepositorio.findByEmail(registroDTO.getEmail());
		Rol rol = this.iRolService.buscarNombre(registroDTO.getRol().toString());
		if (tmp == null) {

			Usuario usuario = new Usuario(registroDTO.getNombre(), registroDTO.getApellido(), registroDTO.getEmail(),
					passwordEncoder.encode(registroDTO.getPassword()), Arrays.asList(rol));
			return this.usuarioRepositorio.save(usuario);
		} else {
			
			Collection<Rol> c = tmp.getRoles();
			boolean valor = false;
			for (Rol rol2 : c) {
				if (rol2.equals(rol)) {
					valor = true;
				} else {
					valor = false;
				}
			}
			if (!valor) {
				c.add(rol);
			}

			tmp.setRoles(c);
			return this.usuarioRepositorio.save(tmp);
		}
	
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepositorio.findByEmail(username);
		if (usuario == null) {
			throw new UsernameNotFoundException("Usuario o password inválidos");
		}
		return new User(usuario.getEmail(), usuario.getPassword(), mapearAutoridadesRoles(usuario.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapearAutoridadesRoles(Collection<Rol> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toList());
	}

	@Override
	public List<Usuario> listarUsuarios() {
		return this.usuarioRepositorio.findAll();
	}
}
