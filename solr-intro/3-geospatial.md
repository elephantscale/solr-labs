# Geospatial Query

In this lab we will practice doing a simple query


Lab Goals:

* Do a geospatial query

### STEP 1) Open Solr 

Open this URL in your browser: 

```bash
http://your-server-url:8983/solr
```

You will be redirected to the Solr admin console


### STEP 2) Index some data (if needed)

If you need to do this again, index the techproducts data. If you are continuing from previous labs this should already be done.

```bash
    bin/post -c techproducts example/exampledocs/*
```

### Step 3) Perform a query:

Navigate in your browser to the following url: 

```text
http://YOURPIPADDRESS:8983/solr/#/techproducts/query
```

You can now navigate to do a *spatial* query

![](../images/solr-spatial.png)


Try experimenting with this yourself.

You can also open the following urL;

```text
http://YOURIPADDRESS:8983/solr/techproducts/browse?q=ipod&pt=37.7752%2C-122.4232&d=10&sfield=store&fq=%7B%21bbox%7D&queryOpts=spatial&queryOpts=spatial
```


