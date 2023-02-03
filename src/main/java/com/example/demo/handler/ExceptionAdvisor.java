package com.example.demo.handler;

import com.example.demo.controller.UserController;
import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 *
 */
//@Slf4j
@ControllerAdvice(basePackageClasses = UserController.class)   // 전역 설정을 위한 annotaion
//@EnableAutoConfiguration(exclude = {WebMvcAutoConfiguration.class})
public class ExceptionAdvisor extends ResponseEntityExceptionHandler {

    /**
     * 메서드 파라미터나 리턴 값에 문제 발생 시
     * @param ex
     * @return
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity constraintViolationException(ConstraintViolationException ex){

        Map<String, Object> errorList = new HashMap<>();
        ex.getConstraintViolations().forEach(error -> {
            Stream<Path.Node> stream = StreamSupport.stream(error.getPropertyPath().spliterator(), false);
            List<Path.Node> list = stream.collect(Collectors.toList());
            String field = list.get(list.size()-1).getName();
            String message = error.getMessage();
//            String invalidValue = error.getInvalidValue().toString();
            errorList.put(field, message);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorList);
    }


//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public final ResponseEntity<ErrorResponse> processValidationError(MethodArgumentNotValidException exception) {
//        BindingResult bindingResult = exception.getBindingResult();
//
//        ErrorResponse errorResponse = makeErrorResponse(exception.getBindingResult());
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//    }

//    /**
//     * 유효성 검사 예외 처리
//     * @param ex      the exception
//     * @param headers the headers to be written to the response
//     * @param status  the selected response status
//     * @param request the current request
//     * @return
//     */
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        BindingResult bindingResult = ex.getBindingResult();
//        ErrorResponse errorResponse = makeErrorResponse(ex.getBindingResult());
////        StringBuilder builder = new StringBuilder();
////        for (FieldError fieldError : bindingResult.getFieldErrors()) {
////            builder.append("");
////            builder.append(fieldError.getField());
////            builder.append("(은)는 ");
////            builder.append(fieldError.getDefaultMessage());
////        }
////        ErrorResponse errorResponse = makeErrorResponse(ex.getBindingResult());
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
////        return super.handleMethodArgumentNotValid(ex, headers, status, request);
//    }

    /**
     * 객체 상태가 메서드 호출을 처리하기에 적절하지 않을 시
     * @param exception
     * @return
     */
        @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> illegalError(IllegalStateException exception) {
        ErrorResponse errorResponse = new ErrorResponse(500, "잘못된 장소", "잘못된 장소입니다.");
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> allException(Exception exception) {
//        BindingResult bindingResult = exception.getBindingResult();
//
//        StringBuilder builder = new StringBuilder();
//        for (FieldError fieldError : bindingResult.getFieldErrors()) {
//            builder.append("");
//            builder.append(fieldError.getField());
//            builder.append("(은)는 ");
//            builder.append(fieldError.getDefaultMessage());
//        }
//        ErrorResponse errorResponse = new ErrorResponse(500, "전체 exception", "다시 접속");
//        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
//    }


    /**
     * error 메세지 목록
     * @param bindingResult
     * @return
     */
    private ErrorResponse makeErrorResponse(BindingResult bindingResult) {
        int status = 0;
        String message = "";
        String detail = "";

        if(bindingResult.hasErrors()){
            detail = bindingResult.getFieldError().getDefaultMessage();

            String bindResultCode = bindingResult.getFieldError().getCode();

            switch (bindResultCode){
                case "NotNull", "NotEmpty", "Pattern" :
                    status = ErrorCode.VAILD_ERROR.getStatus();
                    message = ErrorCode.VAILD_ERROR.getMessage();
                    break;
            }
        }
        return new ErrorResponse(status, message);
    }
}
