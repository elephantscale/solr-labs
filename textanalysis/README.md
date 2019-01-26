# Text analysis

In this lab we will adjust the schema to implement the text analysis
techniques for tweets

Lab Goals

1. Adjust managed-schema
2. Re-index the documents
3. Verify text analysis

### STEP 1) Use the schema from conf/managed-schema in this lab's directory

The examples in this chapter depend on a few minor customizations to the managed-schema that ships with the Solr example. 
Replace the managed-schema file that ships with the Solr example found in conf/ in this lab.


*** NEEDS TO REPLACE THE FILE SCHEMA.XML IN THE CONF DIRECTORY WITH MANAGED-SCHEMA ***
$SOLR_INSTALL/server/solr/collection1/conf/managed-schema by doing cp conf/managed-schema SOLR_INSTALL/server/solr/collection1/conf/

*** I DID NOT FIND ANYTHING ABOUT IT IN THE LATEST VERSION OF SOLR ***

In addition, you need to copy the wdfftypes.txt file to the conf directory:

    cp conf/wdfftypes.txt $SOLR_INSTALL/example/solr/collection1/conf/

************************************************************************

Finally, delete everything in your data directory to start with an empty search index:

    rm -rf $SOLR_INSTALL/example/solr/collection1/data/*

### STEP 2) Re-index the tweet documents in data/

### STEP 3) Verify text analysis
  
1. Select core
2. Go to the "Analysis" screen
3. Verify that the fields are analyzed correctly, check the indexing and the query parts.