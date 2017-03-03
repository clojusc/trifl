check:
	lein test :all

check-jar-config:
	@lein uberjar
	java -cp target/trifl-$(VERSION)-standalone.jar \
	clojusc.env_ini_test show-example-ini
