package org.swingBean.util;

import java.lang.reflect.Method;
import java.util.Comparator;

import org.swingBean.exception.ComponentExecutionException;

public class PropertyComparator implements Comparator {
	
	private String property;

	public PropertyComparator(String property) {
		this.property = property;
	}

	public int compare(Object bean1, Object bean2) {
		
		if(!bean1.getClass().isInstance(bean2))
			return 0;
		
		try {
			Method metodo = bean1.getClass().getMethod(NameUtils.propertyToGetter(property));
			Object valor1 = metodo.invoke(bean1);
			Object valor2 = metodo.invoke(bean2);
			if(valor1 == null && valor2 == null)
				return 0;
			if(valor1 != null && valor2 == null)
				return -1;
			if(valor1 == null && valor2 != null)
				return 1;
			if(valor1 instanceof Comparable){
				return ((Comparable)valor1).compareTo(valor2);
			}else{
				return 0;
			}
		} catch (Exception e) {
			throw new ComponentExecutionException("Error comparing values",e);
		}
	}

}
