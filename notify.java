package Translator;

import jadex.bdi.runtime.Plan;

@SuppressWarnings("serial")
public class notify extends Plan {

	public notify() {
		getLogger().info("Created: "+this);			
	}
		public void body() {
						
			int counter = ((Integer)getBeliefbase().getBelief("counter").getFact()).intValue();			
			
			System.out.println("You have processed  "+counter +" requests so far!");
			
			getLogger().info("You have processed  "+counter	+" requests so far!");
			
		}
	}
