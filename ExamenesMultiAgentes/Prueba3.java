package examenes;
import jade.core.Agent;
import jade.core.behaviours.*;

public class Prueba3 extends Agent{
	ThreadedBehaviourFactory tbf;
	Comportamiento1 Comp1, Comp2, Comp3;
	protected void setup(){
		tbf = new ThreadedBehaviourFactory();

		Comp1 = new Comportamiento1((int) (Math.random()*100+1), "Comportamiento 1");
		Comp2 = new Comportamiento1((int) (Math.random()*100+1), "Comportamiento 2");
		Comp3 = new Comportamiento1((int) (Math.random()*100+1), "Comportamiento 3");

		ParallelBehaviour pb = new ParallelBehaviour(this, ParallelBehaviour.WHEN_ALL);
		pb.addSubBehaviour(tbf.wrap(Comp1));
		pb.addSubBehaviour(tbf.wrap(Comp2));
		pb.addSubBehaviour(tbf.wrap(Comp3));
		
		addBehaviour(pb);
	}
	
	protected void takeDown(){
		Thread td1= tbf.getThread(Comp1);
		td1.interrupt();
		Thread td2= tbf.getThread(Comp2);
		td2.interrupt();		
		Thread td3= tbf.getThread(Comp3);		
		td3.interrupt();
		super.takeDown();
	}

	private class Comportamiento1 extends Behaviour{
		private int ejec;
		private String ident;

		public Comportamiento1(int r, String id){
			ejec=r;
			ident=id;			
		}

		public void action(){
			System.out.println("Soy el comportamiento "+ident+" y me quedan "+ejec+" ejecuciones.");
			ejec--;
			
			if(tbf.size()>1){
				tbf.resume(Comp1);
				tbf.resume(Comp2);
				tbf.resume(Comp3);
			}

			tbf.suspend(Comp3);
			tbf.suspend(Comp2);
			tbf.suspend(Comp1);
			
		}

		public boolean done(){
			return (ejec==0);
		}
		public int onEnd(){
			Thread td1 = tbf.getThread(this);
			td1.interrupt();
			return 0;		
		}
	}//Fin Comportamiento1
	
}
