(ns trifl.net)

(defn get-local-host
  "Get the java.net.InetAddress object for the local host."
  []
  (java.net.InetAddress/getLocalHost))

(defn get-local-ip []
  "Get the IP address for the machine that this JVM is running on.

  Uses the `java.net.InetAddress` method `getHostAddress`."
  (.getHostAddress (get-local-host)))

(defn get-local-hostname
  "Get the hostname for the machine that this JVM is running on.

  Uses the `java.net.InetAddress` method `getHostName`."
  []
  (.getHostName (get-local-host)))
