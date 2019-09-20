package org.qamation.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
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

	/**
	 * For example the path is /a/b/1/c and returned value
	 * stored in variable node.
	 * If c is a string value in array b then in order to access it
	 * use node.asText() function.
	 * If c is an object then use node.get(String fieldName) to dig
	 * deeper into the c node.
	 *
	 * @param  path
	 * @return JsonNode
	 */
	public JsonNode findNode(String path) {
	    JsonPointer pointer = JsonPointer.compile(path);
        return findNode(pointer);
    }

    public JsonNode findNode(JsonPointer pointer) {
        return root.at(pointer);
    }

	public String replaceFieldValueInAllNodes(String fieldName, String oldValue, String newValue) {
		List<JsonNode> parents = root.findParents(fieldName);
		for (JsonNode parentNode : parents) {
			replaceFieldValueInNode(parentNode,fieldName,oldValue,newValue);
		}
		return nodeToString(root);
	}





	/**
	 * Inserts a JsonNode into another JsonNode
	 * @param parent node
	 * @param fieldName
	 * @param child node
	 * @return the updated json.
	 */
	public String insertNodeIntoNode (JsonNode parent, String fieldName, JsonNode child) {
		((ObjectNode)parent).set(fieldName,child);
		return nodeToString(root);
	}
	public String setStringValueInArray(String pathToArray, int index, String value) {
	    ArrayNode arrayNode = prepareArrayNodeForInsert(pathToArray,index);
		arrayNode.insert(index,value);
		return nodeToString(root);
	}

    public String setStringValueInArray(ArrayNode arrayNode, int index, String value) {
        arrayNode.insert(index,value);
        return nodeToString(root);
    }

	public String setIntValueInArray(String pathToArray, int index, int value) {
		ArrayNode arrayNode = prepareArrayNodeForInsert(pathToArray,index);
		arrayNode.insert(index,value);
		return nodeToString(root);
	}

    public String setIntValueInArray(ArrayNode arrayNode, int index, int value) {
        arrayNode.insert(index,value);
        return nodeToString(root);
    }

	public String setDoubleValueInArray(String pathToArray, int index, double value) {
		ArrayNode arrayNode = prepareArrayNodeForInsert(pathToArray,index);
		arrayNode.insert(index,value);
		return nodeToString(root);
	}
    public String setDoubleValueInArray(ArrayNode arrayNode, int index, double value) {
        arrayNode.insert(index,value);
        return nodeToString(root);
    }
	public String setBooleanValueInArray(String pathToArray, int index, boolean value) {
		ArrayNode arrayNode = prepareArrayNodeForInsert(pathToArray,index);
		arrayNode.insert(index,value);
		return nodeToString(root);
	}
    public String setBooleanValueInArray(ArrayNode arrayNode, int index, boolean value) {
        arrayNode.insert(index,value);
        return nodeToString(root);
    }

    public ArrayNode prepareArrayNodeForInsert(String pathToArray, int index) {
	    ArrayNode node = getArrayNodeByPath(pathToArray);
        node.remove(index);
        return node;
	}

	public ArrayNode getArrayNodeByPath(String pathToArray) {
        JsonNode node = findNode(pathToArray);
        if (node.isArray()) {
            return (ArrayNode)node;
        }
        else throw new RuntimeException ("Path\n"+pathToArray+"\ndoes not point to Array element.");

    }

    public ObjectNode getObjectNodeByPath (String path) {
        JsonNode node = findNode(path);
        if (node.isObject()) {
            return (ObjectNode)node;
        }
        throw new RuntimeException("Path\n"+path+"\n does not point to an object element.");
    }

	public String setStringFieldValueObject(String path, String fieldName, String fieldValue) {
		ObjectNode node = getObjectNodeByPath(path);
		node.put(fieldName, fieldValue);
		return nodeToString(root);
	}

    public String setStringFieldValueObject(ObjectNode node, String fieldName, String fieldValue) {
        node.put(fieldName, fieldValue);
        return nodeToString(root);
    }

    public String setIntFieldValueObject(String path, String fieldName, int fieldValue) {
        ObjectNode node = getObjectNodeByPath(path);
        node.put(fieldName, fieldValue);
        return nodeToString(root);
    }

    public String setIntFieldValueObject(ObjectNode node, String fieldName, int fieldValue) {
        node.put(fieldName, fieldValue);
        return nodeToString(root);
    }

    public String setDoubleFieldValueObject(String path, String fieldName, double fieldValue) {
        ObjectNode node = getObjectNodeByPath(path);
        node.put(fieldName, fieldValue);
        return nodeToString(root);
    }

    public String setDoubleFieldValueObject(ObjectNode  node, String fieldName, double fieldValue) {
        node.put(fieldName, fieldValue);
        return nodeToString(root);
    }

    public String setBooleanFieldValueObject(String path, String fieldName, boolean fieldValue) {
        ObjectNode node = getObjectNodeByPath(path);
        node.put(fieldName, fieldValue);
        return nodeToString(root);
    }

    public String setBooleanFieldValueObject(ObjectNode node, String fieldName, boolean fieldValue) {
        node.put(fieldName, fieldValue);
        return nodeToString(root);
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


	public JsonNode findParentNodeByPath(String path) {
		String [] tokens = splitPathIntoTokens(path);
		JsonNode parent = findParentNodeByPath(tokens);
		return parent;
	}

	public String getFieldValueFromNode(JsonNode node, String fieldName) {
		if (node.isObject()) {
			if (node.has(fieldName)) {
				JsonNode n = node.get(fieldName);
				return getValueFromNode(n);
			}
			throw new RuntimeException("Given node does not have field "+fieldName);
		}
		throw new RuntimeException("Given node does not have fields.");

	}

	public String getValueFromNode(JsonNode node) {
		return nodeToString(node);
	}

	public String getValueFromArray(JsonNode array, int index) {
	    if (array.isArray()) {
            if (index>=array.size()) throw new RuntimeException("Provided index is outside of boundaries of provided array.");
            JsonNode node = array.get(index);
            if (node.isObject())
                throw new RuntimeException("Provided index " + index + " points to an object node, not a primitive value");
            return getValueFromNode(node);
        }
        throw new RuntimeException("Provided node is not array");
	}

	public String getValueFromPath(String path) {
		JsonNode node = findNode(path);
		return nodeToString(node);
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
