package com.santech.mtm.swagger;


import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

@ApiResponse(
        responseCode = "500",
        description = "Internal server error",
        content = @Content(
                mediaType = "application/json",
                examples = {
                        @ExampleObject(
                                name = "Unchecked runtime exception",
                                value = "500 Internal Server Error at: {exceptionMessage}"
                        ),
                        @ExampleObject(
                                name = "Unhandled exception",
                                value = "500 Internal Server Error at: {exceptionMessage}"
                        )
                }
        )
)
@Target(METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InternalServerErrorResponse { }