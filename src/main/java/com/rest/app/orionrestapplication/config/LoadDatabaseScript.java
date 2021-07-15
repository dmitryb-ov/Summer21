package com.rest.app.orionrestapplication.config;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@EnableScheduling
@EnableAsync
@Log4j
public class LoadDatabaseScript {

    private final DataSource dataSource;

    @Autowired
    public LoadDatabaseScript(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Async
    @Scheduled(cron = "0 */5 * ? * *", zone = "Europe/Moscow")
    public void loadDeleteOldPostsScript() {
        var resourceDatabasePopulator = new ResourceDatabasePopulator(
                false,
                false,
                "UTF-8",
                new ClassPathResource("schedule_del.sql")
        );
        resourceDatabasePopulator.execute(dataSource);
        log.info("Run check old posts");
    }
}
