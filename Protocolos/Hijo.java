package examen;

import jade.core.Agent;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;
import jade.domain.FIPANames;
 
 
public class Hijo extends Agent {
 
    protected void setup()
    {
        Object[] args = getArguments();
 
        if (args != null && args.length == 1) {
            System.out.println("Fiesta!!, preguntare a mi padre.");
 
            ACLMessage msg = new ACLMessage(ACLMessage.QUERY_IF);
            msg.addReceiver(new AID((String) args[0], AID.ISLOCALNAME));
            msg.setProtocol(FIPANames.InteractionProtocol.FIPA_QUERY); 
            msg.setContent("Puedo salir de fiesta?");;
 
            addBehaviour(new ManejadorInitiator(this,msg));
 
        }
        else System.out.println("Debe escribir el nombre del agente intermedio (pasado como parametro).");
    }
 
    class ManejadorInitiator extends AchieveREInitiator
    {
        public ManejadorInitiator(Agent a,ACLMessage msg) {
            super(a,msg);
        }
 
		
        protected void handleAgree(ACLMessage agree)
        {
            System.out.println("Mi padre " + agree.getSender().getName()
            + " dice que le preguntara a mi madre.");
        }
 
		
        protected void handleRefuse(ACLMessage refuse)
        {
            System.out.println("Mi padre " + refuse.getSender().getName()
            + " dice que no, que tengo que madrugar.");
        }
 
		
        protected void handleNotUnderstood(ACLMessage notUnderstood)
        {
            System.out.println("Mi padre " + notUnderstood.getSender().getName()
            + " no esta en casa.");
        }
 
		
        protected void handleInform(ACLMessage inform)
        {
            System.out.println("Mi padre " + inform.getSender().getName()
            + " dice que: " + inform.getContent() + ".");
        }
 
		
        protected void handleFailure(ACLMessage fallo)
        {
            if (fallo.getSender().equals(myAgent.getAMS())) {
                System.out.println("Soy huerfano....");
            }
            else
            {
                System.out.println("Mi padre " + fallo.getSender().getName()
                + ": " + fallo.getContent().substring(1, fallo.getContent().length()-1)+".");
            }
        }
    }
}
