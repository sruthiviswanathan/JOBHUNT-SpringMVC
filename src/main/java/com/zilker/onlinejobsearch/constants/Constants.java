package com.zilker.onlinejobsearch.constants;


/*
 * class for all constants
 */
public class Constants {

	public static final String EMAILPATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";
	public static final String PASSWORDPATTERN = "^(?=.*[A-Za-z#?!@$%^&*-])(?=.*\\d)[A-Za-z\\d#?!@$%^&*-]{8,15}$";
	public static final String HOST = "smtp.gmail.com";
	public static final String USER = "jobhuntintern2019@gmail.com";
	public static final String PASS = "jobhunt1234";
	public static final String FROM = "jobhuntintern2019@gmail.com";
	public static final String SUBJECT = "New Vacancy Available";
	public static final String MESSAGETEXT = "DON'T MISS OUT THIS OPPORTUNITY!! GET LOGGED INTO INDEED!!! GET PLACED IN YOUR DREAM COMPANY";
	public static final String CHARACTERSONLY = "^[a-zA-Z. ]{2,}$";
	public static final String URL ="^(https?|http)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
	public static final String FLOATNUMBERS="([0-9]*[.])?[0-9]+";
	public static final String INTEGERNUMBERS="^[0-9]+$";
	public static final String RATING="^[[0-4]{1}([.][0-9]+)]?|[5]$";
}
