# Solr Query

In this lab we will practice doing some


Lab Goals:

* Use the various query parers

### Step 3) use the standard query parser

You will use the following standard query parser query: `id:SP2514N&wt=json`

**=> Type the following:**

```bash
curl "http://localhost:8984/solr/techproducts/select?q=id:SP2514N&wt=json"
```

You should get the same JSON output.


```json
{
  "responseHeader":{
    "status":0,
    "QTime":1,
    "params":{
      "q":"id:SP2514N",
      "wt":"json"}},
  "response":{"numFound":1,"start":0,"docs":[
      {
        "id":"SP2514N",
        "name":["Samsung SpinPoint P120 SP2514N - hard drive - 250 GB - ATA-133"],
        "manu":["Samsung Electronics Co. Ltd."],
        "manu_id_s":"samsung",
        "cat":["electronics",
          "hard drive"],
        "features":["7200RPM, 8MB cache, IDE Ultra ATA-133",
          "NoiseGuard, SilentSeek technology, Fluid Dynamic Bearing (FDB) motor"],
        "price":[92.0],
        "popularity":[6],
        "inStock":[true],
        "manufacturedate_dt":"2006-02-13T15:26:37Z",
        "store":["35.0752,-97.032"],
        "_version_":1650098037891006464}]
  }}
```

You can also find the same search term on the website.


Now, do a second query searching for the word "video":

```bash
curl http://localhost:8983/solr/techproducts/select?q=video&fl=name+score
```


See the results that you get.

### Step 5: Use the DisMax Query Parser

To search for a term, enter it as the q parameter value in the Solr Admin UI Query screen, replacing `*:*` with the term you want to find.

You will use the dismax using the following query: `q=video&fl=*,score`

Enter "foundation" and hit Execute Query again.

If you prefer curl, enter something like this:

```bash
curl "http://localhost:8983/solr/techproducts/select?defType=dismax&q=video&fl=*,score"
```


Observer the JSON output. Does it make sense


## More DisMax queries:

Results for the word "video" using the standard query parser, and we assume "df" is pointing to a field to search:

`http://localhost:8983/solr/techproducts/select?q=video&fl=name+score`


### Dismax parser search fields

The "dismax" parser is configured to search across the text, features, name, sku, id, manu, and cat fields all with varying boosts designed to ensure that "better" matches appear first, specifically: documents which match on the name and cat fields get higher scores.

 * `http://localhost:8983/solr/techproducts/select?defType=dismax&q=video`

Note that this instance is also configured with a default field list, which can be overridden in the URL.

 * `http://localhost:8983/solr/techproducts/select?defType=dismax&q=video&fl=*,score`

You can also override which fields are searched on and how much boost each field gets.

 * `http://localhost:8983/solr/techproducts/select?defType=dismax&q=video&qf=features^20.0+text^0.3

You can boost results that have a field that matches a specific value.

 * `http://localhost:8983/solr/techproducts/select?defType=dismax&q=video&bq=cat:electronics^5.0`

Another request handler is registered at "/instock" and has slightly different configuration options, notably: a filter for (you guessed it) inStock:true).

 * `http://localhost:8983/solr/techproducts/select?defType=dismax&q=video&fl=name,score,inStock`

 * `http://localhost:8983/solr/techproducts/instock?defType=dismax&q=video&fl=name,score,inStock`


### DisMax parser minimumNumberShouldMatch

One of the other really cool features in this parser is robust support for specifying the "BooleanQuery.minimumNumberShouldMatch" you want to be used based on how many terms are in your user’s query. These allows flexibility for typos and partial matches. For the dismax parser, one and two word queries require that all of the optional clauses match, but for three to five word queries one missing word is allowed.

 * `http://localhost:8983/solr/techproducts/select?defType=dismax&q=belkin+ipod`

 * `http://localhost:8983/solr/techproducts/select?defType=dismax&q=belkin+ipod+gibberish`

 * `http://localhost:8983/solr/techproducts/select?defType=dismax&q=belkin+ipod+apple`

Use the debugQuery option to see the parsed query, and the score explanations for each document.

 * `http://localhost:8983/solr/techproducts/select?defType=dismax&q=belkin+ipod+gibberish&debugQuery=true`

 * `http://localhost:8983/solr/techproducts/select?defType=dismax&q=video+card&debugQuery=true`


## EDisMax Parser

Boost the result of the query term "hello" based on the document’s popularity:

 * `http://localhost:8983/solr/techproducts/select?defType=edismax&q=hello&pf=text&qf=text&boost=popularity`

Search for iPods OR video:

  * `http://localhost:8983/solr/techproducts/select?defType=edismax&q=ipod+OR+video`
Search across multiple fields, specifying (via boosts) how important each field is relative each other:

  * `http://localhost:8983/solr/techproducts/select?q=video&defType=edismax&qf=features^20.0+text^0.3`
You can boost results that have a field that matches a specific value:

  * `http://localhost:8983/solr/techproducts/select?q=video&defType=edismax&qf=features^20.0+text^0.3&bq=cat:electronics^5.0`

Using the mm parameter, 1 and 2 word queries require that all of the optional clauses match, but for queries with three or more clauses one missing clause is allowed:

  * `http://localhost:8983/solr/techproducts/select?q=belkin+ipod&defType=edismax&mm=2`
  * `http://localhost:8983/solr/techproducts/select?q=belkin+ipod+gibberish&defType=edismax&mm=2`
  * `http://localhost:8983/solr/techproducts/select?q=belkin+ipod+apple&defType=edismax&mm=2`

In the example below, we see a per-field override of the qf parameter being used to alias "name" in the query string to either the “last_name” and “first_name” fields:

```text
defType=edismax
q=sysadmin name:Mike
qf=title text last_name first_name
f.name.qf=last_name first_name
```

**TODO**: Try to take the above and compose your own query with it!


