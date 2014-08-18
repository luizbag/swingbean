package org.swingBean.descriptor.validator;

import org.swingBean.descriptor.FieldDescriptor;
import org.swingBean.descriptor.TypeConstants;
import org.swingBean.exception.ComponentCreationException;
import org.swingBean.exception.InvalidPropertyException;
import org.swingBean.gui.wrappers.WrapperFactory;

public class ValidatorFactory {
	
	public void createValidators(String property, FieldDescriptor descriptor){
		String type = WrapperFactory.getComponentType(property, descriptor);
		if(type == null){
			throw new InvalidPropertyException("There is no component type defined for property "+property);
		}
		if(type.equals(TypeConstants.TEXT) || type.equals(TypeConstants.LARGE_TEXT) || type.equals(TypeConstants.PASSWORD)){
			if(descriptor.getParameter(property,"size") != null || descriptor.getParameter(property,"minSize") != null){
				StringSizeValidator validator = new StringSizeValidator();
				validator.setProperty(property);
				if(descriptor.getParameter(property,"minSize") != null)
					validator.setMinSize(Integer.parseInt(descriptor.getParameter(property,"minSize")));
				if(descriptor.getParameter(property,"size") != null)
					validator.setMaxSize(Integer.parseInt(descriptor.getParameter(property,"size")));
				descriptor.addValidator(validator);
			}
			if(descriptor.getParameter(property,"pattern")!= null){
				PatternValidator validator = new PatternValidator();
				validator.setProperty(property);
				validator.setPattern(descriptor.getParameter(property,"pattern"));
				if(descriptor.getParameter(property,"formatExample")!= null)
					validator.setFormatExample(descriptor.getParameter(property,"formatExample"));
				descriptor.addValidator(validator);
			}
		}
		if(type.equals(TypeConstants.INTEGER) || type.equals(TypeConstants.LONG) || type.equals(TypeConstants.FLOAT) || type.equals(TypeConstants.DOUBLE)){
			if(descriptor.getParameter(property,"max") != null || descriptor.getParameter(property,"min") != null){
				NumericRangeValidator validator = new NumericRangeValidator();
				validator.setProperty(property);
				if(descriptor.getParameter(property,"max") != null){
					validator.setMaxValue(Double.parseDouble(descriptor.getParameter(property,"max")));
				}
				if(descriptor.getParameter(property,"min") != null){
					validator.setMinValue(Double.parseDouble(descriptor.getParameter(property,"min")));
				}
				descriptor.addValidator(validator);
			}
		}
		if(type.equals(TypeConstants.MULTIPLE_LIST) || type.equals(TypeConstants.CHECKBOX_LIST)){
			if(descriptor.getParameter(property,"maxSelected") != null || descriptor.getParameter(property,"minSelected") != null){
				SelectedRangeValidator validator = new SelectedRangeValidator();
				validator.setProperty(property);
				if(descriptor.getParameter(property,"maxSelected") != null){
					validator.setMaxValue(Integer.parseInt(descriptor.getParameter(property,"maxSelected")));
				}
				if(descriptor.getParameter(property,"minSelected") != null){
					validator.setMinValue(Integer.parseInt(descriptor.getParameter(property,"minSelected")));
				}
				descriptor.addValidator(validator);
			}
		}
		if(descriptor.getParameter(property,"mandatory") != null && descriptor.getParameter(property,"mandatory").equals("true")){
			MandatoryValidator validator = new MandatoryValidator();
			validator.setProperty(property);
			descriptor.addValidator(validator);
		}
		
		if(descriptor.getParameter(property,"customValidator") != null){
			try {
				Class validatorClass = Class.forName(descriptor.getParameter(property,"customValidator"));
				Validator validator = (Validator)validatorClass.newInstance();
				validator.setProperty(property);
				descriptor.addValidator(validator);
			} catch (Exception e) {
				throw new ComponentCreationException("Can't create custom validator for property "+property);
			}
		}

	}

}
