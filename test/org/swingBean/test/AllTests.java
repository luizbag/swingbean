package org.swingBean.test;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.swingBean.actions.test.TestActionChainFactory;
import org.swingBean.descriptor.test.TestBeanTableModel;
import org.swingBean.descriptor.test.TestGenericFieldDescriptor;
import org.swingBean.descriptor.test.TestTreeFactory;
import org.swingBean.descriptor.test.TestXMLDescriptor;
import org.swingBean.gui.test.TestJBeanPanel;
import org.swingBean.util.test.TestBeanUtils;
import org.swingBean.util.test.TestNameUtils;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.swingBean.test");
		//$JUnit-BEGIN$
		suite.addTestSuite(TestGenericFieldDescriptor.class);
		suite.addTestSuite(TestXMLDescriptor.class);
		suite.addTestSuite(TestJBeanPanel.class);
		suite.addTestSuite(TestBeanUtils.class);
		suite.addTestSuite(TestNameUtils.class);
		suite.addTestSuite(TestActionChainFactory.class);
		suite.addTestSuite(TestBeanTableModel.class);	
		suite.addTestSuite(TestTreeFactory.class);
		//$JUnit-END$
		return suite;
	}

}
