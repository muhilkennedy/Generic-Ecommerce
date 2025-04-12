package com.platform.hibernate.search;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.en.EnglishPossessiveFilterFactory;
import org.apache.lucene.analysis.en.PorterStemFilterFactory;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilterFactory;
import org.apache.lucene.analysis.ngram.NGramFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurationContext;
import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurer;
import org.springframework.context.annotation.Configuration;

/**
 * @author I339628
 * default configurer used for hibernate search.
 */
@Configuration
public class AnalysisConfigurer implements LuceneAnalysisConfigurer {

	@Override
	public void configure(LuceneAnalysisConfigurationContext context) {
		context.analyzer("name_analyzer").custom()
			.tokenizer(StandardTokenizerFactory.class) // Splits words at punctuation characters, removing punctuations. 
			.tokenFilter(LowerCaseFilterFactory.class) // converts keywords to lower case
			.tokenFilter(SnowballPorterFilterFactory.class)
			.param("language", "English") // snowball is language analyser, matches english words
			//.tokenFilter(EdgeNGramFilterFactory.class) // startswith based search
			.tokenFilter(NGramFilterFactory.class) // contains based search
			.param("minGramSize", "2") 
			.param("maxGramSize", "15"); // muhil -> mu,um, il, mh, hu, muh, hil, etc
		
		// Custom "english" analyzer -> @FullTextField(analyzer = "english")
        /*context.analyzer("english").custom()
            .tokenizer(StandardTokenizerFactory.class) // Splits text into words
            .tokenFilter(EnglishPossessiveFilterFactory.class) // Removes possessive (e.g., "John’s" → "John")
            .tokenFilter(LowerCaseFilterFactory.class) // Converts all text to lowercase
            .tokenFilter(ASCIIFoldingFilterFactory.class) // Converts special characters (e.g., é → e)
            .tokenFilter(StopFilterFactory.class) // Removes common stop words (e.g., "the", "is", "and")
            .tokenFilter(PorterStemFilterFactory.class); // Applies stemming (e.g., "running" → "run")*/

        // Custom "name_analyzer" for case-insensitive name searches
        /*context.analyzer("name_search").custom()
            .tokenizer(StandardTokenizerFactory.class)
            .tokenFilter(LowerCaseFilterFactory.class); // Case-insensitive search*/
	}

}
