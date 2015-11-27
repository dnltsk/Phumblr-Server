package org.wherecamp.hackathon.phumblr.service.flickr;

import org.junit.Ignore;
import org.junit.Test;
import org.wherecamp.hackathon.phumblr.service.FlickrPojo;

import java.sql.SQLException;
import java.util.List;

import static junit.framework.Assert.*;

/**
 * Created by danielt on 27.11.15.
 */
public class FlickrDatabaseDatasourceTest {

  @Test
  public void gid_can_be_loaded() throws SQLException {
    //given
    double latBrbTor = 52.51612;
    double lonBrbTor = 13.37899;

    //when
    int gid = new FlickrDatabaseDatasource().loadHotspotGid(latBrbTor, lonBrbTor);

    //then
    assertNotNull(gid);
    assertTrue(gid > 0);
  }

  @Test
  public void relevant_photos_of_gid_are_loaded() throws SQLException {
    //given
    double latBrbTor = 52.51612;
    double lonBrbTor = 13.37899;
    int amount = 5;

    //when
    int gid = new FlickrDatabaseDatasource().loadHotspotGid(latBrbTor, lonBrbTor);
    List<FlickrPojo> photos = new FlickrDatabaseDatasource().loadRelevantPhotos(gid, amount);

    //then
    assertEquals(photos.size(), amount);
    for(FlickrPojo photo : photos){
      assertNotNull(photo.photoId);
      assertNotNull(photo.views);
    }
  }

}
