package com.uautso.sovs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author Banza Timoth
 *
 * @ May 5, 2022:7:33:59 PM
 */

@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
public class CustomCorsConfiguration {
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		config.setMaxAge((long) 864000);
		source.registerCorsConfiguration("/**", config);

		return new CorsFilter(source);
	}

}
