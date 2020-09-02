package diegoreyesmo.springboot.nosql.controller;

import static diegoreyesmo.springboot.nosql.util.Constant.STATUS_FAIL;
import static diegoreyesmo.springboot.nosql.util.Constant.STATUS_OK;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import diegoreyesmo.springboot.nosql.dto.DatabaseDTO;
import diegoreyesmo.springboot.nosql.dto.RequestCreateDTO;
import diegoreyesmo.springboot.nosql.dto.RequestSearchDTO;
import diegoreyesmo.springboot.nosql.dto.RequestUpdateDTO;
import diegoreyesmo.springboot.nosql.dto.ResponseDTO;
import diegoreyesmo.springboot.nosql.service.NoSqlService;
import lombok.extern.java.Log;

@Log
@RestController
public class NoSqlController {
	@Autowired
	NoSqlService service;

	@PostMapping(path = "/{database}/{collections}")
	public HttpEntity<ResponseDTO> create(@RequestBody RequestCreateDTO requestCreateDTO, @PathVariable String database,
			@PathVariable String collections) {
		if (requestCreateDTO.getNewDocuments() == null) {
			return new ResponseEntity<>(ResponseDTO.builder().status(STATUS_FAIL)
					.message("Debe especificar el campo 'new-documents'").build(), HttpStatus.BAD_REQUEST);
		}
		DatabaseDTO databaseDTO = DatabaseDTO.builder().name(database).collections(collections).build();
		log.info("[create]" + databaseDTO.toString() + "requestCreateDTO:" + requestCreateDTO.toString());
		ResponseDTO result = service.create(databaseDTO, requestCreateDTO);
		String status = result.getStatus();
		if (status != null && status.equals(STATUS_OK)) {
			return new ResponseEntity<>(result, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping(path = "/{database}/{collections}/{id}")
	public HttpEntity<ResponseDTO> read(@PathVariable String database, @PathVariable String collections,
			@PathVariable String id) {
		DatabaseDTO databaseDTO = DatabaseDTO.builder().name(database).collections(collections).build();
		log.info("[read]" + databaseDTO.toString() + "id:" + id);
		return new HttpEntity<>(service.read(databaseDTO, id));
	}

	@PutMapping(path = "/{database}/{collections}/{id}")
	public HttpEntity<ResponseDTO> update(@RequestBody RequestUpdateDTO requestUpdateDTO, @PathVariable String database,
			@PathVariable String collections, @PathVariable String id) {
		if (requestUpdateDTO.getUpdatedDocument() == null) {
			return new ResponseEntity<>(ResponseDTO.builder().status(STATUS_FAIL)
					.message("Debe especificar el campo 'updated-document'").build(), HttpStatus.BAD_REQUEST);
		}
		DatabaseDTO databaseDTO = DatabaseDTO.builder().name(database).collections(collections).build();
		log.info("[update]" + databaseDTO.toString() + "requestUpdateDTO:" + requestUpdateDTO.toString() + "id:" + id);
		return new HttpEntity<>(service.update(databaseDTO, requestUpdateDTO, id));
	}

	@DeleteMapping(path = "/{database}/{collections}/{id}")
	public HttpEntity<ResponseDTO> delete(@PathVariable String database, @PathVariable String collections,
			@PathVariable String id) {
		DatabaseDTO databaseDTO = DatabaseDTO.builder().name(database).collections(collections).build();
		log.info("[delete]" + databaseDTO.toString() + "id:" + id);
		ResponseDTO result = service.delete(databaseDTO, id);
		String status = result.getStatus();
		if (status != null && status.equals(STATUS_OK)) {
			return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
	}

	@PostMapping(value = "/{database}/{collections}/search")
	public HttpEntity<ResponseDTO> search(@RequestBody RequestSearchDTO requestSearchDTO, @PathVariable String database,
			@PathVariable String collections) {
		if (requestSearchDTO.getQuery() == null) {
			return new ResponseEntity<>(
					ResponseDTO.builder().status(STATUS_FAIL).message("Debe especificar el campo 'query'").build(),
					HttpStatus.BAD_REQUEST);
		}
		DatabaseDTO databaseDTO = DatabaseDTO.builder().name(database).collections(collections).build();
		ResponseDTO result = service.search(databaseDTO, requestSearchDTO);
		String status = result.getStatus();
		if (status != null && status.equals(STATUS_OK)) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
