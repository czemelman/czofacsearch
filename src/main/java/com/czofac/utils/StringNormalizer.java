package com.czofac.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringNormalizer {
	
	private static final Map<Character, Character[]> STOP_NM_CHAR_SPECIAL_CASES = createStopNameSpecialCasesCharReplacementMap();
	
	private static Map<Character, Character[]> createStopNameSpecialCasesCharReplacementMap(){
		Map<Character, Character[]> stopNameCharReplacements = new HashMap<Character, Character[]>();
		stopNameCharReplacements.put(',', new Character[]{' '});
		stopNameCharReplacements.put(';', new Character[]{' '});
		stopNameCharReplacements.put('_', new Character[]{' ',Character.MIN_VALUE});
		stopNameCharReplacements.put('-', new Character[]{' ',Character.MIN_VALUE});
		return stopNameCharReplacements;
	}
	
	/**
	 * 
	 * @param stopComination - raw stop combination
	 * @return List of normalized stop combinations derived from the input raw stop combination
	 * 
	 * Function to normalize stop name
	 * Convert all characters to upper case 
	 * remove all non alphanumeric characters ilke quotes tilda  etc
	 * by default all non-alpahanumeric characters would be replaced 
	 * with empty string
	 * there are some exceptions like comma semicolon etc 
	 * where we put space as a replacement
	 * converting string to all upper case letters
	 * some characters that could be used to compound words like underscore  hyphen or dash
	 * would cause a new stop combination with different spelling
	 * to be created 
	 * example : raw stop name is AL-SHAHID
	 * we will create 2 different stop names
	 * one where "-" is replaced with space
	 * and one where 
	 * AL SHAHID and ALSHAHID
	 * 
	 */
	public static List<String> normalizeStopCombinationString(String stopComination){
		List<String> normalizedStopCombinations = new ArrayList<String>();
		List<StringBuilder> stringBuildersStopCombinations = new ArrayList<StringBuilder>();
		//create a default StringBuilder for stopcombination
		stringBuildersStopCombinations.add(new StringBuilder());
		char[] rawStopCombinationCharArray = stopComination.toCharArray();
		for(char currChar : rawStopCombinationCharArray){
			List<Character> charactersToAdd = new ArrayList<Character>();
			if(Character.isDigit(currChar)||Character.isLetter(currChar) || Character.isSpaceChar(currChar)){
				char charToAdd = Character.toUpperCase(currChar);
				charactersToAdd.add(charToAdd);
			}else{
				//non alpha-numeric character
				//check if it is one of the special cases
				if(STOP_NM_CHAR_SPECIAL_CASES.containsKey(currChar)){
					charactersToAdd = Arrays.asList(STOP_NM_CHAR_SPECIAL_CASES.get(currChar));
				}
			}
			//check if there is character to be added
			//if there are 2 characters first one would be added to all
			//stop name variants 
			//second one would fork a new variant of stop combination
			switch (charactersToAdd.size()){
				case 1:
					addCharToAllStrings(charactersToAdd.get(0),stringBuildersStopCombinations);
					break;
				case 2:
					//clone stop names that we already have
					List<StringBuilder> stopNamesCloned =  cloneList(stringBuildersStopCombinations);
					//add first character to stop names that are currently in place
					addCharToAllStrings(charactersToAdd.get(0),stringBuildersStopCombinations);
					//check if second character is not empty
					if(!charactersToAdd.get(1).equals(Character.MIN_VALUE)){
						addCharToAllStrings(charactersToAdd.get(1),stopNamesCloned);
					}
					//add cloned list to the original list
					stringBuildersStopCombinations.addAll(stopNamesCloned);
					break;
					
			}
			
		}
		if(stringBuildersStopCombinations != null && stringBuildersStopCombinations.size() >0){
			
			normalizedStopCombinations  = stringBuilderListToStringList(stringBuildersStopCombinations);
		}
		return normalizedStopCombinations;
	}
	private static void addCharToAllStrings(char charToAdd, List<StringBuilder> stringsToApply){
		for(StringBuilder currStringBuilder : stringsToApply){
			currStringBuilder.append(charToAdd);
		}
	}
	private static   List<StringBuilder> cloneList(List<StringBuilder> list) {
	    List<StringBuilder> clone = new ArrayList<StringBuilder>(list.size());
	    for (StringBuilder item : list) {
	    	clone.add(new StringBuilder(item));
	    }
	    return clone;
	}
	
	private static List<String> stringBuilderListToStringList(List<StringBuilder> listOfStringBuilders){
		List<String> listToReturn = new ArrayList<String>(listOfStringBuilders.size());
	    for(StringBuilder currStrBldr : listOfStringBuilders ){
	    	
	    	listToReturn.add(currStrBldr.toString());
		}
	    return listToReturn;
	}

}
