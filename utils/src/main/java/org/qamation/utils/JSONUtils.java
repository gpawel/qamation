package org.qamation.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JSONUtils {
	private String jsonInput;	
	private JsonNode root = null;
	private String PATH_DELIMETER = "/";
    private ObjectMapper mapper;

	public JSONUtils(String jsonInp) {
		this.jsonInput = jsonInp;
		root = getRoot();
	}
	
	public JsonNode getRoot() {
		if (root != null)
			return root;
		else {
			mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
			byte[] jsonData = this.jsonInput.getBytes();
			try {
				JsonNode root = mapper.readTree(jsonData);
				return root;
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}
	
	public String replaceFieldValueInAllNodes(String fieldName, String oldValue, String newValue) {
		List<JsonNode> parents = root.findParents(fieldName);
		for (JsonNode parentNode : parents) {
			replaceFieldValueInNode(parentNode,fieldName,oldValue,newValue);
		}
		return nodeToString(root);
	}
	
	public String insertIntoNode(JsonNode node, String fieldName, String fieldValue) {
		((ObjectNode) node).put(fieldName,fieldValue);
		return nodeToString(root);
	}
	
	public String insertIntoNode(JsonNode node, String fieldName, int value) {
		((ObjectNode) node).put(fieldName, value);
		return nodeToString(root);
	}
	
	public String insertIntoNode(JsonNode node, String fieldName, double value) {
		((ObjectNode) node).put(fieldName, value);
		return nodeToString(root);
	}

	public String insertNodeIntoNode (JsonNode parent, String fieldName, JsonNode child) {
		((ObjectNode)parent).set(fieldName,child);
		return nodeToString(root);
	}
	
	public String replaceFieldValueInFirst(String fieldName, String oldValue, String newValue) {
		List<JsonNode> parents = root.findParents(fieldName);
		if (parents.size()>0) replaceFieldValueInNode(parents.get(0), fieldName, oldValue, newValue);
		else throw new RuntimeException("replaceFieldValueInFirst expectes at least one node with field name: "+fieldName+" to have value:"+oldValue);
		return nodeToString(root);
	}

	//for example in path /a/b/c c as a last element points to an object's  field
	public String replaceFieldValueInPath(String path, String oldValue, String newValue) {
		String[] pathTokens = splitPathIntoTokens(path);
		if (pathTokens.length==0) throw new RuntimeException("replaceFieldValueInPath expects the path "+path+" to have at least one token.");
		JsonNode parentNode = findParentNodeByPath(pathTokens);
		if (parentNode.isObject()) {
			String fieldName = pathTokens[pathTokens.length-1];
			replaceFieldValueInNode(parentNode, fieldName, oldValue, newValue);
			return nodeToString(root);
		}
		throw new RuntimeException("Replacing value is supported in object nodes only. Array support comes shortly.");
	}
	
	
	
	public List<JsonNode> findParentsByPathWithValue(String startPath, String fieldValue) {
		String fieldName = getFieldNameFromPath(startPath);
		JsonNode parentNode = setStartingNodeByPath(startPath);
		List<JsonNode> nodes = parentNode.findParents(fieldName);
		return filterParentsByNameValue(nodes, fieldName, fieldValue);
	}

	

	public List<JsonNode> findParents(String fieldName, String fieldValue) {
		List<JsonNode> nodes =  root.findParents(fieldName);
		return filterParentsByNameValue(nodes, fieldName, fieldValue);
	}

	public String replaceFieldValueInNode(JsonNode parentNode, String fieldName, String oldValue,
										  String newValue) {
		if (hasNameWithValue(parentNode, fieldName, oldValue)){		 
			ObjectNode obj = (ObjectNode)parentNode;
			obj.put(fieldName,newValue);
		}
		return nodeToString(root);
	}

	/*
	Finds a node by a path.
	A path always starts from /
	A path always finishes with a file name.
	Array elements numbering starts from 1.

	For example, with json :
	{
	"paging" : null,
    "positionToLocation" : null,
	"cartNumber" : null,
	"editMode" : false,
	"filterResult" : [ {
	    "facilityId" : 1,
		"locationUseCode" : "1",
	    "cartNumber" : 444,
	    "locationCode" : "001R01",
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
			 +"}"
	 */

	public JsonNode findParentNodeByPath(String path) {
		String [] tokens = splitPathIntoTokens(path);
		JsonNode parent = findParentNodeByPath(tokens);
		return parent;
	}

	public String getFieldValueFromNode(JsonNode node, String fieldName) {
		return extractFieldValueFromNode(node,fieldName);
	}

	public String getFieldValueFromPath(String path) {
		String fieldName = getFieldNameFromPath(path);
		JsonNode node = findParentNodeByPath(path);
		String value = extractFieldValueFromNode(node,fieldName);
		return value;
	}

	public String nodeToString(JsonNode node) {
        if (node == null) {return "";}
        JsonNodeType nodeType = node.getNodeType();
        switch (nodeType) {
            case ARRAY: {return arrayNodeToString(node);}
            case STRING: {return node.asText("null");}
            case NULL: {return nullNodeToString((NullNode)node);}
            case OBJECT: {return objectNodeToString((ObjectNode)node);}
            case NUMBER: {return numberNodeToString(node);}
            default: return unknownNodeTypeToString(node);
        }
    }

    private String unknownNodeTypeToString(JsonNode el) {
        return el.asText();
    }

    private String nullNodeToString(NullNode el) {
        return el.asText();
    }

    private String arrayNodeToString(JsonNode el) {
        StringBuffer buff = new StringBuffer();
        buff.append("[");
        Iterator<JsonNode> arrayElements = el.elements();
        while (arrayElements.hasNext()) {
            JsonNode n = arrayElements.next();
            buff.append(nodeToString(n));
            buff.append(",");
        }
        if (buff.length() > 1) buff.deleteCharAt(buff.length()-1);
        buff.append("]");
        return buff.toString();
    }

    private String numberNodeToString(JsonNode node) {
        return node.asText();
    }

    private String objectNodeToString(ObjectNode node) {
        try {
            return mapper.writeValueAsString(node);
        } catch (JsonProcessingException e) {
            throw  new RuntimeException("Cannot covert node to a string",e);
        }
    }

	private List<JsonNode> filterParentsByNameValue(List<JsonNode> parents, String fieldName, String fieldValue) {
		List<JsonNode> result = new ArrayList<JsonNode>();
		for (JsonNode n : parents) {
			if (hasNameWithValue(n, fieldName, fieldValue)) result.add(n);
		}
		return result;
	}

	private String[] splitPathIntoTokens(String path) {
		return path.split(PATH_DELIMETER);
	}
	
	//remember, last token in a path is a fieldName.
	private String getFieldNameFromPath(String path) {
		String[] tokens = splitPathIntoTokens(path); 
		return tokens[tokens.length-1];
	}
	


	private JsonNode findParentNodeByPath(String[] pathTokens) {
		if (pathTokens.length == 1  && pathTokens[0].isEmpty()) return root;
		JsonNode parent = root;
		for (int i=1; i<pathTokens.length-1; i++) {			 
				JsonNode n = findParentByPathToken(parent, pathTokens[i]);
				if (isPath(n)) parent=n;
		}
		return parent;
	}
	
	
	private JsonNode findParentByPathToken(JsonNode parent, String pathToken) {
		JsonNode resultNode;
		if (StringUtils.isPositiveInteger(pathToken)) {
			int index = Integer.parseInt(pathToken)-1;
			if (index < 0) throw new RuntimeException("Array elements numbering must start from 1.");
			resultNode =  parent.path(index);
		}
		else resultNode = parent.path(pathToken);
		if (resultNode.isMissingNode()) throw new RuntimeException("findParentByPath. token "+pathToken+" is a field or does not point to a parent node in json.");
		return resultNode;
	}

	private String extractFieldValueFromNode(JsonNode node,String fieldName) {
		Iterator<Entry<String, JsonNode>> fields = node.fields();
		while (fields.hasNext()) {
			Entry<String, JsonNode> field = fields.next();
			if (field.getKey().equalsIgnoreCase(fieldName)) {
				JsonNode childNode = field.getValue();
				return childNode.asText();
			}
		}
		return "";
	}

	private boolean hasNameWithValue(JsonNode parentNode, String fieldName, String fieldValue) {
		String nodeValue = extractFieldValueFromNode(parentNode, fieldName);
		if (nodeValue.equalsIgnoreCase(fieldValue)) return true;
		return false;
	}

	private JsonNode setStartingNodeByPath(String startPath) {
		if (startPath.isEmpty()) return root;
		else return findParentNodeByPath(startPath);
	}
	
	private boolean isPath(JsonNode node) {
		if (node == null) {throw new NullPointerException("A JsonNode expected, not Null.");}
		JsonNodeType nodeType = node.getNodeType();
		switch (nodeType) {
			case ARRAY: {return true;}
			case OBJECT: {return true;}
			default: return false;
		}
	}

}
