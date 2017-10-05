package com.czofac.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.czofac.tempuri.sdnlist.SdnList.SdnEntry;


@Document(collection="sdnentries")
public class SdnEntryModel {
	@Id
	private int uid;
	private SdnEntry sdnEntry;
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public SdnEntry getSdnEntry() {
		return sdnEntry;
	}
	public void setSdnEntry(SdnEntry sdnEntry) {
		this.sdnEntry = sdnEntry;
	}
	public SdnEntryModel() {
		
	}
	public SdnEntryModel(SdnEntry sdnEntry) {
		this.uid = sdnEntry.getUid();
		this.sdnEntry = sdnEntry;
	}
}
