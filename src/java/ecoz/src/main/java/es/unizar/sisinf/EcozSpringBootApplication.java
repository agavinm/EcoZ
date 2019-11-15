/**
 * @file	EcozSpringBootApplication.java
 * @author	Andrés Gavín Murillo 716358
 * @author	Eduardo Gimeno Soriano 721615
 * @author	Sergio Álvarez Peiro 740241
 * @date	Noviembre 2019
 * @coms	Sistemas de información - Práctica 3
 */

package es.unizar.sisinf;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("es.unizar.sisinf.control")
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class EcozSpringBootApplication {

	public static void main(String[] args) {
        SpringApplication app = new SpringApplication(EcozSpringBootApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8083"));
        app.run(args);
	}
}
