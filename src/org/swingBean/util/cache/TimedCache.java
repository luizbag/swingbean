package org.swingBean.util.cache;

import java.lang.reflect.Proxy;

public class TimedCache extends BasicCache {
	
	private long expirationPeriod;
	private long cacheTime = -1;
	
	public static Object createProxy(Object obj){
		return Proxy.newProxyInstance(obj.getClass().getClassLoader(),
									  obj.getClass().getInterfaces(),
									  new TimedCache(obj));
	}
	public static Object createProxy(Object obj, long expirationPeriod){
		return Proxy.newProxyInstance(obj.getClass().getClassLoader(),
									  obj.getClass().getInterfaces(),
									  new TimedCache(obj, expirationPeriod));
	}

	public TimedCache(Object obj, long expirationPeriod) {
		super(obj);
		this.expirationPeriod = expirationPeriod;
	}
	
	public TimedCache(Object obj) {
		super(obj);
		this.expirationPeriod = 10*60*1000; //10 minutos
	}

	@Override
	protected boolean isCacheExpired() {
		long currentTime = System.currentTimeMillis();
		if(cacheTime == -1){
			cacheTime = currentTime;
			return false;
		}
		if(currentTime - cacheTime > expirationPeriod){
			clearCache();
			cacheTime = currentTime;
			return true;
		}
		return false;
	}

}
