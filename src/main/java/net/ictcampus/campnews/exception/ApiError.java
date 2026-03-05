package net.ictcampus.campnews.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {

    private int status;
    private String message;
    private List<FieldValidationError> fieldErrors;

    @Getter @Setter
    @AllArgsConstructor
    public static class FieldValidationError {
        private String field;
        private String error;
    }

    public ApiError(int status, String message) {
        this.status = status;
        this.message = message;
    }

}
