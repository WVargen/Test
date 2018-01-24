package com.liangli.nj.table;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.liangli.nj.bean.english_grammar_vocabulary_book;
import com.liangli.nj.database.DatabaseAccessor;

public class ReadEngGrammarVocbook {
	public static Map<String, List<english_grammar_vocabulary_book>> readGrammarBookMap(String... whereClause) {
		Map<String, List<english_grammar_vocabulary_book>> grammarVocMap = new LinkedHashMap<>();
		
//		Definition.printSqlInConsol = true;
		List<english_grammar_vocabulary_book> engGramVocList = DatabaseAccessor.get().getSelect()
				.select(english_grammar_vocabulary_book.class)
				.where(whereClause)
				.orderBy("displayOrder asc")
				.find(english_grammar_vocabulary_book.class);
		
		for (english_grammar_vocabulary_book engGramVoc : engGramVocList) {
			List<english_grammar_vocabulary_book> engRecord = new ArrayList<>();
			String period = engGramVoc.getPeriod();
			if (grammarVocMap.keySet().contains(period)) {
				engRecord = grammarVocMap.get(period);
			}
			engRecord.add(engGramVoc); 
			grammarVocMap.put(period, engRecord);
		}		
		return grammarVocMap;
	}
	
	public static Map<String, List<String>> readGrammarBookSimpleMap(String... whereClause) {
		Map<String, List<String>> grammarVocMap = new LinkedHashMap<>();
		
//		Definition.printSqlInConsol = true;
		List<english_grammar_vocabulary_book> engGramVocList = DatabaseAccessor.get().getSelect()
				.select(english_grammar_vocabulary_book.class)
				.where(whereClause)
				.orderBy("displayOrder asc")
				.find(english_grammar_vocabulary_book.class);
		
		for (english_grammar_vocabulary_book engGramVoc : engGramVocList) {
			List<String> engRecord = new ArrayList<>();
			String period = engGramVoc.getPeriod();
			if (grammarVocMap.keySet().contains(period)) {
				engRecord = grammarVocMap.get(period);
			}
			engRecord.add(engGramVoc.getName()); 
			grammarVocMap.put(period, engRecord);
		}		
		return grammarVocMap;
	}
	
}
