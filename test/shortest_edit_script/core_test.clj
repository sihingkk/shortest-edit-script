(ns shortest-edit-script.core-test
    (:use clojure.test
          shortest-edit-script.core))

(deftest recongnize-one-elements
  (is (= [{:add {:revision ["a"]}, :index 0}]
         (shortest-edit-script [] ["a"])))
  (is (= [{:del {:origin ["a"]}, :index 0}]
         (shortest-edit-script ["a"] [])))
  (is (= [{:change {:origin ["a"], :revision ["b"]}, :index 0}]
         (shortest-edit-script ["a"] ["b"])))
  (is (= [{:del {:origin ["A" "B"]}, :index 0}
          {:add {:revision ["B"]}, :index 1}
          {:del {:origin ["B"]}, :index 4}
          {:add {:revision ["C"]}, :index 5}]
         (shortest-edit-script ["A" "B" "C" "A" "B" "B" "A"] ["C" "B" "A" "B" "A" "C"]))))
