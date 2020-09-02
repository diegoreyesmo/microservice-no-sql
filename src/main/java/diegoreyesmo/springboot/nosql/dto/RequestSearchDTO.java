package diegoreyesmo.springboot.nosql.dto;

import java.io.Serializable;
import java.util.List;

import org.bson.Document;

import lombok.Data;

@Data
public class RequestSearchDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Document query;
	private List<String> fields;
	private List<String> asc;
	private List<String> desc;
	private boolean count;
	private Integer pageNum;
	private Integer pageSize;

}
