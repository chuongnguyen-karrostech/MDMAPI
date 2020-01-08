package com.mdmdashboard.configurations;

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
		"com.mdmdashboard.dao" }, entityManagerFactoryRef = "mdmdashboardEntityManager", transactionManagerRef = "mdmdashboardTransactionManager")
public class MDMDashboardDBConfig {

	private static final Logger log = LoggerFactory.getLogger(MDMDashboardDBConfig.class);

	@Autowired
	private Environment env;

	@Bean
	public LocalContainerEntityManagerFactoryBean mdmdashboardEntityManager() {

		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(mdmdashboardDataSource());
		em.setPackagesToScan(new String[] { "com.mdmdashboard.entity" });

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);

		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.dialect", env.getProperty("mdmdashboard.hibernate.dialect"));

		em.setJpaPropertyMap(properties);

		return em;
	}

	@Bean
	public DataSource mdmdashboardDataSource() {

		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		try {

			dataSource.setDriverClassName(env.getProperty("mdmdashboard.jdbc.driverClassName"));
			dataSource.setUrl(env.getProperty("mdmdashboard.jdbc.url"));
			dataSource.setUsername(env.getProperty("mdmdashboard.jdbc.user"));
			dataSource.setPassword(env.getProperty("mdmdashboard.jdbc.pass"));
			
			log.info(String.format("mdmdashboard.jdbc.url = %s",dataSource.getUrl()));
			log.info(String.format("mdmdashboard.jdbc.user = %s",dataSource.getUsername()));
			log.info(String.format("mdmdashboard.jdbc.pass = %s",dataSource.getPassword()));			
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);			
		}

		return dataSource;
	}

	@Bean
	public PlatformTransactionManager mdmdashboardTransactionManager() {

		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(mdmdashboardEntityManager().getObject());
		return transactionManager;
	}
}
