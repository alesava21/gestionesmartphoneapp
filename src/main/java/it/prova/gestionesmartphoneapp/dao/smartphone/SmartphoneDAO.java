package it.prova.gestionesmartphoneapp.dao.smartphone;

import it.prova.gestionesmartphoneapp.dao.IBaseDAO;
import it.prova.gestionesmartphoneapp.model.Smartphone;

public interface SmartphoneDAO extends IBaseDAO<Smartphone> {
	
	public Smartphone findByIdFetchingApps(Long id) throws Exception;

	public void deleteAllSmartphonesFromJoinTable() throws Exception;
	
	public void deleteSmartphoneFromJoinTable(Long idSmartphone) throws Exception;

}