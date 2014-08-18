package org.swingBean.util.cache;

import java.lang.reflect.Array;
import java.util.Arrays;

public class CacheKey {
	
	private final Object[] params;
	private final String methodName;
	
	public CacheKey(String name, Object[] params) {
		methodName = name;
		this.params = params;
	}

	@Override
	public boolean equals(Object obj) {
		if(! (obj instanceof CacheKey))
			return false;
		CacheKey other = (CacheKey)obj;
		if(!methodName.equals(other.getMethodName()))
			return false;
		if(params != null){
			if(other.getParams()== null || params.length != other.getParams().length)
				return false;
			for(int i=0;i<params.length;i++){
				if(params[i] == null && other.getParams()[i]!=null || params[i] != null && other.getParams()[i]==null)
					return false;
				if(params[i] == null && other.getParams()[i]==null)
					continue;
				if(params[i].getClass() != other.getParams()[i].getClass())
					return false;
				if(params[i] instanceof Object[]){
					if(!Arrays.equals((Object[])params[i],(Object[])other.getParams()[i]))
						return false;
				}else if(params[i].getClass() == int[].class){
					if(!Arrays.equals((int[])params[i],(int[])other.getParams()[i]))
						return false;
				}else if(params[i].getClass() == byte[].class){
					if(!Arrays.equals((byte[])params[i],(byte[])other.getParams()[i]))
						return false;
				}else if(params[i].getClass() == short[].class){
					if(!Arrays.equals((short[])params[i],(short[])other.getParams()[i]))
						return false;
				}else if(params[i].getClass() == long[].class){
					if(!Arrays.equals((long[])params[i],(long[])other.getParams()[i]))
						return false;
				}else if(params[i].getClass() == char[].class){
					if(!Arrays.equals((char[])params[i],(char[])other.getParams()[i]))
						return false;
				}else if(params[i].getClass() == double[].class){
					if(!Arrays.equals((double[])params[i],(double[])other.getParams()[i]))
						return false;
				}else if(params[i].getClass() == float[].class){
					if(!Arrays.equals((float[])params[i],(float[])other.getParams()[i]))
						return false;
				}else if(!params[i].equals(other.getParams()[i])){
					return false;
				}
			}
		}else{
			if(other.getParams() != null)
				return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hashcode = methodName.hashCode();
		if(params != null){
			for(Object obj : params)
				if(obj == null)
					hashcode ^= "null".hashCode();
				else
					if(obj.getClass().isArray())
						hashcode ^= Array.getLength(obj);
					else
						hashcode ^= obj.hashCode();
		}
		return hashcode;
	}

	public String getMethodName() {
		return methodName;
	}

	public Object[] getParams() {
		return params;
	}
	
	
	
	

}
