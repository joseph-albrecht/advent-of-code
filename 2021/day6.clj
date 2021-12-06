(ns aoc.2021.day6
  (:import java.lang.Math)
  (:require [clojure.string :as string]
            [clojure.java.io :as io]))

(defn make-pool
  []
  (zipmap (range 9) (repeat 0)))

(defn parse-fish
  [input]
  (let [fish (-> input
                 (string/split #",")
                 (->> (mapv (fn [num] (Integer/parseInt num))))
                 )
        pool (reduce (fn [school fish]
                       (update school fish (fn [val] (if val (inc val) 1))))
                     {} fish)]
    (reduce (fn [pool age]
              (update pool age #(or % 0)))
            pool
            (range 9))))

(defn age-fish
  [pool]
  (reduce
   (fn [new-pool age]
     (if (= age 0)
       (-> new-pool
           (update 8 #(+ % (get pool age 0)))
           (update 6 #(+ % (get pool age 0))))
       (update new-pool (- age 1) #(+ % (get pool age 0)))))
   (make-pool)
   (range 9)))

(defn count-fish
  [pool]
  (reduce + (vals pool)))

(def demo
  "3,4,3,1,2")

(def input (slurp "inputs/day6.txt"))

(def demo-fish (parse-fish demo))

(def input-fish (parse-fish input))

(count-fish (nth (iterate age-fish input-fish) 80));; => 380243
(count-fish (nth (iterate age-fish input-fish) 256));; => 1708791884591



