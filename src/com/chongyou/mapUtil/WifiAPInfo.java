package com.chongyou.mapUtil;

public class WifiAPInfo {
	private int id;
	private double x;
	private double y;
	private double AP1;
	private double AP2;
	private double AP3;
	private double AP4;
	
	public WifiAPInfo() {
		super();
	}
	
	public WifiAPInfo(int id, double x, double y, double aP1, double aP2,
			double aP3, double aP4, double aP5) {
		super();
		this.id = id;
		this.x = x;
		this.y = y;
		AP1 = aP1;
		AP2 = aP2;
		AP3 = aP3;
		AP4 = aP4;
		AP5 = aP5;
	}
	

	@Override
	public String toString() {
		return "WifiAPInfo [id=" + id + ", x=" + x + ", y=" + y + ", AP1="
				+ AP1 + ", AP2=" + AP2 + ", AP3=" + AP3 + ", AP4=" + AP4
				+ ", AP5=" + AP5 + "]";
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getAP1() {
		return AP1;
	}
	public void setAP1(double aP1) {
		AP1 = aP1;
	}
	public double getAP2() {
		return AP2;
	}
	public void setAP2(double aP2) {
		AP2 = aP2;
	}
	public double getAP3() {
		return AP3;
	}
	public void setAP3(double aP3) {
		AP3 = aP3;
	}
	public double getAP4() {
		return AP4;
	}
	public void setAP4(double aP4) {
		AP4 = aP4;
	}
	public double getAP5() {
		return AP5;
	}
	public void setAP5(double aP5) {
		AP5 = aP5;
	}
	private double AP5;
}
