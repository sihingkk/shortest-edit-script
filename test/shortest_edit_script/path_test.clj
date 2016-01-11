(ns shortest-edit-script.path-test
  (:use clojure.test
        shortest-edit-script.path))

(def example (envelope ["A" "B" "C" "A" "B" "B" "A"] ["C" "B" "A" "B" "A" "C"]))

(deftest creating-envelopes
  (testing "go right and left if there is no diagonal"
    (is (=  [[[    "B" "C" "A" "B" "B" "A"]["C" "B" "A" "B" "A" "C"][:right]]
             [["A" "B" "C" "A" "B" "B" "A"][    "B" "A" "B" "A" "C"][:down]]]
            (next-envelope example) )))
  (testing "if there is diagonal - follow it immediately as long as its possible"
    (is (=  [[[            "A" "B" "B" "A"][    "B" "A" "B" "A" "C"][:right :right :cross]]
             [[        "C" "A" "B" "B" "A"][        "A" "B" "A" "C"][:right :down :cross]]
             [[        "C" "A" "B" "B" "A"][                "A" "C"][:down :down :cross :cross]]]
            (-> example next-envelope next-envelope))))
  (testing "choose only direction that have further previouse move"
    (is (=  [[[                    "B" "A"][        "A" "B" "A" "C"][:right :right :cross :right :cross]]
             [[                    "B" "A"][                "A" "C"][:right :right :cross :down :cross :cross]]
             [[                "B" "B" "A"][                    "C"][:down :down :cross :cross :right :cross]]
             [[            "A" "B" "B" "A"][                       ][:down :down :cross :cross :down :cross]]]
            (-> example next-envelope next-envelope next-envelope)))))

(deftest finding-paths
  (testing "is able to find proper shortest change path"
    (is (= [:right :right :cross :down :cross :cross :right :cross :down]
           (shortest-path ["A" "B" "C" "A" "B" "B" "A"] ["C" "B" "A" "B" "A" "C"])))))
