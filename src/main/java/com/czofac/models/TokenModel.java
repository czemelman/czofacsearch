package com.czofac.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.czofac.data.elements.Token;

@Document(collection="tokens")
public class TokenModel {
	private String tokenName;
	private int tokenId;
	private boolean isSynonym;
	
	public TokenModel(){
		this.isSynonym = false;
	}
	
	public TokenModel(Token token){
		this.tokenId = token.getTokenId();
		this.tokenName = token.getTokenName();
		this.isSynonym = false;
	}
	public int getTokenId() {
		return tokenId;
	}
	public void setTokenId(int tokenId) {
		this.tokenId = tokenId;
	}
	
	@Id
	public String getTokenName() {
		return tokenName;
	}
	public void setTokenName(String tokenName) {
		this.tokenName = tokenName;
	}
	public boolean isSynonym() {
		return isSynonym;
	}
	public void setSynonym(boolean isSynonym) {
		this.isSynonym = isSynonym;
	}
	
	
	
	
}
