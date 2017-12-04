package laboratorio.examen;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import jade.core.AID;

public class Emisor extends Agent{

	private Behaviour comp;

	protected void setup(){

		comp = new EmisorComportamiento();
		addBehaviour(comp);

	}

	protected void takeDown(){

		System.out.println("Liberando Recursos");

	}

	public class EmisorComportamiento extends Behaviour{

		private boolean fin;
		private int contador;
		private int aleatorio;

		public void onStart(){

			fin = false;
			contador = 0;
			//Envia siempre el mismo numero porque solo lo he inicializado una vez
			aleatorio = (int) (Math.random()*1000+1);
			
		}

		public void action(){

			contador++;
			System.out.println(getLocalName()+" esta preparandose el envio numero "+contador);
			System.out.println("El nuemro a enviar es: "+aleatorio);

			AID id = new AID();
			id.setLocalName("Receptor");

			ACLMessage mensaje = new ACLMessage(ACLMessage.REQUEST);

			mensaje.setSender(getAID());
			mensaje.addReceiver(id);
			mensaje.setContent(aleatorio+"");

			send(mensaje);

			MessageTemplate filtro = MessageTemplate.MatchSender(new AID("Receptor",AID.ISLOCALNAME));
			ACLMessage respuesta = blockingReceive(filtro);
			if(respuesta != null){
				if(respuesta.getContent().equals("fin")){
					fin = true;
				} else{
					System.out.println("Recibido "+respuesta.getContent());
					fin = false;
				}
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