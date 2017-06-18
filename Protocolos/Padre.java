package examen;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
 
import jade.proto.AchieveREResponder;
import jade.proto.ProposeInitiator;
 
public class Padre extends Agent {
 
    private boolean aceptado = false;

    protected void setup() {
 
    Object[] args = getArguments();
 
    if (args != null && args.length == 1) {
		
        ACLMessage mensaje = new ACLMessage(ACLMessage.PROPOSE);
        mensaje.setProtocol(FIPANames.InteractionProtocol.FIPA_PROPOSE);
        mensaje.setContent("Puede salir el ninio de fiesta?");
 
        mensaje.addReceiver(new AID((String) args[0], AID.ISLOCALNAME));
 
        this.addBehaviour(new PreguntarSiAceptado(this, mensaje));
 
        System.out.println(this.getLocalName() + ": Esperando peticion...");
 
		
        MessageTemplate plantilla = AchieveREResponder.createMessageTemplate(FIPANames.InteractionProtocol.FIPA_QUERY);
        this.addBehaviour(new ComprobarResponder(this, plantilla));
 
        }
        else System.out.println("Debe escribir el nombre del tercer agente (pasado como parametro).");
    }
 
	// Respuesta al hijo
    class ComprobarResponder extends AchieveREResponder {
        public ComprobarResponder(Agent agente, MessageTemplate plantilla) {
            super(agente, plantilla);
        }
 
        protected ACLMessage handleRequest(ACLMessage request){
            System.out.printf("Recibida peticion de %s , le preguntare a la madre.\n", request.getSender().getLocalName());
 
            ACLMessage agree = request.createReply();
            agree.setPerformative(ACLMessage.AGREE); 
            return agree;
        }
 
        protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response){
            System.out.printf("Comprobando si la madre le deja.\n");
 
            ACLMessage inform = request.createReply();
            inform.setPerformative(ACLMessage.INFORM); 
 
			String retorno;
			if ( aceptado )
				retorno = "Vale.";
			else
				retorno = "No, maniana tienes que madrugar.";

			inform.setContent(retorno); 

			System.out.printf("Mandando respuesta al hijo.\n");

			return inform;
        }
    }
 
	// Pregunta a la madre
    class PreguntarSiAceptado extends ProposeInitiator {
 
        public PreguntarSiAceptado(Agent agente, ACLMessage mensaje) {
            super(agente, mensaje);
        }
 
       
 
        protected void handleAcceptProposal(ACLMessage aceptacion) {
			System.out.printf("Le deja.\n");
            aceptado = true;
        }
 
       
 
        protected void handleRejectProposal(ACLMessage rechazo) {
			System.out.printf("No le deja.\n");
            aceptado = false;
        }
    }
}
