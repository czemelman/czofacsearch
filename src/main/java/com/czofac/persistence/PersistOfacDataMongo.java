package com.czofac.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czofac.data.elements.StopCombination;
import com.czofac.data.elements.Token;
import com.czofac.models.SdnEntryModel;
import com.czofac.models.StopCombinationModel;
import com.czofac.models.TokenModel;
import com.czofac.repositories.SdnEntryRepository;
import com.czofac.repositories.StopCombinationRepository;
import com.czofac.repositories.TokenRepository;
import com.czofac.tempuri.sdnlist.SdnList.SdnEntry;

public class PersistOfacDataMongo implements PersistOfacData {

	@Autowired
	private  TokenRepository tokenRepository;
	
	@Autowired
	private StopCombinationRepository stopCombRepository;
	
	@Autowired
	private SdnEntryRepository sdnEntryRepository;
	
	@Override
	public void mergeToken(Token token) {
		TokenModel tokenToPersist = new TokenModel(token);
		tokenRepository.save(tokenToPersist);

	}

	@Override
	public void mergeStopCombination(StopCombination stopCombination) {
		StopCombinationModel stopCombToPersist = new StopCombinationModel(stopCombination);
		stopCombRepository.save(stopCombToPersist);

	}

	@Override
	public void mergeSdnEntry(SdnEntry sdnEntry) {
		SdnEntryModel sdnEntryToPersist = new SdnEntryModel(sdnEntry);
		sdnEntryRepository.save(sdnEntryToPersist);

	}

}
