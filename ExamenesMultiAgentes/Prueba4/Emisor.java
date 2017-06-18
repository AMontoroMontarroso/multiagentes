package examenes;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.core.AID;
public class Emisor extends Agent{

	public class EmisorComportamiento extends Behaviour{
		private ACLMessage mensajeContinuo;
		private boolean fin = false;
		private int contador=0;
		public void action(){
			int random = (int) (Math.random()*1000+1);
			contador++;
			System.out.println(getLocalName()+" está preparando el envío numero "+contador);
			System.out.println("El numero a enviar es: "+random);

			AID id = new AID();
			id.setLocalName("receptor");

			ACLMessage mensaje = new ACLMessage(ACLMessage.INFORM);

			mensaje.setSender(getAID());
			mensaje.setLanguage("Español");
			mensaje.addReceiver(id);
			mensaje.setContent(random+"");

			send(mensaje);
		
			mensajeContinuo = blockingReceive();
			if(mensajeContinuo!=null){
				if (mensajeContinuo.getContent().equals("fin")) fin=true;
				else{
					System.out.println("Recibido numero calculado.");
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
	}//Fin private class

	protected void setup(){
		addBehaviour(new EmisorComportamiento());
	}
}
