package com.example.evolutioncodetest;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.LinkedHashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class EvolutionCodeTestApplication {

	@GetMapping
	public Map<String, String> getRoutes(HttpServletRequest request){
		var baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
				.replacePath(null).build().toUriString();
		var routes = new LinkedHashMap<String, String>();
		routes.put("tasks", baseUrl + "/tasks");
		return routes;
	}

	public static void main(String[] args) {
		SpringApplication.run(EvolutionCodeTestApplication.class, args);
	}

}
