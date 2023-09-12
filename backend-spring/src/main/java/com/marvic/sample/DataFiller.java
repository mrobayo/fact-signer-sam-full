package com.marvic.sample;

import com.marvic.factsigner.model.sistema.Cliente;
import ec.gob.sri.types.SriEnumIdentidad;
import net.datafaker.Faker;
import net.datafaker.providers.base.Address;
import net.datafaker.providers.base.Name;

import java.util.Locale;

public final class DataFiller {

    private static Faker faker = new Faker(new Locale("es"));

    public static Cliente getCliente() {
        Cliente c = new Cliente();
        Name name = faker.name();
        c.setNombres(name.firstName());
        c.setApellidos(name.lastName());
        c.setIdentidad(faker.idNumber().ssnValid());
        //c.setIdentidad( c.getIdentidad().substring(0, 20));
        c.setTipo(SriEnumIdentidad.CED);
        c.setBirthday(faker.date().birthday().toLocalDateTime().toLocalDate());
        Address address = faker.address();
        c.setCiudad(address.city());
        if (c.getCiudad().length() > 20) {
            c.setCiudad( c.getCiudad().substring(0, 20) );
        }
        c.setDireccion(address.streetAddress());
        c.setPais(address.countryCode());
        c.setTelefono(faker.phoneNumber().phoneNumberInternational());
        if (c.getTelefono().length() > 20) {
            c.setTelefono( c.getTelefono().substring(0, 20)  );
        }

        c.setEmail(c.getNombres().toLowerCase() + "@mail.com");
        c.setName(c.getNombres() + " " + c.getApellidos());
        return c;
    }

}
