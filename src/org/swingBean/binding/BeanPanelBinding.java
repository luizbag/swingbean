package org.swingBean.binding;

import java.io.ObjectStreamException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.swingBean.gui.JBeanPanel;
import org.swingBean.util.NameUtils;

public class BeanPanelBinding implements MethodInterceptor,ReplaceSerialization {

	private JBeanPanel panel;
	private Object bean;

	public BeanPanelBinding(JBeanPanel panel, Object bean) {
		this.panel = panel;
		this.bean = bean;
	}

	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		Object retValFromSuper = null;
		try {
			if (method.getName().equals("writeReplace"))
				return writeReplace();
			if (method.getName().startsWith("get")
					&& args.length == 0
					&& panel.existsProperty(NameUtils.acessorToProperty(method
							.getName()))) {
				retValFromSuper = panel.getPropertyValue(NameUtils
						.acessorToProperty(method.getName()));
			} else if (!Modifier.isAbstract(method.getModifiers())) {
				retValFromSuper = method.invoke(bean, args);
			}
		} finally {
			if (method.getName().startsWith("set")
					&& args.length == 1
					&& method.getReturnType() == Void.TYPE
					&& panel.existsProperty(NameUtils.acessorToProperty(method
							.getName()))) {
				panel.setPropertyValue(NameUtils.acessorToProperty(method
						.getName()), args[0]);
			}
		}
		return retValFromSuper;
	}

	public static Object createSynchonizedBean(Object bean,
			JBeanPanel panel) {
		try {
			BeanPanelBinding interceptor = new BeanPanelBinding(panel, bean);
			Enhancer e = new Enhancer();
			e.setSuperclass(bean.getClass());
			e.setInterfaces(new Class[]{ReplaceSerialization.class});
			e.setCallback(interceptor);
			Object obj = e.create();
			return obj;
		} catch (Throwable e) {
			throw new Error(e.getMessage());
		}
	}
	
	public Object writeReplace() throws ObjectStreamException{
		panel.populateBean(bean);
		return bean;
	}

}
