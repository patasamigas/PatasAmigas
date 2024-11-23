package dao;
import model.Pessoa;
import util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaDAO {

    public void inserir(Pessoa pessoa) {
        String sql = "INSERT INTO pessoa (nome, data_nascimento, genero, cpf, endereco, telefone, email, senha) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pessoa.getNome());
            stmt.setDate(2, Date.valueOf(pessoa.getDataNascimento()));
            stmt.setString(3, pessoa.getGenero());
            stmt.setString(4, pessoa.getCpf());
            stmt.setString(5, pessoa.getEndereco());
            stmt.setString(6, pessoa.getTelefone());
            stmt.setString(7, pessoa.getEmail());
            stmt.setString(8, pessoa.getSenha());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir pessoa", e);
        }
    }

    public Pessoa buscarPorEmail(String email) {
        String sql = "SELECT * FROM pessoa WHERE email = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extrairPessoa(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pessoa", e);
        }
    }

    private Pessoa extrairPessoa(ResultSet rs) throws SQLException {
        Pessoa pessoa = new Pessoa();
        pessoa.setId((long) rs.getInt("id"));
        pessoa.setNome(rs.getString("nome"));
        pessoa.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
        pessoa.setGenero(rs.getString("genero"));
        pessoa.setCpf(rs.getString("cpf"));
        pessoa.setEndereco(rs.getString("endereco"));
        pessoa.setTelefone(rs.getString("telefone"));
        pessoa.setEmail(rs.getString("email"));
        pessoa.setSenha(rs.getString("senha"));
        return pessoa;
    }
}