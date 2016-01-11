(ns shortest-edit-script.path)

(defn envelope [xs ys] [[xs ys []]])

(defn- move-right [[xs ys path]]
  [(rest xs) ys (conj path :right)])

(defn- move-down [[xs ys path]]
  [xs (rest ys) (conj path :down)])

(defn- move-cross [[xs ys path :as envelope]]  
  (if (and (not (empty? xs)) (= (first xs) (first ys)))
    (recur [(rest xs) (rest ys) (conj path :cross)])
    envelope))

(defn- left-is-further [[top-xs _ _] [left-xs _ _]]
  (>= (count top-xs) (count left-xs)))

(defn next-envelope [paths]
  (map (fn [[top left]]           
         (if (or (= top :guardian)
                 (and (not= left :guardian) (left-is-further top left))) 
           (-> left move-right move-cross)
           (-> top move-down move-cross)))
       (zipmap (cons :guardian paths)
               (concat paths [:guardian]))))

(defn path-stream [xs ys]
  (iterate next-envelope (envelope xs ys)))

(defn to-solution [envelopes]
  (->> envelopes
      (map (fn [[xs ys path]] (if (= [] xs ys) path [])))
      (filter not-empty)))

(defn shortest-path [xs ys]
  (first (filter not-empty (mapcat to-solution
                            (path-stream xs ys)))))
