package pt.vstore.stock;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication
public class ApplicationStartup {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationStartup.class, args);

		final String homePage = "http://localhost:8080/swagger-ui.html";
		final Logger logger = LoggerFactory.getLogger(ApplicationStartup.class);
		logger.info("HomePage: " + homePage);

		if (isDebug())
			openHomePage(homePage);
	}

	@Bean
	public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
		final String title = "VStore Stock API";

		License license = new License()
				.name("Apache 2.0")
				.url("http://springdoc.org");

		Info info = new Info()
				.title(title)
				.version(appVersion)
				.description("OpenAPI 3 for " + title + ".")
				.termsOfService("http://swagger.io/terms/")
				.license(license);

		return new OpenAPI().info(info);
	}


	private static void openHomePage(final String url) {
		try {
			Runtime rt = Runtime.getRuntime();
			rt.exec("cmd /c start chrome.exe " + url);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static boolean isDebug() {
		return java.lang.management.ManagementFactory.getRuntimeMXBean()
				.getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;
	}

}
