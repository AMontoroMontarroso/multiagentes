package examenes;

import jade.core.Agent;
import jade.core.behaviours.*;

public class Prueba2 extends Agent{

	protected void setup(){

		ParallelBehaviour pb = new ParallelBehaviour(this, 1);
		pb.addSubBehaviour(new SubComportamiento1((int) (Math.random()*100+1), "Comportamieto paralelo 1"));
		pb.addSubBehaviour(new SubComportamiento1((int) (Math.random()*100+1), "Comportamieto paralelo 2"));
		pb.addSubBehaviour(new SubComportamiento1((int) (Math.random()*100+1), "Comportamieto paralelo 3"));

		FSMBehaviour fsm = new FSMBehaviour(this);

		fsm.registerFirstState(new Inicial(), "Inicial");
		fsm.registerLastState(new Final(), "Final");
	
		fsm.registerTransition("Inicial", "Inicial", 0);
		fsm.registerTransition("Inicial", "Final", 1);

		SequentialBehaviour seq = new SequentialBehaviour();
		seq.addSubBehaviour(pb);
		seq.addSubBehaviour(fsm);

		addBehaviour(seq);
	}//Fin setup

	private class SubComportamiento1 extends Behaviour{
		private String identificador;
		private int actual;
		
		public SubComportamiento1(int ejecucion, String id){			
			identificador=id;
			actual=ejecucion;
		}

		public void action(){
			System.out.println("Quedan "+actual+" ejecuciones del "+identificador+".");
			actual--;
		}
		public boolean done(){
			return (actual<=0);		
		}
	}

	private class Inicial extends OneShotBehaviour{
		
		private int al;
		public void action(){
			System.out.println("Soy el estado inicial");
			al = (int) (Math.random()*1000+1);
		
		}
		public int onEnd(){
			if(al%2==0) return 1;
			else return 0;
		}
	}

	private class Final extends OneShotBehaviour{
		public void action(){
			System.out.println("Soy el estado final");		
		}
		public int onEnd(){
			myAgent.doDelete();
			return 0;
		}
	}

}//fin Prueba2
