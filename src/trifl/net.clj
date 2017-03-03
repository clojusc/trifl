(ns trifl.net)

(defn get-local-ip []
  (.getHostAddress (java.net.InetAddress/getLocalHost)))
