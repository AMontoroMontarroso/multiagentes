package laboratorio.examen;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import jade.core.AID;

public class Agente3 extends Agent{

	private Behaviour comp;

	protected void setup(){
		comp = new ComportamientoAgente3();
		addBehaviour(comp);
	}

	protected void takeDown(){
		System.out.println("Liberando recursos");
	}
	public class ComportamientoAgente3 extends Behaviour{

		private int numero;

		public void action(){

			MessageTemplate filtroEmisor = MessageTemplate.MatchSender(new AID("Agente2",AID.ISLOCALNAME));
			ACLMessage mensaje2 = blockingReceive(filtroEmisor);
			if(mensaje2 != null){
				System.out.println(getLocalName() + ": acaba de recibir el siguiente mensaje del Agente2");
				numero = Integer.parseInt(mensaje2.getContent());
				System.out.println(numero);

				//Envia contestacion
				System.out.println(getLocalName() +": Enviando contestacion al Agente2");
				ACLMessage respuesta = new ACLMessage(ACLMessage.INFORM);
				numero = numero/2;
				respuesta.setContent(numero+"");
				respuesta.addReceiver(mensaje2.getSender());
				send(respuesta);
				System.out.println(respuesta);
			}

		}

		public boolean done(){
			return (numero == 0);
		}

		public int onEnd(){
			myAgent.doDelete();
			return 0;
		}
	}
}