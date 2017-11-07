package test_word;

public class VocabularyBean{
	private String Vocabulary;
	private String Pronunciation;
	private String Explain;
	private String Example;
	
	static final int data_type_Voc = 0;
	static final int data_type_Pro = 1;
	static final int data_type_Exp = 2;
	static final int data_type_Exa = 3;
	static final int data_type_count = 4;
	public VocabularyBean(String vocabulary, String pronunciation, String explain, String example) {
		super();
		Vocabulary = vocabulary;
		Pronunciation = pronunciation;
		Explain = explain;
		Example = example;
	}
	public String getVocabulary() {
		return Vocabulary;
	}
	public void setVocabulary(String vocabulary) {
		Vocabulary = vocabulary;
	}
	public String getPronunciation() {
		return Pronunciation;
	}
	public void setPronunciation(String pronunciation) {
		Pronunciation = pronunciation;
	}
	public String getExplain() {
		return "解释:\r\n" + Explain;
	}
	public void setExplain(String explain) {
		Explain = explain;
	}
	public String getExample() {
		return "例句:\r\n" + Example;
	}
	public void setExample(String example) {
		Example = example;
	}
	
	
	
}