# name-generator

Random name generator based on 2010 and 1990 US census data. Mainly just to brush up on my Clojure.

## Dev / clj

```bash
clj -C:resources -m name-generator.core
```

## Build / UberJAR

```bash
lein uberjar
java -jar target/uberjar/name-generator-0.1.0-SNAPSHOT-standalone.jar
```

## License

Copyright © 2018 Evan Louie

Distributed under the Eclipse Public License
