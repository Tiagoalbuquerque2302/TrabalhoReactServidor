/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.edu.ifrs.restinga.dev1.projetoFinal.tudo.controller;

import be.edu.ifrs.restinga.dev1.projetoFinal.tudo.aut.ForbiddenException;
import be.edu.ifrs.restinga.dev1.projetoFinal.tudo.aut.UsuarioAut;
import be.edu.ifrs.restinga.dev1.projetoFinal.tudo.DAO.UsuarioDAO;
import be.edu.ifrs.restinga.dev1.projetoFinal.tudo.modelo.Usuario;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jezer
 */

@RestController
@RequestMapping(path = "/api")
public class UsuarioControll {

    public static final PasswordEncoder 
            PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @RequestMapping(path = "/usuarios/user", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario inserirUsuario(@AuthenticationPrincipal UsuarioAut usuarioAut, @RequestBody Usuario usuario) {
        usuario.setId(0);
        usuario.setSenha(PASSWORD_ENCODER.encode(usuario.getNovaSenha()));

        if (usuarioAut == null || !usuarioAut.getUsuario().getPermissoes().contains("administrador")) {
            ArrayList<String> permissao = new ArrayList<String>();
            permissao.add("usuario");
            usuario.setPermissoes(permissao);
        }
        Usuario usuarioSalvo = usuarioDAO.save(usuario);
        return usuarioSalvo;
    }
    
    @PreAuthorize("hasAuthority('administrador')")
    @RequestMapping(path = "/usuarios/entregador", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario inserirEntregador(@AuthenticationPrincipal UsuarioAut usuarioAut, @RequestBody Usuario usuario) {
        usuario.setId(0);
        usuario.setSenha(PASSWORD_ENCODER.encode(usuario.getNovaSenha()));

            ArrayList<String> permissao = new ArrayList<String>();
            permissao.add("entregador");
            usuario.setPermissoes(permissao);
        
        Usuario usuarioSalvo = usuarioDAO.save(usuario);
        return usuarioSalvo;
    }
  
    @Autowired
    UsuarioDAO usuarioDAO;

    @PreAuthorize("hasAuthority('administrador')")
    @RequestMapping(path = "/usuarios", method = RequestMethod.GET)
    public Iterable<Usuario> listar(@RequestParam(required = false, defaultValue = "0") int pagina) {
        PageRequest pageRequest = new PageRequest(pagina, 5);
        return usuarioDAO.findAll(pageRequest);
    }


    @RequestMapping(path = "/usuarios/{id}", method = RequestMethod.GET)
    public Usuario recuperar(@AuthenticationPrincipal UsuarioAut usuarioAut, @PathVariable int id) {
        if (usuarioAut.getUsuario().getId() == id 
                || usuarioAut.getUsuario().getPermissoes().contains("administrador")) {
            return usuarioDAO.findOne(id);
        } else {
            throw new ForbiddenException("Não é permitido acessar dados de outro usuários");
        }
    }

    @PreAuthorize("hasAuthority('administrador')")
    @RequestMapping(path = "/usuarios/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void atualizar(@PathVariable int id, @RequestBody Usuario usuario) {
        if (usuarioDAO.exists(id)) {
            usuario.setId(id);
            usuarioDAO.save(usuario);
        }
    }

    @PreAuthorize("hasAuthority('administrador')")
    @RequestMapping(path = "/usuarios/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void apagar(@PathVariable int id) {
        if (usuarioDAO.exists(id)) {
            usuarioDAO.delete(id);
        }

    }
    
    @RequestMapping(path = "/usuarios/login", method = RequestMethod.GET)
    public Usuario login(@AuthenticationPrincipal UsuarioAut userAut) {
        return userAut.getUsuario();
    }
    
    

}
