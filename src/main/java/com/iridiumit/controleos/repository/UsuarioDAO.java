package com.iridiumit.controleos.repository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.iridiumit.controleos.model.Permissao;
import com.iridiumit.controleos.model.Usuario;

@Repository
public class UsuarioDAO implements UserDetailsService{

    @PersistenceContext
    private EntityManager manager;
    
    @Autowired
    private Permissoes permissoes;

    @Override
    public UserDetails loadUserByUsername (String login) throws UsernameNotFoundException {
        String jpql = "select u from Usuario u where u.login = :login";    
        List<Usuario> usuarios    = manager.createQuery(jpql, Usuario.class)
                .setParameter("login", login).getResultList();

     if(usuarios.isEmpty()){
         throw new UsernameNotFoundException("O " + login + " não foi encontrado!");
     }

     return usuarios.get(0);
    }

    public void adicionaUsuario(Usuario usuario, Permissao permissao){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hasSenha = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(hasSenha);
        usuario.setAtivo(true);
        
        Permissao p = permissoes.findOne(permissao.getId());
        usuario.setPermissoes(new HashSet<Permissao>(Arrays.asList(p)));
        
        manager.persist(usuario);
    }

    public List<Usuario> lista() {
        return manager.createQuery("select u from Usuario u", Usuario.class).getResultList();
    }


}
