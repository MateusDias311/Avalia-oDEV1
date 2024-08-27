package br.com.soc.sistema.dao.exames;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.soc.sistema.dao.Dao;
import br.com.soc.sistema.vo.ExameVo;

public class ExameDao extends Dao {
	
	public void insertExame(ExameVo exameVo){
		StringBuilder query = new StringBuilder("INSERT INTO exame (nm_exame) values (?)");
		try(
			Connection con = getConexao();
			PreparedStatement  ps = con.prepareStatement(query.toString())){
			
			int i=1;
			ps.setString(i++, exameVo.getNome());
			ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<ExameVo> findAllExames(){
		StringBuilder query = new StringBuilder("SELECT rowid id, nm_exame nome FROM exame");
		try(
			Connection con = getConexao();
			PreparedStatement  ps = con.prepareStatement(query.toString());
			ResultSet rs = ps.executeQuery()){
			
			ExameVo vo =  null;
			List<ExameVo> exames = new ArrayList<>();
			while (rs.next()) {
				vo = new ExameVo();
				vo.setRowid(rs.getString("id"));
				vo.setNome(rs.getString("nome"));	
				
				exames.add(vo);
			}
			return exames;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return Collections.emptyList();
	}
	
	public List<ExameVo> findAllByNome(String nome){
		StringBuilder query = new StringBuilder("SELECT rowid id, nm_exame nome FROM exame ")
								.append("WHERE lower(nm_exame) like lower(?)");
		
		try(Connection con = getConexao();
			PreparedStatement ps = con.prepareStatement(query.toString())){
			int i = 1;
			
			ps.setString(i, "%"+nome+"%");
			
			try(ResultSet rs = ps.executeQuery()){
				ExameVo vo =  null;
				List<ExameVo> exames = new ArrayList<>();
				
				while (rs.next()) {
					vo = new ExameVo();
					vo.setRowid(rs.getString("id"));
					vo.setNome(rs.getString("nome"));	
					
					exames.add(vo);
				}
				return exames;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}		
		return Collections.emptyList();
	}
	
	public ExameVo findByCodigo(Long codigo){
		StringBuilder query = new StringBuilder("SELECT rowid id, nm_exame nome FROM exame ")
								.append("WHERE rowid = ?");
		
		try(Connection con = getConexao();
			PreparedStatement ps = con.prepareStatement(query.toString())){
			 ps.setLong(1, codigo);
			
			 ps.setLong(1, codigo);
			
			try(ResultSet rs = ps.executeQuery()){
				ExameVo vo =  null;
				
				while (rs.next()) {
					vo = new ExameVo();
					vo.setRowid(rs.getString("id"));
					vo.setNome(rs.getString("nome"));	
				}
				return vo;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}		
		return null;
	}
	
	
	//**********EXCLUSÃO DE EXAMES**********
	
	public boolean deleteExame(int cod) {
        String query = "DELETE FROM exame WHERE rowid = ?";
        try (Connection con = getConexao();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, cod);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao executar a exclusão do exame.", e);
        }
    }
		
	public boolean excluindoExame(String rowid) {
		
		int cod;
		if (rowid == null || rowid.isEmpty()) {
	        return false; 
	    }
	    try {
	        cod = Integer.parseInt(rowid);
	    } catch (NumberFormatException e) {
	        return false;  
	    }
	    return deleteExame(cod);  
	}
	
	
	//**********EDIÇÃO DE EXAMES**********

	public void updateExame(ExameVo exameVo) {
	    String query = "UPDATE exame SET nm_exame = ? WHERE rowid = ?";
	    try (Connection con = getConexao();
	         PreparedStatement ps = con.prepareStatement(query)) {
	        ps.setString(1, exameVo.getNome());
	        ps.setLong(2, exameVo.getCodigoExame());
	        ps.setLong(3, Long.parseLong(exameVo.getRowid()));
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new RuntimeException("Erro ao atualizar o exame.", e);
	    }
	}
	
	//**********BLOQUEIO DE EXCLUSÃO DE EXAMES VINCULADOS**********

	
	 public boolean isExameVinculado(String rowidExame) {
	        boolean vinculado = false;
	        String sql = "SELECT COUNT(*) FROM exameRealizado WHERE codigo_exame = ?";
	        
	        try (Connection conn = getConexao();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setString(1, rowidExame);
	            try (ResultSet rs = stmt.executeQuery()) {
	                if (rs.next()) {
	                    vinculado = rs.getInt(1) > 0;
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw new RuntimeException("Erro ao verificar vínculo do exame.", e);
	        }
	        return vinculado;
	    }
	
}