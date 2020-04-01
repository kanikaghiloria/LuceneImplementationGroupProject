package lucenegroupproject;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class QueryIndex {


    // Limit the number of search results we get

    public static void main(String[] args) throws IOException, ParseException, org.apache.lucene.queryparser.classic.ParseException {

        // Analyzer used by the query parser, the same as the one used in index
        // EnglishAnalyzer shall be changed later
//        Analyzer analyzer = new EnglishAnalyzer();

        AnalyzerClass analyzer = new AnalyzerClass();
        // Open the folder that contains our search index
        Directory directory = FSDirectory.open(Paths.get(LuceneConstants.INDEX_DIRECTORY_PATH));

        // Create objects to read and search across the index
        DirectoryReader directoryReader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(directoryReader);
        indexSearcher.setSimilarity(analyzer.getSimilarity());
//        indexSearcher.setSimilarity(new BooleanSimilarity());

        // Open the topic file and split
//        String file = "topics";
//        String file = "Group/topics";
//        String content = new String(Files.readAllBytes(Paths.get(file)));
        String content = new String(Files.readAllBytes(Paths.get(LuceneConstants.TOPICS_FILE_PATH)));
        System.out.println("Indexing queries from topics file ...");
        String[] topics = content.split("<top>");

        // Delete the blank items
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < topics.length && topics.length > 0; i++) {
            if (topics[i] == null || "".equals(topics[i].trim().toString())) {
                continue;
            } else {
                list.add(topics[i]);
            }
        }
        String[] newTopics = new String[list.size()];
        for (int i = 0; i < newTopics.length; i++) {
            newTopics[i] = list.get(i);
        }

        // Divide the topic into four parts - num, title, desc and narrative
        String[] NUMBs = new String[newTopics.length];
        String[] TITLs = new String[newTopics.length];
        String[] DESCs = new String[newTopics.length];
        String[] NARRs = new String[newTopics.length];

        for (int i = 0; i < newTopics.length; i++) {
            // Load the text of this topic
            String[] split = newTopics[i].split("<num> Number: |<title> |<desc> Description: |<narr> Narrative: |</top>");

            NUMBs[i] = split[1].trim();
            TITLs[i] = split[2].trim();
            DESCs[i] = split[3].trim();
            NARRs[i] = split[4].trim();
        }

        // Detect if the words in title has been adopted in the description part
        // Define a query based on title and description parts
        String[] simpleQueries = new String[newTopics.length];
        for (int i = 0; i < newTopics.length; i++) {
            if (TITLs[i].contains(", ")) {
                String[] split = TITLs[i].split(", ");
                for (String txt : split) {
                    if (DESCs[i].contains(txt.trim())) {
                        simpleQueries[i] = DESCs[i];
                    } else {
                        simpleQueries[i] = "Considering " + txt.trim() + ", " + DESCs[i];
                    }
                }
            } else {
                if (DESCs[i].contains(TITLs[i].trim())) {
                    simpleQueries[i] = DESCs[i];
                } else {
                    simpleQueries[i] = "Considering " + TITLs[i].trim() + ", " + DESCs[i];
                }
            }
        }

        // Find "not relevant", "unrelated to" and "irrelevant" in narrative part.
        // Then, complete the query with the narrative part.
        String[] Queries = new String[newTopics.length];
        for (int i = 0; i < newTopics.length; i++) {
            String[] split = NARRs[i].split("\\.|\\. |\\?|\\? |; ");
            for (String txt : split) {
                if (txt.contains("not relevant") || txt.contains("irrelevant") || txt.contains(("unrelated to"))) {
                    Queries[i] = simpleQueries[i] + " But attention, " + txt.trim() + ".";
                } else {
                    Queries[i] = simpleQueries[i].toLowerCase().trim();
                }
            }
        }
        System.out.println("Parsing query...");
        // Create the query parser. The default search field is "words"
//        QueryParser parser = new QueryParser(LuceneConstants.TEXT, analyzer.getAnalyzer());
        QueryParser parser = new MultiFieldQueryParser(new String[] {LuceneConstants.HEADLINE, LuceneConstants.TEXT,
                LuceneConstants.BYLINE, LuceneConstants.HEADING, LuceneConstants.HEADER, LuceneConstants.FOOTNOTE, LuceneConstants.ADDRESS,
                LuceneConstants.AGENCY, LuceneConstants.SUMMARY, LuceneConstants.TITLE, LuceneConstants.US_BUREAU, LuceneConstants.US_DEPARTMRNT,
                LuceneConstants.DATE_LINE, LuceneConstants.SUBJECT}, analyzer.getPerFieldAnalyzer());

        String queryString = "";
        List<String> resultsList = new ArrayList<>();

        for (int j = 0; j < Queries.length; j++) {

            // Trim leading and trailing whitespace from the query
            queryString = Queries[j].trim();


            // If the user entered a query string
            if (queryString.length() > 0) {

                // Parse the query with the parser
//                System.out.println(j + " --------Query ---------: " + queryString);

//                BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
//                if (queryString.contains("But attention,"))
//                {
////                    System.out.println(("Query with But Attention, : " + queryString));
//                    str = queryString.split("But attention,");
//                    str[1].replace("But attention,", "");
//                    Query queryInclude = new TermQuery(new Term(LuceneConstants.TEXT, str[0]));
//                    Query queryNotInclude = new TermQuery(new Term(LuceneConstants.TEXT, str[1]));
////                    System.out.println("Query Include: " + queryInclude.toString());
////                    System.out.println("Query not include: " + queryNotInclude.toString());
//                    booleanQuery.add(queryInclude, BooleanClause.Occur.MUST);
//                    booleanQuery.add(queryNotInclude, BooleanClause.Occur.MUST_NOT);
//                }else{
//                    Query queryInclude = new TermQuery(new Term(LuceneConstants.TEXT, queryString));
//                    booleanQuery.add(queryInclude, BooleanClause.Occur.MUST);
////                    System.out.println("Query Include: " + queryInclude.toString());
//                }

                ScoreDoc[] hitsInclude, hitsExclude = new ScoreDoc[0];
//                List<ScoreDoc> hitsInclude = new ArrayList<>();
//                List<ScoreDoc> hitsExclude = new ArrayList<>();

                if(queryString.contains("But attention,"))
                {
                    String[] queries = queryString.split("But attention,");
//                    queries[1].replace("relevant", "");
//                    queries[1].replace("irrelavant", "");
//                    if(queries[1].contains("unless"))
//                    {
//                        String[] str = queries[1].split("unless");
//                        queries[0] = queries[0] + str[1];
//                        queries[1] = str[0];
//                    }
//                    System.out.println(j + " - 1st part of query: " + queries[0]);
//                    System.out.println(j + " - 2nd part of query: " + queries[1]);
                    Query queryInclude = parser.parse(parser.escape(queries[0]));
                    Query queryExclude = parser.parse(parser.escape(queries[1]));

                    hitsInclude = indexSearcher.search(queryInclude, LuceneConstants.MAX_SEARCH_RESULTS).scoreDocs;
                    hitsExclude = indexSearcher.search(queryExclude, LuceneConstants.MAX_SEARCH_RESULTS).scoreDocs;
                } else {
                    Query query = parser.parse(parser.escape(queryString));
//                    System.out.println(j + " - Query: " + queryString);
                    hitsInclude = indexSearcher.search(query, LuceneConstants.MAX_SEARCH_RESULTS).scoreDocs;
                }
//                System.out.println("hitsInclude.length: "+ hitsInclude.length);
//                System.out.println("hitsExclude.length: "+ hitsExclude.length);


//                Query query = new QueryParser(LuceneConstants.TEXT, analyzer.getAnalyzer()).parse(booleanQuery.toString());
//                System.out.println(j + " Query: " + query.toString());
                for (int i=0; i<hitsInclude.length; i++)
                {
                    for (int k=0; k<hitsExclude.length; k++)
                    {
                        if (hitsInclude[i] == hitsExclude[k])
                        {
                            hitsInclude = removeTheElement(hitsInclude, i);
                        }
                    }
                }

                // Get the set of results
//                ScoreDoc[] hits = indexSearcher.search(query, LuceneConstants.MAX_SEARCH_RESULTS).scoreDocs;

//                ScoreDoc[] hits = indexSearcher.search(booleanQuery, LuceneConstants.MAX_SEARCH_RESULTS).scoreDocs;

                // Save the results
                ArrayList<String> docNums = new ArrayList<String>();

//                System.out.println("hits.length: " + hits.length);
//                for (int i = 0; i < hitsInclude.length; i++)
                int totalResults;
                if (hitsInclude.length < 1000)
                    totalResults = hitsInclude.length;
                else
                    totalResults = 1000;

                for (int i = 0; i < totalResults; i++)
                {
                    Document hitDoc = indexSearcher.doc(hitsInclude[i].doc);
//                    System.out.println("i: "+i +" //query : " + j);

//                    if(docNums.contains(hitDoc.get("DOCNO").trim())) {
                    if(docNums.contains(hitDoc.get(LuceneConstants.DOCUMENT_NUMBER).trim())) {
//                        System.out.println("In IF");
                        continue;
                    }
                    else{
//                        String sResults = (j + 401) + " 0 " + hitDoc.get("DOCNO").trim() + " " + (i + 1) + " " + hits[i].score + " run-tag";
                        String sResults = (j + 401) + " 0 " + hitDoc.get(LuceneConstants.DOCUMENT_NUMBER).trim() + " " + (i + 1) + " " + hitsInclude[i].score + " run-tag";
                        String outString = "Getting the result " + (i + 1) + " of query " + (j + 1) + "...";
                        resultsList.add(sResults);
                        System.out.println(outString);
//                        docNums.add(hitDoc.get("DOCNO").trim());
                        docNums.add(hitDoc.get(LuceneConstants.DOCUMENT_NUMBER).trim());
                    }

                }
            }
        }

        // Create a results file
//        File results = new File("results");
        File results = new File(LuceneConstants.OUTPUT_FILE_PATH);
        results.createNewFile();
        System.out.println("\nCreating results file...");
//        BufferedWriter resultsWriter = new BufferedWriter(new FileWriter("../trec_eval-9.0.7/group/results"));
        BufferedWriter resultsWriter = new BufferedWriter(new FileWriter(LuceneConstants.OUTPUT_FILE_PATH));
        for (String item : resultsList) {
            resultsWriter.write(item);
            resultsWriter.newLine();
        }
        System.out.println("\nResults file is successfully created!");

        resultsWriter.close();
        directoryReader.close();
        directory.close();

    }
    public static ScoreDoc[] removeTheElement(ScoreDoc[] arr, int index)
    {
        // If the array is empty
        // or the index is not in array range
        // return the original array
        if (arr == null
                || index < 0
                || index >= arr.length) {

            return arr;
        }

        // Create another array of size one less
        ScoreDoc[] anotherArray = new ScoreDoc[arr.length - 1];

        // Copy the elements from starting till index
        // from original array to the other array
        System.arraycopy(arr, 0, anotherArray, 0, index);

        // Copy the elements from index + 1 till end
        // from original array to the other array
        System.arraycopy(arr, index + 1,
                anotherArray, index,
                arr.length - index - 1);

        // return the resultant array
        return anotherArray;

    }
}
