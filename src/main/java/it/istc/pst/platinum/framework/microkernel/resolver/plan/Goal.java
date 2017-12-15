package it.istc.pst.platinum.framework.microkernel.resolver.plan;

import it.istc.pst.platinum.framework.domain.component.Decision;
import it.istc.pst.platinum.framework.domain.component.DomainComponent;
import it.istc.pst.platinum.framework.microkernel.lang.flaw.Flaw;
import it.istc.pst.platinum.framework.microkernel.lang.flaw.FlawType;

/**
 * 
 * @author anacleto
 *
 */
public class Goal extends Flaw 
{
	private Decision decision;
	
	/**
	 * 
	 * @param id
	 * @param component
	 * @param decision
	 */
	protected Goal(int id, DomainComponent component, Decision decision) {
		super(id, component, FlawType.PLAN_REFINEMENT);
		this.decision = decision;
	}
	
	/**
	 * 
	 */
	public void setMandatoryExpansion() {
		this.decision.setMandatoryExpansion();
	}
	
	/**
	 * 
	 */
	public void setMandatoryUnification() {
		this.decision.setMandatoryUnification();
	}
	
	/**
	 * 
	 */
	@Override
	public DomainComponent getComponent() {
		return this.decision.getComponent();
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isMandatoryExpansion() {
		return this.decision.isMandatoryExpansion();
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isMandatoryUnification() {
		return this.decision.isMandatoryUnification();
	}
	
	/**
	 * 
	 * @return
	 */
	public Decision getDecision() {
		return decision;
	}
	
	/**
	 * 
	 */
	@Override
	public String toString() {
		return "[Goal decision= " + this.decision + "]";
	}
}
