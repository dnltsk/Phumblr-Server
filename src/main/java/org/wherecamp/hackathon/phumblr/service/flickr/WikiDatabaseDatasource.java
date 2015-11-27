package org.wherecamp.hackathon.phumblr.service.flickr;

import org.apache.log4j.Logger;
import org.wherecamp.hackathon.phumblr.service.PhumblrApplication;
import org.wherecamp.hackathon.phumblr.service.WikiPojo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by danielt on 27.11.15.
 */
public class WikiDatabaseDatasource {

  Logger LOGGER = Logger.getLogger(FlickrDatabaseDatasource.class);

  public List<WikiPojo> loadWikisInsideHotspot(int hotspotGid, int amount) throws SQLException {
    StringBuffer sql = new StringBuffer()
        .append(" select distinct")
        .append("   w.title, ")
        .append("   0.0 as distanceFromHotspotInMeter ")
        .append(" from")
        .append("   wiki w, flickr_heatmap h")
        .append(" where")
        .append("   h.gid = "+hotspotGid)
        .append("   and ST_Contains(h.geom, w.geom)")
        .append(" limit " + amount);

    LOGGER.info(sql.toString());

    Connection conn = PhumblrApplication.openPostgresConnection();
    Statement stmt = null;
    ResultSet rs = null;
    try{
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql.toString());
      List<WikiPojo> wikis = new ArrayList<>();
      while(rs.next()){
        WikiPojo wiki = readWikiFromResultSet(rs);
        wikis.add(wiki);
      }
      return wikis;
    } catch (SQLException e) {
      LOGGER.error("error while loadWikisInsideHotspot: "+e.getLocalizedMessage(), e);
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

  public List<WikiPojo> loadWikisAroundHotspot(int hotspotGid, int maxDistanceInMeter, int amount) throws SQLException {
    StringBuffer sql = new StringBuffer()
        .append(" select distinct")
        .append("   w.title, ")
        .append("   ST_Distance(h.geom, w.geom) as distanceFromHotspotInMeter ")
        .append(" from")
        .append("   wiki w, flickr_heatmap h")
        .append(" where")
        .append("   h.gid = "+hotspotGid)
        .append("   and ST_Contains(h.geom, w.geom) = False ")
        .append("   and ST_Contains(ST_Buffer(h.geom, "+maxDistanceInMeter+"), w.geom)")
        .append(" order by ST_Distance(h.geom, w.geom) ")
        .append(" limit " + amount);

    LOGGER.info(sql.toString());

    Connection conn = PhumblrApplication.openPostgresConnection();
    Statement stmt = null;
    ResultSet rs = null;
    try{
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql.toString());
      List<WikiPojo> wikis = new ArrayList<>();
      while(rs.next()){
        WikiPojo wiki = readWikiFromResultSet(rs);
        wikis.add(wiki);
      }
      return wikis;
    } catch (SQLException e) {
      LOGGER.error("error while loadWikisInsideHotspot: "+e.getLocalizedMessage(), e);
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


  private WikiPojo readWikiFromResultSet(ResultSet rs) throws SQLException {
    WikiPojo wiki = new WikiPojo();
    wiki.title = rs.getString("title");
    wiki.distanceFromHotspotInMeter = rs.getDouble("distanceFromHotspotInMeter");
    //wiki.wikiId = rs.getString("wiki_id");
    return wiki;
  }

}
