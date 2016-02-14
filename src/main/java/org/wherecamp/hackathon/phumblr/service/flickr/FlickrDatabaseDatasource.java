package org.wherecamp.hackathon.phumblr.service.flickr;

import org.apache.log4j.Logger;
import org.wherecamp.hackathon.phumblr.service.FlickrPojo;
import org.wherecamp.hackathon.phumblr.service.PhumblrApplication;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by danielt on 27.11.15.
 */
public class FlickrDatabaseDatasource {

  Logger LOGGER = Logger.getLogger(FlickrDatabaseDatasource.class);

  public Integer loadHotspotGid(double lat, double lon) throws SQLException {
    StringBuffer sql = new StringBuffer()
        .append(" select")
        .append("   gid")
        .append(" from")
        .append("   flickr_heatmap")
        .append(" where")
        .append("   ST_Contains(")
        .append("     geom,")
        .append("     ST_Transform(")
        .append("       ST_SetSRID(ST_MakePoint("+lon+", "+lat+"),4326),")
        .append("       25833")
        .append("     )")
        .append("   ) ");

    LOGGER.info(sql.toString());

    Connection conn = PhumblrApplication.openPostgresConnection();
    Statement stmt = null;
    ResultSet rs = null;
    try{
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql.toString());
      if(rs.next()){
        Long gid = rs.getLong("gid");
        return gid.intValue();
      }
      return null;
    } catch (SQLException e) {
      LOGGER.error("error while loadHotspotGid: "+e.getLocalizedMessage(), e);
      throw e;
    } finally {
      try {
        rs.close();
        stmt.close();
        conn.close();
      } catch (SQLException e) {
        LOGGER.error("error while closing stuff: "+e.getLocalizedMessage(), e);
      }
    }
  }

  public List<FlickrPojo> loadRelevantPhotos(Integer gid, int amount) throws SQLException {

	  StringBuffer sql = new StringBuffer()
		        .append("SELECT distinct h.photo_id, f.views  as views "+
	  "from hotspot_photo_cache h left join flickr f on h.photo_id = f.photo_id "+
	  "where hotspot_id = " + gid +
	  " order by f.views desc "+
	  " limit "+ amount);
	  
    LOGGER.info(sql.toString());

    Connection conn = PhumblrApplication.openPostgresConnection();
    Statement stmt = null;
    ResultSet rs = null;
    try{
      stmt = conn.createStatement();
      LOGGER.info("statement created");
      rs = stmt.executeQuery(sql.toString());
      LOGGER.info("statement executed");
      List<FlickrPojo> photos = new ArrayList<>();
      while(rs.next()){
        FlickrPojo photo = new FlickrPojo();
        photo.views = rs.getInt("views");
        photo.photoId = rs.getLong("photo_id");
        photos.add(photo);
      }
      LOGGER.info("returning...");
      return photos;
    } catch (SQLException e) {
      LOGGER.error("error while loadRelevantPhotos: "+e.getLocalizedMessage(), e);
      throw e;
    } finally {
      try {
        rs.close();
        stmt.close();
        conn.close();
      } catch (SQLException e) {
        LOGGER.error("error while closing stuff: "+e.getLocalizedMessage(), e);
      }
    }
  }
}
