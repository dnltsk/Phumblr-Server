package org.wherecamp.hackathon.phumblr.harvest;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by danielt on 27.11.15.
 *
 * Source: http://stackoverflow.com/questions/15636303/extract-coordinates-from-kml-file-in-java
 *
 */
public class UrlContentReader {

  public static String urlContentAsString(URL url){
    try {
      URLConnection urlConnection = url.openConnection();
      InputStream is = urlConnection.getInputStream();
      InputStreamReader isr = new InputStreamReader(is);

      int numCharsRead;
      char[] charArray = new char[1024];
      StringBuffer sb = new StringBuffer();
      while ((numCharsRead = isr.read(charArray)) > 0) {
        sb.append(charArray, 0, numCharsRead);
      }
      String result = sb.toString();
      return result;
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "";
  }

}
