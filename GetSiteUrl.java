package com.html;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetSiteUrl {

	public static void main(String[] args) {
		
		final String DOMAIN = "https://www.google.com/";  
		final String DOM_SELECTOR = "li.g > h3 > a"; 
		String searchQuery = "search?q=";
		String countQuery = "&num=";
		String keyword = "india is my country";
		int resultCount = 5;
		keyword.replace(" ", "+");
		
		String searchUrl = DOMAIN+searchQuery+keyword+countQuery+resultCount;
		
		GetSiteUrl siteUrl = new GetSiteUrl();
		
		System.out.print(siteUrl.getUrlList(siteUrl.crawlSearchList(siteUrl.crawlWebpage(searchUrl), DOM_SELECTOR) ));
		
	}
	
	public Document crawlWebpage(String searchUrl){
		try {
			return Jsoup.connect(searchUrl)
					.userAgent("Mozilla/5.0")
					.timeout(5000).get();						
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return null;	
	}
	
	public Elements crawlSearchList(Document webpage, String selector){
		return webpage.select(selector);
	}
	
	public List<Map<String, String>> getUrlList(Elements getCrawledUrls){
		List<Map<String, String>> urlObject = new ArrayList<Map<String, String>>();
		Map<String,String> urldetails;
		Integer index = 0;
		for(Element crawledUrl:getCrawledUrls){
			urldetails = new LinkedHashMap<String,String>();
			urldetails.put("id",(++index).toString());
			urldetails.put("title", crawledUrl.text());
			urldetails.put("url", fetchURL(crawledUrl.attr("href")));
			urlObject.add(urldetails);
		}
		return urlObject;
	}
	
	public String fetchURL(String link){
		String sanitizedURL = null;
		if(link.startsWith("/url?q=")){
			sanitizedURL = link.replace("/url?q=","");
			if(sanitizedURL.contains("&")){
				int index = sanitizedURL.indexOf("&");
				sanitizedURL = sanitizedURL.substring(0, index);
			}			
		}
		return sanitizedURL;
	}
}