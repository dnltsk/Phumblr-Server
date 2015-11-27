package org.wherecamp.hackathon.phumblr.service.flickr;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.photos.PhotosInterface;
import com.flickr4java.flickr.photos.Size;
import org.apache.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by danielt on 27.11.15.
 */
public class FlickrApiDatasource {

  private Logger LOGGER = Logger.getLogger(FlickrApiDatasource.class);

  private String flickrKey = "7ad8ddffead4f54320ac26f57ab8c6ea";
  private String flickrSecureKey = "7eb755f2b10a6cd2";
  private PhotosInterface flickrInterface;

  FlickrApiDatasource(){
    flickrInterface = new Flickr(flickrKey, flickrSecureKey, new REST()).getPhotosInterface();
  }

  public URL getPhotoUrl(Long photoId, int maxSize){
    try {
      ArrayList<Size> sizes = new ArrayList<>(flickrInterface.getSizes(photoId.toString()));
      Size bestSize = sizes.get(0);
      for(Size size : sizes){
        if(size.getHeight() > maxSize || size.getWidth() > maxSize){
          continue;
        }
        if(size.getHeight() >= bestSize.getHeight() && size.getWidth() >= bestSize.getWidth()){
          bestSize = size;
        }
      }
      return new URL(bestSize.getSource());
    } catch (FlickrException e) {
      LOGGER.error("general flickr api exception: "+e.getLocalizedMessage(), e);
    } catch (MalformedURLException e) {
      LOGGER.error("cannot handle url of bestSize: " + e.getLocalizedMessage(), e);
    }
    return null;
  }

}
