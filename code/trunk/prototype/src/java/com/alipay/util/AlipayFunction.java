package com.alipay.util;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.alipay.config.AlipayConfig;

public class AlipayFunction {

	public static String BuildMysign(Map sArray, String key) {
		String prestr = CreateLinkString(sArray);
		prestr = prestr + key;
		String mysign = Md5Encrypt.md5(prestr);
		return mysign;
	}

	public static Map ParaFilter(Map sArray){
		List keys = new ArrayList(sArray.keySet());
		Map sArrayNew = new HashMap();
		
		for(int i = 0; i < keys.size(); i++){
			String key = (String) keys.get(i);
			String value = (String) sArray.get(key);
			
			if(value.equals("") || value == null || 
					key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("sign_type")){
				continue;
			}
			
			sArrayNew.put(key, value);
		}
		
		return sArrayNew;
	}

	public static String CreateLinkString(Map params){
		List keys = new ArrayList(params.keySet());
		Collections.sort(keys);

		StringBuilder prestr = new StringBuilder();

		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = (String) params.get(key);

			if (i == keys.size() - 1) {
				prestr.append(key + "=" + value);
			} else {
				prestr.append(key + "=" + value + "&");
			}
		}

		return prestr.toString();
	}

	public static String CreateEncodedLinkString(Map params){
		List keys = new ArrayList(params.keySet());
		Collections.sort(keys);

		StringBuilder prestr = new StringBuilder();
		try {
			for (int i = 0; i < keys.size(); i++) {
				String key = (String) keys.get(i);
				String value = (String) params.get(key);

				if (i == keys.size() - 1) {

					prestr.append(key + "=" + URLEncoder.encode(value, "utf-8"));

				} else {
					prestr.append(key + "=" + URLEncoder.encode(value, "utf-8") + "&");
				}
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prestr.toString();
	}

	public static void LogResult(String sWord){
		try {
			FileWriter writer = new FileWriter("D:\\alipay_log" + System.currentTimeMillis() + ".txt");
			writer.write(sWord);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String query_timestamp(String partner)
			throws MalformedURLException, DocumentException, IOException {
		String strUrl = "https://mapi.alipay.com/gateway.do?service=query_timestamp&partner="+partner;
		StringBuffer buf1 = new StringBuffer();
		SAXReader reader = new SAXReader();
		Document doc = reader.read(new URL(strUrl).openStream());
		
		List<Node> nodeList = doc.selectNodes("//alipay/*");
		
		for (Node node : nodeList) {
			if (node.getName().equals("is_success")
					&& node.getText().equals("T")) {
				List<Node> nodeList1 = doc.selectNodes("//response/timestamp/*");
				for (Node node1 : nodeList1) {
					buf1.append(node1.getText());
				}
			}
		}
		
		return buf1.toString();
	}
}
