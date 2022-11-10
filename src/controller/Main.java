package controller;

import model.Playroom;
import view.MainWindow;

import javax.swing.SwingUtilities;

/**
 * Main principal del programa
 * s'ocupa de crear el controlador principal d'aquest programa servidor
 * @author Team Troner (grupC3)
 * @version v_final
 */

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// --------------------------
				// CREACIÓ DE LA VISTA PRINCIPAL
				// --------------------------
				// Crea la vista principal
				MainWindow view = new MainWindow(ConfFileManager.loadConfiguration());

				// --------------------------
				// CREACIÓ DEL MODEL
				// --------------------------
				// Crea el model
				Playroom model = new Playroom();

				// --------------------------
				// CREACIÓ DELS CONTROLADORS (i establiment de les relacions C->V i C->M)
				// --------------------------
				MainController mainController = new MainController(model, view);

				// --------------------------
				// Estableix la relació V->C (el controlador gestionarà els esdeveniments que generi la vista)
				// --------------------------
				view.registerController(mainController);

				// Finalment, fem la vista principal visible
				view.setVisible(true);
			}
		});
	}
}