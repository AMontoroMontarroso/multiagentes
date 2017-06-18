package examen;

import javax.swing.JOptionPane;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ProposeResponder;
 
public class Madre extends Agent {
 
    protected void setup() {
        System.out.printf("Esperando peticiones...\n");
 
        MessageTemplate  plantilla = ProposeResponder.createMessageTemplate(FIPANames.InteractionProtocol.FIPA_PROPOSE);
 
        this.addBehaviour(new ResponderPermiso(this, plantilla));
    }
 

    private boolean checkContent(String agente, String propuesta) {
        if (JOptionPane.showConfirmDialog(null, propuesta, agente + " dice:", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            return true;
        } else {
            return false;
        }
    }
 
    private class ResponderPermiso extends ProposeResponder {
 
        public ResponderPermiso (Agent agente, MessageTemplate plantilla) {
            super(agente, plantilla);
        }
 
        protected ACLMessage prepareResponse(ACLMessage propuesta){
            System.out.printf("Peticion de permiso recibida de %s.\n", propuesta.getSender().getLocalName());
 

            if (Madre.this.checkContent(propuesta.getSender().getLocalName(), "Conceder permiso")) {
                System.out.printf("Vale.\n");
 
                ACLMessage agree = propuesta.createReply(); 
                agree.setPerformative(ACLMessage.ACCEPT_PROPOSAL); 
 
                return agree;
            } else {
 
                System.out.printf("No, maniana tiene que madrugar.\n");
 
                ACLMessage refuse = propuesta.createReply(); 
                refuse.setPerformative(ACLMessage.REJECT_PROPOSAL); 
 
                return refuse;
            }
        }
    }
}
