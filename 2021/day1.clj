(ns aoc.2021.day1
  (:import java.lang.Math)
  (:require [clojure.string :as string]
            [clojure.java.io :as io]))

(def lines (-> (slurp "inputs/day1.txt")
               (string/split #"\n")))
(def depths  (map #(Integer/parseInt %) lines))

;; original part one
(-> (reduce (fn [[counter lst] el]
              (if (> el lst)
                [(inc counter) el]
                [counter el]))
            [0 (first depths)]
            depths)
    first);; => 1791

;; improved part one
(->> depths
     (partition 2 1)
     (filter (fn [[one two]] (< one two)))
     count);; => 1791

(def windows (partition 3 1 depths))

;; original part two
(-> (reduce (fn [[counter lst] el]
              (if (> (reduce + el) (reduce + lst))
                [(inc counter) el]
                [counter el]))
            [0 (first windows)]
            windows)
    first);; => 1822

;; improved part two
(->> windows
     (map #(reduce + %))
     (partition 2 1)
     (filter (fn [[one two]] (< one two)))
     count);; => 1822