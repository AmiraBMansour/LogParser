package com.ov.spark.training;

import java.io.Serializable;
import java.util.Date;

public class Session  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1;
	String mIp;
	Date mTimesTampConnect;
	Date mTimesTampDisConnect;
	// take the duration in millSeconde 
	long mDuration;
	String mListUrl;
	String mListRequete;
	int mNumberOfPage;

	public Session(String iIp,Date iTimesTampConnect,Date iTimesTampDisConnect,
			long iDuration, String iListUrl,String iListRequete,int iNumberOfPage) {
		this.mDuration = iDuration;
		this.mIp = iIp;
		this.mListRequete = iListRequete;
		this.mListUrl = iListUrl;
		this.mNumberOfPage = iNumberOfPage;
		this.mTimesTampConnect = iTimesTampConnect;
		this.mTimesTampDisConnect = iTimesTampDisConnect;
	}
	public String getmIp() {
		return mIp;
	}
	public void setmIp(String mIp) {
		this.mIp = mIp;
	}
	public Date getmTimesTampConnect() {
		return mTimesTampConnect;
	}
	public void setmTimesTampConnect(Date mTimesTampConnect) {
		this.mTimesTampConnect = mTimesTampConnect;
	}
	public Date getmTimesTampDisConnect() {
		return mTimesTampDisConnect;
	}
	public void setmTimesTampDisConnect(Date mTimesTampDisConnect) {
		this.mTimesTampDisConnect = mTimesTampDisConnect;
	}
	public String getmListUrl() {
		return mListUrl;
	}
	public void setmListUrl(String mListUrl) {
		this.mListUrl = mListUrl;
	}
	public String getmListRequete() {
		return mListRequete;
	}
	public void setmListRequete(String mListRequete) {
		this.mListRequete = mListRequete;
	}
	public long getmDuration() {
		return mDuration;
	}
	public void setmDuration(long mDuration) {
		this.mDuration = mDuration;
	}
	public int getmNumberOfPage() {
		return mNumberOfPage;
	}
	public void setmNumberOfPage(int mNumberOfPage) {
		this.mNumberOfPage = mNumberOfPage;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		
		String details = "Timestamp de connection: "+mTimesTampConnect
				+"\nTimestamp de deconnection: "+mTimesTampDisConnect
				+"\nDure: "+mDuration+" (millSeconde) "
				+"\nList des requetes: "+mListRequete
				+"\nlist des url visites: "+mListUrl
				+"\nNumber de Page visites: "+mNumberOfPage;
		return details;
	}

}
