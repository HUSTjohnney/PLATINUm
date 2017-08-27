package it.istc.pst.platinum.framework.microkernel.resolver.timeline.scheduling;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.istc.pst.platinum.framework.domain.component.Decision;
import it.istc.pst.platinum.framework.domain.component.sv.StateVariable;
import it.istc.pst.platinum.framework.microkernel.lang.flaw.Flaw;
import it.istc.pst.platinum.framework.microkernel.lang.flaw.FlawType;

/**
 * 
 * @author anacleto
 *
 */
public class OverlappingSet extends Flaw implements Comparable<OverlappingSet> 
{
	private Set<Decision> decisions;
	
	/**
	 * 
	 * @param sv
	 */
	protected OverlappingSet(StateVariable sv) {
		super(sv, FlawType.TIMELINE_OVERFLOW);
		this.decisions = new HashSet<>();
	}
	
	/**
	 * 
	 * @return
	 */
	public int size() {
		return this.decisions.size();
	}
	
	/**
	 * 
	 * @param dec
	 */
	public void add(Decision dec) {
		this.decisions.add(dec);
	}
	
	/**
	 * 
	 * @param dec
	 */
	public void remove(Decision dec) {
		this.decisions.remove(dec);
	}
	
	/**
	 * 
	 * @param dec
	 * @return
	 */
	public boolean contains(Decision dec) {
		return this.decisions.contains(dec);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Decision> getDecisions() {
		// get sorted list
		return new ArrayList<>(this.decisions);
	}
	
	/**
	 * 
	 */
	@Override
	public int compareTo(OverlappingSet o) {
		// check sizes of overlapping sets
		return this.size() >= o.size() ? -1 : 1;
	}
	
	/**
	 * 
	 */
	@Override
	public String toString() {
		return "[OverlappingSet requirement= " + this.size() + "]";
	}
}
