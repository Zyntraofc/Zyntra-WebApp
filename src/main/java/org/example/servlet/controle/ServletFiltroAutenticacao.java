package org.example.servlet.controle;

/// Classe criada com objetivo de filtrar o acesso à páginas restritas com acesso ao banco de dados no site
/// Todos os endpoints começados com "private/<nomeDaPagina>" ficarão privadas
/// Caso alguém tente acessar, se a sessão não estiver com usuário logado, manda direto para página de login
/// Criada para fim de evitar que qualquer um possa acessar a área restrita pela URL

//Importações
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

//Prefixo a ser colocado em cada endpoint para ser filtrado
@WebFilter("/private/*")
//Abertura da classe que implementa interface de filtro
public class ServletFiltroAutenticacao implements Filter {

    //Metodo doFilter que realizará ação de segurança
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        //Atributos de requisição, resposta e sessão
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession sessao = request.getSession(false);


        //Verificação se a sessão for ativa e usuário estiver logado, roda a página normalmente
        if (sessao != null && sessao.getAttribute("usuario") != null) {
            chain.doFilter(req, resp);
        }
        //Se não redireciona para página de login
        else {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }

    }

    //Declaração de metodo abstrado de inicialização da configuração do filtro
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    //Declaração de metodo abstrato de destruição do filtro
    @Override
    public void destroy() {

    }
}
