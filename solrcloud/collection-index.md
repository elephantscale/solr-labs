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
- leave other fields as default 
- Click on add collection

Now, after couple of second this collection will appear on your other nodes.

### Step 1 - Indexing data ) 

For this sample we are going to use Enron dataset.

Go to /home/ubuntu and create a directory called dataset

```bash
cd /home/ubuntu
sudo mkdir dataset
cdtdataset
```

Then download the dataset

```bash
sudo wget https://elephantscale-public.s3.amazonaws.com/data/datasets.zip
```

Extract the downloaded dataset

```bash
sudo unzip datasets.zip
cd datasets
```
 
 now we can index this datasets with solr, go to solr installation directory.
 We'll assume that you followed **multi-node-install** correctly and Solr is installed in **/opt/solr/**
 
 ```bash
cd /opt/solr/bin/
```
 
to start indexing use the following template

```bash
./post -c  CollectionName datasetAddress
```

**CollectionName**: Name of the collection that you created in the previous step

**datasetAddress**: Our dataset address, in our example it is Enron

so use this command for our example

```bash
./post -c  Enron /home/ubuntu/dataset/datasets
```

this operation could take minutes.

once you get the following result, you are done indexing and you can start creating search queries in Solr.


```console
COMMITting Solr index changes to 
http://localhost:8983/Solr/Solr_sample/update... 
Time spent: 0:00:00.228
```

