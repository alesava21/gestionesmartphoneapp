package it.prova.gestionesmartphoneapp.dao.smartphone;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.prova.gestionesmartphoneapp.model.Smartphone;

public class SmartphoneDAOImpl implements SmartphoneDAO {

	private EntityManager entityManager;

	@Override
	public List<Smartphone> list() throws Exception {
		return entityManager.createQuery("from Smartphone", Smartphone.class).getResultList();
	}

	@Override
	public Smartphone get(Long id) throws Exception {
		return entityManager.find(Smartphone.class, id);
	}

	@Override
	public void update(Smartphone smartphoneInstance) throws Exception {
		if (smartphoneInstance == null) {
			throw new Exception("Problema valore in input");
		}
		smartphoneInstance = entityManager.merge(smartphoneInstance);
	}

	@Override
	public void insert(Smartphone smartphoneInstance) throws Exception {
		if (smartphoneInstance == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(smartphoneInstance);
	}

	@Override
	public void delete(Smartphone smartphoneInstance) throws Exception {
		if (smartphoneInstance == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.remove(entityManager.merge(smartphoneInstance));
	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public Smartphone findByIdFetchingApps(Long id) throws Exception {
		TypedQuery<Smartphone> query = entityManager.createQuery(
				"select s FROM Smartphone s left join fetch s.apps a where s.id = :idSmartphone", Smartphone.class);
		query.setParameter("idSmartphone", id);
		return query.getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public void deleteAllSmartphonesFromJoinTable() throws Exception {
		entityManager.createNativeQuery("delete from smartphone_app").executeUpdate();

	}

	@Override
	public void deleteSmartphoneFromJoinTable(Long idSmartphone) throws Exception {
		entityManager.createNativeQuery("delete from smartphone_app where smartphone_id = ?1")
				.setParameter(1, idSmartphone).executeUpdate();

	}

}