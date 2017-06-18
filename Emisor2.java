package ejercicios.eje20;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;

public class Emisor2 extends Agent{
    class EmisorComportamiento extends SimpleBehaviour{
        boolean fin =false;
        public void action(){
            System.out.println(getLocalName()+": Preparandose para enviar un mensaje a receptor");
            AID id = new AID();
            id.setLocalName("receptor");

            //Creacion del objeto ACLMessage
            ACLMessage mensaje = new ACLMessage(ACLMessage.REQUEST);

            //Rellenar los campos necesarios del mensaje
            mensaje.setSender(getAID());
            mensaje.setLanguage("Español");
            mensaje.addReceiver(id);
            mensaje.setContent("Hola, que tal receptor?");

            //Envia mensaje a los destinatarios
            send(mensaje);
            System.out.println(getLocalName()+": Enviando hola a receptor");
            System.out.println(mensaje.toString());

            //Espera la respuesta
            ACLMessage mensaje2 = blockingReceive();
            if(mensaje2 != null){
                System.out.println(getLocalName()+ ": acaba de recibir el siguiente mensaje: ");
                System.out.println(mensaje2.toString());
                fin =true;
            }
        }
        public boolean done(){
            return fin;
        }
    }
    protected void setup(){
        addBehaviour(new EmisorComportamiento());
    }
}