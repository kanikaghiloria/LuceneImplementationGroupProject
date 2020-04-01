package lucenegroupproject;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.Similarity;

import java.util.HashMap;
import java.util.Map;

public class AnalyzerClass {
    public AnalyzerClass() { }

    public Analyzer getAnalyzer()
    {
        return new StandardAnalyzer(LuceneConstants.stopWordsSet);
//        return new EnglishAnalyzer(LuceneConstants.stopWordsSet);
//        return new EnglishAnalyzer();
    }
    public Analyzer getPerFieldAnalyzer()
    {
        Map<String,Analyzer> analyzerPerField = new HashMap<>();
        analyzerPerField.put(LuceneConstants.HEADLINE, new EnglishAnalyzer());
        analyzerPerField.put(LuceneConstants.TEXT, new EnglishAnalyzer());
        analyzerPerField.put(LuceneConstants.ABS, new WhitespaceAnalyzer());
        analyzerPerField.put(LuceneConstants.HEADER, new EnglishAnalyzer());
        analyzerPerField.put(LuceneConstants.FURTHER, new EnglishAnalyzer());
        analyzerPerField.put(LuceneConstants.SIGN_JOB, new WhitespaceAnalyzer());
        analyzerPerField.put(LuceneConstants.ADDRESS, new WhitespaceAnalyzer());
        analyzerPerField.put(LuceneConstants.AGENCY, new WhitespaceAnalyzer());
        analyzerPerField.put(LuceneConstants.SUMMARY, new EnglishAnalyzer());
        analyzerPerField.put(LuceneConstants.TITLE, new WhitespaceAnalyzer());
//        analyzerPerField.put(LuceneConstants.TITLE, new WhitespaceAnalyzer());


        return new PerFieldAnalyzerWrapper(new StandardAnalyzer(LuceneConstants.stopWordsSet), analyzerPerField);
    }
    public Similarity getSimilarity()
    {
        return new BM25Similarity();
    }
}
