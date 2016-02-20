package org.wherecamp.hackathon.phumblr.service;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.descriptor.web.ContextResource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiObjectFactoryBean;

import javax.naming.NamingException;
import javax.sql.DataSource;


/**
 * Created by danielt on 26.11.15.
 */
@SpringBootApplication
public class PhumblrApplication {

    private final static Logger LOGGER = Logger.getLogger(PhumblrApplication.class);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(PhumblrHttpController.class, args);
    }


    @Bean
    public TomcatEmbeddedServletContainerFactory tomcatFactory() {
        return new TomcatEmbeddedServletContainerFactory() {

            @Value("${app.pg.host}")
            private String host;

            @Value("${app.pg.port}")
            private String port;

            @Value("${app.pg.db}")
            private String db;

            @Value("${app.pg.user}")
            private String user;

            @Value("${app.pg.pass}")
            private String password;

            @Override
            protected TomcatEmbeddedServletContainer getTomcatEmbeddedServletContainer(Tomcat tomcat) {
                tomcat.enableNaming();
                return super.getTomcatEmbeddedServletContainer(tomcat);
            }

            @Override
            protected void postProcessContext(Context context) {
                ContextResource resource = new ContextResource();
                resource.setName("jdbc/phumblr");
                resource.setType(DataSource.class.getName());
                resource.setProperty("driverClassName", "org.postgresql.Driver");
                resource.setProperty("url", "jdbc:postgresql://"+host+":"+port+"/"+db);
                resource.setProperty("username", user);
                resource.setProperty("password", password);
                context.getNamingResources().addResource(resource);
            }
        };
    }

    @Bean(destroyMethod="")
    public DataSource jndiDataSource() throws IllegalArgumentException, NamingException {
        JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
        bean.setJndiName("java:comp/env/jdbc/phumblr");
        bean.setProxyInterface(DataSource.class);
        bean.setLookupOnStartup(false);
        bean.afterPropertiesSet();
        return (DataSource)bean.getObject();
    }
}
