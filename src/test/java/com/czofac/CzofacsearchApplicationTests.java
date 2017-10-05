package com.czofac;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.czofac.file.FIleParser;
import com.czofac.tempuri.sdnlist.SdnList;

@RunWith(SpringRunner.class)
@SpringBootTest

public class CzofacsearchApplicationTests {

	@Autowired
	FIleParser fparser;
	@Test
	public void contextLoads() {
	}

	@Test
	public void parseFileTest() throws Throwable{
	
			String filePath = "C:\\Temp\\sdn\\consolidated.xml";
			fparser.parseSDNFIle(filePath);
			File file = new File(filePath);
			JAXBContext jaxbContext = JAXBContext.newInstance(SdnList.class);

	
	}
}
