# Solr install

In this lab we will practice the installation of the Solr server.


Lab Goals:

* Install Solr and verify its operation
* Prepare to use this install for all subsequent labs

### STEP 1) Login to the server
 
Each student is provided his/her individual server and credentials

(Instructor: use m3.medium instance and Solr security group)

### STEP 2) Verify the environment

Verify that you have Java installed 
```bash
    java -version
```
### STEP 3) Download and install Solr

Solr is found here, [Solr web site]
```bash(https://www-eu.apache.org/dist/lucene/solr/7.6.0) 
```

Find out the link to download, copy the link, then use the wget (or curl) command to place the Solr bundle on your server, such as


```bash
    wget https://www-eu.apache.org/dist/lucene/solr/7.6.0/solr-7.6.0.tgz
```

Decompress and untar the bundle. You can put Solr in any directory.

(Standard for Linux would be `/opr/solr`)

For example,
```bash
    tar zxf solr-7.6.0.tgz
```
### STEP 4) Start Solr

Assuming that `$SOLR_INSTALL/` is where you put your Solr

```bash
    cd $SOLR_INSTALL/bin 
    start
```    

### STEP 5) Verify If Solr Is Up And Running
```bash
Open this URL in your browser: http://your-server-url:8983/solr
```
You will be redirected to the Solr admin console

### STEP 6) Stop Solr

```bash
    cd $SOLR_INSTALL
    ./bin/solr stop -all
```

Now start Solr again


### STEP 7) Index some data
```bash
    bin/post -c gettingstarted docs/
```
### STEP 8) Answer the following questions:

1 What is the value of the lucene-spec version property for your Solr server?

2 What is the log level of the org.apache.solr.core.SolrConfig class?

3 What is the value of the maxDoc property for the cores present?

4 What is the value of the java.vm.vendor Java system property?

5 What is the segment count for the cores present?

6 What is the response time of pinging your server?

7 What is the top term for the manu field? (Hint: select the manu field in the schema
browser, and click the Load Term Info button.)

8 What is the current size of your documentCache? (Hint: think stats.)

9 What is the analyzed value of the name Belkin Mobile Power

Hint: select the name field on the Analysis page
