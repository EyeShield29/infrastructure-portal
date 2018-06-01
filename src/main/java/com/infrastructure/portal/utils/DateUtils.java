package com.infrastructure.portal.utils;


import java.text.DateFormat;  
import java.text.SimpleDateFormat;  
import java.util.Date;  

  
/** 
 * 日期工具类 
 */  
public class DateUtils{  
      
    // ==格式到年==   
    /** 
     * 日期格式，年份，例如：2004，2008 
     */  
    public static final String DATE_FORMAT_YYYY = "yyyy";  
      
      
    // ==格式到年月 ==   
    /** 
     * 日期格式，年份和月份，例如：200707，200808 
     */  
    public static final String DATE_FORMAT_YYYYMM = "yyyyMM";  
  
    /** 
     * 日期格式，年份和月份，例如：200707，2008-08 
     */  
    public static final String DATE_FORMAT_YYYY_MM = "yyyy-MM";  
  
      
    // ==格式到年月日==   
    /** 
     * 日期格式，年月日，例如：050630，080808 
     */  
    public static final String DATE_FORMAT_YYMMDD = "yyMMdd";  
  
    /** 
     * 日期格式，年月日，用横杠分开，例如：06-12-25，08-08-08 
     */  
    public static final String DATE_FORMAT_YY_MM_DD = "yy-MM-dd";  
  
    /** 
     * 日期格式，年月日，例如：20050630，20080808 
     */  
    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";  
      
    /** 
     * 日期格式，年月日，用横杠分开，例如：2006-12-25，2008-08-08 
     */  
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";  
      
    /** 
     * 日期格式，年月日，例如：2016.10.05 
     */  
    public static final String DATE_FORMAT_POINTYYYYMMDD = "yyyy.MM.dd";  
      
    /** 
     * 日期格式，年月日，例如：2016年10月05日 
     */  
    public static final String DATE_TIME_FORMAT_YYYY年MM月DD日 = "yyyy年MM月dd日";  
      
      
    // ==格式到年月日 时分 ==   
      
    /** 
     * 日期格式，年月日时分，例如：200506301210，200808081210 
     */  
    public static final String DATE_FORMAT_YYYYMMDDHHmm = "yyyyMMddHHmm";  
  
    /** 
     * 日期格式，年月日时分，例如：20001230 12:00，20080808 20:08 
     */  
    public static final String DATE_TIME_FORMAT_YYYYMMDD_HH_MI = "yyyyMMdd HH:mm";  
      
    /** 
     * 日期格式，年月日时分，例如：2000-12-30 12:00，2008-08-08 20:08 
     */  
    public static final String DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI = "yyyy-MM-dd HH:mm";  
      
      
    // ==格式到年月日 时分秒==   
    /** 
     * 日期格式，年月日时分秒，例如：20001230120000，20080808200808 
     */  
    public static final String DATE_TIME_FORMAT_YYYYMMDDHHMISS = "yyyyMMddHHmmss";  
      
    /** 
     * 日期格式，年月日时分秒，年月日用横杠分开，时分秒用冒号分开 
     * 例如：2005-05-10 23：20：00，2008-08-08 20:08:08 
     */  
    public static final String DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS = "yyyy-MM-dd HH:mm:ss";  
  
      
    // ==格式到年月日 时分秒 毫秒==   
    /** 
     * 日期格式，年月日时分秒毫秒，例如：20001230120000123，20080808200808456 
     */  
    public static final String DATE_TIME_FORMAT_YYYYMMDDHHMISSSSS = "yyyyMMddHHmmssSSS";  
      
      
    // ==特殊格式==  
    /** 
     * 日期格式，月日时分，例如：10-05 12:00 
     */  
    public static final String DATE_FORMAT_MMDDHHMI = "MM-dd HH:mm";  
    
    public static String parseDateToStr(Date time, String timeFromat){  
        DateFormat dateFormat=new SimpleDateFormat(timeFromat);  
        return dateFormat.format(time);  
    }  
}
