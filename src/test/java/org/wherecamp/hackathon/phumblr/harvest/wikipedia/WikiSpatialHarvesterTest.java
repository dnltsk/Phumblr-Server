package org.wherecamp.hackathon.phumblr.harvest.wikipedia;

import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

/**
 * Created by danielt on 27.11.15.
 */
public class WikiSpatialHarvesterTest {

  @Ignore
  @Test
  public void bundesliga_is_parsed_correctly() throws Exception {
    //given
    String kmlText = getBundesligaMock();

    //when
    List<WikiHarvestPojo> harvestedWikis = new WikiSpatialHarvester().executeWithText(kmlText);
    harvestedWikis = new WikiTextHarvester().queryText(harvestedWikis);
  }

    @Test
    public void landmarklists_kml_is_parsed_correctly() throws Exception {
        //given
        String kmlText = getListenMock();

        //when
        List<WikiHarvestPojo> harvestedWikis = new WikiSpatialHarvester().executeWithText(kmlText);

    }



  private String getBundesligaMock(){
    return "\n" +
        "\n" +
        "<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n" +
        "<Document>\n" +
        "  <name>cities.kml</name>\n" +
        "  <visibility>0</visibility>\n" +
        "  <open>1</open>\n" +
        "\n" +
        "\n" +
        "\n" +
        "\n" +
        "\n" +
        "<Style id=\"landmark\">\n" +
        "    <IconStyle>\n" +
        "      <scale>0.65</scale>\n" +
        "      <color>ffaaaaff</color>\n" +
        "      <Icon>\n" +
        "        <href>//tools.wmflabs.org/wp-world/icons/W.png</href>\n" +
        "       </Icon>\n" +
        "    </IconStyle>\n" +
        "    <LabelStyle>\n" +
        "      <color>ff00aaff</color>\n" +
        "      <scale>0.8</scale>\n" +
        "    </LabelStyle>\n" +
        "  </Style>\n" +
        "  <Placemark><name>Berlin-Liga</name> <visibility>1</visibility>  <description><![CDATA[ <a target=\"_blank\" href=\"//en.wikipedia.org/wiki/Berlin-Liga\">en.Wikipedia</a><br><br><small>Source: de<br> style: landmark </small>]]></description>     <styleUrl>#landmark</styleUrl>    <Point><coordinates>13.26472222,52.50111111,0</coordinates>\n" +
        " </Point>  </Placemark>\n" +
        "  <Placemark><name>All-time Bundesliga table</name> <visibility>1</visibility>  <description><![CDATA[ <a target=\"_blank\" href=\"//en.wikipedia.org/wiki/All-time%20Bundesliga%20table\">en.Wikipedia</a><br><br><small>Source: de<br> style: landmark </small>]]></description>     <styleUrl>#landmark</styleUrl>    <Point><coordinates>13.26416667,52.50083333,0</coordinates>\n" +
        " </Point>  </Placemark>\n" +
        "  <Placemark><name>German Football League</name> <visibility>1</visibility>  <description><![CDATA[ <a target=\"_blank\" href=\"//en.wikipedia.org/wiki/German%20Football%20League\">en.Wikipedia</a><br><br><small>Source: de<br> style: landmark </small>]]></description>     <styleUrl>#landmark</styleUrl>    <Point><coordinates>13.26416667,52.50083333,0</coordinates>\n" +
        " </Point>  </Placemark>\n" +
        "  <Placemark><name>2010–11 2. Bundesliga (women)</name> <visibility>1</visibility>  <description><![CDATA[ <a target=\"_blank\" href=\"//en.wikipedia.org/wiki/2010%E2%80%9311%202.%20Bundesliga%20%28women%29\">en.Wikipedia</a><br><br><small>Source: de<br> style: landmark </small>]]></description>     <styleUrl>#landmark</styleUrl>    <Point><coordinates>13.26388889,52.50111111,0</coordinates>\n" +
        " </Point>  </Placemark>\n" +
        "  <Placemark><name>2007–08 2. Bundesliga (women)</name> <visibility>1</visibility>  <description><![CDATA[ <a target=\"_blank\" href=\"//en.wikipedia.org/wiki/2007%E2%80%9308%202.%20Bundesliga%20%28women%29\">en.Wikipedia</a><br><div style=\" padding-top: 0.5em; padding-left: 0.5em; width:190px;height:250px; border:10px;\" > <img alt=\"USV_Jena-Aufstiegsjubel.jpg\"  src=\"//upload.wikimedia.org/wikipedia/commons/thumb/e/eb/USV_Jena-Aufstiegsjubel.jpg/180px-USV_Jena-Aufstiegsjubel.jpg\"></a></div><br><small>Source: de<br> style: landmark </small>]]></description>     <styleUrl>#landmark</styleUrl>    <Point><coordinates>13.26388889,52.50111111,0</coordinates>\n" +
        " </Point>  </Placemark>\n" +
        "  <Placemark><name>2008–09 2. Bundesliga (women)</name> <visibility>1</visibility>  <description><![CDATA[ <a target=\"_blank\" href=\"//en.wikipedia.org/wiki/2008%E2%80%9309%202.%20Bundesliga%20%28women%29\">en.Wikipedia</a><br><br><small>Source: de<br> style: landmark </small>]]></description>     <styleUrl>#landmark</styleUrl>    <Point><coordinates>13.26388889,52.50111111,0</coordinates>\n" +
        " </Point>  </Placemark>\n" +
        "  <Placemark><name>1991–92 Bundesliga (women)</name> <visibility>1</visibility>  <description><![CDATA[ <a target=\"_blank\" href=\"//en.wikipedia.org/wiki/1991%E2%80%9392%20Bundesliga%20%28women%29\">en.Wikipedia</a><br><br><small>Source: de<br> style: landmark </small>]]></description>     <styleUrl>#landmark</styleUrl>    <Point><coordinates>13.26388889,52.50111111,0</coordinates>\n" +
        " </Point>  </Placemark>\n" +
        "  <Placemark><name>1992–93 Bundesliga (women)</name> <visibility>1</visibility>  <description><![CDATA[ <a target=\"_blank\" href=\"//en.wikipedia.org/wiki/1992%E2%80%9393%20Bundesliga%20%28women%29\">en.Wikipedia</a><br><br><small>Source: de<br> style: landmark </small>]]></description>     <styleUrl>#landmark</styleUrl>    <Point><coordinates>13.26388889,52.50111111,0</coordinates>\n" +
        " </Point>  </Placemark>\n" +
        "  <Placemark><name>1998–99 NOFV-Oberliga</name> <visibility>1</visibility>  <description><![CDATA[ <a target=\"_blank\" href=\"//en.wikipedia.org/wiki/1998%E2%80%9399%20NOFV-Oberliga\">en.Wikipedia</a><br><br><small>Source: de<br> style: landmark </small>]]></description>     <styleUrl>#landmark</styleUrl>    <Point><coordinates>13.2645,52.501111,0</coordinates>\n" +
        " </Point>  </Placemark>\n" +
        "  <Placemark><name>2008–09 NOFV-Oberliga</name> <visibility>1</visibility>  <description><![CDATA[ <a target=\"_blank\" href=\"//en.wikipedia.org/wiki/2008%E2%80%9309%20NOFV-Oberliga\">en.Wikipedia</a><br><br><small>Source: de<br> style: landmark </small>]]></description>     <styleUrl>#landmark</styleUrl>    <Point><coordinates>13.2645,52.5011,0</coordinates>\n" +
        " </Point>  </Placemark>\n" +
        "  <Placemark><name>1994–95 Bundesliga (women)</name> <visibility>1</visibility>  <description><![CDATA[ <a target=\"_blank\" href=\"//en.wikipedia.org/wiki/1994%E2%80%9395%20Bundesliga%20%28women%29\">en.Wikipedia</a><br><br><small>Source: de<br> style: landmark </small>]]></description>     <styleUrl>#landmark</styleUrl>    <Point><coordinates>13.26388889,52.50111111,0</coordinates>\n" +
        " </Point>  </Placemark>\n" +
        "  <Placemark><name>2002–03 NOFV-Oberliga</name> <visibility>1</visibility>  <description><![CDATA[ <a target=\"_blank\" href=\"//en.wikipedia.org/wiki/2002%E2%80%9303%20NOFV-Oberliga\">en.Wikipedia</a><br><br><small>Source: de<br> style: landmark </small>]]></description>     <styleUrl>#landmark</styleUrl>    <Point><coordinates>13.2645,52.501,0</coordinates>\n" +
        " </Point>  </Placemark>\n" +
        "  <Placemark><name>1993–94 Bundesliga (women)</name> <visibility>1</visibility>  <description><![CDATA[ <a target=\"_blank\" href=\"//en.wikipedia.org/wiki/1993%E2%80%9394%20Bundesliga%20%28women%29\">en.Wikipedia</a><br><br><small>Source: de<br> style: landmark </small>]]></description>     <styleUrl>#landmark</styleUrl>    <Point><coordinates>13.26388889,52.50111111,0</coordinates>\n" +
        " </Point>  </Placemark>\n" +
        "  <Placemark><name>2007–08 NOFV-Oberliga</name> <visibility>1</visibility>  <description><![CDATA[ <a target=\"_blank\" href=\"//en.wikipedia.org/wiki/2007%E2%80%9308%20NOFV-Oberliga\">en.Wikipedia</a><br><br><small>Source: de<br> style: landmark </small>]]></description>     <styleUrl>#landmark</styleUrl>    <Point><coordinates>13.2645,52.5011,0</coordinates>\n" +
        " </Point>  </Placemark>\n" +
        "  <Placemark><name>2001–02 NOFV-Oberliga</name> <visibility>1</visibility>  <description><![CDATA[ <a target=\"_blank\" href=\"//en.wikipedia.org/wiki/2001%E2%80%9302%20NOFV-Oberliga\">en.Wikipedia</a><br><br><small>Source: de<br> style: landmark </small>]]></description>     <styleUrl>#landmark</styleUrl>    <Point><coordinates>13.2645,52.501,0</coordinates>\n" +
        " </Point>  </Placemark>\n" +
        "  <Placemark><name>2005–06 NOFV-Oberliga</name> <visibility>1</visibility>  <description><![CDATA[ <a target=\"_blank\" href=\"//en.wikipedia.org/wiki/2005%E2%80%9306%20NOFV-Oberliga\">en.Wikipedia</a><br><br><small>Source: de<br> style: landmark </small>]]></description>     <styleUrl>#landmark</styleUrl>    <Point><coordinates>13.2645,52.5011,0</coordinates>\n" +
        " </Point>  </Placemark>\n" +
        "  <Placemark><name>2003–04 NOFV-Oberliga</name> <visibility>1</visibility>  <description><![CDATA[ <a target=\"_blank\" href=\"//en.wikipedia.org/wiki/2003%E2%80%9304%20NOFV-Oberliga\">en.Wikipedia</a><br><br><small>Source: de<br> style: landmark </small>]]></description>     <styleUrl>#landmark</styleUrl>    <Point><coordinates>13.2645,52.501,0</coordinates>\n" +
        " </Point>  </Placemark>\n" +
        "  <Placemark><name>2006–07 NOFV-Oberliga</name> <visibility>1</visibility>  <description><![CDATA[ <a target=\"_blank\" href=\"//en.wikipedia.org/wiki/2006%E2%80%9307%20NOFV-Oberliga\">en.Wikipedia</a><br><br><small>Source: de<br> style: landmark </small>]]></description>     <styleUrl>#landmark</styleUrl>    <Point><coordinates>13.2645,52.5011,0</coordinates>\n" +
        " </Point>  </Placemark>\n" +
        "  <Placemark><name>1995–96 Bundesliga (women)</name> <visibility>1</visibility>  <description><![CDATA[ <a target=\"_blank\" href=\"//en.wikipedia.org/wiki/1995%E2%80%9396%20Bundesliga%20%28women%29\">en.Wikipedia</a><br><div style=\" padding-top: 0.5em; padding-left: 0.5em; width:190px;height:250px; border:10px;\" > <img alt=\"Sandra_Smisek.jpg\"  src=\"//upload.wikimedia.org/wikipedia/commons/thumb/5/5d/Sandra_Smisek.jpg/180px-Sandra_Smisek.jpg\"></a></div><br><small>Source: de<br> style: landmark </small>]]></description>     <styleUrl>#landmark</styleUrl>    <Point><coordinates>13.26388889,52.50111111,0</coordinates>\n" +
        " </Point>  </Placemark>\n" +
        "  <Placemark><name>1996–97 Bundesliga (women)</name> <visibility>1</visibility>  <description><![CDATA[ <a target=\"_blank\" href=\"//en.wikipedia.org/wiki/1996%E2%80%9397%20Bundesliga%20%28women%29\">en.Wikipedia</a><br><br><small>Source: de<br> style: landmark </small>]]></description>     <styleUrl>#landmark</styleUrl>    <Point><coordinates>13.26388889,52.50111111,0</coordinates>\n" +
        " </Point>  </Placemark>\n" +
        "  <Placemark><name>2004–05 NOFV-Oberliga</name> <visibility>1</visibility>  <description><![CDATA[ <a target=\"_blank\" href=\"//en.wikipedia.org/wiki/2004%E2%80%9305%20NOFV-Oberliga\">en.Wikipedia</a><br><br><small>Source: de<br> style: landmark </small>]]></description>     <styleUrl>#landmark</styleUrl>    <Point><coordinates>13.2645,52.5011,0</coordinates>\n" +
        " </Point>  </Placemark>\n" +
        "  <Placemark><name>2010–11 NOFV-Oberliga</name> <visibility>1</visibility>  <description><![CDATA[ <a target=\"_blank\" href=\"//en.wikipedia.org/wiki/2010%E2%80%9311%20NOFV-Oberliga\">en.Wikipedia</a><br><br><small>Source: de<br> style: landmark </small>]]></description>     <styleUrl>#landmark</styleUrl>    <Point><coordinates>13.2645,52.501,0</coordinates>\n" +
        " </Point>  </Placemark>\n" +
        "  <Placemark><name>2002–03 Bundesliga (women)</name> <visibility>1</visibility>  <description><![CDATA[ <a target=\"_blank\" href=\"//en.wikipedia.org/wiki/2002%E2%80%9303%20Bundesliga%20%28women%29\">en.Wikipedia</a><br><div style=\" padding-top: 0.5em; padding-left: 0.5em; width:190px;height:250px; border:10px;\" > <img alt=\"Inka_Grings_01.jpg\"  src=\"//upload.wikimedia.org/wikipedia/commons/thumb/3/3a/Inka_Grings_01.jpg/180px-Inka_Grings_01.jpg\"></a></div><br><small>Source: de<br> style: landmark </small>]]></description>     <styleUrl>#landmark</styleUrl>    <Point><coordinates>13.26388889,52.50111111,0</coordinates>\n" +
        " </Point>  </Placemark>\n" +
        "  <Placemark><name>Mommsenstadion</name> <visibility>1</visibility>  <description><![CDATA[ <a target=\"_blank\" href=\"//en.wikipedia.org/wiki/Mommsenstadion\">en.Wikipedia</a><br><div style=\" padding-top: 0.5em; padding-left: 0.5em; width:190px;height:250px; border:10px;\" > <img alt=\"WestendMommsenstadion-4.JPG\"  src=\"//upload.wikimedia.org/wikipedia/commons/thumb/3/34/WestendMommsenstadion-4.JPG/180px-WestendMommsenstadion-4.JPG\"></a></div><br><small>Source: de<br> style: landmark </small>]]></description>     <styleUrl>#landmark</styleUrl>    <Point><coordinates>13.26416667,52.50083333,0</coordinates>\n" +
        " </Point>  </Placemark>\n" +
        "  <Placemark><name>Tennis Borussia Berlin</name> <visibility>1</visibility>  <description><![CDATA[ <a target=\"_blank\" href=\"//en.wikipedia.org/wiki/Tennis%20Borussia%20Berlin\">en.Wikipedia</a><br><br><small>Source: de<br> style: landmark </small>]]></description>     <styleUrl>#landmark</styleUrl>    <Point><coordinates>13.26393889,52.50041111,0</coordinates>\n" +
        " </Point>  </Placemark>\n" +
        "\n" +
        " <Placemark>\n" +
        "      <name>Info</name>\n" +
        "      <visibility>1</visibility>\n" +
        "      <description><![CDATA[This is a extract from <a href=\"//en.wikipedia.org/wiki/Hauptseite\">Wikipedia</a> from 42 languages, it use the Interwikilinks to generarte all the other languages. See for more infos: <a href=\"//de.wikipedia.org/wiki/Wikipedia:WikiProjekt_Georeferenzierung/Wikipedia-World/en\">Wikipedia-World</a> \n" +
        "\t\t   <p>Created in 0.005+0.015+0<p>(SELECT \"T_en\" as title, pop,lon,lat,style,lang,image,imagejpg,name,arms FROM wp_coords_red0  WHERE the_geom && \n" +
        "ST_SetSRID(ST_MakeBox2D(ST_Point(13.263036890475995,52.49932667856996), \n" +
        "\t\t\tST_Point(13.265805335713992,52.502095123807955)),4326) and \"T_en\" IS NOT NULL  ORDER BY psize DESC LIMIT 80)  thumbs: 0 COATS: 0 \n" +
        "]]></description>\n" +
        "    </Placemark>\n" +
        "\n" +
        "</Document>\n" +
        "</kml>\n";
  }
    
    
    private String getListenMock(){
        return "\n" +
            "\n" +
            "<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n" +
            "<Document>\n" +
            "  <name>cities.kml</name>\n" +
            "  <visibility>0</visibility>\n" +
            "  <open>1</open>\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "<Style id=\"landmarklist\">\n" +
            "    <IconStyle>\n" +
            "      <scale>0.6</scale>\n" +
            "      <color>aaaaaaff</color>\n" +
            "      <Icon>\n" +
            "        <href>//tools.wmflabs.org/wp-world/icons/W-list.png</href>\n" +
            "       </Icon>\n" +
            "    </IconStyle>\n" +
            "    <LabelStyle>\n" +
            "      <color>ff00aaff</color>\n" +
            "      <scale>0.8</scale>\n" +
            "    </LabelStyle>\n" +
            "  </Style>\n" +
            "  <Placemark><name>#Eichkampstraße</name> <visibility>1</visibility>  <description><![CDATA[ <a target=\"_blank\" href=\"//de.wikipedia.org/wiki/Liste%20der%20Stra%C3%9Fen%20und%20Pl%C3%A4tze%20in%20Berlin-Grunewald%23Eichkampstra%C3%9Fe\">Liste der Straßen und Plätze in Berlin-Grunewald</a><br><div style=\" padding-top: 0.5em; padding-left: 0.5em; width:190px;height:250px; border:10px;\" > <img alt=\"2000-03-26_Bahnhof_Grunewald.jpg\"  src=\"//upload.wikimedia.org/wikipedia/commons/thumb/b/bc/2000-03-26_Bahnhof_Grunewald.jpg/180px-2000-03-26_Bahnhof_Grunewald.jpg\"></a></div><br><small>Source: de<br> style: landmarklist </small>]]></description>     <styleUrl>#landmarklist</styleUrl>    <Point><coordinates>13.257774,52.487323,0</coordinates>\n" +
            " </Point>  </Placemark>\n" +
            "  <Placemark><name>#Auerbachstraße</name> <visibility>1</visibility>  <description><![CDATA[ <a target=\"_blank\" href=\"//de.wikipedia.org/wiki/Liste%20der%20Stra%C3%9Fen%20und%20Pl%C3%A4tze%20in%20Berlin-Grunewald%23Auerbachstra%C3%9Fe\">Liste der Straßen und Plätze in Berlin-Grunewald</a><br><div style=\" padding-top: 0.5em; padding-left: 0.5em; width:190px;height:250px; border:10px;\" > <img alt=\"2000-03-26_Bahnhof_Grunewald.jpg\"  src=\"//upload.wikimedia.org/wikipedia/commons/thumb/b/bc/2000-03-26_Bahnhof_Grunewald.jpg/180px-2000-03-26_Bahnhof_Grunewald.jpg\"></a></div><br><small>Source: de<br> style: landmarklist </small>]]></description>     <styleUrl>#landmarklist</styleUrl>    <Point><coordinates>13.259856,52.486857,0</coordinates>\n" +
            " </Point>  </Placemark>\n" +
            "\n" +
            " <Placemark>\n" +
            "      <name>Info</name>\n" +
            "      <visibility>1</visibility>\n" +
            "      <description><![CDATA[This is a extract from <a href=\"//en.wikipedia.org/wiki/Hauptseite\">Wikipedia</a> from 42 languages, it use the Interwikilinks to generarte all the other languages. See for more infos: <a href=\"//de.wikipedia.org/wiki/Wikipedia:WikiProjekt_Georeferenzierung/Wikipedia-World/en\">Wikipedia-World</a> \n" +
            "\t\t   <p>Created in 0.007+0.014+0<p>(SELECT \"T_de\" as title, pop,lon,lat,style,lang,image,imagejpg,name,arms FROM wp_coords_red0  WHERE the_geom && \n" +
            "ST_SetSRID(ST_MakeBox2D(ST_Point(13.2575,52.48548445237997), \n" +
            "\t\t\tST_Point(13.260268445237998,52.48825289761797)),4326) and \"T_de\" IS NOT NULL  ORDER BY psize DESC LIMIT 80)  thumbs: 0 COATS: 0 \n" +
            "]]></description>\n" +
            "    </Placemark>\n" +
            "\n" +
            "</Document>\n" +
            "</kml>\n";
    }

}
