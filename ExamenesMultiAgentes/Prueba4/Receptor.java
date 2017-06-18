package examenes;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;

public class Receptor extends Agent{

	protected void setup(){
		System.out.println("Preparado para recibir.");
		addBehaviour(new ReceptorComportamiento());
	}

	private class ReceptorComportamiento extends Behaviour{
		private int ejecPar=0;
		private int ejecImpar=0;

		public void action(){

			ACLMessage mensaje = receive();
			if(mensaje!=null){
				System.out.println(getLocalName()+" acaba de recibir el número "+mensaje.getContent());
				int numero = Integer.parseInt(mensaje.getContent());
				if(numero%2==0){
					numero=numero*2;
					ejecPar++;

				}
				else if (numero%2!=0){
					numero=numero/2;
					ejecImpar++;
				}
				System.out.println("Actualmente van "+ejecPar+" ejecuciones con numeros pares");
				System.out.println("Actualmente van "+ejecImpar+" ejecuciones con numeros impares");
			
				ACLMessage respuesta = new ACLMessage(ACLMessage.INFORM);
				if(ejecImpar ==10 || ejecPar ==10){
					respuesta.setContent("fin");
					respuesta.addReceiver(mensaje.getSender());
					send(respuesta);
				}
				else{
					respuesta.setContent(numero+"");
					respuesta.addReceiver(mensaje.getSender());
					send(respuesta);				
				}

				
			}//Fin if
		}//Fin action

		public boolean done(){
			return (ejecPar>=10 || ejecImpar>=10);
		}
		public int onEnd(){
			myAgent.doDelete();
			return 0;
		}
	}//fin private class
	public void takeDown(){
		System.out.println("Finalizando ejecución.");
		super.takeDown();
	}

}//Fin receptor
