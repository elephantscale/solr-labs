# Solr indexing and searching, part A. Example documents 

In this lab we will practice the direct indexing of documents with Solr server and searching in them.

### Lab Goals:


1. Index example documents
2. Search the results


### STEP 1) Stop and restart Solr (if needed)
```bash
cd $SOLR_HOME
bin/solr stop -all
./bin/solr start -c -p 8983 -s example/cloud/node1/solr
./bin/solr start -c -p 7574 -s example/cloud/node2/solr -z localhost:9983

```


### Step 2) Create A a new collection

```bash 
 ./bin/solr create -c tweets -s 2 -rf 2

```

### STEP 2) Index documents


Let's start by doing the exampledocs

```bash
    $ ls $SOLR_INSTALL/example/exampledocs
    $ bin/post -c tweets example/exampledocs/*.xml
```

### STEP 3) Execute the *:* query
 For that, select `tweets`. Follow the screenshot on the slides.

### STEP 4) Investigate various search options provided by Solr
 Compose your list, then compare it to the screenshot on the slides.

### Step 5) Clean up the Index

This will delete all the documents in  the `tweets` collection

```bash
bin/post -c tweets -d "<delete><query>*:*</query></delete>"
```


# Solr indexing and searching, part B. Tweets in XML and JSON

In this lab we will practice the indexing of XML and JSON tweet documents.

#### The documents are found in this labs's data folder, $SOLR_LAB

For example, `$SOLR_LAB=~/solr_labs`


Lab Goals:

1. Index tweet documents
2. Search the results

### STEP 1) Index XML documents
```bash
    bin/post -c tweets $SOLR_LABS/indexing/data/tweets/tweets.xml
```
### STEP 2) Execute the *:* query. 

Click Query under tweets in the menu on the left, and execute query type_s:post

### STEP 3) Index JSON documents
```bash
    bin/post -c tweets $SOLR_LABS/indexing/data/tweets/tweets.json
```
### Notice:

We use dynamic fields. For instance, `screen_name_s` will is a string because of the `_s suffix` on the name.

Question: do we have 4 documents in the index now? We indexed 2 documents 2 times! If not, explain why not.


## Solr indexing and searching, part C: document deletion

In this lab we will practice various ways of deleting documents from the Solr index.


Lab Goals:

1. Delete specific document by ID
2. Delete all documents

### STEP 1) Index documents as above. Use either example documents or tweets.

### STEP 2) To delete a document by ID

Use the following query form
```bash
    <delete>
      <id>1</id>
    </delete>
```
With the update handler below 
```bash
    curl http://localhost:8983/solr/update
    
[Hint](https://wiki.apache.org/solr/UpdateXmlMessages)
```
Another way to delete a document
```bash
    $bin/post -Ddata=args -Dcommit=? "<delete><id>?</id></delete>"
```
### STEP 3) To delete all documents in the collection
```bash
    curl http://localhost:8983/solr/update --data '<delete><query>*:*</query></delete>' -H 'Content-type:text/xml; charset=utf-8'
    curl http://localhost:8983/solr/update --data '<commit/>' -H 'Content-type:text/xml; charset=utf-8'
```

### STEP 4) To Delete the entire collection

```bash
bin/solr delete -c tweets
```
