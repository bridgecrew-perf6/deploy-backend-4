package com.vtw.deploy.user.dto;

public class UserCountDTO extends UserDTO{
	private int countN;//오늘 진행중인 일정 개수
	private int countY;//오늘 완료된 일정 개수
	private int countE;//오늘 완료 안된, 초과된 일정 개수
	public UserCountDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserCountDTO(int countN, int countY, int countE) {
		super();
		this.countN = countN;
		this.countY = countY;
		this.countE = countE;
	}
	public int getCountN() {
		return countN;
	}
	public void setCountN(int countN) {
		this.countN = countN;
	}
	public int getCountY() {
		return countY;
	}
	public void setCountY(int countY) {
		this.countY = countY;
	}
	public int getCountE() {
		return countE;
	}
	public void setCountE(int countE) {
		this.countE = countE;
	}
}
