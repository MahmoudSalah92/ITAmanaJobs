package sa.gov.amana.alriyadh.job.clients.retrofit.interceptor;

import lombok.Generated;
import okhttp3.*;
import okio.Buffer;
import okio.BufferedSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

@Component
public class ExternalPartiesInterceptor implements Interceptor {

    @Generated
    private static final Logger log = LoggerFactory.getLogger(ExternalPartiesInterceptor.class);
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static final long peekBodySize = Long.MAX_VALUE;

    /*@Autowired
    private LoggingService loggingService;
    @Autowired
    private LogMapper logMapper;*/

    public ExternalPartiesInterceptor() {
    }

    public Response intercept(Interceptor.Chain chain) throws IOException {
        String httpRequestId = null;
        String loginUsername = null;
        String loginIdentityId = null;
        String loginIdentityType = null;
        ContentCachingRequestWrapper wrapper = null;
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        if (ra != null && ra instanceof ServletRequestAttributes) {
            HttpServletRequest httpRequest = ((ServletRequestAttributes)ra).getRequest();
            wrapper = new ContentCachingRequestWrapper(httpRequest);
            httpRequestId = (String)httpRequest.getAttribute("http-request-id");
            loginUsername = httpRequest.getHeader("loginUsername");
            loginIdentityId = httpRequest.getHeader("loginIdentityId");
            loginIdentityType = httpRequest.getHeader("loginIdentityType");
        }

        Request request = chain.request();
        /*LoggingDto logDto = this.logMapper.createClientsLoggingData(request, wrapper);
        logDto.setUsername(loginUsername);
        logDto.setIdentityNumber(loginIdentityId);
        logDto.setIdentityTypeId(IntegerValidator.getInstance().validate(loginIdentityType));
        logDto.setRequestMethod(request.method());
        logDto.setHttpRequestId(httpRequestId);*/
        long startNs = System.nanoTime();

        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            log.error(" exception: {}", e.getMessage());
            /*logDto.setException(Utils.getStackTrace(e));
            logDto.setResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            this.loggingService.addLog(logDto);*/
            throw e;
        }

        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
        /*logDto.setResponseStatus(response.code());
        if (response.code() != HttpStatus.OK.value() && response.code() != HttpStatus.ACCEPTED.value()) {
            logDto.setBusinessError(true);
            logDto.setFailedResponseBody(getResponseBody(response));
        }

        logDto.setExecutionTime((float)tookMs / 1000.0F);
        this.loggingService.addLog(logDto);*/
        return response;
    }

    private static String getResponseBody(final Response response) {
        try {
            ResponseBody responseBody = response.body();
            long contentLength = responseBody.contentLength();
            if (hasBody(response)) {
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE);
                Buffer buffer = source.getBuffer();
                Charset charset = UTF8;
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }

                if (isPlaintext(buffer) && contentLength != 0L) {
                    return buffer.clone().readString(charset);
                }
            }
        } catch (Exception e) {
            log.error("exception in getResponseBody: {}", e);
        }

        return null;
    }

    static boolean hasBody(Response response) {
        if (response.request().method().equals("HEAD")) {
            return false;
        } else {
            int responseCode = response.code();
            if ((responseCode < 100 || responseCode >= 200) && responseCode != 204 && responseCode != 304) {
                return true;
            } else {
                return contentLength(response) != -1L || "chunked".equalsIgnoreCase(response.header("Transfer-Encoding"));
            }
        }
    }

    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64L ? buffer.size() : 64L;
            buffer.copyTo(prefix, 0L, byteCount);

            for(int i = 0; i < 16 && !prefix.exhausted(); ++i) {
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }

            return true;
        } catch (EOFException var6) {
            return false;
        }
    }

    static long contentLength(Response response) {
        return contentLength(response.headers());
    }

    static long contentLength(Headers headers) {
        return stringToLong(headers.get("Content-Length"));
    }

    private static long stringToLong(String s) {
        if (s == null) {
            return -1L;
        } else {
            try {
                return Long.parseLong(s);
            } catch (NumberFormatException var2) {
                return -1L;
            }
        }
    }

}

