package org.qamation.utils;
import org.junit.Test;
import org.junit.Before;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JSONParsingTests {
	final private String input = "{"
			 +" \"paging\" : {"
			 +"     \"offset\" : 0,"
			 +"     \"limit\" : 10"
			 +" },"
			 +" \"positionToLocation\" : null,"
			 +" \"cartNumber\" : null,"
			 +" \"editMode\" : false,"
			 +" \"filterResult\" : [ {"
			 +"   \"facilityId\" : 1,"
			 +"   \"locationUseCode\" : \"1\","
			 +"   \"cartNumber\" : 444,"
			 +"   \"locationCode\" : \"001R01\","
			 +"   \"locationDescription\" : \"Auto Accessories\""
			 +" }, {"
			 +"   \"facilityId\" : 1,"
			 +"   \"locationUseCode\" : \"1\","
			 +"   \"cartNumber\" : null,"
			 +"   \"locationCode\" : \"001R02\","
			 +"   \"locationDescription\" : \"Auto Accessories\""
			 +" }, {"
			 +"   \"facilityId\" : 1,"
			 +"   \"locationUseCode\" : \"1\","
			 +"   \"cartNumber\" : null,"
			 +"   \"locationCode\" : \"001R03\","
			 +"   \"locationDescription\" : \"Auto Accessories\""
			 +" }"
			 + "],"
			 +" \"vector\" :[1,2,3],"
			 +" \"fcpoStatusEffDateCorp\" : \"09/09/12\","
			 +" \"date\" : \"2016-01-21\""
			 +"}";
	private JsonNode root;
	private Map<String,String> jsonMap;
	private JSONUtils jsonUtils;
	@Before
	public void setUp() {
		byte[] jsonData = input.getBytes();
		ObjectMapper mapper = new ObjectMapper();
		jsonMap = new HashMap<String,String>();
		try {
			jsonMap = mapper.readValue(jsonData,HashMap.class);
			//System.out.println(jsonMap);
			root = mapper.readTree(jsonData);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		jsonUtils = new JSONUtils(input);
	}
	
	@After
	public void tearDown() {}
	
	@Test
	public void findNodeByPath() {
		JsonNode n = jsonUtils.findNode("/filterResult/1/facilityId");
		printFoundResult(n);
	}
	
	@Test
	public void iterateThroughJson() {
		nodeToString(root);
	}
	
	@Test
	public void findParentsByFieldName() {
		List<JsonNode> parents = root.findParents("cartNumber");
		for (JsonNode n : parents) {
			printFoundResult(n);
		}
	}
	
	
	@Test
	public void findNodesWithName() {
		List<JsonNode> result = root.findValues("cartNumber");
		for (JsonNode n : result) {
			printFoundResult(n);
		}
	}
	
	@Test
	public void updateFieldValue() {
		List<JsonNode> parents = root.findParents("cartNumber");
		for (JsonNode n : parents) {
			ObjectNode obj = (ObjectNode)n;
			obj.put("cartNumber","123");
		}		
		System.out.println(root.toString());
	}

	@Test
	public void replaceAllFieldValues() {
		String result = jsonUtils.replaceFieldValueInAllNodes("cartNumber","null","123");	
		System.out.println(result);
		String result1 = jsonUtils.replaceFieldValueInAllNodes("cartNumber","444","123");	
		System.out.println(result1);
		String result2 = jsonUtils.replaceFieldValueInAllNodes("locationCode","001R01","34566");	
		System.out.println(result2);
		String result3 = jsonUtils.replaceFieldValueInAllNodes("editMode","false","true");	
		System.out.println(result3);
	}
	
	@Test
	public void replaceAllFieldValues1() {
		String result2 = jsonUtils.replaceFieldValueInAllNodes("locationCode","001R01","34566");	
		System.out.println(result2);

	}
	
	@Test
	public void replaceFirstFieldValue() {
		String result = jsonUtils.replaceFieldValueInFirst("cartNumber","null","123");	
		System.out.println(result);
	}
	
	@Test
	public void replaceFieldValueByPath() {
		String path = "/filterResult/2/cartNumber";
		String result = jsonUtils.replaceFieldValueInPath(path, "null", "321");
		System.out.println(result);
		String path2 = "/cartNumber";
		String result2 = jsonUtils.replaceFieldValueInPath(path2, "null", "888");
		System.out.println(result2);

		String path3 = "/editMode";
		String oldFieldValue3 = "false";
		String newValue3 = "true";
		String result3 = jsonUtils.replaceFieldValueInPath(path3,oldFieldValue3,newValue3);
		System.out.println(result3);
	}

	@Test
	public void replaceFieldVlaueInNode() {
		String path3 = "/filterResult/cartNumber";
		String fieldName3 = "cartNumber";
		String oldFieldValue3 = "null";
		List<JsonNode> nodes = jsonUtils.findParentsByPathWithValue(path3,oldFieldValue3);
		JsonNode node = nodes.get(0);
		String result3 = jsonUtils.replaceFieldValueInNode(node,fieldName3,oldFieldValue3,"123");
		System.out.println(result3);
	}

	
	@Test
	public void findListOfNodesByNameValeUsingPath() {
		String path = "/filterResult/locationCode";
		List<JsonNode> result = jsonUtils.findParentsByPathWithValue(path, "001R03");
		for (JsonNode n : result) {
			printFoundResult(n);
		}
	}

	@Test
	public void replaceValueInPosition() {
		String fieldName = "facilityId";
		String oldValue = "1";
		String newValue = "970";
		int position = 2;
		String newJson = jsonUtils.replaceFieldValueInPosition(fieldName,oldValue,newValue,position);
		JSONUtils u = new JSONUtils(newJson);
		printFoundResult(u.getRoot());

	}

	@Test
	public void findParentNodeByPath() {
		findAndPrint("/filterResult/2");
		findAndPrint("/vector/1");
		findAndPrint("/vector");
		findAndPrint("/filterResult/2/locationCode");
		findAndPrint("/filterResult/1/locationCode");
	}

	private void findAndPrint(String path) {
		JsonNode result = jsonUtils.findParentNodeByPath(path);
		printFoundResult(path, result);
	}

	@Test
	public void insertIntoRoot() {
		JsonNode  root = jsonUtils.getRoot();
		String result1 = jsonUtils.insertIntoNode(root, "newField", "New Value");
		System.out.println(result1);
		
	}
	
	@Test
	public void insertIntoParentNode() {
		String path = "/filterResult"; //\"locationCode\" : \"001R03\","
		JsonNode parent = jsonUtils.findParentNodeByPath(path);
		jsonUtils.insertIntoNode(parent, "string field name", "string value");
		jsonUtils.insertIntoNode(parent,"fload field name", 2.054);
		String str = jsonUtils.insertIntoNode(parent, "int field name", 5);
		System.out.println(str);
	}
	@Test
	public void insertIntoParentNodeWithValue() {
		String path = "/filterResult/locationCode"; //\"locationCode\" : \"001R03\","
		List<JsonNode> parents = jsonUtils.findParentsByPathWithValue(path, "001R03");
		for (JsonNode parent : parents) {
			jsonUtils.insertIntoNode(parent, "string field name", "string value");
			jsonUtils.insertIntoNode(parent,"fload field name", 2.054);
			String str = jsonUtils.insertIntoNode(parent, "int field name", 5);
			System.out.println(str);
		}
	}

	@Test
	public void insertNodeIntoNode() {
		String childPath = "/paging/offset";
		String parentPath = "/filterResult/locationCode";

		JsonNode child = jsonUtils.findParentNodeByPath(childPath);
		nodeToString(child);

		List<JsonNode> parents = jsonUtils.findParentsByPathWithValue(parentPath, "001R03");
		JsonNode parent = parents.get(0);

		jsonUtils.insertNodeIntoNode(parent,"paging",child);
		nodeToString(parent);

	}

	@Test
	public void getFieldValueFromPath() {
		String path = "/filterResult/2/locationCode";
		String value = jsonUtils.getFieldValueFromPath(path);
		Assert.assertTrue("001R02".equals(value));
	}

	@Test
	public void getFieldValueFromNode() {
		String path = "/filterResult/2/locationCode";
		JsonNode node = jsonUtils.findParentNodeByPath(path);
		String value = jsonUtils.getFieldValueFromNode(node,"locationCode");
		Assert.assertTrue("001R02".equals(value));
	}
	
	@Test
	public void findParentsByPathWithValueTest() {
		String json = StringUtils.readFileIntoString("json.txt");
		jsonUtils = new JSONUtils(json);
		String path = "/filterResult/timedSpecial";
		String value = "Y";
		List <JsonNode> list = jsonUtils.findParentsByPathWithValue(path, value);
		Assert.assertTrue(list.size()==1);
		JsonNode node = list.get(0);
		System.out.println(jsonUtils.nodeToString(node));
		
	}
	
	@Test
	public void findTimedSpecialProduct() {
		String json = StringUtils.readFileIntoString("timedspecialproducts.json");
		jsonUtils = new JSONUtils(json);
		String path = "/timeSpecialProducts/1/recordId";
		JsonNode node = jsonUtils.findParentNodeByPath(path);
		String nodeStr = jsonUtils.nodeToString(node);
		String recordId = jsonUtils.getFieldValueFromNode(node, "recordId");
		Assert.assertTrue(recordId.equals("cf1ddf4c-cd32-443f-a388-b39610bb3311"));
	}

	@Test
	public void escapeQuoteInValue() {
		String json = StringUtils.readFileIntoString("json_with_quote.json");
		jsonUtils = new JSONUtils(json);
		String path = "/overrides/field";
		String value = "productDescription";
		List<JsonNode> nodes = jsonUtils.findParentsByPathWithValue(path,value);
		JsonNode node = nodes.get(0);
		String fieldValue = jsonUtils.getFieldValueFromNode(node,"value");
		System.out.println(fieldValue);
		fieldValue = fieldValue.replace("\"","\\\"");
		System.out.println(fieldValue);
	}

	private void printFoundResult(JsonNode n) {
		System.out.println();
		System.out.println("Found <");
		nodeToString(n);
		System.out.println(">");
		System.out.println();
	}

	private void printFoundResult(String path, JsonNode n) {
		System.out.println();
		System.out.print("Path: "+path);
		printFoundResult(n);
	}

	private void nodeToString(JsonNode node) {
	    System.out.println(jsonUtils.nodeToString(node));
	}
	
	
	
}


