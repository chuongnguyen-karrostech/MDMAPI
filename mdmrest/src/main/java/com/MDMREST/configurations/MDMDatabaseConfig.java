package com.MDMREST.configurations;

import java.util.HashMap;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@PropertySource({ "classpath:database.properties" })
@EnableJpaRepositories(basePackages = {
		"com.MDMREST.dao.mdm" }, entityManagerFactoryRef = "mdmEntityManager", transactionManagerRef = "mdmTransactionManager")
public class MDMDatabaseConfig {

	private static final Logger log = LoggerFactory.getLogger(MDMDatabaseConfig.class);

	@Autowired
	private Environment env;

	@Bean
	public LocalContainerEntityManagerFactoryBean mdmEntityManager() {

		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(mdmDataSource());
		em.setPackagesToScan(new String[] { "com.MDMREST.entity.mdm" });

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);

		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.dialect", env.getProperty("mdm.hibernate.dialect"));

		em.setJpaPropertyMap(properties);

		return em;
	}

	@Bean
	public DataSource mdmDataSource() {

		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		try {

			dataSource.setDriverClassName(env.getProperty("mdm.jdbc.driverClassName"));
			dataSource.setUrl(env.getProperty("mdm.jdbc.url"));
			dataSource.setUsername(env.getProperty("mdm.jdbc.user"));
			dataSource.setPassword(env.getProperty("mdm.jdbc.pass"));
			
			log.info(String.format("mdm.jdbc.url = %s",dataSource.getUrl()));
			log.info(String.format("mdm.jdbc.user = %s",dataSource.getUsername()));
			log.info(String.format("mdm.jdbc.pass = %s",dataSource.getPassword()));			
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);			
		}

		return dataSource;
	}

	@Bean
	public PlatformTransactionManager mdmTransactionManager() {

		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(mdmEntityManager().getObject());
		return transactionManager;
	}
}
