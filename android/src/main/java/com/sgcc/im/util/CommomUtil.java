package com.sgcc.im.util;

/**
 * Created on 2019/8/2.
 *
 * @author wr
 * @version 1.0.0
 * @Description
 */

public class CommomUtil {


    public static String xmlTitle = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
    public static String setUpXml(String connect,String connectPort,String lvs,String lvsport,String file,String fileport){
        StringBuilder sb=new StringBuilder();
        sb.append(xmlTitle);
        sb.append("<ServerAddr version=\"2\">");
        sb.append("<Connector>");
        sb.append("<server>");
        sb.append("<host>");
        sb.append(connect);
        sb.append("</host>");
        sb.append("<port>");
        sb.append(connectPort);
        sb.append("</port>");
        sb.append("</server>");
        sb.append("</Connector>");

        sb.append("<LVS>");
        sb.append("<server>");
        sb.append("<host>");
        sb.append(lvs);
        sb.append("</host>");
        sb.append("<port>");
        sb.append(lvsport);
        sb.append("</port>");
        sb.append("</server>");
        sb.append("</LVS>");



        sb.append("<FileServer>");
        sb.append("<server>");
        sb.append("<host>");
        sb.append(file);
        sb.append("</host>");
        sb.append("<port>");
        sb.append(fileport);
        sb.append("</port>");
        sb.append("</server>");
        sb.append("</FileServer>");

        sb.append("</ServerAddr>");

        return sb.toString();
    }

}
