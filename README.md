# name-generator

Random name generator based on 2010 and 1990 US census data. Mainly just to brush up on my Clojure.

## clj

We must ensure the `resources` directory is added to the classpath. I've added an alias for it in `deps.edn`. But it still must be loaded at runtime.

```bash
clj -C:resources -m name-generator.core
```

## Leiningen

The `resources` directory will automatically be loaded into the classpath via Leiningen.

### UberJAR

```bash
lein uberjar
java -jar target/uberjar/name-generator-0.1.0-SNAPSHOT-standalone.jar
```

## License

Copyright © 2018 Evan Louie

Distributed under the Eclipse Public License
