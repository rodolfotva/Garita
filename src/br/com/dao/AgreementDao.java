package br.com.dao;

import java.util.List;

import br.com.models.Agreement;

public interface AgreementDao  extends DaoGenerico <Agreement, Long>{
	
	public List consultar();
	public Agreement consultaId(Long id);
	public Agreement consultaDescription(String description);

}