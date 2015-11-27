package org.wherecamp.hackathon.phumblr.harvest;

import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.tags.Tag;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.wherecamp.hackathon.phumblr.harvest.wikipedia.WikiHarvestPojo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by danielt on 26.11.15.
 */
public class CsvWriter {

    private final static Logger LOGGER = Logger.getLogger(CsvWriter.class);

    BufferedWriter writer;

    public CsvWriter(String targetFileName){
        try {
            //File targetFile = new File(new File(getClass().getResource("").getFile()).getAbsoluteFile()+"/"+targetFileName);
            File targetFile = new File(targetFileName);
            LOGGER.info("init writer to " + targetFile.getAbsolutePath());
            writer = new BufferedWriter(new FileWriter(targetFile));
        } catch (IOException e) {
            LOGGER.error("cannot init CsvWriter.", e);
        }
    }

    public void initFlickr(){
        try {
            writer.append("photo_id;owner;lat;lon;accuracy;views;comments;is_public;is_family;is_favorite;date_taken;title;tags\n");
        } catch (IOException e) {
            LOGGER.error("cannot init Flickr.", e);
        }
    }

    public void addPhoto(Photo photo){
        try {
            writer.append(photo.getId()).append(";");
            writer.append(photo.getOwner().getId()).append(";");
            writer.append(Double.toString(photo.getGeoData().getLatitude())).append(";");
            writer.append(Double.toString(photo.getGeoData().getLongitude())).append(";");
            writer.append(Double.toString(photo.getGeoData().getAccuracy())).append(";");
            writer.append(Integer.toString(photo.getViews())).append(";");
            writer.append(Integer.toString(photo.getComments())).append(";");
            writer.append(Boolean.toString(photo.isPublicFlag())).append(";");
            writer.append(Boolean.toString(photo.isFamilyFlag())).append(";");
            writer.append(Boolean.toString(photo.isFavorite())).append(";");

            if(photo.getDateTaken()!=null) {
                writer.append(photo.getDateTaken().toString());
            }
            writer.append(";");

            writer.append(photo.getTitle()).append(";");

            ArrayList<Tag> tagsArray = new ArrayList<Tag>(photo.getTags());
            for(int i=0; i < tagsArray.size(); i++){
                Tag tag = tagsArray.get(i);
                writer.append(tag.getValue());
                if(i<tagsArray.size()-1){
                    writer.append(", ");
                }
            }
            writer.append("\n");
        } catch (IOException e) {
            LOGGER.error("cannot write Photo ",e);
        }
    }

    public void initWiki(){
        try {
            writer.append("lat,lon,title,desc\n");
        } catch (IOException e) {
            LOGGER.error("cannot init Wiki.", e);
        }

    }

    public void addWiki(WikiHarvestPojo p) {
        try {
            writer.append(Double.toString(p.lat)).append(";");
            writer.append(Double.toString(p.lon)).append(";");
            writer.append(StringEscapeUtils.escapeCsv(p.humanReadableTitle)).append(";");
            writer.append(StringEscapeUtils.escapeCsv(p.abstractSection)).append(";");
            writer.append("\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void close(){
        try {
            writer.close();
        } catch (IOException e) {
            LOGGER.error("cannot close CsvWriter.", e);
        }
    }



}
