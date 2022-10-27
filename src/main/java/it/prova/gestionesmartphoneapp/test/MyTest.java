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
					"****************************** inizio batteria di test ********************************************");
			System.out.println(
					"*************************************************************************************************");
			System.out.println("In tabella Smartphone ci sono " + smartphoneService.listAll().size() + " elementi.");
			System.out.println("In tabella App ci sono " + appService.listAll().size() + " elementi.");

			testInserisciSmartphone(smartphoneService);
			System.out.println("In tabella Smartphone ci sono " + smartphoneService.listAll().size() + " elementi.");

			testInsertApp(appService);
			System.out.println("In tabella App ci sono " + appService.listAll().size() + " elementi.");

			testAggiornamentoApp(appService);

			testAggiornaSistemaOperativo(smartphoneService);

			testInstallazioneDellApplicazione(smartphoneService, appService);

			testInstallazioneApp(appService, smartphoneService);

			testDisinstallaApp(appService, smartphoneService);

			testEliminaTelefonoAssociatoDueApp(smartphoneService, appService);
			
			System.out.println(
					"****************************** fine batteria di test ********************************************");
			System.out.println(
					"*************************************************************************************************");
			System.out.println("In tabella Smartphone ci sono " + smartphoneService.listAll().size() + " elementi.");
			System.out.println("In tabella App ci sono " + appService.listAll().size() + " elementi.");
			
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
		smartphoneServiceInstance.rimuovi(nuovoSmartphone.getId());
		System.out.println(".......testInserisciSmartphone fine: PASSED.............");
	}

	private static void testInsertApp(AppService appServiceInstance) throws Exception {
		System.out.println(".......testInsertApp inizio.............");
		App nuovApp = new App("baseCamp", new Date(), new Date(), "3.2.0");
		appServiceInstance.inserisciNuovo(nuovApp);
		if (nuovApp.getId() == null) {
			throw new RuntimeException("testAggiornamentoApp failed.");
		}
		appServiceInstance.rimuovi(nuovApp.getId());
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
		appServiceInstance.rimuovi(nuovaApp.getId());
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
		smartphoneServiceInstance.rimuovi(nuovoSmartphone.getId());
		System.out.println(".......testAggiornaSistemaOperativo fine: PASSED.............");
	}

	private static void testInstallazioneDellApplicazione(SmartphoneService smartphoneServiceInstance,
			AppService appServiceInstance) throws Exception {
		System.out.println(".......testInstallazioneDellApplicazione inizio.............");

		Smartphone nuovoTelefono = new Smartphone("Apple", "Iphone", 1400, "14.3.4");
		smartphoneServiceInstance.inserisciNuovo(nuovoTelefono);

		App nuovaApplicazione = new App("BaseCamp", new Date(), new Date(), "3.2.1");
		appServiceInstance.inserisciNuovo(nuovaApplicazione);
		
		smartphoneServiceInstance.rimuovi(nuovoTelefono.getId());
		appServiceInstance.rimuovi(nuovaApplicazione.getId());
		System.out.println(
				".......	private static void testInstallazioneDellApplicazione(SmartphoneService smartphoneServiceInstance, AppService appServiceInstance) throws Exception {\n"
						+ " fine: PASSED.............");
	}

	private static void testInstallazioneApp(AppService appServiceInstance, SmartphoneService smartphoneServiceInstance)
			throws Exception {
		System.out.println(".......testInstallazioneApp inizio.............");

		Smartphone nuovoTelefono = new Smartphone("Apple", "Iphone", 1400, "14.3.4");
		smartphoneServiceInstance.inserisciNuovo(nuovoTelefono);

		if (nuovoTelefono.getId() == null)
			throw new RuntimeException("testAggiornamentoVersioneOSSmartphone fallito: smartphone non inserito. ");

		App nuovaApplicazione = new App("BaseCamp", new Date(), new Date(), "3.2.1");
		appServiceInstance.inserisciNuovo(nuovaApplicazione);

		if (nuovaApplicazione.getId() == null)
			throw new RuntimeException("testAggiornamentoVersioneAppEDataAggiornamento FAILED: app non inserita! ");

		smartphoneServiceInstance.aggiungiApp(nuovoTelefono, nuovaApplicazione);

		Smartphone smartphoneReloaded = smartphoneServiceInstance.caricaSingoloElementoEagerApps(nuovoTelefono.getId());

		if (smartphoneReloaded.getApps().isEmpty())
			throw new RuntimeException("testInstallazioneApp FAILED: installazione non avvenuta correttamente.");

		smartphoneServiceInstance.rimuoviTuttiGliSmartphoneDallaTabellaDiJoin();
		smartphoneServiceInstance.rimuovi(nuovoTelefono.getId());
		appServiceInstance.rimuovi(nuovaApplicazione.getId());

		System.out.println(".......testInstallazioneApp fine: PASSED.............");

	}

	private static void testDisinstallaApp(AppService appServiceInstance, SmartphoneService smartphoneServiceInstance)
			throws Exception {
		System.out.println(".......testDisinstallazioneApp inizio.............");

		Smartphone nuovoTelefono = new Smartphone("Apple", "Iphone", 1400, "14.3.4");
		smartphoneServiceInstance.inserisciNuovo(nuovoTelefono);

		if (nuovoTelefono.getId() == null)
			throw new RuntimeException("testDisinstallazioneApp fallito: smartphone non inserito. ");

		App nuovaApplicazione = new App("BaseCamp", new Date(), new Date(), "3.2.1");
		appServiceInstance.inserisciNuovo(nuovaApplicazione);

		if (nuovaApplicazione.getId() == null)
			throw new RuntimeException("testDisinstallazioneApp FAILED: app non inserita! ");

		smartphoneServiceInstance.aggiungiApp(nuovoTelefono, nuovaApplicazione);

		Smartphone smartphoneReloaded = smartphoneServiceInstance.caricaSingoloElementoEagerApps(nuovoTelefono.getId());

		if (smartphoneReloaded.getApps().isEmpty())
			throw new RuntimeException("testDisinstallazioneApp FAILED: installazione non avvenuta correttamente.");

		appServiceInstance.rimuoviAppDallaTabellaDiJoin(nuovaApplicazione.getId());
		smartphoneReloaded = smartphoneServiceInstance.caricaSingoloElementoEagerApps(nuovoTelefono.getId());

		if (!smartphoneReloaded.getApps().isEmpty())
			throw new RuntimeException("testDisinstallazioneApp FAILED: disinstallazione non avvenuta correttamente.");

		// reset tabelle
		smartphoneServiceInstance.rimuovi(nuovoTelefono.getId());
		appServiceInstance.rimuovi(nuovaApplicazione.getId());

		System.out.println(".......testDisinstallazioneApp fine: PASSED.............");
	}

	private static void testEliminaTelefonoAssociatoDueApp(SmartphoneService smartphoneServiceInstance,
			AppService appServiceInstance) throws Exception {
		System.out.println(".......testDisinstallazioneApp inizio.............");

		Smartphone nuovoTelefono = new Smartphone("Apple", "Iphone", 1400, "14.3.4");
		smartphoneServiceInstance.inserisciNuovo(nuovoTelefono);

		if (nuovoTelefono.getId() == null)
			throw new RuntimeException("testDisinstallazioneApp fallito: smartphone non inserito. ");

		App nuovaApplicazione = new App("BaseCamp", new Date(), new Date(), "3.2.1");
		appServiceInstance.inserisciNuovo(nuovaApplicazione);
		
		App nuovaApplicazione1 = new App("BaseCamp", new Date(), new Date(), "3.2.1");
		appServiceInstance.inserisciNuovo(nuovaApplicazione1);

		if (nuovaApplicazione.getId() == null)
			throw new RuntimeException("testDisinstallazioneApp FAILED: app non inserita! ");

		smartphoneServiceInstance.aggiungiApp(nuovoTelefono, nuovaApplicazione);

		Smartphone smartphoneReloaded = smartphoneServiceInstance.caricaSingoloElementoEagerApps(nuovoTelefono.getId());

		if (smartphoneReloaded.getApps().isEmpty())
			throw new RuntimeException("testDisinstallazioneApp FAILED: installazione non avvenuta correttamente.");

		appServiceInstance.rimuoviAppDallaTabellaDiJoin(nuovaApplicazione.getId());

		smartphoneReloaded = smartphoneServiceInstance.caricaSingoloElementoEagerApps(nuovoTelefono.getId());

		if (!smartphoneReloaded.getApps().isEmpty())
			throw new RuntimeException("testDisinstallazioneApp FAILED: disinstallazione non avvenuta correttamente.");

		// reset tabelle
		smartphoneServiceInstance.rimuovi(nuovoTelefono.getId());
		appServiceInstance.rimuovi(nuovaApplicazione.getId());
		appServiceInstance.rimuovi(nuovaApplicazione1.getId());

		System.out.println(".......testDisinstallazioneApp fine: PASSED.............");

	}
}
