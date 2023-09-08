package com.marvic.factsigner;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@SpringBootApplication() // exclude={DataSourceAutoConfiguration.class} )
//@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
public class FactsignerApplication {

//	@Value("${spring.profiles.active:}")
//	private String activeProfiles;
//
//	@PostConstruct
//	public void afterStartup() {
//		out.println("*************** PROFILE: " + activeProfiles + " ****************");
//	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();

		// Skip cliente ID
		//		TypeMap<ClienteDTO, Cliente> propertyMapper = mapper.createTypeMap(ClienteDTO.class, Cliente.class);
		//		propertyMapper.addMappings(expression -> expression.skip(Cliente::setId));
		return mapper;
	}

	@Bean
	public static PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Autowired
	private DataSource dataSource;

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}

	public static void main(String[] args) {
		SpringApplication.run(FactsignerApplication.class, args);
	}

}
