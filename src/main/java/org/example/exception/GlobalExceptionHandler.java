package org.example.exception;

import org.example.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String FAILEDSTATUS = "Failed";

    @ExceptionHandler(DepartmentException.class)
    ResponseEntity<ErrorResponse> handleDepartmentException(DepartmentException ex){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(FAILEDSTATUS)
                .message(ex.getMessage())
                .build();
        return ResponseEntity.ok().body(errorResponse);
    }

    @ExceptionHandler(EmployeeException.class)
    ResponseEntity<ErrorResponse> handleDepartmentException(EmployeeException ex){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(FAILEDSTATUS)
                .message(ex.getMessage())
                .build();
        return ResponseEntity.ok().body(errorResponse);
    }

    @ExceptionHandler(AttendanceException.class)
    ResponseEntity<ErrorResponse> handleDepartmentException(AttendanceException ex){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(FAILEDSTATUS)
                .message(ex.getMessage())
                .build();
        return ResponseEntity.ok().body(errorResponse);
    }

    @ExceptionHandler(LeaveBalanceException.class)
    ResponseEntity<ErrorResponse> handleDepartmentException(LeaveBalanceException ex){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(FAILEDSTATUS)
                .message(ex.getMessage())
                .build();
        return ResponseEntity.ok().body(errorResponse);
    }

    @ExceptionHandler(LeaveRequestException.class)
    ResponseEntity<ErrorResponse> handleDepartmentException(LeaveRequestException ex){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(FAILEDSTATUS)
                .message(ex.getMessage())
                .build();
        return ResponseEntity.ok().body(errorResponse);
    }

    @ExceptionHandler(PayrollException.class)
    ResponseEntity<ErrorResponse> handleDepartmentException(PayrollException ex){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(FAILEDSTATUS)
                .message(ex.getMessage())
                .build();
        return ResponseEntity.ok().body(errorResponse);
    }

    @ExceptionHandler(UserException.class)
    ResponseEntity<ErrorResponse> handleDepartmentException(UserException ex){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(FAILEDSTATUS)
                .message(ex.getMessage())
                .build();
        return ResponseEntity.ok().body(errorResponse);
    }
}
