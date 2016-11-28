package com.test;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class MetaDataServiceTest {

	public static void main(String[] args) throws IOException {
		
		/*Document doc = Jsoup.parse("https://media.licdn.com/mpr/mpr/shrinknp_200_200/p/4/005/081/001/09d7ae1.jpg");  
        
		Elements elements=doc.select("img[src]");
		int length=elements.size();
		
		for(int i=0;i<length;i++){
			 String height=elements.get(i).attr("height");
			 System.out.println(height);
		}*/

		URL url = new URL("https://media.licdn.com/mpr/mpr/shrinknp_200_200/p/4/005/081/001/09d7ae1.jpg");
		BufferedImage image = ImageIO.read(url);
		int height = image.getHeight();
		int width = image.getWidth();
		System.out.println("Height : "+ height);
		System.out.println("Width : "+ width);		
  	}

}
