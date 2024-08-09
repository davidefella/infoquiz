package com.davidefella.infoquiz.utility;

import com.davidefella.infoquiz.model.persistence.*;
import com.davidefella.infoquiz.service.*;
import com.davidefella.infoquiz.utility.datafactory.DummyDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Profile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Profile("dev")
@Component
public class StartupDataLoader implements CommandLineRunner {

    @Autowired
    private DummyDataFactory dummyDataFactory;

    private static final Logger logger = LoggerFactory.getLogger(StartupDataLoader.class);

    @Override
    public void run(String... args) throws Exception {
        logger.info("*** DATA LOADING: START ***");

        dummyDataFactory.loadAllDummyData();

        logger.info("*** DATA LOADING: END ***");
    }
}
