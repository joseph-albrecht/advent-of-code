(ns aoc.2021.day2
  (:import java.lang.Math)
  (:require [clojure.string :as string]
            [clojure.java.io :as io]))

(def lines (-> (slurp "inputs/day2.txt")
               (string/split #"\n")))

(def moves (->> lines
                (map #(string/split % #" "))
                (map (fn [[move amt]] [move (Integer/parseInt amt)]))))

;; part one
(defn move
  [[x y] [movement amt]]
  (cond
    (= movement "forward")
    [(+ x amt) y]
    (= movement "up")
    [x (- y amt)]
    (= movement "down")
    [x (+ y amt)]))

(->> (reduce move [0 0] moves)
     (reduce *));; => 1728414

;; part two
(defn move2
  [[x y aim] [movement amt]]
  (cond
    (= movement "forward")
    [(+ x amt) (+ y (* aim amt)) aim]
    (= movement "up")
    [x y (- aim amt)]
    (= movement "down")
    [x y (+ aim amt)]))

(->> (reduce move2 [0 0 0] moves)
     (take 2)
     (reduce *));; => 1765720035