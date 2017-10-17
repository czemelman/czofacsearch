package com.czofac.data.elements;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


public class Token {
	private final String tokenName;
	private final int tokenId;
	public Token(String tokenName, int tokenId){
		this.tokenId = tokenId;
		this.tokenName = tokenName;
	}
	public String getTokenName() {
		return tokenName;
	}
	public int getTokenId() {
		return tokenId;
	}
}
