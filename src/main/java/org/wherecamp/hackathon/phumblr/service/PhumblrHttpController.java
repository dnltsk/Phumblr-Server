package org.wherecamp.hackathon.phumblr.service;


import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.wherecamp.hackathon.phumblr.service.flickr.FlickrHotspotRepository;

import java.sql.SQLException;

/**
 * Created by danielt on 26.11.15.
 */
@ComponentScan
@Configuration
@EnableAutoConfiguration
@RestController
public class PhumblrHttpController {

    @Autowired
    private DataSource dataSource;
    private final static Logger LOGGER = Logger.getLogger(PhumblrHttpController.class);



    @RequestMapping("/factoryBean")
    @ResponseBody
    public String factoryBean() {
        return "DataSource retrieved from JNDI using JndiObjectFactoryBean: " + dataSource;
    }


    @RequestMapping("/direct")
    @ResponseBody
    public String direct() throws NamingException {
        return "DataSource retrieved directly from JNDI: " +
                new InitialContext().lookup("java:comp/env/jdbc/phumblr");
    }

    @RequestMapping("/live")
    public String operational(@RequestParam(value="lat") Double lat,
                              @RequestParam(value="lon") Double lon){
        FlickrHotspotRepository repo = new FlickrHotspotRepository(dataSource);
        try {
            HotspotPojo flickrHotspot = repo.getHeatmap(lat, lon);
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

