package com.xoriant.delivery.exception;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputUserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	// always declare filed with private and final in exception class.
	private String errorMsg;

	private String errorCode;

}
