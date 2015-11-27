package org.wherecamp.hackathon.phumblr.service;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by danielt on 26.11.15.
 */
@SpringBootApplication
public class PhumblrApplication {

  private final static Logger LOGGER = Logger.getLogger(PhumblrApplication.class);
  private static Properties properties;


  public static Properties getProperties(){
    if(properties != null){
      return properties;
    }
    try{
      final InputStream stream = PhumblrApplication.class.getClassLoader().getResourceAsStream("server.properties");
      properties = new Properties();
      properties.load(stream);
      stream.close();
      return properties;
    } catch (Exception e) {
      LOGGER.error("properties not found -> will exit! ",e);
      System.exit(0);
      return null;
    }
  }

  public static Connection openPostgresConnection(){
    Properties server = PhumblrApplication.getProperties();

    String host = server.getProperty("pg_host");
    String port = server.getProperty("pg_port");
    String db = server.getProperty("pg_db");
    String user = server.getProperty("pg_user");
    String pw = server.getProperty("pg_pass");

    try {
      return DriverManager.getConnection(
          "jdbc:postgresql://"+host+":"+port+"/"+db, user, pw);
    } catch (SQLException e) {
      LOGGER.error("unable to open connection! -> will exit! ",e);
      System.exit(0);
      return null;
    }

  }

  public static void main(String[] args) throws Exception {
    SpringApplication.run(PhumblrHttpController.class, args);
    Class.forName("org.postgresql.Driver");

    Connection conn = PhumblrApplication.openPostgresConnection();
    LOGGER.info("Connection check: "+(!conn.isClosed()));
  }

}
