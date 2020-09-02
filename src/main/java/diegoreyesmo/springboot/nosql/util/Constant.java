package diegoreyesmo.springboot.nosql.util;

public class Constant {
	// PATH PARAMS
	public static final String DATABASE_PARAM = "database";
	public static final String DATABASE_DESCRIPTION = "Nombre de la base de datos. Si no existe, la crea";
	public static final String COLLECTIONS_PARAM = "collections";
	public static final String COLLECTIONS_DESCRIPTION = "Nombre de la colección. Si no existe, la crea";
	public static final String DOCUMENT_ID_PARAM = "documentId";
	public static final String READ_DOCUMENT_ID_DESCRIPTION = "Identificador del documento que se quiere consultar";
	public static final String UPDATE_DOCUMENT_ID_DESCRIPTION = "Identificador del documento que se quiere actualizar";
	public static final String DELETE_DOCUMENT_ID_DESCRIPTION = "Identificador del documento que se quiere eliminar";

	// REQUEST FIELDS
	public static final String QUERY_PARAM = "query";
	public static final String QUERY_DESCRIPTION = "[requerido] Filtros de búsqueda.";
	public static final String COUNT_PARAM = "count";
	public static final String COUNT_DESCRIPTION = "[opcional] true para contar documentos que cumplen con el filtro. false para obtener los documentos que cumplen con el filtro. Valor por defecto: false";
	public static final String ASC_PARAM = "asc";
	public static final String ASC_DESCRIPTION = "[opcional] Ordena los documentos de forma ascendente, según los campos indicados";
	public static final String DESC_PARAM = "desc";
	public static final String DESC_DESCRIPTION = "[opcional] Ordena los documentos de forma descendente, según los campos indicados";
	public static final String FIELDS_PARAM = "fields";
	public static final String FIELDS_DESCRIPTION = "[opcional] Lista de campos que se desea mostrar en cada documento. Si no se especifica, se mostrará el documento completo.";
	public static final String PAGE_NUM_PARAM = "page-num";
	public static final String PAGE_NUM_DESCRIPTION = "[opcional] Índice de página que se desea mostrar. Valor por defecto: 1";
	public static final String PAGE_SIZE_PARAM = "page-size";
	public static final String PAGE_SIZE_DESCRIPTION = "[opcional] Cantidad de documentos por página. Valor por defecto: 50";
	public static final String UPDATED_DOCUMENT_PARAM = "updated-document";
	public static final String UPDATED_DOCUMENT_DESCRIPTION = "[requerido] Campos del documentos que se desean actualizar. Si los campos no existen, serán agregados al documento.";
	public static final String NEW_DOCUMENTS_PARAM = "new-documents";
	public static final String NEW_DOCUMENTS_DESCRIPTION = "[requerido] Lista de documentos a insertar";

	// RESPONSE FIELDS
	public static final String COUNT_FIELD = "count";
	public static final String STATUS_FIELD = "status";
	public static final String STATUS_DESCRIPTION = "Indica si la consulta se ejecutó correctamente. Posibles valores: ok|fail";
	public static final String MESSAGE_FIELD = "message";
	public static final String READ_MESSAGE_DESCRIPTION = "Documento consultado";
	public static final String CREATE_MESSAGE_DESCRIPTION = "Informa cantidad de documentos insertados";
	public static final String UPDATE_MESSAGE_DESCRIPTION = "Documento actualizado";
	public static final String DELETE_MESSAGE_DESCRIPTION = "Informa cantidad de documentos eliminados";
	public static final String SEARCH_MESSAGE_DESCRIPTION = "Conjunto de documentos que cumplen los filtros indicados en 'query'";

	// PATHS
	public static final String CREATE_PATH = "/{" + DATABASE_PARAM + "}/{" + COLLECTIONS_PARAM + "}";
	public static final String RUD_PATH = "/{" + DATABASE_PARAM + "}/{" + COLLECTIONS_PARAM + "}/{" + DOCUMENT_ID_PARAM
			+ "}";
	public static final String SEARCH_PATH = "/{" + DATABASE_PARAM + "}/{" + COLLECTIONS_PARAM + "}/search";

	// SNIPPETS NAMES
	public static final String CREATE_SNIPPET = "create";
	public static final String READ_SNIPPET = "read";
	public static final String UPDATE_SET_SNIPPET = "update-set";
	public static final String UPDATE_PUSH_SNIPPET = "update-push";
	public static final String DELETE_SNIPPET = "delete";
	public static final String SEARCH_SNIPPET = "search";
	public static final String SEARCH_COUNT_SNIPPET = "search-count";
	public static final String STATUS_OK = "ok";
	public static final String STATUS_FAIL = "fail";

}

