package br.com.dao;

import java.util.List;

import br.com.models.Procedure;

public interface ProcedureDao extends DaoGenerico <Procedure, Long>{
	
	public List consultar();
	public Procedure consultaId(Long id);
}
