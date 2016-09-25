package com.meghajindal.assignment.kalah.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.meghajindal.assignment.kalah.cache.GameCacheRepository;
import com.meghajindal.assignment.kalah.validation.GameValidator;

@Profile("test")
@Configuration
public class GameServiceTestConfiguration {

	 @Bean
	    @Primary
	    public GameCacheRepository repo() {
	        return Mockito.mock(GameCacheRepository.class,Mockito.CALLS_REAL_METHODS);
	    }
	
	 
	   
	   @Bean
	    @Primary
	    public GameValidator gameValidator() {
	        return Mockito.mock(GameValidator.class);
	    }
	
	
	  
}
