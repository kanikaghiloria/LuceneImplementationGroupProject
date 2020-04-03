package lucenegroupproject;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;


public class IndexFiles {

    private static Object BM25Similarity;

    private IndexFiles() {
    }


    public static void main(String[] args) throws IOException {
        String usage = "java org.apache.lucene.demo.IndexFiles" + " [-index INDEX_PATH] [-docs DOCS_PATH] [-update]\n\n" + "This indexes the documents in DOCS_PATH, creating a Lucene index" + "in INDEX_PATH that can be searched with SearchFiles";
//        String indexDirectoryPath = "index";
//        String indexDirectoryPath = LuceneConstants.INDEX_DIRECTORY_PATH;
//        String dataDirectoryPath = LuceneConstants.DATA_DIRECTORY_PATH;

        Directory indexDirectory = FSDirectory.open(Paths.get(LuceneConstants.INDEX_DIRECTORY_PATH));
        String [] oldIndexFiles = indexDirectory.listAll();
        for (String s : oldIndexFiles) {
            indexDirectory.deleteFile(s);
        }

//        Analyzer analyzer = new EnglishAnalyzer();
        AnalyzerClass analyzer = new AnalyzerClass();
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer.getPerFieldAnalyzer());
//        iwc.setSimilarity(new BooleanSimilarity());
        iwc.setSimilarity(analyzer.getSimilarity());
        IndexWriter writer = new IndexWriter(indexDirectory, iwc);

        //ft
//        String[] docsArrayft = new String[610];
        List<String> docsArrayft = new ArrayList<>();
//        File docsDirft = new File("../documents/ft");
        File docsDirft = new File(LuceneConstants.DATA_DIRECTORY_PATH + "/ft");
        File[] directoriesft = docsDirft.listFiles();

        if (directoriesft != null) {
            for (File child : directoriesft) {
//                System.out.println("child: " + child);
//                System.out.println("child.getPath(): " + child.getPath());
//                System.out.println("child.getPath().substring(child.getPath().length() -5): " + child.getPath().substring(child.getPath().length() - 5));

//                if (child.getPath().substring(41, 43).contains("ft")) {
                if (child.getPath().substring(child.getPath().length() - 5).contains("ft9")) {
                    String path = child.getPath();
//                    docsArrayft[num] = path;
                    docsArrayft.add(path);
//                    System.out.println(" : path: " + path);
                }
            }
        }
        System.out.println("docsArrayft.size(): " + docsArrayft.size());

        //fbis
//        String[] docsArrayfbis = new String[495];
        List<String> docsArrayfbis = new ArrayList<>();
//        File docsDirfbis = new File("../documents/fbis");
        File docsDirfbis = new File(LuceneConstants.DATA_DIRECTORY_PATH + "/fbis");
        File[] directoriesfbis = docsDirfbis.listFiles();
        if (directoriesfbis != null) {
            for (File child : directoriesfbis) {
//                System.out.println("child.getPath().substring(child.getPath().length() -8): " + child.getPath().substring(child.getPath().length() - 8));
                if (child.getPath().substring(child.getPath().length() - 8).contains("fb3") ||
                        child.getPath().substring(child.getPath().length() - 8).contains("fb4")) {
                    String path = child.getPath();
//                    docsArrayfbis[num] = path;
//                    System.out.println("Path : " + path);
                    docsArrayfbis.add(path);
                }
            }
        }
        System.out.println("docsArrayfbis.size(): "+ docsArrayfbis.size());

        //fr94
//        String[] docsArrayfr94 = new String[495];
        List<String> docsArrayfr94 = new ArrayList<>();
        File docsDirfr94 = new File(LuceneConstants.DATA_DIRECTORY_PATH + "/fr94");
        File[] directoriesfr94 = docsDirfr94.listFiles();
        for (File directory : directoriesfr94) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    String path = file.getPath();
//                    docsArrayfr94[num] = path;
//                    System.out.println("Path: " + path);
                    docsArrayfr94.add(path);
                }
            }
        }
        System.out.println("docsArrayfr94.size(): " + docsArrayfr94.size());

        //latimes
//        String[] docsArraylatimes = new String[735];
        List<String> docsArraylatimes = new ArrayList<>();
//        File docsDirlatimes = new File("../documents/latimes");
        File docsDirlatimes = new File(LuceneConstants.DATA_DIRECTORY_PATH + "/latimes");
        File[] directorieslatimes = docsDirlatimes.listFiles();
        if (directorieslatimes != null) {
            for (File child : directorieslatimes) {
//                System.out.println("child.getPath().substring(child.getPath().length() -8): " + child.getPath().substring(child.getPath().length() - 8));
                if ((child.getPath().substring(child.getPath().length() - 8).contains("la0")) || (child.getPath().substring(child.getPath().length() - 8).contains("la1"))) {
                    String path = child.getPath();
//                    docsArraylatimes[num] = path;
//                    System.out.println("Path: " + path);
                    docsArraylatimes.add(path);
                }
            }
        }
        System.out.println("docsArraylatimes.size(): " + docsArraylatimes.size());

        String docsPath;
        // Indexing documents in ft
        List<String> docsArray = docsArrayft;
        System.out.println("Indexing files in ft ...");
        for (String s : docsArray) {
            docsPath = s;
//            System.out.println((j+1) + "docsPath: " + docsPath);
//            System.out.println("Index path =" + LuceneConstants.INDEX_DIRECTORY_PATH);
            boolean create = true;
//            for (int i = 0; i < args.length; i++) {
//                if ("-index".equals(args[i])) {
//                    indexDirectoryPath = args[i + 1];
//                    i++;
//                } else if ("-docs".equals(args[i])) {
//                    docsPath = args[i + 1];
//                    i++;
//                } else if ("-update".equals(args[i])) {
//                    create = false;
//                }
//            }
            if (docsPath == null) {
                System.err.println("Usage: " + usage);
                break;
            }
            final Path docDir = Paths.get(docsPath);
            if (!Files.isReadable(docDir)) {
                System.out.println("Document directory '" + docDir.toAbsolutePath() + "' does not exist or is not readable, please check the path");
                System.exit(1);
            }
            Date start = new Date();
            try {
//                System.out.println("Indexing to directory '" + indexDirectoryPath + "'...");
//                System.out.println("analyzer" + analyzer.getAnalyzer());
                if (create) {
                    iwc.setOpenMode(OpenMode.CREATE);
                } else {
                    iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
//                    iwc.setOpenMode(OpenMode.CREATE);
                }
//                System.out.println("here iwc = " + iwc);
//                System.out.println("Codec is " + Codec.availableCodecs());
//                System.out.println("here writer = " + writer + "iwc = " + iwc);
                indexDocs(writer, docDir);
                Date end = new Date();
//                System.out.println(end.getTime() - start.getTime() + " total milliseconds");
            } catch (IOException e) {
                System.out.println(" caught a " + e.getClass() + "\n with message: " + e.getMessage());
            }
        }
//        System.out.println("Total document indexed: " + ctr);
        System.out.println("Indexing of ft complete");

//         Indexing documents in fbis
        int ctr = 1;
        System.out.println("indexing files in fbis ...");
        for (String docsArrayfbi : docsArrayfbis) {
            docsPath = docsArrayfbi;
            boolean create = true;
//            for (int i = 0; i < args.length; i++) {
//                if ("-index".equals(args[i])) {
//                    indexDirectoryPath = args[i + 1];
//                    i++;
//                } else if ("-docs".equals(args[i])) {
//                    docsPath = args[i + 1];
//                    i++;
//                } else if ("-update".equals(args[i])) {
//                    create = false;
//                }
//            }
            if (docsPath == null) {
                System.err.println("Usage: " + usage);
                break;
            }
            final Path docDir = Paths.get(docsPath);
            if (!Files.isReadable(docDir)) {
                System.out.println("Document directory " + docDir.toAbsolutePath() + " does not exist or is not readable, please check the path");
                System.exit(1);
            }
//            System.out.println("Index path ='" + indexDirectoryPath);
//            System.out.println("doc path =" + docsPath);
            Date start = new Date();
            try {
//                System.out.println("Indexing to directory '" + indexDirectoryPath + "'...");
//                System.out.println("analyzer" + analyzer.getAnalyzer());
                if (create) {
                    iwc.setOpenMode(OpenMode.CREATE);
                } else {
                    iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
//                    iwc.setOpenMode(OpenMode.CREATE);
                }
//                System.out.println("here iwc = " + iwc);
//                System.out.println("Codec is " + Codec.availableCodecs());
//                System.out.println("here writer = " + writer + "iwc = " + iwc);

                indexDocfbis(writer, docDir);
                ctr++;
                Date end = new Date();
//                System.out.println(end.getTime() - start.getTime() + " total milliseconds");

            } catch (IOException e) {
                System.out.println(" caught a " + e.getClass() +
                        "\n with message: " + e.getMessage());
            }
        }
        System.out.println("Total document indexed: " + ctr);
        System.out.println("Indexing of fbis complete");

        ctr =1;
        System.out.println("indexing files in fr94 ...");
        for (String s : docsArrayfr94) {
//            docsPath = docsArrayfr94[j];
            docsPath = s;
            boolean create = true;
//            for (int i = 0; i < args.length; i++) {
//                if ("-index".equals(args[i])) {
//                    indexDirectoryPath = args[i + 1];
//                    i++;
//                } else if ("-docs".equals(args[i])) {
//                    docsPath = args[i + 1];
//                    i++;
//                } else if ("-update".equals(args[i])) {
//                    create = false;
//                }
//            }
            if (docsPath == null) {
//                System.err.println("Usage: " + usage);
                break;
            }
            final Path docDir = Paths.get(docsPath);
            if (!Files.isReadable(docDir)) {
                System.out.println("Document directory " + docDir.toAbsolutePath() + " does not exist or is not readable, please check the path");
                System.exit(1);
            }
//            System.out.println("Index path ='" + indexDirectoryPath);
//            System.out.println("doc path =" + docsPath);
            Date start = new Date();
            try {
//                System.out.println("Indexing to directory '" + indexDirectoryPath + "'...");
//                System.out.println("analyzer" + analyzer.getAnalyzer());
                if (create) {
                    iwc.setOpenMode(OpenMode.CREATE);
                } else {
                    iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
//                    iwc.setOpenMode(OpenMode.CREATE);
                }
//                System.out.println("here iwc = " + iwc);
//                System.out.println("Codec is " + Codec.availableCodecs());
//                System.out.println("here writer = " + writer + "iwc = " + iwc);
//                System.out.println("Document Dir: " + docDir.getFileName());
                indexDocfr94(writer, docDir);
                ctr++;
                Date end = new Date();
//                System.out.println(end.getTime() - start.getTime() + " total milliseconds");

            } catch (IOException e) {
                System.out.println(" caught a " + e.getClass() +
                        "\n with message: " + e.getMessage());
            }
        }
        System.out.println("Total document indexed: " + ctr);
        System.out.println("Indexing of fr94 complete");

        ctr =1;
        System.out.println("indexing files in latimes ...");
        for (String docsArraylatime : docsArraylatimes) {
//            docsPath = docsArraylatimes[j];
            docsPath = docsArraylatime;
            boolean create = true;
//            for (int i = 0; i < args.length; i++) {
//                if ("-index".equals(args[i])) {
//                    indexDirectoryPath = args[i + 1];
//                    i++;
//                } else if ("-docs".equals(args[i])) {
//                    docsPath = args[i + 1];
//                    i++;
//                } else if ("-update".equals(args[i])) {
//                    create = false;
//                }
//            }
            if (docsPath == null) {
                System.err.println("Usage: " + usage);
                break;
            }
            final Path docDir = Paths.get(docsPath);
            if (!Files.isReadable(docDir)) {
                System.out.println("Document directory '" + docDir.toAbsolutePath() + "' does not exist or is not readable, please check the path");
                System.exit(1);
            }
//            System.out.println("Index path ='" + indexDirectoryPath);
//            System.out.println("doc path =" + docsPath);
            Date start = new Date();
            try {
//                System.out.println("Indexing to directory '" + indexDirectoryPath + "'...");
//                System.out.println("analyzer" + analyzer.getAnalyzer());
                if (create) {
                    iwc.setOpenMode(OpenMode.CREATE);
                } else {
                    iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
//                    iwc.setOpenMode(OpenMode.CREATE);
                }
//                System.out.println("here iwc = " + iwc);
//                System.out.println("Codec is " + Codec.availableCodecs());
//                System.out.println("here writer = " + writer + "iwc = " + iwc);

                indexDoclatimes(writer, docDir);
                ctr++;
                Date end = new Date();
//                System.out.println(end.getTime() - start.getTime() + " total milliseconds");

            } catch (IOException e) {
                System.out.println(" caught a " + e.getClass() +
                        "\n with message: " + e.getMessage());
            }
        }
        System.out.println("Total document indexed: " + ctr);
        System.out.println("Indexing of latimes complete");
        writer.close();
    }


    static void indexDocs(final IndexWriter writer, Path path) throws IOException
    {
        final int[] num = {1};
        if (Files.isDirectory(path))
        {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {

                    try {
//                        if (file.equals("/Users/siqiwei/Desktop/Assignment_Two/fbis/.DS_Store")) {
                        if (file.equals(LuceneConstants.DATA_DIRECTORY_PATH + "/fbis/.DS_Store")) {
                            System.out.println("DS_store here");
                        } else {
                            indexDocft(writer, file);
                            num[0]++;
                        }

                    } catch (IOException ignore) {
                        System.out.println("can't index file");
                        // don't index files that can't be read.
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } else {
            indexDocft(writer, path);
            num[0]++;
        }
        System.out.println("Total Number of files indexed: " + num[0]);
    }


    static void indexDocft(IndexWriter writer, Path file) throws IOException {
        StringBuilder all = new StringBuilder();
        try (InputStream stream = Files.newInputStream(file)) {
//            System.out.println("file = " + file);

            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            String line;
            int mark = -1;
            int count = 0;
            ArrayList<Document> doc = new ArrayList<>();
            Document doc_0 = new Document();
            doc.add(doc_0);
            while ((line = br.readLine()) != null) {
//                System.out.println("index go here now, line is " + line);
                if (line.startsWith("<DOC>")) {
                    //需要push document
//                    System.out.println("line is " + line);
                    Document newdoc = new Document();
                    doc.add(newdoc);
                    if (count != 0) {
                        if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
                            // New index, so we just add the document (no old document can be there):
//                            System.out.println("adding " + file);
                            writer.addDocument(doc.get(count - 1));
                        } else {
//                            System.out.println("updating " + file);
                            writer.updateDocument(new Term("path", file.toString()), doc.get(count - 1));
                        }
                        //writer.addDocument(doc.get(count-1));
                    }
                    count = count + 1;
                } else if (line.startsWith("<PUB>")) {
                    String pub = line.substring(5);
//                    doc.get(count - 1).add(new TextField(LuceneConstants.PUBLISHER, pub, Field.Store.YES));
                    doc.get(count - 1).add(new StringField(LuceneConstants.PUBLISHER, pub, Field.Store.YES));
//                    doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, pub, Field.Store.YES));
//                    all.append(pub + "\n");
//                } else if (line.startsWith("</PUB>")) {
                    mark = -1;
                } else if (line.startsWith("</DOC>")) {

                    mark = -1;
                } else if (line.startsWith("<PAGE>")) {
                    mark = 3;
                } //<PAGE>
                else if (line.startsWith("<TEXT>")) {
                    mark = 2;
                } // <TEXT>
                else if (line.startsWith("<DATE>")) {
                    String date = line.substring(6);
                    doc.get(count - 1).add(new TextField(LuceneConstants.DATE, date, Field.Store.YES));
//                    System.out.println("date is " + date);

                } else if (line.startsWith("</PAGE>")) {
                    mark = -3;
                } else if (line.startsWith("<DOCNO>")) {

                    String docno = StringUtils.substringBetween(line, "<DOCNO>", "</DOCNO>");
//                    System.out.println("docno is " + docno);
//                    doc.get(count - 1).add(new TextField(LuceneConstants.DOCUMENT_NUMBER, docno, Field.Store.YES));
                    doc.get(count - 1).add(new StringField(LuceneConstants.DOCUMENT_NUMBER, docno, Field.Store.YES));
                } //<DOCNO>

                else if (line.startsWith("</DATE>")) {
//                    System.out.println("date end here");
                } else if (line.startsWith("</TEXT>")) {
                    mark = -2;
                } else if (line.startsWith("<BYLINE>")) {
                    mark = 4;
                } else if (line.startsWith("</BYLINE>")) {
                    mark = -4;
                } else if (line.startsWith("<PROFILE>")) {
                    // substract profile name
                    String profile = StringUtils.substringBetween(line, "<PROFILE>", "</PROFILE>");
//                    System.out.println("profile is " + profile);
//                    doc.get(count - 1).add(new TextField(LuceneConstants.PROFILE, profile, Field.Store.YES));
                    doc.get(count - 1).add(new StringField(LuceneConstants.PROFILE, profile, Field.Store.YES));

                } // <PROFILE>
                else if (line.startsWith("<HEADLINE>")) {
                    mark = 1;
                } // <HEADLINE>
                else if (line.startsWith("<DATELINE>")) {
                    mark = 5;
                } // <DATELINE>
                else if (line.startsWith("</HEADLINE>")) {
                    mark = -1;
                } else if (line.startsWith("</DATELINE>")) {
                    mark = -1;
                } else if (mark == 1) {
                    doc.get(count - 1).add(new TextField(LuceneConstants.HEADLINE, line, Field.Store.YES));
//                    doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, line, Field.Store.YES));
//                    all.append(line + "\n");
                } else if (mark == 2) {
                    doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, line, Field.Store.YES));
//                    all.append(line + "\n");
                } else if (mark == 3) {
                    doc.get(count - 1).add(new TextField(LuceneConstants.PAGE, line, Field.Store.YES));
                } else if (mark == 4) {
                    doc.get(count - 1).add(new TextField(LuceneConstants.BYLINE, line, Field.Store.YES));
//                    doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, line, Field.Store.YES));
//                    all.append(line + "\n");
                } else if (mark == 5) {
//                    doc.get(count - 1).add(new TextField(LuceneConstants.DATE_LINE, line, Field.Store.YES));
                    doc.get(count - 1).add(new StringField(LuceneConstants.DATE_LINE, line, Field.Store.YES));
//                    doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, line, Field.Store.YES));
//                    all.append(line + "\n");
                }
                //System.out.println("mark is " + mark);
            }
//            doc.get(count-1).add(new StringField(LuceneConstants.ALL_FIELDS, String.valueOf(all), Field.Store.NO));
            writer.addDocument(doc.get(count - 1));
//            System.out.println("Document: " + doc);
//            System.out.println(doc.size());
        }
    }


    static void indexDocfbis(IndexWriter writer, Path file) throws IOException {

        try (InputStream stream = Files.newInputStream(file)) {
//            System.out.println("file = " + file);
//            if (!file.equals("/Users/siqiwei/Desktop/Assignment_Two/fbis/.DS_Store")) {
            if (!file.equals(LuceneConstants.DATA_DIRECTORY_PATH + "/fbis/.DS_Store")) {
                BufferedReader br = new BufferedReader(new InputStreamReader(stream));
                String line;
                int curr = -100;
                int count = 0;
                ArrayList<Document> doc = new ArrayList<>();
                Document doc_0 = new Document();
                doc.add(doc_0);
                Stack<Integer> stack = new Stack<>();
                while ((line = br.readLine()) != null) {
//                    System.out.println("index go here now, line is " + line);
                    if (line.startsWith("<DOC>")) {
                        //需要push document
//                        System.out.println("line is " + line);
                        Document newdoc = new Document();
                        doc.add(newdoc);
                        if (count != 0) {
                            if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
                                // New index, so we just add the document (no old document can be there):
//                                System.out.println("adding " + file);
                                writer.addDocument(doc.get(count - 1));
                            } else {
//                                System.out.println("updating " + file);
                                writer.updateDocument(new Term("path", file.toString()), doc.get(count - 1));
                            }
                            //writer.addDocument(doc.get(count-1));
                        }
                        count = count + 1;
                        curr = 1;
                        stack.push(curr);
                    } else if (line.startsWith("</DOC>")) {
                        stack.pop();
                        curr = -100;
                    } else if (line.startsWith("<TEXT>")) {
                        curr = 2;
                        stack.push(curr);
                    } else if (line.startsWith("</TEXT>")) {
                        stack.pop();
                        curr = stack.peek();
                    } else if (line.startsWith("<HT>")) {
                        String ht = StringUtils.substringBetween(line, "<HT>", "</HT>");
//                        doc.get(count - 1).add(new TextField(LuceneConstants.HT, ht, Field.Store.YES));
                        doc.get(count - 1).add(new StringField(LuceneConstants.HT, ht, Field.Store.YES));
//                        System.out.println("ht is " + ht);
                    } else if (line.startsWith("<AU>")) {
                        String au = StringUtils.substringBetween(line, "<AU>", "</AU>");
//                        doc.get(count - 1).add(new TextField(LuceneConstants.AU, au, Field.Store.YES));
                        doc.get(count - 1).add(new StringField(LuceneConstants.AU, au, Field.Store.YES));
//                        System.out.println("au is " + au);
                    } else if (line.startsWith("<DOCNO>")) {
                        String docno = StringUtils.substringBetween(line, "<DOCNO>", "</DOCNO>");
//                        System.out.println("docno is " + docno);
//                        doc.get(count - 1).add(new TextField(LuceneConstants.DOCUMENT_NUMBER, docno, Field.Store.YES));
                        doc.get(count - 1).add(new StringField(LuceneConstants.DOCUMENT_NUMBER, docno, Field.Store.YES));
                    } //<DOCNO>
                    else if (line.startsWith("<DATE1>")) {
                        String date1 = StringUtils.substringBetween(line, "<DATE1>", "</DATE1>");
//                        System.out.println("date1 is " + date1);
                        doc.get(count - 1).add(new TextField(LuceneConstants.DATE, date1, Field.Store.YES));
                    } else if (line.startsWith("<H1>")) {
                        String H1 = null;
                        if (line.contains("<TI>")) {
                            H1 = StringUtils.substringBetween(line, "<TI>", "</TI>");
                        } else {
                            H1 = StringUtils.substringBetween(line, "<H1>", "</H1>");
                        }
//                        System.out.println("H1 is " + H1);
//                        doc.get(count - 1).add(new TextField(LuceneConstants.HEADING_1, H1, Field.Store.YES));
                        doc.get(count - 1).add(new TextField(LuceneConstants.HEADING, H1, Field.Store.YES));
//                        doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, H1, Field.Store.YES));
                    } else if (line.startsWith("<H2>")) {
                        String H2 = null;
                        if (line.contains("<TI>")) {
                            H2 = StringUtils.substringBetween(line, "<TI>", "</TI>");
                        } else {
                            H2 = StringUtils.substringBetween(line, "<H2>", "</H2>");
                        }
//                        System.out.println("H2 is " + H2);
//                        doc.get(count - 1).add(new TextField(LuceneConstants.HEADING_2, H2, Field.Store.YES));
                        doc.get(count - 1).add(new TextField(LuceneConstants.HEADING, H2, Field.Store.YES));
//                        doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, H2, Field.Store.YES));
                    } else if (line.startsWith("<H3>")) {
                        String H3 = null;
                        if (line.contains("<TI>")) {
                            if (line.contains("</TI>")) {
                                H3 = StringUtils.substringBetween(line, "<TI>", "</TI>");
                            } else {
                                H3 = line.substring(10);
                            }
                        } else if (line.contains("</H3>")) {
                            H3 = StringUtils.substringBetween(line, "<H3>", "</H3>");
                        } else {
                            H3 = line.substring(4);
                        }
//                        System.out.println("H3 is " + H3);
//                        doc.get(count - 1).add(new TextField(LuceneConstants.HEADING_3, H3, Field.Store.YES));
                        doc.get(count - 1).add(new TextField(LuceneConstants.HEADING, H3, Field.Store.YES));
//                        doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, H3, Field.Store.YES));
                    } else if (line.startsWith("<H4>")) {
                        String H4 = null;
                        if (line.contains("<TI>")) {
                            H4 = StringUtils.substringBetween(line, "<TI>", "</TI>");
                        } else if (line.contains("</H4>")) {
                            H4 = StringUtils.substringBetween(line, "<H4>", "</H4>");
                        } else {
                            H4 = line.substring(4);
                        }
//                        System.out.println("H4 is " + H4);
//                        doc.get(count - 1).add(new TextField(LuceneConstants.HEADING_4, H4, Field.Store.YES));
                        doc.get(count - 1).add(new TextField(LuceneConstants.HEADING, H4, Field.Store.YES));
//                        doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, H4, Field.Store.YES));
                    } else if (line.startsWith("<H5>")) {
                        String H5 = null;
                        if (line.contains("<TI>")) {
                            H5 = StringUtils.substringBetween(line, "<TI>", "</TI>");
                        } else if (line.contains("</H5>")) {
                            H5 = StringUtils.substringBetween(line, "<H5>", "</H5>");
                        } else {
                            H5 = line.substring(4);
                        }
//                        doc.get(count - 1).add(new TextField(LuceneConstants.HEADING_5, H5, Field.Store.YES));
                        doc.get(count - 1).add(new TextField(LuceneConstants.HEADING, H5, Field.Store.YES));
//                        doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, H5, Field.Store.YES));
                    } else if (line.startsWith("<H6>")) {
                        String H6 = null;
                        if (line.contains("<TI>")) {
                            H6 = StringUtils.substringBetween(line, "<TI>", "</TI>");
                        } else if (line.contains("</H6>")) {
                            H6 = StringUtils.substringBetween(line, "<H6>", "</H6>");
                        } else {
                            H6 = line.substring(4);
                        }
//                        doc.get(count - 1).add(new TextField(LuceneConstants.HEADING_6, H6, Field.Store.YES));
                        doc.get(count - 1).add(new TextField(LuceneConstants.HEADING, H6, Field.Store.YES));
//                        doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, H6, Field.Store.YES));
                    } else if (line.startsWith("<H7>")) {
                        String H7 = null;
                        if (line.contains("<TI>")) {
                            H7 = StringUtils.substringBetween(line, "<TI>", "</TI>");
                        } else if (line.contains("</H7>")) {
                            H7 = StringUtils.substringBetween(line, "<H7>", "</H7>");
                        } else {
                            H7 = line.substring(4);
                        }
//                        doc.get(count - 1).add(new TextField(LuceneConstants.HEADING_7, H7, Field.Store.YES));
                        doc.get(count - 1).add(new TextField(LuceneConstants.HEADING, H7, Field.Store.YES));
//                        doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, H7, Field.Store.YES));
                    } else if (line.startsWith("<H8>")) {
                        String H8 = null;
                        if (line.contains("<TI>")) {
                            H8 = StringUtils.substringBetween(line, "<TI>", "</TI>");
                        } else if (line.contains("</H8>")) {
                            H8 = StringUtils.substringBetween(line, "<H8>", "</H8>");
                        } else {
                            H8 = line.substring(4);
                        }
//                        doc.get(count - 1).add(new TextField(LuceneConstants.HEADING_8, H8, Field.Store.YES));
                        doc.get(count - 1).add(new TextField(LuceneConstants.HEADING, H8, Field.Store.YES));
//                        doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, H8, Field.Store.YES));
                    } else if (line.startsWith("<F ")) {
                        String f = null;
                        if (line.contains("</F>")) {
                            f = StringUtils.substringBetween(line, ">", "</F>");
                        } else {
                            f = line.substring(10);
                        }
//                        doc.get(count - 1).add(new TextField("F", f, Field.Store.YES));
                        doc.get(count - 1).add(new StringField("F", f, Field.Store.YES));
                    } else if (line.startsWith("<ABS>")) {
                        String abs = StringUtils.substringBetween(line, "<ABS>", "</ABS>");
                        doc.get(count - 1).add(new TextField(LuceneConstants.ABS, abs, Field.Store.YES));
//                        doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, abs, Field.Store.YES));
                    } else if (line.startsWith("<FIG")) {
                        String fig = StringUtils.substringBetween(line, ">", "</FIG>");
//                        doc.get(count - 1).add(new TextField(LuceneConstants.FIGURE, fig, Field.Store.YES));
                        doc.get(count - 1).add(new StringField(LuceneConstants.FIGURE, fig, Field.Store.YES));
                    } else if (line.startsWith("<HEADER>")) {
                        curr = 3;
                        stack.push(curr);
                    } else if (line.startsWith("</HEADER>")) {
                        stack.pop();
                        curr = stack.peek();
                    } else if (line.startsWith("<TR>")) {
                        String tr = line.substring(4);
                        doc.get(count - 1).add(new TextField(LuceneConstants.TR, tr, Field.Store.YES));
//                        doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, tr, Field.Store.YES));
                        curr = 4;
                        stack.push(curr);
                    } else if (line.startsWith("</TR>")) {
                        stack.pop();
                        curr = stack.peek();
                    } else if (line.startsWith("<TXT5>")) {
                        String txt5 = line.substring(6);
                        doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, txt5, Field.Store.YES));
                        curr = 5;
                        stack.push(curr);
                    } else if (line.startsWith("</TXT5>")) {
                        stack.pop();
                        curr = stack.peek();
                    } else if (curr == 2) {
                        doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, line, Field.Store.YES));
                    } else if (curr == 3) {
                        doc.get(count - 1).add(new TextField(LuceneConstants.HEADER, line, Field.Store.YES));
//                        doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, line, Field.Store.YES));
                    } else if (curr == 4) {
                        doc.get(count - 1).add(new TextField(LuceneConstants.TR, line, Field.Store.YES));
//                        doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, line, Field.Store.YES));
                    } else if (curr == 5) {
                        doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, line, Field.Store.YES));
                    }
                }
                writer.addDocument(doc.get(count - 1));
//                System.out.println(doc.size());
            }
        }
    }


    static void indexDocfr94(IndexWriter writer, Path file) throws IOException {
        try (InputStream stream = Files.newInputStream(file)) {

//            System.out.println("file = " + file);

            BufferedReader br = new BufferedReader(new InputStreamReader(stream));

            String line;
            int curr = -100;
            int count = 0;
            ArrayList<Document> doc = new ArrayList<>();
            Document doc_0 = new Document();
            doc.add(doc_0);
            Stack<Integer> stack = new Stack<>();


            while ((line = br.readLine()) != null) {
//                System.out.println("index go here now, line is " + line);
                if (line.startsWith("<DOC>")) {
                    //需要push document
//                    System.out.println("line is " + line);
                    Document newdoc = new Document();
                    doc.add(newdoc);
                    if (count != 0) {
                        if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
                            // New index, so we just add the document (no old document can be there):
//                            System.out.println("adding " + file);
                            writer.addDocument(doc.get(count - 1));
                        } else {
//                            System.out.println("updating " + file);
                            writer.updateDocument(new Term("path", file.toString()), doc.get(count - 1));
                        }
                        //writer.addDocument(doc.get(count-1));
                    }
                    count = count + 1;
                    curr = 1;
                    stack.push(curr);
                } else if (line.startsWith("</DOC>")) {
                    stack.pop();
                    curr = -100;
                } else if (line.startsWith("<TEXT>")) {
                    curr = 2;
                    stack.push(curr);
                } else if (line.startsWith("</TEXT>")) {
                    stack.pop();
                    curr = stack.peek();
                } else if (line.startsWith("<PARENT>")) {
                    String parent = StringUtils.substringBetween(line, "<PARENT>", "</PARENT>");
//                    doc.get(count - 1).add(new TextField(LuceneConstants.PARENT, parent, Field.Store.YES));
                    doc.get(count - 1).add(new StringField(LuceneConstants.PARENT, parent, Field.Store.YES));
//                    System.out.println("parent is " + parent);
                } else if (line.startsWith("<DOCNO>")) {
                    String docno = StringUtils.substringBetween(line, "<DOCNO>", "</DOCNO>");
//                    System.out.println("docno is " + docno);
//                    doc.get(count - 1).add(new TextField(LuceneConstants.DOCUMENT_NUMBER, docno, Field.Store.YES));
                    doc.get(count - 1).add(new StringField(LuceneConstants.DOCUMENT_NUMBER, docno, Field.Store.YES));
                } //<DOCNO>
                else if (line.startsWith("<USDEPT>")) {
                    curr = 20;
                    stack.push(curr);
                    //String usdept = StringUtils.substringBetween(line, "<USDEPT>", "</USDEPT>");
                    //oc.get(count-1).add(new TextField("USDEPT", usdept, Field.Store.YES));
                } else if (line.startsWith("</USDEPT>")) {
                    stack.pop();
                    curr = stack.peek();
                } else if (line.startsWith("<USBUREAU>")) {
                    curr = 21;
                    stack.push(curr);
                    //String usbureau = StringUtils.substringBetween(line, "<USBUREAU>", "</USBUREAU>");
                    //doc.get(count-1).add(new TextField("USBUREAU", usbureau, Field.Store.YES));
                } else if (line.startsWith("</USBUREAU>")) {
                    stack.pop();
                    curr = stack.peek();
                } else if (line.startsWith("<CFRNO>")) {
                    curr = 22;
                    stack.push(curr);
                    //String cfrno = StringUtils.substringBetween(line, "<CFRNO>", "</CFRNO>");
                    //doc.get(count-1).add(new TextField("CFRNO", cfrno, Field.Store.YES));
                } else if (line.startsWith("</CFRNO>")) {
                    stack.pop();
                    curr = stack.peek();
                } else if (line.startsWith("<RINDOCK>")) {
                    curr = 18;
                    stack.push(curr);
                    //String rindock = StringUtils.substringBetween(line, "<RINDOCK>", "</RINDOCK>");
                    //doc.get(count-1).add(new TextField("RINDOCK", rindock, Field.Store.YES));
                } else if (line.startsWith("</RINDOCK>")) {
                    stack.pop();
                    curr = stack.peek();
                } else if (line.startsWith("<FOOTCITE>")) {
                    curr = 19;
                    stack.push(curr);
                    //String footcite = StringUtils.substringBetween(line, "<FOOTCITE>", "</FOOTCITE>");
                    //doc.get(count-1).add(new TextField("FOOTCITE", footcite, Field.Store.YES));
                } else if (line.startsWith("</FOOTCITE>")) {
                    stack.pop();
                    curr = stack.peek();
                } else if (line.startsWith("<DOCTITLE>")) {
                    curr = 17;
                    stack.push(curr);
                    //String doctitle = StringUtils.substringBetween(line, "<DOCTITLE>", "</DOCTITLE>");
                    //doc.get(count-1).add(new TextField("DOCTITLE", doctitle, Field.Store.YES));
                } else if (line.startsWith("</DOCTITLE>")) {
                    stack.pop();
                    curr = stack.peek();
                } else if (line.startsWith("<FOOTNAME>")) {
                    curr = 3;
                    stack.push(curr);
                } else if (line.startsWith("</FOOTNAME>")) {
                    stack.pop();
                    curr = stack.peek();
                } else if (line.startsWith("<AGENCY>")) {
                    curr = 4;
                    stack.push(curr);
                } else if (line.startsWith("</AGENCY>")) {
                    stack.pop();
                    curr = stack.peek();
                } else if (line.startsWith("<ACTION>")) {
                    curr = 5;
                    stack.push(curr);
                } else if (line.startsWith("</ACTION>")) {
                    stack.pop();
                    curr = stack.peek();
                } else if (line.startsWith("<SUMMARY>")) {
                    curr = 6;
                    stack.push(curr);
                } else if (line.startsWith("</SUMMARY>")) {
                    stack.pop();
                    curr = stack.peek();
                } else if (line.startsWith("<DATE>")) {
                    curr = 7;
                    stack.push(curr);
                } else if (line.startsWith("</DATE>")) {
                    stack.pop();
                    curr = stack.peek();
                } else if (line.startsWith("<FURTHER>")) {
                    curr = 8;
                    stack.push(curr);
                } else if (line.startsWith("</FURTHER>")) {
                    stack.pop();
                    curr = stack.peek();
                } else if (line.startsWith("<SUPPLEM>")) {
                    curr = 9;
                    stack.push(curr);
                } else if (line.startsWith("</SUPPLEM>")) {
                    stack.pop();
                    curr = stack.peek();
                } else if (line.startsWith("<SIGNER>")) {
                    curr = 10;
                    stack.push(curr);
                } else if (line.startsWith("</SIGNER>")) {
                    stack.pop();
                    curr = stack.peek();
                } else if (line.startsWith("<SIGNJOB>")) {
                    curr = 11;
                    stack.push(curr);
                } else if (line.startsWith("</SIGNJOB>")) {
                    stack.pop();
                    curr = stack.peek();
                } else if (line.startsWith("<FRFILING>")) {
                    curr = 12;
                    stack.push(curr);
                } else if (line.startsWith("</FRFILING>")) {
                    stack.pop();
                    curr = stack.peek();
                } else if (line.startsWith("<BILLING>")) {
                    curr = 13;
                    stack.push(curr);
                } else if (line.startsWith("</BILLING>")) {
                    stack.pop();
                    curr = stack.peek();
                } else if (line.startsWith("<FOOTNOTE>")) {
                    curr = 14;
                    stack.push(curr);
                } else if (line.startsWith("</FOOTNOTE>")) {
                    stack.pop();
                    curr = stack.peek();
                } else if (line.startsWith("<ADDRESS>")) {
                    curr = 15;
                    stack.push(curr);
                } else if (line.startsWith("</ADDRESS>")) {
                    stack.pop();
                    curr = stack.peek();
                } else if (line.startsWith("<FOOTNOTE>")) {
                    curr = 16;
                    stack.push(curr);
                } else if (line.startsWith("</FOOTNOTE>")) {
                    stack.pop();
                    curr = stack.peek();
                } else if (line.startsWith("<TABLE>")) {
                    curr = 16;
                    stack.push(curr);
                } else if (line.startsWith("</TABLE>")) {
                    stack.pop();
                    curr = stack.peek();
                } else if (curr == 7) {
                    doc.get(count - 1).add(new TextField(LuceneConstants.DATE, line, Field.Store.YES));
                } else if (curr == 8) {
                    doc.get(count - 1).add(new TextField(LuceneConstants.FURTHER, line, Field.Store.YES));
//                    doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, line, Field.Store.YES));
                } else if (curr == 9) {
//                    doc.get(count - 1).add(new TextField(LuceneConstants.SUPPLEMENTARY_INFO, line, Field.Store.YES)); // <SUPPLEM>
                    doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, line, Field.Store.YES)); // <SUPPLEM>
                } else if (curr == 10) {
//                    doc.get(count - 1).add(new TextField(LuceneConstants.SIGNER, line, Field.Store.YES));
                    doc.get(count - 1).add(new StringField(LuceneConstants.SIGNER, line, Field.Store.YES));
//                    doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, line, Field.Store.YES));
                } else if (curr == 11) {
//                    doc.get(count - 1).add(new TextField(LuceneConstants.SIGN_JOB, line, Field.Store.YES));
                    doc.get(count - 1).add(new StringField(LuceneConstants.SIGN_JOB, line, Field.Store.YES));
//                    doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, line, Field.Store.YES));
                } else if (curr == 12) {
                    doc.get(count - 1).add(new TextField(LuceneConstants.FR_FILING, line, Field.Store.YES));
                } else if (curr == 13) {
                    doc.get(count - 1).add(new TextField(LuceneConstants.BILLING, line, Field.Store.YES));
                } else if (curr == 14) {
                    doc.get(count - 1).add(new TextField(LuceneConstants.FOOTNOTE, line, Field.Store.YES));
//                    doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, line, Field.Store.YES));
                } else if (curr == 15) {
                    doc.get(count - 1).add(new TextField(LuceneConstants.ADDRESS, line, Field.Store.YES));
//                    doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, line, Field.Store.YES));
                }
                //else if (mark == 1) { doc.get(count-1).add(new TextField("HEADLINE", line, Field.Store.YES)); }
                else if (curr == 16) {
                    doc.get(count - 1).add(new TextField(LuceneConstants.FOOTNOTE, line, Field.Store.YES));
//                    doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, line, Field.Store.YES));
                } else if (curr == 2) {
                    doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, line, Field.Store.YES));
                } else if (curr == 3) {
                    doc.get(count - 1).add(new TextField(LuceneConstants.FOOTCITE, line, Field.Store.YES));
                } else if (curr == 4) {
//                    doc.get(count - 1).add(new TextField(LuceneConstants.AGENCY, line, Field.Store.YES));
                    doc.get(count - 1).add(new StringField(LuceneConstants.AGENCY, line, Field.Store.YES));
//                    doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, line, Field.Store.YES));
                } else if (curr == 5) {
                    doc.get(count - 1).add(new TextField(LuceneConstants.ACTION, line, Field.Store.YES));
                } else if (curr == 6) {
//                    doc.get(count - 1).add(new TextField(LuceneConstants.SUMMARY, line, Field.Store.YES));
                    doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, line, Field.Store.YES));
                } else if (curr == 17) {
                    doc.get(count - 1).add(new TextField(LuceneConstants.TITLE, line, Field.Store.YES)); //<DOCTITLE>
                } else if (curr == 18) {
                    doc.get(count - 1).add(new TextField(LuceneConstants.RINDOCK, line, Field.Store.YES));
                } else if (curr == 19) {
                    doc.get(count - 1).add(new TextField(LuceneConstants.FOOTCITE, line, Field.Store.YES));
                } else if (curr == 20) {
//                    doc.get(count - 1).add(new TextField(LuceneConstants.US_DEPARTMRNT, line, Field.Store.YES)); //<USDEPT>
                    doc.get(count - 1).add(new StringField(LuceneConstants.US_DEPARTMRNT, line, Field.Store.YES)); //<USDEPT>
//                    doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, line, Field.Store.YES)); //<USDEPT>
                } else if (curr == 21) {
//                    doc.get(count - 1).add(new TextField(LuceneConstants.US_BUREAU, line, Field.Store.YES));
                    doc.get(count - 1).add(new StringField(LuceneConstants.US_BUREAU, line, Field.Store.YES));
//                    doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, line, Field.Store.YES));
                } else if (curr == 22) {
//                    doc.get(count - 1).add(new TextField(LuceneConstants.CFRNO, line, Field.Store.YES));
                    doc.get(count - 1).add(new StringField(LuceneConstants.CFRNO, line, Field.Store.YES));
//                    doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, line, Field.Store.YES));
                }
            }
            writer.addDocument(doc.get(count - 1));
//            System.out.println(doc.size());
        }
    }


    static void indexDoclatimes(IndexWriter writer, Path file) throws IOException {
        try (InputStream stream = Files.newInputStream(file)) {
//            System.out.println("file = " + file);
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            String line;
            int curr = -100;
            int count = 0;
            ArrayList<Document> doc = new ArrayList<>();
            Document doc_0 = new Document();
            doc.add(doc_0);
            Stack<Integer> stack = new Stack<>();
            while ((line = br.readLine()) != null) {
//                System.out.println("index go here now, line is " + line);
                if (line.startsWith("<DOC>")) {
                    //需要push document
//                    System.out.println("line is " + line);
                    Document newdoc = new Document();
                    doc.add(newdoc);
                    if (count != 0) {
                        if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
                            // New index, so we just add the document (no old document can be there):
//                            System.out.println("adding " + file);
                            writer.addDocument(doc.get(count - 1));
                        } else {
//                            System.out.println("updating " + file);
                            writer.updateDocument(new Term("path", file.toString()), doc.get(count - 1));
                        }
                        //writer.addDocument(doc.get(count-1));
                    }
                    count = count + 1;
                    curr = 1;
                    stack.push(curr);
                } else if (line.startsWith("</DOC>")) {
                    stack.pop();
                    curr = -100;
                } else if (line.startsWith("<TEXT>")) {
                    curr = 2;
                    stack.push(curr);
                } else if (line.startsWith("</TEXT>")) {
                    stack.pop();
                    curr = stack.peek();
                } else if (line.startsWith("<SECTION>")) {
                    curr = 3;
                    stack.push(curr);
                } else if (line.startsWith("</SECTION>")) {
                    stack.pop();
                    curr = stack.peek();
                } else if (line.startsWith("<LENGTH>")) {
                    curr = 4;
                    stack.push(curr);
                } else if (line.startsWith("</LENGTH>")) {
                    stack.pop();
                    curr = stack.peek();
                } else if (line.startsWith("<BYLINE>")) {
                    curr = 5;
                    stack.push(curr);
                } else if (line.startsWith("</BYLINE>")) {
                    stack.pop();
                    curr = stack.peek();
                } else if (line.startsWith("<DATELINE>")) {
                    curr = 6;
                    stack.push(curr);
                } else if (line.startsWith("</DATELINE>")) {
                    stack.pop();
                    curr = stack.peek();
                } else if (line.startsWith("<SUBJECT>")) {
                    curr = 7;
                    stack.push(curr);
                } else if (line.startsWith("</SUBJECT>")) {
                    stack.pop();
                    curr = stack.peek();
                } else if (line.startsWith("<CORRECTION-DATE>")) {
                    curr = 8;
                    stack.push(curr);
                } else if (line.startsWith("</CORRECTION-DATE>")) {
                    stack.pop();
                    curr = stack.peek();
                } else if (line.startsWith("<CORRECTION>")) {  //有问题
                    curr = 9;
                    stack.push(curr);
                } else if (line.startsWith("</CORRECTION>")) {
                    stack.pop();
                    curr = stack.peek();
                } else if (line.startsWith("<GRAPHIC>")) {  //有问题
                    curr = 10;
                    stack.push(curr);
                } else if (line.startsWith("</GRAPHIC>")) {
                    stack.pop();
                    curr = stack.peek();
                } else if (line.startsWith("<HEADLINE")) {
                    curr = 12;
                    stack.push(curr);
                } else if (line.startsWith("</HEADLINE>")) {
                    stack.pop();
                    curr = stack.peek();
                } else if (line.startsWith("<TEXT")) {
                    curr = 13;
                    stack.push(curr);
                } else if (line.startsWith("</TEXT>")) {
                    stack.pop();
                    curr = stack.peek();
                } else if (line.startsWith("<DATE>")) {
                    curr = 14;
                    stack.push(curr);
                } else if (line.startsWith("</DATE>")) {
                    stack.pop();
                    curr = stack.peek();
                } else if (line.startsWith("<HT>")) {
                    String ht = StringUtils.substringBetween(line, "<HT>", "</HT>");
//                    doc.get(count - 1).add(new TextField(LuceneConstants.HT, ht, Field.Store.YES));
                    doc.get(count - 1).add(new StringField(LuceneConstants.HT, ht, Field.Store.YES));
//                    System.out.println("ht is " + ht);
                } else if (line.startsWith("<AU>")) {
                    String au = StringUtils.substringBetween(line, "<AU>", "</AU>");
//                    doc.get(count - 1).add(new TextField(LuceneConstants.AU, au, Field.Store.YES));
                    doc.get(count - 1).add(new StringField(LuceneConstants.AU, au, Field.Store.YES));
//                    System.out.println("au is " + au);
                } else if (line.startsWith("<DOCNO>")) {
                    String docno = StringUtils.substringBetween(line, "<DOCNO>", "</DOCNO>");
//                    System.out.println("docno is " + docno);
//                    doc.get(count - 1).add(new TextField(LuceneConstants.DOCUMENT_NUMBER, docno, Field.Store.YES));
                    doc.get(count - 1).add(new StringField(LuceneConstants.DOCUMENT_NUMBER, docno, Field.Store.YES));
                } else if (line.startsWith("<DOCID>")) {
                    String docid = StringUtils.substringBetween(line, "<DOCID>", "</DOCID>");
//                    System.out.println("docid is " + docid);
//                    doc.get(count - 1).add(new TextField(LuceneConstants.DOCUMENT_ID, docid, Field.Store.YES)); //<DOCID>
                    doc.get(count - 1).add(new StringField(LuceneConstants.DOCUMENT_ID, docid, Field.Store.YES)); //<DOCID>
                } else if (line.startsWith("<DATE>")) {
                    String date = StringUtils.substringBetween(line, "<DATE>", "</DATE>");
//                    System.out.println("date is " + date);
                    doc.get(count - 1).add(new TextField(LuceneConstants.DATE, date, Field.Store.YES));
                } else if (curr == 2) {
                    doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, line, Field.Store.YES));
                } else if (curr == 3) {
                    if (!line.startsWith("<p>") && !line.startsWith("</p>")) {
                        doc.get(count - 1).add(new TextField(LuceneConstants.SECTION, line, Field.Store.YES));
                    }
                } else if (curr == 4) {
                    if (!line.startsWith("<p>") && !line.startsWith("</p>")) {
                        doc.get(count - 1).add(new TextField(LuceneConstants.LENGTH, line, Field.Store.YES));
                    }
                } else if (curr == 5) {
                    if (!line.startsWith("<p>") && !line.startsWith("</p>")) {
                        doc.get(count - 1).add(new TextField(LuceneConstants.BYLINE, line, Field.Store.YES));
//                        doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, line, Field.Store.YES));
                    }
                } else if (curr == 6) {
                    if (!line.startsWith("<p>") && !line.startsWith("</p>")) {
//                        doc.get(count - 1).add(new TextField(LuceneConstants.DATE_LINE, line, Field.Store.YES));
                        doc.get(count - 1).add(new StringField(LuceneConstants.DATE_LINE, line, Field.Store.YES));
//                        doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, line, Field.Store.YES));
                    }
                } else if (curr == 7) {
                    if (!line.startsWith("<p>") && !line.startsWith("</p>")) {
                        doc.get(count - 1).add(new TextField(LuceneConstants.SUBJECT, line, Field.Store.YES));
//                        doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, line, Field.Store.YES));
                    }
                } else if (curr == 8) {
                    if (!line.startsWith("<p>") && !line.startsWith("</p>")) {
                        doc.get(count - 1).add(new TextField(LuceneConstants.CORRECTION_DATE, line, Field.Store.YES));
//                        doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, line, Field.Store.YES));
                    }
                } else if (curr == 9) {
                    if (!line.startsWith("<p>") && !line.startsWith("</p>")) {
//                        doc.get(count - 1).add(new TextField(LuceneConstants.CORRECTION, line, Field.Store.YES));
                        doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, line, Field.Store.YES));
                    }
                } else if (curr == 10) {
                    if (!line.startsWith("<p>") && !line.startsWith("</p>")) {
                        doc.get(count - 1).add(new TextField(LuceneConstants.GRAPHIC, line, Field.Store.YES));
                    }
                } else if (curr == 11) {
                    if (!line.startsWith("<") && !line.startsWith("</")) {
                        doc.get(count - 1).add(new TextField(LuceneConstants.TABLE, line, Field.Store.YES));
                    }
                } else if (curr == 12) {
                    if (!line.startsWith("<") && !line.startsWith("</")) {
                        doc.get(count - 1).add(new TextField(LuceneConstants.HEADLINE, line, Field.Store.YES));
//                        doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, line, Field.Store.YES));
                    }
                } else if (curr == 13) {
                    if (!line.startsWith("<") && !line.startsWith("</")) {
                        doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, line, Field.Store.YES));
                    }
                } else if (curr == 14) {
                    if (!line.startsWith("<") && !line.startsWith("</")) {
                        doc.get(count - 1).add(new TextField(LuceneConstants.DATE, line, Field.Store.YES));
//                        doc.get(count - 1).add(new TextField(LuceneConstants.TEXT, line, Field.Store.YES));
                    }
                }
            }
            writer.addDocument(doc.get(count - 1));
//            System.out.println(doc.size());
        }
    }

}
