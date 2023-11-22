package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet implementation class Controlador
 */
public class Controlador extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession sesion;
	public Controlador() {
		super();
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		sesion = request.getSession(false);
// Recibe "nome" da vista "login.jsp" e según o valor: "user" ou "admin"
// asigna un rol. OMITIMOS A AUTENTICACIÓN (CON PASSWORD) QUE TERÍA LUGAR
		String nome = request.getParameter("nome");
		
		
		System.out.println("Controlador:: chega nome = "+ nome);
		System.out.println("Controlador:: rol en sesion= "+ sesion.getAttribute("rol"));
		String destino = "/login.jsp";
		// recibiríamos ademáis a contrasinal e comprobariamos rol
		if (nome != null) {
			if (nome.equals("admin")) {
				if (sesion!= null)  {
					sesion.setAttribute("rol", "ADMIN");
					System.out.println("authfilter:: config rol ADMIN en session");
				}
				destino = "/admin.jsp"; // vista de admin
			} else if (nome.equals("user")) {
				if (sesion != null)  {
					sesion.setAttribute("rol", "BASIC");
					System.out.println("authfilter:: config rol BASIC en session");
				}
				destino = "/perfil.jsp";
			}
		}
		
		
		//Para el logOut
				String action=request.getParameter("action");
				if (action != null && sesion !=null && !sesion.getAttribute("rol").equals("ANON")) {
					if (action.equals("LogOut")) {
						sesion.invalidate();  //Crea na neva página vacía y limpia
					}
				}
		System.out.println("Controlador:: redirecciono a  "+destino);
		request.getRequestDispatcher(destino).forward(request, response);
}
}