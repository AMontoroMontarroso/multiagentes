package examenes;

import jade.core.Agent;
import jade.core.behaviours.*;


public class Prueba1 extends Agent{
private 		Behaviour [] mc = new Behaviour[3];
private 		int comportamientos=0;
	protected void setup(){
		for(int i=0; i<mc.length; i++){
			mc[i] = new MiComportamiento1((int)(Math.random()*1000+1), this, "Comportamiento"+i);
			addBehaviour(mc[i]);
		}
	
	}//Fin setup

	private class MiComportamiento1 extends Behaviour{
		private int ejecuciones;
		private int actual=1;
		private String identificador;
		public MiComportamiento1(int x, Agent a, String id){
			super(a);
			ejecuciones=x;
			identificador=id;
		}
		public void action(){
			System.out.println("Soy el comportamiento "+identificador+" y estoy en mi ejecución numero "+actual);
			actual++;
		}//Fin action
		public boolean done(){
			return actual>=ejecuciones;
		}//Fin done

		public int onEnd(){
			((Prueba1) myAgent).apagar();
			return 0;
		}//Fin onEnd
	}//Fin MICOmportamiento1

	public void takeDown(){
		System.out.println("Liberando recursos...");
	}

	public void apagar(){
			comportamientos+=1;
			if(comportamientos==3)
				for(int j =0; j<3; j++){ removeBehaviour(mc[j]);}		
	}

}	
