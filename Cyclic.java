package ejercicios.eje6;

import jade.core.Agent;
import jade.core.behaviours.*;

public class Cyclic extends Agent{

    public void setup(){
        MyCyclicBehaviour c = new MyCyclicBehaviour();
        addBehaviour(c);
    }

    protected void takeDown(){
        System.out.println("Ejecución finalizada");
    }

    private class MyCyclicBehaviour extends MyCyclicBehaviour{
        public void action(){
            System.out.println("Ejecutamos la acción ciclicamente");
        }
    }
}