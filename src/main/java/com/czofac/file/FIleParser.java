package com.czofac.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czofac.models.SdnEntryModel;
import com.czofac.repositories.SdnEntryRepository;
import com.czofac.tempuri.sdnlist.SdnList;
import com.czofac.tempuri.sdnlist.SdnList.SdnEntry;


@Service
public class FIleParser {
	private final static String SDN_ENTRY_START = "<sdnEntry>";
	private final static String SDN_ENTRY_END = "</sdnEntry>";
	
	private final static String SDN_LIST_START = "<sdnList>";
	private final static String SDN_LIST_END = "</sdnList>";
	private final static int MAX_ENTITITES_IN_CHUNK =100;
	
	@Autowired
	SdnEntryRepository sdnEntryRepository;
	
	public void parseSDNFIle(String sdnFilePath)throws Throwable{
		try (BufferedReader br = new BufferedReader(new FileReader(sdnFilePath))) {

			String line;
			StringBuilder xmlChunk = new StringBuilder();
			boolean sdnEntryStarted = false;
			int entitiesInCurrChunk = 0;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				boolean skipLine = false;
				if(line.equals(SDN_ENTRY_START)){
					//validate that previous entity has ended
					if(sdnEntryStarted){
						throw new Exception("Invalid file. entity tag not closed properly.");
					}else{
						sdnEntryStarted = true;
					}
				}else if(line.equals(SDN_ENTRY_END)){
					//validate that entity tag start was read before end tag
					if(!sdnEntryStarted){
						throw new Exception("Invalid file. entity end tag appeared before start tag.");
					}else{
						entitiesInCurrChunk++;
						sdnEntryStarted =false;
					}
				}else{
					//if entity tag has not started yet
					//skip line
					if(!sdnEntryStarted){
						skipLine =true;
					}
					
				}
				if(!skipLine){
					xmlChunk.append(line);
					
					//if we reached maximum number of entities in chunk
					//parse string into appropriate structures
					if(entitiesInCurrChunk ==MAX_ENTITITES_IN_CHUNK){
						parseEntitiesIntoDataStructures(entitiesInChunkToSdnListXmlString(xmlChunk));
						//erase current chunk
						xmlChunk.delete(0, xmlChunk.length());
						//reset counter
						entitiesInCurrChunk= 0;
					}
				}
			}
			
			//check if there are any leftover entities that were not parsed
			if(xmlChunk.length()>0){
				parseEntitiesIntoDataStructures(entitiesInChunkToSdnListXmlString(xmlChunk));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	private String entitiesInChunkToSdnListXmlString(final StringBuilder entitiesInchunk ){
		StringBuilder entitiesListBlock = new StringBuilder(SDN_LIST_START);
		entitiesListBlock.append(entitiesInchunk);
		entitiesListBlock.append(SDN_LIST_END);
		return entitiesListBlock.toString();
	}
	
	public void parseEntitiesIntoDataStructures(String entitiesBlock) throws Throwable{
		InputStreamReader isr = new InputStreamReader(IOUtils.toInputStream(entitiesBlock,Charset.defaultCharset()));
		JAXBContext jaxbContext = JAXBContext.newInstance(SdnList.class);

		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		SdnList sdnLst = (SdnList) jaxbUnmarshaller.unmarshal(isr);
		parseSdnEntries(sdnLst.getSdnEntry());
	}
	
	private void parseSdnEntries(List<SdnEntry> sdnEntries){
		for(SdnEntry currentEntry : sdnEntries){
			SdnEntryModel currentSdnModel = new SdnEntryModel(currentEntry);
			if(!sdnEntryRepository.exists(currentSdnModel.getUid())){
				sdnEntryRepository.save(currentSdnModel);
			}else{
				System.out.println("uid " + currentEntry.getUid() + " already exist");
			}
			//sdnEntryRepository.save(currentSdnModel);
			
		}
	}
}
