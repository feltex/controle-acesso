package br.com.feltex.controleacesso.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Component
@Order(1)
public class AcessoFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        try {
            var req = (HttpServletRequest) request;
            var res = (HttpServletResponse) response;
            HttpSession sessao = req.getSession(false);
            var reqURI = req.getRequestURI();
            if (acessoLiberado(sessao, reqURI))
                chain.doFilter(request, response);
            else {
                log.debug("URI bloqueada {}", reqURI);
                res.sendRedirect(req.getContextPath() + "/login.xhtml");
            }
        } catch (Throwable t) {
            log.error("Erro ao filtrar a requisição", t);
        }
    }

    private static boolean acessoLiberado(HttpSession sessao, String requestURI) {
        return requestURI.endsWith("/login.xhtml") || sessao != null && sessao.getAttribute("usuario") != null
                || requestURI.indexOf("/public/") >= 0 || requestURI.contains("javax.faces.resource");
    }
}
