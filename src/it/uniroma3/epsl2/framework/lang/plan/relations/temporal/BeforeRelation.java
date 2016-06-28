package it.uniroma3.epsl2.framework.lang.plan.relations.temporal;

import it.uniroma3.epsl2.framework.lang.plan.Decision;
import it.uniroma3.epsl2.framework.lang.plan.RelationType;
import it.uniroma3.epsl2.framework.time.lang.IntervalConstraintFactory;
import it.uniroma3.epsl2.framework.time.lang.TemporalConstraintType;
import it.uniroma3.epsl2.framework.time.lang.allen.BeforeIntervalConstraint;

/**
 * 
 * @author anacleto
 *
 */
public class BeforeRelation extends TemporalRelation 
{
	private long[] bound;
	
	private IntervalConstraintFactory factory;
	
	/**
	 * 
	 * @param reference
	 * @param target
	 */
	protected BeforeRelation(Decision reference, Decision target) {
		super(RelationType.BEFORE, reference, target);
		this.bound = new long[] {0, Long.MAX_VALUE - 1};
		// get factory
		this.factory = IntervalConstraintFactory.getInstance();
	}
	
	/**
	 * 
	 */
	@Override
	public long[][] getBounds() {
		return new long[][] {this.bound};
	}
	
	/**
	 * 
	 */
	@Override
	public void setBounds(long[][] bounds) {
		this.bound = bounds[0];
	}
	
	/**
	 * 
	 * @param bound
	 */
	public void setBound(long[] bound) {
		this.bound = bound;
	}
	
	/**
	 * 
	 * @return
	 */
	public long getDistanceLowerBound() {
		return this.bound[0];
	}
	
	/**
	 * 
	 */
	public long getDistanceUpperBound() {
		return this.bound[1];
	}

	/**
	 * 
	 * @return
	 */
	public TemporalConstraintType getConstraintType() {
		return TemporalConstraintType.BEFORE;
	}
	
	/**
	 * 
	 */
	@Override
	public BeforeIntervalConstraint create() {
		// create constraint
		BeforeIntervalConstraint c = this.factory.create(
				TemporalConstraintType.BEFORE);

		// set intervals
		c.setReference(this.reference.getToken().getInterval());
		c.setTarget(this.target.getToken().getInterval());
		// set bounds
		c.setLb(this.bound[0]);
		c.setUb(this.bound[1]);
		// set constraint
		this.constraint = c;
		// get constraint
		return c;
	}
}
