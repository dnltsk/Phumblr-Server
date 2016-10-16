package org.wherecamp.hackathon.phumblr.service;



import javax.sql.DataSource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.wherecamp.hackathon.phumblr.service.flickr.FlickrHotspotRepository;

import java.sql.SQLException;
import java.util.List;

@ComponentScan
@Configuration
@EnableAutoConfiguration
@RestController
public class PhumblrHttpController {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private FlickrConfig flickrConfig;

    private final static Logger LOGGER = Logger.getLogger(PhumblrHttpController.class);



    @RequestMapping("/WikiInfo")
    @ResponseBody
    public String factoryBean(@RequestParam(value="hotspotid") Integer hotspotId) {
        FlickrHotspotRepository repo = new FlickrHotspotRepository(dataSource,flickrConfig);
        try {
           List<WikiPojo> hotspots = repo.loadWikis(hotspotId);

                ObjectMapper mapper = createJacksonMapper();
                String json = mapper.writeValueAsString(hotspots);
                return json;
        } catch (Exception e) {
            LOGGER.error( e.getLocalizedMessage(), e);
        }
        return "generating wiki data failed for id: " + hotspotId;
    }


    @RequestMapping("/simple")
    @ResponseBody
    public String simple(@RequestParam(value="lat") Double lat,
                              @RequestParam(value="lon") Double lon){
        FlickrHotspotRepository repo = new FlickrHotspotRepository(dataSource,flickrConfig);
        try {
            HotspotPojo flickrHotspot = repo.getHeatmap(lat, lon, false, false);
            if(flickrHotspot!=null && flickrHotspot.flickr!=null && flickrHotspot.flickr.size()>0) {
                ObjectMapper mapper = createJacksonMapper();
                String json = mapper.writeValueAsString(flickrHotspot);
                return json;
            }
        } catch (SQLException e) {
            LOGGER.error("SQL Exception in Controller: " + e.getLocalizedMessage(), e);
        } catch (JsonProcessingException e) {
            LOGGER.error("Jackson Exception in Controller: " + e.getLocalizedMessage(), e);
        }
        return "{}";
    }

    @RequestMapping("/live")
    public String operational(@RequestParam(value="lat") Double lat,
                              @RequestParam(value="lon") Double lon){
        FlickrHotspotRepository repo = new FlickrHotspotRepository(dataSource,flickrConfig);
        try {
            HotspotPojo flickrHotspot = repo.getHeatmap(lat, lon, true, true);
            if(flickrHotspot!=null && flickrHotspot.flickr!=null && flickrHotspot.flickr.size()>0) {
                ObjectMapper mapper = createJacksonMapper();
                String json = mapper.writeValueAsString(flickrHotspot);
                return json;
            }
        } catch (SQLException e) {
            LOGGER.error("SQL Exception in Controller: " + e.getLocalizedMessage(), e);
        } catch (JsonProcessingException e) {
            LOGGER.error("Jackson Exception in Controller: " + e.getLocalizedMessage(), e);
        }
        return "{}";
    }

    private ObjectMapper createJacksonMapper(){
        ObjectMapper mapper = new ObjectMapper();
    /*mapper.setVisibilityChecker(mapper.getSerializationConfig().getDefaultVisibilityChecker().
        withGetterVisibility(JsonAutoDetect.Visibility.NONE).
        withSetterVisibility(JsonAutoDetect.Visibility.NONE));
    */
        return mapper;
    }

    @RequestMapping("/")
    public String mock(@RequestParam(value="lat") Double lat,
                       @RequestParam(value="lon") Double lon){
        return "{\n" +
                "\t\"flickr\":[\n" +
                "\t\t{\n" +
                "\t\t\t\"url\":\"http://flickr...\",\n" +
                "\t\t\t\"views\":123\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"url\":\"http://flickr...\",\n" +
                "\t\t\t\"views\":123\n" +
                "\t\t}\n" +
                "\t\t\n" + //minimum 5
                "\t],\n" +
                "\n" +
                "\t\"wiki\":[\n" +
                "\t\t{\n" +
                "\t\t\t\"title\":\"Hauptbahnhof\",\n" + //human readble
                "\t\t\t\"distanceFromLocationInMeter\":123,\n" +
                "\t\t\t\"sections\":[\n" +
                "\t\t\t\t{\n" +
                "\t\t\t\t\t\"title\":\"History\",\n" +
                "\t\t\t\t\t\"content\":\"bla\"\n" +
                "\t\t\t\t}\n" +
                "\t\t\t\t\n" + //...
                "\t\t\t]\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"title\":\"Hauptbahnhof\",\t\t\n" +//human readble
                "\t\t\t\"distanceInMeter\":123,\n" +
                "\t\t\t\"sections\":[\n" +
                "\t\t\t\t{\n" +
                "\t\t\t\t\t\"title\":\"History\",\n" +
                "\t\t\t\t\t\"content\":\"bla\"\n" +
                "\t\t\t\t}\n" +
                "\t\t\t\t\n" + //...
                "\t\t\t]\n" +
                "\t\t}\n" +
                "\t\t\n" + //all within 500m
                "\t]\n" +
                "}";
    }

}

