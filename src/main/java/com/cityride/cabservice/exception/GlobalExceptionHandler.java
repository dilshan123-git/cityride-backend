
package com.cityride.cabservice.exception;


import com.cityride.cabservice.dto.CommonResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<CommonResponseDto<Void>> handleResponseStatusException(
            ResponseStatusException ex) {

        CommonResponseDto<Void> response =
                CommonResponseDto.error(ex.getReason());

        return ResponseEntity
                .status(ex.getStatusCode())
                .body(response);
    }
}
