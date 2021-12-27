package br.com.feltex.controleacesso.bean;

import br.com.feltex.controleacesso.service.LoginService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
@RequestScoped
public class LoginBean {

    @Getter
    @Setter
    private String usuario;

    @Getter
    @Setter
    private String senha;

    @Autowired
    private LoginService loginService;

    public String login() {
        final FacesMessage mensagem;
        String destino;

        if (loginService.validarLogin(usuario, senha)) {
            mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bem vindo!", usuario);
            HttpSession session = getSession();
            session.setAttribute("usuario", usuario);
            destino = "home";
        } else {
            mensagem = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro no login", "Credenciais inválidas.");
            destino = "login";
        }

        FacesContext.getCurrentInstance().addMessage(null, mensagem);
        return destino;
    }

    public String logout() {
        HttpSession session = getSession();
        session.invalidate();
        return "login";
    }

    private static HttpSession getSession() {
        return (HttpSession)
                FacesContext.
                        getCurrentInstance().
                        getExternalContext().
                        getSession(false);
    }

}
