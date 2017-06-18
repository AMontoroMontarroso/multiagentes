package ejercicios.eje19;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;

public class Emisor extends Agent{

    class EmisorComportamiento extends SimpleBehaviour{
        boolean fin = false;
        public void action(){
            System.out.println(getLocalName()+": Preparandose para enviar un mensaje a receptor");
            AID id = new AID();
            id.setLocalname("receptor");

            //Creacion del objeto ACLMessage
            ACLMessage mensaje = new ACLMessage(ACLMessage.REQUEST);

            //Rellenar los campos necesarios del mensaje
            mensaje.setSender(getAID());
            mensaje.setLanguage("Español");
            mensaje.addReceiver(id);
            mensaje.setContent("Hola, que tal receptor?");

            //Envia el mensaje a los destinatarios
            send(mensaje);

            System.out.println(getLocalName() +": Enviando hola a receptor");
            System.out.println(mensaje.toString());
            fin = true;
        }

        public boolean done(){
            return fin;
        }
    }
    protected void setup(){
        addBehaviour(new EmisorComportamiento());
    }
}