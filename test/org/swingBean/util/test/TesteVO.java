package org.swingBean.util.test;

public class TesteVO{
	
	private TesteVO innerVO;
	
	private String strProp;
	private int intProp;
	private boolean bolProp;
	
	public boolean isBolProp() {
		return bolProp;
	}
	public void setBolProp(boolean bolProp) {
		this.bolProp = bolProp;
	}
	public int getIntProp() {
		return intProp;
	}
	public void setIntProp(int intProp) {
		this.intProp = intProp;
	}
	public String getStrProp() {
		return strProp;
	}
	public void setStrProp(String strProp) {
		this.strProp = strProp;
	}
	public int getTwoInt(){
		return intProp*2;
	}
	public TesteVO(int prop2, String prop3) {
		intProp = prop2;
		strProp = prop3;
	}
	public TesteVO() {
	}
	public TesteVO getInnerVO() {
		return innerVO;
	}
	public void setInnerVO(TesteVO innerVO) {
		this.innerVO = innerVO;
	}
}

