package np.com.roshanadhikary.testdemo.bootstrap;

import np.com.roshanadhikary.testdemo.entity.Battery;
import np.com.roshanadhikary.testdemo.repository.BatteryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Bootstrap the in-memory H2 database with some Battery resources when
 * the application starts
 */
@Configuration
public class H2Bootstrap {

    private static final Logger logger = LoggerFactory.getLogger(H2Bootstrap.class);

    public static final List<Battery> mockBatteries = List.of(
            new Battery("Battery Loc 1", String.valueOf(1010), 20500),
            new Battery("Battery Loc 2", String.valueOf(1020), 20000),
            new Battery("Battery Loc 3", String.valueOf(1040), 30000),
            new Battery("Battery Loc 4", String.valueOf(1060), 30500)
    );

    @Bean
    CommandLineRunner initDb(BatteryRepository repository) {
        return args -> mockBatteries.forEach(battery ->
                logger.info(String.format("Preloading %s", repository.save(battery)))
        );
    }
}
