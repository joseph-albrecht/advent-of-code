(ns aoc.2021.day3
  (:import java.lang.Math)
  (:require [clojure.string :as string]
            [clojure.java.io :as io]
            [utils :as utils]))

(defn parse-binary
  [string]
  (cond
    (string? string)
    (Integer/parseInt string 2)

    (char? string)
    (Character/digit string 2)))

(def demo "00100
11110
10110
10111
10101
01111
00111
11100
10000
11001
00010
01010")

(def demo-numbers (-> demo (string/split #"\n")))

(def lines (-> (slurp "inputs/day3.txt")
               (string/split #"\n")))

(def binary-numbers lines)

;;part one
(defn string->map
  [string]
  (->> string
       (map #(Character/digit % 2))
       (zipmap (range (count string)))))

(defn frequent-bit
  [numbers func idx]
  (let [default (if (= func >) 1 0)
        counts  (->> numbers
                     (map string->map)
                     (map #(get % idx))
                     (frequencies))]
    (cond
      (= (get counts 0 0) (get counts 1 0))
      default

      (func (get counts 0 0) (get counts 1 0))
      0

      :else
      1)))

(defn bit-flip
  [bit]
  (case bit
    0 1
    1 0))

(let [size (->> demo-numbers first count)]
  (->> (map #(frequent-bit demo-numbers > %) (range size))
       (reduce str "")
       parse-binary))

(let [size (->> binary-numbers first count)]
  (* (->> (map #(frequent-bit binary-numbers > %) (range size))
          (reduce str "")
          parse-binary)
     (->> (map #(frequent-bit binary-numbers > %) (range size))
          (map bit-flip)
          (reduce str "")
          parse-binary))) ;; => 3320834


;; part two
(defn filter-for
  [numbers default flip]
  (first (reduce
          (fn
            [nums idx]
            (let [freq     (frequent-bit nums flip idx)
                  filtered (filter #(= (Character/digit (nth % idx) 2) freq) nums)]
              (if (empty? filtered)
                nums
                filtered)))
          numbers
          (range (count (first numbers))))))

(* (parse-binary (filter-for binary-numbers 1 >))
   (parse-binary (filter-for binary-numbers 0 <)))

(* (parse-binary (filter-for demo-numbers 1 >))
   (parse-binary (filter-for demo-numbers 0 <)));; => 506

;; => 7627275
;; => 4514787
;; => 4481199


(for [x [1 2 3]
      y [1 2 3]
      :while (<= x y)
      z [1 2 3]]
  [x y z])

(for [x [1 2 3]
      y [1 2 3]
      z [1 2 3]
      :while (<= x y)]
  [x y z])