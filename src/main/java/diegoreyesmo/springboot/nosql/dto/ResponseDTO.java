package diegoreyesmo.springboot.nosql.dto;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String status;
	private Object message;
}
