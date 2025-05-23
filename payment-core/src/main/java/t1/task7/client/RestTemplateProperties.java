package t1.task7.client;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Duration;

@Getter
@Setter
@RequiredArgsConstructor
public class RestTemplateProperties {

    public String url;
    public Duration connectTimeout;
    public Duration readTimeout;

}
