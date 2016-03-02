package br.com.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import br.com.dao.AgreementDaoImpl;
import br.com.dao.ClientDao;
import br.com.dao.ClientDaoImpl;
import br.com.dao.ProcedureDaoImpl;
import br.com.models.Client;
import br.com.models.ClientSearch;


@ManagedBean
@SessionScoped
public class ClientBean implements Serializable{
	
	private Logger logger = Logger.getLogger(getClass());
	private Client client = new Client();
	private List<Client> clients = new ArrayList<Client>();
	private List<Client> clientsSearch = new ArrayList<Client>();
    private ClientDao dao = new ClientDaoImpl();
    private long selectedProcedure;
    private long selectedAgreement;
    private Client selectClient = new Client();
    private static SelectItem[] pgOptions;
    private static String[] pgValues;  
    private int totalClients;
    private ProcedureDaoImpl proc = new ProcedureDaoImpl();
    private AgreementDaoImpl agree = new AgreementDaoImpl();
    private ClientSearch clientSearch = new ClientSearch();
    private int totalClientsSearch;
    
    static {  
        pgValues = new String[2];
        pgValues[0] = "Não";
        pgValues[1] = "Sim";
    }    		

	public ClientBean() {
		clients = dao.consultar();
		setTotalClients(clients.size());
		pgOptions = createFilterOptions(pgValues);
    }

    public void save() {
    	try{
    		client.setId_atend(client.getMatsame());
    		client.setAgreement(agree.consultaId(selectedAgreement));
    		client.setProcedure(proc.consultaId(selectedProcedure));
    		client.setTelephone(client.getTelephone().replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("\\-", "").replaceAll(" ", "").trim());
    		client.setRepasse("000000");
    		dao.save(client);
    		client = new Client();
    		clients = dao.consultar();
    		geraMsg("Registro salvo com sucesso", null);
    	}catch(Exception ex){
    		geraMsg("Erro ao salvar registro", ex);
    	}
    			
    }

    public void edit() {
    	try{
    		selectClient.setAgreement(agree.consultaId(selectedAgreement));
    		selectClient.setProcedure(proc.consultaId(selectedProcedure));
    		selectClient.setTelephone(selectClient.getTelephone().replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("\\-", "").replaceAll(" ", "").trim());
    		dao.save(selectClient);
    		geraMsg("Registro salvo com sucesso", null);
    	}catch(Exception ex){
    		geraMsg("Erro ao salvar registro", ex);
    	}
    }

    public void delete() {
    	try{
    		dao.delete(client);
    		clients = dao.consultar();
    		setTotalClients(clients.size());
    		geraMsg("Registro Excluido com Sucesso", null);
    	}catch(Exception ex){
    		geraMsg("Erro ao excluir registro", ex);
    	}
    }
    
    public void search(){
    	try{
    		if(selectedProcedure != 0){
    			clientSearch.setProcedure(proc.consultaId(selectedProcedure));
    		}
    		if(selectedAgreement != 0){
    			clientSearch.setAgreement(agree.consultaId(selectedAgreement));
    		}
    		clientsSearch = dao.consultarReport(clientSearch);
    		setTotalClientsSearch(clientsSearch.size());
    		clientSearch.setAgreement(null);
    		clientSearch.setProcedure(null);
    		geraMsg("Resgistros consultados com sucesso", null);
    	}catch (Exception ex){
    		geraMsg("Erro ao consultar registros", ex);
    	}
    }

    private SelectItem[] createFilterOptions(String[] data)  {  
    	SelectItem[] options = new SelectItem[data.length + 1];  
    	options[0] = new SelectItem("", "Select");  
    	for(int i = 0; i < data.length; i++) {  
    		options[i + 1] = new SelectItem(i, data[i]);  
    	}  
    	return options;  
    } 

    private void geraMsg(String mensagem, Exception ex){
    	if(ex != null){
    		logger.error(mensagem, ex);
    	}else{
    		logger.info(mensagem);
    	}
    	FacesContext context = FacesContext.getCurrentInstance();
    	context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso:", mensagem));
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List getClients() {
        return clients;
    }

    public void setClients(List clients) {
        this.clients = clients;
    }
    
    public long getSelectedProcedure() {
		return selectedProcedure;
	}

	public void setSelectedProcedure(long selectedProcedure) {
		this.selectedProcedure = selectedProcedure;
	}
	
	public long getSelectedAgreement() {
		return selectedAgreement;
	}

	public void setSelectedAgreement(long selectedAgreement) {
		this.selectedAgreement = selectedAgreement;
	}
	
	public SelectItem[] getPgOptions() {
		return pgOptions;
	}

	public void setPgOptions(SelectItem[] pgOptions) {
		this.pgOptions = pgOptions;
	}

	public Client getSelectClient() {
		return selectClient;
	}

	public void setSelectClient(Client selectClient) {
		setSelectedAgreement(selectClient.getAgreement().getId());
		setSelectedProcedure(selectClient.getProcedure().getId());
		this.selectClient = selectClient;
	}

	public int getTotalClients() {
		return totalClients;
	}

	public void setTotalClients(int totalClients) {
		this.totalClients = totalClients;
	}

	public List<Client> getClientsSearch() {
		return clientsSearch;
	}

	public void setClientsSearch(List<Client> clientsSearch) {
		this.clientsSearch = clientsSearch;
	}

	public ClientSearch getClientSearch() {
		return clientSearch;
	}

	public void setClientSearch(ClientSearch clientSearch) {
		this.clientSearch = clientSearch;
	}

	public int getTotalClientsSearch() {
		return totalClientsSearch;
	}

	public void setTotalClientsSearch(int totalClientsSearch) {
		this.totalClientsSearch = totalClientsSearch;
	}
	
	
}
