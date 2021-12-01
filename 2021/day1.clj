(ns aoc.2019.day1
  (:import java.lang.Math)
  (:require [clojure.string :as string]
            [clojure.java.io :as io]))

;; original part one
(comment
  (let [windows (-> (slurp "inputs/day1.txt")
                    (string/split #"\n")
                    (->> (map #(Integer/parseInt %))))]
    (reduce (fn [[counter lst] el]
              (if (> el lst)
                [(inc counter) el]
                [counter el]))
            [0 (first windows)])))

;; improved part one
(-> (slurp "inputs/day1.txt")
    (string/split #"\n")
    (->> (map #(Integer/parseInt %))
         (partition 2 1)
         (filter (fn [[one two]] (< one two)))
         count));; => 1791

;; original part two
(comment
  (let [windows (-> (slurp "inputs/day1.txt")
                    (string/split #"\n")
                    (->> (map #(Integer/parseInt %))
                         (partition 3 1)))]
    (reduce (fn [[counter lst] el]
              (if (> (reduce + el) (reduce + lst))
                [(inc counter) el]
                [counter el]))
            [0 (first windows)]
            windows)))

;; improved part two
(-> (slurp "inputs/day1.txt")
    (string/split #"\n")
    (->> (map #(Integer/parseInt %))
         (partition 3 1)
         (map #(reduce + %))
         (partition 2 1)
         (filter (fn [[one two]] (< one two)))
         count));; => 1822