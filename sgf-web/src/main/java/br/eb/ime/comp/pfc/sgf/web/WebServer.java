package br.eb.ime.comp.pfc.sgf.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import br.eb.ime.comp.pfc.sgf.web.controller.WebController;
import br.eb.ime.comp.pfc.sgf.web.service.AlunoService;

@EnableAutoConfiguration
// O Spring também procura Beans nos packages das classes informadas
@ComponentScan(basePackageClasses = { WebController.class, AlunoService.class })
@EnableDiscoveryClient
@Import({SecurityConfig.class})
public class WebServer extends WebMvcConfigurerAdapter{

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "application");
		
		if(args.length == 1){
			try{
				Integer.parseInt(args[0]);
				System.setProperty("server.port", args[0]);
			}catch (Exception e) {

			}			
		}
		
		SpringApplication.run(WebServer.class, args);
	}

	@LoadBalanced
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	UserDetailsService userDetailService(){
		return new SGFUserDetailService();
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
	}
	
}
