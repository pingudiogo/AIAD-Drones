package drone;

import java.awt.geom.Point2D;
import java.util.TreeSet;

import request.*;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class Drone extends Agent {
	
	private TreeSet<Request> requests;
	
	private Point2D position;
	private float weightCapacity;
	private float baseVelocity;

	public static AID[] getDrones(Agent agent) {
		AID[] drones = new AID[0];
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("delivery-service");
		template.addServices(sd);
		try {
			DFAgentDescription[] result = DFService.search(agent, template);
			System.out.println("Found the following drone agents:");
			drones = new AID[result.length];
			for (int i = 0; i < result.length; ++i) {
				drones[i] = result[i].getName();
			}
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
		return drones;
	}
	
	public void setup() {
		
		System.out.println(getLocalName() + ": drone created");
		
		registerDroneService();
		
		/* r = new Random();
		xPosition = r.nextInt(20);
		yPosition = r.nextInt(20);*/
		
		//TODO adding behaviours
		//addBehaviour(new OfferRequestsServer());

	}
	
	
	public void registerDroneService(){
		
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("delivery-service");
		sd.setName("UPS");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}

	}
	
	public void takeDown() {
		try {
			DFService.deregister(this);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
		System.out.println(getLocalName() + ": drone killed");
	}

	public Point2D getCurrentPosition() {
		return position;
	}
	
	public float getWeightCapacity() {
		return weightCapacity;
	}

}
