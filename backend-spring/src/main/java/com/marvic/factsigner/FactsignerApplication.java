package com.marvic.factsigner;

import com.marvic.factsigner.model.sistema.Cliente;
import com.marvic.factsigner.payload.sistema.ClienteDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@SpringBootApplication( exclude={DataSourceAutoConfiguration.class} )
public class FactsignerApplication {

//	@Value("${spring.profiles.active:}")
//	private String activeProfiles;
//
//	@PostConstruct
//	public void afterStartup() {
//		System.out.println("*************** PROFILE: " + activeProfiles + " ****************");
//	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();

		// Skip cliente ID
//		TypeMap<ClienteDTO, Cliente> propertyMapper = mapper.createTypeMap(ClienteDTO.class, Cliente.class);
//		propertyMapper.addMappings(expression -> expression.skip(Cliente::setId));



		return mapper;
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
