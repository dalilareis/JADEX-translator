package Translator;

import jadex.bdi.runtime.IExpression;
import jadex.bdi.runtime.IMessageEvent;
import jadex.bdi.runtime.Plan;
import jadex.bridge.fipa.SFipa;
import jadex.commons.Tuple;


@SuppressWarnings("serial")
public class addWordDict extends Plan {
	
	protected IExpression queryword;
	private String eword;
	private String pword;
	
	public addWordDict(){
		getLogger().info("Created: "+this);
		this.queryword = getExpression("query_epword");		
	}
		
		public void body() {
			Object content;
		
			IMessageEvent addMsg = waitForMessageEvent("request_addword");		
			String words = (String) addMsg.getParameter("content").getValue().toString();
			String[] tokens = words.split(" ");
			this.eword = tokens[1];
			this.pword = tokens[2];
			Object testwords = queryword.execute("$eword", eword);
		
			if (testwords == null)
			{
				getBeliefbase().getBeliefSet("epwords").addFact(new Tuple(eword, pword));
				
				content = "Added  new wordpair to database: "+eword+" - "+pword;
				IMessageEvent reply = createMessageEvent("inform");
				reply.getParameterSet(SFipa.RECEIVERS).addValue("client");
				reply.getParameter(SFipa.CONTENT).setValue(content);
				sendMessage(reply);			
			}
			else {
				content = "Word " + eword + " already in dictionary!";
				IMessageEvent reply = createMessageEvent("failure");
				reply.getParameterSet(SFipa.RECEIVERS).addValue("client");
				reply.getParameter(SFipa.CONTENT).setValue(content);
				sendMessage(reply);						
			}	
}
}
