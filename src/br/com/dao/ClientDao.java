package br.com.dao;

import java.util.List;

import br.com.models.Client;
import br.com.models.ClientSearch;
import br.com.models.Procedure;

public interface ClientDao extends DaoGenerico <Client, Long>{
	
	public List consultar();
	public List consultarReport(ClientSearch client);
	public Client consultaToImport(Long atendimento, Procedure procedure);
}

