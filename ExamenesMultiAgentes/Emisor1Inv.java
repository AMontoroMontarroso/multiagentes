package laboratorio.sesion4;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import jade.core.AID;

public class Emisor1 extends Agent{

	private Behaviour comp;

	protected void setup(){
		comp = new ComportamientoEmisor1();
		addBehaviour(comp);
	}

	protected void takeDown(){
		System.out.println("Liberando recursos");
	}

	public class ComportamientoEmisor1 extends Behaviour{

		private int aleatorio;
		private boolean fin;

		public void onStart(){
			aleatorio = (int) (Math.random()*100);
			fin = false;

			AID id = new AID();
			id.setLocalName("Receptor");

			ACLMessage mensaje = new ACLMessage(ACLMessage.REQUEST);

			mensaje.setSender(getAID());
			mensaje.addReceiver(id);
			mensaje.setContent(aleatorio+"");
			send(mensaje);
			block(500);
		}

		public void action(){
			//va a recibir un mensaje de finalizacion
			MessageTemplate filtro = MessageTemplate.MatchSender(new AID("Receptor",AID.ISLOCALNAME));
			ACLMessage mensaje2 = blockingReceive(filtro);
			if(mensaje2 != null){
				fin = Boolean.parseBoolean(mensaje2.getContent());
				System.out.println(fin);
			}
		}

		public boolean done(){
			return fin;
		}

		public int onEnd(){
			myAgent.doDelete();
			return 0;
		}
	}

}