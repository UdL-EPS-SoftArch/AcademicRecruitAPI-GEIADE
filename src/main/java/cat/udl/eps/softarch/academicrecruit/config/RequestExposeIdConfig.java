package cat.udl.eps.softarch.academicrecruit.config;


import cat.udl.eps.softarch.academicrecruit.domain.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
class RequestExposeIdConfig {

    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer() {

        return new RepositoryRestConfigurer() {

            @Override
            public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
                config.exposeIdsFor(Document.class);
                config.exposeIdsFor(SelectionProcess.class);
                config.exposeIdsFor(ProcessStage.class);
                config.exposeIdsFor(Participant.class);
                config.exposeIdsFor(Candidate.class);
            }
        };
    }
}
