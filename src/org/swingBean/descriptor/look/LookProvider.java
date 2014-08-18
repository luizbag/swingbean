package org.swingBean.descriptor.look;

public class LookProvider {
	
	private static LookDescriptor look = new DefaultLookDescriptor();
	
	public static LookDescriptor getLook(){
		return look;
	}
	
	public static void setLook(LookDescriptor setLook){
		look = setLook;
	}

}
