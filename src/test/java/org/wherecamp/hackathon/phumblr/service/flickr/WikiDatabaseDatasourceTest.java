package org.wherecamp.hackathon.phumblr.service.flickr;

import org.junit.Test;
import org.wherecamp.hackathon.phumblr.service.WikiPojo;

import java.sql.SQLException;
import java.util.List;

import static junit.framework.Assert.*;

/**
 * Created by danielt on 27.11.15.
 */
public class WikiDatabaseDatasourceTest {

  @Test
  public void wikis_inside_hotspot_are_found() throws SQLException {
    //given
    double latBrbTor = 52.51612;
    double lonBrbTor = 13.37899;
    int amount = 5;

    //when
    int gid = new FlickrDatabaseDatasource().loadHotspotGid(latBrbTor, lonBrbTor);
    List<WikiPojo> wikis = new WikiDatabaseDatasource().loadWikisInsideHotspot(gid, amount);

    //then
    assertNotNull(wikis);
    assertEquals(wikis.size(), amount);
    for (WikiPojo wiki : wikis){
      assertNotNull(wiki.title);
      assertNotNull(wiki.distanceFromHotspotInMeter);
    }
  }

  @Test
  public void wikis_around_hotspot_are_found() throws SQLException {
    //given
    double latBrbTor = 52.51612;
    double lonBrbTor = 13.37899;
    int amount = 5;
    int maxDistanceInMeter = 500;

    //when
    int gid = new FlickrDatabaseDatasource().loadHotspotGid(latBrbTor, lonBrbTor);
    List<WikiPojo> wikis = new WikiDatabaseDatasource().loadWikisAroundHotspot(gid, maxDistanceInMeter, amount);

    //then
    assertNotNull(wikis);
    assertEquals(wikis.size(), amount);
    for (WikiPojo wiki : wikis){
      assertNotNull(wiki.title);
      assertNotNull(wiki.distanceFromHotspotInMeter);
    }
  }

}
