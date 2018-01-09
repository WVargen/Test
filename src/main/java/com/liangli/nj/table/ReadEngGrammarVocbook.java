package com.liangli.nj.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.liangli.nj.bean.english_grammar_vocabulary_book;
import com.liangli.nj.database.DatabaseAccessor;
import com.liangli.nj.utils.Definition;

public class ReadEngGrammarVocbook {
	public static Map<String, List<english_grammar_vocabulary_book>> readEngGrammarVocbook2Map() {
		Map<String, List<english_grammar_vocabulary_book>> grammarVocMap = new HashMap<>();
		
		//Definition.printSqlInConsol = true;
		List<english_grammar_vocabulary_book> engGramVocList = DatabaseAccessor.get().getSelect()
				.select(english_grammar_vocabulary_book.class)
				.where("course=?", "grammar_basic_primary")
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
	
}
