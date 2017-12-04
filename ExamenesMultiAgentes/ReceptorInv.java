package laboratorio.sesion4;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import jade.core.AID;

public class Receptor extends Agent{
	
	private static final long serialVersionUID = 1L;

	protected void setup(){
		ParallelBehaviour pb = new ParallelBehaviour(this,4);
		pb.addSubBehaviour(new Emisor1Par());
		pb.addSubBehaviour(new Emisor1Impar());
		pb.addSubBehaviour(new Emisor2Par());
		pb.addSubBehaviour(new Emisor2Impar());

		addBehaviour(pb);

	}

	protected void takeDown(){
		System.out.println("Liberando recursos");
	}

	private class Emisor1Par extends OneShotBehaviour{

		private int numero;
		private boolean fin;

		public void action(){

			MessageTemplate filtro = MessageTemplate.MatchSender(new AID("Emisor1",AID.ISLOCALNAME));
			ACLMessage mensaje = blockingReceive(filtro);
			if(mensaje!=null){
				System.out.println("Acaba de entrar un mensaje del Emisor1");
				numero = Integer.parseInt(mensaje.getContent());
				if (numero%2==0){
					fin = true;
					ACLMessage respuesta = new ACLMessage(ACLMessage.INFORM);
					respuesta.setContent(fin+"");
					respuesta.addReceiver(new AID("Emisor1",AID.ISLOCALNAME));
					send(respuesta);
				}else{
					System.out.println("Este no era el comportamiento adecuado para el mensaje");
				}
			}

		}
		public int onEnd(){
			myAgent.doDelete();
			return 0;
		}
	}
	private class Emisor1Impar extends OneShotBehaviour{
		private int numero;
		private boolean fin;

		public void action(){
			MessageTemplate filtro = MessageTemplate.MatchSender(new AID("Emisor1", AID.ISLOCALNAME));
			ACLMessage mensaje = blockingReceive(filtro);
			if(mensaje != null){
				System.out.println("Acaba de entrar un mensaje del Emisor1");
				numero = Integer.parseInt(mensaje.getContent());
				if(numero%2 != 0){
					fin = true;
					ACLMessage respuesta = new ACLMessage(ACLMessage.INFORM);
					respuesta.setContent(fin+"");
					respuesta.addReceiver(new AID("Emisor1",AID.ISLOCALNAME));
					send(respuesta);
				}
				else{
					System.out.println("Este no era el comportamiento adecuado para el mensaje");
				}
			}

		}
		public int onEnd(){
			return 0;
		}
	}
	private class Emisor2Par extends OneShotBehaviour{
		private int numero;
		private boolean fin;

		public void action(){
			MessageTemplate filtro = MessageTemplate.MatchSender(new AID("Emisor2", AID.ISLOCALNAME));
			ACLMessage mensaje = blockingReceive(filtro);
			if(mensaje != null){
				System.out.println("Acaba de entrar un mensaje del Emisor2");
				numero = Integer.parseInt(mensaje.getContent());
				if(numero%2==0){
					fin = true;
					ACLMessage respuesta = new ACLMessage(ACLMessage.INFORM);
					respuesta.setContent(fin+"");
					respuesta.addReceiver(new AID("Emisor2",AID.ISLOCALNAME));
					send(respuesta);
				}else{
					System.out.println("Este no era el comportamiento adecuado para el mensaje");
				}
			}

		}
		public int onEnd(){
			myAgent.doDelete();
			return 0;
		}
	}
	private class Emisor2Impar extends OneShotBehaviour{
		private int numero;
		private boolean fin;

		public void action(){
			MessageTemplate filtro = MessageTemplate.MatchSender(new AID("Emisor2", AID.ISLOCALNAME));
			ACLMessage mensaje = blockingReceive(filtro);
			if(mensaje != null){
				System.out.println("Acaba de entrar un mensaje del Emisor2");
				numero = Integer.parseInt(mensaje.getContent());
				if(numero%2!=0){
					fin = true;
					ACLMessage respuesta = new ACLMessage(ACLMessage.INFORM);
					respuesta.setContent(fin+"");
					respuesta.addReceiver(new AID("Emisor2",AID.ISLOCALNAME));
					send(respuesta);
				}else{
					System.out.println("Este no era el comportamiento adecuado para el mensaje");
				}
			}

		}
		public int onEnd(){
			myAgent.doDelete();
			return 0;
		}
	}
}