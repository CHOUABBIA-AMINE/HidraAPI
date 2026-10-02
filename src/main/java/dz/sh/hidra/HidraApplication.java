/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HidraApplication
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Bootstrap
 * @Module      : bootstrap
 * @Package     : dz.sh.hidra
 *
 * @Description : Spring Boot entry point for the HidraAPI backend platform.
 *
 */
package dz.sh.hidra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HidraApplication {

	public static void main(String[] args) {
		SpringApplication.run(HidraApplication.class, args);
	}

}
