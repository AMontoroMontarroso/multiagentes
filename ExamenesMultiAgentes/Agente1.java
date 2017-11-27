package laboratorio.examen;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import jade.core.AID;

public class Agente1 extends Agent{

	//Todos los comportamientos

	private Behaviour comp;
	
	protected void setup(){

		comp = new ComportamientoAgente1();
		addBehaviour(comp);
	}

	public class ComportamientoAgente1 extends Behaviour{

		private long aleatorio;
		private int numero;

		public void onStart(){

			aleatorio = (long) (Math.random()*10000+1);
			System.out.println("Preparandose para enviar un mensaje al Agente2");
			AID id = new AID();
			id.setLocalName("Agente2");

			ACLMessage mensaje = new ACLMessage(ACLMessage.REQUEST);

			mensaje.setSender(getAID());
			mensaje.addReceiver(id);
			mensaje.setContent(aleatorio+"");
			send(mensaje);

		}

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