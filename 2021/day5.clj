(ns aoc.2021.day5
  (:import java.lang.Math)
  (:require [clojure.string :as string]
            [clojure.java.io :as io]))

(def demo
  "0,9 -> 5,9
8,0 -> 0,8
9,4 -> 3,4
2,2 -> 2,1
7,0 -> 7,4
6,4 -> 2,0
0,9 -> 2,9
3,4 -> 1,4
0,0 -> 8,8
5,5 -> 8,2")

(defn parse-line
  [line]
  (let [coordinates (rest (re-find #"([0-9]+),([0-9]+) -> ([0-9]+),([0-9]+)" line))]
    (->> coordinates
         (map (fn [xy] (Integer/parseInt xy)))
         (partition 2)
         (mapv vec))))

(def demo-numbers (-> demo
                      (string/split #"\n")
                      (->> (map parse-line))))

(def input (-> (slurp "inputs/day5.txt")
               (string/split #"\n")
               (->> (map parse-line))))

(def demo-width   (->> demo-numbers
                       (reduce (fn [m [[x1 y1] [x2 y2]]] (max m x1 x2)) 0)))

(def input-width   (->> input
                        (reduce (fn [m [[x1 y1] [x2 y2]]] (max m x1 x2)) 0)))

(def input-height   (->> input
                         (reduce (fn [m [[x1 y1] [x2 y2]]] (max m y1 y2)) 0)))

(defn make-board
  [width height]
  (->> (repeat 0)
       (take (inc width))
       (repeat)
       (take (inc height))
       (mapv vec)))


(defn transpose
  [vectors]
  (vec (apply map vector vectors)))

(defn increment
  [board x y1 y2]
  (let [row          (nth board x)
        modified-row (vec (map-indexed (fn [idx el] (if (or (<= y1 idx y2)
                                                            (<= y2 idx y1)) (inc el) el)) row))]
    (assoc board x modified-row)))


(defn increment-board
  [board [[x1 y1] [x2 y2]]]
  (cond
    (= x1 x2) (-> board transpose (increment x1 y1 y2) transpose)
    (= y1 y2) (increment board y1 x1 x2)
    :else     board))

(->> (reduce increment-board (make-board demo-width demo-height) demo-numbers)
     flatten
     (filter #(>= % 2))
     count)

;; part one
(comment (->> (reduce increment-board (make-board input-width input-height) input)
              flatten
              (filter #(>= % 2))
              count))

;; part two
(defn increment-straight
  [board x y1 y2]
  (let [row          (nth board x)
        modified-row (vec (map-indexed (fn [idx el] (if (or (<= y1 idx y2)
                                                            (<= y2 idx y1)) (inc el) el)) row))]
    (assoc board x modified-row)))

(defn abs
  [n]
  (max (- 0 n) n))

[[2 0] [0 2]]

[[0 0 1]
 [0 1 0]
 [1 0 0]]

[[2 0] [1 1] [0 2]]

(defn increment-diag
  [board [[x1 y1] [x2 y2]]]
  (let [diff  (- x1 y1)
        total (+ x1 y1)]
    (cond
      (= (+ x1 y1)
         (+ x2 y2))
      (->> board
           (map-indexed (fn [idy row]
                          (if (or (<= y1 idy y2)
                                  (<= y2 idy y1))
                            (map-indexed (fn [idx el]
                                           (if (= (+ idx idy)
                                                  total)
                                             (inc el)
                                             el))
                                         row)
                            row)))
           (mapv vec))

      (= (- x1 y1)
         (- x2 y2))
      (->> board
           (map-indexed (fn [idy row]
                          (if (or (<= y1 idy y2)
                                  (<= y2 idy y1))
                            (map-indexed (fn [idx el]
                                           (if (= (- idx idy)
                                                  diff)
                                             (inc el)
                                             el))
                                         row)
                            row)))
           (mapv vec)))))

(defn increment-board2
  [board [[x1 y1] [x2 y2]]]
  (cond
    (= x1 x2) (-> board transpose (increment-straight x1 y1 y2) transpose)
    (= y1 y2) (increment-straight board y1 x1 x2)
    :else     (increment-diag board [[x1 y1] [x2 y2]])))


(->> (reduce increment-board2 (make-board demo-width demo-height) demo-numbers)
     flatten
     (filter #(>= % 2))
     count)

(->> (reduce increment-board2 (make-board input-width input-height) input)
     flatten
     (filter #(>= % 2))
     count)
