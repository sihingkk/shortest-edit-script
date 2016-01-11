(ns shortest-edit-script.core
  (:use shortest-edit-script.path))

(defn compress [path] 
  (->> path
       (partition-by #(= :cross %))
       (map frequencies)))

(defn negative->zero [it]
  (if (< 0 it) it 0))

(defn shortest-edit-script 
  ([xs ys] 
   (shortest-edit-script xs ys (compress (shortest-path xs ys)) [] 0))
  ([xs ys path script index]   
   (if (empty? path) script
       (let [path-segment (first path)
             rights (or (:right path-segment) 0)
             downs (or (:down path-segment) 0)
             crosses (or (:cross path-segment) 0)
             changes (min rights downs)
             deletions (negative->zero (- rights changes))
             additions (negative->zero (- downs changes))]         
         (recur (drop (+ crosses changes deletions) xs)
                (drop (+ crosses changes additions) ys)
                (rest path)
                (concat script
                        (if (< 0 changes) [{:change {:origin (take changes xs)
                                                       :revision (take changes ys)}
                                             :index index}])
                        (if (< 0 deletions) [{:del {:origin (take deletions (drop changes xs))}
                                               :index (+ index changes)}])
                        (if (< 0 additions) [{:add {:revision (take additions (drop changes ys))}
                                               :index (+ index changes)}]))
                (+ index changes crosses additions))))))
