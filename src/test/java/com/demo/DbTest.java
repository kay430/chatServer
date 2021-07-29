package com.demo;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DbTest {
	
	Logger log = LoggerFactory.getLogger(DbTest.class);
	
	@Autowired
	DataSource dataSource;
	
	@Test
	public void connection() throws SQLException {
		try(Connection connection = dataSource.getConnection()) {
			DatabaseMetaData metaData = connection.getMetaData();
			log.info("URL: " + metaData.getURL());
			log.info("DriverName: " + metaData.getDriverName());
			log.info("UserName: " + metaData.getUserName());
		}
	}

}
