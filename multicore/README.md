## Multicore Solr

In this lab we will create a second core, and run it in the same Solr server 
as the first one.

Lab Goals

1. Create another core
2. Register it in Solr
3. Run queries against it

### STEP 1) Do a recursive copy of collection1. Call it newcore

cd $SOLR_INSTALL/example/solr

cp -a collection1 newcore

cd newcore;

rm -rf core.properties data README.txt

### STEP 2) Register it with Solr (hint: we are using Solr 4.9)

  Try to find the information online

  Report your results

  Barring that

 http://localhost:8983/solr/admin/cores?action=CREATE&name=newcore&instanceDir=newcore&config=solrconfig.xml&dataDir=data&schema=schema.xml

### STEP 3) Index some documents (tweets) into the second core

### STEP 4) Run queries against individual cores

Bonus

### STEP 5) Run queries against combined cores