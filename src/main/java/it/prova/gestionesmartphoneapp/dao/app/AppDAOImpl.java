package it.prova.gestionesmartphoneapp.dao.app;

import java.util.List;

import javax.persistence.EntityManager;

import it.prova.gestionesmartphoneapp.model.App;

public class AppDAOImpl implements AppDAO {

	private EntityManager entityManager;

	@Override
	public List<App> list() throws Exception {
		return entityManager.createQuery("from App", App.class).getResultList();
	}

	@Override
	public App get(Long id) throws Exception {
		return entityManager.find(App.class, id);
	}

	@Override
	public void update(App appInstance) throws Exception {
		if (appInstance == null) {
			throw new Exception("Problema valore in input");
		}
		appInstance = entityManager.merge(appInstance);

	}

	@Override
	public void insert(App appInstance) throws Exception {
		if (appInstance == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(appInstance);
	}

	@Override
	public void delete(App appInstance) throws Exception {
		if (appInstance == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.remove(entityManager.merge(appInstance));
	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}