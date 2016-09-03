package com.ov.spark.training;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;

import scala.Tuple2;

public class LogProcessing {

	@SuppressWarnings("resource")
	public static void processingLog( String iFilename )
	{

		System.setProperty("hadoop.home.dir", "C:/Hadoop");
		SparkConf conf = new SparkConf().setMaster("local").setAppName("Parse log file");
		JavaSparkContext sc = new JavaSparkContext(conf);
		JavaRDD<String> input = sc.textFile( iFilename );
		JavaRDD<String> text = input.filter(l -> Filter.validIP(l));
		JavaRDD<LineParser> data = text.flatMap(new FlatMapFunction<String, LineParser>() {
			private static final long serialVersionUID = 1L;

			public Iterable<LineParser> call(String iLine) {
				return (Iterable<LineParser>) parseFile(iLine);
			}
		});
		JavaPairRDD<String, Session> lSession = data.mapToPair(ses -> constructMapOfSession(ses)).reduceByKey((ses1,ses2) -> createSession(ses1,ses2));
		lSession.saveAsTextFile( "output" );
	}
	private static Session createSession(Session iSes1, Session iSes2){

		String lListUrl = iSes1.getmListUrl()+","+iSes2.getmListUrl();
		String lListRequete = iSes1.getmListRequete()+","+iSes2.getmListRequete();
		String lIp = iSes1.getmIp();
		int lNumberOfPage = iSes1.getmNumberOfPage()+iSes2.getmNumberOfPage();
		Date lTimesTampConnect1 = iSes1.getmTimesTampConnect();
		Date lTimesTampConnect2 = iSes2.getmTimesTampConnect();
		Date lTimesTampConnect;
		Date lTimesTampDisConnect;
		long lDuration = 0;
		long lTime1MilSec = lTimesTampConnect1.getTime();
		long lTime2MilSec = lTimesTampConnect2.getTime();
		if(lTimesTampConnect1.before(lTimesTampConnect2)){
			lTimesTampConnect = lTimesTampConnect1;
			lTimesTampDisConnect = lTimesTampConnect2;
			lDuration = lTime2MilSec - lTime1MilSec;
		}else {
			lTimesTampConnect = lTimesTampConnect2;
			lTimesTampDisConnect = lTimesTampConnect1;
			lDuration = lTime1MilSec - lTime2MilSec;
		}
		return new Session(lIp, lTimesTampConnect, lTimesTampDisConnect,
				lDuration, lListUrl, lListRequete, lNumberOfPage);

	}

	private static Tuple2<String,Session> constructMapOfSession(LineParser iData){
		String lUser = iData.getmIp();
		Date lTimesTampConnect = iData.getmTimesTamp();
		String lListRequete = iData.getmRequete();
		String lListUrl = iData.getmUrl();
		int lDuration = 0;
		int lNumberOfPage = 1;
		Session lSessionofUser = new Session(lUser, lTimesTampConnect, lTimesTampConnect,
				lDuration, lListUrl, lListRequete, lNumberOfPage);
		return new Tuple2<String,Session>(lUser,lSessionofUser);

	}

	private static Iterable<LineParser> parseFile(String iLine){
		String[] lSplitLine = iLine.split(" ");
		String lIp = lSplitLine[0];
		String lTimesLine = lSplitLine[3];
		String lTimes = lTimesLine.replace("[", "");
		lTimes = lTimes.replaceFirst(":", " ");
		lTimes = lTimes.replace("/", " ");
		DateFormat df = new SimpleDateFormat("dd MMM yyyy hh:mm:ss",Locale.ENGLISH);
		Date lTimesTamp = null;
		try {
			lTimesTamp = df.parse(lTimes);
		} catch (ParseException e) {
			System.out.println("Fail to parse line");
		}
		String lRequete = lSplitLine[5];
		String lUrl = lSplitLine[6];
		LineParser lParse = new LineParser(lIp, lTimesTamp, lUrl, lRequete);
		return Arrays.asList(lParse);


	}

	public static void main( String[] args )
	{
		if( args.length == 0 )
		{
			System.out.println( "Please try to enter a file" );
			System.exit( 0 );
		}

		processingLog( args[ 0 ] );
	}

}
