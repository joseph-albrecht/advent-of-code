(ns aoc.2019.day1
  (:import java.lang.Math)
  (:require [clojure.string :as string]
            [clojure.java.io :as io]))

;; part one
(-> (slurp "inputs/day1.txt")
    (string/split #"\n")
    (->> (map #(Integer/parseInt %))
         (partition 2 1)
         (filter (fn [[one two]] (< one two)))
         count));; => 1791

;; part two
(-> (slurp "inputs/day1.txt")
    (string/split #"\n")
    (->> (map #(Integer/parseInt %))
         (partition 3 1)
         (map #(reduce + %))
         (partition 2 1)
         (filter (fn [[one two]] (< one two)))
         count));; => 1822
