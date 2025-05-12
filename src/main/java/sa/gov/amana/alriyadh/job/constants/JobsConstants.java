package sa.gov.amana.alriyadh.job.constants;

public class JobsConstants {

    JobsConstants() {
    }

    /**
     * -------------------------------------------Gateway-------------------------------------------
     **/

    // TEST
    public static final String API_GATEWAY_BASE_URL_KEY = "https://sit-integration.apps.stg-intg.itamana.net/rrm/";
    public static final String API_GATEWAY_ENV = "sit/";
    public static final String API_GATEWAY_USERNAME_KEY = "b5b1c585bf1af82abbcea017fa5f1b46";
    public static final String API_GATEWAY_PASSWORD_KEY = "ccf5d1ea674c16b7deb1a2b1e4c4755e";

    // PROD
    //	public static final String API_GATEWAY_BASE_URL_KEY = "https://prod-integration.apps.prd-intg.itamana.net/rrm/";
    //	public static final String API_GATEWAY_ENV = "prod/";
    //	public static final String API_GATEWAY_USERNAME_KEY = "a2cf5f3d4a98579f07f9c3c1df6aaa5a";
    //	public static final String API_GATEWAY_PASSWORD_KEY = "5fc3c496aae2383bcf33999b432da3df";


    /**
     * ---------------------------------------------OSB---------------------------------------------
     **/

    // TEST
    public static final String ETISALAT_SMS_STATUS_URL = "http://rrmosbtst:40113/RRMengagexSMSRS/v1/RRMengagex/campaigns/";
    public static final String API_OSB_USERNAME_KEY = "apptest";
    public static final String API_OSB_PASSWORD_KEY = "user1234";

    // PROD
    // public static final String ETISALAT_SMS_STATUS_URL = "http://rmintegservice:40006/RRMengagexSMSRS/v1/RRMengagex/campaigns/";
    // public static final String API_OSB_USERNAME_KEY = "appsuser";
    // public static final String API_OSB_PASSWORD_KEY = "FPYGHGBbZarzXZ6L06pk";

}
