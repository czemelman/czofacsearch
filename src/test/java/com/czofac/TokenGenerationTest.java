package com.czofac;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

import com.czofac.data.elements.StopCombination;
import com.czofac.utils.EntitiesEnumerator;
import com.czofac.utils.StringNormalizer;

@RunWith(SpringRunner.class)
@SpringBootTest

public class TokenGenerationTest {
	@Autowired
	EntitiesEnumerator entEnumerator;
	@Test
	public void testTokenStringNormalizationMultiSeparator(){
		//testing that stop combination string is normalized properly
		//that every hyphen fork a new stop combination  
		List<String> stopNameStrings = null;
		String[] expectedMultiSeparatorTest = new String[] {"ABU NIDAL SADAM BEN HAIM", "ABUNIDAL SADAM BEN HAIM", "ABU NIDAL SADAM BENHAIM", "ABUNIDAL SADAM BENHAIM"};
		stopNameStrings = StringNormalizer.normalizeStopCombinationString("'abu-nidal' $saD^am,beN-Haim");
		assertThat(stopNameStrings, containsInAnyOrder(expectedMultiSeparatorTest));
			    	
	}
	
	@Test
	public void testTokenStringNormalizationOneWord(){
		//Testing that  non alphanumeric character are removed properly
		//and string case is converted to upper
		List<String> stopNameStrings = null;
		String[] expectedMultiSeparatorTest = new String[] {"DARTAGNAN"};
		stopNameStrings = StringNormalizer.normalizeStopCombinationString("d'artagnan");
		assertThat(stopNameStrings, containsInAnyOrder(expectedMultiSeparatorTest));
		assert(stopNameStrings.size()==1);
			    	
	}
	
	@Test
	public void testStopNameEquality(){
		//testing that 2 stop names would be equal even though their tokens
		//are ordered differently within stop combination
		
		StopCombination stopWd1 = new StopCombination("AMIN MUHHAMAD AMIN",entEnumerator);
		StopCombination stopWd2 = new StopCombination("MUHHAMAD   AMIN  AMIN",entEnumerator);
		StopCombination stopWd3 = new StopCombination("MUHHAMAD   AMIN",entEnumerator);
		
		assert(stopWd1.equals(stopWd2));
		assert(!stopWd1.equals(stopWd3));
		assert(!stopWd2.equals(stopWd3));
	}
}
