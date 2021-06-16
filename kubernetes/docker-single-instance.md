
# Lab: Running Solr with host-mounted directories

We will start a single-countainer docker which contains 1 core.  This is a typical developer configuraiton.

##  STEP 1: run Docker with gettingsstarted

```console
$ mkdir solrdata
$ sudo chown 8983:8983 solrdata  # necessary on Linux, not Mac.
$ docker run -d -v "$PWD/solrdata:/var/solr" -p 8983:8983 --name my_solr solr:8 solr-precreate gettingstarted
```

Then with a web browser go to `http://localhost:8983/` to see the Admin Console (adjust the hostname for your docker host).
In the web UI if you click on "Core Admin" you should now see the "gettingstarted" core.

### STEP 2: Load some data

Next load some of the example data that is included in the container:

```console
$ docker exec -it my_solr post -c gettingstarted example/exampledocs/manufacturers.xml
```

In the UI, find the "Core selector" popup menu and select the "gettingstarted" core, then select the "Query" menu item. This gives you a default search for `*:*` which returns all docs. Hit the "Execute Query" button, and you should see a few docs with data. Congratulations!


### Step 3: Use `docker-compose`

Create the following file called `docker-compose.yml`:


```yaml
version: '3'
services:
  solr:
    image: solr:8
    ports:
     - "8983:8983"
    volumes:
      - data:/var/solr
    command:
      - solr-precreate
      - gettingstarted
volumes:
  data:
```

you can simply run:

```console
docker-compose up -d
```

### Step 4: Run the Demo

For quick demos of docker-solr, there is a single command that starts Solr, creates a collection called "demo", and loads sample data into it:

```console
$ docker run --name solr_demo -d -p 8983:8983 solr:8 solr-demo
```
