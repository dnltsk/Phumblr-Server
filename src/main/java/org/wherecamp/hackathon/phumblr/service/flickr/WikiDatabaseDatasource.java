package org.wherecamp.hackathon.phumblr.service.flickr;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.wherecamp.hackathon.phumblr.service.PhumblrApplication;
import org.wherecamp.hackathon.phumblr.service.WikiPojo;
import org.wherecamp.hackathon.phumblr.service.WikiSection;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by danielt on 27.11.15.
 */

public class WikiDatabaseDatasource {

  Logger LOGGER = Logger.getLogger(FlickrDatabaseDatasource.class);
  DataSource dataSource;

  public WikiDatabaseDatasource(){
    super();
  }

  public WikiDatabaseDatasource(DataSource dataSource){
    this.dataSource = dataSource;
  }

  public List<WikiPojo> loadWikisInsideHotspot(int hotspotGid, int amount) throws SQLException {
    StringBuffer sql = new StringBuffer()
        .append(" select distinct")
        .append("   w.title, ")
        .append("   0.0 as distanceFromHotspotInMeter, ")
        .append("   w.sections ")
        .append(" from")
        .append("   wiki w, flickr_heatmap h")
        .append(" where")
        .append("   h.gid = ")
        .append(hotspotGid)
        .append("   and ST_Contains(h.geom, w.geom)")
        .append(" limit ")
        .append(amount);

    LOGGER.info(sql.toString());

    Connection conn = dataSource.getConnection();
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
        if (rs != null) {
          rs.close();
        }
        if (stmt != null) {
          stmt.close();
        }
        if (conn != null) {
          conn.close();
        }
      } catch (SQLException e) {
        LOGGER.error("error while closing stuff: "+e.getLocalizedMessage(), e);
      }
    }
  }

  public List<WikiPojo> loadWikisAroundHotspot(int hotspotGid, int maxDistanceInMeter, int amount) throws SQLException {
    StringBuilder sql = new StringBuilder()
        .append(" select distinct")
        .append("   w.title, ")
        .append("   ST_Distance(h.geom, w.geom) as distanceFromHotspotInMeter, ")
        .append("   w.sections ")
        .append(" from ")
        .append("   wiki w, flickr_heatmap h")
        .append(" where ")
        .append("   h.gid = ")
        .append( hotspotGid)
        .append("   and ST_Contains(h.geom, w.geom) = False ")
        .append("   and ST_Contains(ST_Buffer(h.geom, "+maxDistanceInMeter+"), w.geom)")
        .append(" order by distanceFromHotspotInMeter ")
        .append(" limit " )
        .append(amount);

    LOGGER.info(sql.toString());

    Connection conn = dataSource.getConnection(); //PhumblrApplication.openPostgresConnection();
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
      LOGGER.error("error while loadWikisAroundHotspot: "+e.getLocalizedMessage(), e);
      throw e;
    } finally {
      try {
        if (rs != null) {
          rs.close();
        }
        if (stmt != null) {
          stmt.close();
        }
        if (conn != null) {
          conn.close();
        }
      } catch (SQLException e) {
        LOGGER.error("error while closing stuff: "+e.getLocalizedMessage(), e);
      }
    }
  }


  private WikiPojo readWikiFromResultSet(ResultSet rs) throws SQLException {
    WikiPojo wiki = new WikiPojo();
    wiki.title = rs.getString("title");
    wiki.distanceFromHotspotInMeter = rs.getDouble("distanceFromHotspotInMeter");
    String sections = rs.getString("sections");
    if(sections != null){
      wiki.sections = createSingleSections(sections, wiki.title);
    }
    return wiki;
  }

  private List<WikiSection> createSingleSections(String sections, String title) {
    ArrayList<WikiSection> newSingleSections = new ArrayList<>();
    ArrayList<String[]> simpleSections = writeSections(sections, title);
    for(String[] simpleSection : simpleSections){
      WikiSection newSection = new WikiSection();
      newSection.title = simpleSection[0];
      newSection.content = simpleSection[1];
      newSingleSections.add(newSection);
    }
    return newSingleSections;
  }

  /**
   * Method returns sections ArrayList by testing two patterns the Wikipedia API uses.
   *
   * @param extract input String containing fetched Wikipedia article
   * @return sections as ArrayList of Strings
   */
  private ArrayList<String[]>  writeSections(String extract, String title) {
    ArrayList<String[]> sections = scanPattern("== (.*?)Bearbeiten ==", extract, title);

    if (sections.size() < 2) {
      sections = scanPattern("== (.*?) ==", extract, title);
    }

    return sections;
  }

  /**
   * Method writes ArrayList of String Arrays with section title and section content via Pattern
   * Matching from the extract text fetched from the WikiMedia API.
   * <p/>
   * Each line gets scanned and compared if it starts and ends with "==". If so that text between
   * get is taken as section title. The next lines lines are getting combined together as content
   * of the section until the next line with "==" appears or the extract text has no more line. If
   * there is no content after title the section gets dropped and nothing gets written to the
   * list.
   *
   * @param pattern a specific String pattern to detect section of article
   * @param extract String object containing all section content to search word from WikiMedia API
   * @return ArrayList<String[]> object with String[title, content] for each list item
   */
  public ArrayList<String[]> scanPattern(String pattern, String extract, String title) {
    ArrayList<String[]> temp_sections = new ArrayList<>();

    Scanner scanner = new Scanner(extract);
    String content = "";

    //temp_sections.add(new String[]{title, site.getAddress()});

    while (scanner.hasNextLine()) {
      boolean isContent = true;
      String line = scanner.nextLine();
      Pattern p = Pattern.compile(pattern);
      Matcher m = p.matcher(line);

      while (m.find()) {
        String section_title = m.group(1);
        if (!content.equals("")) temp_sections.add(new String[]{section_title, content});
        isContent = false;
        content = "";
      }

      if (isContent) content += line;
    }
    scanner.close();

    return temp_sections;
  }


}
