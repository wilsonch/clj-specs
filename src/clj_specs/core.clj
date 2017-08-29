
;; # Clojure specs
(ns clj-specs.core
  (:require 
    [clojure.spec.alpha :as s]
    [clojure.pprint :refer [pprint]]))

;; ## Predicates
;; - s/def adds a spec to the registry of specs
(s/def ::big-even (s/and int? even? #(> % 1000)))

;; ## Conform 
;; - s/conform returns the value if it conforms to the given spec or :clojure.spec/invalid otherwise

;; 12000
(s/conform ::big-even 12000)

;; :clojure.spec.alpha/invalid
(s/conform ::big-even 123)

;; ## Explain 
;; - why the data doesn't conform to the given spec
(s/explain ::big-even 123)
; val: 123 fails spec: :clj-specs.core/big-even predicate: even?

(s/explain-data ::big-even "not a number")
; returns explanation as data structure

;; ## Generate test data
;; - s/exercise requires [org.clojure/test.check "0.10.0-alpha2"]
(pprint (s/exercise ::big-even))
; returns tuples like [[an-example-value a-conformed-value]]

;; ## Structural
;; ### s/coll-of
;; - specify structure of a collection

(s/conform (s/coll-of number?) [5 10 2])

;; s/coll-of also takes the following:
;; :kind - a predicate or spec that the incoming collection must satisfy, such as vector?
;; :count - specifies exact expected count
;; :min-count, :max-count - checks that collection has (<= min-count count max-count)
;; :distinct - checks that all elements are distinct
;; :into - one of [], (), {}, or #{} for output conformed value. If :into is not specified, the input collection type will be used.

(s/def ::a-numeric-vector-of-3-distint-values 
  (s/coll-of number? :kind vector? :count 3 :distinct true :into #{}))

(s/conform ::a-numeric-vector-of-3-distint-values [1 2 3])
;;=> #{1 2 3}

;; ### s/keys
;; - specify required or optional keys in a map
(s/def ::person (s/keys :req [::first-name ::last-name ::email]
                        :opt [::phone]))


;; ### Specify every element of a collection should be valid
(s/every)

;; ### Specify a map of
(s/map-of)

;; ## Sequences

;; ## Regex
;; - s/cat s/* s/+ s/&


;; # Functions
;; - s/fdef 
;; :args 
;; :ret 
;; :fn
(s/fdef some-fn 
  :args (some-predicate)
  :ret int?
  :fn (some-relationship-between-ret-and-args)) 


  ;; # Validation
  ;; - using spec for validation
  (defn person-name
  [person]
  {:pre [(s/valid? ::person person)]
   :post [(s/valid? string? %)]}
  (str (::first-name person) " " (::last-name person)))

(defn- set-config [prop val]
  ;; dummy fn
  (println "set" prop val))

(defn configure [input]
  (let [parsed (s/conform ::config input)]
    (if (= parsed ::s/invalid)
      (throw (ex-info "Invalid input" (s/explain-data ::config input)))
      (for [{prop :prop [_ val] :val} parsed]
        (set-config (subs prop 1) val)))))

(configure ["-server" "foo" "-verbose" true "-user" "joe"])

