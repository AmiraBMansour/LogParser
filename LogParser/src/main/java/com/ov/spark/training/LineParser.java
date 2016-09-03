package com.ov.spark.training;

import java.util.Date;

public class LineParser {

	String mIp;
	Date mTimesTamp;
	String mUrl;
	String mRequete;
	public LineParser(String iIp, Date iTimesTamp, String iUrl, String iRequete) {
		this.mIp = iIp;
		this.mTimesTamp = iTimesTamp;
		this.mRequete = iRequete;
		this.mUrl = iUrl;		

	}
	public String getmIp() {
		return mIp;
	}

	public Date getmTimesTamp() {
		return mTimesTamp;
	}

	public String getmRequete() {
		return mRequete;
	}

	public String getmUrl() {
		return mUrl;
	}




}
