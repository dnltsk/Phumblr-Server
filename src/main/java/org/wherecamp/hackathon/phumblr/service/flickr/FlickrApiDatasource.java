package org.wherecamp.hackathon.phumblr.service.flickr;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.photos.PhotosInterface;
import com.flickr4java.flickr.photos.Size;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.wherecamp.hackathon.phumblr.service.FlickrConfig;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by danielt on 27.11.15.
 */

public class FlickrApiDatasource {

  private Logger LOGGER = Logger.getLogger(FlickrApiDatasource.class);

  private PhotosInterface flickrInterface;

  private FlickrConfig flickrConfig;

  FlickrApiDatasource(){
    super();
    if(flickrConfig != null) {
      flickrInterface = new Flickr(flickrConfig.getFlickrKey(), flickrConfig.getFlickrSecureKey(), new REST()).getPhotosInterface();
    }
  }


  FlickrApiDatasource(FlickrConfig flickrConfig){
    this.flickrConfig = flickrConfig;
    flickrInterface = new Flickr(flickrConfig.getFlickrKey(), flickrConfig.getFlickrSecureKey(), new REST()).getPhotosInterface();
  }

  public void setFlickrConfig(FlickrConfig flickrConfig) {
    this.flickrConfig = flickrConfig;
    flickrInterface = new Flickr(flickrConfig.getFlickrKey(), flickrConfig.getFlickrSecureKey(), new REST()).getPhotosInterface();
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
