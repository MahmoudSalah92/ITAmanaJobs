package sa.gov.amana.alriyadh.job.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

//@Configuration
public class ResourceServerEndpointConfig extends ResourceServerConfigurerAdapter {
	
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/serv/api/common/getEmployeeJobTitle").hasAuthority("ADMIN_ROLE");
    }

    @Bean
    public ResourceServerTokenServices tokenService() {
        RemoteTokenServices tokenServices = new RemoteTokenServices();
        tokenServices.setClientId("admin@pixeltrice.com");
        tokenServices.setClientSecret("$2a$08$fL7u5xcvsZl78su29x1ti.dxI.9rYO8t0q5wk2ROJ.1cdR53bmaVG");
        tokenServices.setCheckTokenEndpointUrl("http://localhost:8989/oauth/check_token");
        return tokenServices;
    }
}
