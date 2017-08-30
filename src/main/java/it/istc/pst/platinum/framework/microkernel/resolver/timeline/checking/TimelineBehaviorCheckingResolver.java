package it.istc.pst.platinum.framework.microkernel.resolver.timeline.checking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import it.istc.pst.platinum.framework.domain.component.ComponentValue;
import it.istc.pst.platinum.framework.domain.component.Decision;
import it.istc.pst.platinum.framework.domain.component.ex.FlawSolutionApplicationException;
import it.istc.pst.platinum.framework.domain.component.sv.StateVariable;
import it.istc.pst.platinum.framework.domain.component.sv.StateVariableValue;
import it.istc.pst.platinum.framework.microkernel.annotation.inject.framework.ComponentPlaceholder;
import it.istc.pst.platinum.framework.microkernel.lang.flaw.Flaw;
import it.istc.pst.platinum.framework.microkernel.lang.flaw.FlawSolution;
import it.istc.pst.platinum.framework.microkernel.query.TemporalQueryType;
import it.istc.pst.platinum.framework.microkernel.resolver.Resolver;
import it.istc.pst.platinum.framework.microkernel.resolver.ResolverType;
import it.istc.pst.platinum.framework.microkernel.resolver.ex.InvalidBehaviorException;
import it.istc.pst.platinum.framework.microkernel.resolver.ex.UnsolvableFlawException;
import it.istc.pst.platinum.framework.time.lang.query.IntervalDistanceQuery;
import it.istc.pst.platinum.framework.time.tn.TimePoint;

/**
 * 
 * @author anacleto
 *
 */
public class TimelineBehaviorCheckingResolver extends Resolver implements Comparator<Decision> 
{
	@ComponentPlaceholder
	protected StateVariable sv;
	
	/**
	 * 
	 */
	protected TimelineBehaviorCheckingResolver() {
		super(ResolverType.TIMELINE_BEHAVIOR_CHECKING_RESOLVER.getLabel(), 
				ResolverType.TIMELINE_BEHAVIOR_CHECKING_RESOLVER.getFlawType());
	}
	
	/**
	 * 
	 */
	@Override
	public int compare(Decision d1, Decision d2) {
		// get start times
		TimePoint s1 = d1.getToken().getInterval().getStartTime();
		TimePoint s2 = d2.getToken().getInterval().getStartTime();
		return s1.getLowerBound() <= s2.getLowerBound() ? -1 : 1;
	}
	
	/**
	 * 
	 */
	@Override
	protected List<Flaw> doFindFlaws() 
	{
		// list of gaps
		List<Flaw> issues = new ArrayList<>();
		// get the "ordered" list of tokens on the component
		List<Decision> decs = this.sv.getActiveDecisions();
		
		// sort decisions
		Collections.sort(decs, this);
		
		// look for gaps
		for (int index = 0; index <= decs.size() - 2; index++) 
		{
			// get two adjacent tokens
			Decision left = decs.get(index);
			Decision right = decs.get(index + 1);
			
			// check distance between related temporal intervals
			IntervalDistanceQuery query = this.tdb.
					createTemporalQuery(TemporalQueryType.INTERVAL_DISTANCE);
			
			// set intervals
			query.setSource(left.getToken().getInterval());
			query.setTarget(right.getToken().getInterval());
			// process query
			this.tdb.process(query);
			// get result
			long lb = query.getDistanceLowerBound();
			long ub = query.getDistanceUpperBound();
			// check distance bounds
			if (lb == 0 && ub == 0) 
			{
				// get values
				StateVariableValue vi = (StateVariableValue) left.getValue();
				StateVariableValue vj = (StateVariableValue) right.getValue();
				// get direct successors of value vi
				List<ComponentValue> successors = this.sv.getDirectSuccessors(vi);
				if (!successors.contains(vj)) {
					// inconsistent behavior
					InvalidTransition issue = new InvalidTransition(this.sv, left, right);
					issues.add(issue);
				}
			}
		}
		
		// get detected gaps
		return issues;
	}
	
	/**
	 * 
	 */
	@Override
	protected void doApply(FlawSolution solution) 
			throws FlawSolutionApplicationException {
		// nothing to do
	}
	
	/**
	 * 
	 */
	@Override
	protected void doRetract(FlawSolution solution) {
		// nothing to do
	}

	/**
	 * 
	 */
	@Override
	protected void doRestore(FlawSolution solution) throws Exception {
		// nothing to do
	}
	
	/**
	 * 
	 */
	@Override
	protected void doComputeFlawSolutions(Flaw flaw) 
			throws UnsolvableFlawException {
		// simply throw exception
		throw new InvalidBehaviorException("Inconsistent temporal behavior of component " + this.sv.getName() + ":\n" + flaw);
	}
}
