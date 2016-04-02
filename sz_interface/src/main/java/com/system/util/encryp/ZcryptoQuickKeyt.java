package com.system.util.encryp;

public class ZcryptoQuickKeyt {
	byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0};
	byte[] rk = {0, 0, 0, 0, 0, 0, 0, 0};
	
	public byte[] getIv() {
		return iv;
	}
	public void setIv(byte[] iv) {
		this.iv = iv;
	}
	public byte[] getRk() {
		return rk;
	}
	public void setRk(byte[] rk) {
		this.rk = rk;
	}
	
	
}
