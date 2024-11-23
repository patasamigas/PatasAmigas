package controller;
import dao.PessoaDAO;
import model.Pessoa;
import util.HashUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private PessoaDAO pessoaDAO = new PessoaDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        Pessoa pessoa = pessoaDAO.buscarPorEmail(email);

        if (pessoa != null && HashUtil.hashSenha(senha).equals(pessoa.getSenha())) {
            request.getSession().setAttribute("usuario", pessoa);
            response.sendRedirect("dashboard.jsp");
        } else {
            request.setAttribute("erro", "Email ou senha inválidos");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}