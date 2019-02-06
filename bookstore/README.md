# Bookstore example 

In this lab we will build a bookstore search and explore it.
This is a real-life lab, where you may need to be creative, reformat
the documents, and invent your own schema and features.

Lab Goals:

1. Explore real-world imports
2. Find ways of dealing with different document formats

### STEP 1) Import the provided book catalog

Let's create a new collection called `bookstore` . You know how to do this. If not, find a previous lab.

Go ahead and import the example docs in `$SOLR_HOME/example/exampledocs`.

The catalog is provided in the Solr installation here

    $SOLR/example/exampledocs

Use the post command 

```bash
$ bin/post ...... FILL IN THE BLANK .....
```

    
What additional parameters do you need to give? 

Hint: think of the 'indexing' lab we have done before

### STEP 2) Import another book catalog

It can be found in $SOLR_LAB/bookstore/data/books/books.json

(It came from this repository, https://github.com/tamingtext)

Does it work?

Why does it work? 

Can you explain why luckily our schema accepted it?

### Bonus) Import an XML book catalog

Now this file is found in the data/books/books.xml in this lab, but it is not formatted the way we expect it
(it is from a Microsoft XML books collection used to explain XML services)

What would be the ways to import it?

(Hint: how does one transform XML? - manually or XSLT)
    
### Bonus plus) Add features appropriate for book lovers

Use your imagination
