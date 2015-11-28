package org.wherecamp.hackathon.phumblr.harvest;

import javafx.util.Pair;
import org.apache.log4j.Logger;
import org.wherecamp.hackathon.phumblr.harvest.wikipedia.WikiHarvestPojo;
import org.wherecamp.hackathon.phumblr.harvest.wikipedia.WikiSpatialHarvester;
import org.wherecamp.hackathon.phumblr.harvest.wikipedia.WikiTextHarvester;

import java.util.ArrayList;
import java.util.List;

import static org.wherecamp.hackathon.phumblr.harvest.Settings.*;

/**
 * Created by danielt on 26.11.15.
 */
public class HarvestWikipediaTask {

  private final static Logger LOGGER = Logger.getLogger(HarvestWikipediaTask.class);

  private final String TARGET_FILE_NAME = "de_wiki.csv";

  private List<Pair<String, String>> queryGrid;
  private int wikiCounts;
  private long startTime;
  int downloadCounter;

  private CsvWriter writer;

  public void harvest() throws Exception {
    queryGrid = new ArrayList<Pair<String, String>>();
    wikiCounts = 0;
    downloadCounter = 0;
    writer = new CsvWriter(TARGET_FILE_NAME);
    writer.initWiki();

    double queryWindowSize = getQueryWindowSize();

    startTime = System.currentTimeMillis();
    WikiSpatialHarvester spatialHarvester = new WikiSpatialHarvester();
    WikiTextHarvester textHarvester = new WikiTextHarvester();
    for(int col = 0; BERLIN_CITY_LEFT + queryWindowSize * ((double)col) <= BERLIN_CITY_RIGHT; col++) {
      for (int row = 0; BERLIN_CITY_BOTTOM + queryWindowSize * ((double) row) <= BERLIN_CITY_UP; row++) {
        downloadCounter++;
        String[] bbox = {
            Double.toString(BERLIN_CITY_LEFT + ((double) col * queryWindowSize)),
            Double.toString(BERLIN_CITY_BOTTOM + ((double) row * queryWindowSize)),
            Double.toString(BERLIN_CITY_LEFT + ((double) (col + 1) * queryWindowSize)),
            Double.toString(BERLIN_CITY_BOTTOM + ((double) (row + 1) * queryWindowSize))
        };
        List<WikiHarvestPojo> harvestedWikis = spatialHarvester.queryBbox(queryGrid, bbox, col, row);
        harvestedWikis = textHarvester.queryText(harvestedWikis);
        writeWikis(harvestedWikis);
      }
    }
    writer.close();
    LOGGER.info("downloaded " + wikiCounts+ " wikis in " + ((double) queryGrid.size() / 2.0) + " bboxes");
    LOGGER.info("time taken: "+(System.currentTimeMillis() - startTime)+" ms");
    StringBuffer sb = new StringBuffer();
    for(Pair<String, String> point : queryGrid){
      sb.append(point.getKey()).append(";").append(point.getValue()).append("\n");
    }
    LOGGER.info("\n" + sb.toString());
  }

  private void writeWikis(List<WikiHarvestPojo> harvestedWikis) {
    for (WikiHarvestPojo harvestedWiki : harvestedWikis){
      writer.addWiki(harvestedWiki);
    }
  }


  public static void main(String[] args) throws Exception {
    HarvestWikipediaTask harvester = new HarvestWikipediaTask();
    harvester.harvest();
  }


  public double getQueryWindowSize() {
    // reference: https://tools.wmflabs.org/wp-world/marks.php?LANG=de&coats=0&thumbs=0&bbox=13.376309490565,52.516919307772,13.382062828903,52.51968775301
    double left = 13.376309490565;
    double top = 52.51968775301;
    double right = 13.382062828903;
    double bottom = 52.516919307772;
    double width = right - left;
    double height = top - bottom;
    return (width > height)? height : width;
  }
}
