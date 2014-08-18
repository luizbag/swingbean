package org.swingBean.util.test;

import junit.framework.TestCase;

import org.swingBean.util.cache.BasicCache;
import org.swingBean.util.cache.Cache;
import org.swingBean.util.cache.CacheVerifyer;
import org.swingBean.util.cache.InvalidateCache;
import org.swingBean.util.cache.TimedCache;
import org.swingBean.util.cache.VerifyCache;

public class TestCache extends TestCase {
	
	public interface CachedInterf{
		@Cache public int execute();
		@Cache public int executeInteger(Integer num);
		@Cache public int execute(int[] num);
		public int getTimesExecuted();
		@InvalidateCache public void setNum(int num);
		public void setInformationTimeMillis(long time);
	}
	
	public class CachedObj implements CachedInterf,  CacheVerifyer{
		
		public int timesExecuted = 0;
		public int num = 0;
		public long referenceTime = 1000;
		
		public int execute(){
			timesExecuted ++;
			return num*num;
		}
		public int getTimesExecuted() {
			return timesExecuted;
		}
		public void setNum(int num) {
			this.num = num;
		}
		public int executeInteger(Integer num) {
			timesExecuted ++;
			if(num == null)
				return this.num;
			return this.num*num;
		}
		public int execute(int[] num) {
			timesExecuted ++;
			int result = 1;
			for(int aux : num)
				result *= aux;
			return this.num*result;
		}
		public long getInformationTimeMillis() {
			return referenceTime;
		}
		public void setInformationTimeMillis(long time) {
			referenceTime = time;
		}
	}
	
	public void testCache(){
		CachedInterf obj = (CachedInterf) BasicCache.createProxy(new CachedObj());
		obj.setNum(2);
		assertEquals(obj.execute(),4);
		assertEquals(obj.execute(),4);
		assertEquals(obj.getTimesExecuted(),1);
		obj.setNum(3);
		assertEquals(obj.execute(),9);
		assertEquals(obj.execute(),9);
		assertEquals(obj.getTimesExecuted(),2);
	}
	
	public void testCacheNull(){
		CachedInterf obj = (CachedInterf) BasicCache.createProxy(new CachedObj());
		obj.setNum(2);
		assertEquals(obj.executeInteger(null),2);
		assertEquals(obj.executeInteger(null),2);
		assertEquals(obj.getTimesExecuted(),1);
	}
	
	public void testCacheParam(){
		CachedInterf obj = (CachedInterf) BasicCache.createProxy(new CachedObj());
		obj.setNum(2);
		assertEquals(obj.executeInteger(3),6);
		assertEquals(obj.executeInteger(2),4);
		assertEquals(obj.execute(new int[]{2,2}),8);
		assertEquals(obj.execute(new int[]{2,3}),12);
		assertEquals(obj.executeInteger(3),6);
		assertEquals(obj.executeInteger(2),4);
		assertEquals(obj.execute(new int[]{2,2}),8);
		assertEquals(obj.execute(new int[]{2,3}),12);
		assertEquals(obj.getTimesExecuted(),4);
	}
	
	public void testTimedCache() throws InterruptedException{
		CachedInterf obj = (CachedInterf) TimedCache.createProxy(new CachedObj(),2000);
		obj.setNum(2);
		assertEquals(obj.execute(),4);
		assertEquals(obj.execute(),4);
		assertEquals(obj.getTimesExecuted(),1);
		Thread.sleep(2500);
		assertEquals(obj.execute(),4);
		assertEquals(obj.execute(),4);
		assertEquals(obj.execute(),4);
		assertEquals(obj.getTimesExecuted(),2);
	}
	
	public void testVerifyCache() throws InterruptedException{
		CachedInterf obj = (CachedInterf) VerifyCache.createProxy(new CachedObj());
		obj.setNum(2);
		assertEquals(obj.execute(),4);
		assertEquals(obj.execute(),4);
		assertEquals(obj.getTimesExecuted(),1);
		obj.setInformationTimeMillis(2000);
		assertEquals(obj.execute(),4);
		assertEquals(obj.execute(),4);
		assertEquals(obj.execute(),4);
		assertEquals(obj.getTimesExecuted(),2);
	}

}
