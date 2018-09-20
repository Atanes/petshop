package com.iridiumit.controleos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.iridiumit.controleos.model.Permissao;
import com.iridiumit.controleos.model.Usuario;
import com.iridiumit.controleos.repository.Permissoes;
import com.iridiumit.controleos.repository.Usuarios;
import com.iridiumit.controleos.repository.filtros.UsuarioFiltro;

@Service
public class UsuarioService implements UserDetailsService{
	
	@Autowired
	private Usuarios usuarios;
	
	@Autowired
	private Permissoes permissoes;
	
	
	
	public List<Usuario> filtrar(UsuarioFiltro filtro) {
		String nome = filtro.getNome() == null ? "%" : filtro.getNome();
		return usuarios.findByNomeContainingIgnoreCase(nome);
	}
	
	public void excluir(Long codigo) {
		usuarios.delete(codigo);
	}
	
	public Usuario localizar(Long id){
		return usuarios.findOne(id);
	}
	
	public Usuario localizarLogin(String login){
		return usuarios.findByLogin(login);
	}
	
	public void incluir(Usuario usuario){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hasSenha = passwordEncoder.encode(usuario.getSenha());
        
        usuario.setSenha(hasSenha);
        usuario.setAtivo(true);
        
        usuarios.save(usuario);
    }
	
	public void salvar(Usuario usuario) {
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hasSenha = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(hasSenha);
        
        usuarios.save(usuario);
		
	}
	
	public List<Permissao> permissoes(){
		return permissoes.findAll();
	}

	@Override
    public UserDetails loadUserByUsername (String login) throws UsernameNotFoundException {
        Usuario u = usuarios.findByLogin(login);

     if(u == null){
         throw new UsernameNotFoundException("O " + login + " não foi encontrado!");
     }

     return u;
    }

}
