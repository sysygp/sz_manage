package com.zhrt.util;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XmlUtil {
	
	@SuppressWarnings("unchecked")
	public static TreeMap xml2Map(String xmlstr) {
		TreeMap itemValue = new TreeMap();
		Element elementObj;		
		try{
			Document document = DocumentHelper.parseText(xmlstr);
			if (document != null) {
				Element elementRoot = (Element) document.getRootElement();
				Element message = elementRoot.element("message");
				Iterator attrItr = message.attributeIterator();
				while(attrItr.hasNext()){
					Attribute attr = (Attribute)attrItr.next();
					itemValue.put(attr.getName(),attr.getData());
				}
				
				
			}
		}catch(Exception e){
			return null;
		}			
		return itemValue;			
	}
	
	public static void main(String[] args) {
		String reqContentOri = "<?xml version=\"1.0\" encoding=\"utf-8\"?><messages><message mobile=\"106902280827\" content=\"111\" dateTime=\"2015-09-16 18:48:41\"/><hret>0</hret><a1><a11 name=\"a11\">a11</a11></a1></messages>";
		Map map = XmlUtil.xml2Map(reqContentOri);
		System.out.println(map.get("mobile"));
	}
}
