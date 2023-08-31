package com.marvic.factsigner;

import com.marvic.factsigner.model.sistema.Cliente;
import com.marvic.factsigner.payload.sistema.ClienteDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FactsignerApplication {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();

		// Skip cliente ID
		TypeMap<ClienteDTO, Cliente> propertyMapper = mapper.createTypeMap(ClienteDTO.class, Cliente.class);
		propertyMapper.addMappings(expression -> expression.skip(Cliente::setId));

		return mapper;
	}

	public static void main(String[] args) {
		SpringApplication.run(FactsignerApplication.class, args);
	}

}
