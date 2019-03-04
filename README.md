# Postmortem: infix-postfix-converter

## Timeline

### Phase I - Modeling
* 01/07/2019 - **Project Start**
* 01/26/2019 - **Arithmetic Model Complete**

### Phase II - Parsing
* 01/27/2019 - **Started Postfix Parser Implementation**
* 01/29/2019 - **Completed Postfix Parser Implementation**
* 02/02/2019 - **Started Postfix Writer Implementation**
* 02/03/2019 -
    * **Completed Postfix Writer Implementation**
    * **Started Infix Writer Implementation**
    * **Started Infix Parser Implementation**

### Phase III - Polish
* 02/04/2019 - **Started and Completed Converter Implementations**
* 02/06/2019 - **Started Command Line Interface**
* 03/02/2019 - **Started PEMDAS Support for Infix Parser**
* 03/03/2019 - 
    * **Finished PEMDAS Support for Infix Parser**
    * **Finished Command Line Interface**
    * **Project End**

## Successes
* The project is actually complete (the main goal of project was to complete something).
* The Arithmetic model is versatile.
* Code is fairly clean and readable.
* Followed Test Driven Development (personal goal).

## Failures
* Does not support exponents.
* Has not been optimized, various implementations would likely not perform well in speed critical situations.

## Lessons Learned
1. How satisfying it feels to see a project through to the end, and I hope to carry that forward in to much bigger and more useful projects.
2. Test Driven Development makes it easy to be confident in your unit tests, and therefore confident in your commits, improving development time. 
