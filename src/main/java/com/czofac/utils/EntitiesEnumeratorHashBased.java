package com.czofac.utils;

import java.util.List;

import org.springframework.stereotype.Component;
import com.czofac.data.elements.Token;


/**
 * Helper class that assigns numeric Id to Ofac  data elements 
 * e.g stop combination or token
 * based on hashcode of the underlying strings
 */
@Component
public class EntitiesEnumeratorHashBased implements EntitiesEnumerator {
	/**
	 * generates token id 
	 * @param token
	 * @return int
	 */
	public int enumerateToken(String token){
		//Since there are not that many tokens 
		//collision is highly unlikely
		return token.hashCode();
	}
	/**
	 * 
	 * @param orderedTokens - list of tokens ordered alphabetically
	 * @return int - stop combination id
	 */
	public int enumerateStopCombination(List<Token> orderedTokens){
		//for now we try to generate unique
		//stop combination id by concatenating each token 
		//into one string and then generating hash code
		//of it
		StringBuilder concatenatedTokens = new StringBuilder();
		for(Token currToken: orderedTokens){
			concatenatedTokens.append(currToken.getTokenName());
		}
		return concatenatedTokens.toString().hashCode();
	}
}
