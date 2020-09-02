package diegoreyesmo.springboot.nosql.util;

import static diegoreyesmo.springboot.nosql.util.Constant.MESSAGE_FIELD;
import static diegoreyesmo.springboot.nosql.util.Constant.STATUS_FAIL;
import static diegoreyesmo.springboot.nosql.util.Constant.STATUS_OK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;

import diegoreyesmo.springboot.nosql.dto.ResponseDTO;
import lombok.extern.java.Log;

@Log
public class Util {

	private static final String OBJECT_ID = "_id";
	private static final String MONGO_DRIVER_ID = OBJECT_ID;

	public Bson filterById(String id) {
		if (id == null)
			return null;
		return Filters.eq(MONGO_DRIVER_ID, new ObjectId(id));

	}

	public List<Document> documentsAsList(Document[] documents) {
		return Arrays.asList(documents);
	}

	public void fixObjectId(Document next) {
		Document tmp = Document.parse(next.toJson().replace("$oid", "objectId"));
		Object object = tmp.get(OBJECT_ID);
		if (object instanceof Document) {
			Object id = ((Document) object).get("objectId");
			next.remove(OBJECT_ID);
			next.put("id", (String) id);
		}
	}

	public ResponseDTO cargarRespuestaUnDocumento(FindIterable<Document> collection) {
		MongoCursor<Document> cursor = collection.iterator();
		ResponseDTO response;
		try {
			ArrayList<Document> documents = new ArrayList<>();
			while (cursor.hasNext()) {
				Document next = cursor.next();
				fixObjectId(next);
				documents.add(next);
			}
			if (documents.size() == 1)
				response = ResponseDTO.builder().status(STATUS_OK).message(documents.get(0)).build();
			else {
				response = ResponseDTO.builder().status(STATUS_OK).message(documents).build();
			}
		} catch (Exception e) {
			response = ResponseDTO.builder().status(STATUS_FAIL).message(errorMessage(e)).build();
		} finally {
			cursor.close();
		}
		return response;
	}

	public ResponseDTO cargarRespuesta(FindIterable<Document> collection) {
		MongoCursor<Document> cursor = collection.iterator();
		ResponseDTO response;
		try {
			ArrayList<Document> documents = new ArrayList<>();
			while (cursor.hasNext()) {
				Document next = cursor.next();
				fixObjectId(next);
				documents.add(next);
			}
			response = ResponseDTO.builder().status(STATUS_OK).message(documents).build();
		} catch (Exception e) {
			response = ResponseDTO.builder().status(STATUS_FAIL).message(errorMessage(e)).build();
		} finally {
			cursor.close();
		}
		return response;
	}

	public ResponseDTO cargarRespuestaSearch(FindIterable<Document> collection, List<String> fields) {
		ResponseDTO respuesta = cargarRespuesta(collection);

		// si existen campos por filtrar
		if (fields != null) {
			ArrayList<Document> destDocuments = new ArrayList<>();
			ResponseDTO respuestaFiltrada = ResponseDTO.builder().status(respuesta.getStatus()).message(destDocuments)
					.build();
			// por cada documento de la respuesta
			ArrayList<Document> arrayList = (ArrayList<Document>) respuesta.getMessage();
			for (Document srcDocument : arrayList) {
				Document tmp = new Document();
				// para cada campo a filtrar
				for (String field : fields) {
					// si el documento respuesta lo contiene, entonces se agrega al documento
					// destino
					if (srcDocument.containsKey(field)) {
						tmp.put(field, srcDocument.get(field));
					}
				}
				// si el documento destino contiene elementos, se agrega a la respuesta
				if (!tmp.isEmpty()) {
					destDocuments.add(tmp);
				}
			}
			respuesta = respuestaFiltrada;
		}
		return respuesta;
	}

	public String errorMessage(Exception e) {
		String errorMessage = e.getMessage();
		errorMessage = errorMessage == null ? "exception" : errorMessage;
		log.severe(errorMessage);
		return errorMessage;
	}

	public void insertMany(MongoCollection<Document> collection, List<Document> newDocuments) {
		// se agrega objectId en formato reconocido por driver mongo
		for (Document newDocument : newDocuments) {
			newDocument.append(OBJECT_ID, new ObjectId());		
		}
		collection.insertMany(newDocuments);
		// se corrige representación de objectId por su representación digerida
		for (Document newDocument : newDocuments) {
			fixObjectId(newDocument);		
		}

	}

	public void update(MongoCollection<Document> collection, Bson queryById, Document setBody,
			UpdateOptions upsertTrue) {
		collection.updateOne(queryById, setBody, upsertTrue);
	}

	public DeleteResult delete(MongoCollection<Document> collection, Bson queryById) {
		return collection.deleteOne(queryById);
	}
}
