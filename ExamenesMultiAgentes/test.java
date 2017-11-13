package ejlab3;


import jade.core.Agent;
import jade.core.behaviours.*;

public class AgenteParalelo extends Agent{
	int ejecuciones;
	protected void setup(){

		ejecuciones=0;
		ParallelBehaviour padre = new ParallelBehaviour(this,ParallelBehaviour.WHEN_ALL){
			public int onEnd(){
				doDelete();
				return 0;
			}
		};
		padre.addSubBehaviour(new Behaviour(this){
				public void action(){
						System.out.println("Soy el primer subcomportamiento y estoy en mi ejecución: "+ (ejecuciones+1));
						ejecuciones++;
						}
				public boolean done(){
							return (ejecuciones==3);
						}
					});
		padre.addSubBehaviour(new OneShotBehaviour(this) {
				public void action(){
					System.out.println("Soy el segundo subcomportamiento");
				}
		});

		padre.addSubBehaviour(new WakerBehaviour (this,5000) {
			public void onWake(){
				System.out.println("Soy el tercer subcomportamiento y me acabo de despertar");
			}
		});
		addBehaviour(padre);
		}
	
	
 protected void takeDown(){
 	System.out.println("Liberando recursos");
 }


}//end agente
