package com.czofac.persistence;

import com.czofac.data.elements.StopCombination;
import com.czofac.data.elements.Token;
import com.czofac.tempuri.sdnlist.SdnList.SdnEntry;

public interface PersistOfacData {
	public void mergeToken(Token token);
	public void mergeStopCombination(StopCombination stopCombination);
	public void mergeSdnEntry(SdnEntry sdnEntry);
	
	public default void mergeTokenAll(Iterable<Token> tokens){
		for(Token currToken:tokens){
			mergeToken(currToken);
		}
	}
	public default void mergeStopCombinationAll(Iterable<StopCombination> stopCombinations){
		for(StopCombination currStopCombination : stopCombinations){
			mergeStopCombination(currStopCombination);
		}
	}
	
	public default void mergeSdnEntryAll(Iterable<SdnEntry> sdnEntrys){
		for(SdnEntry currSdnEntry : sdnEntrys){
			mergeSdnEntry(currSdnEntry);
		}
	}
}
