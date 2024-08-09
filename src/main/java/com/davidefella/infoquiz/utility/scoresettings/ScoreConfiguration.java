package com.davidefella.infoquiz.utility.scoresettings;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "com.infoquiz")
public class ScoreConfiguration {

    private float penaltyPoints;
    private float bonusPoints;
    private float notAnsweredPoints;
}
