# redis horus package
[![horusable](https://cdn.rawgit.com/crambit/horus/master/public/badges/horusable.svg)](https://github.com/crambit/horus)

Redis monitoring. Submits the result of `INFO` command.

## Producer

Source: https://github.com/riemann/riemann-redis

``` bash
gem install riemann-redis
riemann-redis --host my.riemann.server --tag '#redis'
```

If you want to use a different tag, please make sure to also modify the namespace tag in the configuration so that both match.

## Metrics

See [services](https://github.com/elafleur/horuspack-redis/blob/master/services) for a list of event services exported by the producer.

## License

Copyright (c) 2016 Eric Lafleur

Distributed under the Eclipse Public License, the same as Clojure.
