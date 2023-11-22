package servlets;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;


import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Servlet implementation class Filtro
 */

public class AuthFilter implements Filter {
	private HttpServletRequest httpRequest;
	private Hashtable<String, String[]> taboaAutorizacions;
	 private static final Logger logger= Logger.getLogger(AuthFilter.class.getName());

	/**
	 * Default constructor.
	 */
	public AuthFilter() {
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// obteño url a que desexo acceder
		httpRequest = (HttpServletRequest) request;
		String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());  //También puedo dejarlo en substring()
		// obteño a sesión SEN crear unha nova
		HttpSession session = httpRequest.getSession(false);
		// se non hai session ou non hai user fago que o user sexa anonymous
		if (session == null || session.getAttribute("rol") == null) {
			session = httpRequest.getSession(true);
			session.setAttribute("rol", "ANON");
			System.out.println("authfilter:: config rol ANON en session");
		}
		// chamada a método de autorización
		String rol = (String) session.getAttribute("rol");
		boolean isAuthorised = isAuthorised(rol, path);
		// decisión de filtrado
		if (!isAuthorised) {
			System.out.println("Authfilter:: path " + path + " NON autorizado para rol: "+rol);
			httpRequest.getRequestDispatcher("/").forward(request, response);
		} else {
			//Son lo mismo
			logger.info("acceso actualizado a"+ path+" para rol: "+rol);
			System.out.println("Authfilter:: path " + path + " autorizado para rol: "+rol);
			chain.doFilter(request, response);
		}
		// PARA PROBAS
		// chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// construo a taboa de autorizacións que conten para cada rol
		// unha lista das urls autorizadas
		taboaAutorizacions = new Hashtable<String, String[]>();
		String[] urlsANON = { "/favicon.ico", "/login.jsp", "/" };
		String[] urlsBASIC = { "/favicon.ico", "/login.jsp", "/perfil.jsp", "/" };
		String[] urlsADMIN = { "/favicon.ico", "/login.jsp", "/perfil.jsp", "/admin.jsp", "/" };
		taboaAutorizacions.put("ANON", urlsANON);
		taboaAutorizacions.put("BASIC", urlsBASIC);
		taboaAutorizacions.put("ADMIN", urlsADMIN);
	}

	private boolean isAuthorised(String _rol, String _url) {
		// recorro o Hashtable de roles e listas de urls autorizadas comprobando
		// se as urls autorizadas para o _rol do parámetro incluen a
		// url enviada como parámetro _url
		for (Map.Entry<String, String[]> entrada : taboaAutorizacions.entrySet()) {
			String rol = entrada.getKey();
			String[] urls = entrada.getValue();
			if (rol.equals(_rol)) {
				for (String url : urls) {
					if (url.equals(_url)) {
						System.out.println(url + " equals " + _url);
						return true;
					}
				}
			}
		}
		return false;
	}

}