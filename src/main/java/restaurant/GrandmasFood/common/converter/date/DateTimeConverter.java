package restaurant.GrandmasFood.common.converter.date;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateTimeConverter {
   public String formatDateTimeToIso8601(LocalDateTime localDate){
       DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
       return localDate.format(dateTimeFormatter);
   }
}
