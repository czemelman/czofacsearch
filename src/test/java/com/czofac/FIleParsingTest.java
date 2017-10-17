package com.czofac;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.czofac.file.FIleParser;
//using a special property file for unit test
//one of the reason to use a separate database
@TestPropertySource(locations = "classpath:application-test.properties")
@RunWith(SpringRunner.class)
@SpringBootTest
public class FIleParsingTest {
	@Autowired
	FIleParser fparser;
	
	
	@Test
	public void testFileParsing() throws Throwable{
		Resource resource = new ClassPathResource("sdnlist.xml");
		File fl = resource.getFile();
		fparser.parseSDNFIle(fl.getAbsolutePath());
		assert(fparser.getSdnEntries().size() ==4); //4
		assert(fparser.getStopCombinationsById().size()==8);//8
		assert(fparser.getTokenIdsByTokenName().size() ==21);//21
	}
}
