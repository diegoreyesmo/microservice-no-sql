package diegoreyesmo.springboot.nosql.service.impl;

import static diegoreyesmo.springboot.nosql.util.Constant.COUNT_FIELD;
import static diegoreyesmo.springboot.nosql.util.Constant.STATUS_FAIL;
import static diegoreyesmo.springboot.nosql.util.Constant.STATUS_OK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;

import diegoreyesmo.springboot.nosql.configuration.MongoConfiguration;
import diegoreyesmo.springboot.nosql.dto.DatabaseDTO;
import diegoreyesmo.springboot.nosql.dto.RequestCreateDTO;
import diegoreyesmo.springboot.nosql.dto.RequestSearchDTO;
import diegoreyesmo.springboot.nosql.dto.RequestUpdateDTO;
import diegoreyesmo.springboot.nosql.dto.ResponseDTO;
import diegoreyesmo.springboot.nosql.exception.NoSqlException;
import diegoreyesmo.springboot.nosql.service.NoSqlService;
import diegoreyesmo.springboot.nosql.util.Util;
import lombok.extern.java.Log;

@Log
@Service
public class NoSqlServiceImpl implements NoSqlService {

	@Autowired
	private MongoConfiguration mongoConfiguration;

	@Autowired
	private Util util;

	@Override
	public ResponseDTO create(DatabaseDTO databaseDTO, RequestCreateDTO requestCreateDTO) {
		ResponseDTO response;
		List<MongoClient> mongoClient = new ArrayList<>();
		try {
			MongoCollection<Document> collection = conectarMongoDB(databaseDTO, mongoClient);
			List<Document> newDocuments = util.documentsAsList(requestCreateDTO.getNewDocuments());
			util.insertMany(collection, newDocuments);
			response = ResponseDTO.builder().status(STATUS_OK).message(newDocuments).build();
		} catch (Exception e) {
			log.info(e.getMessage());
			response = ResponseDTO.builder().status(STATUS_FAIL).message(util.errorMessage(e)).build();
		} finally {
			if (mongoClient.get(0) != null)
				mongoClient.get(0).close();
		}
		return response;
	}

	
	@Override
	public ResponseDTO read(DatabaseDTO databaseDTO, String id) {
		List<MongoClient> mongoClient = new ArrayList<>();
		Document document = new Document();
		ResponseDTO response;
		try {
			MongoCollection<Document> collection = conectarMongoDB(databaseDTO, mongoClient);
			document.put("id", id);
			log.info("[read] query:" + document.toString());
			Bson queryById = util.filterById(document.getString("id"));
			response = util.cargarRespuestaUnDocumento(skyAndLimit(collection.find(queryById), 1, 1));
		} catch (Exception e) {
			response = ResponseDTO.builder().status(STATUS_FAIL).message(util.errorMessage(e)).build();
		} finally {
			if (mongoClient.get(0) != null)
				mongoClient.get(0).close();
		}
		return response;
	}

	@Override
	public ResponseDTO update(DatabaseDTO databaseDTO, RequestUpdateDTO requestUpdateDTO, String id) {
		List<MongoClient> mongoClient = new ArrayList<>();
		Document document = new Document();
		ResponseDTO response;
		try {
			MongoCollection<Document> collection = conectarMongoDB(databaseDTO, mongoClient);
			UpdateOptions upsertTrue = new UpdateOptions().upsert(true);
			document.put("id", id);
			Document body = requestUpdateDTO.getUpdatedDocument();
			log.info("[update] query:" + document.toString());
			log.info("[update] body:" + body.toString());
			Bson queryById = util.filterById(document.getString("id"));
			util.update(collection, queryById, body, upsertTrue);
			response = util.cargarRespuestaUnDocumento(collection.find(queryById));
		} catch (Exception e) {
			response = ResponseDTO.builder().status(STATUS_FAIL).message(util.errorMessage(e)).build();
		} finally {
			if (mongoClient.get(0) != null)
				mongoClient.get(0).close();
		}
		return response;
	}

	@Override
	public ResponseDTO delete(DatabaseDTO databaseDTO, String id) {
		List<MongoClient> mongoClient = new ArrayList<>();
		Document document = new Document();
		ResponseDTO response;
		try {
			MongoCollection<Document> collection = conectarMongoDB(databaseDTO, mongoClient);
			document.put("id", id);
			log.info("[delete] query:" + document.toString());

			Bson queryById = util.filterById(document.getString("id"));
			DeleteResult deleted = util.delete(collection, queryById);
			deleted.getDeletedCount();
			String strMessage = "cantidad documentos eliminados: " + deleted.getDeletedCount();
			response = ResponseDTO.builder().status(STATUS_OK).message(strMessage).build();
		} catch (Exception e) {
			log.info(e.getMessage());
			response = ResponseDTO.builder().status(STATUS_FAIL).message(util.errorMessage(e)).build();
		} finally {
			if (mongoClient.get(0) != null)
				mongoClient.get(0).close();
		}
		return response;
	}

	@Override
	public ResponseDTO search(DatabaseDTO databaseDTO, RequestSearchDTO requestSearchDTO) {
		List<MongoClient> mongoClient = new ArrayList<>();
		Document document = new Document();
		ResponseDTO response;
		try {
			MongoCollection<Document> collection = conectarMongoDB(databaseDTO, mongoClient);
			Document query = requestSearchDTO.getQuery();
			Integer pageNum = requestSearchDTO.getPageNum();
			Integer pageSize = requestSearchDTO.getPageSize();
			log.info("[search] query:" + query.toString());
			List<String> fields = requestSearchDTO.getFields();
			if (query.containsKey("id")) {
				Bson queryById = util.filterById(query.getString("id"));
				response = util.cargarRespuestaSearch(skyAndLimit(collection.find(queryById), pageNum, pageSize),
						fields);
			} else if (requestSearchDTO.isCount()) {
				long countDocuments = collection.countDocuments(query);
				document.append(COUNT_FIELD, countDocuments);
				response = ResponseDTO.builder().status(STATUS_OK).message(document).build();
			} else if (requestSearchDTO.getAsc() != null) {
				response = util.cargarRespuestaSearch(
						skyAndLimit(collection.find(query).sort(Sorts.ascending(requestSearchDTO.getAsc())), pageNum,
								pageSize),
						fields);
			} else if (requestSearchDTO.getDesc() != null) {
				response = util.cargarRespuestaSearch(
						skyAndLimit(collection.find(query).sort(Sorts.descending(requestSearchDTO.getDesc())), pageNum,
								pageSize),
						fields);
			} else {
				response = util.cargarRespuestaSearch(skyAndLimit(collection.find(query), pageNum, pageSize), fields);
			}
		} catch (Exception e) {
			response = ResponseDTO.builder().status(STATUS_FAIL).message(util.errorMessage(e)).build();
		} finally {
			if (mongoClient.get(0) != null)
				mongoClient.get(0).close();
		}
		return response;
	}

	private FindIterable<Document> skyAndLimit(FindIterable<Document> find, Integer pageNum, Integer pageSize)
			throws NoSqlException {
		if (pageNum == null)
			pageNum = 1;
		if (pageSize == null) {
			pageSize = 50;
		}
		if (pageNum < 1) {
			throw new NoSqlException("Número de página inválido. Intente con un número mayor que cero.");
		}
		FindIterable<Document> skip = find.skip(pageSize * (pageNum - 1));
		return skip.limit(pageSize);
	}

	private MongoCollection<Document> conectarMongoDB(DatabaseDTO databaseDTO, List<MongoClient> mongoClient) {
		MongoCredential credential = MongoCredential.createCredential(mongoConfiguration.getUser(),
				mongoConfiguration.getSource(), mongoConfiguration.getPassword().toCharArray());
		mongoClient.add(MongoClients.create(MongoClientSettings.builder().applyToClusterSettings(builder -> {
			ServerAddress serverAddress = new ServerAddress(mongoConfiguration.getHostname(),
					mongoConfiguration.getPort());
			List<ServerAddress> list = Arrays.asList(serverAddress);
			builder.hosts(list);
			builder.maxWaitQueueSize(10000);// default: 500
		}).credential(credential).build()));
		String databaseString = databaseDTO.getName();
		MongoDatabase database = mongoClient.get(0).getDatabase(databaseString);
		String collection = databaseDTO.getCollections();
		return database.getCollection(collection);
	}

}
