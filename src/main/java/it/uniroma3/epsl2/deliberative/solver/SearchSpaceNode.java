package it.uniroma3.epsl2.deliberative.solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import it.uniroma3.epsl2.framework.lang.flaw.Flaw;

/**
 * 
 * @author anacleto
 *
 */
public class SearchSpaceNode implements Comparable<SearchSpaceNode>
{
	private static AtomicInteger ID_COUNTER = new AtomicInteger(0);
	private int id;
	private List<Operator> operators;	// node generation trace
	private Operator generator;			// node generator operator
	
	/**
	 * Create a root node
	 * 
	 * @param id
	 */
	protected SearchSpaceNode() {
		// set node's id
		this.id = ID_COUNTER.getAndIncrement();
		// initialize operators
		this.operators = new ArrayList<>();
		this.generator = null;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Create a child node generated by means of the specified operator
	 * 
	 * @param parent
	 * @param op
	 */
	protected SearchSpaceNode(SearchSpaceNode parent, Operator op) {
		// set node's id
		this.id = ID_COUNTER.getAndIncrement();
		// set operators
		this.operators = new ArrayList<>(parent.getOperators());
		// add generator
		this.operators.add(op);
		this.generator = op;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getDepth() {
		return this.operators.size();
	}
	
	/**
	 * 
	 * @return
	 */
	public double getCost() {
		// compute the cost of the current node
		double cost = 0.0;
		if (this.generator != null) {
			// set initial cost
			cost = this.generator.getCost();
			for (Operator op : this.operators) {
				cost += op.getCost();
			}
		}
		
		// get node cost
		return cost;
	}
	
	/**
	 * 
	 * @return
	 */
	public Flaw getFlaw() {
		return this.generator.getFlaw();
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isRootNode() {
		// check if root node
		return this.getGenerator() == null;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Operator> getOperators() {
		return operators;
	}
	
	/**
	 * The method returns the generator operator of the node.
	 * 
	 * The method returns null for the root node of the search space
	 * 
	 * @return
	 */
	public Operator getGenerator() {
		// get generator
		return this.generator;
	}
	
	/**
	 * Get the list of applied operators from the more recent to the 
	 * specified one (excepted) 
	 * 
	 * @param operator
	 * @return
	 */
	public List<Operator> getOperatorsUpTo(Operator operator) {
		// list of operators
		List<Operator> list = new ArrayList<>();
		if (operator == null) {
			// add all operators
			list.addAll(this.operators);
			// reverse order
			Collections.reverse(list);
		}
		else {
			// get index of the operator
			int index = this.operators.indexOf(operator);
			for (int i = this.operators.size() - 1; i > index; i--) {
				// add operator
				list.add(this.operators.get(i));
			}
		}
		// get list
		return list;
	}
	
	/**
	 * 
	 * @param operator
	 * @return
	 */
	public List<Operator> getOperatorsFrom(Operator operator) {
		// list of operators
		List<Operator> list = new ArrayList<>();
		if (operator == null) {
			// add all operators
			list.addAll(this.operators);
		}
		else {
			// get index of the operator
			for (int index = this.operators.indexOf(operator) + 1; index < this.operators.size(); index++) {
				// add operator
				list.add(this.operators.get(index));
			}
		}
		// get list
		return list;
	}
	
	/**
	 * 
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
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SearchSpaceNode other = (SearchSpaceNode) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	/**
	 * 
	 * @param o
	 * @return
	 */
	@Override
	public int compareTo(SearchSpaceNode o) {
		// compare nodes by their ID
		return this.id <= o.id ? -1 : 1;
	}
	
	/**
	 * 
	 */
	@Override
	public String toString() {
		return "[SearchSpaceNode id= " + this.id +" depth= " + this.getDepth() + " cost= " + this.getCost() +" " + (this.isRootNode() ? "" : "operator= " + this.getGenerator()) + "]";
	}
}
