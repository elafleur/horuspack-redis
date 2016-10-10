# redis horus package
[![horusable](https://cdn.rawgit.com/crambit/hobs/master/public/badges/horusable.svg)](https://github.com/crambit/hobs)

Redis monitoring. Submits the result of `INFO` command.

## Producer

Source: https://github.com/riemann/riemann-redis

``` bash
gem install riemann-redis
riemann-redis --host my.riemann.server --interval 1 --tag '#redis'
```

If you want to use a different tag, please make sure to also modify the namespace tag in the configuration so that both match.

## License

Copyright (c) 2016 Eric Lafleur

Distributed under the Eclipse Public License, the same as Clojure.
