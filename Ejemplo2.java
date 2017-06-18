package ejercicios.eje4;

import jade.core.Agent;
import jade.core.behaviours.*;

public class Ejemplo2 extends Agent{

    //Inicialización del agente
    protected void setup(){
        //Añadimos un comportamiento
        addBehaviour(new MiComportamiento());
    }

    //Finalización del agente
    protected void takeDown(){
        System.out.println("La escala ha terminado");
    }

    //Definición de un comportamiento
    private class MiComportamiento extends Behaviour{
        private int estado = 0;

        //Función que realiza MiComportamiento
        public void action(){
            switch(estado){
                case 0: System.out.println("Do"); break;
                case 1: System.out.println("Re"); break;
                case 2: System.out.println("Mi"); break;
                case 3: System.out.println("Fa"); break;
                case 4: System.out.println("Sol"); break;
                case 5: System.out.println("La"); break;
                case 6: System.out.println("Si"); break;
                case 7:{
                    System.out.println("Do");
                    myAgent.doDelete();
                    break;
                }
            }
            estado++;
        }

        //Comprueba si el comportamiento ha finalizado.
        public boolean done(){
            return (estado >7);
        }
    }
}