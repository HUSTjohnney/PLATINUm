package it.uniroma3.epsl2.framework.microkernel.query;

import java.lang.reflect.Constructor;

/**
 * 
 * @author anacleto
 *
 */
public class ParameterQueryFactory 
{
	private static ParameterQueryFactory INSTANCE = null;
	
	private ParameterQueryFactory() {}
	
	/**
	 * 
	 * @return
	 */
	public static ParameterQueryFactory getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ParameterQueryFactory();
		}
		return INSTANCE;
	}
	
	/**
	 * 
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public final <T extends ParameterQuery> T create(ParameterQueryType type) {
		// query instance
		T query = null;
		try {
			// get class
			Class<T> clazz = (Class<T>) Class.forName(type.getQueryClassName());
			// get constructor
			Constructor<T> c = clazz.getDeclaredConstructor();
			c.setAccessible(true);
			// create instance
			query = c.newInstance();
		}
		catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
		// get created instance
		return query;
	}
}
