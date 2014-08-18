package org.swingBean.util.cache;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class BasicCache implements InvocationHandler {
	
	private Object obj;
	private Map<CacheKey, Object> cacheMap = new HashMap<CacheKey, Object>();
	
	public BasicCache(Object obj) {
		this.obj = obj;
	}

	public static Object createProxy(Object obj){
		return Proxy.newProxyInstance(obj.getClass().getClassLoader(),
									  obj.getClass().getInterfaces(),
									  new BasicCache(obj));
	}
	

	public Object invoke(Object proxy, Method met, Object[] params)
			throws Throwable {
		
		if(met.isAnnotationPresent(Cache.class)){
			CacheKey key = new CacheKey(met.getName(),params);
			if(cacheMap.containsKey(key) && !isCacheExpired()){
				return cacheMap.get(key);
			}else{
				Object result = met.invoke(obj,params);
				cacheMap.put(key,result);
				return result;
			}
		}else{
			if(met.isAnnotationPresent(InvalidateCache.class))
				clearCache();
			return met.invoke(obj,params);
		}
	}

	protected void clearCache() {
		cacheMap.clear();
	}
	
	protected boolean isCacheExpired(){
		return false;
	}

	protected Object getObj() {
		return obj;
	}

}
