package pasarela.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;


@SpringBootApplication
@EnableZuulProxy
public class PasarelaZuulApplication {

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