(ns sas.core
  (:gen-class)
  (:require [clojure.java.shell :refer [sh]]
            [clojure.string :as string]))

;;;;;;;;;;;;;;;;;;;;
;; System helpers

(defn cmd [& c] (sh "bash" "-c" (apply str c)))

(defn select [opts]
  (let [dmenu-entries (string/join "\n" opts)]
    (->> (cmd "echo -e '" dmenu-entries "' | dmenu -l 5 -i")
         :out
         string/trim)))

;;;;;;;;;;;;;;;;;;;;
;; Script helpers

(defn parse-line [line]
  (as-> (string/replace line #"[=:<>\"]" "") _
    (string/trim _)
    (let [[k v] (string/split _ #"\s" 2)]
      [(keyword k) (string/trim v)])))

(defn get-device-map []
  (as-> (cmd  "pacmd list-sinks |grep -e 'name:' -e 'device.description'") _
    (:out  _)
    (string/split _ #"\n")
    (map parse-line _)
    (partition 2 _)
    (map #(into {} %) _)
    (map (fn [dev] [(:device.description dev) (:name dev)]) _)
    (into {} _)
    ))

(defn set-default-sink [sink-name]
  (cmd "pacmd set-default-sink " sink-name))

;;;;;;;;;;;;;;;;;;;;
;; Script

(defn -main []
  (def devices (get-device-map))
  (def selected-device (select (keys devices)))
  (def sink-name (devices selected-device))
  (set-default-sink sink-name)
  (shutdown-agents))
