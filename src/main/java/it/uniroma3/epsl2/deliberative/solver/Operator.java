package it.uniroma3.epsl2.deliberative.solver;

import java.util.concurrent.atomic.AtomicInteger;

import it.uniroma3.epsl2.framework.lang.flaw.Flaw;
import it.uniroma3.epsl2.framework.lang.flaw.FlawSolution;
import it.uniroma3.epsl2.framework.lang.plan.Agenda;

/**
 * 
 * @author anacleto
 *
 */
public class Operator implements Comparable<Operator> 
{
	private static AtomicInteger ID_COUNTER = new AtomicInteger(0);
	private int id;
	private FlawSolution solution;
	private boolean applied;						// application flag
	private double makespan;						// resulting makespan
	private Agenda agenda;							// resulting agenda
	
	/**
	 * 
	 * @param solution
	 */
	protected Operator(FlawSolution solution) {
		this.id = ID_COUNTER.getAndIncrement();
		this.solution = solution;
		this.applied = false;
		this.makespan = Double.MAX_VALUE - 1;
		this.agenda = new Agenda();
	}
	
	/**
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * 
	 * @param makespan
	 */
	public void setMakespan(double makespan) {
		this.makespan = makespan;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getMakespan() {
		return makespan;
	}
	
	/**
	 * 
	 * @param agenda
	 */
	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}
	
	/**
	 * 
	 * @return
	 */
	public Agenda getAgenda() {
		return agenda;
	}
	
	/**
	 * 
	 */
	public void setApplied() {
		this.applied = true;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isApplied() {
		return this.applied;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getCost() {
		return this.solution.getCost();
	}
	
	/**
	 * 
	 * @return
	 */
	public Flaw getFlaw() {
		return this.solution.getFlaw();
	}
	
	/**
	 * 
	 * @return
	 */
	public FlawSolution getFlawSolution() {
		return solution;
	}
	
	/**
	 * 
	 */
	@Override
	public int compareTo(Operator o) {
		return this.id <= o.id ? -1 : 1;
	}
	
	/**
	 * 
	 * @return
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	/**
	 * 
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Operator other = (Operator) obj;
		if (id != other.id)
			return false;
		return true;
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		return "[Operator id= " + this.id + " cost= " + this.getCost() + " solution= " + this.solution + "]";
	}
}
