package droneManagementSystem;

import java.io.IOException;

import Utils.Utils;
import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class Launcher {

	public static void main(String[] args) {
		

		Runtime rt = Runtime.instance();

		Profile p1 = new ProfileImpl();
		ContainerController mainContainer = rt.createMainContainer(p1);

		Profile p2 = new ProfileImpl();
		p2.setParameter(Profile.CONTAINER_NAME, "Drones");
		ContainerController drones = rt.createAgentContainer(p2);

		Profile p3 = new ProfileImpl();
		p3.setParameter(Profile.CONTAINER_NAME, "Clients");
		ContainerController clients = rt.createAgentContainer(p3);

		Profile p4 = new ProfileImpl();
		p4.setParameter(Profile.CONTAINER_NAME, "Warehouses");
		//ContainerController warehouses = rt.createAgentContainer(p4);

		AgentController ac1;
		try {
			ac1 = mainContainer.acceptNewAgent("myRMA", new jade.tools.rma.rma());
			ac1.start();
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}

		/* INIT Drones */
		AgentController ac2;
		try {
			Utils.readFileDrones(Utils.PATH_DRONES);

			for (Object[] drone : Utils.dronesInformation) {
				ac2 = drones.createNewAgent( drone[0].toString(), "drone.Drone", drone);
				//System.out.println("Drones args: "+drone[0].toString()+","+drone[1]+","+drone[2]+","+drone[3]);
				ac2.start();
			}
			System.out.println("\n--- Drones ---");


		} catch (StaleProxyException | IOException e) {
			e.printStackTrace();
		}
		
		/* INIT Clients */
		AgentController ac3;
		try {
			Utils.readFileClients(Utils.PATH_CLIENTS);
			for (Object[] client : Utils.clientsInformation) {
				ac3 = clients.createNewAgent((String) client[0], "client.Client", client);
				ac3.start();
			}
			System.out.println("--- Clients ---");

		} catch (StaleProxyException | IOException e) {
			e.printStackTrace();
		}
		/* INIT Warehouses 
		AgentController ac4;
		try {
			Utils.readFileWarehouses(Utils.PATH_WAREHOUSES);
			for (Object[] warehouse : Utils.warehousesInformation) {
				ac4 = warehouses.createNewAgent((String) warehouse[0], "warehouse.Warehouse", warehouse);
				ac4.start();
			}
			System.out.println("\n--- Warehouses ---\n");

		} catch (StaleProxyException | IOException e) {
			e.printStackTrace();
		}
		*/
		
	
	}

}