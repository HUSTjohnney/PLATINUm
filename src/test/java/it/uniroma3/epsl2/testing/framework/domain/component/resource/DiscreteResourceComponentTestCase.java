package it.uniroma3.epsl2.testing.framework.domain.component.resource;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import it.uniroma3.epsl2.framework.domain.component.ComponentValue;
import it.uniroma3.epsl2.framework.domain.component.DomainComponentFactory;
import it.uniroma3.epsl2.framework.domain.component.DomainComponentType;
import it.uniroma3.epsl2.framework.domain.component.ex.DecisionPropagationException;
import it.uniroma3.epsl2.framework.domain.component.ex.RelationPropagationException;
import it.uniroma3.epsl2.framework.domain.component.resource.costant.DiscreteResource;
import it.uniroma3.epsl2.framework.lang.flaw.Flaw;
import it.uniroma3.epsl2.framework.lang.plan.Decision;
import it.uniroma3.epsl2.framework.lang.plan.RelationType;
import it.uniroma3.epsl2.framework.lang.plan.relations.parameter.BindParameterRelation;
import it.uniroma3.epsl2.framework.microkernel.query.ParameterQueryType;
import it.uniroma3.epsl2.framework.microkernel.resolver.ex.UnsolvableFlawFoundException;
import it.uniroma3.epsl2.framework.parameter.ParameterDataBaseFacade;
import it.uniroma3.epsl2.framework.parameter.ParameterDataBaseFacadeFactory;
import it.uniroma3.epsl2.framework.parameter.ParameterDataBaseFacadeType;
import it.uniroma3.epsl2.framework.parameter.lang.NumericParameter;
import it.uniroma3.epsl2.framework.parameter.lang.query.CheckValuesParameterQuery;
import it.uniroma3.epsl2.framework.time.TemporalDataBaseFacade;
import it.uniroma3.epsl2.framework.time.TemporalDataBaseFacadeFactory;
import it.uniroma3.epsl2.framework.time.TemporalDataBaseFacadeType;
import it.uniroma3.epsl2.framework.utils.log.FrameworkLoggerFactory;
import it.uniroma3.epsl2.framework.utils.log.FrameworkLoggingLevel;

/**
 * 
 * @author anacleto
 *
 */
public class DiscreteResourceComponentTestCase 
{
	private TemporalDataBaseFacade tdb;
	private ParameterDataBaseFacade pdb;
	private DiscreteResource resource;
	
	/**
	 * 
	 */
	@Before
	public void init() {
		System.out.println("**********************************************************************************");
		System.out.println("************************* Discrete Resource Component Test Case ***********************");
		System.out.println("**********************************************************************************");
		
		// create logger
		FrameworkLoggerFactory lf = new FrameworkLoggerFactory();
		lf.createFrameworkLogger(FrameworkLoggingLevel.DEBUG);
		
		// create temporal facade
		TemporalDataBaseFacadeFactory tf = new TemporalDataBaseFacadeFactory();
		this.tdb = tf.createSingleton(TemporalDataBaseFacadeType.UNCERTAINTY_TEMPORAL_FACADE, 0, 100);
		
		// get parameter facade
		ParameterDataBaseFacadeFactory pf = new ParameterDataBaseFacadeFactory();
		this.pdb = pf.createSingleton(ParameterDataBaseFacadeType.CSP_PARAMETER_FACADE);
		
		// create unary resource
		DomainComponentFactory df = new DomainComponentFactory();
		this.resource = df.create("Disco1", DomainComponentType.RESOURCE_DISCRETE);
		// set minimum and maximum capacity
		this.resource.setMinCapacity(0);
		this.resource.setMaxCapacity(10);
		this.resource.setInitialCapacity(10);
		this.resource.addRequirementValue("REQUIREMENT");
	}
	
	/**
	 * 
	 */
	@After
	public void clear() {
		this.resource = null;
		this.pdb = null;
		System.gc();
		System.out.println();
		System.out.println("**********************************************************************************");
		System.out.println();
	}
	
	/**
	 * 
	 */
	@Test
	public void createDiscreteResourceTest() {
		System.out.println("[Test]: createDiscreteResourceTest() --------------------");
		System.out.println();
		
		// check state variable object
		Assert.assertNotNull(this.resource);
		// check values
		List<ComponentValue> values = this.resource.getValues();
		Assert.assertNotNull(values);
		Assert.assertTrue(values.size() == 1);
		// check min capacity
		Assert.assertTrue(this.resource.getMinCapacity() == 0);
		// check max capacity
		Assert.assertTrue(this.resource.getMaxCapacity() == 10);
		// add transitions
		System.out.println(this.resource);
	}
	
	/**
	 * 
	 */
	@Test
	public void addResourceRequirementsTest() {
		System.out.println("[Test]: addResourceRequirementsTest() --------------------");
		System.out.println();
		
		// get value
		ComponentValue requirement = this.resource.getValues().get(0);
		Assert.assertNotNull(requirement);
		Assert.assertTrue(requirement.getLabel().equals("REQUIREMENT"));
		
		// create decision
		Decision dec = this.resource.create(requirement, new String[] {"?a0"});
		Assert.assertNotNull(dec);
		Assert.assertNull(dec.getToken());
		System.out.println(dec);
		try
		{
			// add decision
			this.resource.add(dec);
			Assert.assertNotNull(dec.getToken());
			
			// bind parameter
			BindParameterRelation bind = this.resource.create(RelationType.BIND_PARAMETER, dec, dec);
			bind.setReference(dec);
			bind.setReferenceParameterLabel(dec.getParameterLabelByIndex(0));
			bind.setValue("3");
			// add relation
			this.resource.add(bind);

			// get parameter
			NumericParameter param = (NumericParameter) dec.getParameterByIndex(0);
			// check parameter value
			CheckValuesParameterQuery query = this.pdb.createQuery(ParameterQueryType.CHECK_PARAMETER_VALUES);
			query.setParameter(param);
			// process query
			this.pdb.process(query);
			// check value
			Assert.assertTrue(param.getLowerBound() == 3);
			Assert.assertTrue(param.getUpperBound() == 3);
			System.out.println(param);
		} 
		catch (RelationPropagationException | DecisionPropagationException ex) {
			System.err.println(ex.getMessage());
			Assert.assertTrue(false);
		}
	}
	
	/**
	 * 
	 */
	@Test
	public void addDecisionsAndFindPeaksTest1() {
		System.out.println("[Test]: addDecisionsAndFindPeaksTest1() --------------------");
		System.out.println();
		
		// get value
		ComponentValue requirement = this.resource.getValues().get(0);
		Assert.assertNotNull(requirement);
		Assert.assertTrue(requirement.getLabel().equals("REQUIREMENT"));
		
		try
		{
			// create decision
			Decision a1 = this.resource.create(
					requirement, 
					new String[] {"?a0"},
					new long[] {2, 4},
					new long[] {8, 10},
					new long[] {1, this.tdb.getHorizon()});
			// add decision
			this.resource.add(a1);
			
			
			// bind parameter
			BindParameterRelation bind = this.resource.create(RelationType.BIND_PARAMETER, a1, a1);
			bind.setReference(a1);
			bind.setReferenceParameterLabel(a1.getParameterLabelByIndex(0));
			bind.setValue("5");
			// add relation
			this.resource.add(bind);
			System.out.println("a1: " + a1);
			
			// create decision
			Decision a2 = this.resource.create(
					requirement, 
					new String[] {"?a1"},
					new long[] {4, 6},
					new long[] {16, 18},
					new long[] {1, this.tdb.getHorizon()});
			// add decision
			this.resource.add(a2);
			
			
			// bind parameter
			bind = this.resource.create(RelationType.BIND_PARAMETER, a2, a2);
			bind.setReference(a2);
			bind.setReferenceParameterLabel(a2.getParameterLabelByIndex(0));
			bind.setValue("5");
			// add relation
			this.resource.add(bind);
			System.out.println("a2: " + a2);
			
			// create decision
			Decision a3 = this.resource.create(
					requirement, 
					new String[] {"?a2"},
					new long[] {11, 13},
					new long[] {21, 23},
					new long[] {1, this.tdb.getHorizon()});
			// add decision
			this.resource.add(a3);
			
			// bind relation
			bind = this.resource.create(RelationType.BIND_PARAMETER, a3, a3);
			bind.setReference(a3);
			bind.setReferenceParameterLabel(a3.getParameterLabelByIndex(0));
			bind.setValue("7");
			// add relation
			this.resource.add(bind);
			System.out.println("a3: " + a3);
			
			
			// check peak
			List<Flaw> flaws = this.resource.detectFlaws();
			Assert.assertNotNull(flaws);
			Assert.assertTrue(!flaws.isEmpty());
			Assert.assertTrue(flaws.size() == 1);
			System.out.println("#" + flaws.size() + " peaks found");
			for (Flaw flaw : flaws) {
				System.out.println("peak -> " + flaw + "\n");
			}
		}
		catch (RelationPropagationException | DecisionPropagationException | UnsolvableFlawFoundException ex) {
			System.err.println(ex.getMessage());
			Assert.assertTrue(false);
		}
	}
}
