package laboratorio.examen;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import jade.core.AID;

public class Agente2 extends Agent{

	private Behaviour comp;

	protected void setup(){
		comp = new ComportamientoAgente2();
		addBehaviour(comp);
	}
	public class ComportamientoAgente2 extends Behaviour{

		private int numero;

		public void action(){

			MessageTemplate filtroEmisor = MessageTemplate.MatchSender(new AID("Agente1",AID.ISLOCALNAME));
			ACLMessage mensaje2 = blockingReceive(filtroEmisor);
			if(mensaje2 != null){
				System.out.println("acaba de recibir el siguiente mensaje del Agente1");
				numero = Integer.parseInt(mensaje2.getContent());
				System.out.println(numero);

				//Envia contestacion
				System.out.println(": Enviando contestacion al Agente3");
				ACLMessage respuesta = new ACLMessage(ACLMessage.INFORM);
				numero = numero/2;
				respuesta.setContent(numero+"");
				respuesta.addReceiver(new AID("Agente3",AID.ISLOCALNAME));
				send(respuesta);
				System.out.println(respuesta);
			}

			MessageTemplate filtroEmisor1 = MessageTemplate.MatchSender(new AID("Agente3",AID.ISLOCALNAME));
			ACLMessage mensaje3 = blockingReceive(filtroEmisor1);
			if(mensaje3 != null){
				System.out.println(": acaba de recibir el siguiente mensaje del Agente3");
				numero = Integer.parseInt(mensaje2.getContent());
				System.out.println(numero);

				//Envia contestacion
				System.out.println(": Enviando contestacion al Agente1");
				ACLMessage respuesta3 = new ACLMessage(ACLMessage.INFORM);
				numero = numero/2;
				respuesta3.setContent(numero+"");
				respuesta3.addReceiver(new AID("Agente1",AID.ISLOCALNAME));
				send(respuesta3);
				System.out.println(respuesta3);
			}

		}
		public boolean done(){
			return (numero==0);
		}
		public int onEnd(){
			myAgent.doDelete();
			return 0;
		}
	}
}

