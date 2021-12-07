(ns aoc.2021.day7
  (:import java.lang.Math)
  (:require [clojure.string :as string]
            [clojure.java.io :as io]))

(def demo "16,1,2,0,4,2,7,1,2,14")

(defn parse-crabs
  [line]
  (-> line
      (string/split #",")
      (->> (map (fn [num] (Integer/parseInt num))))))

(def demo-crabs  (parse-crabs demo))
(def input-crabs (parse-crabs (slurp "inputs/day7.txt")))

(defn abs
  [n]
  (max (- 0 n) n))

(defn fuel-required
  [crabs position]
  (->> crabs
       (map (partial - position))
       (map abs)
       (reduce +)))

(defn fuel-required-triangle
  [crabs position]
  (->> crabs
       (map (partial - position))
       (map abs)
       (map #(/ (* % (inc %)) 2))
       (reduce +)))

(defn find-slope
  [crabs calc position]
  (let [left   (calc crabs (dec position))
        right  (calc crabs (inc position))
        middle (calc crabs position)]
    (cond
      (= (min left right middle) middle) :valley
      (= (max left right middle) middle) :peak
      (< left middle)                    :left
      (< right middle)                   :right)))

(defn binary-search
  [crabs fuel-calc]
  (loop [lo (apply min crabs)
         hi (apply max crabs)]
    (let [mid   (Math/floor (/ (+ lo hi) 2))
          slope (find-slope crabs fuel-calc mid)]
      (case slope
        :valley (fuel-calc crabs mid)
        :left   (recur lo        (dec mid))
        :right  (recur (inc mid) hi)))))

(binary-search demo-crabs fuel-required);; => 37.0
(binary-search demo-crabs fuel-required-triangle);; => 168.0
(binary-search input-crabs fuel-required);; => 328262.0
(binary-search input-crabs fuel-required-triangle);; => 9.0040997E7

(let [crabs input-crabs
      lo    (apply min crabs)
      hi    (apply max crabs)]
  (->> (map-indexed (fn [idx pos] [(fuel-required-triangle crabs pos) idx]) (range lo (inc hi)))
       sort
       ))