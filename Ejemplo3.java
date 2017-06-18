package ejercicios.eje5;

import jade.core.Agent;
import jade.core.behaviours.*;

public class Ejemplo3 extends Agent{

    //Inicialización del agente
    protected void setup(){
        addBehaviour(new MiComportamiento());
    }

    //Finalización del agente
    protected void takeDown(){
        System.out.println("****Agente finalizado*****");
    }

    // Definición de un comportamiento
    private class MiComportamiento extends Behaviour{

        int numeroEjecuciones = 1;

        //Función que realiza MiCmportamiento
        public void action(){
            System.out.println("Esta es la ejecucion "+numeroEjecuciones);

            //Lo bloqueamos durante 3 segundos
            block(3000);
            System.out.println("Termina la acción y ejecuta el done. Despues se bloquea 3s.");
            numeroEjecuciones++;
        }

        //Comprueba si el comportamiento ha finalizado
        public boolean done(){
            if(numeroEjecuciones>10){
                myAgent.doDelete();
                return true;
            }
            else return false;
        }
    }
}