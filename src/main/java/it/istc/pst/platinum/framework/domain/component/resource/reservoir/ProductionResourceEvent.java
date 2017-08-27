package it.istc.pst.platinum.framework.domain.component.resource.reservoir;

import it.istc.pst.platinum.framework.domain.component.Decision;
import it.istc.pst.platinum.framework.domain.component.resource.ResourceEvent;
import it.istc.pst.platinum.framework.domain.component.resource.ResourceEventType;
import it.istc.pst.platinum.framework.time.tn.TimePoint;

/**
 * 
 * @author anacleto
 *
 */
public class ProductionResourceEvent extends ResourceEvent<TimePoint>
{
	/**
	 * 
	 * @param activity
	 * @param amount
	 */
	protected ProductionResourceEvent(Decision activity, int amount) {
		super(ResourceEventType.PRODUCTION, activity, amount, activity.getToken().getInterval().getEndTime());
	}
}
