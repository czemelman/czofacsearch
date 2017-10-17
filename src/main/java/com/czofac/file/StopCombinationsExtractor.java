package com.czofac.file;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.czofac.data.elements.StopCombination;
import com.czofac.tempuri.sdnlist.SdnList.SdnEntry;
import com.czofac.tempuri.sdnlist.SdnList.SdnEntry.AkaList.Aka;
import com.czofac.tempuri.sdnlist.SdnList.SdnEntry.IdList.Id;
import com.czofac.utils.EntitiesEnumerator;
import com.czofac.utils.StringNormalizer;
@Component
public class StopCombinationsExtractor {
	@Autowired
	private  EntitiesEnumerator entitiesEnumerator;
	
	
	private static Set<String> EXCLUDED_ID_TYPES = createExcludedIdTypesSet();
	private static Set<String> createExcludedIdTypesSet(){
		Set<String> excludedIdTypes = new HashSet<String>();
		excludedIdTypes.add("Additional Sanctions Information -");
		excludedIdTypes.add("For more information, please see:");
		return excludedIdTypes;
	}
	/**
	 * 
	 * @param sdnEntry
	 * @return List of stop of raw (not yet normalized) stop combinations
	 * main stop combination would be first + last name from main entry
	 * additional stop combination  would be extracted for each aka entry (concatenating first + last names)
	 * if some id numbers are present stop combination would be created for each id
	 */
	public  List<String> getRawStopCombinationsFromSdnEntry(SdnEntry sdnEntry){
		List<String> rawStopNames = new ArrayList<String>();
		//starting with main entry
		StringBuilder mainEntryStopCombination =new StringBuilder();
		if(sdnEntry.getFirstName() != null){
			mainEntryStopCombination.append(sdnEntry.getFirstName());
		}
		
		if(sdnEntry.getLastName() != null){
			if(mainEntryStopCombination.length() >0){
				mainEntryStopCombination.append(" ");
			}
			mainEntryStopCombination.append(sdnEntry.getLastName());
		}
		if(mainEntryStopCombination.length() >0){
			rawStopNames.add(mainEntryStopCombination.toString());
		}
		
		//process AKAs
		if(sdnEntry.getAkaList() !=  null){
			for(Aka currEka : sdnEntry.getAkaList().getAka()){
				StringBuilder akaStopCombination =new StringBuilder();
				if(currEka.getFirstName() != null){
					akaStopCombination.append(currEka.getFirstName());
				}
				if(currEka.getLastName() != null){
					if(akaStopCombination.length() >0){
						akaStopCombination.append(" ");
					}
					akaStopCombination.append(currEka.getLastName());
				}
				if(akaStopCombination.length() >0){
					rawStopNames.add(akaStopCombination.toString());
				}
			}
		}
		
		//process iDs
		if(sdnEntry.getIdList() != null){
			for(Id currId : sdnEntry.getIdList().getId()){
				String idType = currId.getIdType().trim();
				if(!EXCLUDED_ID_TYPES.contains(idType)){
					rawStopNames.add(currId.getIdNumber());
				}
			}
		}
		
		return rawStopNames;
	}
	
	public  List<String> normalizeStopCombinations(List<String> rawStopCombinations){
		List<String> normalizedStopNamesToReturn = new ArrayList<String>();
		for(String currRawStopCombination :rawStopCombinations){
			List<String> normalizedStopNames = StringNormalizer.normalizeStopCombinationString(currRawStopCombination);
			normalizedStopNamesToReturn.addAll(normalizedStopNames);
		}
		return normalizedStopNamesToReturn;
	}
	
	public  List<StopCombination> convertStopCombinationStringListIntoDataElements(List<String> normalizedStopCombinations){
		List<StopCombination> stopCombDataElementsToReturn = new ArrayList<StopCombination>(normalizedStopCombinations.size());
		for(String currStopCombination : normalizedStopCombinations){
			StopCombination stopCombinationToAdd = new StopCombination(currStopCombination,entitiesEnumerator);
			stopCombDataElementsToReturn.add(stopCombinationToAdd);
		}
		return stopCombDataElementsToReturn;
	}
	
	
	public  List<StopCombination> getStopCombinationsFromSdnEntry(SdnEntry sdnEntry){
		List<String> rawStopCombinations = getRawStopCombinationsFromSdnEntry(sdnEntry);
		List<String> normalizedStopCombinations = normalizeStopCombinations(rawStopCombinations);
		List<StopCombination> stopCombinationsToReturn =  convertStopCombinationStringListIntoDataElements(normalizedStopCombinations);
		
		//remove duplicates in case there are any
		stopCombinationsToReturn= stopCombinationsToReturn.stream().distinct().collect(Collectors.toList());
		return stopCombinationsToReturn;
	}
	
}
