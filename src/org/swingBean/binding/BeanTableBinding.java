package org.swingBean.binding;

import java.io.ObjectStreamException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.swingBean.descriptor.BeanTableModel;
import org.swingBean.gui.JBeanPanel;
import org.swingBean.util.NameUtils;

public class BeanTableBinding implements MethodInterceptor, BeanRetriever, ReplaceSerialization {

	private BeanTableModel model;

	private int lineNumber;

	private Object bean;

	public BeanTableBinding(BeanTableModel model, int lineNumber, Object bean) {
		this.model = model;
		this.lineNumber = lineNumber;
		this.bean = bean;
	}

	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		Object retValFromSuper = null;

		if (method.getName().equals("retrieveBean"))
			return retrieveBean();
		if (method.getName().equals("writeReplace"))
			return writeReplace();
		else if (method.getName().startsWith("get") && args.length == 0) {
			if (model.getPropertyCol(NameUtils.acessorToProperty(method
					.getName())) >= 0) {
				retValFromSuper = model.getValueAt(lineNumber, NameUtils
						.acessorToProperty(method.getName()));
			} else {
				retValFromSuper = method.invoke(bean, args);
			}
		} else if (method.getName().startsWith("set") && args.length == 1
				&& method.getReturnType() == Void.TYPE) {
			if (model.getPropertyCol(NameUtils.acessorToProperty(method
					.getName())) >= 0) {
				model.setValueAt(args[0], lineNumber, model
						.getPropertyCol(NameUtils.acessorToProperty(method
								.getName())));
			} else {
				method.invoke(bean, args);
			}
		} else if (!Modifier.isAbstract(method.getModifiers())) {
			retValFromSuper = method.invoke(bean, args);
		}
		return retValFromSuper;
	}

	public static Object createSynchonizedBean(Object bean, int lineNumber,
			BeanTableModel model) {
		try {
			BeanTableBinding interceptor = new BeanTableBinding(model,
					lineNumber, bean);
			Enhancer e = new Enhancer();
			e.setSuperclass(bean.getClass());
			e.setCallback(interceptor);
			e.setInterfaces(new Class[]{ReplaceSerialization.class,BeanRetriever.class});
			Object obj = e.create();
			return obj;
		} catch (Throwable e) {
			throw new Error(e.getMessage());
		}
	}

	public Object retrieveBean() {
		return bean;
	}

	public Object writeReplace() throws ObjectStreamException {
		return bean;
	}

}
