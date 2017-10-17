package com.czofac.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.czofac.data.elements.StopCombination;
import com.czofac.data.elements.Token;

@Document(collection="stopcombinations")
public class StopCombinationModel {
	int id;
	String description;
	@Indexed
	List<Integer> tokenIds;
	Map<Integer,InnerTokenModel> tokens;
	
	public StopCombinationModel() {
		
	}

	public StopCombinationModel(StopCombination stopCombinationDataElement) {
		this.description = stopCombinationDataElement.getStopDescription();
		this.id = stopCombinationDataElement.getId();
		tokenIds = new ArrayList<Integer>(stopCombinationDataElement.getTokenCounts().size());
		tokens = new HashMap<Integer,InnerTokenModel>(stopCombinationDataElement.getTokenCounts().size());
		for(Token currToken: stopCombinationDataElement.getTokens()){
			if(!tokens.containsKey(currToken.getTokenId())){
				InnerTokenModel innerTokenToAdd = new InnerTokenModel();
				innerTokenToAdd.tokenId = currToken.getTokenId();
				innerTokenToAdd.tokenDescription =  currToken.getTokenName();
				innerTokenToAdd.tokenCount = stopCombinationDataElement.getTokenCounts().get(currToken.getTokenId());
				tokens.put(innerTokenToAdd.getTokenId(), innerTokenToAdd);
				tokenIds.add(innerTokenToAdd.getTokenId());
			}
		}
	}	
	
	@Id
	public int getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	

	public void setId(int id) {
		this.id = id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	
	public List<Integer> getTokenIds() {
		return tokenIds;
	}

	public Map<Integer, InnerTokenModel> getTokens() {
		return tokens;
	}

	public void setTokenIds(List<Integer> tokenIds) {
		this.tokenIds = tokenIds;
	}

	public void setTokens(Map<Integer, InnerTokenModel> tokens) {
		this.tokens = tokens;
	}



	public class InnerTokenModel{
		private String tokenDescription;
		private int tokenId;
		//number of tokens with thisid needed to produce a match
		private int tokenCount;
		public String getTokenDescription() {
			return tokenDescription;
		}
		public int getTokenId() {
			return tokenId;
		}
		public int getTokenCount() {
			return tokenCount;
		}
		public void setTokenDescription(String tokenDescription) {
			this.tokenDescription = tokenDescription;
		}
		public void setTokenId(int tokenId) {
			this.tokenId = tokenId;
		}
		public void setTokenCount(int tokenCount) {
			this.tokenCount = tokenCount;
		}
	}
}
