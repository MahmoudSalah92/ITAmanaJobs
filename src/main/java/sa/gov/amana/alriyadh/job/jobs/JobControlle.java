package sa.gov.amana.alriyadh.job.jobs;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sa.gov.amana.alriyadh.job.utils.DynamicPropertyUpdater;

import java.util.HashMap;
import java.util.Map;

@RestController
public class JobControlle {

    @Autowired
    DynamicPropertyUpdater updater;

    @PostMapping("resetProperty")
    public ResponseEntity<String> resetProperty(@RequestBody Object request){
        Map<String, Object> resultSet = new HashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> datafields = mapper.convertValue(request, new TypeReference<Map<String, Object>>() {});
        if(datafields != null && !datafields.isEmpty() && datafields.get("propertyName") != null
                && datafields.get("propertyValue") != null ){
            updater.updateProperty((String) datafields.get("propertyName"), (String) datafields.get("propertyValue"));
            return ResponseEntity.ok().body("API property update done successfully.");
        }else{
            return ResponseEntity.badRequest().body("API property update fail.");
        }
    }
}
