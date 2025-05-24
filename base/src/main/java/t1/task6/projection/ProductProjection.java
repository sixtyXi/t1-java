package t1.task6.projection;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.math.BigDecimal;

public interface ProductProjection {

    Long getId();

    String getAccountNumber();

    @JsonSerialize(using = ToStringSerializer.class)
    BigDecimal getBalance();

    String getType();

    Long getUserId();
}
