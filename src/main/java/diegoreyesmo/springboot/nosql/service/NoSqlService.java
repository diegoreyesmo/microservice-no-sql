package diegoreyesmo.springboot.nosql.service;

import diegoreyesmo.springboot.nosql.dto.DatabaseDTO;
import diegoreyesmo.springboot.nosql.dto.RequestCreateDTO;
import diegoreyesmo.springboot.nosql.dto.RequestSearchDTO;
import diegoreyesmo.springboot.nosql.dto.RequestUpdateDTO;
import diegoreyesmo.springboot.nosql.dto.ResponseDTO;

public interface NoSqlService {

	ResponseDTO search(DatabaseDTO databaseDTO, RequestSearchDTO requestSearchDTO);

	ResponseDTO create(DatabaseDTO databaseDTO, RequestCreateDTO requestCreateDTO);

	ResponseDTO read(DatabaseDTO databaseDTO, String id);

	ResponseDTO update(DatabaseDTO databaseDTO, RequestUpdateDTO requestUpdateDTO, String id);

	ResponseDTO delete(DatabaseDTO databaseDTO, String id);

}
