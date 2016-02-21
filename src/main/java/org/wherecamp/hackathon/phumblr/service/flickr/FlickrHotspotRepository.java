package org.wherecamp.hackathon.phumblr.service.flickr;

import org.wherecamp.hackathon.phumblr.service.FlickrConfig;
import org.wherecamp.hackathon.phumblr.service.FlickrPojo;
import org.wherecamp.hackathon.phumblr.service.HotspotPojo;
import org.wherecamp.hackathon.phumblr.service.WikiPojo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import javax.sql.DataSource;

/**
 * Created by danielt on 27.11.15.
 */
public class FlickrHotspotRepository {

  Logger LOGGER = Logger.getLogger(FlickrHotspotRepository.class);

  FlickrConfig flickrConfig;

  FlickrApiDatasource flickrData;

  private DataSource dataSource;
  public FlickrHotspotRepository(DataSource dataSource, FlickrConfig flickrConfig){
    this.dataSource = dataSource;
    this.flickrConfig = flickrConfig;
  }


	
  public HotspotPojo getHeatmap(double lat, double lon, boolean loadURLs, boolean loadWiki) throws SQLException {
    HotspotPojo hotspot = new HotspotPojo();
    FlickrDatabaseDatasource ds = new FlickrDatabaseDatasource(dataSource);

    Integer hotspotId = ds.loadHotspotGid(lat, lon);
    if(hotspotId==null){
      //no hotspot -> return empty hotspot
      return hotspot;
    }

    hotspot.hotspotId = hotspotId;
    if (loadURLs) {
      hotspot.flickr = loadPhotoUrls(ds, hotspotId);
    }else{
      hotspot.flickr = loadPhotoIds(ds, hotspotId);
    }
    if (loadWiki) {
      hotspot.wiki = loadWikis(hotspotId);
    }
    LOGGER.info("loaded wikis");
    return hotspot;
  }

  private List<WikiPojo> loadWikis(Integer gid) throws SQLException {
    List<WikiPojo> wikis = new ArrayList<>();
    int amount = 5;
    int maxDistance = 500;
    WikiDatabaseDatasource wikiSource = new WikiDatabaseDatasource(dataSource);
    List<WikiPojo> wikisInside = wikiSource.loadWikisInsideHotspot(gid, amount);
    wikis.addAll(wikisInside);
    if(wikisInside.size()<amount) {
      List<WikiPojo> wikisAround = wikiSource.loadWikisAroundHotspot(gid, maxDistance, amount - wikisInside.size());
      wikis.addAll(wikisAround);
    }



    return wikis;
  }



  private List<FlickrPojo> loadPhotoIds(FlickrDatabaseDatasource ds, Integer gid) throws SQLException {
    int maxSize = 320;

    List<FlickrPojo> photos = ds.loadRelevantPhotos(gid, 3);

    LOGGER.info("loadedPhotoUrls");
    return photos;
  }

  private List<FlickrPojo> loadPhotoUrls(FlickrDatabaseDatasource ds, Integer gid) throws SQLException {
    int maxSize = 320;

    List<FlickrPojo> photos = ds.loadRelevantPhotos(gid, 3);

    if (flickrData == null){
      LOGGER.info("start flickr init");
      flickrData = new FlickrApiDatasource(flickrConfig);
      LOGGER.info("init FlickrApiDatasource");
    }

    for(FlickrPojo photo : photos){
      photo.url = flickrData.getPhotoUrl(photo.photoId, maxSize);
      LOGGER.info("got photo url");
    }
    LOGGER.info("loadedPhotoUrls");
    return photos;
  }


}
