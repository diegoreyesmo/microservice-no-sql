package diegoreyesmo.springboot.nosql.controller;

import static diegoreyesmo.springboot.nosql.util.Constant.ASC_DESCRIPTION;
import static diegoreyesmo.springboot.nosql.util.Constant.ASC_PARAM;
import static diegoreyesmo.springboot.nosql.util.Constant.COLLECTIONS_DESCRIPTION;
import static diegoreyesmo.springboot.nosql.util.Constant.COLLECTIONS_PARAM;
import static diegoreyesmo.springboot.nosql.util.Constant.COUNT_DESCRIPTION;
import static diegoreyesmo.springboot.nosql.util.Constant.COUNT_PARAM;
import static diegoreyesmo.springboot.nosql.util.Constant.CREATE_MESSAGE_DESCRIPTION;
import static diegoreyesmo.springboot.nosql.util.Constant.CREATE_PATH;
import static diegoreyesmo.springboot.nosql.util.Constant.CREATE_SNIPPET;
import static diegoreyesmo.springboot.nosql.util.Constant.DATABASE_DESCRIPTION;
import static diegoreyesmo.springboot.nosql.util.Constant.DATABASE_PARAM;
import static diegoreyesmo.springboot.nosql.util.Constant.DELETE_DOCUMENT_ID_DESCRIPTION;
import static diegoreyesmo.springboot.nosql.util.Constant.DELETE_MESSAGE_DESCRIPTION;
import static diegoreyesmo.springboot.nosql.util.Constant.DELETE_SNIPPET;
import static diegoreyesmo.springboot.nosql.util.Constant.DESC_DESCRIPTION;
import static diegoreyesmo.springboot.nosql.util.Constant.DESC_PARAM;
import static diegoreyesmo.springboot.nosql.util.Constant.DOCUMENT_ID_PARAM;
import static diegoreyesmo.springboot.nosql.util.Constant.FIELDS_DESCRIPTION;
import static diegoreyesmo.springboot.nosql.util.Constant.FIELDS_PARAM;
import static diegoreyesmo.springboot.nosql.util.Constant.MESSAGE_FIELD;
import static diegoreyesmo.springboot.nosql.util.Constant.NEW_DOCUMENTS_DESCRIPTION;
import static diegoreyesmo.springboot.nosql.util.Constant.NEW_DOCUMENTS_PARAM;
import static diegoreyesmo.springboot.nosql.util.Constant.PAGE_NUM_DESCRIPTION;
import static diegoreyesmo.springboot.nosql.util.Constant.PAGE_NUM_PARAM;
import static diegoreyesmo.springboot.nosql.util.Constant.PAGE_SIZE_DESCRIPTION;
import static diegoreyesmo.springboot.nosql.util.Constant.PAGE_SIZE_PARAM;
import static diegoreyesmo.springboot.nosql.util.Constant.QUERY_DESCRIPTION;
import static diegoreyesmo.springboot.nosql.util.Constant.QUERY_PARAM;
import static diegoreyesmo.springboot.nosql.util.Constant.READ_DOCUMENT_ID_DESCRIPTION;
import static diegoreyesmo.springboot.nosql.util.Constant.READ_MESSAGE_DESCRIPTION;
import static diegoreyesmo.springboot.nosql.util.Constant.READ_SNIPPET;
import static diegoreyesmo.springboot.nosql.util.Constant.RUD_PATH;
import static diegoreyesmo.springboot.nosql.util.Constant.SEARCH_COUNT_SNIPPET;
import static diegoreyesmo.springboot.nosql.util.Constant.SEARCH_MESSAGE_DESCRIPTION;
import static diegoreyesmo.springboot.nosql.util.Constant.SEARCH_PATH;
import static diegoreyesmo.springboot.nosql.util.Constant.SEARCH_SNIPPET;
import static diegoreyesmo.springboot.nosql.util.Constant.STATUS_DESCRIPTION;
import static diegoreyesmo.springboot.nosql.util.Constant.STATUS_FAIL;
import static diegoreyesmo.springboot.nosql.util.Constant.STATUS_FIELD;
import static diegoreyesmo.springboot.nosql.util.Constant.STATUS_OK;
import static diegoreyesmo.springboot.nosql.util.Constant.UPDATED_DOCUMENT_DESCRIPTION;
import static diegoreyesmo.springboot.nosql.util.Constant.UPDATED_DOCUMENT_PARAM;
import static diegoreyesmo.springboot.nosql.util.Constant.UPDATE_DOCUMENT_ID_DESCRIPTION;
import static diegoreyesmo.springboot.nosql.util.Constant.UPDATE_MESSAGE_DESCRIPTION;
import static diegoreyesmo.springboot.nosql.util.Constant.UPDATE_SET_SNIPPET;
import static diegoreyesmo.springboot.nosql.util.Constant.UPDATE_PUSH_SNIPPET;
import static org.mockito.Matchers.any;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedRequestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import diegoreyesmo.springboot.nosql.dto.ResponseDTO;
import diegoreyesmo.springboot.nosql.service.NoSqlService;
import lombok.extern.java.Log;

@Log
@RunWith(SpringRunner.class)
@WebMvcTest(NoSqlController.class)
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class NoSqlControllerTests {

	private static final String ORIGINAL_SUBJECT = "Documentación no-sql";

	private static final String MODIFIED_SUBJECT = "Documentación de microservicio no-sql";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	NoSqlService noSqlService;

	@Autowired
	private ObjectMapper objectMapper;

	private Document alice;
	private Document aliceModificadoSet;
	private Document aliceModificadoPush;
	private Document bob;
	private Document eve;
	private Document query;
	private String database;
	private String collection;
	private String documentId;
	private Map<String, Object> event2;

	private ParameterDescriptor databaseParam;
	private ParameterDescriptor collectionsParam;

	private FieldDescriptor statusField;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		database = "server";
		collection = "mails";
		documentId = "5dd29f31e6d9de05bd14104d";

		databaseParam = parameterWithName(DATABASE_PARAM).description(DATABASE_DESCRIPTION);
		collectionsParam = parameterWithName(COLLECTIONS_PARAM).description(COLLECTIONS_DESCRIPTION);
		statusField = fieldWithPath(STATUS_FIELD).description(STATUS_DESCRIPTION);

		alice = new Document();
		aliceModificadoSet = new Document();
		aliceModificadoPush = new Document();
		bob = new Document();
		eve = new Document();
		query = new Document();
		alice.put("from", "no-reply@example.com");
		alice.put("to", "alice@example.com");
		alice.put("subject", ORIGINAL_SUBJECT);

		List<Map<String, Object>> originalAudit = new ArrayList<>();
		List<Map<String, Object>> pushedAudit = new ArrayList<>();
		Map<String, Object> event1 = new HashMap<>();
		event2 = new HashMap<>();
		event1.put("event-type", "delivery");
		event1.put("timestamp", "1479249799770");
		event2.put("event-type", "complaint");
		event2.put("timestamp", "1479249869812");
		originalAudit.add(event1);
		pushedAudit.add(event1);
		pushedAudit.add(event2);
		alice.put("audit", originalAudit);
		alice.put("id", "5dc2ebaa76f3e07445497317");

		bob.put("from", "no-reply@example.com");
		bob.put("to", "bob@example.com");
		bob.put("subject", "Asunto de prueba");
		bob.put("id", "7aae1e77ca88f5b63bdc068b");

		eve.put("from", "no-reply@example.com");
		eve.put("to", "eve@example.com");
		eve.put("subject", "Restdoc");
		eve.put("id", "dab814391d362676fd7ed4d8");

		query.put("from", "no-reply@example.com");
		aliceModificadoSet.put("from", "no-reply@example.com");
		aliceModificadoSet.put("to", "alice@example.com");
		aliceModificadoSet.put("subject", MODIFIED_SUBJECT);
		aliceModificadoSet.put("audit", originalAudit);
		aliceModificadoSet.put("id", "8b20c54272a1174d0b2533f9");

		aliceModificadoPush.put("from", "no-reply@example.com");
		aliceModificadoPush.put("to", "alice@example.com");
		aliceModificadoPush.put("subject", ORIGINAL_SUBJECT);
		aliceModificadoPush.put("audit", pushedAudit);
		aliceModificadoPush.put("id", "77e7a77b6d88abd240790382");
	}

	@Test
	public void testCreate() throws Exception {
		ResponseDTO response;
		response = ResponseDTO.builder().status(STATUS_OK).message("cantidad documentos insertados: 1").build();
		// Mocks
		BDDMockito.given(noSqlService.create(any(), any())).willReturn(response);

		ArrayList<Object> newDocuments = new ArrayList<>();
		newDocuments.add(alice);
		Map<String, Object> request = new HashMap<String, Object>();
		request.put(NEW_DOCUMENTS_PARAM, newDocuments);
		String bodyContent = objectMapper.writeValueAsString(request);
		log.info(bodyContent);
		FieldDescriptor newDocumentParam = fieldWithPath(NEW_DOCUMENTS_PARAM).description(NEW_DOCUMENTS_DESCRIPTION);
		FieldDescriptor messageField = fieldWithPath(MESSAGE_FIELD).description(CREATE_MESSAGE_DESCRIPTION);
		this.mockMvc.perform(post(CREATE_PATH, database, collection).content(bodyContent).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andDo(document(CREATE_SNIPPET, relaxedRequestFields(newDocumentParam),
						pathParameters(databaseParam, collectionsParam), relaxedResponseFields(statusField, messageField)))
				.andDo(print());
	}

	@Test
	public void testCreateInvalidRequest() throws Exception {
		ArrayList<Object> newDocuments = new ArrayList<>();
		newDocuments.add(alice);
		Map<String, Object> request = new HashMap<String, Object>();
		String bodyContent = objectMapper.writeValueAsString(request);
		log.info(bodyContent);
		this.mockMvc.perform(post(CREATE_PATH, database, collection).content(bodyContent).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testCreateInternalErrorStatusNull() throws Exception {
		// Mocks
		ResponseDTO response;
		response = ResponseDTO.builder().status(null).message("cantidad documentos insertados: 1").build();
		BDDMockito.given(noSqlService.create(any(), any())).willReturn(response);
		ArrayList<Object> newDocuments = new ArrayList<>();
		newDocuments.add(alice);
		Map<String, Object> request = new HashMap<String, Object>();
		request.put(NEW_DOCUMENTS_PARAM, newDocuments);
		String bodyContent = objectMapper.writeValueAsString(request);
		log.info(bodyContent);
		this.mockMvc.perform(post(CREATE_PATH, database, collection).content(bodyContent).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError());
	}

	@Test
	public void testCreateInternalErrorStatusFail() throws Exception {
		// Mocks
		ResponseDTO response;
		response = ResponseDTO.builder().status(STATUS_FAIL).message("cantidad documentos insertados: 1").build();
		BDDMockito.given(noSqlService.create(any(), any())).willReturn(response);
		ArrayList<Object> newDocuments = new ArrayList<>();
		newDocuments.add(alice);
		Map<String, Object> request = new HashMap<String, Object>();
		request.put(NEW_DOCUMENTS_PARAM, newDocuments);
		String bodyContent = objectMapper.writeValueAsString(request);
		log.info(bodyContent);
		this.mockMvc.perform(post(CREATE_PATH, database, collection).content(bodyContent).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError());
	}

	@Test
	public void testRead() throws Exception {
		ResponseDTO response;
		response = ResponseDTO.builder().status(STATUS_OK).message(alice).build();
		// Mocks
		BDDMockito.given(noSqlService.read(any(), any())).willReturn(response);
		ParameterDescriptor documentIdParam = parameterWithName(DOCUMENT_ID_PARAM).description(READ_DOCUMENT_ID_DESCRIPTION);
		FieldDescriptor messageField = fieldWithPath(MESSAGE_FIELD).description(READ_MESSAGE_DESCRIPTION);
		this.mockMvc.perform(get(RUD_PATH, database, collection, documentId)).andExpect(status().isOk()).andDo(
				document(READ_SNIPPET, pathParameters(databaseParam, collectionsParam, documentIdParam), relaxedResponseFields(statusField, messageField)))
				.andDo(print());
	}

	@Test
	public void testUpdateSet() throws Exception {
		ResponseDTO response;
		response = ResponseDTO.builder().status(STATUS_OK).message(aliceModificadoSet).build();
		// Mocks
		BDDMockito.given(noSqlService.update(any(), any(), any())).willReturn(response);

		Map<String, Object> request = new HashMap<String, Object>();
		Document modifiedSubject = new Document("subject", MODIFIED_SUBJECT);
		Document $set = new Document("$set", modifiedSubject);
		request.put(UPDATED_DOCUMENT_PARAM, $set);
		String bodyContent = objectMapper.writeValueAsString(request);
		log.info(bodyContent);
		ParameterDescriptor documentIdParam = parameterWithName(DOCUMENT_ID_PARAM).description(UPDATE_DOCUMENT_ID_DESCRIPTION);
		FieldDescriptor messageField = fieldWithPath(MESSAGE_FIELD).description(UPDATE_MESSAGE_DESCRIPTION);
		FieldDescriptor updateDocumentParam = fieldWithPath(UPDATED_DOCUMENT_PARAM).description(UPDATED_DOCUMENT_DESCRIPTION);
		FieldDescriptor setParam = fieldWithPath("updated-document.$set").description("actualiza campos indicados (reemplaza valor anterior)");
		FieldDescriptor subjectParam = fieldWithPath("updated-document.$set.subject").description("campos por actualizar");
		
		this.mockMvc.perform(put(RUD_PATH, database, collection, documentId)
				.content(bodyContent)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document(UPDATE_SET_SNIPPET, requestFields(updateDocumentParam, setParam, subjectParam),
						pathParameters(databaseParam, collectionsParam, documentIdParam), relaxedResponseFields(statusField, messageField)))
				.andDo(print());
	}

	@Test
	public void testUpdatePush() throws Exception {
		ResponseDTO response;
		response = ResponseDTO.builder().status(STATUS_OK).message(aliceModificadoPush).build();
		// Mocks
		BDDMockito.given(noSqlService.update(any(), any(), any())).willReturn(response);

		Map<String, Object> request = new HashMap<String, Object>();
		Document audit = new Document("audit", event2);
		Document $push = new Document("$push", audit);
		request.put(UPDATED_DOCUMENT_PARAM, $push);
		String bodyContent = objectMapper.writeValueAsString(request);
		log.info(bodyContent);
		ParameterDescriptor documentIdParam = parameterWithName(DOCUMENT_ID_PARAM).description(UPDATE_DOCUMENT_ID_DESCRIPTION);
		FieldDescriptor messageField = fieldWithPath(MESSAGE_FIELD).description(UPDATE_MESSAGE_DESCRIPTION);
		FieldDescriptor updateDocumentParam = fieldWithPath(UPDATED_DOCUMENT_PARAM).description(UPDATED_DOCUMENT_DESCRIPTION);
		FieldDescriptor pushParam = fieldWithPath("updated-document.$push").description("actualiza arreglos, agregando nuevos elementos");
		FieldDescriptor valueAParam = fieldWithPath("updated-document.$push.audit.event-type").description("valor de ejemplo para agregar");
		FieldDescriptor valueBParam = fieldWithPath("updated-document.$push.audit.timestamp").description("otro valor de ejemplo para agregar");
		this.mockMvc.perform(put(RUD_PATH, database, collection, documentId).content(bodyContent).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(document(UPDATE_PUSH_SNIPPET, requestFields(updateDocumentParam, pushParam, valueAParam, valueBParam),
						pathParameters(databaseParam, collectionsParam, documentIdParam), relaxedResponseFields(statusField, messageField)))
				.andDo(print());
	}

	@Test
	public void testUpdateBadRequest() throws Exception {
		ResponseDTO response;
		response = ResponseDTO.builder().status(STATUS_OK).message(aliceModificadoSet).build();
		// Mocks
		BDDMockito.given(noSqlService.update(any(), any(), any())).willReturn(response);
		Map<String, Object> request = new HashMap<String, Object>();
		String bodyContent = objectMapper.writeValueAsString(request);
		log.info(bodyContent);
		this.mockMvc.perform(put(RUD_PATH, database, collection, documentId).content(bodyContent).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testDelete() throws Exception {
		ResponseDTO response;
		response = ResponseDTO.builder().status(STATUS_OK).message("cantidad documentos eliminados: 1").build();
		// Mocks
		BDDMockito.given(noSqlService.delete(any(), any())).willReturn(response);
		ParameterDescriptor documentIdParam = parameterWithName(DOCUMENT_ID_PARAM).description(DELETE_DOCUMENT_ID_DESCRIPTION);
		FieldDescriptor messageField = fieldWithPath(MESSAGE_FIELD).description(DELETE_MESSAGE_DESCRIPTION);
		this.mockMvc.perform(delete(RUD_PATH, database, collection, documentId)).andExpect(status().isNoContent()).andDo(
				document(DELETE_SNIPPET, pathParameters(databaseParam, collectionsParam, documentIdParam), responseFields(statusField, messageField)))
				.andDo(print());
	}

	@Test
	public void testDeleteNotFoundStatusNull() throws Exception {
		ResponseDTO response;
		response = ResponseDTO.builder().status(null).message(null).build();
		// Mocks
		BDDMockito.given(noSqlService.delete(any(), any())).willReturn(response);
		this.mockMvc.perform(delete(RUD_PATH, database, collection, documentId)).andExpect(status().isNotFound());
	}

	@Test
	public void testDeleteNotFoundStatusFail() throws Exception {
		ResponseDTO response;
		response = ResponseDTO.builder().status(STATUS_FAIL).message(null).build();
		// Mocks
		BDDMockito.given(noSqlService.delete(any(), any())).willReturn(response);
		this.mockMvc.perform(delete(RUD_PATH, database, collection, documentId)).andExpect(status().isNotFound());
	}

	@Test
	public void testSearch() throws Exception {
		ResponseDTO response;
		ArrayList<Document> documents = new ArrayList<>();
		documents.add(alice);
		documents.add(bob);
		response = ResponseDTO.builder().status(STATUS_OK).message(documents).build();
		// Mocks
		BDDMockito.given(noSqlService.search(any(), any())).willReturn(response);

		Map<String, Object> request = new HashMap<String, Object>();
		request.put(QUERY_PARAM, query);
		ArrayList<String> descList = new ArrayList<>();
		descList.add("to");
		request.put(DESC_PARAM, descList);
		String bodyContent = objectMapper.writeValueAsString(request);
		log.info(bodyContent);
		FieldDescriptor query = fieldWithPath(QUERY_PARAM).description(QUERY_DESCRIPTION);
		FieldDescriptor from = fieldWithPath("query.from").description("campo por el cual se desea filtrar la búsqueda");
		FieldDescriptor count = fieldWithPath(COUNT_PARAM).description(COUNT_DESCRIPTION).optional().type(JsonFieldType.BOOLEAN);
		FieldDescriptor asc = fieldWithPath(ASC_PARAM).description(ASC_DESCRIPTION).optional().type(JsonFieldType.ARRAY);
		FieldDescriptor desc = fieldWithPath(DESC_PARAM).description(DESC_DESCRIPTION).optional().type(JsonFieldType.ARRAY);
		FieldDescriptor fields = fieldWithPath(FIELDS_PARAM).description(FIELDS_DESCRIPTION).optional().type(JsonFieldType.ARRAY);
		FieldDescriptor pageNum = fieldWithPath(PAGE_NUM_PARAM).description(PAGE_NUM_DESCRIPTION).optional().type(JsonFieldType.NUMBER);
		FieldDescriptor pageSize = fieldWithPath(PAGE_SIZE_PARAM).description(PAGE_SIZE_DESCRIPTION).optional().type(JsonFieldType.NUMBER);
		FieldDescriptor messageField = fieldWithPath(MESSAGE_FIELD).description(SEARCH_MESSAGE_DESCRIPTION);
		this.mockMvc.perform(post(SEARCH_PATH, database, collection).content(bodyContent).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(document(SEARCH_SNIPPET, requestFields(query, from, count, asc, desc, fields, pageNum, pageSize),
						pathParameters(databaseParam, collectionsParam), relaxedResponseFields(statusField, messageField)))
				.andDo(print());
	}

	@Test
	public void testSearchBadRequest() throws Exception {
		Map<String, Object> request = new HashMap<String, Object>();
		String bodyContent = objectMapper.writeValueAsString(request);
		log.info(bodyContent);
		this.mockMvc.perform(post(SEARCH_PATH, database, collection).content(bodyContent).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testSearchInternalErrorStatusNull() throws Exception {
		ResponseDTO response;
		response = ResponseDTO.builder().status(null).message(new Document("count", 2)).build();
		// Mocks
		BDDMockito.given(noSqlService.search(any(), any())).willReturn(response);
		Map<String, Object> request = new HashMap<String, Object>();
		request.put(QUERY_PARAM, query);
		ArrayList<String> descList = new ArrayList<>();
		descList.add("to");
		request.put(COUNT_PARAM, true);
		String bodyContent = objectMapper.writeValueAsString(request);
		log.info(bodyContent);
		this.mockMvc.perform(post(SEARCH_PATH, database, collection).content(bodyContent).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError());
	}

	@Test
	public void testSearchInternalErrorStatusFail() throws Exception {
		ResponseDTO response;
		response = ResponseDTO.builder().status(STATUS_FAIL).message(new Document("count", 2)).build();
		// Mocks
		BDDMockito.given(noSqlService.search(any(), any())).willReturn(response);
		Map<String, Object> request = new HashMap<String, Object>();
		request.put(QUERY_PARAM, query);
		ArrayList<String> descList = new ArrayList<>();
		descList.add("to");
		request.put(COUNT_PARAM, true);
		String bodyContent = objectMapper.writeValueAsString(request);
		log.info(bodyContent);
		this.mockMvc.perform(post(SEARCH_PATH, database, collection).content(bodyContent).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError());
	}

	@Test
	public void testSearchCount() throws Exception {
		ResponseDTO response;

		response = ResponseDTO.builder().status(STATUS_OK).message(new Document("count", 2)).build();
		// Mocks
		BDDMockito.given(noSqlService.search(any(), any())).willReturn(response);

		Map<String, Object> request = new HashMap<String, Object>();
		request.put(QUERY_PARAM, query);
		ArrayList<String> descList = new ArrayList<>();
		descList.add("to");
		request.put(COUNT_PARAM, true);
		String bodyContent = objectMapper.writeValueAsString(request);
		log.info(bodyContent);
		this.mockMvc.perform(post(SEARCH_PATH, database, collection).content(bodyContent).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(document(SEARCH_COUNT_SNIPPET)).andDo(print());
	}

}
