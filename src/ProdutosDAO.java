/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Isadora
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import java.util.List;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public ProdutosDAO() {
        conectaDAO conexao = new conectaDAO();
        conn = conexao.connectDB();
    }
    
    public void cadastrarProduto(ProdutosDTO produto){

    String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";

    try {

        conn = new conectaDAO().connectDB();

        prep = conn.prepareStatement(sql);

        prep.setString(1, produto.getNome());
        prep.setDouble(2, produto.getValor());
        prep.setString(3, produto.getStatus());

        prep.execute();
        prep.close();

        JOptionPane.showMessageDialog(null, "Produto salvo com sucesso!");

    } catch (Exception erro) {

        JOptionPane.showMessageDialog(null, "Erro ao salvar produto: " + erro);

    }
}

    
    public ArrayList<ProdutosDTO> listarProdutos() {

        String sql = "SELECT * FROM produtos";
        ArrayList<ProdutosDTO> lista = new ArrayList<>();

        try {
            conn = new conectaDAO().connectDB();
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();

            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();

                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getDouble("valor"));
                produto.setStatus(resultset.getString("status"));

                lista.add(produto);
            }

            prep.close();

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + erro);
        }

        return lista;
    }

    public boolean venderProduto(int id) {
        
        System.out.println("ID recebido para vender: " + id);

        String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            int resultado = stmt.executeUpdate();
            stmt.close();

            return resultado > 0;

        } catch (SQLException erro) {
            System.out.println("Erro ao vender produto: " + erro.getMessage());
            return false;
        }
    }

     public List<ProdutosDTO> listarProdutosVendidos() {

        List<ProdutosDTO> lista = new ArrayList<>();

        String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                ProdutosDTO p = new ProdutosDTO();

                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setValor(rs.getDouble("valor"));
                p.setStatus(rs.getString("status"));

                lista.add(p);
            }

            rs.close();
            stmt.close();

        } catch (SQLException erro) {
            System.out.println("Erro ao listar produtos vendidos: " + erro.getMessage());
        }

        return lista;
    }

}

    
        


