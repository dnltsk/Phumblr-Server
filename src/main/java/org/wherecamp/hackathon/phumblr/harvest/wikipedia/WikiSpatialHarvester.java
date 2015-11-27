package org.wherecamp.hackathon.phumblr.harvest.wikipedia;

import javafx.util.Pair;
import org.apache.log4j.Logger;
import org.wherecamp.hackathon.phumblr.harvest.UrlContentReader;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by danielt on 27.11.15.
 */
public class WikiSpatialHarvester {

  Logger LOGGER = Logger.getLogger(WikiSpatialHarvester.class);

  public List<WikiHarvestPojo> queryBbox(List<Pair<String, String>> queryGrid, String[] bbox, int col, int row) throws Exception {
    queryGrid.add(new Pair<String, String>(bbox[0], bbox[1]));
    queryGrid.add(new Pair<String, String>(bbox[2], bbox[3]));
    URL url = new  URL("https://tools.wmflabs.org/wp-world/marks.php?LANG=en&coats=0&thumbs=0" +
        "&bbox="+bbox[0]+","+bbox[1]+","+bbox[2]+","+bbox[3]);
    //executeWithGeotools(bbox, col, row, url);
    List<WikiHarvestPojo> harvestedWikis = executeWithText(url);
    return harvestedWikis;
  }

   public List<WikiHarvestPojo> executeWithText(URL url) throws IOException {
     List<WikiHarvestPojo> harvestedWikis = new ArrayList<>();
     String kmlText = UrlContentReader.urlContentAsString(url);
     return executeWithText(kmlText);
  }

  public List<WikiHarvestPojo> executeWithText(String kmlText) throws UnsupportedEncodingException {
    List<WikiHarvestPojo> harvestedWikis = new ArrayList<>();
    if (kmlText != null & kmlText.length() > 0) {
     // Change case of relevant tags to match our search string case
     kmlText = kmlText.replaceAll("(?i)<Placemark>", "<Placemark>")
         .replaceAll("(?i)</Placemark>", "</Placemark>")
         .replaceAll("(?i)<name>", "<name>")
         .replaceAll("(?i)</name>", "</name>")
         .replaceAll("(?i)<coordinates>", "<coordinates>")
         .replaceAll("(?i)</coordinates>", "</coordinates>")
         .replaceAll("(?i)<styleurl>", "<styleurl>")
         .replaceAll("(?i)</styleurl>", "</styleurl>");
      // Get <Placemark> tag
     String[] kmlPlacemarks = kmlText.split("</Placemark>");
     if (kmlPlacemarks.length > 0) {
       for (Integer i = 0; i < kmlPlacemarks.length; i++) {
         // Add '</Placemark>' to the end - actually not necessary
         kmlPlacemarks[i] += "</Placemark>";
         if (kmlPlacemarks[i].indexOf("<Placemark>") > -1)
               /* Trim front to start from '<Placemark>'
               Otherwise additional tags may be in between leading
               to parsing of incorrect values especially Name */
           kmlPlacemarks[i] = kmlPlacemarks[i].substring(kmlPlacemarks[i].indexOf("<Placemark>"));
       }
       String tmpPlacemarkName;
       String tmpPlacemarkCoordinates;
       String tmpPlacemarkStyle;
       for (String kmlPlacemark: kmlPlacemarks)
         if ((kmlPlacemark.indexOf("<name>") > -1 && kmlPlacemark.indexOf("</name>") > -1) &&
             (kmlPlacemark.indexOf("<coordinates>") > -1 && kmlPlacemark.indexOf("</coordinates>") > -1)) {
           WikiHarvestPojo harvestedWiki = new WikiHarvestPojo();
           tmpPlacemarkCoordinates = kmlPlacemark.substring(kmlPlacemark.indexOf("<coordinates>") + 13, kmlPlacemark.indexOf("</coordinates>"));
           harvestedWiki.lon = Double.parseDouble(tmpPlacemarkCoordinates.split(",")[0]);
           harvestedWiki.lat = Double.parseDouble(tmpPlacemarkCoordinates.split(",")[1]);
           tmpPlacemarkName = kmlPlacemark.substring(kmlPlacemark.indexOf("<name>") + 6, kmlPlacemark.indexOf("</name>"));
           harvestedWiki.humanReadableTitle = tmpPlacemarkName;
           harvestedWiki.uniqueTitle = URLEncoder.encode(tmpPlacemarkName, "UTF-8");
           tmpPlacemarkStyle = kmlPlacemark.substring(kmlPlacemark.indexOf("<styleurl>") + 10, kmlPlacemark.indexOf("</styleurl>"));
           harvestedWiki.type = tmpPlacemarkStyle.replace("#","");
           harvestedWikis.add(harvestedWiki);
         }
     }
   }
    return harvestedWikis;
  }



}
