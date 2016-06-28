package it.uniroma3.epsl2.executive.pdb;

/**
 * 
 * @author anacleto
 *
 */
public enum ControllabilityType {
	
	/**
	 * The token is completely under the control of the executive
	 */
	CONTROLLABLE,
	
	/**
	 * The executor can decide the start time of the activity but not the duration duration
	 */
	UNCONTROLLABLE_DURATION,
	
	/**
	 * The token belongs to an external component of the domain
	 */
	EXTERNAL_TOKEN
}
