package t1.task8.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigDecimal;

@ConfigurationProperties(prefix = "limits")
@Getter
@RequiredArgsConstructor
public class LimitsProperties {

    private final BigDecimal maxAmount;
}
