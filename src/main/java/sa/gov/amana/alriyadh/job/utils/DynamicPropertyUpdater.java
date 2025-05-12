package sa.gov.amana.alriyadh.job.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class DynamicPropertyUpdater {

//    @Value("${my.property}")
//    private String myProperty;

    //private final ApplicationContext context;

    @Autowired
    private ConfigurableApplicationContext context;

//    public DynamicPropertyUpdater(ApplicationContext context) {
//        this.context = context;
//    }

    public void updateProperty(String property,String newValue) {
        System.setProperty(property, newValue);
        // Refresh the application context (be cautious of side effects).
        //((ConfigurableApplicationContext) context).refresh();
        context.refresh();
    }
}
