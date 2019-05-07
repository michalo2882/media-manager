package mediamanager.component;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Log4j2
public class EnvironmentReport {

    @Autowired
    Environment environment;

    @PostConstruct
    void logReport() {
        String[] activeProfiles = environment.getActiveProfiles();
        if (activeProfiles.length == 0) {
            log.info("No active profiles");
        }
        else {
            log.info("Active profiles:");
            for (String profile : activeProfiles) {
                log.info("  " + profile);
            }
        }
    }
}
