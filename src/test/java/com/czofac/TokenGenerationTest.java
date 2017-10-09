package com.czofac;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

import com.czofac.utils.StringNormalizer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TokenGenerationTest {
	@Test
	public void testTokenStringNormalizationMultiSeparator(){
		List<String> stopNameStrings = null;
		String[] expectedMultiSeparatorTest = new String[] {"ABU NIDAL SADAM BEN HAIM", "ABUNIDAL SADAM BEN HAIM", "ABU NIDAL SADAM BENHAIM", "ABUNIDAL SADAM BENHAIM"};
		stopNameStrings = StringNormalizer.normalizeStopCombinationString("'abu-nidal' $saD^am,beN-Haim");
		assertThat(stopNameStrings, containsInAnyOrder(expectedMultiSeparatorTest));
			    	
	}
	
	@Test
	public void testTokenStringNormalizationOneWord(){
		List<String> stopNameStrings = null;
		String[] expectedMultiSeparatorTest = new String[] {"DARTAGNAN"};
		stopNameStrings = StringNormalizer.normalizeStopCombinationString("d'artagnan");
		assertThat(stopNameStrings, containsInAnyOrder(expectedMultiSeparatorTest));
		assert(stopNameStrings.size()==1);
			    	
	}
}
