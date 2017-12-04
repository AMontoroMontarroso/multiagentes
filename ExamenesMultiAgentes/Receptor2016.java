package laboratorio.examen;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import jade.core.AID;

public class Receptor extends Agent{

	private Behaviour comp;

	protected void setup(){
		comp = new ReceptorComportamiento();
		addBehaviour(comp);
	}

	protected void takeDown(){
		System.out.println("Liberando Recursos");
	}

	public class ReceptorComportamiento extends Behaviour{

		private int ejecPar;
		private int ejecImpar;

		public void onStart(){
			ejecPar=0;
			ejecImpar=0;
		}

		public void action(){
			MessageTemplate filtro = MessageTemplate.MatchSender(new AID("Emisor",AID.ISLOCALNAME));
			ACLMessage mensaje = blockingReceive(filtro);
			if(mensaje != null){
				System.out.println(getLocalName()+" acaba de recibir el numero "+mensaje.getContent());
				int numero = Integer.parseInt(mensaje.getContent());
				if(numero%2==0){
					numero=numero*2;
					ejecPar++;
				}else if(numero%2!=0){
					numero = numero/2;
					ejecImpar++;
				}

				System.out.println("Actualmente van "+ejecPar+" ejecuciones con numeros pares");
				System.out.println("Actualmente van "+ejecImpar+" ejecuciones con numeros impares");

				ACLMessage respuesta = new ACLMessage(ACLMessage.INFORM);
				if(ejecImpar == 10 || ejecPar == 10){
					respuesta.setContent("fin");
					respuesta.addReceiver(new AID("Emisor",AID.ISLOCALNAME));
					send(respuesta);
				}
				else{
					respuesta.setContent(numero+"");
					respuesta.addReceiver(new AID("Emisor",AID.ISLOCALNAME));
					send(respuesta);
				}
			}
		}

		public boolean done(){
			return (ejecPar>=10 || ejecImpar >= 10);
		}
		public int onEnd(){
			myAgent.doDelete();
			return 0;
		}
	}
}