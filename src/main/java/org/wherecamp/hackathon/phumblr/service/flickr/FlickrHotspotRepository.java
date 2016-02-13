package org.wherecamp.hackathon.phumblr.service.flickr;

import org.wherecamp.hackathon.phumblr.service.FlickrPojo;
import org.wherecamp.hackathon.phumblr.service.HotspotPojo;
import org.wherecamp.hackathon.phumblr.service.WikiPojo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by danielt on 27.11.15.
 */
public class FlickrHotspotRepository {

  public HotspotPojo getHeatmap(double lat, double lon) throws SQLException {
    HotspotPojo hotspot = new HotspotPojo();
    FlickrDatabaseDatasource ds = new FlickrDatabaseDatasource();

    Integer hotspotId = ds.loadHotspotGid(lat, lon);
    if(hotspotId==null){
      //no hotspot -> return empty hotspot
      return hotspot;
    }

    hotspot.hotspotId = hotspotId;
    hotspot.flickr = loadPhotoUrls(ds, hotspotId);
    hotspot.wiki = loadWikis(hotspotId);

    return hotspot;
  }

  private List<WikiPojo> loadWikis(Integer gid) throws SQLException {
    List<WikiPojo> wikis = new ArrayList<>();
    int amount = 5;
    int maxDistance = 500;
    WikiDatabaseDatasource wikiSource = new WikiDatabaseDatasource();
    List<WikiPojo> wikisInside = wikiSource.loadWikisInsideHotspot(gid, amount);
    wikis.addAll(wikisInside);
    if(wikisInside.size()<amount) {
      List<WikiPojo> wikisAround = wikiSource.loadWikisAroundHotspot(gid, maxDistance, amount - wikisInside.size());
      wikis.addAll(wikisAround);
    }



    return wikis;
  }

  private List<FlickrPojo> loadPhotoUrls(FlickrDatabaseDatasource ds, Integer gid) throws SQLException {
    int maxSize = 320;
    List<FlickrPojo> photos = ds.loadRelevantPhotos(gid, 15);
    for(FlickrPojo photo : photos){
      photo.url = new FlickrApiDatasource().getPhotoUrl(photo.photoId, maxSize);
    }
    return photos;
  }


}
