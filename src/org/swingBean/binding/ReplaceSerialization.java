package org.swingBean.binding;

import java.io.ObjectStreamException;

public interface ReplaceSerialization {
	
	Object writeReplace() throws ObjectStreamException;

}
