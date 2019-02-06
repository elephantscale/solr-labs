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



### Step 3) Edit the file for `SolrJAddDocumentClientAPI.java`

Ok, now we need to edit the file for `SolrJAddDocumentClientAPI.java`

Notice that we are adding the following document with this code:

```java

    document.addField("id","HPPRO445");
    document.addField("name","HP Probook 445");
    document.addField("manu","Hewlett Packard");
    document.addField("features", "8GB DDR3LSD RAM");
    document.addField("weight","1.2");
    document.addField("price","800");

```

Here's the code you need to uncomment:

```java
    //TODO: Uncomment out this code
    //UpdateResponse response = solr.add(document);
    //solr.commit();
    //_log.info(response.toString());
```

Go ahead and uncomment out the code.


### Step 4) Run the application for Add Document

```bash
java -cp target/solrjdemo-1.0-SNAPSHOT-jar-with-dependencies.jar com.es.solrjdemo.SolrJAddDocumentClientAPI.java
```

Let's check to see if our add worked:

```bash
curl "http://localhost:8983/solr/techproducts/select?q=HP"
```

If all is well you should see the following response:

```json
{
  "responseHeader":{
    "zkConnected":true,
    "status":0,
    "QTime":8,
    "params":{
      "q":"HP"}},
 13
  "response":{"numFound":1,"start":0,"maxScore":4.492892,"docs":[
      {
        "id":"HPPRO445",
        "name":"HP Probook 445",
        "manu":"Hewlett Packard",
        "features":["8GB DDR3LSD RAM"],
        "weight":1.2,
        "price":800.0,
        "price_c":"800,USD",
        "_version_":1624730521588203520,
        "price_c____l_ns":80000}]
  }}
```



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
