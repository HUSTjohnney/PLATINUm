package it.istc.pst.platinum.framework.microkernel.resolver;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import it.istc.pst.platinum.framework.domain.component.DomainComponent;
import it.istc.pst.platinum.framework.microkernel.ApplicationFrameworkFactory;
import it.istc.pst.platinum.framework.microkernel.annotation.inject.framework.ComponentPlaceholder;

/**
 * 
 * @author anacleto
 *
 */
public class ResolverFactory extends ApplicationFrameworkFactory {

	/**
	 * 
	 */
	public ResolverFactory() {
		super();
	}
	
	/**
	 * 
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends Resolver> T create(ResolverType type, DomainComponent<?> component) 
	{
		// resolver
		T resv = null;
		try 
		{
			// get class
			Class<T> clazz = (Class<T>) Class.forName(type.getClassName());
			// get constructor
			Constructor<T> c = clazz.getDeclaredConstructor();
			// set constructor accessible
			c.setAccessible(true);
			resv = c.newInstance();
			
			// inject logger
			this.injectFrameworkLogger(resv);
			// inject reference to temporal facade
			this.injectTemporalFacade(resv);
			// inject parameter data base reference
			this.injectParameterFacade(resv);
			
			// set reference to component
			List<Field> fields = this.findFieldsAnnotatedBy(clazz, ComponentPlaceholder.class);
			for (Field field : fields) {
				// inject reference
				field.setAccessible(true);
				field.set(resv, component);
			}
			
			// complete initialization
			this.doCompleteApplicationObjectInitialization(resv);
			// add entry to the registry
			this.register(resv);
		}
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
			throw new RuntimeException(ex.getMessage());  
		} 
		catch (InstantiationException | SecurityException | NoSuchMethodException | ClassNotFoundException ex) {
			throw new RuntimeException(ex.getMessage());
		}
		// get resolver
		return resv;
	}
}
