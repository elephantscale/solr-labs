## SolrJ API 

In this lab we will investigate a project using Solr API to do CRUD type operations


Lab Goals:

1. Learn how to connect to solr programatically through Java.
2. Understand how to user SolrJ to do CRUD type quries and operations.

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


Build:

```bash
mvn clean package
```

Run the following:

```bash
java -cp target/solrjdemo-1.0-SNAPSHOT-jar-with-dependencies.jar com.es.solrjdemo.SolrJAddDocumentClientAPI
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

### Step 5) Edit the file for `SolrJAddDocumentsClientAPI.java`

Ok, now we need to edit the file for `SolrJAddDocumentsClientAPI.java`

We have several products we need to create here. Here is what is already in the file:

```java
  // TODO: Make up a product here.
  //SolrInputDocument document1 = new SolrInputDocument();
  //document1.addField("id","id1");
  //document1.addField("name","product1");
  //documentList.add(document1);

```

Make up some new products.  Remember that you do need to have a unique id for each one, so it's best if you 
pick one is unique.

Once you are done making up fake data, you need to uncomment and modify the following line of code:

```java
 // TODO: Uncomment and complete:
 //  UpdateResponse response = solr.???(???);
 // solr.commit();
```

### Step 6) Run and Test Your Code


Build:

```bash
mvn clean package
```


Run the following:


```bash
java -cp target/solrjdemo-1.0-SNAPSHOT-jar-with-dependencies.jar com.es.solrjdemo.SolrJAddDocumentsClientAPI
```

Verify your add worked. To do this you need to compose a query that will match your new data. Do that either with curl (as shown earlier)
or with the Solr Search Web based Interface.

### Step 7) **QUERY TIME!!**

Time to do a query programatically.  Edit the file for  `SolrJSearchClientAPI.java`


Now build and run your query:

```bash
mvn clean package
java -cp target/solrjdemo-1.0-SNAPSHOT-jar-with-dependencies.jar com.es.solrjdemo.SolrJSearchClientAPI
```

You should get the following results:

```console
2019-02-06 15:32:21 INFO  SolrJSearchClientAPI:35 - {responseHeader={zkConnected=true,status=0,QTime=9,params={q=ipod,fl=id,name,wt=javabin,version=2}},response={numFound=3,start=0,maxScore=4.235964,docs=[SolrDocument{id=F8V7067-APL-KIT, name=Belkin Mobile Power Cord for iPod w/ Dock}, SolrDocument{id=IW-02, name=iPod & iPod Mini USB 2.0 Cable}, SolrDocument{id=MA147LL/A, name=Apple 60 GB iPod with Video Playback Black}]}}
```

Observe the results.

### Step 8) Modify and run the Delete Code

Edit the file `SolrJDeleteDocumentClientAPI.java`

Complete the TODO Items.

Now build and run your query:

```bash
mvn clean package
java -cp target/solrjdemo-1.0-SNAPSHOT-jar-with-dependencies.jar com.es.solrjdemo.SolrJDeleteDocumentClientAPI
```

