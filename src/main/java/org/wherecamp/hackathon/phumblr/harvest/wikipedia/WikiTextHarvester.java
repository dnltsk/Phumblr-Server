package org.wherecamp.hackathon.phumblr.harvest.wikipedia;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.wherecamp.hackathon.phumblr.harvest.UrlContentReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by danielt on 27.11.15.
 */
public class WikiTextHarvester {

  String wikiApi = "https://en.wikipedia.org/w/api.php?action=query&prop=extracts|images&format=json&explaintext=&titles=";

  private final String TAG_QUERY = "query";
  private final String TAG_PAGES = "pages";
  private final String TAG_TITLE = "title";
  private final String TAG_EXTRACT = "extract";

  public List<WikiHarvestPojo> queryText(List<WikiHarvestPojo> harvestedWikis) throws MalformedURLException, ParseException {
    for (WikiHarvestPojo harvestedWiki : harvestedWikis){
      enrichHarvestedWiki(harvestedWiki);
    }
    return harvestedWikis;
  }

  private void enrichHarvestedWiki(WikiHarvestPojo harvestedWiki) throws MalformedURLException, ParseException {
    URL wikiApiRequest = new URL(wikiApi+harvestedWiki.uniqueTitle);
    String wikiApiResponse = UrlContentReader.urlContentAsString(wikiApiRequest);
    JSONObject json = (JSONObject)new JSONParser().parse(wikiApiResponse);
    JSONObject query = (JSONObject)json.get(TAG_QUERY);
    JSONObject pages = (JSONObject)query.get(TAG_PAGES);
    JSONObject pageid = (JSONObject)pages.get(pages.keySet().iterator().next());
    harvestedWiki.sections = (String)pageid.get(TAG_EXTRACT);
  }
}
