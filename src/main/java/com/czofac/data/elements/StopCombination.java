package com.czofac.data.elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.util.StringUtils;

import com.czofac.utils.EntitiesEnumerator;
@Configurable
public class StopCombination {
	private final String stopDescription;
	private final int id;
	private  final List<Token> tokens;
	//map will hold count of tokens in stop combination
	//that is needed to produce a match
	//for example stop combination is BORA BORA 
	// so the map will hold only one entry
	//where key is id of token BORA and value 2 since you need
	//2 of this token to produce a match
	//key - token id , value - number of tokens of this kind in stop combination
	private final Map<Integer,Integer> tokenCounts;
	
	private static EntitiesEnumerator enumerator;
	
	public StopCombination(String stopCombination){
		if(enumerator== null){
			enumerator = new EntitiesEnumerator();
		}
		if(StringUtils.isEmpty(stopCombination)){
			throw new IllegalArgumentException("Stop combination could not be empty");
		}
		
		stopDescription = stopCombination;
		String[] tokenStrings = stopDescription.split("\\s+");
		Arrays.sort(tokenStrings);
		tokens = new ArrayList<Token>(tokenStrings.length);
		tokenCounts = new HashMap<Integer,Integer>(tokenStrings.length);
		for(String currTokenString : tokenStrings){
			int currTokenId = enumerator.enumerateToken(currTokenString);
			if(tokenCounts.containsKey(currTokenId)){
				tokenCounts.put(currTokenId, tokenCounts.get(currTokenId)+1);
			}else{
				tokenCounts.put(currTokenId,1);
			}
			tokens.add(new Token(currTokenString,currTokenId));
		}
		id = enumerator.enumerateStopCombination(tokens);
	}

	public String getStopDescription() {
		return stopDescription;
	}

	public int getId() {
		return id;
	}

	public  List<Token> getTokens() {
		return tokens;
	}

	public Map<Integer, Integer> getTokenCounts() {
		return tokenCounts;
	}
}
