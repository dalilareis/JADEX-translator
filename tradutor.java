package Translator;

import jadex.bridge.fipa.SFipa;
import jadex.bdi.runtime.*;


@SuppressWarnings("serial")
public class tradutor extends Plan {
	
	private String eword;
	protected IExpression queryword;	
	
	public tradutor() {		
		getLogger().info("Created: "+this);
		this.queryword	= getExpression("query_epword");		
	}
	
		public void body() {
			Object content;				
					
			IMessageEvent waitMsg = waitForMessageEvent("request_translation");		
			String words = (String) waitMsg.getParameter("content").getValue().toString();
			String[] tokens = words.split(" ");
			this.eword = tokens[1];
			String pword = (String) queryword.execute("$eword", eword);
			
			if (pword != null) {
								
				content = "The portuguese translation of " + eword + "is " + pword;
				IMessageEvent reply = createMessageEvent("inform");
				reply.getParameterSet(SFipa.RECEIVERS).addValue(waitMsg.getParameter(SFipa.SENDER));//cid --> suposto criar um Component Identifier para o agente cliente?
				reply.getParameter(SFipa.CONTENT).setValue(content);
				sendMessage(reply);
				
				int counter = ((Integer)getBeliefbase().getBelief("counter").getFact()).intValue();
				getBeliefbase().getBelief("counter").setFact(new Integer(counter+1));						
				}
			
			else {
				content = "Word " + eword + " is not in the dictionary!";
				IMessageEvent reply = createMessageEvent("failure");
				reply.getParameterSet(SFipa.RECEIVERS).addValue(waitMsg.getParameter(SFipa.SENDER)); 
				reply.getParameter(SFipa.CONTENT).setValue(content);
				sendMessage(reply);
				}	
		}
		
	}
	


