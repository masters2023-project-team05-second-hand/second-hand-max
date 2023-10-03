package team05a.secondhand.errors.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import team05a.secondhand.errors.ErrorResponse;
import team05a.secondhand.errors.exception.BuyerIdAndMessageSenderIdNotSameException;
import team05a.secondhand.errors.exception.ChatRoomExistsException;
import team05a.secondhand.errors.exception.SellerIdAndBuyerIdSameException;

@Slf4j
@RestControllerAdvice
public class ChatExceptionHandler {

	@ExceptionHandler(BuyerIdAndMessageSenderIdNotSameException.class)
	public ResponseEntity<ErrorResponse> handleBuyerIdAndMessageSenderIdNotSameException(
		BuyerIdAndMessageSenderIdNotSameException e) {
		log.warn("BuyerIdAndMessageSenderIdNotSameException handling : {}", e.toString());

		ErrorResponse response = ErrorResponse.from(HttpStatus.BAD_REQUEST, e.getMessage());
		return ResponseEntity.status(response.getStatus())
			.body(response);
	}

	@ExceptionHandler(SellerIdAndBuyerIdSameException.class)
	public ResponseEntity<ErrorResponse> handleSellerIdAndBuyerIdSameException(SellerIdAndBuyerIdSameException e) {
		log.warn("SellerIdAndBuyerIdSameException handling : {}", e.toString());

		ErrorResponse response = ErrorResponse.from(HttpStatus.BAD_REQUEST, e.getMessage());
		return ResponseEntity.status(response.getStatus())
			.body(response);
	}

	@ExceptionHandler(ChatRoomExistsException.class)
	public ResponseEntity<ErrorResponse> handleChatRoomExistsException(ChatRoomExistsException e) {
		log.warn("ChatRoomExistsException handling : {}", e.toString());

		ErrorResponse response = ErrorResponse.from(HttpStatus.BAD_REQUEST, e.getMessage());
		return ResponseEntity.status(response.getStatus())
			.body(response);
	}
}
