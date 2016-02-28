package org.wherecamp.hackathon.phumblr.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;




/**
 * Created by danielt on 26.11.15.
 */
@SpringBootApplication
public class PhumblrApplication {

    private final static Logger LOGGER = Logger.getLogger(PhumblrApplication.class);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(PhumblrHttpController.class, args);
    }


    @Value("${flickrKey}")
    private String flickrKey;

    @Value("${flickrSecureKey}")
    private String flickrSecureKey;

    @Bean
    public FlickrConfig flickrConfig() {
        LOGGER.info("getting flickr config");
        FlickrConfig fc = new FlickrConfig();
        fc.setFlickrKey(flickrKey);
        fc.setFlickrSecureKey(flickrSecureKey);
        return fc;
    }
}
