# Solr Intro:

## 1-Install

In this lab we will practice changing the schema


Lab Goals:

* Install Solr and verify its operation
* Prepare to use this install for all subsequent labs

### STEP 1) Stop solr and restart with `gettingstarted` schema

Let's not muck around with the `techproducts` schema we've been working on. Instead, let us restart solrcloud and run
the default gettingstarted schema. 

```bash
bin/solr stop -all
bin/solr start -e cloud
```

This time, just accept all the defaults.

### Step 2) Retrieve the schema

```bash
curl http://localhost:8983/solr/gettingstarted/schema
```

The response will be very lengthy, in JSON format, a sample is like this:

```json
{
  "responseHeader":{
    "status":0,
    "QTime":9},
  "schema":{
    "name":"default-config",
    "version":1.6,
    "uniqueKey":"id",
    "fieldTypes":[{
        "name":"ancestor_path",
        "class":"solr.TextField",
        "indexAnalyzer":{
          "tokenizer":{
            "class":"solr.KeywordTokenizerFactory"}},
        "queryAnalyzer":{
          "tokenizer":{
            "class":"solr.PathHierarchyTokenizerFactory",
            "delimiter":"/"}}},
      {
        "name":"binary",
        "class":"solr.BinaryField"},
      {
        "name":"boolean",
        "class":"solr.BoolField",
        "sortMissingLast":true},
      {
        "name":"booleans",
        "class":"solr.BoolField",
        "sortMissingLast":true,
        "multiValued":true},
{
        "name":"*_p",
        "type":"location",
        "indexed":true,
        "stored":true}],
    "copyFields":[]}}
```


 
### STEP 3) Add a New Field

```bash
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "add-field":{
     "name":"sell_by",
     "type":"pdate",
     "stored":true }
}' http://localhost:8983/solr/gettingstarted/schema
```


You should get a response like this

```console
{ "responseHeader":{
    "status":0,
    "QTime":1662}}
```

Again retrive the schema. It will be long, but try to find your new field.

```bash
curl http://localhost:8983/solr/gettingstarted/schema
```

```json
 {
         "name":"sell_by",
         "type":"pdate",
         "stored":true}
```

### STEP 3) Delete a Field

Now delete your new field

```bash
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "delete-field" : { "name":"sell_by" }
}' http://localhost:8983/solr/gettingstarted/schema
```

Retrive the schema and witness it is no longer there.

```bash
curl http://localhost:8983/solr/gettingstarted/schema
```
