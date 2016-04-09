Morpha Stemmer
==============

This is a Java version of [the Morpha stemmer](http://www.informatics.sussex.ac.uk/research/groups/nlp/carroll/morph.html),
a fast and robust morphological analyser for English based on finite-state
techniques that returns the lemma and inflection type of a word, given the word
form and its part of speech. (The latter is optional but accuracy is degraded
if it is not present).

The original `.lex` and verb-stem files (see `src/main/resources`) were converted to Java using JFlex by Michael Schmitz <schmmd@cs.washington.edu>.
This is a fork/rewrite of [his Morpha wrapper](https://github.com/knowitall/morpha).

Usage
---

```java
import static com.samthomson.morpha.Morpha.getLemma;

// when part-of-speech tags are available:
getLemma("saw", "VBD");  // "see"
getLemma("saw", "NN");   // "saw"

// when part-of-speech tags are not available a best effort is made:
getLemma("saw");  // "see"
getLemma("finding") // "find"
```


From the README for the original distribution:
---
([full version](src/main/resources/README))

> This directory contains software for morphological processing of English
> as developed by Kevin Humphreys <kwh@dcs.shef.ac.uk>, John Carroll
> <john.carroll@cogs.susx.ac.uk> and Guido Minnen.
> 
> To be used for research purposes only (see section 4 below). If you make
> any changes, the authors would appreciate it if you sent them details of
> what you have done.
> 
> Covers the English inflectional suffixes:
> *    -s     plural of nouns, 3rd person singular present of verbs
> *    -ed    past tense
> *    -en    past participle
> *    -ing   progressive of verbs
>
> 4. Acknowledgements, copyrights etc.
> ------------------------------------
> 
> Copyright (c) 1995-2000 University of Sheffield, University of Sussex
> All rights reserved.
> 
> Redistribution and use of source and derived binary forms are
> permitted without fee provided that:
> 
>   - they are not used in commercial products
>   - the above copyright notice and this paragraph are duplicated in
>     all such forms
>   - any documentation, advertising materials, and other materials
>     related to such distribution and use acknowledge that the software
>     was developed by Kevin Humphreys <kwh@dcs.shef.ac.uk>, John
>     Carroll <john.carroll@cogs.susx.ac.uk> and Guido Minnen
>     and refer to the following related publication:
> 
>   Guido Minnen, John Carroll and Darren Pearce. 2001. `Applied
>   morphological processing of English'. Natural Language Engineering,
>   7(3). 207-223.
> 
> The name of University of Sheffield may not be used to endorse or
> promote products derived from this software without specific prior
> written permission.
>   
> This software is provided "as is" and without any express or implied
> warranties, including, without limitation, the implied warranties of
> merchantibility and fitness for a particular purpose.
> 
> The exception lists were derived semi-automatically from WordNet 1.5,
> and various other corpora and MRDs.
> 
> Many thanks to Tim Baldwin, Chris Brew, Bill Fisher, Gerald Gazdar,
> Dale Gerdemann, Adam Kilgarriff and Ehud Reiter for suggested
> improvements.
> 
> WordNet 1.5 Copyright 1995 by Princeton University.
> All rights reseved.
> 
> THIS SOFTWARE AND DATABASE IS PROVIDED "AS IS" AND PRINCETON
> UNIVERSITY MAKES NO REPRESENTATIONS OR WARRANTIES, EXPRESS OR IMPLIED.
> BY WAY OF EXAMPLE, BUT NOT LIMITATION, PRINCETON UNIVERSITY MAKES NO
> REPRESENTATIONS OR WARRANTIES OF MERCHANT- ABILITY OR FITNESS FOR ANY
> PARTICULAR PURPOSE OR THAT THE USE OF THE LICENSED SOFTWARE, DATABASE
> OR DOCUMENTATION WILL NOT INFRINGE ANY THIRD PARTY PATENTS,
> COPYRIGHTS, TRADEMARKS OR OTHER RIGHTS.
> 
> The name of Princeton University or Princeton may not be used in
> advertising or publicity pertaining to distribution of the software
> and/or database.  Title to copyright in this software, database and
> any associated documentation shall at all times remain with Princeton
> University and LICENSEE agrees to preserve same.
