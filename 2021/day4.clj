(ns aoc.2021.day4
  (:import java.lang.Math)
  (:require [clojure.string :as string]
            [clojure.java.io :as io]))

(def demo
  "7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1

22 13 17 11  0
 8  2 23  4 24
21  9 14 16  7
 6 10  3 18  5
 1 12 20 15 19

 3 15  0  2 22
 9 18 13 17  5
19  8  7 25 23
20 11 10 24  4
14 21 16 12  6

14 21 17 24  4
10 16 15  9 19
18  8 23 26 20
22 11 13  6  5
 2  0 12  3  7")

(second [1 2])

(defn parse-board-line
  [line]
  (let [matcher (re-matcher #" *([0-9]+)" line)]
    (vec (for [x (range 5)]
           (Integer/parseInt (second (re-find matcher)))))))

(defn parse-number-line
  [line]
  (-> line
      (string/split #",")
      (->> (map (fn [num] (Integer/parseInt num))))
      vec))

(defn parse-board
  [board]
  (mapv parse-board-line board))

(defn read-board
  [board x y]
  (nth (nth board x) y))

(defn stamp-board
  [board number]
  (println board)
  (mapv #(vec (for [x    (range 5)
                    :let [cell (nth % x)]]
                (if (= cell number)
                  true
                  cell)))
        board))

(defn winning-line?
  [line]
  (every? (partial = true) line))

(defn get-column
  [board column]
  (mapv #(nth % column) board))

(defn get-columns
  [board]
  (mapv (partial get-column board) (range 5)))
;; transpose: (apply map vector [[1 2 3] [4 5 6]])

(defn check-board
  [board]
  (or (when (some winning-line? board) board)
      (when (some winning-line? (get-columns board)) board)))

(defn check-boards
  [boards]
  (some check-board boards))

(defn stamp-boards
  [boards number]

  (map #(stamp-board % number) boards))

(check-board [[57 7 8 38 31] [17 96 5 12 18] [58 45 81 89 4] [73 51 93 32 10] [true true true true true]])
(some winning-line? [[57 7 8 38 31] [17 96 5 12 18] [58 45 81 89 4] [73 51 93 32 10] [true true true true true]])

(def demo "7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1

22 13 17 11  0
 8  2 23  4 24
21  9 14 16  7
 6 10  3 18  5
 1 12 20 15 19

 3 15  0  2 22
 9 18 13 17  5
19  8  7 25 23
20 11 10 24  4
14 21 16 12  6

14 21 17 24  4
10 16 15  9 19
18  8 23 26 20
22 11 13  6  5
 2  0 12  3  7")

(def demo-numbers (-> demo
                      (string/split #"\n\n")
                      first
                      parse-number-line))

(def demo-boards (-> demo
                     (string/split #"\n\n")
                     rest
                     (->> (map #(string/split % #"\n"))
                          (map parse-board))))

(def input (slurp "inputs/day4.txt"))

(def numbers (-> input
                 (string/split #"\n\n")
                 first
                 parse-number-line))

(def boards (-> input
                (string/split #"\n\n")
                rest
                (->> (map #(string/split % #"\n"))
                     (map parse-board))))

(defn calculate-score
  [board last-number]
  (let [unstamped-numbers
        (->> (for [x     (range 5)
                   y     (range 5)
                   :let  [cell (read-board board x y)]
                   :when (not= cell true)]
               cell))]
    (->> unstamped-numbers
         (reduce +)
         (* last-number))))

(reduce (fn [boards number]
          (let [stamped-boards (map #(stamp-board % number) boards)
                winning-board  (check-boards stamped-boards)]
            (if winning-board
              (reduced (calculate-score winning-board number))
              stamped-boards))) boards numbers)
;; => 58838
(reduce (fn [boards number]
          (let [stamped-boards   (map #(stamp-board % number) boards)
                remaining-boards (remove check-board stamped-boards)]
            (if (and (= (count remaining-boards) 0)
                     (check-board (first stamped-boards)))
              (reduced (calculate-score (first stamped-boards) number))
              remaining-boards))) boards numbers)
;; => 6256

