package org.springframework.cloud.servicebroker.mongodb.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.servicebroker.model.Catalog;
import org.springframework.cloud.servicebroker.model.Plan;
import org.springframework.cloud.servicebroker.model.ServiceDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CatalogConfig {
	
	@Bean
	public Catalog catalog() {
		return new Catalog(Collections.singletonList(
				new ServiceDefinition(
						getEnvOrDefault("SERVICE_ID","mongo-service-broker"), //env variable
						getEnvOrDefault("SERVICE_NAME","MongoDB"), //env variable
						"MongoDB service broker implementation for Pivotal Cloud Foundry",
						true,
						false,
						Collections.singletonList(
								new Plan(getEnvOrDefault("PLAN_ID","mongo-plan"), //env variable
										"standard",
										"This is a default MongoDB plan.",
										getPlanMetadata(),
										true)),
						Arrays.asList("mongodb", "document"),
						getServiceDefinitionMetadata(),
						null,
						null)));
	}
	
/* Used by Pivotal CF console */

	private Map<String, Object> getServiceDefinitionMetadata() {
		Map<String, Object> sdMetadata = new HashMap<>();
		sdMetadata.put("displayName", "MongoDB");
		sdMetadata.put("imageUrl", "http://info.mongodb.com/rs/mongodb/images/MongoDB_Logo_Full.png");
		sdMetadata.put("longDescription", "MongoDB Service");
		sdMetadata.put("providerDisplayName", "Pivotal");
		sdMetadata.put("documentationUrl", "https://github.com/cf-platform-eng/mongodb-broker");
		sdMetadata.put("supportUrl", "https://github.com/svrc-pivotal/mongodb-broker");
		return sdMetadata;
	}
	
	private Map<String,Object> getPlanMetadata() {
		Map<String,Object> planMetadata = new HashMap<>();
		planMetadata.put("bullets", getBullets());
		return planMetadata;
	}


	
	private List<String> getBullets() {
		return Arrays.asList("Shared MongoDB server", 
				"Creates a new database instance on a shared MongoDB cluster", 
				"Contact your administrator for storage size options");
	}
	
	private String getEnvOrDefault(final String variable, final String defaultValue){
		String value = System.getenv(variable);
		if(value != null){
			return value;
		}
		else{
			return defaultValue;
		}
	}
	
}
