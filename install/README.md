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

    java -version

### STEP 3) Download and install Solr

Solr is found here, [Solr web site](http://lucene.apache.org/solr) 

Find the 4.x or 5.x version you want.

Find out the link to download, copy the link, then use the wget (or curl) command to place the Solr bundle on your server, such as

4.x

    wget http://archive.apache.org/dist/lucene/solr/4.9.1/solr-4.9.1.tgz

5.x

    wget http://apache.spinellicreations.com/lucene/solr/5.2.0/solr-5.2.0.zip

Decompress and untar the bundle. You can put Solr in any directory.

(Standard for Linux would be /opr/solr)

For example,

    tar xf solr-4.9.1.tgz

### STEP 4) Start Solr

Assuming that $SOLR_INSTALL/ is where you put your Solr

4.x

    cd $SOLR_INSTALL/example
    java -jar start.jar
    
    To change the port from 8983, start this way
    sudo /usr/java/latest/bin/java -jar start.jar -Djetty.port=80

5.x

    cd $SOLR_INSTALL
    ./bin/solr start -e cloud -noprompt

### STEP 5) Verify install

Open this URL in your browser: http://your-server-url:8983

You will be redirected to the Solr admin console

### STEP 6) Stop Solr

4.x

For now

    CTRL-X

5.x

    cd $SOLR_INSTALL
    ./bin/solr stop
     
Observe and fix the error message
     
     ./bin/solr stop all

Now start Solr again

### STEP 7) Index some data

4.x

    cd $SOLR_INSTALL/example/exampledocs
    java -jar post.jar *.xml

5.x

    bin/post -c gettingstarted docs/

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
