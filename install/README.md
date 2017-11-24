# ES install

In this lab we will practice the installation of the Solr server.


Lab Goals:

* Install Solr and verify its operation
* Prepare to use this install for all subsequent labs

### STEP 1) Login to the server
 
Each student is provided their individual server and credentials

(Instructor: use our ubuntu AMI, t2.large or t2.xlarge instances and Elasticsearch security group)

### STEP 2) Verify the environment

Verify that you have Java installed 

    java -version

### STEP 3) Download and install ES

    curl -L –O https://download.elastic.co/elasticsearch/elasticsearch/
    elasticsearch-2.3.3.tar.gz
    tar -zxvf elasticsearch-2.3.3.tar.gz
    cd elasticsearch-2.3.3

### STEP 4) Start ES

    bin/elasticsearch
    
_Tip_: You can run it in the background as a daemon by using the -d option
    
### STEP 5) Verify install

    curl 'http://localhost:9200/?pretty'

### Environment Variables

* The JAVA_OPTS passed to JVM is used by Elasticsearch

### Configuration files

    elasticsearch.yml

Configure different Elasticsearch modules.

    logging.yml
    
Configure the Elasticsearch logging
    
### STEP 6) ES options

    bin/elasticsearch 
or
    bin/elasticsearch --help
    
Study the options




