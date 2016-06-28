package it.uniroma3.epsl2.framework.lang.plan.relations.temporal;

import it.uniroma3.epsl2.framework.lang.plan.Decision;
import it.uniroma3.epsl2.framework.lang.plan.RelationType;
import it.uniroma3.epsl2.framework.time.lang.IntervalConstraintFactory;
import it.uniroma3.epsl2.framework.time.lang.TemporalConstraintType;
import it.uniroma3.epsl2.framework.time.lang.allen.StartsDuringIntervalConstraint;

/**
 * 
 * @author anacleto
 *
 */
public class StartsDuringRelation extends TemporalRelation 
{
	private long[] firstBound;
	private long[] secondBound;
	
	protected IntervalConstraintFactory factory;
	
	/**
	 * 
	 * @param reference
	 * @param target
	 */
	protected StartsDuringRelation(Decision reference, Decision target) {
		super(RelationType.STARTS_DURING, reference, target);
		this.firstBound = new long[] {0, Long.MAX_VALUE - 1};
		this.secondBound = new long[] {0, Long.MAX_VALUE - 1};
		// get factory
		this.factory = IntervalConstraintFactory.getInstance();
	}
	
	/**
	 * 
	 * @return
	 */
	@Override
	public long[][] getBounds() {
		return new long[][] {
			this.firstBound,
			this.secondBound
		};
	}
	
	/**
	 * 
	 * @param bounds
	 */
	@Override
	public void setBounds(long[][] bounds) {
		this.firstBound = bounds[0];
		this.secondBound = bounds[1];
	}
	
	/**
	 * 
	 * @param bound
	 */
	public void setFirstBound(long[] bound) {
		this.firstBound = bound;
	}
	
	/**
	 * 
	 * @param bound
	 */
	public void setSecondBound(long[] bound) {
		this.secondBound = bound;
	}
	
	/**
	 * 
	 * @return
	 */
	public long[] getFirstBound() {
		return this.firstBound;
	}
	
	/**
	 * 
	 * @return
	 */
	public long[] getSecondBound() {
		return this.secondBound;
	}
	
	/**
	 * 
	 */
	@Override
	public TemporalConstraintType getConstraintType() {
		return TemporalConstraintType.STARTS_DURING;
	}
	
	/**
	 * 
	 */
	@Override
	public StartsDuringIntervalConstraint create() {
		// create constraint
		StartsDuringIntervalConstraint c = this.factory.create(
				TemporalConstraintType.STARTS_DURING);

		// set intervals
		c.setReference(this.reference.getToken().getInterval());
		c.setTarget(this.target.getToken().getInterval());
		// set bounds
		c.setFirstBound(this.firstBound);
		c.setSecondBound(this.secondBound);
		// set constraint
		this.constraint = c;
		// get constraint
		return c;
	}
}
