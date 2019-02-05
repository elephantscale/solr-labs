# IR - Information Retrieval

In this lab we will practice measuring the performance of search engines, treating them is IR (information retrieval) systems.
We will look at the two major metrics: precision and recall.


Lab Goals:

1. Run search engine queries, with the view of measuring their metrics.
2. Calculate the precision and recall for Google and Bing.
3. Challenge task



### You will be putting the results in this comparison table:

    | Query    |  Google  |   Bing  |
    | ---------|:--------:|----------
    | Precision|          |         |
    | Recall   |          |         |

### STEP 1) Run the query of your choice in Google. Measure precision and recall for the entire page of results

Assume that the first 50 results returned contain *all* results. This is relevant for calculating recall, because we will assume that the first five pages represents all
possible documents to return.

For calculating Precision, consider the top 10 results.

### STEP 2) Run the same query in Bing. Measure precision and recall.

Make the same assumptions as before.


### STEP 3) Challenge: find cases where Bing outperforms Google

1. On precision
2. On recall


### Step 4) Perform a Precision versus recall curve for points at time k

Use a language of your choice: python, R, Excel, etc to do a quick plot of precision vs recall. Do this for *both* google and bing.

Here's what you will do:

1. Take each of the first 10 results, and calculate the precision and recall for *that* result only
   - the first result should have a precision of exactly `0` or `1` and a recall of exactly `0.02` or `0.0`

2. Plot the 10 data points on an axis with `X=recall and Y=precision`.
