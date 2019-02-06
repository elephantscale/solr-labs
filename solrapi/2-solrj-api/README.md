## SolrJ API 

In this lab we will investigate a project using Solr API, with JUnit test


Lab Goals:

1. Overview of Solr API

### STEP 1)  Investigate the project code, main and test

1. Read and explain the code
2. Run complete build with tests

        mvn clean package 
    
Observe and fix any errors

3. Run only tests

        mvn test



### Step 3) Run Main Class

```bash
java -cp target/solr-lab-1.0-SNAPSHOT-jar-with-dependencies.jar com.es.solr.Main
```

What happened?  Did it work?


### Step 4) Modify File to add Gouda Document

Notice the `addDoucument` method in the file `src/main/java/com/es/solr/SolrOps.java`.

```java
    public void addDocument(File fileToAdd) {
//        try {
            String urlString = "http://localhost:8983/solr/techproducts";
            SolrClient solr = new HttpSolrClient.Builder(urlString).build();;
            SolrInputDocument document = new SolrInputDocument();
            // TODO parse the input document, form fields, add them to the document
//            document.addField("id", "552199");
//            document.addField("name", "Gouda cheese wheel");
//            document.addField("price", "49.99");
//            UpdateResponse response = solr.add(document);
//            solr.commit();
//        } catch (SolrServerException | IOException e) {
//            e.printStackTrace();
//        }
    }

```


Notice that a number of lines are commented out.  Uncomment out the lines so we will be doing the addDocument.


### STEP 4) Modify the project to test in a flexible way

1. Fix every TODO item
2. Run the build
3. Run the tests
