(ns trifl.xml
  (:import
    (java.io
      StringReader
      StringWriter)
    (javax.xml.transform
      OutputKeys
      TransformerFactory)
    (javax.xml.transform.stream
      StreamResult
      StreamSource)))

(defn pretty-xml [xml]
  (let [reader (new java.io.StringReader xml)
        writer (new StringWriter)
        in (new StreamSource reader)
        out (new StreamResult writer)
        transformer (.newTransformer (TransformerFactory/newInstance))
        indent-amount "{http://xml.apache.org/xslt}indent-amount"]
    (.setOutputProperty transformer OutputKeys/INDENT "yes")
    (.setOutputProperty transformer indent-amount "2")
    (.setOutputProperty transformer OutputKeys/METHOD "xml")
    (.transform transformer in out)
    (-> out
        .getWriter
        .toString)))
