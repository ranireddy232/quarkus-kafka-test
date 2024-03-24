package Consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.util.Date;

@ApplicationScoped
public class DataConsumer {

    //private final Logger logger = new JsonLogger(LoggerFactory.getLogger(DataConsumer.class));

    @Incoming("data-input")
    public void receive(String message) throws JsonProcessingException {
        System.out.println(" Inside the Consumer");
        //logger.info("Got a msg: " + message);
        if(message.indexOf("-") != -1) {
            /*Skipping the last string after '-' to get the second to last string */
            String firstSubString = message.substring(0, message.lastIndexOf('-'));
            if(firstSubString.indexOf("-") != -1) {

                /*Extracting the last string to get the required second to last from original string */
                String secondLastSubString = firstSubString.substring(firstSubString.lastIndexOf('-') + 1);

                /*Stripping the characters in the extracted second to last string */
                String onlyDigitsString = secondLastSubString.replaceAll("[^0-9]", "");

                /*Convert from string to integer */
                Integer extractedIntegerValue = Integer.parseInt(onlyDigitsString);

            //Verifying the extracted integer values is even or not
                if (extractedIntegerValue % 2 == 0) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    String value = objectMapper.writeValueAsString(new DataOutput(message, new Date()));
                    System.out.println("value ---"+value);
//               return value;
                }
            }
        }
    }
}
