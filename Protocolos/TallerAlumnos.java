package laboratorio;

import java.util.StringTokenizer;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.domain.FIPAAgentManagement.FailureException;
 
 
public class TallerAlumnos extends Agent {
 
    public int CODIGO_AVERIA;
    Object[] args;
 
    protected void setup()
    {
        args = getArguments();
       // Recuperar argumentos
        if (args != null && args.length > 0) {
        
		CODIGO_AVERIA=(int)(Math.random()*40); //Pueden reparar desde 0 hasta este random
        	System.out.println("Taller "+getLocalName()+": Esperando avisos...");
        	MessageTemplate protocolo = MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
        	MessageTemplate performativa = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);

        	//Aqui se compone la "plantilla"
 		MessageTemplate plantilla = MessageTemplate.and(protocolo, performativa);

        	addBehaviour(new ManejadorResponder(this, plantilla));
    }

	else 

		System.out.println("Debe de indicar la marca de los vehículos a reparar en este taller.");

    }
 
    class ManejadorResponder extends AchieveREResponder
    {
        public ManejadorResponder(Agent a,MessageTemplate mt) {
            super(a,mt);
        }
 
        protected ACLMessage handleRequest(ACLMessage request)throws NotUnderstoodException, RefuseException
        {
      
            StringTokenizer st=new StringTokenizer(request.getContent());
            String contenido=st.nextToken();
		
            if(contenido.equalsIgnoreCase((String)args[0]) || contenido.equalsIgnoreCase((String)args[1])) 
            {
                st.nextToken();
                int CodAveria=Integer.parseInt(st.nextToken());
                if (CodAveria <= CODIGO_AVERIA)
                {
                    ACLMessage agree = request.createReply();
		    //Texto del Mensaje
		    agree.setContent("Salimos ahora hacia el lugar de la averia");
                    agree.setPerformative(ACLMessage.AGREE);
                    return agree;
                }
                else
                {
                    ACLMessage refuse = request.createReply();
		    //Texto del Mensaje
		    refuse.setContent("No se pueden tratar este tipo de averias en este taller");
                    refuse.setPerformative(ACLMessage.REFUSE);
                    return refuse;  
                    //throw new RefuseException("No se pueden tratar este tipo de averias en este taller");
                }
            }
            else {
		ACLMessage notUnderstood = request.createReply();
		//Texto del Mensaje
		notUnderstood.setContent("los mecánicos no entienden de esta marca de vehículos.");
              	notUnderstood.setPerformative(ACLMessage.NOT_UNDERSTOOD);
              	return notUnderstood;  
		//throw new NotUnderstoodException("los mecánicos no entienden de esta marca de vehículos.");
	    }
        }
 
        protected ACLMessage prepareResultNotification(ACLMessage request,ACLMessage response) throws FailureException
        {
            if (Math.random() > 0.4) {
                
                ACLMessage inform = request.createReply();
		inform.setContent("La grua ha recogido el vehiculo");
                inform.setPerformative(ACLMessage.INFORM);
                return inform;
            }
            else
            {	
		ACLMessage failure = request.createReply();
		//Texto del Mensaje
		failure.setContent("dirección desconocida no se ha podido recoger el vehículo.");
              	failure.setPerformative(ACLMessage.FAILURE);
              	return failure;  
                //throw new FailureException("dirección desconocida no se ha podido recoger el vehículo.");
            }
        }
    }
}
