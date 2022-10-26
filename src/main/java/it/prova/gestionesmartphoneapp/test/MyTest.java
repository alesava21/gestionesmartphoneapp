package it.prova.gestionesmartphoneapp.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import it.prova.gestionesmartphoneapp.dao.EntityManagerUtil;
import it.prova.gestionesmartphoneapp.model.App;
import it.prova.gestionesmartphoneapp.model.Smartphone;
import it.prova.gestionesmartphoneapp.service.AppService;
import it.prova.gestionesmartphoneapp.service.MyServiceFactory;
import it.prova.gestionesmartphoneapp.service.SmartphoneService;

public class MyTest {
	public static void main(String[] args) {

		SmartphoneService smartphoneService = MyServiceFactory.getSmartphoneServiceInstance();
		AppService appService = MyServiceFactory.getAppServiceInstance();

		try {

			System.out.println(
					"****************************** fine batteria di test ********************************************");
			System.out.println(
					"*************************************************************************************************");
			System.out.println("In tabella Genere ci sono " + smartphoneService.listAll().size() + " elementi.");
			System.out.println("In tabella Genere ci sono " + appService.listAll().size() + " elementi.");

			testInserisciSmartphone(smartphoneService);
			System.out.println("In tabella Genere ci sono " + smartphoneService.listAll().size() + " elementi.");

			testInsertApp(appService);
			System.out.println("In tabella Genere ci sono " + appService.listAll().size() + " elementi.");

			testAggiornamentoApp(appService);

			testAggiornaSistemaOperativo(smartphoneService);
			
			testInstallazioneDellApplicazione(smartphoneService, appService);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			EntityManagerUtil.shutdown();
		}

	}

	private static void testInserisciSmartphone(SmartphoneService smartphoneServiceInstance) throws Exception {
		System.out.println(".......testInserimentoSmartphone inizio.............");
		Smartphone nuovoSmartphone = new Smartphone("Apple", "iPhone", 1000, "14.0.1");
		smartphoneServiceInstance.inserisciNuovo(nuovoSmartphone);
		if (nuovoSmartphone.getId() == null) {
			throw new RuntimeException("testInserimentoNuovoCd fallito ");
		}
		smartphoneServiceInstance.rimuovi(nuovoSmartphone);
		System.out.println(".......testInserisciSmartphone fine: PASSED.............");
	}

	private static void testInsertApp(AppService appServiceInstance) throws Exception {
		System.out.println(".......testInsertApp inizio.............");
		App nuovApp = new App("baseCamp", new Date(), new Date(), "3.2.0");
		appServiceInstance.inserisciNuovo(nuovApp);
		if (nuovApp.getId() == null) {
			throw new RuntimeException("testAggiornamentoApp failed.");
		}
		appServiceInstance.rimuovi(nuovApp);
		System.out.println(".......testInsertApp fine: PASSED.............");
	}

	private static void testAggiornamentoApp(AppService appServiceInstance) throws Exception {
		System.out.println(".......testAggiornamentoApp inizio.............");

		App nuovaApp = new App("baseCamp", new Date(), new SimpleDateFormat("dd/MM/yyyy").parse("21/05/2022"), "3.2.0");
		appServiceInstance.inserisciNuovo(nuovaApp);

		Date nuovaData = new SimpleDateFormat("dd/MM/yyyy").parse("21/10/2022");
		nuovaApp.setDataUltimoAggiornamento(nuovaData);
		nuovaApp.setVersione("3.3.2");
		appServiceInstance.aggiorna(nuovaApp);

		if (!appServiceInstance.caricaSingoloElemento(nuovaApp.getId()).getVersione().equals("3.3.2")
				|| !(appServiceInstance.caricaSingoloElemento(nuovaApp.getId()).getDataUltimoAggiornamento()
						.compareTo(nuovaData) == 0))
			throw new RuntimeException("testAggiornamentoApp failed.");
		appServiceInstance.rimuovi(nuovaApp);
		System.out.println(".......testAggiornamentoApp fine: PASSED.............");
	}

	private static void testAggiornaSistemaOperativo(SmartphoneService smartphoneServiceInstance) throws Exception {
		System.out.println(".......testAggiornaSistemaOperativo inizio.............");
		Smartphone nuovoSmartphone = new Smartphone("Apple", "iPhone", 1000, "14.0.1");
		smartphoneServiceInstance.inserisciNuovo(nuovoSmartphone);

		nuovoSmartphone.setVersioneOS("14.1.2");
		smartphoneServiceInstance.update(nuovoSmartphone);
		if (nuovoSmartphone.getId() == null) {
			throw new RuntimeException("testInserimentoNuovoCd fallito ");
		}
		if (!smartphoneServiceInstance.caricaSingoloElemento(nuovoSmartphone.getId()).getVersioneOS()
				.equals("14.1.2")) {
			throw new RuntimeException("testAggiornamentoSistemaOperativo failed.");
		}
		smartphoneServiceInstance.rimuovi(nuovoSmartphone);
		System.out.println(".......testAggiornaSistemaOperativo fine: PASSED.............");
	}

	private static void testInstallazioneDellApplicazione(SmartphoneService smartphoneServiceInstance,
			AppService appServiceInstance) throws Exception {
		System.out.println(".......testInstallazioneDellApplicazione inizio.............");

		Smartphone nuovoTelefono = new Smartphone("Apple", "Iphone", 1400, "14.3.4");
		smartphoneServiceInstance.inserisciNuovo(nuovoTelefono);

		App nuovaApplicazione = new App("BaseCamp", new Date(), new Date(), "3.2.1");
		appServiceInstance.inserisciNuovo(nuovaApplicazione);
		System.out.println(
				".......	private static void testInstallazioneDellApplicazione(SmartphoneService smartphoneServiceInstance, AppService appServiceInstance) throws Exception {\n"
						+ " fine: PASSED.............");
	}

}
