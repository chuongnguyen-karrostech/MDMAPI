package com.MDMREST.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

public class DateFunction {

	private static final Logger log = LoggerFactory.getLogger(DateFunction.class);

	public static String MMddyyyyHHmmss = "MM/dd/yyyy HH:mm:ss";
	public static String MMddyyyy = "MM/dd/yyyy";
	public static String yyyyMMdd = "yyyy/MM/dd";
	public static String yyyyMMddHHmmss = "yyyy/MM/dd HH:mm:ss";

	public static Date StringToDate(String strdate, String strformat) {
		SimpleDateFormat formatter = new SimpleDateFormat(strformat);
		try {
			return formatter.parse(strdate);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String DateTimeToString(Timestamp ts, String format) {
		if (ts == null)
			return "";
		return new SimpleDateFormat(format).format(ts);
	}

	public static int CompareDate(Timestamp ts1, Timestamp ts2) {
		SimpleDateFormat formatter = new SimpleDateFormat(yyyyMMdd);
		try {

			Date d1 = formatter.parse(DateTimeToString(ts1, yyyyMMdd));
			Date d2 = formatter.parse(DateTimeToString(ts2, yyyyMMdd));
			return d1.compareTo(d2);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static Timestamp CurrentTimeStamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static String CurrentTimeStamp(String format) {
		Date d = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String dateString = formatter.format(d);
		return dateString;
	}

	public static Timestamp NextTimeStamp() {
		DateTime currentdate = new DateTime(System.currentTimeMillis());
		return new Timestamp(currentdate.plusDays(1).getMillis());
	}

	public static String NextTimeStamp(String format) {
		DateTime currentdate = new DateTime(System.currentTimeMillis());
		return currentdate.plusDays(1).toString(format);
	}

	public static Timestamp AddTimeZone(Timestamp ts) {
		if (ts == null)
			return null;
		DateTimeZone tz = DateTimeZone.getDefault();
		Long instant = DateTime.now().getMillis();
		long offsetInMilliseconds = tz.getOffset(instant);
		return new Timestamp(ts.getTime() - offsetInMilliseconds);
	}

	public static Date getCurrentDateTime() {

		DateTimeZone tz = DateTimeZone.getDefault();
		Timestamp ts = Timestamp.valueOf(LocalDateTime.now());
		Long instant = DateTime.now().getMillis();
		long offsetInMilliseconds = tz.getOffset(instant);
		return new Date(ts.getTime() + offsetInMilliseconds);
	}

	public static Date getVehicleTime(String dpapi) {
		try {
			if (dpapi == null || dpapi.trim().isEmpty()) {
				log.error("Cannot get vehicle time of site. Driver portal api of site may be not config or empty.");
				return null;
			}
			RestTemplate restTemplate = new RestTemplate();
			String str = restTemplate.getForObject(dpapi + "/setting/getvehicletime", String.class);
			JSONObject bookList = new JSONObject(str);
			String date = bookList.getString("valueSetting");
			return StringToDate(date, "yyyy-MM-dd HH:mm:ss.SSS");
		} catch (JSONException e) {
			log.error(e.getMessage(), e.getCause());
		}
		return null;
	}
}
