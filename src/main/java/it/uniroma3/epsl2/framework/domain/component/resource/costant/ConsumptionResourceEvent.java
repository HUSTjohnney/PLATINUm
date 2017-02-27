package it.uniroma3.epsl2.framework.domain.component.resource.costant;

import it.uniroma3.epsl2.framework.lang.plan.Decision;
import it.uniroma3.epsl2.framework.lang.plan.resource.ResourceEvent;
import it.uniroma3.epsl2.framework.lang.plan.resource.ResourceEventType;
import it.uniroma3.epsl2.framework.time.tn.TimePoint;

/**
 * 
 * @author anacleto
 *
 */
public class ConsumptionResourceEvent extends ResourceEvent 
{
	/**
	 * 
	 * @param activity
	 * @param event
	 * @param amount
	 */
	protected ConsumptionResourceEvent(Decision activity, TimePoint event, int amount) {
		super(ResourceEventType.CONSUMPTION, event, amount, activity);
	}
}
