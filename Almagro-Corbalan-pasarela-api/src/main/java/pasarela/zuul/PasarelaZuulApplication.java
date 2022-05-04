package pasarela.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;


@SpringBootApplication(exclude = {
		  MongoAutoConfiguration.class, 
		  MongoDataAutoConfiguration.class
		})
@EnableZuulProxy
public class PasarelaZuulApplication {

	// set JWT=eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIzNDU2Y2MyNC0xNjRhLTRjNjUtYTJiZS05M2UxYTJkMDY3YzQiLCJpc3MiOiJQYXNhcmVsYSBadXVsIiwiZXhwIjoxNjUxNTg0MzkyLCJzdWIiOiIyNjY4MTU1MiIsInVzdWFyaW8iOiJtYXJjb3NAdW0uZXMiLCJyb2wiOiJQUk9GRVNPUiJ9.tJ8I2aiyTcZxb8aTwUaxo-S78bRvs27XzFIedDFOtSc
	// curl -i -H "Authorization: Bearer %JWT%" http://localhost:8090/opiniones/localhost%3A8080%2Fapi%2Fciudades%2FLorca%2Fpuntos%2FCastillo_de_Lorca%2Faparcamientos%2F37.677385652447754.-1.7053383432526061
	
	public static void main(String[] args) {
        System.setProperty("org.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH", "true");
		SpringApplication.run(PasarelaZuulApplication.class, args);
	}
	

    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);    
        return firewall;
    }

	/*
	 * @Override public void configure(WebSecurity web) throws Exception {
	 * web.httpFirewall(allowUrlEncodedSlashHttpFirewall()); }
	 */
}