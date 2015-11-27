package org.wherecamp.hackathon.phumblr.service.flickr;

import org.junit.Test;

import java.net.URL;

import static junit.framework.Assert.*;

/**
 * Created by danielt on 27.11.15.
 */
public class FlickrApiDatasourceTest {

  @Test
  public void best_image_url_can_be_resolved(){
    //given
    Long photoId = 14030905149L;
    int small320 = 320;
    int large = 1024;

    //when
    URL small320Url = new FlickrApiDatasource().getPhotoUrl(photoId, small320);
    URL largeUrl = new FlickrApiDatasource().getPhotoUrl(photoId, large);

    //then
    assertEquals(small320Url.toString(), "https://farm6.staticflickr.com/5236/14030905149_abde72f0e3_n.jpg");
    assertEquals(largeUrl.toString(), "https://farm6.staticflickr.com/5236/14030905149_abde72f0e3_b.jpg");

  }

}
