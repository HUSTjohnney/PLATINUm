package it.uniroma3.epsl2.executive.pdb;

/**
 * 
 * @author anacleto
 *
 */
public enum ExecutionNodeStatus {
	
	/**
	 * The token is in the plan but it cannot be executed yet.
	 */
	WAITING(0),
	
	/**
	 * The process has started the execution of the token.
	 */
	IN_EXECUTION(2),
	
	/**
	 * The token has been successfully executed
	 */
	EXECUTED(3);
	
	private int index;
	
	/**
	 * 
	 * @param index
	 */
	private ExecutionNodeStatus(int index) {
		this.index = index;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getIndex() {
		return index;
	}
}
