# Creating Collection and Indexing Data

In general, indexing is an arrangement of documents or (other entities) systematically. Indexing enables users to locate information in a document.

## Lab Goal

- Indexing collects, parses, and stores documents.

### Step 1 - Creating a collection ) 

In Apache Solr, we can index (add, delete, modify) various document formats such as xml, csv, pdf, etc. We can add data to Solr index in several ways.

- Open one of your nodes , for e.g

    http://nodeIP:8983
    
Note: 8983 is the default port for Solr

- Click on  Collections on the left menu
- Click on  Add Collection (for e.g. Enron)
- Input name 
- Open config set and select **__default**  
- input 2 for **numShards**
- input 2 for **replicationFactor**

Now, after couple of second this collection will appear on your other nodes.

You can also create this using the command line:

```bash
sudo -u zk /opt/solr/bin/solr create -c Enron -s 2 -rf 2
```

### Step 1 - Indexing data ) 

For this sample we are going to use Enron dataset.

Go to /home/ubuntu and create a directory called dataset

```bash
cd /home/ubuntu
sudo mkdir dataset
cd dataset
```

Then download the dataset

```bash
sudo wget https://s3.amazonaws.com/freeeed.org/enron/results/enron001.zip
```

Extract the downloaded dataset

```bash
sudo unzip enron001.zip

sudo rm -rf enron001.zip
```
 
 now we can index this dataset with solr, go to solr installation directory.
 We'll assume that you followed **multi-node-install** correctly and Solr is installed in **/opt/solr/**
 
 ```bash
cd /opt/solr/bin/
```
 
to start indexing with zookeeper use the following template

```bash
sudo -u zk ./post -c  CollectionName datasetAddress
```

**CollectionName**: Name of the collection that you created in the previous step

**datasetAddress**: Our dataset address, in our example it is Enron

so use this command for our example

```bash
sudo -u zk ./post -c  Enron /home/ubuntu/dataset
```

this operation could take minutes.

once you get the following result, you are done indexing and you can start creating search queries in Solr.


```console
COMMITting Solr index changes to 
http://localhost:8983/Solr/Solr_sample/update... 
Time spent: 0:00:00.228
```

