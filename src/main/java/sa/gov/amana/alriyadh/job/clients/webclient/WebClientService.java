package sa.gov.amana.alriyadh.job.clients.webclient;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@AllArgsConstructor
@Service
public class WebClientService {

    private final WebClient webClient;


    // GET Request
    public Mono<String> createGet(String url, String tokenType, String authToken, Map<String, String> headers) {

        if (tokenType.equalsIgnoreCase("Bearer")) {
            return createGetWithBearerToken(url, authToken, headers);

        } else if (tokenType.equalsIgnoreCase("Basic")) {
            return createGetWithBasicToken(url, authToken, headers);

        } else {
            return createGetNoAuth(url, headers);
        }
    }

    // GET Request No Auth
    public Mono<String> createGetNoAuth(String url, Map<String, String> headers) {
        return webClient.get()
                .uri(url)
                .headers(httpHeaders -> headers.forEach(httpHeaders::set))
                .retrieve()
                .bodyToMono(String.class);
    }

    // GET Request With Bearer Token
    public Mono<String> createGetWithBearerToken(String url, String authToken, Map<String, String> headers) {
        return webClient.get()
                .uri(url)
                .header("Authorization", "Bearer %s" + authToken)
                .headers(httpHeaders -> headers.forEach(httpHeaders::set))
                .retrieve()
                .bodyToMono(String.class);
    }

    // GET Request With Basic Token
    public Mono<String> createGetWithBasicToken(String url, String authToken, Map<String, String> headers) {
        return webClient.get()
                .uri(url)
                .header("Authorization", "Basic %s" + authToken)
                .headers(httpHeaders -> headers.forEach(httpHeaders::set))
                .retrieve()
                .bodyToMono(String.class);
    }

    // POST Request
    public Mono<String> createPost(String url, String tokenType, String authToken, Object requestBody, Map<String, String> headers) {

        if (tokenType.equalsIgnoreCase("Bearer")) {
            return createPostWithBearerToken(url, authToken, headers, requestBody);

        } else if (tokenType.equalsIgnoreCase("Basic")) {
            return createPostWithBasicToken(url, authToken, headers, requestBody);

        } else {
            return createPostNoAuth(url, headers, requestBody);
        }

    }

    // POST Request No Auth
    public Mono<String> createPostNoAuth(String url, Map<String, String> headers, Object requestBody) {
        return webClient.post()
                .uri(url)
                .headers(httpHeaders -> headers.forEach(httpHeaders::set))
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class);

        //.bodyToFlux()
    }

    // POST Request With Bearer Token
    public Mono<String> createPostWithBearerToken(String url, String authToken, Map<String, String> headers, Object requestBody) {
        return webClient.post()
                .uri(url)
                .header("Authorization", "Bearer " + authToken)
                .headers(httpHeaders -> headers.forEach(httpHeaders::set))
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class);
    }

    // POST Request With Basic Token
    public Mono<String> createPostWithBasicToken(String url, String authToken, Map<String, String> headers, Object requestBody) {
        return webClient.post()
                .uri(url)
                .header("Authorization", "Basic " + authToken)
                .headers(httpHeaders -> headers.forEach(httpHeaders::set))
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class);
    }

}
