package com.czofac.data.elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.util.StringUtils;

import com.czofac.utils.EntitiesEnumerator;



public class StopCombination {
	private final String stopDescription;
	private final int id;
	private  final List<Token> tokens;
	private Set<Integer> belongToSdnEntries;
	//map will hold count of tokens in stop combination
	//that is needed to produce a match
	//for example stop combination is BORA BORA 
	// so the map will hold only one entry
	//where key is id of token BORA and value 2 since you need
	//2 of this token to produce a match
	//key - token id , value - number of tokens of this kind in stop combination
	private final Map<Integer,Integer> tokenCounts;
	
	private  EntitiesEnumerator enumerator;
	
	public StopCombination(String stopCombination ,EntitiesEnumerator entEnumerator){
		this.enumerator =entEnumerator;
		
		if(StringUtils.isEmpty(stopCombination)){
			throw new IllegalArgumentException("Stop combination could not be empty");
		}
		belongToSdnEntries = new HashSet<Integer>();
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

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object compareTo) {
		boolean result;
	    if((compareTo == null) || (getClass() != compareTo.getClass())){
	        result = false;
	    } // end if
	    else{
	    	StopCombination compareToStopCmb = (StopCombination)compareTo;
	    	if(compareToStopCmb.tokenCounts.size() != this.tokenCounts.size()){
	    		result = false;
	    	}else{
	    		result = true;
	    		for(Integer currTokenId : compareToStopCmb.tokenCounts.keySet()){
	    			Integer currTokenCount = this.tokenCounts.get(currTokenId);
	    			if(currTokenCount == null || !compareToStopCmb.tokenCounts.get(currTokenId).equals(currTokenCount)){
	    				result = false;
	    				break;
	    			}
	    		}
	    	}
	        
	    } // end else

	    
	    return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		StringBuilder concatenatedTokens = new StringBuilder();
		for(Token currToken: this.tokens){
			concatenatedTokens.append(currToken.getTokenName());
		}
		return concatenatedTokens.toString().hashCode();
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
	
		public static Comparator<StopCombination> StopCombinationByHashCodeComparator
	    = new Comparator<StopCombination>() {
	
			public int compare(StopCombination stopComb1, StopCombination stopComb2) {
			
			Integer stopComb1HashCode = stopComb1.hashCode();
			Integer stopComb2HashCode = stopComb2.hashCode();
			
			//ascending order
			return stopComb1HashCode.compareTo(stopComb2HashCode);
		
		
		}

	};

	public Set<Integer> getBelongToSdnEntries() {
		return belongToSdnEntries;
	}

	
}
