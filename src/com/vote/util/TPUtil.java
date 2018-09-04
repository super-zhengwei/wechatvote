/*
 * @author 2006-7-21 Administrator
 *
 * VspdUtil is a part of com.vsagent.presatation.util 
 * ALL RIGHTS RESERVED.
 * COPYRIGHT (C) <���>
 */
package com.vote.util;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import javax.servlet.http.HttpSession;

/**
 * <PRE>
 * 
 * History 2006-7-21 created by  Administrator
 * 
 * 目的 :Vspd工具类
 *  
 * </PRE>
 */
public class TPUtil {
	
	/**
	   * 生成订单编号
	   * @return
	   */
	  public static String getOrderNumber(String herd,int a){
	       
	       String onum = null;
	       onum = TPUtil.getToday();
	       onum = herd + onum;//增加头
	       int n = herd.length();
	       onum = onum.substring(0,4+n) + onum.substring(5+n,n+7) + onum.substring(n+8,onum.length());
	       GregorianCalendar theCa = new GregorianCalendar();
	       int h =  12*theCa.get(Calendar.AM_PM) +  theCa.get(Calendar.HOUR);
	       int m =  theCa.get(Calendar.MINUTE);
	       //int s =  theCa.get(Calendar.SECOND);
	       
	       onum = onum + h;
	       if(m<10){
	           onum = onum +"0"+m;
	       }else{
	           onum = onum + m;
	       }
	       
	       return onum+TPUtil.vRandom(1,a); 
	       
	   }
  
      
   
  /**
   * 获取订单编号
   * @return
   */
  public static String getOrderNum(){
       
       String onum = null;
       onum = TPUtil.getToday_format();
       GregorianCalendar theCa = new GregorianCalendar();
       int h =  12*theCa.get(Calendar.AM_PM) +  theCa.get(Calendar.HOUR);
       int m =  theCa.get(Calendar.MINUTE);
       int s =  theCa.get(Calendar.SECOND);
       
       onum = onum + h;
       if(m<10){
           onum = onum +"0"+m;
       }else{
           onum = onum + m;
       }
       
       if(s<10){
           onum = onum +"0"+s;
       }else{
           onum = onum + s;
       }
       
       return "V"+onum.substring(1,onum.length())+TPUtil.vRandom(1,99); 
       
   }
 
   /**
     * 获取当前日期
	 * @return
	 */
   public static String getToday(){
       
       String m = "00";
       String d = "00";
       int style = DateFormat.MEDIUM;
       Locale locale = Locale.getDefault();
       long today = new java.util.Date().getTime();
       DateFormat df = DateFormat.getDateInstance(style,locale);
       String strToday=df.format(new java.util.Date(today));
       int i = strToday.indexOf("-");
       int t = strToday.length();
       String tempDay = strToday.substring(i+1,t);
       String[] str = tempDay.split("-");
       if(str[0].length() == 1){
           m = "0"+str[0];
       }else{
           m = str[0];
       }
       
       if(str[1].length() == 1){
           d = "0"+str[1];
       }else{
           d = str[1];
       }
       
       strToday = strToday.substring(0,5)+m+"-"+d;
       return strToday;
   }

   /**
    * 获取当前日期（和之前格式不一样）
     * @return
     */
  public static String getToday_format(){
      
      String m = "00";
      String d = "00";
      int style = DateFormat.MEDIUM;
      Locale locale = Locale.getDefault();
      long today = new java.util.Date().getTime();
      DateFormat df = DateFormat.getDateInstance(style,locale);
      String strToday=df.format(new java.util.Date(today));
      int i = strToday.indexOf("-");
      int t = strToday.length();
      String tempDay = strToday.substring(i+1,t);
      String[] str = tempDay.split("-");
      if(str[0].length() == 1){
          m = "0"+str[0];
      }else{
          m = str[0];
      }
      
      if(str[1].length() == 1){
          d = "0"+str[1];
      }else{
          d = str[1];
      }
      
      strToday = strToday.substring(2,4)+m+d;
      return strToday;
  }
  
   /**
    * 获取当前时间
	 * @return
	 */
   public static  String getTime(){
       
	   Calendar theCa;
       String nowT;
       theCa = new GregorianCalendar();
       theCa.setTime(new Date());
       String h = String.valueOf((12*theCa.get(Calendar.AM_PM) +  theCa.get(Calendar.HOUR)));
       if(h.length() == 1){
    	   h = "0" + h;
       }
       String m = String.valueOf(theCa.get(Calendar.MINUTE));
       if(m.length() == 1){
    	   m = "0" + m;
       }
       String s = String.valueOf(theCa.get(Calendar.SECOND));
       if(s.length() == 1){
    	   s = "0" + s;
       }
       nowT = " " + h + ":" + m + ":" + s;
       
       return nowT;
   }
   
   
   /**
     * 获取一个指定范围的随机数
     * @param upLimit
     *         开始值 
     * @param downLimit
     *         结束值
     * @return
     */
    public static int vRandom(int upLimit, int downLimit){
       
      return (int)(Math.random()*(upLimit-downLimit))+downLimit;
    }
   
   
   /**
     * 把haystack中的needle替换成str
     * 
     * @param haystack
     *            待处理的源字串
     * @param needle
     *            要被取代的字串
     * @param str
     *            替换成str字串
     * @return 已处理的字串
     */
    public static String replace(String needle, String str, String haystack) {
       
        if (haystack == null) {
            return null;
        }

        int i = 0;
        if ((i = haystack.indexOf(needle, i)) >= 0) {
            char[] line = haystack.toCharArray(); // 把字串类转成字符数组
            char[] newString = str.toCharArray();

            int needleLength = needle.length();

            StringBuffer buf = new StringBuffer(line.length);
            buf.append(line, 0, i).append(newString);

            i += needleLength;

            int j = i;

            while ((i = haystack.indexOf(needle, i)) > 0) {
                buf.append(line, j, i - j).append(newString);
                i += needleLength;
                j = i;
            }
            buf.append(line, j, line.length - j);
            return buf.toString();
        }
        
            return haystack;
        }
      
        
   /**
     * 四舍五入的方法(double)
     * 
     * @param d
     * @param scale
     * @return
     */
	public static double round_d(double d, int scale) {
	   
	   long temp=1;
       for (int i=scale; i>0; i--) {
               temp*=10;
       }
       d*=temp;
       long dl=Math.round(d);
       return (double)(dl)/temp;
   }

	/**
     * 四舍五入的方法(int)
	 * @param inte
	 * 
	 * @return
	 */
	public static int round_i(int num) {

        int k1 = 0;
        if (num > 1000) {
            k1 = num % 1000;
            if (k1 != 0) {
                k1 = k1 % 10;
                if (k1 != 0) {
                    if (k1 >= 5) {
                        num = num + (10 - k1);
                    } else {
                        num = num - k1;
                    }
                }
            }
        } else {
            k1 = num % 10;
            if (k1 != 0) {
                if (k1 >= 5) {
                    num = num + (10 - k1);
                } else {
                    num = num - k1;
                }
            }
        }
        return num;
    }
	
	/**
	 * 获取隔天的日期,格式:yyyy-MM-dd
	 * @param beforDate
	 * @return
	 */
	public static String getAfterDate(String beforDate){
		try{
			  String   strBefore = beforDate;   
			  java.util.Date   dBefore   =   new   java.util.Date();   
			  java.text.SimpleDateFormat   dateFormat   =   new   java.text.SimpleDateFormat("yyyy-MM-dd");
			  dBefore   =   dateFormat.parse(strBefore);   
			  java.util.Calendar   dd=Calendar.getInstance();   
			  dd.setTime(dBefore);
			  dd.add(Calendar.DATE,1);
			  String   AfterDate   =   dateFormat.format(dd.getTime());   
			  return AfterDate;
			}catch(Exception e){
				e.printStackTrace();
				return "";
			}
	}
  
   /**
	 * 文格式日期转换为中文格式.即:2006-02-12 --> 12FEB06
	 * @param dateStr
	 * @return
	 */
	public static String dateToEnglish ( String dateStr ){		
		if(dateStr.equals("yyyy-mm-dd")){
			return dateStr;
		}
	    String yy = dateStr.substring( 0, 4 );
	    String mm = dateStr.substring( 5, 7 );
	    String dd = dateStr.substring( 8, 10 ) ;	    
	    
	    yy = yy.substring( 2,4 );	    
		if (mm.equals("01")) {
			mm = "JAN";

		} else if (mm.equals("02")) {
			mm = "FEB";

		} else if (mm.equals("03")) {
			mm = "MAR";

		} else if (mm.equals("04")) {
			mm = "APR";

		} else if (mm.equals("05")) {
			mm = "MAY";

		} else if (mm.equals("06")) {
			mm = "JUN";

		} else if (mm.equals("07")) {
			mm = "JUL";

		} else if (mm.equals("08")) {
			mm = "AUG";

		} else if (mm.equals("09")) {
			mm = "SEP";

		} else if (mm.equals("10")) {
			mm = "OCT";

		} else if (mm.equals("11")) {
			mm = "NOV";

		} else if (mm.equals("12")) {
			mm = "DEC";

		}
		
		StringBuffer strBuf = new StringBuffer();
		strBuf.append( dd );
		strBuf.append( mm );
		strBuf.append( yy );
		
		return strBuf.toString();
	}
	
	/**
	 * 中文格式日期转换为英文格式.即:12FEB06 --> 2006-02-12
	 * @param dateStr
	 * @return
	 */
	public static String dateToChinese( String dateStr ){
		if(dateStr.equals("yyyy-mm-dd")){
			return dateStr;
		}
		String dd = "";
		String mm = "";
		String yy = "";
		
		String temp = "";
		dd = dateStr.substring(0, 2);
		temp = dateStr.substring(2, 5);
		if (temp.equals("")) {

		} else if (temp.equals("JAN")) {
			mm = "01";

		} else if (temp.equals("FEB")) {
			mm = "02";

		} else if (temp.equals("MAR")) {
			mm = "03";

		} else if (temp.equals("APR")) {
			mm = "04";

		} else if (temp.equals("MAY")) {
			mm = "05";

		} else if (temp.equals("JUN")) {
			mm = "06";

		} else if (temp.equals("JUL")) {
			mm = "07";

		} else if (temp.equals("AUG")) {
			mm = "08";

		} else if (temp.equals("SEP")) {
			mm = "09";

		} else if (temp.equals("OCT")) {
			mm = "10";

		} else if (temp.equals("NOV")) {
			mm = "11";

		} else if (temp.equals("DEC")) {
			mm = "12";

		}

		if (dateStr.length() == 7) {			
			temp = dateStr.substring(5, 7);
			yy = "20" + temp;
		} else {
			Calendar date = java.util.Calendar.getInstance();
			yy = String.valueOf(date.get(Calendar.YEAR));
		}
		
		StringBuffer strBuf = new StringBuffer();
		strBuf.append( yy );
		strBuf.append( "-" );
		strBuf.append( mm );
		strBuf.append( "-" );
		strBuf.append( dd );
		
		return strBuf.toString();
	}
   
	/**
	 * 解析PAT数据中的机场建设费和燃油附加费
	 * @param patData
	 * pat数据
	 * @return
	 * 返回机场建设费和燃油附加费
	 */
	public static String eterm_getPAT(String patData){
	    
	    String[] pat = null;
	    String[] cnyq = null;
	    String patResult = null;
	    try{
	        pat = patData.split("\n");
	        for(int i=0;i<pat.length;i++){
	            if(pat[i].indexOf("YQ") != -1 && pat[i].indexOf("FN") != -1){
	                
	                cnyq = pat[i].split("/");
	                for(int j=0;j<cnyq.length;j++){
	                    if(cnyq[j].indexOf("TCNY")!= -1 && cnyq[j].indexOf("YQ")!= -1){
	                      //机场建设费
	                        int t1 = cnyq[j].indexOf("TCNY")+4;
	                        int t2 = cnyq[j].indexOf(".");
	                        patResult = patResult+cnyq[j].substring(t1,t2).trim();
	                    }else if(cnyq[j].indexOf("TCNY")!= -1 && cnyq[j].indexOf("CN")!= -1){
	                      //燃油附加费
	                        int t1 = cnyq[j].indexOf("TCNY")+4;
	                        int t2 = cnyq[j].indexOf(".");
	                        patResult = cnyq[j].substring(t1,t2).trim()+",";
	                    }
	                }
	            }
	        }
	        
	    }catch(Exception e){
	        patResult = "00,00";
	        System.out.println("获取机场建设费和燃油费错误:"+e.getMessage());
	    }
	    
	    return patResult;
	}
	/**
	 * 解析获取数据中的PNR
	 * @param pnrData
	 * 封口完数据
	 * @return
	 * 返回解析完的一个PNR
	 */
	public static String eterm_getPNR(String pnrData){
	   
	    String PNR = null;
	    int charid = 0;
	    try {
           String[] data = pnrData.split("\n");
           for(int i=0;i<data.length;i++){
               if(data[i].length() >4){
                   if(data[i].indexOf("-") != -1){
                       charid = data[i].indexOf("-");
                       PNR = data[i].substring(0,charid-1);
                       break;
                   }else{
                   	PNR = data[i];
                   }
               }
           }
       } catch (RuntimeException e) {
           PNR = "00000";
           e.printStackTrace();
       }
	    return PNR;
	}
	
	/**
	 * 目的：根据Ｙ舱位的总价、舱位的折扣率得到一个舱位的真实价格
	 * @param thePrice:	该航程的Ｙ舱位的总价
	 * @param airwaysBerth:航空公司加舱位（ＭＦＣ）－＞代表厦门航空的Ｃ舱位
	 */
	public static int getBerthPrice( HttpSession session,int thePrice,String airwaysBerth){
		
		int realPrice = -1;
		
		//找出该舱位的折扣率,计算出该舱位的价格
		HashMap map = (HashMap)session.getAttribute("BerthRebateMap");	
		if( map != null ){
			String rebateValue = (String)map.get( airwaysBerth );
			if( rebateValue != null ){
				
				//这里取个位上的四舍五入值
				int offset = Integer.parseInt( rebateValue );																				
				double doublePrice = thePrice * offset;										
				doublePrice = doublePrice / 1000.0;										
				realPrice = new Double( Math.round( doublePrice) ).intValue() * 10;
				
				map = null;
				return realPrice;
			}
		}
		
		return realPrice;
	}
	
	/**
	 * 这里对数据的重组,可以删除掉数据里不想要的字符(或无效字符)
	 * 当前只删除了两个无效字符,ASCII码分别为:28,29
	 * @param data
	 * @return
	 */
	public static String regroupData( String data ){
		
		String cmdResult = data;
		char theChar1 = '';
		char theChar2 = '';
		char[] charArr1 = new char[1];
		char[] charArr2 = new char[1];
		charArr1[0] = theChar1;
		charArr2[0] = theChar2;					
		String theStr1 = new String( charArr1 );
		String theStr2 = new String( charArr2 );
		cmdResult = cmdResult.replaceAll( theStr1," ");
		cmdResult = cmdResult.replaceAll( theStr2," ");
		
		return cmdResult;
	}
	

    
    /**
     * @param tcode
     * @return
     */
    public static String getTicketTitle(String tcode){
       
        if(tcode.equals("CA")){
            
            return "999";
        }else if(tcode.equals("CA")){
            return "999";
        }else if(tcode.equals("MF")){
            return "731";
        }else if(tcode.equals("FM")){
            return "774";
        }else if(tcode.equals("MU")){
            return "781";
        }else if(tcode.equals("3U")){
            return "876";
        }else if(tcode.equals("CZ")){
            return "784";
        }else if(tcode.equals("ZH")){
            return "479";
        }else if(tcode.equals("SC")){
            return "324";
        }else if(tcode.equals("HU")){
            return "880";
        }else if(tcode.equals("SC")){
            return "324";
        }else if(tcode.equals("HU")){
            return "880";
        }
        
        return "999";
    }
    /**
     * 根据航空公司两字代码获取对应的英文名称
     * @param twoCode
     * @return
     */
    public static String getAirCompanyName(String twoCode){
        
        String acname = null;
        
        if(twoCode.equals("3U")){
            acname = "CHINA SICHUAN AIRLINE";
        }else if(twoCode.equals("MF")){
            acname = "XIAMEN AIRLINES";
        }else if(twoCode.equals("CA")){
            acname = "AIR CHINA";
        }else if(twoCode.equals("MU")){
            acname = "CHINA EASTERN AIRLINES";
        }else if(twoCode.equals("FM")){
            acname = "SHANGHAI AIRLINE";
        }else if(twoCode.equals("CZ")){
            acname = "CHINA SOUTHERN AIRLINES";
        }else {
            acname = " AIRLINES";
        }
        
        return acname;
    }
	/**
	 * 获取三字代码的中文名称
	 * @param threeCode
	 * @return
	 */
	public static String getThreedCodeName(String threeCode){
		
		String name = null;
		if(threeCode.equals("XMN")){
			name = "厦门";
		}else if(threeCode.equals("SHA")){
			name = "上海虹桥";
		}else if(threeCode.equals("CAN")){
			name = "广州";
		}else if(threeCode.equals("PEK")){
			name = "北京";
		}else if(threeCode.equals("BJS")){
			name = "北京";
		}else if(threeCode.equals("BAV")){
			name = "包头";
		}else if(threeCode.equals("BHY")){
			name = "北海";
		}else if(threeCode.equals("BFU")){
			name = "蚌埠";
		}else if(threeCode.equals("CGQ")){
			name = "长春";
		}else if(threeCode.equals("CGD")){
			name = "常德";
		}else if(threeCode.equals("CSX")){
			name = "长沙";
		}else if(threeCode.equals("CIH")){
			name = "长治";
		}else if(threeCode.equals("CZX")){
			name = "常州";
		}else if(threeCode.equals("CHG")){
			name = "朝阳";
		}else if(threeCode.equals("CTU")){
			name = "成都";
		}else if(threeCode.equals("CIF")){
			name = "赤峰";
		}else if(threeCode.equals("CKG")){
			name = "重庆";
		}else if(threeCode.equals("DAX")){
			name = "达县";
		}else if(threeCode.equals("DLC")){
			name = "大连";
		}else if(threeCode.equals("DLU")){
			name = "大理";
		}else if(threeCode.equals("DDG")){
			name = "丹东";
		}else if(threeCode.equals("DAT")){
			name = "大同";
		}else if(threeCode.equals("DOY")){
			name = "东营";
		}else if(threeCode.equals("DNH")){
			name = "敦煌";
		}else if(threeCode.equals("ENH")){
			name = "恩施";
		}else if(threeCode.equals("FUG")){
			name = "阜阳";
		}else if(threeCode.equals("FYN")){
			name = "富蕴";
		}else if(threeCode.equals("FOC")){
			name = "福州";
		}else if(threeCode.equals("KOW")){
			name = "赣州";
		}else if(threeCode.equals("GOQ")){
			name = "格尔木";
		}else if(threeCode.equals("GHN")){
			name = "广汉";
		}else if(threeCode.equals("CAN")){
			name = "广州";
		}else if(threeCode.equals("KWL")){
			name = "桂林";
		}else if(threeCode.equals("KWE")){
			name = "贵阳";
		}else if(threeCode.equals("HRB")){
			name = "哈尔滨";
		}else if(threeCode.equals("HAK")){
			name = "海口";
		}else if(threeCode.equals("HLD")){
			name = "海拉尔";
		}else if(threeCode.equals("HMI")){
			name = "哈密";
		}else if(threeCode.equals("HGH")){
			name = "杭州";
		}else if(threeCode.equals("HZG")){
			name = "汉中";
		}else if(threeCode.equals("HFE")){
			name = "合肥";
		}else if(threeCode.equals("HEK")){
			name = "黑河";
		}else if(threeCode.equals("HKG")){
			name = "香港";
		}else if(threeCode.equals("HNY")){
			name = "衡阳";
		}else if(threeCode.equals("HTN")){
			name = "和田";
		}else if(threeCode.equals("TXN")){
			name = "黄山";
		}else if(threeCode.equals("HYN")){
			name = "黄岩";
		}else if(threeCode.equals("HET")){
			name = "呼和浩特";
		}else if(threeCode.equals("KNC")){
			name = "吉安";
		}else if(threeCode.equals("JMU")){
			name = "佳木斯";
		}else if(threeCode.equals("JGN")){
			name = "嘉峪关";
		}else if(threeCode.equals("JIL")){
			name = "吉林";
		}else if(threeCode.equals("TNA")){
			name = "济南";
		}else if(threeCode.equals("JNG")){
			name = "济宁";
		}else if(threeCode.equals("JDZ")){
			name = "景德镇";
		}else if(threeCode.equals("JHG")){
			name = "景洪";
		}else if(threeCode.equals("JJN")){
			name = "晋江";
		}else if(threeCode.equals("JNZ")){
			name = "锦州";
		}else if(threeCode.equals("CHW")){
			name = "酒泉";
		}else if(threeCode.equals("JIU")){
			name = "九江";
		}else if(threeCode.equals("JZH")){
			name = "九寨黄龙";
		}else if(threeCode.equals("KRY")){
			name = "克拉玛依";
		}else if(threeCode.equals("KHG")){
			name = "喀什";
		}else if(threeCode.equals("KRL")){
			name = "库尔勒";
		}else if(threeCode.equals("KMG")){
			name = "昆明";
		}else if(threeCode.equals("KCA")){
			name = "库车";
		}else if(threeCode.equals("LHW")){
			name = "兰州";
		}else if(threeCode.equals("LXA")){
			name = "拉萨";
		}else if(threeCode.equals("LYG")){
			name = "连云港";
		}else if(threeCode.equals("LJG")){
			name = "丽江";
		}else if(threeCode.equals("LYI")){
			name = "临沂";
		}else if(threeCode.equals("LZH")){
			name = "柳州";
		}else if(threeCode.equals("LYA")){
			name = "洛阳";
		}else if(threeCode.equals("LZO")){
			name = "泸州";
		}else if(threeCode.equals("MFM")){
			name = "澳门";
		}else if(threeCode.equals("LUM")){
			name = "芒市";
		}else if(threeCode.equals("MXZ")){
			name = "梅县";
		}else if(threeCode.equals("MIG")){
			name = "绵阳";
		}else if(threeCode.equals("MDG")){
			name = "牡丹江";
		}else if(threeCode.equals("KHN")){
			name = "南昌";
		}else if(threeCode.equals("NAO")){
			name = "南充";
		}else if(threeCode.equals("NKG")){
			name = "南京";
		}else if(threeCode.equals("NNG")){
			name = "南宁";
		}else if(threeCode.equals("NTG")){
			name = "南通";
		}else if(threeCode.equals("NNY")){
			name = "南阳";
		}else if(threeCode.equals("NGB")){
			name = "宁波";
		}else if(threeCode.equals("IQM")){
			name = "且末";
		}else if(threeCode.equals("TAO")){
			name = "青岛";
		}else if(threeCode.equals("IQN")){
			name = "庆阳";
		}else if(threeCode.equals("SHP")){
			name = "秦皇岛";
		}else if(threeCode.equals("NDG")){
			name = "齐齐哈尔";
		}else if(threeCode.equals("JJN")){
			name = "泉州";
		}else if(threeCode.equals("JUZ")){
			name = "衢州";
		}else if(threeCode.equals("SYX")){
			name = "三亚";
		}else if(threeCode.equals("SHA")){
			name = "上海虹桥";
		}else if(threeCode.equals("PVG")){
			name = "上海浦东";
		}else if(threeCode.equals("SWA")){
			name = "汕头";
		}else if(threeCode.equals("SHS")){
			name = "沙市";
		}else if(threeCode.equals("SZX")){
			name = "深圳";
		}else if(threeCode.equals("SHE")){
			name = "沈阳";
		}else if(threeCode.equals("SJW")){
			name = "石家庄";
		}else if(threeCode.equals("SYM")){
			name = "思茅";
		}else if(threeCode.equals("SZV")){
			name = "苏州";
		}else if(threeCode.equals("TCG")){
			name = "塔城";
		}else if(threeCode.equals("TYN")){
			name = "太原";
		}else if(threeCode.equals("TSN")){
			name = "天津";
		}else if(threeCode.equals("TNH")){
			name = "通化";
		}else if(threeCode.equals("TGO")){
			name = "通辽";
		}else if(threeCode.equals("TEN")){
			name = "铜仁";
		}else if(threeCode.equals("WXN")){
			name = "万县";
		}else if(threeCode.equals("WEF")){
			name = "潍坊";
		}else if(threeCode.equals("WEH")){
			name = "威海";
		}else if(threeCode.equals("WNZ")){
			name = "温州";
		}else if(threeCode.equals("WUH")){
			name = "武汉天河";
		}else if(threeCode.equals("WJD")){
			name = "武汉王家墩";
		}else if(threeCode.equals("HLH")){
			name = "乌兰浩特";
		}else if(threeCode.equals("URC")){
			name = "乌鲁木齐";
		}else if(threeCode.equals("WUS")){
			name = "武夷山";
		}else if(threeCode.equals("WUX")){
			name = "无锡";
		}else if(threeCode.equals("WUZ")){
			name = "梧州";
		}else if(threeCode.equals("XIY")){
			name = "西安";
		}else if(threeCode.equals("XFN")){
			name = "襄樊";
		}else if(threeCode.equals("XIC")){
			name = "西昌";
		}else if(threeCode.equals("XIL")){
			name = "锡林浩特";
		}else if(threeCode.equals("XNN")){
			name = "西宁";
		}else if(threeCode.equals("XUZ")){
			name = "徐州";
		}else if(threeCode.equals("ENY")){
			name = "延安";
		}else if(threeCode.equals("YNJ")){
			name = "延吉";
		}else if(threeCode.equals("YNT")){
			name = "烟台";
		}else if(threeCode.equals("YNZ")){
			name = "盐城";
		}else if(threeCode.equals("YBP")){
			name = "宜宾";
		}else if(threeCode.equals("YIH")){
			name = "宜昌";
		}else if(threeCode.equals("INC")){
			name = "银川";
		}else if(threeCode.equals("YIN")){
			name = "伊宁";
		}else if(threeCode.equals("YIW")){
			name = "义乌";
		}else if(threeCode.equals("UYN")){
			name = "榆林";
		}else if(threeCode.equals("ZAT")){
			name = "昭通";
		}else if(threeCode.equals("DYG")){
			name = "张家界";
		}else if(threeCode.equals("ZHA")){
			name = "湛江";
		}else if(threeCode.equals("DIG")){
			name = "中甸";
		}else if(threeCode.equals("CGO")){
			name = "郑州";
		}else if(threeCode.equals("HSN")){
			name = "舟山";
		}else if(threeCode.equals("ZUH")){
			name = "珠海";
		}else if(threeCode.equals("ZYI")){
			name = "遵义";
		}else if(threeCode.equals("BSD")){
			name = "保山";
		}else if(threeCode.equals("AOG")){
			name = "鞍山";
		}else if(threeCode.equals("AQG")){
			name = "安庆";
		}else if(threeCode.equals("AKA")){
			name = "安康";
		}else if(threeCode.equals("AAT")){
			name = "阿勒泰";
		}else if(threeCode.equals("AKU")){
			name = "阿克苏";
		}
		
		return name;
	}
	
	public static String getAircoamChinaName(String en){
		String cname = "";
		if(en.equals("3U")){
			cname = "川航";
		}else if(en.equals("CA")){
			cname = "国航";
		}else if(en.equals("CZ")){
			cname = "南航";
		}else if(en.equals("FM")){
			cname = "上航";
		}else if(en.equals("HO")){
			cname = "吉祥";
		}else if(en.equals("HU")){
			cname = "海航";
		}else if(en.equals("MF")){
			cname = "厦航";
		}else if(en.equals("MU")){
			cname = "东航";
		}else if(en.equals("SC")){
			cname = "山航";
		}else if(en.equals("ZH")){
			cname = "深航";
		}else if(en.equals("EU")){
			cname = "鹰联";
		}else if(en.equals("PN")){
			cname = "西部";
		}else if(en.equals("OQ")){
			cname = "重航";
		}else if(en.equals("GS")){
			cname = "大航";
		}else if(en.equals("8C")){
			cname = "东星";
		}else {
			cname = en;
		}
		return cname;
	}
	
	public static String getLogTime(){
		Date d = new Date();
		long l = d.getTime();
		return String.valueOf(l);
	}
	
	/**
	 * <p>发送简讯
	 *
	 */
	public static void sendMobilInfo(String telnum ,String msg){
        try {
            String strURL = "http://sms.asp.sh.cn/sms.asp?cUser=test6621&cPwd=3033&mobile="+telnum+"&content="+msg;
            System.out.println(strURL);
            int iHttpResult;
            URL m_URL = new URL(strURL);
            System.out.println(m_URL.getProtocol());
            URLConnection m_URLConn = m_URL.openConnection();
            m_URLConn.connect();
            HttpURLConnection m_HttpConn = (HttpURLConnection) m_URLConn;
            iHttpResult = m_HttpConn.getResponseCode();
            System.out.println("HttpResult:" + iHttpResult);
            if (iHttpResult != HttpURLConnection.HTTP_OK){
                
                System.out.println("无法连接...");
            }else {
                
                InputStreamReader m_Reader = new InputStreamReader(m_URLConn.getInputStream());
                char[] Buffer = new char[2048];
                int iNum = 0;
                while (iNum > -1) {
                    iNum = m_Reader.read(Buffer);
                    if (iNum < 0){
                        break;
                    }
                }
                m_Reader.close();
                
                System.out.println(new String(Buffer));
            }
            
        } catch (Exception e) {
            System.out.println("连接失败...");
        }
	}
	
	/**
	 * <p>发送简讯
	 *
	 */
	public static void sendMobilInfo_b(String telnum ,String msg){

            String strURL = "http://sms.asp.sh.cn/sms.asp?cUser=test6621&cPwd=3033&mobile="+telnum+"&content="+msg;
            System.out.println("开始发送指令");
        	Date date_1 = new Date();
        	String rs = "";
//        	xml = "request="+xml;
        	try {
                // Construct data
                String data = "cUser=test6621&cPwd=3033&mobile="+telnum+"&content="+msg;  
                data = new String(data.getBytes("gb2312"),"iso8859_1");
                // Create a socket to the host
                String hostname = "sms.asp.sh.cn";
                int port = 80;
                System.out.println("builder connection...");
                InetAddress addr = InetAddress.getByName(hostname);
                Socket socket = new Socket(addr, port);
            
                // Send header
                System.out.println("create writebuffer..");
                String path = "/sms.asp?" + data;
                BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "GB2312"));
                System.out.println("write.........");
                wr.write("GET "+path+" HTTP/1.1\r\n");
                wr.write("Host: " + hostname + "\r\n");
                wr.write("User-Agent: Java/1.5.0_04"+"\r\n");
                wr.write("Accept: text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2\r\n");
                wr.write("Connection: keep-alive\r\n");
               // wr.write("Content-Length: "+data.length() +"\r\n");
                System.out.println("内容长度:" + msg.length());
                wr.write("Content-Type: text/html\r\n");
                wr.write("\r\n");
            
                // Send data
                wr.write(data);
                wr.flush();
                System.out.println("write over");
                // Get response
                System.out.println("read....");
                BufferedReader rd = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line;
                while ((line = rd.readLine()) != null) {
                	System.out.println("read:" + line);
                    rs = rs + line;
                }
                System.out.println("read over");
                wr.close();
                rd.close();
                rs = rs.substring(rs.indexOf('<'),rs.length());
                
                
            } catch (Exception e) {
            	e.printStackTrace();
            }
	}
	
	public static void main(String[] args){
		// TPUtil.sendMobilInfo_b("13906032889", "您已经成功预订了;我们将在24小时之内跟您确认定单信息,详情可登陆www.766la.com查询定单!");
		System.out.println("imya"+vRandom(1,999));
		System.out.println("imya"+getLogTime());
	}
	
}

