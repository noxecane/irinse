# reagent-comps

## Introduction
`reagent-comps` is a collection of simple reagent components.

## Install
The simplest way to use `reagent-comps` in a clojurescript project, is by including it in the dependency vector on your project.clj file:

```clojure
[reagent-comps "0.1.0"]
```

## Events

### Creating event streams
To listen to events from a reagent component, you'll need to create a subject. The subject acts like a function so you can call it's next method.

```clojure
(require '[beicon.core :as rx]
         '[reagent-comp.beiconx :as rxt]) ;; import beiconx as subjects can be used as functions

(let [v (rx/subject)
      _ (rxt/log "Value" v)]
    (v 1) ;; ==> Value: 1
    (v [1 2]) ;; ==> Value: [1 2]
    (v)) ;; end
```


### Converting streams to state
We could use reagent atoms to store the values from streams mainly because reagent itself does not work with reactive streams.

```clojure
(def name-stream (rx/subject))

(def name (rxt/to-ratom "" name-stream))

@name 
;; ==> ""

(name-stream "noxecane")
(name-stream "Obasanjo")
 
@name
;; ==> "Obasanjo"
```

One could also reduce over the state
```clojure
(def fill-list (rx/subject))
(def ilist (rxt/to-ratom [] conj fill-list))

(fill-list 12)
(fill-list 13)
(fill-list 14)

@ilist
;; ==> [12 13 14]
```

### Converting a channel to an observable
There is an equivalent function to `from-promise` for **core.async** channels. Bear in mind that buffers don't matter
as this function eagerly consume new events from the channel and dispatches them regardless of availability of
subscribers. Hence, more like subjects, only events received after subscription matter. 
```clojure
(require '[cljs.core.async :as a])

(def b (a/chan 1))

(def c (rxt/from-chan a))
(rxt/log "From Channel" c)

(a/put! b 10)
;; ==> From Channel: 10
```

### Creating Simple forms
For instance to create a login form component
```clojure
(require '[reagent-comp.forms :as form])

(defn model [{:keys [login-service]}]
  (let [write  (rx/subject)
        submit (rx/subject)
        state  (form/writes write) ;; reduce write events over a map [:name :value]
        login  (rx/flat-map  submit)] ;
    {:write  write
     :submit submit
     :state  state}))

(defn view [{:keys [write submit state]}]
  (fn []
    [:form
      [:input.input {:type      "text"
                     :on-change (form/input write [:username])}]
      [:input.input {:type      "password"
                     :on-change (form/input write [:username])}]
      [:button.button {:type     "button"
                       :on-click #(submit @state)}]]))
```

## License

`reagent-comps` is licensed under GPLv3 license:
