# Installing SolrCloud

In this lab we will launch solr nodes in cloud mode through the wizard

Lab Goals

Start and use SolrCloud

### STEP 1) get started

```bash
$bin/solr -e cloud
```
### STEP 2) Asking The Number Of Solr Nodes
 answer the question "how many solr nodes would you like to run in your local cluster?" by number 2

### STEP 3)Determine Port Number

leave the default values:
first node 8983 and second node 7574

the command it uses to start the server is:
```bash
solr start -cloud -s example/cloud/node1/solr -p 8983
```

The first node will also start an embedded ZooKeeper server bound to port 9983. The Solr home for the first node is in `example/cloud/node1/solr` as indicated by the -s option

### STEP 4) Name Of Collection 

The next question is about the collection nama. The default name is `gettingstarted`. Type your own

### STEP 5) Number Of Shards

Please accept the default: 2

### STEP 6) Number Of Replicas For Each Shard

Leave the default: 2

### STEP 7) Name Of Configuration Directory

Choose `_default`

### STEP 8) Disable Schemaless Functionality

It's done in order to lock down the schema so that documents indexed after doing so will not alter the schema or to configure the schema by yourself.
This can be done as follows (assuming your collection name is mycollection).

V1 API:
```bash
curl http://host:8983/solr/mycollection/config -d '{"set-user-property": {"update.autoCreateFields":"false"}}'
```

V2 API SolrCloud:
```bash
curl http://host:8983/api/collections/mycollection/config -d '{"set-user-property": {"update.autoCreateFields":"false"}}'
```

### STEP 9) Verify New Collection

To check if your new collection has been created:

```bash
bin/solr status
```
If you get any error see the file `example/cloud/node1/logs` and
`example/cloud/node2/logs`

### STEP 10) Cloud Panel

```bash
http://localhost:8983/solr/#/~cloud
```
or 
```bash
bin/solr healthcheck -c gettingstarted
```
