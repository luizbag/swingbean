package org.swingBean.descriptor;

import java.util.List;

public interface TableStateHolder<Bean> {   
	
	public void resetCounters();
	
	public List<Bean> getInserted();

	public List<Bean> getDeleted();

	public List<Bean> getUpdated();
}
