package org.swingBean.util;

public class NameUtils {
	
    public static String acessorToProperty(String acessorName){
		int initLetter = 3;
		if(acessorName.startsWith("is")){
			initLetter = 2;
		}
		if(Character.isLowerCase(acessorName.charAt(initLetter+1)))
			return acessorName.substring(initLetter,initLetter+1).toLowerCase()+acessorName.substring(initLetter+1);
		else
			return acessorName.substring(initLetter);
    }

	public static String propertyToGetter(String propertieName, boolean isBoolean) {
		if(isBoolean)
			return "is"+propertieName.substring(0,1).toUpperCase()+propertieName.substring(1);
		return "get"+propertieName.substring(0,1).toUpperCase()+propertieName.substring(1);
	}
	
	public static String propertyToGetter(String propertieName) {
		return propertyToGetter(propertieName, false);
	}

	public static String propertyToSetter(String propertieName) {
		return "set"+propertieName.substring(0,1).toUpperCase()+propertieName.substring(1);
	}

	public static String toDefaultName(String propertyName) {
		if(propertyName.indexOf(".")>=0){
			String newProperty = propertyName.substring(propertyName.indexOf(".")+1);
			return toDefaultName(newProperty);
		}
		String defaultName = String.valueOf(propertyName.charAt(0)).toUpperCase();
		for(int i=1;i<propertyName.length();i++){
			if(Character.isLowerCase(propertyName.charAt(i))){
				defaultName += propertyName.charAt(i);
			}else{
				if( (Character.isDigit(propertyName.charAt(i)) && Character.isDigit(propertyName.charAt(i-1)))
				   || (Character.isUpperCase(propertyName.charAt(i)) && Character.isUpperCase(propertyName.charAt(i-1))))
					defaultName += propertyName.charAt(i);
				else
					defaultName += " "+propertyName.charAt(i);
			}
		}
		return defaultName;
	}

}