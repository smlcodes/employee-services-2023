package com.employee;

import com.employee.constants.DepartmentEnum;
import com.employee.patterns.factory.ReportTypeEnum;
import com.employee.patterns.factory.ReportWriter;
import com.employee.patterns.factory.ReportWriterFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

/**
 * Main Application class
 *
 * @author Satya
 */
@SpringBootApplication
@Configuration
@Slf4j
@EnableJpaAuditing
@EnableEnversRepositories
@EnableJpaRepositories(basePackages = {"com.employee"}, repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
@EnableScheduling
@EnableCaching
public class Application {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setBannerMode(Banner.Mode.OFF);

        // Start Application
        app.run(args);
    }

    @EventListener(ApplicationReadyEvent.class)
    void onApplicationReady(ApplicationReadyEvent event) {
        Environment env = event.getApplicationContext().getEnvironment();
        log.info("Application Ready!! \n\tListening on address: {} and port: {} \n\tProfiles: {}",
                env.getProperty("server.address"),
                env.getProperty("server.port"),
                env.getActiveProfiles()
        );

        log.info("\n Swagger : http://localhost:" + env.getProperty("server.port") + "/empapp/swagger-ui/index.html");
        log.info("Basic base64(username:password) :dXNlcjpwYXNzd29yZA==");
    }

	@PostConstruct
	void afterStartup(){
		log.info("\n \nAfter StartUp");

        //Checking Enum
        enumChecking();
        factoryPatternCheck();

	}

    public static void enumChecking(){
        log.info("Printing Enums");
        log.info("CSE : "+DepartmentEnum.CSE.getCode()+", Value: "+DepartmentEnum.CSE.getLabel());
        log.info("ECE : "+DepartmentEnum.ECE.getCode()+", Value: "+DepartmentEnum.ECE.getLabel());
        log.info("Value by code MBBS: "+DepartmentEnum.getDepartmentByCode("MBBS"));

        DepartmentEnum.CSE.print();
        DepartmentEnum.MBBS.print();
    }


    public static void factoryPatternCheck() {

        log.info("\n \n \n factoryPatternCheck \n *********************");
        ReportWriter pdfReportWriter = ReportWriterFactory.createReportWriter(ReportTypeEnum.PDF);
        pdfReportWriter.writeReport();

        ReportWriter xmlReportWriter = ReportWriterFactory.createReportWriter(ReportTypeEnum.XML);
        xmlReportWriter.writeReport();

        ReportWriter wordReportWriter = ReportWriterFactory.createReportWriter(ReportTypeEnum.WORD);
        wordReportWriter.writeReport();
        log.info("\n  factoryPatternCheck \n \n \n *********************");
    }


}
