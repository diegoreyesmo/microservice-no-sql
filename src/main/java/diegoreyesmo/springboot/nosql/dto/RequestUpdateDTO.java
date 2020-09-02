package diegoreyesmo.springboot.nosql.dto;

import java.io.Serializable;

import org.bson.Document;

import lombok.Data;

@Data
public class RequestUpdateDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Document updatedDocument;

}
