# reagent-comps

## Introduction
`reagent-comps` is a collection of simple reagent components.

## Install
The simplest way to use `reagent-comps` in a clojurescript project, is by including it in the dependency vector on your project.clj file:

```clojure
[reagent-components "0.1.0"]
```

## Events

### Creating event streams
To listen to events from a reagent component, you'll need to create an event stream tuple. It contains the function for *pushing* and the actual *observable*.

```clojure
(require '[beicon.core :as rx]
         '[reagent-comp.beiconx :as rxt])

(let [[f v$] (rxt/factory)
      cancel (rx/on-value v$ #(println "event:" %))]
    (f 1) ;; ==> event: [1] always passes arrays
    (f 1 2) ;; ==> event: [1 2])
```


## License

`reagent-comps` is licensed under GPLv3 license:
