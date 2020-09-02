package diegoreyesmo.springboot.nosql.service.impl;

import static diegoreyesmo.springboot.nosql.util.Constant.STATUS_FAIL;
import static diegoreyesmo.springboot.nosql.util.Constant.STATUS_OK;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;

import java.util.List;

import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;

import diegoreyesmo.springboot.nosql.configuration.MongoConfiguration;
import diegoreyesmo.springboot.nosql.dto.DatabaseDTO;
import diegoreyesmo.springboot.nosql.dto.RequestCreateDTO;
import diegoreyesmo.springboot.nosql.dto.RequestSearchDTO;
import diegoreyesmo.springboot.nosql.dto.RequestUpdateDTO;
import diegoreyesmo.springboot.nosql.dto.ResponseDTO;
import diegoreyesmo.springboot.nosql.util.Util;

@RunWith(SpringJUnit4ClassRunner.class)
public class NoSqlServiceImplTests {

	private static final String ID = "5dd29f31e6d9de05bd14104d";

	@Mock
	private MongoConfiguration mongoConfiguration;

	@Mock
	private MongoDatabase mongoDatabase;

	@Mock
	private MongoCollection<Document> collection;

	@Mock
	private Document document;

	private Document[] newDocuments;

	@Mock
	private List<Document> listDocument;

	@Mock
	private List<MongoClient> mongoClient;

	@Mock
	private MongoClient client;

	@Mock
	private DatabaseDTO databaseDTO;

	@Mock
	private RequestCreateDTO requestCreateDTO;

	@Mock
	private ResponseDTO responseDTO;

	@Mock
	private RequestUpdateDTO requestUpdateDTO;

	@Mock
	private RequestSearchDTO requestSearchDTO;

	@Mock
	private DeleteResult deleteResult;

	@Mock
	private Util util;

	@InjectMocks
	private NoSqlServiceImpl noSqlServiceImpl;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		Mockito.mock(MongoCollection.class);
		Mockito.mock(MongoDatabase.class);

		newDocuments = new Document[] { new Document() };

		BDDMockito.given(responseDTO.getStatus()).willReturn(STATUS_OK);

		BDDMockito.given(util.documentsAsList(any())).willReturn(listDocument);
		BDDMockito.given(util.filterById(any())).willReturn(document);
		BDDMockito.given(util.cargarRespuesta(any())).willReturn(responseDTO);
		BDDMockito.given(util.cargarRespuestaSearch(any(), any())).willReturn(responseDTO);
		BDDMockito.given(util.cargarRespuestaUnDocumento(any())).willReturn(responseDTO);
		BDDMockito.given(util.filterById(any())).willReturn(document);
		BDDMockito.given(util.filterById(any())).willReturn(document);
		BDDMockito.given(util.delete(any(), any())).willReturn(deleteResult);

		BDDMockito.given(client.getDatabase(any())).willReturn(mongoDatabase);
		BDDMockito.given(mongoDatabase.getCollection(any())).willReturn(collection);
		BDDMockito.given(deleteResult.getDeletedCount()).willReturn(1L);

		BDDMockito.given(mongoConfiguration.getUser()).willReturn("dummyUser");
		BDDMockito.given(mongoConfiguration.getPassword()).willReturn("dummyPassword");
		BDDMockito.given(mongoConfiguration.getHostname()).willReturn("localhost");
		BDDMockito.given(mongoConfiguration.getSource()).willReturn("dummySource");
		BDDMockito.given(mongoConfiguration.getPort()).willReturn(1);
		BDDMockito.given(requestCreateDTO.getNewDocuments()).willReturn(newDocuments);
		BDDMockito.given(requestUpdateDTO.getUpdatedDocument()).willReturn(newDocuments[0]);
		BDDMockito.given(requestSearchDTO.getQuery()).willReturn(newDocuments[0]);
		BDDMockito.given(databaseDTO.getName()).willReturn("dbName");
		BDDMockito.given(databaseDTO.getCollections()).willReturn("collectionName");

	}

	@Test
	public void testCreateValid() {
		ResponseDTO response = noSqlServiceImpl.create(databaseDTO, requestCreateDTO);
		assertEquals(STATUS_OK, response.getStatus());
	}

	@Test
	public void testReadValid() {
		ResponseDTO response = noSqlServiceImpl.read(databaseDTO, ID);
		assertEquals(STATUS_OK, response.getStatus());
	}

	@Test
	public void testUpdateStatusFail() {
		ResponseDTO response = noSqlServiceImpl.update(databaseDTO, requestUpdateDTO, ID);
		assertEquals(STATUS_OK, response.getStatus());
	}

	@Test
	public void testDelete() {
		ResponseDTO response = noSqlServiceImpl.delete(databaseDTO, ID);
		assertEquals(STATUS_OK, response.getStatus());
	}

	@Test
	public void testSearchId() {
		requestSearchDTO.getQuery().put("id", ID);
		ResponseDTO response = noSqlServiceImpl.search(databaseDTO, requestSearchDTO);
		assertEquals(STATUS_FAIL, response.getStatus());
	}

	@Test
	public void testSearchStatusFail() {
		ResponseDTO response = noSqlServiceImpl.search(databaseDTO, requestSearchDTO);
		assertEquals(STATUS_FAIL, response.getStatus());
	}

}
