package org.swingBean.util.cache;

import java.lang.reflect.Proxy;

public class VerifyCache extends BasicCache {
	
	private long informationTime = -1;
	
	public static Object createProxy(CacheVerifyer obj){
		return Proxy.newProxyInstance(obj.getClass().getClassLoader(),
									  obj.getClass().getInterfaces(),
									  new VerifyCache(obj));
	}

	public VerifyCache(CacheVerifyer obj) {
		super(obj);
	}

	@Override
	protected boolean isCacheExpired() {
		long currentTime = ((CacheVerifyer)getObj()).getInformationTimeMillis();
		if(informationTime == -1){
			informationTime = currentTime;
			return false;
		}
		if(currentTime != informationTime){
			clearCache();
			informationTime = currentTime;
			return true;
		}
		return false;
	}

}
