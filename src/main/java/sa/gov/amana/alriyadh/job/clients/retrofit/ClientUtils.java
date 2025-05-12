package sa.gov.amana.alriyadh.job.clients.retrofit;

import lombok.Generated;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import sa.gov.amana.alriyadh.job.clients.retrofit.interceptor.ExternalPartiesInterceptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class ClientUtils {

    @Generated
    private static final Logger log = LoggerFactory.getLogger(ClientUtils.class);

    @Autowired
    private ExternalPartiesInterceptor externalPartiesInterceptor;


    public <T> T createApiGetwayClient(String baseUrl, Class<T> clientClass, Interceptor... interceptors) {

        List<Interceptor> interceptorList = new ArrayList<>();
        interceptorList.add(this.externalPartiesInterceptor);
        if (interceptors != null && interceptors.length > 0)
            interceptorList.addAll(Arrays.asList(interceptors));
        OkHttpClient.Builder httpClient = createHttpClientBuilder(interceptorList);

        Retrofit retrofit = (new Retrofit.Builder()).baseUrl(baseUrl).addConverterFactory(JacksonConverterFactory.create()).client(httpClient.build()).build();

        return createClient(retrofit, clientClass);
    }

    public OkHttpClient.Builder createHttpClientBuilder(List<Interceptor> interceptors) {
        log.info("adding interceptors: {} ", Arrays.toString(interceptors.toArray()));
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(20L, TimeUnit.SECONDS)
                .writeTimeout(20L, TimeUnit.SECONDS)
                .readTimeout(60L, TimeUnit.SECONDS);
        if (CollectionUtils.isNotEmpty(interceptors))
            for (Interceptor interceptor : interceptors)
                httpClient.addInterceptor(interceptor);
        return httpClient;
    }

    private <T> T createClient(Retrofit retrofit, Class<T> clientClass) {
        log.info("creating client {}", clientClass.getName());
        return (T) retrofit.create(clientClass);
    }

}
