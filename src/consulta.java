
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/consulta")
public class consulta extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public consulta() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());

		// recogemos el parametros de "autor" del formulario
		String autor = request.getParameter("autor");
		// Preparamos la salida del servlet
		response.setContentType("text/html");

		// obejeto de coneccion y consulta a la base de datos
		Connection con;
		PreparedStatement stmt;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			String url = "jdbc:mysql://localhost/TiendaLibros";
			String usuario = "librero";
			String password = "Ageofempires2";

			con = DriverManager.getConnection(url, usuario, password);
			String sql = "select * from libros wher autor =?";
			stmt = con.prepareStatement(sql);
			stmt.setString(1, autor);
			// Mostramos los resultados

			ResultSet resultados = stmt.executeQuery();
			// Generamos la página HTML de salida
			out.println("<html>");
			out.println("<head>");
			out.println("<meta charset='utf-8'>");
			out.println("<title>Consulta</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Consulta por autor</h1>");
			out.println("<p>Los libros publicados por " + autor + " son:</p>");
			// Vamos recorriendo el ResultSet y mostrando sus campos.
			
			while (resultados.next()) {
				out.println("<hr>");
				out.println("Título: " + resultados.getString("titulo") + "<br>");
				out.println("Precio: " + resultados.getString("precio") + "<br>");
				out.println("Cantidad: " + resultados.getString("cantidad") + "<br> <hr>");
			}

		} catch (ClassNotFoundException |SQLException e) {
			System.out.println("ERROR EN LA CONNECCION A LA BASE DE DATOS");

		}finally {
			try {
				
			} catch (SQLException e) {
				System.out.println("ERROR AL CERRAR LA  BASE DE DATOS");

				con.close();
				stmt.close();
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
