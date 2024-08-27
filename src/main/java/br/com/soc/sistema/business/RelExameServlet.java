package br.com.soc.sistema.business;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.soc.sistema.action.relExame.relExameAction; // Importação correta

@WebServlet("/relatorioExames") // Mapeamento do servlet
public class RelExameServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Configurações do banco de dados
    private static final String DB_URL = "jdbc:mysql://localhost:3306/seu_banco_de_dados"; // Substitua pelo nome do seu banco de dados
    private static final String DB_USER = "seu_usuario"; // Substitua pelo seu usuário de banco de dados
    private static final String DB_PASSWORD = "sua_senha"; // Substitua pela sua senha de banco de dados

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String dataInicial = request.getParameter("dataInicial");
        String dataFinal = request.getParameter("dataFinal");

        // Formato de data para conversão, se necessário
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        List<relExameAction> exames = new ArrayList<>();

        try (Connection conn = getConnection()) { // Obtendo a conexão usando o método interno getConnection()
            String query = "SELECT * FROM exameRealizado WHERE dataRealizacao BETWEEN ? AND ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, dataInicial);
                stmt.setString(2, dataFinal);

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        relExameAction exame = new relExameAction();
                        exame.setId(rs.getInt("id")); // Define o ID
                        exame.setNome(rs.getString("nome")); // Define o Nome
                        exame.setDataRealizacao(rs.getDate("dataRealizacao")); // Define a Data de Realização
                        // Preencha outros campos conforme necessário
                        exames.add(exame);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Trate erros de SQL aqui
        }

        // Adiciona a lista de exames ao request e redireciona para a página de visualização
        request.setAttribute("exames", exames);
        request.getRequestDispatcher("relatorioExamesView.jsp").forward(request, response);
    }

    // Método interno para obter a conexão com o banco de dados
    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Carrega o driver do MySQL
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("Driver de banco de dados não encontrado.");
        }

        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // Método para exportar para Excel (caso necessário)
    private void exportarParaExcel(List<relExameAction> exames, HttpServletResponse response) throws IOException {
        // Implementação do método conforme necessário
    }
}
