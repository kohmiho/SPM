package com.kohmiho.spm.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static final DateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * MM/dd/yyyy
	 */
	public static final DateFormat formatter2 = new SimpleDateFormat("MM/dd/yyyy");

	/**
	 * MMM dd yyyy HH:mma
	 */
	public static final DateFormat formatter3 = new SimpleDateFormat("MMM dd yyyy HH:mma");

	/**
	 * yyyy-MM-dd
	 */
	public static final DateFormat formatter4 = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * dd-MMM-yy
	 */
	public static final DateFormat formatter5 = new SimpleDateFormat("dd-MMM-yy");

	/**
	 * 
	 */
	public static final DateFormat formatter6 = new SimpleDateFormat("yyyyMMdd-HHmmss");

	/**
	 * yyyy-MM-dd HH:mm:ss (2009-01-01 00:00:00)
	 * 
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Date parse1(String dateStr) throws ParseException {
		return formatter1.parse(dateStr);
	}

	/**
	 * MM/dd/yyyy (09/21/2009)
	 * 
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Date parse2(String dateStr) throws ParseException {
		return formatter2.parse(dateStr);
	}

	/**
	 * MMM dd yyyy HH:mma (Jun 30 2009 12:00AM)
	 * 
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Date parse3(String dateStr) throws ParseException {
		return formatter3.parse(dateStr);
	}

	/**
	 * yyyy-MM-dd (2009-01-01)
	 * 
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Date parse4(String dateStr) throws ParseException {
		return formatter4.parse(dateStr);
	}

	/**
	 * dd-MMM-yy (01-Mar-14)
	 * 
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 */
	public static Date parse5(String dateStr) throws ParseException {
		return formatter5.parse(dateStr);
	}

	/**
	 * yyyy-MM-dd HH:mm:ss (2009-01-01 00:00:00)
	 * 
	 * @param date
	 * @return
	 */
	public static String format1(Date date) {
		return formatter1.format(date);
	}

	/**
	 * MM/dd/yyyy (01/01/2009)
	 * 
	 * @param date
	 * @return
	 */
	public static String format2(Date date) {
		return formatter2.format(date);
	}

	/**
	 * MMM dd yyyy HH:mma (Jun 30 2009 12:00AM)
	 * 
	 * @param date
	 * @return
	 */
	public static String format3(Date date) {
		return formatter3.format(date);
	}

	/**
	 * yyyy-MM-dd (2009-01-01)
	 * 
	 * @param date
	 * @return
	 */
	public static String format4(Date date) {
		return formatter4.format(date);
	}

	/**
	 * dd-MMM-yy (01-Mar-14)
	 * 
	 * @param date
	 * @return
	 */
	public static String format5(Date date) {
		return formatter5.format(date);
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static String format6(Date date) {
		return formatter6.format(date);
	}

	public static void main(String[] args) throws ParseException {

		Date date = new Date();
		System.out.println(DateFormatter.format5(date));
		System.out.println(DateFormatter.parse5(DateFormatter.format5(date)));

	}
}
