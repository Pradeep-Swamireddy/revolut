package com.revolut.main;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Main {
	public static void main(String[] args) {
		//MathContext mc = new MathContext(12, RoundingMode.HALF_UP);
		BigDecimal a = new BigDecimal(100);
		BigDecimal b = new BigDecimal(50.525);
		System.out.println(a.subtract(b).setScale(2, RoundingMode.HALF_DOWN));
	}
}
