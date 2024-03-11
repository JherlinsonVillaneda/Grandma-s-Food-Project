package restaurant.GrandmasFood.exception;

import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorInfo {
    private String code;
    private String timestamp;
    private String description;
    private String exception;
}
