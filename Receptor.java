package ejercicios.eje19;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;

public class Receptor extends Agent{
    class ReceptorComportamiento extends SimpleBehaviour{
        private boolean fin = false;
        public void action(){
            System.out.println("Preparandose para recibir");
            //Obtiene el primer mensaje de la cola de mensajes
            ACLMenssage mensaje = blockingReceive();
            block();

            if (mensaje != null){
                System.out.println(getLocalName() + ": acaba de recibir el siguiente mensaje: ");
                System.out.println(mensaje.toString());
                fin = true;
            }
        }
        public boolean done(){
            return fin;
        }
    }
    protected void setup(){
        addBehaviour(new ReceptorComportamiento());
    }
}