package br.com.feltex.controleacesso.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final String usuarioPadrao;
    private final String senhaPadrao;

    public LoginService(@Value("${app.controle.acesso.usuario}") String usuario,
                        @Value("${app.controle.acesso.senha}") String senha) {
        usuarioPadrao = usuario;
        senhaPadrao = senha;
    }

    public boolean validarLogin(final String usuario, final String senha) {
        return usuarioPadrao.equals(usuario) && senhaPadrao.equals(senha);
    }
}
