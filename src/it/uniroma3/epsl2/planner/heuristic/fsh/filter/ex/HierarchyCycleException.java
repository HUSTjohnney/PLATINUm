package it.uniroma3.epsl2.planner.heuristic.fsh.filter.ex;

/**
 * 
 * @author anacleto
 *
 */
public class HierarchyCycleException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param msg
	 */
	public HierarchyCycleException(String msg) {
		super(msg);
	}
}
